package org.code_studio.model;

import org.code_studio.database.BillOfReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillOfReceiptModel extends JpaRepository <BillOfReceipt, Integer> {
	//NE TREBA ZA SAD List <BillOfReceipt> findAllByClientId(Long clientId);
}
