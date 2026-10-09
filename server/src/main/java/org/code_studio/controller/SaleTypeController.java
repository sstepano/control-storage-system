package org.code_studio.controller;

import org.code_studio.database.SaleType;
import org.code_studio.model.SaleTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.saleType}")
public class SaleTypeController extends BaseController <SaleType> {

	@Autowired
	SaleTypeModel model;
	
	public SaleTypeController (SaleTypeModel model) {
		super(model);
		this.model = model;
	}
	
}
