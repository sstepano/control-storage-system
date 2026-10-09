package org.code_studio.controller;

import org.code_studio.database.ItemGroup;
import org.code_studio.model.ItemGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemGroup}")
public class ItemGroupController extends BaseController <ItemGroup> {

	@Autowired
	ItemGroupModel model;
	
	public ItemGroupController (ItemGroupModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByClientId/{clientId}")
	public ResponseEntity <Object> findAllByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
}
