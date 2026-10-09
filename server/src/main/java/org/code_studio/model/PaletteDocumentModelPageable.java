package org.code_studio.model;

import java.util.List;

import org.code_studio.database.PaletteDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaletteDocumentModelPageable extends PagingAndSortingRepository <PaletteDocument, Integer> {
	//------------------------------------------------------------------------	
	@Query("select p from PaletteDocument p")
	List <PaletteDocument> findAllPageable(Pageable pageable);
	//------------------------------------------------------------------------
	@Query("select p from PaletteDocument p where clientId = :clientId order by id desc")
	List <PaletteDocument> findAllPageableByClientId(Integer clientId, Pageable pageable);
	//------------------------------------------------------------------------
	@Query(value = """
			select p.* from Palette_Document p 
			where 1=1
			  and client_Id = :clientId 
			  and type_Id = :typeId
			  and 1 = case 
				  when status_Id = 1 and :statusId = 1 then 1
		          when status_Id = 2 and :statusId = 2 then 1
		          when status_Id = 3 and :statusId = 3 then 1
		          when status_Id = 4 and :statusId = 4 then 1
		          when status_Id = 5 and :statusId = 5 then 1
		          when status_Id = 6 and :statusId = 6 then 1
		          when status_Id = 7 and :statusId = 7 then 1
		          when type_id = 1 and status_Id in (1, 2, 6, 7) and :statusId = 0 then 1
		          when type_id = 2 and status_Id in (1, 4, 6, 7) and :statusId = 0 then 1  
	          else 0 end
			  order by id desc
		""", nativeQuery = true)
	List <PaletteDocument> allPageableByClientIdAndStatusIdAndTypeIdOrderByIdDesc(Integer clientId, Integer statusId, Integer typeId, Pageable pageable);
	//------------------------------------------------------------------------
	@Query("""
			select p from PaletteDocument p 
			where 1=1
			  and clientId = :clientId
			  and typeId = 1
			  and statusId in (1, 2, 3) 
		    order by id desc	
		""")
	List <PaletteDocument> allPageableByClientIdStoringOrderByIdDesc(Integer clientId,  Pageable pageable);
	//------------------------------------------------------------------------


}
