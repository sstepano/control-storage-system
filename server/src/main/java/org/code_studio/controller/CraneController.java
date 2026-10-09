package org.code_studio.controller;

import org.code_studio.database.Crane;
import org.code_studio.model.CraneModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.crane}")
public class CraneController extends BaseController <Crane> {

	@Autowired
	CraneModel model;
	
	public CraneController (CraneModel model) {
		super(model);
		this.model = model;
	}
	
	
	@GetMapping("findAllActive")
	public ResponseEntity <Object> findAllActive () {
		try {
			responseData = model.findAllByIsActive(1);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
