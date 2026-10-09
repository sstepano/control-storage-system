package org.code_studio.controller;

import org.code_studio.database.BankAccount;
import org.code_studio.model.BankAccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.bankAccount}")
public class BankAccountController extends BaseController <BankAccount> {

	@Autowired
	BankAccountModel model;
	
	public BankAccountController (BankAccountModel model) {
		super(model);
		this.model = model;
	}

}
