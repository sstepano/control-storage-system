package org.code_studio.model;

import java.util.List;

import org.code_studio.database.InternetCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternetCatalogModel extends JpaRepository <InternetCatalog, Integer> {
	List<InternetCatalog> findAllByOrderByOrdinalOrderAsc();
}
