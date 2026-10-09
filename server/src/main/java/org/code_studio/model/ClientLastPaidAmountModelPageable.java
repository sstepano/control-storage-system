package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientLastPaidAmount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientLastPaidAmountModelPageable extends PagingAndSortingRepository <ClientLastPaidAmount, Integer> {

	String qry1 = """
					SELECT DISTINCT
					    c.ID
					  , c.NAME
					  , c.CITY
					  , inv.AMT last_paid_amt
					  , stm.AMT last_delivered_amt
					  , cg.NAME client_group_name
					FROM client c
					JOIN client_sale_officer_link csol ON c.ID = csol.CLIENT_ID
					JOIN client_group cg ON c.GROUP_ID = cg.ID
					join (
					  SELECT
					      1 TYPE_ID
					    , MAX(i.ID) max_id
					    , i.CLIENT_ID
					    , i.AMOUNT_GROSS AMT
					  FROM invoice i
					  where 1=1
					  GROUP by i.CLIENT_ID
					) inv on c.id = inv.client_id
					JOIN (
					  SELECT
					      2 TYPE_ID
					    , MAX(bs.ID) max_id
					    , bs.CLIENT_ID
					    , bs.AMOUNT AMT
					  FROM bank_statement_detail bs
					  where 1=1
					  GROUP by bs.CLIENT_ID
					) stm ON c.ID = stm.CLIENT_ID
					JOIN sale_planner sp ON sp.CLIENT_ID = c.ID AND sp.SALE_OFFICER_ID = csol.SALE_OFFICER_ID
					WHERE 1=1
					  AND csol.SALE_OFFICER_ID = :saleOfficerId
					  AND sp.EVENT_DATE BETWEEN CONCAT_WS('-', :year, :month, '01') AND CONCAT_WS('-', :year, :month, '31')
					ORDER BY c.NAME
			""";
	@Query(value=qry1, nativeQuery=true)
	List<ClientLastPaidAmount> findAllBySaleOfficerIdAndDateRange(Integer saleOfficerId, String month, String year, Pageable pageable);

}
