package org.code_studio.model;

import org.code_studio.database.ApplicationVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationVersionModel extends JpaRepository <ApplicationVersion, Integer> {}
