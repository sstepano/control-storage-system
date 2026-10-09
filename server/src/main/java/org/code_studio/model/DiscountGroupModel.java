package org.code_studio.model;

import org.code_studio.database.DiscountGroup;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiscountGroupModel extends JpaRepository <DiscountGroup, Integer> {
}
