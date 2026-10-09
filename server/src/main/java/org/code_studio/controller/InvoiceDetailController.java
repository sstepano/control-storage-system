package org.code_studio.controller;

import org.code_studio.database.InvoiceDetail;
import org.code_studio.model.InvoiceDetailModel;
import org.code_studio.model.InvoiceDetailModelPageable;
import org.code_studio.model.InvoiceDetailSumModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.invoiceDetail}")
public class InvoiceDetailController extends BaseController <InvoiceDetail> {

	@Value("${server.pageSize}")
	private int pageSize=50;
	
	@Autowired
	public InvoiceDetailModel model;
	
	@Autowired
	InvoiceDetailModelPageable modelPageable;
	
	@Autowired
	InvoiceDetailSumModel modelSum;
	
	public InvoiceDetailController (InvoiceDetailModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageableByInvoiceId/{invoiceId}/{pageId}")
	public ResponseEntity <Object> findAllByInvoiceId (@PathVariable Integer invoiceId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByInvoiceId(invoiceId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("sumByInvoiceId/{invoiceId}")
	public ResponseEntity <Object> sumByInvoiceId (@PathVariable Integer invoiceId) {
		try {
			responseData = modelSum.sumByInvoiceId(invoiceId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
}
