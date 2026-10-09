package org.code_studio.controller;

import org.code_studio.database.PaymentType;
import org.code_studio.model.PaymentTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.paymentType}")
public class PaymentTypeController extends BaseController <PaymentType> {

	@Autowired
	PaymentTypeModel model;
	
	public PaymentTypeController (PaymentTypeModel model) {
		super(model);
		this.model = model;
	}
	
}
