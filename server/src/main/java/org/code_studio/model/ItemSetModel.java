package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSetModel extends JpaRepository <ItemSet, Integer> {
	List <ItemSet> findAllByItemId(Integer itemId);
}
