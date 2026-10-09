package org.code_studio.model;

import org.code_studio.database.ItemBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBalanceModel extends JpaRepository <ItemBalance, Integer> {
	
}
