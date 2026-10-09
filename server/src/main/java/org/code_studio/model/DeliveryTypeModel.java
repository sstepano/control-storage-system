package org.code_studio.model;

import org.code_studio.database.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryTypeModel extends JpaRepository <DeliveryType, Integer> {
	
}
