package org.code_studio.model;

import org.code_studio.database.SalePlanner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalePlannerModel extends JpaRepository <SalePlanner, Integer> {
}
