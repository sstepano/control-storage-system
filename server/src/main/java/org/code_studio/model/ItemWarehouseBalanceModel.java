package org.code_studio.model;

import org.code_studio.database.ItemWarehouseBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemWarehouseBalanceModel extends JpaRepository <ItemWarehouseBalance, Integer> {
	
}
