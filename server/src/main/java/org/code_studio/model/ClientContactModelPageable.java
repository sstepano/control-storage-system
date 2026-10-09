package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientContact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientContactModelPageable extends PagingAndSortingRepository <ClientContact, Integer> {
	//------------------------------------------------------------------------	
	@Query("select c from ClientContact c")
	List <ClientContact> findAllPageable(Pageable pageable);
	//------------------------------------------------------------------------
	String searchQuery = """
			select c from ClientContact c
			where
				upper(c.name) like '%' || upper(:searchKeyword ) || '%'
	""";
	
	@Query(value=searchQuery, nativeQuery=false)	
	List <ClientContact> search(String searchKeyword, Pageable pageable);
	//------------------------------------------------------------------------
	/* IZBACIO, dogovor sa Banetom
	String searchOwnerQuery = """
			select c from ClientContact c
			where
				c.isOwnerDirector = true 
			and c.client.getId() = :clientId
	""";
	
	@Query(value=searchOwnerQuery, nativeQuery=false)	
	List <ClientContact> findOwnerDirector(String clientId);
	*/
	//------------------------------------------------------------------------
	
}
