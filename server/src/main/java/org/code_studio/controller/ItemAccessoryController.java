package org.code_studio.controller;

import org.code_studio.database.ItemAccessory;
import org.code_studio.model.ItemAccessoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemAccessory}")
public class ItemAccessoryController extends BaseController <ItemAccessory> {
	
	@Autowired
	ItemAccessoryModel model;
	
	public ItemAccessoryController (ItemAccessoryModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByParentItemId/{parentItemId}")
	public ResponseEntity <Object> findAllByItemId (@PathVariable Integer parentItemId) {
		try {
			responseData = model.findAllByParentItemId(parentItemId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
