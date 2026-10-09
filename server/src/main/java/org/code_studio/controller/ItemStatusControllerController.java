package org.code_studio.controller;

import org.code_studio.database.ItemStatus;
import org.code_studio.model.ItemStatusModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemStatus}")
public class ItemStatusControllerController extends BaseController <ItemStatus> {
	
	@Autowired
	ItemStatusModel model;
	
	public ItemStatusControllerController (ItemStatusModel model) {
		super(model);
		this.model = model;
	}

}
