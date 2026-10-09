package org.code_studio.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.code_studio.database.SalePlannerMonthByDay;
import org.code_studio.database.SalePlannerReview;
import org.code_studio.model.SalePlannerMonthByDayModel;
import org.code_studio.model.SalePlannerReviewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.salePlannerMonthByDay}")
public class SalePlannerMonthByDayController extends BaseController <SalePlannerMonthByDay> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	SalePlannerReviewModel salePlannerModel;
	
	@Autowired
	SalePlannerMonthByDayModel salePlannerMonthByDayModel;
	
	public SalePlannerMonthByDayController (SalePlannerMonthByDayModel model) {
		super(model);
		this.salePlannerMonthByDayModel = model;
	}
	
	
	// Used in date range report
	@SuppressWarnings("unchecked")
	@GetMapping("allByDateRangeAndGroup/{startDate}/{endDate}/{saleOfficerId}")
	public ResponseEntity <Object> allByDateRangeAndGroup (
			  @PathVariable Integer saleOfficerId,
			  @PathVariable String startDate,
			  @PathVariable String endDate
			){
		try {
			responseData = salePlannerModel.findByDateRange(startDate, endDate, saleOfficerId);
			List<SalePlannerReview> lstSalePlannerReview = new ArrayList<>();
			lstSalePlannerReview.addAll((List<SalePlannerReview>)responseData);
			
			List<SalePlannerMonthByDay> lstMonthByDay = new ArrayList<>();
			SalePlannerMonthByDay mbd = null;
			String clientAction = "0000";
			
			for (SalePlannerReview spr : lstSalePlannerReview) {
				mbd = new SalePlannerMonthByDay();
				mbd.setId(0);
				mbd.setClientName(spr.getClientName());
				mbd.setClientCity(spr.getClientCity());
				mbd.setClientGroup(spr.getClientGroup());
				
				clientAction = getClientAction(spr);
				
				switch(spr.getDayNumber()) {
				  case 1:
					  mbd.setD1(clientAction);
				  break;
				  case 2:
					  mbd.setD2(clientAction);
				  break;
				  case 3:
					  mbd.setD3(clientAction);
				  break;
				  case 4:
					  mbd.setD4(clientAction);
				  break;
				  case 5:
					  mbd.setD5(clientAction);
				  break;
				  case 6:
					  mbd.setD6(clientAction);
				  break;
				  case 7:
					  mbd.setD7(clientAction);
				  break;
				  case 8:
					  mbd.setD8(clientAction);
				  break;
				  case 9:
					  mbd.setD9(clientAction);
				  break;
				  case 10:
					  mbd.setD10(clientAction);
				  break;
				  case 11:
					  mbd.setD11(clientAction);
				  break;
				  case 12:
					  mbd.setD12(clientAction);
				  break;
				  case 13:
					  mbd.setD13(clientAction);
				  break;
				  case 14:
					  mbd.setD14(clientAction);
				  break;
				  case 15:
					  mbd.setD15(clientAction);
				  break;
				  case 16:
					  mbd.setD16(clientAction);
				  break;
				  case 17:
					  mbd.setD17(clientAction);
				  break;
				  case 18:
					  mbd.setD18(clientAction);
				  break;
				  case 19:
					  mbd.setD19(clientAction);
				  break;
				  case 20:
					  mbd.setD20(clientAction);
				  break;
				  case 21:
					  mbd.setD21(clientAction);
				  break;
				  case 22:
					  mbd.setD22(clientAction);
				  break;
				  case 23:
					  mbd.setD23(clientAction);
				  break;
				  case 24:
					  mbd.setD24(clientAction);
				  break;
				  case 25:
					  mbd.setD25(clientAction);
				  break;
				  case 26:
					  mbd.setD26(clientAction);
				  break;
				  case 27:
					  mbd.setD27(clientAction);
				  break;
				  case 28:
					  mbd.setD28(clientAction);
				  break;
				  case 29:
					  mbd.setD29(clientAction);
				  break;
				  case 30:
					  mbd.setD30(clientAction);
				  break;
				  case 31:
					  mbd.setD31(clientAction);
				  break;
				}
				
				lstMonthByDay.add(mbd);
			}

			lstMonthByDay.sort(Comparator.comparing(SalePlannerMonthByDay::getClientGroup));
			return generateResponse(lstMonthByDay);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/**
	 * Calculates client action for display
	 * @return
	 */
	private String getClientAction(SalePlannerReview spr) {
		StringBuilder res = new StringBuilder("0000");
		if (spr.getIsCame()) {
			res.setCharAt(0, '1');
		} else if (spr.getIsCalled()) {
			res.setCharAt(1, '1');
		} else if (spr.getIsWentTo()) {
			res.setCharAt(2, '1');
		} else if (spr.getIsCalledTo()) {
			res.setCharAt(3, '1');
		}		
	
		return res.toString();
	}
	
}
