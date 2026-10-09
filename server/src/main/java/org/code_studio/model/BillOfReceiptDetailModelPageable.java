package org.code_studio.model;

import java.util.List;

import org.code_studio.database.BillOfReceiptDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BillOfReceiptDetailModelPageable extends PagingAndSortingRepository <BillOfReceiptDetail, Integer> {
	//------------------------------------------------------------------------	
	@Query("select d from BillOfReceiptDetail d")
	List <BillOfReceiptDetail> findAllPageable(Pageable pageable);
	//------------------------------------------------------------------------
	@Query("select d from BillOfReceiptDetail d where billOfReceiptId = :billOfReceiptId")
	List <BillOfReceiptDetail> findAllPageableByBillOfReceiptId(Integer billOfReceiptId, Pageable pageable);
	//------------------------------------------------------------------------
}
