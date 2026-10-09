package org.code_studio.controller;

import org.code_studio.database.PackageType;
import org.code_studio.model.PackageTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.packageType}")
public class PackageTypeController extends BaseController <PackageType> {

	@Autowired
	PackageTypeModel model;
	
	public PackageTypeController (PackageTypeModel model) {
		super(model);
		this.model = model;
	}
	
}
