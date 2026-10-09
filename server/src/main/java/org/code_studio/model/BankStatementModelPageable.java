package org.code_studio.model;

import java.util.List;

import org.code_studio.database.BankStatement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankStatementModelPageable extends PagingAndSortingRepository <BankStatement, Integer> {
		
	@Query("from BankStatement")
	List <BankStatement> findAllPageable(Pageable pageable);

	List <BankStatement> findAllByAccountIdOrderByBankStatementDateDescBankStatementNumberDesc(Integer accountId, Pageable pageable);
	
	@Query(value="""
			select max(BANK_STATEMENT_NUMBER) BANK_STATEMENT_NUMBER
			from (
				select max(BANK_STATEMENT_NUMBER) + 1 BANK_STATEMENT_NUMBER
				from BANK_STATEMENT
				where 1=1
					and ACCOUNT_ID = :accountId
					and year(cast(BANK_STATEMENT_DATE as date)) = :year
				UNION SELECT 1 BANK_STATEMENT_NUMBER
			) x
	""", nativeQuery = true)
	List<Integer> findMaxBankStatementNumberByBankStatementDate (Integer accountId, Integer year);

}
