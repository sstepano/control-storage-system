package org.code_studio.controller;

import org.code_studio.database.TransferOrder;
import org.code_studio.model.TransferOrderModel;
import org.code_studio.model.TransferOrderModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.transferOrder}")
public class TransferOrderController extends BaseController <TransferOrder> {
	
	@Value("${server.pageSize}")
	private int pageSize;

	@Autowired
	TransferOrderModel model;
	
	@Autowired
	TransferOrderModelPageable modelPageable;
	
	public TransferOrderController (TransferOrderModel model) {
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
	
	@GetMapping("allPageableById/{id}/{pageId}")
	public ResponseEntity <Object> allPageable (@PathVariable Integer id, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableById(id, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByTypeCode/{typeCode}/{pageId}")
	public ResponseEntity <Object> allPageableByTypeCode (@PathVariable String typeCode, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByTypeCode(typeCode, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByInvoiceId/{invoiceId}")
	public ResponseEntity <Object> allByInvoiceId (@PathVariable Integer invoiceId) {
		try {
			responseData = model.findByInvoiceId(invoiceId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByStatusId/{statusId}/{pageId}")
	public ResponseEntity <Object> allPageableByStatusId (@PathVariable Integer statusId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByStatusId(statusId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("findMaxCurrentNumber")
	public ResponseEntity <Object> findMaxCurrentNumber () {
		try {
			responseData = model.findMaxCurrentNumber();
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
