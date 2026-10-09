package org.code_studio.controller;

import org.code_studio.database.SalePlannerReview;
import org.code_studio.model.SalePlannerReviewModel;
import org.code_studio.model.SalePlannerReviewModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.salePlannerReview}")
public class SalePlannerReviewController extends BaseController <SalePlannerReview> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	SalePlannerReviewModel model;
	
	@Autowired
	SalePlannerReviewModelPageable modelPageable;
	
	public SalePlannerReviewController (SalePlannerReviewModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allBySaleOfficerIdAndClientIdAndDateRange/{saleOfficerId}/{clientId}/{month}/{year}/{part}")
	public ResponseEntity <Object> allBySaleOfficerIdAndClientIdAndDateRange (
			  @PathVariable Integer saleOfficerId,
			  @PathVariable Integer clientId,
			  @PathVariable String month,
			  @PathVariable String year,
			  @PathVariable Integer part
			){
		try {
			responseData = model.findBySaleOfficerIdAndClientIdAndDateRange(saleOfficerId, clientId, month, year, part);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByDateRange/{startDate}/{endDate}/{saleOfficerId}")
	public ResponseEntity <Object> allByDateRange (
			  @PathVariable Integer saleOfficerId,
			  @PathVariable String startDate,
			  @PathVariable String endDate
			){
		try {
			responseData = model.findByDateRange(startDate, endDate, saleOfficerId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableBySaleOfficerIdAndDateRange/{saleOfficerId}/{month}/{year}/{pageId}")
	public ResponseEntity <Object> allBySaleOfficerIdAndDateRange (
			  @PathVariable Integer saleOfficerId,
			  @PathVariable String month,
			  @PathVariable String year,
			  @PathVariable Integer pageId
			){
		try {
			responseData = modelPageable.findBySaleOfficerIdAndDateRange(saleOfficerId, month, year, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
