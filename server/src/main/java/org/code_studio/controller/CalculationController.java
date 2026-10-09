package org.code_studio.controller;

import org.code_studio.database.Calculation;
import org.code_studio.model.CalculationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.calculation}")
public class CalculationController extends BaseController <Calculation> {

	@Autowired
	CalculationModel model;
	
	public CalculationController (CalculationModel model) {
		super(model);
		this.model = model;
	}

	@GetMapping("allByClientId/{clientId}")
	public ResponseEntity <Object> findAllByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("findMaxCalculationIdByClientId/{clientId}")
	public ResponseEntity <Object> findMaxClientCalculationIdByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findTop1ClientCalculationIdByClientIdOrderByClientCalculationIdDesc(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@PostMapping("setIsActiveByCalculationId/{calculationId}")
	public ResponseEntity <Object> setIsActiveByCalculationId (@PathVariable String calculationId) {
		try {
			responseData = model.setIsActiveByCalculationId(Integer.parseInt(calculationId));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
}
