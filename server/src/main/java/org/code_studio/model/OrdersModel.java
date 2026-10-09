package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersModel extends JpaRepository <Orders, Integer> {
	List <Orders> findAllByClientId(Integer clientId);
}
