package org.code_studio.model;

import java.util.List;

import org.code_studio.database.TransferOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransferOrderDetailModel extends JpaRepository <TransferOrderDetail, Integer> {

	List<TransferOrderDetail> findAllByTransferOrderIdOrderByWarehouseIdOrigin(Integer transferOrderId);
	List<TransferOrderDetail> findByInvoiceDetailId(Integer invoiceDetailId);
	
	String qry1 = """
			select tod.*
			FROM transfer_order t
			JOIN transfer_order_detail tod ON t.ID = tod.TRANSFER_ORDER_ID
			WHERE 1=1
				AND t.ID = :id
				AND tod.QTY != tod.ISSUED_QTY;

		""";
	@Query(value = qry1, nativeQuery = true)
	List<TransferOrderDetail> findDifferencesByTransferOrderId(Integer id);
	
}
