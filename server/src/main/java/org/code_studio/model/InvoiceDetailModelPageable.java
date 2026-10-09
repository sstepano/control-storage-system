package org.code_studio.model;

import java.util.List;

import org.code_studio.database.InvoiceDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceDetailModelPageable extends PagingAndSortingRepository <InvoiceDetail, Integer> {
		
	@Query("select c from InvoiceDetail c")
	List <InvoiceDetail> findAllPageable(Pageable pageable);

	List <InvoiceDetail> findAllByInvoiceId(Integer invoiceId, Pageable pageable);

}
