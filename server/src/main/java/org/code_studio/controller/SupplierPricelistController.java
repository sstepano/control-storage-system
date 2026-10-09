package org.code_studio.controller;

import org.code_studio.database.SupplierPricelist;
import org.code_studio.model.SupplierPricelistModel;
import org.code_studio.model.SupplierPricelistModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.supplierPricelist}")
public class SupplierPricelistController extends BaseController <SupplierPricelist> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	SupplierPricelistModel model;
	
	@Autowired
	SupplierPricelistModelPageable modelPageable;
	
	public SupplierPricelistController (SupplierPricelistModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageableByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> allByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientId(clientId, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByItemId/{itemId}/{pageId}")
	public ResponseEntity <Object> allByItemId (@PathVariable Integer itemId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByItemId(itemId, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
