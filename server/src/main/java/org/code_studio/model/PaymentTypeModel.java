package org.code_studio.model;

import org.code_studio.database.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeModel extends JpaRepository <PaymentType, Integer> {
	
}
