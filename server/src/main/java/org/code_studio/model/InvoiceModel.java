package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceModel extends JpaRepository <Invoice, Integer> {
	List <Invoice> findAllByClientId(Integer clientId);
	
	String qry1 = """
			    select IFNULL(max(invoice_number), 0) + 1
			    from invoice i
			    where 1=1
			    	and i.client_id = :clientId
			    	and YEAR(i.invoice_date) = YEAR(CURRENT_DATE());
			""";
	@Query(value = qry1, nativeQuery = true)
	List<Integer> maxInvoiceNumberByClientId(Integer clientId);
}
