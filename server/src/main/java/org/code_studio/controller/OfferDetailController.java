package org.code_studio.controller;

import org.code_studio.database.OfferDetail;
import org.code_studio.model.OfferDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.offerDetail}")
public class OfferDetailController extends BaseController <OfferDetail> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	OfferDetailModel model;

	
	public OfferDetailController (OfferDetailModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByOfferId/{offerId}")
	public ResponseEntity <Object> findAllByOfferId (@PathVariable Integer offerId) {
		try {
			responseData = model.findAllByOfferId(offerId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
