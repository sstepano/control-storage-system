package org.code_studio.controller;

import org.code_studio.database.SalePlanner;
import org.code_studio.model.SalePlannerModel;
import org.code_studio.model.SalePlannerModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.salePlanner}")
public class SalePlannerController extends BaseController <SalePlanner> {
	
	@Value("${server.pageSize}")
	private int pageSize;

	@Autowired
	SalePlannerModel model;
	
	@Autowired
	SalePlannerModelPageable modelPageable;
	

	public SalePlannerController (SalePlannerModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageable/{pageId}")
	public ResponseEntity <Object> allPageable (@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageable(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> allByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientId(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allPageableByClientIdAndSaleOfficerId/{clientId}/{saleOfficerId}/{pageId}")
	public ResponseEntity <Object> allByClientIdAndSaleOfficerIdOrderByPlanDateDesc (@PathVariable Integer clientId, @PathVariable Integer saleOfficerId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientIdAndSaleOfficerIdOrderByEventDateDesc(clientId, saleOfficerId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allPageableBySaleOfficerId/{saleOfficerId}/{pageId}")
	public ResponseEntity <Object> allBySaleOfficerId (@PathVariable Integer saleOfficerId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllBySaleOfficerId(saleOfficerId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableBySaleOfficerIdOrderByPlanDateDesc/{saleOfficerId}/{pageId}")
	public ResponseEntity <Object> allPageableBySaleOfficerIdOrderByPlanDateDesc (
				  @PathVariable Integer saleOfficerId
				, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllBySaleOfficerIdOrderByPlanDateDesc(saleOfficerId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableBySaleOfficerIdRefKomTermin/{saleOfficerId}/{pageId}")
	public ResponseEntity <Object> allBySaleOfficerIdRefKomTermin (
				  @PathVariable Integer saleOfficerId
				, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllBySaleOfficerIdRefKomTermin(saleOfficerId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByPlanDate/{planDate}/{pageId}")
	public ResponseEntity <Object> allByPlanDate (
				  @PathVariable String  planDate
				, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByPlanDate(planDate, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableBySaleOfficerIdAndEventDate/{saleOfficerId}/{eventDateMonth}/{eventDateYear}/{pageId}")
	public ResponseEntity <Object> allBySaleOfficerIdAndEventDate (
				  @PathVariable Integer saleOfficerId
				, @PathVariable Integer eventDateMonth
				, @PathVariable Integer eventDateYear
				, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllBySaleOfficerIdAndEventDate(saleOfficerId, eventDateMonth, eventDateYear, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allPageableByContactedById/{contactedById}/{pageId}")
	public ResponseEntity <Object> allByContactedById (@PathVariable Integer contactedById, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByContactedByIdOrderByPlanDateDesc(contactedById, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableBySaleOfficerIdAndPlanDate/{saleOfficerId}/{planDate}/{pageId}")
	public ResponseEntity <Object> allBySaleOfficerIdAndPlanDate (
			      @PathVariable Integer saleOfficerId
				, @PathVariable String  planDate
				, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllBySaleOfficerIdAndPlanDate(saleOfficerId, planDate, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableOrderByEventDate/{pageId}")
	public ResponseEntity <Object> allOrderByEventDate (@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllOrderByEventDateDesc(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
