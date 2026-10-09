package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ClientContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientContractModel extends JpaRepository <ClientContract, Integer> {
	
	List<ClientContract> findAllByClientId(Integer clientId);
}
