package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientStoreModel extends JpaRepository <ClientStore, Integer> {

	List<ClientStore> findAllByClientId(Integer clientId);
	
}
