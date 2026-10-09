package org.code_studio.controller;

import org.code_studio.database.MeasurementUnit;
import org.code_studio.model.MeasurementUnitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.measurementUnit}")
public class MeasurementUnitController extends BaseController <MeasurementUnit> {

	@Autowired
	MeasurementUnitModel model;
	
	public MeasurementUnitController (MeasurementUnitModel model) {
		super(model);
		this.model = model;
	}
	
}
