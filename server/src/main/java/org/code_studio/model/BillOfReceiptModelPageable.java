package org.code_studio.model;

import java.util.List;

import org.code_studio.database.BillOfReceipt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BillOfReceiptModelPageable extends PagingAndSortingRepository <BillOfReceipt, Integer> {
	//------------------------------------------------------------------------	
	@Query("select b from BillOfReceipt b order by Id desc")
	List <BillOfReceipt> findAllPageable(Pageable pageable);
	//------------------------------------------------------------------------
	String query2= """
				select b from BillOfReceipt b 
				where 1=1
				  and b.clientId = :clientId
				  and b.billOfReceiptType = :billOfReceiptType
				order by b.id desc
			""";
	@Query(value = query2)
	List <BillOfReceipt>  findAllPageableByClientIdAndBillOfReceiptType(Integer clientId, String billOfReceiptType, Pageable pageable);
}
