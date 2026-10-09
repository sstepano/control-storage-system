package org.code_studio.model;

import java.util.List;

import org.code_studio.database.SalePlannerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalePlannerReviewModel extends JpaRepository <SalePlannerReview, Integer> {

	String qry1 = """
				-- ------------------------------
				-- MONTHLY REVIEW OF SALE_OFFICER AND CLIENT ID
				-- ------------------------------
				WITH RECURSIVE date_ranges AS 
				(
				  SELECT CONCAT_WS('-', :year, :month, 
		            CASE :part
		              WHEN 1 THEN '01'
		              WHEN 2 THEN '11'
		              WHEN 3 THEN '21'
		            END) dt
				  UNION ALL
				  SELECT dt + INTERVAL 1 DAY
				  FROM date_ranges d
				  WHERE 1=1
				    AND dt + INTERVAL 1 DAY <= CAST(CONCAT_WS('-', :year, :month, 
		              CASE :part
			            WHEN 1 THEN '10'
			            WHEN 2 THEN '20'
			            WHEN 3 THEN '31'
		              END) as date)
				)
				SELECT 0 id,
				    dt.dt date
				  , day(dt.dt) day
				  , c.name client_name
				  , c.city client_city
				  , cg.name client_group
				  , day(sp.EVENT_DATE) day_number
				  , case when DAYOFWEEK(dt) = 1 then 1 else 0 end is_sunday
				  , sp.is_came
				  , sp.is_went_to
				  , sp.is_called
				  , sp.is_called_to
				  -- , sp.delivered_amt
				  -- , sp.charged_amt
                  , ifnull(inv.amt, 0.00) delivered_amt
                  , ifnull(bs.amt, 0.00) charged_amt
				  , sp.promised_amt
				  , spt.name type_name
				  , null sale_officer_name
				  , null contacted_by_name
				  , null description
				FROM date_ranges dt
				left join sale_planner sp on dt.dt = cast(sp.event_date as date)
				  AND sp.SALE_OFFICER_ID = :saleOfficerId
				  AND sp.CLIENT_ID = :clientId
        left join sale_planner_type spt on sp.type_id = spt.id
        left join client c on sp.client_id = c.id
        left join client_group cg on c.group_id = cg.id
        left join (
          select  
              sum(total_amount) amt
            , invoice_date
          from invoice 
          where client_id = :clientId
          group by invoice_date 
        ) inv on dt.dt = inv.invoice_date
        left join (
           select
               SUM(AMOUNT) AMT
             , value_date
           FROM bank_statement_detail
          where client_id = :clientId
          group by value_date 
        ) bs on dt.dt = bs.value_date
				where 1=1
				  and :month = :month
				  and :year = :year
				order by dt.dt
		""";
	@Query(value=qry1, nativeQuery=true)
	List<SalePlannerReview> findBySaleOfficerIdAndClientIdAndDateRange(Integer saleOfficerId, Integer clientId, String month, String year, Integer part);

	// used for report. Moram da koristim positional params npr ?1, ?2 i ?3 umesto named params kao sto su :saleOfficerName, :startDate i :endDate
	// posto Spring boot ima problema da pronadje named param u kompleksnom subquery-ju kao sto je ovaj. Svejedno je da li koristim WITH ili subquery.
	String qry2 = """
with sums as (
	SELECT
	    c.ID
	  , c.NAME
	  , c.CITY
	  , IFNULL(inv.AMT, 0.00) last_paid_amt
	  , IFNULL(stm.AMT, 0.00) last_delivered_amt
	  , cg.NAME client_group_name
	FROM client c
	JOIN client_group cg ON c.GROUP_ID = cg.ID
	JOIN (
	  SELECT
	      1 TYPE_ID
	    , i.CLIENT_ID
	    , i.AMOUNT_GROSS AMT
           , MAX(i.INVOICE_DATE) last_date
	  FROM invoice i
         GROUP BY i.CLIENT_ID
        ) inv on c.id = inv.client_id
	  JOIN (
    SELECT
      2 TYPE_ID,
      bs.CLIENT_ID,
      bs.AMOUNT AMT,
      MAX(bs.VALUE_DATE) last_date
    FROM bank_statement_detail bs
    GROUP BY bs.CLIENT_ID
    ) stm ON c.ID = stm.CLIENT_ID
)
SELECT 0 id,
  sp.EVENT_DATE date,
  day(sp.EVENT_DATE) day,
  x.name client_name,
  c.city client_city,
  cg.name client_group,
  day(sp.EVENT_DATE) day_number,
  CASE WHEN DAYOFWEEK(sp.EVENT_DATE) = 1 THEN 1 ELSE 0 END is_sunday,
  sp.IS_CAME,
  sp.IS_WENT_TO,
  sp.IS_CALLED,
  sp.IS_CALLED_TO,
  x.last_delivered_amt DELIVERED_AMT,
  x.last_paid_amt CHARGED_AMT,
  sp.PROMISED_AMT,
  spt.name type_name,
  so.name sale_officer_name,
  soc.name contacted_by_name,
  sp.description
FROM sale_planner sp
  LEFT JOIN sale_planner_type spt
    ON sp.TYPE_ID = spt.id
  LEFT JOIN application_user so
    ON sp.SALE_OFFICER_ID = so.id
  LEFT JOIN application_user soc
    ON sp.CONTACTED_BY_ID = soc.id
  LEFT JOIN sums x
    ON sp.CLIENT_ID = x.id
  left join client c on sp.client_id = c.id
  left join client_group cg on c.group_id = cg.id
WHERE sp.EVENT_DATE BETWEEN CONCAT(?1, ' 00:00:00') AND CONCAT(?2, ' 23:59:59')
  AND 1 = CASE WHEN sp.SALE_OFFICER_ID = ?3 OR ?3 = 0 THEN 1 ELSE 0 END
ORDER BY sp.EVENT_DATE;
	""";
	@Query(value=qry2, nativeQuery=true)
	List<SalePlannerReview> findByDateRange(String startDate, String endDate, Integer saleOfficerId);
	
}
