package org.code_studio.controller;

import org.code_studio.database.VatGroup;
import org.code_studio.model.VatGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.vatGroup}")
public class VatGroupController extends BaseController <VatGroup> {

	@Autowired
	VatGroupModel model;
	
	public VatGroupController (VatGroupModel model) {
		super(model);
		this.model = model;
	}
	
}
