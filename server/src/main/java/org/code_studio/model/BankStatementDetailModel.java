package org.code_studio.model;

import org.code_studio.database.BankStatementDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankStatementDetailModel extends JpaRepository <BankStatementDetail, Integer> {}
