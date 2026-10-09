package org.code_studio.model;

import java.util.List;

import org.code_studio.database.SalePlannerReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SalePlannerReviewModelPageable extends PagingAndSortingRepository <SalePlannerReview, Integer> {
	
	String qry1 = """
			-- ------------------------------
			-- MONTHLY REVIEW OF SALE_OFFICER
			-- ------------------------------
			WITH RECURSIVE date_ranges AS 
			(
			  SELECT CONCAT_WS('-', :year, :month, '01') dt
			  UNION ALL
			  SELECT dt + INTERVAL 1 DAY
			  FROM date_ranges d
			  WHERE 1=1
			    AND dt + INTERVAL 1 DAY <= CAST(CONCAT_WS('-', :year, :month, '31') as date)
			)
			SELECT 0 id,
			    dt.dt date
			  , day(dt.dt) day
			  , case 
			    when DAYOFWEEK(dt) = 1 then 1 else 0 
			    end is_sunday
			  , sp.is_came
			  , sp.is_went_to
			  , sp.is_called
			  , sp.is_called_to
			  , sp.delivered_amt
			  , sp.charged_amt
			  , sp.promised_amt
			  , sp.type_id
			FROM date_ranges dt
			left join sale_planner sp on dt.dt = cast(sp.event_date as date)
			  AND sp.SALE_OFFICER_ID = :saleOfficerId
			where 1=1
			  and :month = :month
			  and :year = :year
			order by dt.dt
	""";
	@Query(value=qry1, nativeQuery=true)
	List<SalePlannerReview> findBySaleOfficerIdAndDateRange(Integer saleOfficerId, String month, String year, Pageable pageable);
}
