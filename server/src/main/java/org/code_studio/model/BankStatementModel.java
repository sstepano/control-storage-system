package org.code_studio.model;

import org.code_studio.database.BankStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankStatementModel extends JpaRepository <BankStatement, Integer> {}
