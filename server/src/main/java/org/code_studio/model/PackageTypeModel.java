package org.code_studio.model;

import org.code_studio.database.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageTypeModel extends JpaRepository <PackageType, Integer> {
	
}
