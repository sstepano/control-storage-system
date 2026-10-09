package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientAccountBalanceCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Deprecated
public interface ClientAccountBalanceCardModelPageable extends PagingAndSortingRepository <ClientAccountBalanceCard, Integer> {
	//------------------------------------------------------------------------
	String qry1 = """
				SELECT
				   --RANDOM_UUID() AS UUID,
				   ID,
				   CLIENT_ID, 
				   VALUE_DATE, 
				   BOOKING_DATE,
			       BANK_STATEMENT_ID,
			       INVOICE_ID,
				   AMOUNT * DEBIT_CREDIT AMOUNT,
			       NULL PAYMENT,
				   SUM(AMOUNT * DEBIT_CREDIT) OVER (ORDER BY VALUE_DATE, AMOUNT * DEBIT_CREDIT) SALDO
				FROM CLIENT_ACCOUNT_BALANCE
				WHERE CLIENT_ID = :clientId
				order by VALUE_DATE DESC, ID DESC
			""";
	@Query(value = qry1, nativeQuery = true)
	List <ClientAccountBalanceCard> findAccountBalanceByClientId(Integer clientId, Pageable pageable);
	//------------------------------------------------------------------------
}
