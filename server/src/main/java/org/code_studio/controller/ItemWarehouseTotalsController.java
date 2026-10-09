package org.code_studio.controller;

import org.code_studio.database.ItemWarehouseTotals;
import org.code_studio.model.ItemWarehouseTotalsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemWarehouseTotals}")
public class ItemWarehouseTotalsController extends BaseController <ItemWarehouseTotals> {

	@Autowired ItemWarehouseTotalsModel model;
	
	public ItemWarehouseTotalsController (ItemWarehouseTotalsModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByWarehouseId/{warehouseId}")
	public ResponseEntity <Object> findAllByWarehouseId (@PathVariable Integer warehouseId) {
		try {
			responseData = model.findAllByWarehouseId(warehouseId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
