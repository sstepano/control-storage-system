package org.code_studio.controller;

import org.code_studio.database.PaymentTerm;
import org.code_studio.model.PaymentTermModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.paymentTerm}")
public class PaymentTermController extends BaseController <PaymentTerm> {

	@Autowired
	PaymentTermModel model;
	
	public PaymentTermController (PaymentTermModel model) {
		super(model);
		this.model = model;
	}
	
}
