package org.code_studio.controller;

import org.code_studio.database.Client;
import org.code_studio.model.SupplierModel;
import org.code_studio.model.SupplierModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.supplier}")
public class SupplierController extends BaseController <Client> {
	
	@Value("${server.pageSize}")
	private int pageSize;

	@Autowired
	SupplierModel model;
	
	@Autowired
	SupplierModelPageable modelPageable;
	
	public SupplierController (SupplierModel model) {
		super(model);
		this.model = model;
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
	
	@GetMapping("allPageableByDivisionId/{divisionId}/{pageId}")
	public ResponseEntity <Object> allPageableByDivisionId (@PathVariable Integer divisionId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findByDivisionId(divisionId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByDivisionIdWithPageSize/{divisionId}/{pageId}/{pageSize}")
	public ResponseEntity <Object> allPageableByDivisionIdWithPageSize (@PathVariable Integer divisionId, @PathVariable Integer pageId, @PathVariable Integer pageSize) {
		try {
			responseData = modelPageable.findByDivisionId(divisionId, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/* Ovo je, rekao bih, samo za kupce a ne i za dobavljace. Koristi se za dobanovce magacin
	@GetMapping("allPageableByDivisionIdAndHasQtyWithPageSize/{divisionId}/{hasQty}/{pageId}/{pageSize}")
	public ResponseEntity <Object> allPageableByDivisionIdAndHasQtyWithPageSize (@PathVariable Integer divisionId, @PathVariable Boolean hasQty
			, @PathVariable Integer pageId, @PathVariable Integer pageSize) {
		try {
			responseData = modelPageable.findByDivisionIdAndHasQty(divisionId, hasQty, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	*/

	
	/*
	@GetMapping("supplier/basicinfo/{pageId}")
	public ResponseEntity <Object> getAllSuppliers (@PathVariable Integer pageId) {
		try {
			responseData = mdlSupplier.findAllSuppliers(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	*/
	
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
	
	/*
	@GetMapping("getIdAndName/{clientId}")
	public ResponseEntity <Object> getIdAndName (@PathVariable Long clientId) {
		try {
			responseData = model.findAllById(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allBySaleOfficerId/{saleOfficerId}/{pageId}")
	public ResponseEntity <Object> allBySaleOfficerId (
				@PathVariable Long saleOfficerId, @PathVariable Integer pageId) {
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
	 *
	@GetMapping("allBySaleOfficerIdWithPageSize/{saleOfficerId}/{pageSize}/{pageId}")
	public ResponseEntity <Object> allBySaleOfficerIdWithPageSize (
				@PathVariable Long saleOfficerId, @PathVariable Integer pageId, @PathVariable Integer pageSize) {
		try {
			responseData = modelPageable.findBySaleOfficerId(saleOfficerId, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	*/

}
