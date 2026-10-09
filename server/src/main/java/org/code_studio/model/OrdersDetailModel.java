package org.code_studio.model;

import java.util.List;

import org.code_studio.database.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrdersDetailModel extends JpaRepository <OrdersDetail, Integer> {
	List <OrdersDetail> findAllByOrderId(Integer orderId);
	
	@Query(value="select d from OrdersDetail d where itemId = :itemId order by orderId desc")
	List <OrdersDetail> findAllPreviousOrdersByItemId(Integer itemId);
	
	@Query(value="""
		SELECT SUM(d.deliveredQty) qty
		FROM OrdersDetail d
		WHERE 1=1
		  AND d.itemId = :itemId
	""")
	List <Integer> findSumQtyByItemId(Integer itemId);
}
