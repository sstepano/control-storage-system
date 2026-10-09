package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemGroupModel extends JpaRepository <ItemGroup, Integer> {
	List <ItemGroup> findAllByClientId(Integer clientId);
}
