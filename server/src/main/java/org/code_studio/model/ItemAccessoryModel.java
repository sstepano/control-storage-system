package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemAccessory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemAccessoryModel extends JpaRepository <ItemAccessory, Integer> {
	List <ItemAccessory> findAllByParentItemId(Integer parentItemId);
}
