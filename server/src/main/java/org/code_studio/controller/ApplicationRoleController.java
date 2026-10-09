package org.code_studio.controller;

import org.code_studio.database.ApplicationRole;
import org.code_studio.model.ApplicationRoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.applicationRole}")
public class ApplicationRoleController extends BaseController <ApplicationRole> {

	@Autowired
	ApplicationRoleModel model;
	
	public ApplicationRoleController (ApplicationRoleModel model) {
		super(model);
		this.model = model;
	}
	
}
