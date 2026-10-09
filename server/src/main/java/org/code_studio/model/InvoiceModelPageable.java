package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Invoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceModelPageable extends PagingAndSortingRepository <Invoice, Integer> {
		
	@Query("select c from Invoice c")
	List <Invoice> findAllPageable(Pageable pageable);

	/*final String qry1 = """
			select i from Invoice i 
			join fetch InvoiceDetail d  on i.id = d.invoiceId
			where i.clientId = :clientId 
			order by i.id DESC
			""";
	@Query(value = qry1) */
	List <Invoice> findAllByClientIdOrderByIdDesc(Integer clientId, Pageable pageable);
	List <Invoice> findAllByInvoiceTypeAndClientIdOrderByIdDesc(String invoiceType, Integer clientId, Pageable pageable);

}
