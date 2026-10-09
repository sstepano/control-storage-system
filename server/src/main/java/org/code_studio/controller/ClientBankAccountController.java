package org.code_studio.controller;

import org.code_studio.database.ClientBankAccount;
import org.code_studio.model.ClientBankAccountModel;
import org.code_studio.model.ClientBankAccountModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.clientBankAccount}")
public class ClientBankAccountController extends BaseController <ClientBankAccount> {

	@Value("${server.pageSize}")
	private int pageSize = 30;
	
	@Autowired
	ClientBankAccountModel model;

	@Autowired
	ClientBankAccountModelPageable modelPageable;
	
	public ClientBankAccountController (ClientBankAccountModel model) {
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
	
	@GetMapping("allPageableByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> findAllByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientId(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allPageableByAccountNumber/{accountNumber}/{pageId}")
	public ResponseEntity <Object> findAllByAccountNumber (@PathVariable String accountNumber, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByAccountNumberContaining(accountNumber, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	
}
