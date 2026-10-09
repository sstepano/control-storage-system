package org.code_studio.model;

import org.code_studio.database.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeModel extends JpaRepository <ItemType, Integer> {
}
