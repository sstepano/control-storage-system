package org.code_studio.model;

import java.util.List;

import org.code_studio.database.PaletteItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaletteItemModelPageable extends PagingAndSortingRepository <PaletteItem, Integer> {
	//------------------------------------------------------------------------	
	@Query("select p from PaletteItem p")
	List <PaletteItem> findAllPageable(Pageable pageable);
	//------------------------------------------------------------------------
	@Query("select p, i from PaletteItem p join Item i on p.itemId = i.id where p.paletteId = :paletteId")
	List <PaletteItem> findAllPageableByPaletteId(Integer paletteId, Pageable pageable);
	//------------------------------------------------------------------------
	String qry1 = """
			select pi, i from PaletteDocument d 
			join Palette p on d.id = p.documentId
			join PaletteItem pi on p.id = pi.paletteId
            join Item i on pi.itemId = i.id
			where 1=1
			  and p.statusId = 3
			  and d.clientId = :clientId
			""";	
	@Query(qry1)
	List <PaletteItem> findAllPageableByClientId(Integer clientId, Pageable pageable);
	//------------------------------------------------------------------------	
}
