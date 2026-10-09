package org.code_studio.controller;

import org.code_studio.database.TransferOrderDetail;
import org.code_studio.model.TransferOrderDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.transferOrderDetail}")
public class TransferOrderDetailController extends BaseController <TransferOrderDetail> {
	
	@Value("${server.pageSize}")
	private int pageSize;

	@Autowired
	TransferOrderDetailModel model;
	
	public TransferOrderDetailController (TransferOrderDetailModel model) {
		super(model);
		this.model = model;
	}
	
	
	@GetMapping("findAllByTransferOrderId/{transferOrderId}")
	public ResponseEntity <Object> findAllByTransferOrderId (@PathVariable Integer transferOrderId) {
		try {
			responseData = model.findAllByTransferOrderIdOrderByWarehouseIdOrigin(transferOrderId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("findByInvoiceDetailId/{invoiceDetailId}")
	public ResponseEntity <Object> findByInvoiceDetailId (@PathVariable Integer invoiceDetailId) {
		try {
			responseData = model.findByInvoiceDetailId(invoiceDetailId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allDifferencesByTransferOrderId/{transferOrderId}")
	public ResponseEntity <Object> allDifferencesByTransferOrderId (@PathVariable Integer transferOrderId) {
		try {
			responseData = model.findDifferencesByTransferOrderId(transferOrderId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
