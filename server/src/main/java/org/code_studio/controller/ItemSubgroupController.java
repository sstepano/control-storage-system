package org.code_studio.controller;

import org.code_studio.database.ItemSubgroup;
import org.code_studio.model.ItemSubgroupModel;
import org.code_studio.model.ItemSubgroupModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemSubgroup}")
public class ItemSubgroupController extends BaseController <ItemSubgroup> {

	//TODO: budz ...
	//@Value("${server.pageSize}")
	private int pageSize = 30;
	
	@Autowired
	ItemSubgroupModel model;

	@Autowired
	ItemSubgroupModelPageable modelPageable;
	
	public ItemSubgroupController (ItemSubgroupModel model) {
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
	
	@GetMapping("allByClientIdPageable/{clientId}/{pageId}")
	public ResponseEntity <Object> findAllByClientIdPageable (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientId(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allByCatalogIdPageable/{catalogId}/{pageId}")
	public ResponseEntity <Object> findAllByCatalogIdPageable (@PathVariable Integer catalogId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByCatalogId(catalogId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	

	@GetMapping("allByBranchIdPageable/{branchId}/{pageId}")
	public ResponseEntity <Object> findAllByBranchIdIdPageable (@PathVariable Integer branchId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByBranchId(branchId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByGroupIdPageable/{groupId}/{pageId}")
	public ResponseEntity <Object> findAllByGroupId (@PathVariable Integer groupId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByGroupId(groupId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
