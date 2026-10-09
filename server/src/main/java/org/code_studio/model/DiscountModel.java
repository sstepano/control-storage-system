package org.code_studio.model;

import org.code_studio.database.Discount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiscountModel extends JpaRepository <Discount, Integer> {
}
