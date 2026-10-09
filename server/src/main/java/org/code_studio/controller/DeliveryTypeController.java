package org.code_studio.controller;

import org.code_studio.database.DeliveryType;
import org.code_studio.model.DeliveryTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.deliveryType}")
public class DeliveryTypeController extends BaseController <DeliveryType> {

	@Autowired
	DeliveryTypeModel model;
	
	public DeliveryTypeController (DeliveryTypeModel model) {
		super(model);
		this.model = model;
	}
	
}
