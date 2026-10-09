package org.code_studio.controller;

import org.code_studio.database.Brand;
import org.code_studio.model.BrandModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.brand}")
public class BrandController extends BaseController<Brand> {

	@Autowired
	BrandModel model;
	
	public BrandController (BrandModel model) {
		super(model);
		this.model = model;
	}
	
}
