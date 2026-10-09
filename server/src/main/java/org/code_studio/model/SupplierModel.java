package org.code_studio.model;

import org.code_studio.database.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierModel extends JpaRepository <Client, Integer> {
	
}
