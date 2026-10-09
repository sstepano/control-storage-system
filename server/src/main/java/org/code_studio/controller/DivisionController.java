package org.code_studio.controller;

import org.code_studio.database.Division;
import org.code_studio.model.DivisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.division}")
public class DivisionController extends BaseController <Division> {

	@Autowired
	DivisionModel model;
	
	public DivisionController (DivisionModel model) {
		super(model);
		this.model = model;
	}
	
}
