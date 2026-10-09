package org.code_studio.model;

import org.code_studio.database.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementUnitModel extends JpaRepository <MeasurementUnit, Integer> {
	
}
