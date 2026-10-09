package org.code_studio.controller;

import org.code_studio.database.Discount;
import org.code_studio.model.DiscountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.discount}")
public class DiscountController extends BaseController <Discount> {

	@Value("${server.pageSize}")
	private int pageSize;
	
	@Autowired DiscountModel model;
	
	@SuppressWarnings("unused")
	private final JpaRepository <Discount, Integer> repository;
	
	public DiscountController (DiscountModel model) {
		super(model);
		this.model = model;
		this.repository = super.getRepository();
	}
	

	/*
	@GetMapping("")
	public ResponseEntity <Object> findAll() {
		try {
			responseData = model.findAll();
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	*/

}
