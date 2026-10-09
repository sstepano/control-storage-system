package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Palette;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaletteModelPageable extends PagingAndSortingRepository <Palette, Integer> {
	//------------------------------------------------------------------------	
	@Query("select p from Palette p")
	List <Palette> findAllPageable(Pageable pageable);
	//------------------------------------------------------------------------
	String qry1 = """
			select p from Palette p
			where p.documentId = :paletteDocumentId
			order by p.id desc
			""";
	@Query(qry1)
	List <Palette> findAllPageableByDocumentId(Integer paletteDocumentId, Pageable pageable);
	//------------------------------------------------------------------------
	@Query("""
			select p from Palette p 
			join PaletteDocument d on d.id = p.documentId 
			where 1=1
			  and d.clientId = :clientId 
			order by p.id desc
		""")
	List <Palette> findAllPageableByClientId(Integer clientId, Pageable pageable);
	//------------------------------------------------------------------------
	
	
	//------------------------------------------------------------------------	
	String qry3 = """
		select p from Palette p
		join PaletteItem pi on p.id = pi.paletteId
		where 1=1
		  and p.statusId = 3
		  and pi.itemId = :itemId 
	""";
	@Query(value = qry3)
	List <Palette> findAllPageableStoredByItemId(Integer itemId, Pageable pageable);
	//------------------------------------------------------------------------
	String qry4 = """
			select p from Palette p
			join PaletteItem pi on p.id = pi.paletteId
			where 1=1
			  and p.statusId = 3
			  and upper(pi.description) like '%' || upper( :description ) || '%' 
	""";
	@Query(value = qry4)
	List <Palette> findAllPageableStoredByDescription(String description, Pageable pageable);
	//------------------------------------------------------------------------
	String qry5 = """
			select p from PaletteDocument d 
			join Palette p on d.id = p.documentId
			left join PaletteItem pi on p.id = pi.paletteId
			where 1=1
			  and p.statusId = 1
			  and d.clientId = :clientId
			  order by p.id desc
	""";
	@Deprecated
	@Query(value = qry5)
	List <Palette> findAllPageableStoringByClientId(Integer clientId, Pageable pageable);
	
	//------------------------------------------------------------------------
	/**
	 * All STORED by ClientID, used in GoodsOut -> Palette table
	 * @param clientId
	 * @return
	 */
	@Query("""
			select p
			from Palette p
			join PaletteDocument d on p.documentId = d.id
			where 1=1
				and p.statusId = 3
				and d.clientId = :clientId
		""")
	List<Palette> findAllPageableStoredByClientId(Integer clientId, Pageable pageable);
	//------------------------------------------------------------------------
	
	
}
