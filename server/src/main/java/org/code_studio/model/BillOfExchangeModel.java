package org.code_studio.model;

import java.util.List;

import org.code_studio.database.BillOfExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillOfExchangeModel extends JpaRepository <BillOfExchange, Integer> {
	
	List<BillOfExchange> findAllByClientId(Integer clientId);
}
