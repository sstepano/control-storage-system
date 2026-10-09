package org.code_studio.controller;

import org.code_studio.database.Client;
import org.code_studio.model.ClientCategoryModel;
import org.code_studio.model.ClientContactModelPageable;
import org.code_studio.model.ClientGroupModel;
import org.code_studio.model.ClientModel;
import org.code_studio.model.ClientModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.client}")
public class ClientController extends BaseController <Client> {
	
	@Value("${server.pageSize}")
	private int pageSize;

	@Autowired
	ClientModel model;
	
	@Autowired
	ClientModelPageable modelPageable;
	
	@Autowired
	ClientGroupModel mdlClientGroup;
	
	@Autowired
	ClientCategoryModel mdlClientCategory;
	
	@Autowired
	ClientContactModelPageable mdlClientContact;
	
	public ClientController (ClientModel model) {
		super(model);
		this.model = model;
	}
	

	@GetMapping("allPageableByDivisionId/{divisionId}/{pageId}")
	public ResponseEntity <Object> allPageableByDivisionId (@PathVariable Integer divisionId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByDivisionId(divisionId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByDivisionIdWithPageSize/{divisionId}/{pageId}/{pageSize}")
	public ResponseEntity <Object> allPageableByDivisionIdWithPageSize (@PathVariable Integer divisionId, @PathVariable Integer pageId, @PathVariable Integer pageSize) {
		try {
			responseData = modelPageable.allByDivisionId(divisionId, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByDivisionIdAndHasQtyWithPageSize/{divisionId}/{hasQty}/{pageId}/{pageSize}")
	public ResponseEntity <Object> allPageableByDivisionIdAndHasQtyWithPageSize (@PathVariable Integer divisionId, @PathVariable Boolean hasQty
			, @PathVariable Integer pageId, @PathVariable Integer pageSize) {
		try {
			responseData = modelPageable.allByDivisionIdAndHasQty(divisionId, hasQty, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageable/{pageId}")
	public ResponseEntity <Object> allPageable (@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.allPageable(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	/*
	@GetMapping("allByGroupName/{groupName}/{pageId}")
	public ResponseEntity <Object> allByGroupName (
				@PathVariable String groupName, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findByGroupName(groupName, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	*/
	
	@GetMapping("allByGroupId/{groupId}/{pageId}")
	public ResponseEntity <Object> allByGroupId (
				@PathVariable Integer groupId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findByGroupId(groupId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allByCategoryId/{categoryId}/{pageId}")
	public ResponseEntity <Object> allByCategoryId (
				@PathVariable Integer categoryId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findByCategoryId(categoryId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	@GetMapping("search/{searchKeyword}/{pageId}")
	public ResponseEntity <Object> search (@PathVariable String searchKeyword, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.search(searchKeyword, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("getIdAndName/{clientId}")
	public ResponseEntity <Object> getIdAndName (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllById(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allBySaleOfficerId/{saleOfficerId}/{pageId}")
	public ResponseEntity <Object> allBySaleOfficerId (
				@PathVariable Integer saleOfficerId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findBySaleOfficerId(saleOfficerId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/**
	 * Used on Sales_Workplan. We have just table in screen and default pagesize 30 is too small
	 * @param saleOfficerId
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	@GetMapping("allBySaleOfficerIdWithPageSize/{saleOfficerId}/{pageSize}/{pageId}")
	public ResponseEntity <Object> allBySaleOfficerIdWithPageSize (
				@PathVariable Integer saleOfficerId, @PathVariable Integer pageId, @PathVariable Integer pageSize) {
		try {
			responseData = modelPageable.findBySaleOfficerId(saleOfficerId, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	// DOBANOVCI -----------------------------------------
	/*
	 * Gets all clients whose palettes are currently in process of ENTERING the warehouse
	 */
	@GetMapping("allByDivisionIdAndPreparedPalettesIn/{divisionId}")
	public ResponseEntity <Object> allByDivisionIdAndPreparedPalettesIn (@PathVariable Integer divisionId) {
		try {
			responseData = model.allByDivisionIdAndPreparedPalettesIn(divisionId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/*
	 * Gets all clients whose palettes are currently in process of LEAVING the warehouse
	 */
	@GetMapping("allByDivisionIdAndPreparedPalettesOut/{divisionId}")
	public ResponseEntity <Object> allByDivisionIdAndPreparedPalettesOut (@PathVariable Integer divisionId) {
		try {
			responseData = model.allByDivisionIdAndPreparedPalettesOut(divisionId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
}
