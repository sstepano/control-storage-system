package org.code_studio.model;

import org.code_studio.database.TransferOrderType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferOrderTypeModel extends JpaRepository <TransferOrderType, Integer> {
	
}
