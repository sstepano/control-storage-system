package org.code_studio.model;

import org.code_studio.database.PaletteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaletteItemModel extends JpaRepository <PaletteItem, Integer> {
	
}
