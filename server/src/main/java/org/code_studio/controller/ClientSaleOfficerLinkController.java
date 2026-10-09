package org.code_studio.controller;

import org.code_studio.database.ClientSaleOfficerLink;
import org.code_studio.model.ClientSaleOfficerLinkModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.clientSaleOfficerLink}")
public class ClientSaleOfficerLinkController extends BaseController <ClientSaleOfficerLink> {
	
	@Autowired
	ClientSaleOfficerLinkModel model;
		
	public ClientSaleOfficerLinkController (ClientSaleOfficerLinkModel model) {
		super(model);
		this.model = model;
	}

	/*
	@GetMapping("allSaleOfficersByClientId/{clientId}")
	public ResponseEntity <Object> allSaleOfficersByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllSaleOfficersByClientId(saleOfficerRoleIDs.split(","), clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@PutMapping("primarySaleOfficerByClientId/{clientId}")
	public ResponseEntity <Object> primarySaleOfficerByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findPrimarySaleOfficerByClientId(saleOfficerRoleIDs.split(","), clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}*/
	
	@PutMapping("testUpdate")
	public ResponseEntity <Object> testUpdate () {
		try {
			responseData = model.testUpdate();
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
