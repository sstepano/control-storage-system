package org.code_studio.controller;

import org.code_studio.database.DeliveryAddressType;
import org.code_studio.model.DeliveryAddressTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.deliveryAddressType}")
public class DeliveryAddressTypeController extends BaseController <DeliveryAddressType> {

	@Autowired
	DeliveryAddressTypeModel model;
	
	public DeliveryAddressTypeController (DeliveryAddressTypeModel model) {
		super(model);
		this.model = model;
	}
	
}
