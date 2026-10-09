package org.code_studio.controller;

import org.code_studio.database.ClientLastPaidAmount;
import org.code_studio.model.ClientLastPaidAmountModel;
import org.code_studio.model.ClientLastPaidAmountModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.clientLastPaidAmount}")
public class ClientLastPaidAmountController extends BaseController <ClientLastPaidAmount> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	ClientLastPaidAmountModel model;
	
	@Autowired
	ClientLastPaidAmountModelPageable modelPageable;
	
	public ClientLastPaidAmountController (ClientLastPaidAmountModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByClientId/{clientId}")
	public ResponseEntity <Object> allByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableBySaleOfficerIdAndDateRange/{saleOfficerId}/{month}/{year}/{pageId}")
	public ResponseEntity <Object> allPageableBySaleOfficerIdAndDateRange (
			@PathVariable Integer saleOfficerId, 
			@PathVariable String month, 
			@PathVariable String year, 
			@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllBySaleOfficerIdAndDateRange(saleOfficerId, month, year, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
