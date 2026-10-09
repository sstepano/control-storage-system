package org.code_studio.controller;

import org.code_studio.database.OrdersDetail;
import org.code_studio.model.OrdersDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.ordersDetail}")
public class OrdersDetailController extends BaseController <OrdersDetail> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	OrdersDetailModel model;

	
	public OrdersDetailController (OrdersDetailModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByOrderId/{orderId}")
	public ResponseEntity <Object> findAllPageable (@PathVariable Integer orderId) {
		try {
			responseData = model.findAllByOrderId(orderId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allPreviousOrdersByItemId/{itemId}")
	public ResponseEntity <Object> findAllPreviousOrdersByItemId (@PathVariable Integer itemId) {
		try {
			responseData = model.findAllPreviousOrdersByItemId(itemId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("sumQtyByItemId/{itemId}")
	public ResponseEntity <Object> findSumQtyByItemId (@PathVariable Integer itemId) {
		try {
			responseData = model.findSumQtyByItemId(itemId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
