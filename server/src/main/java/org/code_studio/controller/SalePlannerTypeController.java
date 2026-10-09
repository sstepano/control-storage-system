package org.code_studio.controller;

import org.code_studio.database.SalePlannerType;
import org.code_studio.model.SalePlannerTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.salePlannerType}")
public class SalePlannerTypeController extends BaseController <SalePlannerType> {

	@Autowired
	SalePlannerTypeModel model;
	
	public SalePlannerTypeController (SalePlannerTypeModel model) {
		super(model);
		this.model = model;
	}
	
}
