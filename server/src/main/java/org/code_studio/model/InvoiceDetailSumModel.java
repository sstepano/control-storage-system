package org.code_studio.model;

import java.util.List;

import org.code_studio.database.InvoiceDetailSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceDetailSumModel extends JpaRepository <InvoiceDetailSum, Integer> {
	
	String qry1 = """
			select
			    0 id
			  , COUNT(i.amount_net) cnt
			  , SUM(i.amount_net) sum_net_amt
			from invoice_detail i 
			where i.invoice_id = :invoiceId
			""";
	@Query(value = qry1, nativeQuery = true)
	List<InvoiceDetailSum> sumByInvoiceId(Integer invoiceId);
}
