package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrdersModelPageable extends PagingAndSortingRepository <Orders, Integer> {
	//------------------------------------------------------------------------	
	@Query("select o from Orders o")
	List <Orders> findAllPageable(Pageable pageable);
	//------------------------------------------------------------------------
	@Query("select o from Orders o where clientId = :clientId order by id desc")
	List <Orders> findAllPageableByClientId(Integer clientId, Pageable pageable);
	//------------------------------------------------------------------------
	@Query("select o from Orders o where clientId = :clientId and validationDate is null order by id desc")
	List <Orders> findAllPageableNonValidatedByClientId(Integer clientId, Pageable pageable);
	//------------------------------------------------------------------------
	@Query("select o from Orders o where clientId = :clientId and validationDate is not null order by id desc")
	List <Orders> findAllPageableValidatedByClientId(Integer clientId, Pageable pageable);
	//------------------------------------------------------------------------	
}
