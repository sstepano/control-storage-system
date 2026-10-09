package org.code_studio.model;

import org.code_studio.database.BillOfLadingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillOfLadingTypeModel extends JpaRepository <BillOfLadingType, Integer> {
	
}
