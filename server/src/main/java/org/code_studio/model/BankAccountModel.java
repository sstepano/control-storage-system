package org.code_studio.model;

import org.code_studio.database.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountModel extends JpaRepository <BankAccount, Integer> {
	
}
