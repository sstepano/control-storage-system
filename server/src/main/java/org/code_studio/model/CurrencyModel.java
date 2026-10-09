package org.code_studio.model;

import org.code_studio.database.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyModel extends JpaRepository <Currency, Integer> {
	
}
