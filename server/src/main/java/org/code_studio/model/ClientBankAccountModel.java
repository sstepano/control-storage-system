package org.code_studio.model;

import org.code_studio.database.ClientBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientBankAccountModel extends JpaRepository <ClientBankAccount, Integer> {}
