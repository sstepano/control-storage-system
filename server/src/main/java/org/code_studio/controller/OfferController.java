package org.code_studio.controller;

import org.code_studio.database.Offer;
import org.code_studio.model.OfferModel;
import org.code_studio.model.OfferModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.offer}")
public class OfferController extends BaseController <Offer> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	OfferModel model;
	
	@Autowired
	OfferModelPageable modelPageable;
	
	public OfferController (OfferModel model) {
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
	
	@GetMapping("allPageableByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> findAllPageableByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByClientId(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
