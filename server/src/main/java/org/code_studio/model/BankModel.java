package org.code_studio.model;

import org.code_studio.database.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankModel extends JpaRepository <Bank, Integer> {
	
}
