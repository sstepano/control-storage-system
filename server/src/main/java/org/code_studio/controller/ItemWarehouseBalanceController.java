package org.code_studio.controller;

import org.code_studio.database.ItemWarehouseBalance;
import org.code_studio.model.ItemWarehouseBalanceModel;
import org.code_studio.model.ItemWarehouseBalanceModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemWarehouseBalance}")
public class ItemWarehouseBalanceController extends BaseController <ItemWarehouseBalance> {

	@Value("${server.pageSize}")
	private int pageSize = 20;	
	
	@Autowired ItemWarehouseBalanceModel model;
	@Autowired ItemWarehouseBalanceModelPageable pageableModel;
	
	public ItemWarehouseBalanceController (ItemWarehouseBalanceModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageableByWarehouseId/{warehouseId}/{pageId}")
	public ResponseEntity <Object> findAllPageableByWarehouseId (@PathVariable Integer warehouseId, @PathVariable Integer pageId) {
		try {
			responseData = pageableModel.findAllPageableByWarehouseId(warehouseId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
