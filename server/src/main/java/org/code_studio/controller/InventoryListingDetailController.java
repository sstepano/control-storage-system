package org.code_studio.controller;

import org.code_studio.database.InventoryListingDetail;
import org.code_studio.model.InventoryListingDetailModel;
import org.code_studio.model.InventoryListingDetailModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.inventoryListingDetail}")
public class InventoryListingDetailController extends BaseController <InventoryListingDetail> {

	@Value("${server.pageSize}")
	private int pageSize=50;
	
	@Autowired
	public InventoryListingDetailModel model;
	
	@Autowired
	InventoryListingDetailModelPageable modelPageable;
	
	public InventoryListingDetailController (InventoryListingDetailModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageableByInventoryListingId/{inventoryListingId}/{pageId}")
	public ResponseEntity <Object> findAllByInventoryListingId (@PathVariable int inventoryListingId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByInventoryListingIdOrderByIdDesc(inventoryListingId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("search/{inventoryListingId}/{searchKeyword}/{pageId}")
	public ResponseEntity <Object> search (@PathVariable int inventoryListingId, @PathVariable String searchKeyword, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.search(inventoryListingId, searchKeyword, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByWarehouseIdAndEnumeratorId/{warehouseId}/{enumeratorId}/{pageId}")
	public ResponseEntity <Object> allPageableByWarehouseIdAndEnumeratorId (@PathVariable int warehouseId, @PathVariable int enumeratorId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByWarehouseIdAndCreatedByUserId(warehouseId, enumeratorId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByItemIdAndRowIdAndShelfIdAndVerticalId/{itemId}/{rowId}/{shelfId}/{verticalId}")
	public ResponseEntity <Object> allByItemIdAndRowIdAndShelfIdAndVerticalId (@PathVariable int itemId, @PathVariable String rowId
			, @PathVariable String shelfId, @PathVariable String verticalId) {
		try {
			//responseData = model.findAllByItemIdAndRowIdAndShelfIdAndVerticalIdAndCountedQtyIsNotNull(itemId, rowId, shelfId, verticalId);
			responseData = model.findAllByItemIdAndRowIdAndShelfIdAndVerticalId(itemId, rowId, shelfId, verticalId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
