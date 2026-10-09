package org.code_studio.controller;

import org.code_studio.database.ItemSet;
import org.code_studio.model.ItemSetModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemSet}")
public class ItemSetController extends BaseController <ItemSet> {
	
	@Autowired
	ItemSetModel model;
	
	public ItemSetController (ItemSetModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByItemId/{itemId}")
	public ResponseEntity <Object> findAllByItemId (@PathVariable Integer itemId) {
		try {
			responseData = model.findAllByItemId(itemId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
