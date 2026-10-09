package org.code_studio.model;

import java.time.LocalDate;
import java.util.List;

import org.code_studio.database.ClientAccountBalanceCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientAccountBalanceCardModel extends JpaRepository <ClientAccountBalanceCard, Integer> {
	
	String qry0 = """
            SELECT
                0 ID, NULL CLIENT_ID, NULL VALUE_DATE, NULL BOOKING_DATE, NULL BANK_STATEMENT_ID, NULL INVOICE_ID,
                IFNULL(SUM(DEBIT_AMT), 0)  DEBIT_AMT
              , IFNULL(SUM(CREDIT_AMT), 0) CREDIT_AMT
              , IFNULL(SUM(CREDIT_AMT), 0) - IFNULL(SUM(DEBIT_AMT), 0) BALANCE_AMT
            FROM (
                SELECT
                   ID,
                   CLIENT_ID, 
                   VALUE_DATE, 
                   BOOKING_DATE,
                   BANK_STATEMENT_ID,
                   INVOICE_ID,
                   case when DEBIT_CREDIT = -1 then amount else null end DEBIT_AMT,
                   case when DEBIT_CREDIT = 1 then amount else null end CREDIT_AMT,
                   SUM(AMOUNT * DEBIT_CREDIT) OVER (ORDER BY VALUE_DATE, ID, AMOUNT * DEBIT_CREDIT) BALANCE_AMT
                FROM CLIENT_ACCOUNT_BALANCE
                WHERE CLIENT_ID = :clientId
                order by VALUE_DATE ASC, ID ASC
            ) x 
            WHERE 1=1
                AND VALUE_DATE between :valueDateFrom and :valueDateTo
		""";
	@Query(value = qry0, nativeQuery = true)
	List <ClientAccountBalanceCard> findDebitCreditByClientIdAndValueDate(Integer clientId, LocalDate valueDateFrom, LocalDate valueDateTo);

	
	String qry1 = """
            SELECT
               0 ID,
               NULL CLIENT_ID, 
               NULL VALUE_DATE, 
               NULL BOOKING_DATE,
               NULL BANK_STATEMENT_ID,
               NULL INVOICE_ID,
               NULL DEBIT_AMT,
               NULL CREDIT_AMT,
               sum(case when DEBIT_CREDIT = 1 then amount else null end) - sum(case when DEBIT_CREDIT = -1 then amount else null end) BALANCE_AMT
            FROM CLIENT_ACCOUNT_BALANCE
            WHERE 1=1
                and CLIENT_ID = :clientId
                AND VALUE_DATE <= :valueDateTo
		""";
	@Query(value = qry1, nativeQuery = true)
	List <ClientAccountBalanceCard> findTotalBalanceByClientIdAndValueDate(Integer clientId, LocalDate valueDateTo);
	
	String qry2 = """
            SELECT
                ID, CLIENT_ID, VALUE_DATE, BOOKING_DATE, BANK_STATEMENT_ID, INVOICE_ID, DEBIT_AMT, CREDIT_AMT, BALANCE_AMT
            FROM (
                SELECT
                   ID,
                   CLIENT_ID, 
                   VALUE_DATE, 
                   BOOKING_DATE,
                   BANK_STATEMENT_ID,
                   INVOICE_ID,
                   case when DEBIT_CREDIT = -1 then amount else null end DEBIT_AMT,
                   case when DEBIT_CREDIT = 1 then amount else null end CREDIT_AMT,
                   SUM(AMOUNT * DEBIT_CREDIT) OVER (ORDER BY VALUE_DATE, ID, AMOUNT * DEBIT_CREDIT) BALANCE_AMT
                FROM CLIENT_ACCOUNT_BALANCE
                WHERE CLIENT_ID = :clientId
                order by VALUE_DATE ASC, ID ASC
            ) x 
            WHERE 1=1
                AND VALUE_DATE between :valueDateFrom and :valueDateTo
		""";
	@Query(value = qry2, nativeQuery = true)
	List <ClientAccountBalanceCard> findAccountBalanceByClientIdAndValueDate(Integer clientId, LocalDate valueDateFrom, LocalDate valueDateTo);
	
}
