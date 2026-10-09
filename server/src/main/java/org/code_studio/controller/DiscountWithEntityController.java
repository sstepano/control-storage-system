package org.code_studio.controller;

import org.code_studio.database.DiscountWithEntity;
import org.code_studio.model.DiscountWithEntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.discountWithEntity}")
public class DiscountWithEntityController extends BaseController <DiscountWithEntity> {

	@Value("${server.pageSize}")
	private int pageSize;
	
	@Autowired DiscountWithEntityModel model;
	
	@SuppressWarnings("unused")
	private final JpaRepository <DiscountWithEntity, Integer> repository;
	
	public DiscountWithEntityController (DiscountWithEntityModel model) {
		super(model);
		this.model = model;
		this.repository = super.getRepository();
	}

	@GetMapping("")
	public ResponseEntity <Object> findAll() {
		try {
			responseData = model.findAll();
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("/allByClientId/{clientId}")
	public ResponseEntity <Object> findAllByClientId(@PathVariable Integer clientId) {
		try {
			responseData = model.findAllByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
