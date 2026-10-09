package org.code_studio.controller;

import org.code_studio.database.ClientContact;
import org.code_studio.model.ClientContactModel;
import org.code_studio.model.ClientContactModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.clientContact}")
public class ClientContactController extends BaseController <ClientContact> {

	//TODO: budz ...
	//@Value("${server.pageSize}")
	private int pageSize = 100;
	
	@Autowired
	ClientContactModel model;
	
	@Autowired
	ClientContactModelPageable modelPageable;
	
	public ClientContactController (ClientContactModel model) {
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
	
	@GetMapping("allByClientId/{clientId}")
	public ResponseEntity <Object> allByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("search/{searchKeyword}/{pageId}")
	public ResponseEntity <Object> search (@PathVariable String searchKeyword, @PathVariable Integer pageId) {
		System.out.println(searchKeyword);
		try {
			responseData = modelPageable.search(searchKeyword, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
