package org.code_studio.controller;

import org.code_studio.database.InternetCatalog;
import org.code_studio.model.InternetCatalogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.internetCatalog}")
public class InternetCatalogController extends BaseController <InternetCatalog> {

	@Autowired
	InternetCatalogModel model;
	
	public InternetCatalogController (InternetCatalogModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("findAllByOrderByOrdinalOrderAsc")
	public ResponseEntity <Object> findAllByOrderByOrdinalOrderAsc () {
		try {
			responseData = model.findAllByOrderByOrdinalOrderAsc();
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
