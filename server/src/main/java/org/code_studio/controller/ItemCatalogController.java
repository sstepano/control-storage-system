package org.code_studio.controller;

import org.code_studio.database.ItemCatalog;
import org.code_studio.model.ItemCatalogModel;
import org.code_studio.model.ItemCatalogModelPageable;
import org.code_studio.model.ItemCatalogViewModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemCatalog}")
public class ItemCatalogController extends BaseController <ItemCatalog> {

	//TODO: budz ...
	//@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	ItemCatalogModel model;

	@Autowired
	ItemCatalogModelPageable modelPageable;
	
	@Autowired
	ItemCatalogViewModelPageable modelViewPageable;
	
	public ItemCatalogController (ItemCatalogModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageable/{pageId}")
	public ResponseEntity <Object> findAllPageable (@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageable(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByClientIdPageable/{clientId}/{pageId}")
	public ResponseEntity <Object> findAllByClientIdPageable (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientId(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
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
