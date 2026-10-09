package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCatalogModel extends JpaRepository <ItemCatalog, Integer> {
	List <ItemCatalog> findAllByClientId(Integer clientId);
}
