package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemCatalog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemCatalogModelPageable extends PagingAndSortingRepository <ItemCatalog, Integer> {
		
	@Query("select c from ItemCatalog c")
	List <ItemCatalog> findAllPageable(Pageable pageable);

	
	//@Query("select c from ItemCatalog c where client_id = :clientId")
	List <ItemCatalog> findAllByClientId(Integer clientId, Pageable pageable);

}
