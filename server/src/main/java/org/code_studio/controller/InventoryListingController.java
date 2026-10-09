package org.code_studio.controller;

import org.code_studio.database.InventoryListing;
import org.code_studio.model.InventoryListingModel;
import org.code_studio.model.InventoryListingModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.inventoryListing}")
public class InventoryListingController extends BaseController <InventoryListing> {
	
	@Value("${server.pageSize}")
	private int pageSize=50;

	@Autowired
	public InventoryListingModel model;
	
	@Autowired
	InventoryListingModelPageable modelPageable;
	
	public InventoryListingController (InventoryListingModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageable/{pageId}")
	public ResponseEntity <Object> allPageable (@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageable(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByStatusIdAndEnumeratorUserId/{statusId}/{enumeratorUserId}/{pageId}")
	public ResponseEntity <Object> allPageableByStatusIdAndEnumeratorUserId (@PathVariable int statusId, @PathVariable int enumeratorUserId, @PathVariable int pageId) {
		try {
			responseData = modelPageable.findAllPageableByStatusIdAndEnumeratorUserId(statusId, enumeratorUserId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
