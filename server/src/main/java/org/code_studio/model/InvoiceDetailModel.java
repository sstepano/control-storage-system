package org.code_studio.model;

import java.util.List;

import org.code_studio.database.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailModel extends JpaRepository <InvoiceDetail, Integer> {
	List <InvoiceDetail> findAllByInvoiceId(Integer invoiceId);

}
