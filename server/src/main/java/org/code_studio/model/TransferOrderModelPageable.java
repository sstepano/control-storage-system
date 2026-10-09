package org.code_studio.model;

import java.util.List;

import org.code_studio.database.TransferOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransferOrderModelPageable extends PagingAndSortingRepository <TransferOrder, Integer> {
	//------------------------------------------------------------------------	
	@Query("select o from TransferOrder o order by o.id desc")
	List <TransferOrder> findAllPageable(Pageable pageable);
	//------------------------------------------------------------------------
	@Query("select p from TransferOrder p where typeCode = :typeCode order by id desc")
	List <TransferOrder> findAllPageableByTypeCode(String typeCode, Pageable pageable);
	//------------------------------------------------------------------------
	@Query(nativeQuery = true, value="select * from Transfer_Order t join Invoice i on t.invoice_id = i.id where t.id = :id")
	List <TransferOrder> findAllPageableById(Integer id, Pageable pageable);
	//------------------------------------------------------------------------
	@Query(nativeQuery = false, value="select t from TransferOrder t join Invoice i on t.invoiceId = i.id where t.statusId = :statusId order by t.id desc")
	List <TransferOrder> findAllPageableByStatusId(Integer statusId, Pageable pageable);
	//------------------------------------------------------------------------

}
