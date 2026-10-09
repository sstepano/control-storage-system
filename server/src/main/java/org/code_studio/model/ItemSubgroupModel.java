package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemSubgroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSubgroupModel extends JpaRepository <ItemSubgroup, Integer> {
	List <ItemSubgroup> findAllByClientId(Integer clientId);
}
