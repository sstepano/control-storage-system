package org.code_studio.controller;

import org.code_studio.database.BankStatement;
import org.code_studio.model.BankStatementModel;
import org.code_studio.model.BankStatementModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.bankStatement}")
public class BankStatementController extends BaseController <BankStatement> {

	@Value("${server.pageSize}")
	private int pageSize = 30;
	
	@Autowired
	BankStatementModel model;

	@Autowired
	BankStatementModelPageable modelPageable;
	
	public BankStatementController (BankStatementModel model) {
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
	
	@GetMapping("maxNumberByAccountIdAndYear/{accountId}/{year}")
	public ResponseEntity <Object> findMaxBankStatementNumberByBankStatementDate (@PathVariable Integer accountId, @PathVariable Integer year) {
		try {
			responseData = modelPageable.findMaxBankStatementNumberByBankStatementDate(accountId, year);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByAccountId/{accountId}/{pageId}")
	public ResponseEntity <Object> findAllByAccountId (@PathVariable Integer accountId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByAccountIdOrderByBankStatementDateDescBankStatementNumberDesc(accountId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	
}
