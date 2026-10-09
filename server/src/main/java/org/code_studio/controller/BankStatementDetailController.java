package org.code_studio.controller;

import org.code_studio.database.BankStatementDetail;
import org.code_studio.model.BankStatementDetailModel;
import org.code_studio.model.BankStatementDetailModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.bankStatementDetail}")
public class BankStatementDetailController extends BaseController <BankStatementDetail> {

	@Value("${server.pageSize}")
	private int pageSize = 30;
	
	@Autowired
	BankStatementDetailModel model;

	@Autowired
	BankStatementDetailModelPageable modelPageable;
	
	public BankStatementDetailController (BankStatementDetailModel model) {
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
	
	@GetMapping("allPageableByBankStatementId/{bankStatementId}/{pageId}")
	public ResponseEntity <Object> findAllByAccountId (@PathVariable Integer bankStatementId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByBankStatementIdOrderByDetailOrdinalNumberAsc(bankStatementId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("maxNumberByBankStatementId/{bankStatementId}")
	public ResponseEntity <Object> findMaxBankStatementNumberByBankStatementId (@PathVariable Integer bankStatementId) {
		try {
			responseData = modelPageable.findMaxBankStatementDetailNumberByBankStatementId(bankStatementId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	
}
