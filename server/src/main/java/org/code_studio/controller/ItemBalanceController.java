package org.code_studio.controller;

import org.code_studio.database.ItemBalance;
import org.code_studio.model.ItemBalanceModel;
import org.code_studio.model.ItemBalanceModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.itemBalance}")
public class ItemBalanceController extends BaseController <ItemBalance> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Autowired
	ItemBalanceModel model;
	
	@Autowired
	ItemBalanceModelPageable modelPageable;
	
	public ItemBalanceController (ItemBalanceModel model) {
		super(model);
		this.model = model;
	}

	
	@GetMapping("allPageableItemBalanceForCurrentYearByWarehouseIdAndItemId/{warehouseId}/{itemId}/{pageId}")
	public ResponseEntity <Object> allPageableItemBalanceForCurrentYearByItemId (@PathVariable Integer warehouseId, @PathVariable Integer itemId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.allPageableItemBalanceForCurrentYearByWarehouseIdAndItemId(warehouseId, itemId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableItemBalanceByWarehouseIdAndItemId/{warehouseId}/{itemId}/{pageId}")
	public ResponseEntity <Object> allPageableItemBalanceByItemId (@PathVariable Integer warehouseId, @PathVariable Integer itemId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.allPageableItemBalanceByWarehouseIdAndItemId(warehouseId, itemId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableTotalItemBalanceForCurrentYearByWarehouseIdAndItemId/{warehouseId}/{itemId}/{pageId}")
	public ResponseEntity <Object> allPageableTotalItemBalanceForCurrentYearByItemId (@PathVariable Integer warehouseId, @PathVariable Integer itemId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.allPageableTotalItemBalanceForCurrentYearByWarehouseIdAndItemId(warehouseId, itemId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableTotalItemBalanceByWarehouseIdAndItemId/{warehouseId}/{itemId}/{pageId}")
	public ResponseEntity <Object> allPageableTotalItemBalanceByItemId (@PathVariable Integer warehouseId, @PathVariable Integer itemId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.allPageableTotalItemBalanceByWarehouseIdAndItemId(warehouseId, itemId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
