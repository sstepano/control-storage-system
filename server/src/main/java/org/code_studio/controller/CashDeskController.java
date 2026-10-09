package org.code_studio.controller;

import org.code_studio.database.CashDesk;
import org.code_studio.model.CashDeskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.cashDesk}")
public class CashDeskController extends BaseController <CashDesk> {

	@Autowired
	CashDeskModel model;
	
	public CashDeskController (CashDeskModel model) {
		super(model);
		this.model = model;
	}
	
}
