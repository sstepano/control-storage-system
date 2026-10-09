package org.code_studio.controller;

import org.code_studio.database.Bank;
import org.code_studio.model.BankModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.bank}")
public class BankController extends BaseController <Bank> {

	@Autowired
	BankModel model;
	
	public BankController (BankModel model) {
		super(model);
		this.model = model;
	}
	
}
