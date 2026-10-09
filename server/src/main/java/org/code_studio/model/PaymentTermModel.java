package org.code_studio.model;

import org.code_studio.database.PaymentTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTermModel extends JpaRepository <PaymentTerm, Integer> {
	
}
