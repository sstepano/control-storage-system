package org.code_studio.controller;

import org.code_studio.database.ItemBranch;
import org.code_studio.model.ItemBranchModel;
import org.code_studio.model.ItemBranchModelPageable;
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
@RequestMapping("${url.itemBranch}")
public class ItemBranchController extends BaseController <ItemBranch> {
	
	@Autowired
	ItemBranchModel model;

	@Autowired
	ItemBranchModelPageable modelPageable;	

	@Value("${server.pageSize}")
	private int pageSize = 20;	
	
	public ItemBranchController (ItemBranchModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByClientId/{clientId}")
	public ResponseEntity <Object> findAllByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
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
	
}
