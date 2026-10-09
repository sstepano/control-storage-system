package org.code_studio.controller;

import org.code_studio.database.InventoryListingDetailSum;
import org.code_studio.model.InventoryListingDetailSumModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.inventoryListingDetailSum}")
public class InventoryListingDetailSumController extends BaseController <InventoryListingDetailSum> {

	@Value("${server.pageSize}")
	private int pageSize=50;
	
	@Autowired
	public InventoryListingDetailSumModel model;
	
	public InventoryListingDetailSumController (InventoryListingDetailSumModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByDiffAndWarehouseIdAndItemId/{diff}/{warehouseId}/{itemId}")
	public ResponseEntity <Object> allByDiffOnlyAndWarehouseIdAndItemId (@PathVariable int diff
			, @PathVariable int warehouseId, @PathVariable int itemId) {
		try {
			if (diff != 2) { // nije nepopisano, vec RAZLIKE ili SVE
				responseData = model.findAllByDiffAndWarehouseIdAndItemId(diff, warehouseId, itemId);
			} else { // NEPOPISANO
				responseData = model.findAllUnlistedByWarehouseIdAndItemId(warehouseId, itemId);
			}
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	// Used in inventory listing verification reports
	@GetMapping("allByDifferenceIndicator/{differenceIndicator}")
	public ResponseEntity <Object> allByDifferenceIndicator (@PathVariable int differenceIndicator) {
		try {
			responseData = model.findAllByDifferenceIndicator(differenceIndicator);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
