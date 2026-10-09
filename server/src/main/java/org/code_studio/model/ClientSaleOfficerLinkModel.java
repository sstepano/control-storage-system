package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientSaleOfficerLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClientSaleOfficerLinkModel extends JpaRepository <ClientSaleOfficerLink, Integer> {
	List <ClientSaleOfficerLink> findAllById(Integer Id);
	List <ClientSaleOfficerLink> findAllByClientId(Integer Id);
	
	String qryUpdate = """
			update ClientSaleOfficerLink l
			set isPrimary = false
			where 1=1
				and l.clientId = 1
				and l.isPrimary = true
	""";
	@Transactional
	@Modifying
	@Query(value=qryUpdate, nativeQuery=false)
	Integer testUpdate();
}
