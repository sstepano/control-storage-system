package org.code_studio.controller;

import org.code_studio.database.BillOfLadingType;
import org.code_studio.model.BillOfLadingTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.billOfLadingType}")
public class BillOfLadingTypeController extends BaseController <BillOfLadingType> {

	@Autowired
	BillOfLadingTypeModel model;
	
	public BillOfLadingTypeController (BillOfLadingTypeModel model) {
		super(model);
		this.model = model;
	}
	
}
