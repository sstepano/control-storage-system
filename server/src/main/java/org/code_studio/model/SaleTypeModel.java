package org.code_studio.model;

import org.code_studio.database.SaleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleTypeModel extends JpaRepository <SaleType, Integer> {
	
}
