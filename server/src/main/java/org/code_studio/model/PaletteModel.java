package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Palette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface PaletteModel extends JpaRepository <Palette, Integer> {
	
	String qry1 = """
					select *
					from palette
					where 1=1
					    and STATUS_ID in (1, 2, 3, 4) -- uzimamo sve jer mogu da budu u radu npr, pa da se uzme vise od jedne pozicije, sto ne zelimo
					order by
					    cast(trim(leading '0' from row_id) as INTEGER)
					  , cast(trim(leading '0' from shelf_id) as INTEGER)
					  , cast(trim(leading '0' from vertical_id) as INTEGER)
					""";
	@Deprecated
	@Query(value = qry1, nativeQuery = true)
	List <Palette> storedPalettes();
	
	String qry2 = """
					select count(p.id)
					from Palette p
					join PaletteDocument pd on p.documentId = pd.id
					where 1=1
					    and p.statusId = 3
					    and pd.clientId = :clientId
					group by pd.clientId
			""";
	@Query(value = qry2, nativeQuery = false)
	List<Integer> storedPaletteCountByClientId(Integer clientId);
	
	@Deprecated
	@Transactional
	@Modifying
	@Query(value = """
	update Palette p set
	p.status_id = 3
	where 1=1
	and p.palette_code = :paletteRMCoords
	and p.status_id in (1, 2, 6) -- u procesu ulaza ili procesira se
	""", nativeQuery = true)
	Object store(String paletteRMCoords);
	
	
	@Query(value = """
	SELECT p.* FROM palette p
	WHERE 1=1
	AND p.STATUS_ID in (2, 3, 4, 6, 8) -- Pokrenut ulaz, Smeštena, Pokrenut izlaz, Procesira se, Zakljucana
	AND p.ROW_ID = SUBSTRING(CAST(:rowId as varchar(2)), 1, 1)
	AND p.SHELF_ID % 2 = substring(cast(:rowId as varchar(2)), 2, 1) % 2 -- parni
	order by row_id, shelf_id, vertical_id;
	""", nativeQuery = true)
	List<Palette> findAllOccupiedByRowId(Integer rowId);	

	
	/**
	 * @return
	 */
	@Query(value = """
			select p.paletteCode
			FROM Palette p
			join PaletteDocument d on p.documentId = d.id
			WHERE 1=1
			  and d.typeId = 1
			  and d.statusId in (2, 6)
			  and p.statusId = 2
			ORDER BY d.id, p.id
			LIMIT 1
		""", nativeQuery = false)
	List<String> findNextPaletteForStorage();
	
	
	/**
	 * @return
	 */
	@Query(value = """
			select p.paletteCode
			FROM Palette p
			join PaletteDocument d on p.documentId = d.id
			WHERE 1=1
			  and d.typeId = 2
			  and d.statusId in (4, 6)
			  and p.statusId = 4
			ORDER BY d.id, p.id
			LIMIT 1
		""", nativeQuery = false)
	List<String> findNextPaletteForOutput();
	
	
	/**
	 * Every update returns an Integer with affected row count
	 * @param paletteRMCoords
	 * @param statusId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value = """
	update Palette p set
	p.status_id = :statusId
	where 1=1
	  and p.palette_code = :paletteRMCoords
	""", nativeQuery = true)
	Object updateStatus(String paletteRMCoords, Integer statusId);
	
	@Deprecated
	@Transactional
	@Modifying
	@Query(value = """
		UPDATE PALETTE_DOCUMENT pd
	    JOIN palette p ON pd.ID = p.DOCUMENT_ID AND p.PALETTE_CODE = :paletteRMCoords
  		SET pd.STATUS_ID = case :paletteStatusId
  				  when 4 then 4 -- Sve palete naloga su u statusu SMEŠTEN.
  				  when 7 then 6 -- Dokument izasao iz magacina
	    END
		WHERE 1=1
			AND pd.STATUS_ID = case :paletteStatusId
				when 4 then 3 -- already stored, next storing
				when 7 then 5 -- stored, now exiting
		      END
			  AND (
		        SELECT COUNT(*) 
		        FROM palette pp 
		        WHERE 1=1
		          AND pp.DOCUMENT_ID = pd.ID 
		          AND p.STATUS_ID != :paletteStatusId
		      ) = 0;
	""", nativeQuery = true)
	Object updateDocumentStatus(String paletteRMCoords, Integer paletteStatusId);
	
	/**
	 * Used to fetch and exit all palettes connected to this document ID
	 * @param documentId
	 * @return
	 */
	List<Palette> findAllByDocumentId(Integer documentId);
	
	/**
	 * All exited palettes by client id 
	 * @param documentId
	 * @return
	 */
	@Query("""
			select p
			from Palette p
			join PaletteDocument d on p.documentId = d.id
			where 1=1
				and p.statusId = 5
				and d.clientId = :clientId
		""")
	List<Palette> findAllExitedByClientId(Integer clientId);
	
	List<Palette> findAllByDocumentIdAndStatusId(Integer clientId, Integer statusId);

	
	
	@Transactional
	@Modifying
	@Query(value = """
		UPDATE PALETTE SET
		previous_document_id = document_id,
		document_id = :documentId
		where 1=1
		  and id = :paletteId
	""", nativeQuery = true)
	Object updateDocumentId(Integer paletteId, Integer documentId);
	
	
	/**
	 * Returns Lisdt<Integer> - total, occupied, free
	 * @param rowId
	 * @return
	 */
	@Query(value = """
		with cte as (
		select COUNT(*) occupiedCnt FROM palette p
			WHERE 1=1
			AND p.STATUS_ID in (2, 3, 4, 6, 8) -- Pokrenut ulaz, Smeštena, Pokrenut izlaz, Procesira se, Zakljucana
			AND p.ROW_ID = SUBSTRING(CAST(:rowId as varchar(2)), 1, 1)
			AND p.SHELF_ID % 2 = substring(cast(:rowId as varchar(2)), 2, 1) % 2 -- parni
		)
		SELECT 34 * 21 totalCnt union all
		SELECT occupiedCnt FROM cte union all
		SELECT 34 * 21 - occupiedCnt freeCnt FROM cte
	""", nativeQuery = true)
	List<Integer> findCountTotalStoredFreeByRowId (Integer rowId);

}
