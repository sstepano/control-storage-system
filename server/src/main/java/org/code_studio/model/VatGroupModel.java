package org.code_studio.model;

import org.code_studio.database.VatGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VatGroupModel extends JpaRepository <VatGroup, Integer> {
	
}
