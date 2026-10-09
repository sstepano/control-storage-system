package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientBankAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientBankAccountModelPageable extends PagingAndSortingRepository <ClientBankAccount, Integer> {
		
	@Query("from ClientBankAccount")
	List <ClientBankAccount> findAllPageable(Pageable pageable);
	List <ClientBankAccount> findAllByClientId(Integer clientId, Pageable pageable);
	List <ClientBankAccount> findAllByAccountNumberContaining(String accountNumber, Pageable pageable);

}
