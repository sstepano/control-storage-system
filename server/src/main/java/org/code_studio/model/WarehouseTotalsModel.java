package org.code_studio.model;

import java.util.List;

import org.code_studio.database.WarehouseTotals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WarehouseTotalsModel extends JpaRepository <WarehouseTotals, Integer> {
	
	/**
	 * Gets state by row, summarized both left and right positions into one row value
	 */
	@Query(value = """
			WITH CTE as (                
			  SELECT 1 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 2 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 3 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 4 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 5 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 6 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 7 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 8 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT 
			),
			CTE2 as (
			  SELECT 
			      p.ROW_ID
			    , p.SHELF_ID
			    , p.VERTICAL_ID
			    , COUNT(*) occupied_cnt
			    , 189 - COUNT(*) FREE_CNT
			    , CAST(COUNT(*) / 189  * 100 as INTEGER) occupied_pct
			    , 100 - CAST(COUNT(*) / 189  * 100 as INTEGER) free_pct
			  FROM palette_2 p
			  WHERE 1=1
			    AND p.STATUS_ID = 1 -- u magacinu                                                
			  GROUP BY p.ROW_ID, p.STATUS_ID
			  ORDER by p.ROW_ID
			)
			SELECT 
			    c.ROW_ID ID
			  , IFNULL(c2.OCCUPIED_CNT, 0) OCCUPIED_CNT
			  , IFNULL(c2.FREE_CNT, 189) FREE_CNT
			  , IFNULL(c2.OCCUPIED_PCT, 0) OCCUPIED_PCT
			  , IFNULL(c2.FREE_PCT, 100) FREE_PCT
			 from CTE c
			left join CTE2 c2 on left(c.row_id, 1) = c2.ROW_ID and right(c.ROW_ID, 1) % 2 = c2.shelf_id % 2
			ORDER BY c.ROW_ID
	""", nativeQuery = true)
	List <WarehouseTotals> findAll();
	
	
	/**
	 * Gets all rows by left and right positions in the row.
	 * Initially required by B. but we do not yet use it.
	 * @return
	 */
	@Query(value = """
			WITH CTE as (                
			  SELECT 11 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 12 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 21 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 22 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 31 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 32 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 41 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 42 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 51 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 52 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 61 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 62 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 71 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 72 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT UNION
			  SELECT 81 ROW_ID, 0 OCCUPIED_CNT, 0 FREE_CNT, 0 OCCUPIED_PCT, 0 FREE_PCT 
			),
			CTE2 as (
			  SELECT 
			      p.ROW_ID
			    , p.SHELF_ID
			    , p.VERTICAL_ID
			    , COUNT(*) occupied_cnt
			    , 189 - COUNT(*) FREE_CNT
			    , CAST(COUNT(*) / 189  * 100 as INTEGER) occupied_pct
			    , 100 - CAST(COUNT(*) / 189  * 100 as INTEGER) free_pct
			  FROM palette_2 p
			  WHERE 1=1
			    AND p.STATUS_ID = 1 -- u magacinu                                                
			  GROUP BY p.ROW_ID, p.STATUS_ID
			  ORDER by p.ROW_ID
			)
			SELECT 
			    c.ROW_ID ID
			  , IFNULL(c2.OCCUPIED_CNT, 0) OCCUPIED_CNT
			  , IFNULL(c2.FREE_CNT, 189) FREE_CNT
			  , IFNULL(c2.OCCUPIED_PCT, 0) OCCUPIED_PCT
			  , IFNULL(c2.FREE_PCT, 100) FREE_PCT
			 from CTE c
			left join CTE2 c2 on left(c.row_id, 1) = c2.ROW_ID and right(c.ROW_ID, 1) % 2 = c2.shelf_id % 2
			ORDER BY c.ROW_ID
	""", nativeQuery = true)
	List <WarehouseTotals> findAllDividedByPosition();
	
	
}
