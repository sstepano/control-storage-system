package org.code_studio.controller;

import org.code_studio.database.DiscountGroup;
import org.code_studio.model.DiscountGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.discountGroup}")
public class DiscountGroupController extends BaseController <DiscountGroup> {

	@Value("${server.pageSize}")
	private int pageSize;
	
	@Autowired DiscountGroupModel model;
	
	@SuppressWarnings("unused")
	private final JpaRepository <DiscountGroup, Integer> repository;
	
	public DiscountGroupController (DiscountGroupModel model) {
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
