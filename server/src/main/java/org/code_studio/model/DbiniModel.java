package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Dbini;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

public interface DbiniModel extends JpaRepository <Dbini, Integer> {
	
	public List<Dbini> findAllByAttributeNameAndParameterName(String attributeName, @PathVariable String parameterName);
}
