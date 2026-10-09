package org.code_studio.model;

import org.code_studio.database.CustomsGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomsGroupModel extends JpaRepository <CustomsGroup, Integer> {
	
}
