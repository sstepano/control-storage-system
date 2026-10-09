package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface WarehouseModel extends JpaRepository <Warehouse, Integer> {
	List<Warehouse> findAllByIsActive(Boolean isActive);
	
	
	// Daj sve regularne ILI komisione magacine, u zavisnosti od commission param-a
	@Query(value="""
		select *
		from Warehouse w
		where 1=1
		  and is_active = 1
		  and is_commission =
		    case 
		      when :commission = true then 1 
		      else 0 
		    end
 
	""", nativeQuery = true)
	List<Warehouse> findAllActiveCommissionOrRegular(Boolean commission);

	// Daj sve regularne ILI komisione magacine, u zavisnosti od commission param-a
	@Query(value="""
		with cte as (
		  select 
		      w.*
		    , :isActiveIndex isActiveIndex 
		    , :isCommissionIndex isCommissionIndex
		  from warehouse w
		)
		select
		  ID,
		  WAREHOUSE_NUMBER,
		  NAME,
		  IS_RETAIL,
		  RETAIL_SERIAL_NO,
		  IS_WHOLESALE,
		  WHOLESALE_SERIAL_NO,
		  IS_DISCOUNT_RETAIL,
		  DISCOUNT_RETAIL_SERIAL_NO,
		  IS_DISCOUNT_WHOLESALE,
		  DISCOUNT_WHOLESALE_SERIAL_NO,
		  IS_COMMISSION,
		  COMMISSION_SERIAL_NO,
		  IS_OFFERRED,
		  IS_INTERNET,
		  IS_ACTIVE
		from cte
		where 1=1
		and (
		  (isActiveIndex = 0 and (cte.is_active = 0 OR cte.is_active = 1)) OR -- svi
		  (isActiveIndex = 1 and cte.is_active = 1) OR -- active
		  (isActiveIndex = 2 and cte.is_active = 0)    -- inactive
		)
		and (
		  (isCommissionIndex = 0 and (cte.is_commission = 0 OR cte.is_commission = 1)) OR -- svi
		  (isCommissionIndex = 1 and cte.is_commission = 0) OR -- regular
		  (isCommissionIndex = 2 and cte.is_commission = 1)    -- commission
		)
	""", nativeQuery = true)
	List<Warehouse> findAllByIsActiveAndIsCommission(Integer isActiveIndex, Integer isCommissionIndex);

	@Query(value="""
		SELECT 
		  IFNULL(MAX('TRUE'), 'FALSE') hasItems
		FROM ITEM_WAREHOUSE iw
		where 1=1
		  AND iw.WAREHOUSE_ID = :warehouseId
		  AND QTY > 0
	""", nativeQuery = true)
	Boolean hasItemsInWarehouse(Integer warehouseId);
	
}
