package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientContactModel extends JpaRepository <ClientContact, Integer> {
	
	//------------------------------------------------------------------------
	String findAllByClientIdQuery = """
			select c from ClientContact c
			where
				clientId = :clientId
	""";
	
	@Query(value=findAllByClientIdQuery, nativeQuery=false)	
	List <ClientContact> findAllByClientId(Integer clientId);
}
