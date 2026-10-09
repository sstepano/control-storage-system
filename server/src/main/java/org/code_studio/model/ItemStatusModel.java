package org.code_studio.model;

import org.code_studio.database.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemStatusModel extends JpaRepository <ItemStatus, Integer> {
}
