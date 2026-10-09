package org.code_studio.controller;

import org.code_studio.database.WarehouseTotals;
import org.code_studio.model.WarehouseTotalsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.warehouseTotals}")
public class WarehouseTotalsController extends BaseController <WarehouseTotals> {

	@Autowired 
	WarehouseTotalsModel model;
	
	public WarehouseTotalsController (WarehouseTotalsModel model) {
		super(model);
		this.model = model;
	}
	
	
	@Override
	@GetMapping("")
	public ResponseEntity <Object> findAll () {
		try {
			responseData = model.findAll();
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
