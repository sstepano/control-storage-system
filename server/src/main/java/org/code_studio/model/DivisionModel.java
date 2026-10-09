package org.code_studio.model;

import org.code_studio.database.Division;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionModel extends JpaRepository <Division, Integer> {
	
}
