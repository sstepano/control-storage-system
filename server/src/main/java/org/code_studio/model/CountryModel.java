package org.code_studio.model;

import org.code_studio.database.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryModel extends JpaRepository <Country, Integer> {
	
}
