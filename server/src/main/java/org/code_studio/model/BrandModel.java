package org.code_studio.model;

import org.code_studio.database.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandModel extends JpaRepository <Brand, Integer> {
	
}
