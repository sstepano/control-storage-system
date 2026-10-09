package org.code_studio.model;

import java.util.List;

import org.code_studio.database.BankStatementDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankStatementDetailModelPageable extends PagingAndSortingRepository <BankStatementDetail, Integer> {
		
	@Query("from BankStatementDetail")
	List <BankStatementDetail> findAllPageable(Pageable pageable);

	List <BankStatementDetail> findAllByBankStatementIdOrderByDetailOrdinalNumberAsc(Integer accountId, Pageable pageable);

	
	@Query(value="""
            select max(DETAIL_ORDINAL_NUMBER) DETAIL_ORDINAL_NUMBER
            from (
                select max(DETAIL_ORDINAL_NUMBER) + 1 DETAIL_ORDINAL_NUMBER
                from BANK_STATEMENT_DETAIL
                where 1=1
                    and BANK_STATEMENT_ID = :bankStatementId
                UNION SELECT 1 DETAIL_ORDINAL_NUMBER
            ) x
	""", nativeQuery = true)
	List<Integer> findMaxBankStatementDetailNumberByBankStatementId (Integer bankStatementId);
}
