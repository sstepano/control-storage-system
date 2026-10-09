package org.code_studio.model;

import org.code_studio.database.BillOfReceiptDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillOfReceiptDetailModel extends JpaRepository <BillOfReceiptDetail, Integer> {
	/*List <BillOfReceiptDetailModel> findAllByBillOfReceiptId(Long billOfReceiptId);
	
	/*
	@Query(value="select d from OrdersDetail d where itemId = :itemId order by orderId desc")
	List <OrdersDetail> findAllPreviousOrdersByItemId(Long itemId);
	*/
}
