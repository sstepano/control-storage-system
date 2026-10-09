package org.code_studio.model;

import org.code_studio.database.ApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRoleModel extends JpaRepository <ApplicationRole, Integer> {
	
}
