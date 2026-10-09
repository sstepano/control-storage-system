package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemModel extends JpaRepository <Item, Integer> {
	
	List <Item> findByCode(String code);	
	List<Item> findBySubgroupId(Integer subgroupId);
	
}
