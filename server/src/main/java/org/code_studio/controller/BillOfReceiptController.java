package org.code_studio.controller;

import org.code_studio.database.BillOfReceipt;
import org.code_studio.model.BillOfReceiptModel;
import org.code_studio.model.BillOfReceiptModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.billOfReceipt}")
public class BillOfReceiptController extends BaseController <BillOfReceipt> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	BillOfReceiptModel model;
	
	@Autowired
	BillOfReceiptModelPageable modelPageable;
	
	public BillOfReceiptController (BillOfReceiptModel model) {
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
	
	@GetMapping("allPageableByClientIdAndBillOfReceiptType/{clientId}/{billOfReceiptType}/{pageId}")
	public ResponseEntity <Object> findAllPageableByClientIdAndBillOfReceiptType (
			@PathVariable Integer clientId, @PathVariable String billOfReceiptType, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByClientIdAndBillOfReceiptType(clientId, billOfReceiptType, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
