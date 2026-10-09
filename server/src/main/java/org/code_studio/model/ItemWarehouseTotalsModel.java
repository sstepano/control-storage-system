package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemWarehouseTotals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemWarehouseTotalsModel extends JpaRepository <ItemWarehouseTotals, Integer> {
	
	String qry1 = """
			SELECT
			    1 ID
			  , iw.WAREHOUSE_ID
			  , count(*) ITEM_CNT
			  , sum(iw.QTY - iw.RESERVED_QTY) AVAILABLE_QTY
			  , sum(iw.RESERVED_QTY) RESERVED_QTY
			  , sum(iw.QTY) QTY 
			FROM item_warehouse iw
			JOIN warehouse w ON iw.WAREHOUSE_ID = w.ID
			JOIN item i ON iw.ITEM_ID = i.ID
			WHERE 1=1
			AND iw.WAREHOUSE_ID = :warehouseId
			""";
	@Query(value = qry1, nativeQuery = true)
	List <ItemWarehouseTotals> findAllByWarehouseId(Integer warehouseId);
	
}
