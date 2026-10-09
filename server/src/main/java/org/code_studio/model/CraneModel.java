package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Crane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CraneModel extends JpaRepository <Crane, Integer> {
	
	public List<Crane> findAllByIsActive(Integer isActive);
}
