package org.code_studio.controller;

import org.code_studio.database.TransferOrderType;
import org.code_studio.model.TransferOrderTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.transferOrderType}")
public class TransferOrderTypeController extends BaseController <TransferOrderType> {

	@Autowired
	TransferOrderTypeModel model;
	
	public TransferOrderTypeController (TransferOrderTypeModel model) {
		super(model);
		this.model = model;
	}
	
}
