package org.code_studio.model;

import java.util.List;

import org.code_studio.database.InventoryListing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryListingModelPageable extends PagingAndSortingRepository <InventoryListing, Integer> {
		
	@Query("select il from InventoryListing il order by il.Id desc")
	List <InventoryListing> findAllPageable(Pageable pageable);

	String qry1 = """
			select il.* 
			from Inventory_Listing il 
			where 1=1
			   and 1 = case WHEN (il.status_Id = :statusId OR :statusId = 0) THEN 1
			   else 0 end
			   and 1 = case 
			   when (il.enumerator_User_Id = :enumeratorUserId OR :enumeratorUserId  = 0 ) then 1
			   else 0 end
			 ORDER BY il.ID DESC
			""";
	
	@Query(value = qry1, nativeQuery = true)
	List <InventoryListing> findAllPageableByStatusIdAndEnumeratorUserId(int statusId, int enumeratorUserId, Pageable pageable);
}
