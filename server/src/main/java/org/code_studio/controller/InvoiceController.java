package org.code_studio.controller;

import org.code_studio.database.Invoice;
import org.code_studio.model.InvoiceModel;
import org.code_studio.model.InvoiceModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.invoice}")
public class InvoiceController extends BaseController <Invoice> {
	
	@Value("${server.pageSize}")
	private int pageSize=50;

	@Autowired
	public InvoiceModel model;
	
	@Autowired
	InvoiceModelPageable modelPageable;
	
	public InvoiceController (InvoiceModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageableByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> allPageableByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientIdOrderByIdDesc(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByInvoiceTypeAndClientId/{invoiceType}/{clientId}/{pageId}")
	public ResponseEntity <Object> allPageableByInvoiceTypeAndClientId (@PathVariable String invoiceType, @PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByInvoiceTypeAndClientIdOrderByIdDesc(invoiceType, clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("maxInvoiceNumberByClientId/{clientId}")
	public ResponseEntity <Object> maxInvoiceNumberByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.maxInvoiceNumberByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
}
