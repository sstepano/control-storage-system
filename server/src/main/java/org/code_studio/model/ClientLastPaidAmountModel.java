package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientLastPaidAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientLastPaidAmountModel extends JpaRepository <ClientLastPaidAmount, Integer> {

	String qry1 = """
					SELECT
					    c.ID
					  , c.NAME
					  , c.CITY
					  , inv.AMT last_paid_amt
					  , stm.AMT last_delivered_amt
					  , cg.NAME client_group_name
					FROM client c
					JOIN client_group cg ON c.GROUP_ID = cg.ID
					join (
					  SELECT
					      1 TYPE_ID
					    , i.CLIENT_ID
					    , i.AMOUNT_GROSS AMT
					  FROM invoice i
					  where 1=1
					    and client_id = :clientId
					  ORDER by 1 DESC LIMIT 1
					) inv on c.id = inv.client_id
					JOIN (
					SELECT
					    2 TYPE_ID
					  , bs.CLIENT_ID
					  , bs.AMOUNT AMT
					FROM bank_statement_detail bs
					where 1=1
					  and client_id = :clientId
					ORDER by 1 DESC LIMIT 1
					) stm ON c.ID = stm.CLIENT_ID

		""";
	@Query(value=qry1, nativeQuery=true)
	List<ClientLastPaidAmount> findByClientId(Integer clientId);
	
}
