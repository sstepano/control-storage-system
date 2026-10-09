package org.code_studio.controller;

import org.code_studio.database.Currency;
import org.code_studio.model.CurrencyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.currency}")
public class CurrencyController extends BaseController <Currency> {

	@Autowired
	CurrencyModel model;
	
	public CurrencyController (CurrencyModel model) {
		super(model);
		this.model = model;
	}
	
}
