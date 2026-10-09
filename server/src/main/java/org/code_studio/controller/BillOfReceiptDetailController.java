package org.code_studio.controller;

import org.code_studio.database.BillOfReceiptDetail;
import org.code_studio.model.BillOfReceiptDetailModel;
import org.code_studio.model.BillOfReceiptDetailModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.billOfReceiptDetail}")
public class BillOfReceiptDetailController extends BaseController <BillOfReceiptDetail> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	BillOfReceiptDetailModel model;
	
	@Autowired
	BillOfReceiptDetailModelPageable modelPageable;
	
	public BillOfReceiptDetailController (BillOfReceiptDetailModel model) {
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

	@GetMapping("allPageableByBillOfReceiptId/{billOfReceiptId}/{pageId}")
	public ResponseEntity <Object> findAllPageableByBillOfReceiptId (@PathVariable Integer billOfReceiptId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByBillOfReceiptId(billOfReceiptId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
