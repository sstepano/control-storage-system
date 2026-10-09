package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientPricelistTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientPricelistTotalModel extends JpaRepository <ClientPricelistTotal, Integer> {

	/**
	 * Ovde za cenovnik po kupcu UVEK uzimamo cenovnik koji je napravljen za PARENTA
	 * ako je parent != id u klijentu to znaci da ima parenta, ako je isti ili je null, onda nema
	 * */
	String qry1 = """
                select
                    0 as id
                  , client_Id
                  , sum(case when is_Active = 1 then 1 else 0 end) as active_Cnt
                  , sum(case when is_Active = 1 then measure_Unit_Gross_Amt else 0 end) as active_Amt
                  , sum(case when is_Active = 0 then 1 else 0 end) as inactive_Cnt
                  , sum(case when is_Active = 0 then measure_Unit_Gross_Amt else 0 end) as inactive_Amt
                  , count(*) total_cnt
                  , sum(measure_Unit_Gross_Amt) total_amt  
                from Client_Pricelist cp
                inner join Client c 
                    on cp.client_Id = 
                        case when c.Id != c.parent_id then c.parent_id
                        else c.id 
                    end
                where 1=1
                    and c.id = :clientId
                group by client_Id
		""";
	@Query(value=qry1, nativeQuery=true)
	List<ClientPricelistTotal> findTotalByClientId(Integer clientId);
	
}
