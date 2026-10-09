package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemBranch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemBranchModel extends JpaRepository <ItemBranch, Integer> {
	List <ItemBranch> findAllByClientId(Integer clientId);
}
