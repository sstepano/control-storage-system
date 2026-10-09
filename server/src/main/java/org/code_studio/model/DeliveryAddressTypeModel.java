package org.code_studio.model;

import org.code_studio.database.DeliveryAddressType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressTypeModel extends JpaRepository <DeliveryAddressType, Integer> {
	
}
