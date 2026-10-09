package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientPricelist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientPricelistModelPageable extends PagingAndSortingRepository <ClientPricelist, Integer> {

	/**
	 * Ovde za cenovnik po kupcu UVEK uzimamo cenovnik koji je napravljen za PARENTA
	 * ako je parent != id u klijentu to znaci da ima parenta, ako je isti ili je null, onda nema
	 * */
	String qry1 = """
				select cp.*
				from Client_Pricelist cp
				inner join Client c 
				    on cp.client_Id = 
				        case when c.Id != c.parent_id then c.parent_id
				        else c.id 
				    end
				where 1=1
					and c.id = :clientId
			""";
	@Query(value=qry1, nativeQuery=true)
	List<ClientPricelist> findAllByClientId(Integer clientId, Pageable pageable);

	List<ClientPricelist> findAllByItemId(Integer itemId, Pageable pageable);

}
