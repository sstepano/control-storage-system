package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemTypeModelPageable extends PagingAndSortingRepository <ItemType, Integer> {
		
	@Query("select i from ItemType i")
	List <ItemType> findAllPageable(Pageable pageable);

}
