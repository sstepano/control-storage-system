package org.code_studio.model;

import org.code_studio.database.SalePlannerMonthByDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalePlannerMonthByDayModel extends JpaRepository <SalePlannerMonthByDay, Integer> {
	
}
