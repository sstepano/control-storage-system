package org.code_studio.controller;

import org.code_studio.database.DeliveryAddress;
import org.code_studio.model.DeliveryAddressModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.deliveryAddress}")
public class DeliveryAddressController extends BaseController <DeliveryAddress> {

	@Autowired
	DeliveryAddressModel model;
	
	public DeliveryAddressController (DeliveryAddressModel model) {
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
	
	@GetMapping("deliveryAddressByClientId/{clientId}")
	public ResponseEntity <Object> findDeliveryAddressByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findDeliveryAddressByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/**
	 * Vraca 3 reda, za svaki tip adrese po jedan (ako postoji kdefinisan kod klijenta)
	 * ROBA, POSTA, PRODAVNICA
	 * @param clientId
	 * @return
	 */
	@GetMapping("allCompositeByClientId/{clientId}")
	public ResponseEntity <Object> findAllCompositeByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllCompositeByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
