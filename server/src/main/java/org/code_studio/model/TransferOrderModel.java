package org.code_studio.model;

import java.util.List;

import org.code_studio.database.TransferOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransferOrderModel extends JpaRepository <TransferOrder, Integer> {
	
	String qry1 = """
				select 
				    max(transfer_order_number) number
				from TRANSFER_ORDER
				where year(transfer_order_date) = YEAR(CURRENT_DATE())
				group by year(transfer_order_date)
				order by max(transfer_order_number), year(transfer_order_date)
			""";
	
	@Query(value = qry1, nativeQuery = true)
	List<Integer> findMaxCurrentNumber();
	
	List<TransferOrder> findByInvoiceId(Integer invoiceId);
}
