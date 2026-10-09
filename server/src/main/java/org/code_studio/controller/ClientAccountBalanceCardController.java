package org.code_studio.controller;

import java.time.LocalDate;

import org.code_studio.database.ClientAccountBalanceCard;
import org.code_studio.model.ClientAccountBalanceCardModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.clientAccountBalance}")
public class ClientAccountBalanceCardController extends BaseController <ClientAccountBalanceCard> {
	
	@Autowired
	ApplicationContext ctx;
	
	@Value("${server.pageSize}")
	private int pageSize;

	@Autowired
	ClientAccountBalanceCardModel model;
	
	public ClientAccountBalanceCardController (ClientAccountBalanceCardModel model) {
		super(model);
		this.model = model;
	}

	@GetMapping("debitCreditByClientIdAndValueDate/{clientId}/{valueDateFrom}/{valueDateTo}")
	public ResponseEntity <Object> debitCreditByClientIdAndValueDate (@PathVariable Integer clientId, @PathVariable String valueDateFrom, @PathVariable String valueDateTo) {
		try {
			responseData = model.findDebitCreditByClientIdAndValueDate(clientId, LocalDate.parse(valueDateFrom), LocalDate.parse(valueDateTo));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("accountBalanceByClientIdAndValueDate/{clientId}/{valueDateFrom}/{valueDateTo}")
	public ResponseEntity <Object> accountBalanceByClientIdAndValueDate (@PathVariable Integer clientId, @PathVariable String valueDateFrom, @PathVariable String valueDateTo) {
		try {
			responseData = model.findAccountBalanceByClientIdAndValueDate(clientId, LocalDate.parse(valueDateFrom), LocalDate.parse(valueDateTo));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("totalBalanceByClientIdAndValueDate/{clientId}/{valueDateTo}")
	public ResponseEntity <Object> totalBalanceByClientId (@PathVariable Integer clientId, @PathVariable String valueDateTo) {
		try {
			responseData = model.findTotalBalanceByClientIdAndValueDate(clientId, LocalDate.parse(valueDateTo));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}	

}
