package org.code_studio.controller;

import org.code_studio.database.ClientCategory;
import org.code_studio.model.ClientCategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.clientCategory}")
public class ClientCategoryController extends BaseController <ClientCategory> {

	@Autowired
	ClientCategoryModel model;
	
	public ClientCategoryController (ClientCategoryModel model) {
		super(model);
		this.model = model;
	}

}
