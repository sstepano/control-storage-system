package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemWarehouseModel extends JpaRepository <ItemWarehouse, Integer> {
	List <ItemWarehouse> findAllByWarehouseId(Integer warehouseId);
	List <ItemWarehouse> findAllByItemIdOrderByWarehouseId(Integer itemId);
	List <ItemWarehouse> findAllByWarehouseIdAndItemId(Integer warehouseId, Integer itemId);
	List <ItemWarehouse> findAllByWarehouseIdAndItemIdAndRowIdAndShelfIdAndVerticalId(Integer warehouseId, Integer itemId, String rowId, String shelfId, String verticalId);
	
	String qry1 = """
				SELECT 
				    1 ID -- RM
				  , NULL WAREHOUSE_ID
				  , ITEM_ID
				  , SUM(QTY) QTY
				  , SUM(RESERVED_QTY) RESERVED_QTY
				  , SUM(SOLD_QTY) SOLD_QTY
				  , NULL MIN_FOR_SALE_QTY
				  , NULL MAX_FOR_SALE_QTY
				  , NULL ROW_ID
				  , NULL SHELF_ID
				  , NULL VERTICAL_ID
				  , NULL IS_ACTIVE
				FROM item_warehouse iw
				JOIN warehouse w ON iw.WAREHOUSE_ID = w.ID
				WHERE 1=1
				  AND iw.ITEM_ID = :itemId
				  AND w.IS_COMMISSION = FALSE
				UNION ALL
				SELECT 
				    2 ID -- komision
				  , NULL WAREHOUSE_ID
				  , ITEM_ID
				  , SUM(QTY) QTY
				  , SUM(RESERVED_QTY) RESERVED_QTY
				  , SUM(SOLD_QTY) SOLD_QTY
				  , NULL MIN_FOR_SALE_QTY
				  , NULL MAX_FOR_SALE_QTY
				  , NULL ROW_ID
				  , NULL SHELF_ID
				  , NULL VERTICAL_ID
				  , NULL IS_ACTIVE
				FROM item_warehouse iw
				JOIN warehouse w ON iw.WAREHOUSE_ID = w.ID
				WHERE 1=1
				  AND iw.ITEM_ID = :itemId
				  AND w.IS_COMMISSION = TRUE
				UNION ALL
				SELECT 
				    3 ID -- total
				  , NULL WAREHOUSE_ID
				  , ITEM_ID
				  , SUM(QTY) QTY
				  , SUM(RESERVED_QTY) RESERVED_QTY
				  , SUM(SOLD_QTY) SOLD_QTY
				  , NULL MIN_FOR_SALE_QTY
				  , NULL MAX_FOR_SALE_QTY
				  , NULL ROW_ID
				  , NULL SHELF_ID
				  , NULL VERTICAL_ID
				  , NULL IS_ACTIVE
				FROM item_warehouse iw
				JOIN warehouse w ON iw.WAREHOUSE_ID = w.ID
				WHERE 1=1
				  AND iw.ITEM_ID = :itemId
			""";
	@Query(value = qry1, nativeQuery = true)
	List <ItemWarehouse> findSumByItemId(Integer itemId);
	
	
	/**
	 * Dodaje na stanje prosledjuenu vrednost, trazeci warehouseid i itemid (sto je ujedno i composite key, samo jedan red moze biti)
	 */
	String qry2 = """
			update ItemWarehouse iw
			    set iw.qty = iw.qty + :itemQty 
			where 1=1
				and iw.itemId = :itemId
			    and iw.warehouseId = :warehouseId
		""";
	@Transactional
	@Modifying
	@Query(value=qry2, nativeQuery=false)
	Object appendItemQtyByWarehouseIdAndItemId(Integer warehouseId, Integer itemId, Integer itemQty);
}
