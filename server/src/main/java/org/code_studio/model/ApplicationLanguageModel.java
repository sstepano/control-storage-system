package org.code_studio.model;

import org.code_studio.database.ApplicationLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationLanguageModel extends JpaRepository <ApplicationLanguage, Integer> {
	
}
