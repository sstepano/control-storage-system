package org.code_studio.controller;

import org.code_studio.database.ItemWarehouse;
import org.code_studio.model.ItemWarehouseModel;
import org.code_studio.model.ItemWarehouseModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemWarehouse}")
public class ItemWarehouseController extends BaseController <ItemWarehouse> {
	
	//TODO: NABUDZ JER JE DEFAULT VREDNOST SUVISE MALA
	//@Value("${server.pageSize}")
	private int pageSize=50;

	@Autowired ItemWarehouseModel model;
	@Autowired ItemWarehouseModelPageable pageableModel;
	
	public ItemWarehouseController (ItemWarehouseModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageable/{pageId}")
	public ResponseEntity <Object> allPageable (@PathVariable Integer pageId) {
		try {
			responseData = pageableModel.findAllPageable(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByWarehouseIdPageable/{warehouseId}/{pageId}")
	public ResponseEntity <Object> findAllByWarehouseId (@PathVariable Integer warehouseId, @PathVariable Integer pageId) {
		try {
			responseData = pageableModel.findAllByWarehouseId(warehouseId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByWarehouseIdAndItemId/{warehouseId}/{itemId}")
	public ResponseEntity <Object> allPageableByWarehouseIdAndItemId (@PathVariable Integer warehouseId, @PathVariable Integer itemId) {
		try {
			responseData = model.findAllByWarehouseIdAndItemId(warehouseId, itemId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByItemId/{itemId}")
	public ResponseEntity <Object> allByItemId (@PathVariable Integer itemId) {
		try {
			responseData = model.findAllByItemIdOrderByWarehouseId(itemId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByWarehouseIdAndItemIdAndRowIdAndShelfIdAndVerticalId/{warehouseId}/{itemId}/{rowId}/{shelfId}/{verticalId}")
	public ResponseEntity <Object> allByWarehouseIdAndItemIdAndRowIdAndShelfIdAndVerticalId (@PathVariable Integer warehouseId, @PathVariable Integer itemId,
			@PathVariable String rowId, @PathVariable String shelfId, @PathVariable String verticalId) {
		try {
			responseData = model.findAllByWarehouseIdAndItemIdAndRowIdAndShelfIdAndVerticalId(warehouseId, itemId, rowId, shelfId, verticalId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByWarehouseIdAndItemIdOrItemName/{warehouseId}/{itemIdOrItemName}/{pageId}")
	public ResponseEntity <Object> allByWarehouseIdAndItemIdOrItemName (
			@PathVariable Integer warehouseId, 
			@PathVariable String itemIdOrItemName, 
	        @PathVariable Integer pageId) {
		try {
			responseData = pageableModel.findAllByWarehouseIdAndItemIdOrItemName(warehouseId, itemIdOrItemName, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("sumByItemId/{itemId}")
	public ResponseEntity <Object> sumByItemId (@PathVariable Integer itemId) {
		try {
			responseData = model.findSumByItemId(itemId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@PostMapping("appendItemQtyByItemWarehouseIdAndItemId/{warehouseId}/{itemId}/{itemQty}")
	public ResponseEntity <Object> appendItemQtyByItemWarehouseIdAndItemId (@PathVariable String warehouseId, @PathVariable String itemId, @PathVariable String itemQty) {
		try {
			responseData = model.appendItemQtyByWarehouseIdAndItemId(Integer.parseInt(warehouseId), Integer.parseInt(itemId), Integer.parseInt(itemQty));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
}
