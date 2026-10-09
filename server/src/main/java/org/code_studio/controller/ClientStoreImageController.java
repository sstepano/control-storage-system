package org.code_studio.controller;

import org.code_studio.database.ClientStoreImage;
import org.code_studio.model.ClientStoreImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.clientStoreImage}")
public class ClientStoreImageController extends BaseController <ClientStoreImage> {

	@Autowired
	ClientStoreImageModel model;
	
	public ClientStoreImageController (ClientStoreImageModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByClientId/{clientId}")
	public ResponseEntity <Object> allByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
