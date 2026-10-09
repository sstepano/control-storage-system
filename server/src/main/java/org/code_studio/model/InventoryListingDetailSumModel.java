package org.code_studio.model;

import java.util.List;

import org.code_studio.database.InventoryListingDetailSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryListingDetailSumModel extends JpaRepository <InventoryListingDetailSum, Integer> {
	
	String qry1 = """
		SELECT
		  ROW_NUMBER() OVER (ORDER BY x.id) ID,
		  INVENTORY_LISTING_ID,
		  COUNTING_NBR,
		  ITEM_ID,
		  ROW_ID,
		  SHELF_ID,
		  VERTICAL_ID,
		  QTY,
		  COUNTED_QTY,
		  DIFFERENCE_QTY
		FROM (
		  SELECT
		    ild.ID,
		    il.ID INVENTORY_LISTING_ID,
		    il.COUNTING_NBR,
		    ild.ITEM_ID,
		    ild.ROW_ID,
		    ild.SHELF_ID,
		    ild.VERTICAL_ID,
		    IFNULL(iw.QTY, 0) QTY,
		    ild.COUNTED_QTY,
		    ild.COUNTED_QTY  - IFNULL(iw.QTY, 0) DIFFERENCE_QTY,
		    ROW_NUMBER() OVER (PARTITION BY ild.ITEM_ID, ild.ROW_ID, ild.SHELF_ID, ild.VERTICAL_ID ORDER BY ild.ID DESC) RANK_ID
		  FROM INVENTORY_LISTING_DETAIL ild
		  JOIN INVENTORY_LISTING il ON ild.INVENTORY_LISTING_ID = il.ID and il.WAREHOUSE_ID = :warehouseId
		  LEFT JOIN ITEM_WAREHOUSE iw ON iw.WAREHOUSE_ID = il.WAREHOUSE_ID AND iw.ITEM_ID = ild.ITEM_ID
          WHERE 1=1
          AND ild.COUNTED_QTY is not null -- iskljucujemo nove naloge za popis
		  ) x
		where x.RANK_ID = 1
		    and 1 = case when (:diff = 1 and DIFFERENCE_QTY!= 0) or :diff = 0 then 1 else 0 end
		    and (ITEM_ID = :itemId or :itemId = 0)
		""";
	
	@Query(value = qry1, nativeQuery = true)
	List <InventoryListingDetailSum> findAllByDiffAndWarehouseIdAndItemId(int diff, int warehouseId, int itemId);
	
	
	String qry2 = """
		SELECT
		  ROW_NUMBER() OVER (ORDER BY x.ID) ID,
		  x.ID INVENTORY_LISTING_ID,
		  x.COUNTING_NBR,
		  iw.ITEM_ID,
		  x.ROW_ID,
		  x.SHELF_ID,
		  x.VERTICAL_ID,
		  iw.QTY,
		  0 COUNTED_QTY,
		  -iw.QTY DIFFERENCE_QTY 
		FROM ITEM_WAREHOUSE iw
		LEFT JOIN (
	      SELECT 
	          il.ID,
	          il.COUNTING_NBR,
	          il.WAREHOUSE_ID,
    		  ild.ITEM_ID,
    		  ild.ROW_ID,
    		  ild.SHELF_ID,
    		  ild.VERTICAL_ID
	      FROM INVENTORY_LISTING il
			  LEFT JOIN INVENTORY_LISTING_DETAIL ild ON il.ID = ild.INVENTORY_LISTING_ID
	      WHERE 1=1
	          AND il.WAREHOUSE_ID = :warehouseId
	          AND YEAR(il.CREATED_DATE) = YEAR(NOW())
	    ) x ON iw.WAREHOUSE_ID = x.WAREHOUSE_ID AND iw.ITEM_ID = x.ITEM_ID
		WHERE 1=1
		AND iw.WAREHOUSE_ID = :warehouseId
		AND iw.ITEM_ID = :itemId or (:itemId = 0 and x.ITEM_ID IS NULL)			
		""";
	
	@Query(value = qry2, nativeQuery = true)
	List <InventoryListingDetailSum> findAllUnlistedByWarehouseIdAndItemId(int warehouseId, int itemId);
	
		
	// Used in verification difference reports
	String qry3 = """
		SELECT
		  ROW_NUMBER() OVER (ORDER BY x.id) ID,
		  INVENTORY_LISTING_ID,
		  COUNTING_NBR,
		  ITEM_ID,
		  ROW_ID,
		  SHELF_ID,
		  VERTICAL_ID,
		  QTY,
		  COUNTED_QTY,
		  DIFFERENCE_QTY
		FROM (
		  SELECT
		    ild.ID,
		    il.ID INVENTORY_LISTING_ID,
		    il.COUNTING_NBR,
		    ild.ITEM_ID,
		    ild.ROW_ID,
		    ild.SHELF_ID,
		    ild.VERTICAL_ID,
		    IFNULL(iw.QTY, 0) QTY,
		    ild.COUNTED_QTY,
		    ild.COUNTED_QTY  - IFNULL(iw.QTY, 0) DIFFERENCE_QTY,
		    ROW_NUMBER() OVER (PARTITION BY ild.ITEM_ID, ild.ROW_ID, ild.SHELF_ID, ild.VERTICAL_ID ORDER BY ild.ID DESC) RANK_ID
		  FROM INVENTORY_LISTING_DETAIL ild
		  JOIN INVENTORY_LISTING il ON ild.INVENTORY_LISTING_ID = il.ID
		  LEFT JOIN ITEM_WAREHOUSE iw ON iw.WAREHOUSE_ID = il.WAREHOUSE_ID AND iw.ITEM_ID = ild.ITEM_ID
      where 1=1
        and ild.COUNTED_QTY is not null -- iskljucujemo nove naloge za popis
		  ) x
		where x.RANK_ID = 1
		    and 1 = case when 
		         (:differenceIndicator = 0 and DIFFERENCE_QTY < 0) -- minuses
		      OR (:differenceIndicator = 1 and DIFFERENCE_QTY > 0) -- pluses
		      OR (:differenceIndicator = 2 and DIFFERENCE_QTY != 0) -- all differences
		    then 1 else 0 end
		""";	
	@Query(value = qry3, nativeQuery = true)
	List <InventoryListingDetailSum> findAllByDifferenceIndicator(int differenceIndicator);
}
