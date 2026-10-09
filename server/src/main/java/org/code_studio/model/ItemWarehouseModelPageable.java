package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemWarehouse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemWarehouseModelPageable extends PagingAndSortingRepository <ItemWarehouse, Integer> {
		
	@Query("select iw from ItemWarehouse iw")
	List <ItemWarehouse> findAllPageable(Pageable pageable);
	List <ItemWarehouse> findAllByWarehouseId(Integer warehouseId, Pageable pageable);

	
	String qry1 = """
			SELECT iw.*
			FROM item_warehouse iw
			JOIN item i ON iw.ITEM_ID = i.ID
			WHERE 1=1
			  AND iw.WAREHOUSE_ID = :warehouseId
			  AND (
			       iw.ITEM_ID = cast(:itemIdOrItemName as INTEGER) 
			    OR i.NAME LIKE CONCAT('%', :itemIdOrItemName, '%')
		      )
		""";
	@Query(value = qry1, nativeQuery = true)
	List <ItemWarehouse> findAllByWarehouseIdAndItemIdOrItemName(Integer warehouseId, String itemIdOrItemName, Pageable pageable);
	
}
