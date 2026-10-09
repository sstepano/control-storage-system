package org.code_studio.controller;

import org.code_studio.database.CustomsGroup;
import org.code_studio.model.CustomsGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.customsGroup}")
public class CustomsGroupController extends BaseController <CustomsGroup> {

	@Autowired
	CustomsGroupModel model;
	
	public CustomsGroupController (CustomsGroupModel model) {
		super(model);
		this.model = model;
	}
	
}
