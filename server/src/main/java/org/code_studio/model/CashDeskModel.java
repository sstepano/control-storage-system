package org.code_studio.model;

import org.code_studio.database.CashDesk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashDeskModel extends JpaRepository <CashDesk, Integer> {
	
}
