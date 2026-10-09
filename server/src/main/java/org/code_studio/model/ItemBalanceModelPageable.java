package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemBalance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemBalanceModelPageable extends JpaRepository <ItemBalance, Integer> {
	
	String qry1 = """
				select
				   o.ID
				 , d.ITEM_ID
				 , o.TYPE_CODE
				 , o.TRANSFER_ORDER_DATE
				 , case when d.WAREHOUSE_ID_DESTINATION = :warehouseId then d.QTY else 0 end IN_QTY
				 , case when d.WAREHOUSE_ID_ORIGIN      = :warehouseId then d.QTY else 0 end OUT_QTY
				 , d.WAREHOUSE_ID_ORIGIN
				 , d.WAREHOUSE_ID_DESTINATION
				from TRANSFER_ORDER o
				join TRANSFER_ORDER_DETAIL d on o.ID = d.TRANSFER_ORDER_ID
				where 1=1
				    and d.ITEM_ID = :itemId
				    and (d.WAREHOUSE_ID_DESTINATION = :warehouseId or d.WAREHOUSE_ID_ORIGIN = :warehouseId)
				    and d.WAREHOUSE_ID_DESTINATION != 0 -- ne prikazuje se nulti magacin
				    and YEAR(o.TRANSFER_ORDER_DATE) = YEAR(CURRENT_DATE()) -- cela kartica je bez ovog uslova
				order by TRANSFER_ORDER_DATE DESC -- ako koristimo ID, nece da koristi index
			""";
	
	@Query(value=qry1, nativeQuery=true)
	List <ItemBalance> allPageableItemBalanceForCurrentYearByWarehouseIdAndItemId (Integer warehouseId, Integer itemId, Pageable pageable);
	
	String qry2 = """
				select
				   o.ID
				 , d.ITEM_ID
				 , o.TYPE_CODE
				 , o.TRANSFER_ORDER_DATE
				 , case when d.WAREHOUSE_ID_DESTINATION = :warehouseId then d.QTY else 0 end IN_QTY
				 , case when d.WAREHOUSE_ID_ORIGIN      = :warehouseId then d.QTY else 0 end OUT_QTY
				 , d.WAREHOUSE_ID_ORIGIN
				 , d.WAREHOUSE_ID_DESTINATION
				from TRANSFER_ORDER o
				join TRANSFER_ORDER_DETAIL d on o.ID = d.TRANSFER_ORDER_ID
				where 1=1
				    and d.ITEM_ID = :itemId
				    and (d.WAREHOUSE_ID_DESTINATION = :warehouseId or d.WAREHOUSE_ID_ORIGIN = :warehouseId)
				    and d.WAREHOUSE_ID_DESTINATION != 0 -- ne prikazuje se nulti magacin
				    -- and YEAR(o.TRANSFER_ORDER_DATE) = YEAR(CURRENT_DATE()) -- cela kartica je bez ovog uslova
				order by TRANSFER_ORDER_DATE DESC -- ako koristimo ID, nece da koristi index
			""";
	
	@Query(value=qry2, nativeQuery=true)
	List <ItemBalance> allPageableItemBalanceByWarehouseIdAndItemId (Integer warehouseId, Integer itemId, Pageable pageable);
	
	String qry3 = """
				select
				    0 ID
				  , NULL ITEM_ID
				  , NULL TYPE_CODE
				  , NULL TRANSFER_ORDER_DATE
				  , sum(case when d.WAREHOUSE_ID_DESTINATION = :warehouseId then d.QTY else 0 end) IN_QTY
				  , sum(case when d.WAREHOUSE_ID_ORIGIN      = :warehouseId then d.QTY else 0 end) OUT_QTY
				  , NULL WAREHOUSE_ID_ORIGIN
				  , NULL WAREHOUSE_ID_DESTINATION
				from TRANSFER_ORDER o
				join TRANSFER_ORDER_DETAIL d on o.ID = d.TRANSFER_ORDER_ID
				where 1=1
				    and d.ITEM_ID = :itemId
				    and (d.WAREHOUSE_ID_DESTINATION = :warehouseId or d.WAREHOUSE_ID_ORIGIN = :warehouseId)
				    and d.WAREHOUSE_ID_DESTINATION != 0
				    and YEAR(o.TRANSFER_ORDER_DATE) = YEAR(CURRENT_DATE())
				group by ID
			""";
	
	@Query(value=qry3, nativeQuery=true)
	List <ItemBalance> allPageableTotalItemBalanceForCurrentYearByWarehouseIdAndItemId (Integer warehouseId, Integer itemId, Pageable pageable);
	
	String qry4 = """
			select
			    0 ID
			  , NULL ITEM_ID
			  , NULL TYPE_CODE
			  , NULL TRANSFER_ORDER_DATE
			  , sum(case when d.WAREHOUSE_ID_DESTINATION = :warehouseId then d.QTY else 0 end) IN_QTY
			  , sum(case when d.WAREHOUSE_ID_ORIGIN      = :warehouseId then d.QTY else 0 end) OUT_QTY
			  , NULL WAREHOUSE_ID_ORIGIN
			  , NULL WAREHOUSE_ID_DESTINATION
			from TRANSFER_ORDER o
			join TRANSFER_ORDER_DETAIL d on o.ID = d.TRANSFER_ORDER_ID
			where 1=1
			    and d.ITEM_ID = :itemId
			    and (d.WAREHOUSE_ID_DESTINATION = :warehouseId or d.WAREHOUSE_ID_ORIGIN = :warehouseId)
			    and d.WAREHOUSE_ID_DESTINATION != 0
			group by ID
		""";

@Query(value=qry4, nativeQuery=true)
List <ItemBalance> allPageableTotalItemBalanceByWarehouseIdAndItemId (Integer warehouseId, Integer itemId, Pageable pageable);	
}
