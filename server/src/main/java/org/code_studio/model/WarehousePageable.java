package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Warehouse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WarehousePageable extends PagingAndSortingRepository <Warehouse, Integer> {
		
	@Query("select w from Warehouse w")
	List <Warehouse> findAllPageable(Pageable pageable);

	@Query("select w from Warehouse w order by warehouseNumber")
	List <Warehouse> findAllPageableByOrderByWarehouseNumber(Pageable pageable);
	
	@Query("""
		select w 
		from Warehouse w
		where 1=1
		  and w.isCommission = :isCommission 
		order by warehouseNumber
	""")
	List <Warehouse> findAllPageableByIsCommission(Pageable pageable, Boolean isCommission);
	
	@Query("""
			select w 
			from Warehouse w
			where 1=1
			  and w.isActive = :isActive 
			order by warehouseNumber
	""")
	List <Warehouse> findAllPageableByIsActive(Pageable pageable, Boolean isActive);

}

