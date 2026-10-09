package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemWarehouseBalance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemWarehouseBalanceModelPageable extends JpaRepository <ItemWarehouseBalance, Integer> {
	
	String qry1 = """
				SELECT
				    iw.ID
				  , i.CODE ITEM_CODE
				  , i.NAME ITEM_NAME
				  , iw.ROW_ID
				  , iw.SHELF_ID
				  , iw.VERTICAL_ID
				  , iw.QTY - iw.RESERVED_QTY AVAILABLE_QTY
				  , iw.RESERVED_QTY
				  , iw.QTY 
				FROM item_warehouse iw
				JOIN warehouse w ON iw.WAREHOUSE_ID = w.ID
				JOIN item i ON iw.ITEM_ID = i.ID
				WHERE 1=1
				AND iw.WAREHOUSE_ID = :warehouseId
			""";
	
	@Query(value=qry1, nativeQuery=true)
	List <ItemWarehouseBalance> findAllPageableByWarehouseId (Integer warehouseId, Pageable pageable);

}
