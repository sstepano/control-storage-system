package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemBranch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemBranchModelPageable extends PagingAndSortingRepository <ItemBranch, Integer> {
		
	@Query("select c from ItemBranch c")
	List <ItemBranch> findAllPageable(Pageable pageable);

}
