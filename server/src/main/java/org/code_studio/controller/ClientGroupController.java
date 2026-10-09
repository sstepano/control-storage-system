package org.code_studio.controller;

import org.code_studio.database.ClientGroup;
import org.code_studio.model.ClientGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.clientGroup}")
public class ClientGroupController extends BaseController <ClientGroup> {

	@Autowired
	ClientGroupModel model;
	
	public ClientGroupController (ClientGroupModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByIsSupplierGroup/{isSupplierGroup}")
	public ResponseEntity <Object> allByIsSupplierGroup (@PathVariable Boolean isSupplierGroup) {
		try {
			responseData = model.findAllByIsSupplierGroup(isSupplierGroup);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
