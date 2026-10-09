package org.code_studio.model;

import org.code_studio.database.ClientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCategoryModel extends JpaRepository <ClientCategory, Integer> {}
