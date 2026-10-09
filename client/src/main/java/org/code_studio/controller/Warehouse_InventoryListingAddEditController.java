package org.code_studio.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;

import java.time.LocalDateTime;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSComboBoxEnumerator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.InventoryListing;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;


public class Warehouse_InventoryListingAddEditController extends BaseController {

	@FXML CSTextField tfWarehouseId;
	@FXML CSComboBoxEnumerator cbEnumerator;
	@FXML CSTextField tfCountingId;
	@FXML CSComboBox<String> cbStatus;
	@FXML CSDialogButtons dialogButtons;
	
	private int mode = 0;
	@SuppressWarnings("unused")
	private ApplicationContext ctx;
	private CSTable<InventoryListing> tblInventoryListing;
	private CSRestService<InventoryListing> rsInventoryListing;
	private InventoryListing inventoryListing;
	
	@SuppressWarnings("unchecked")
	public Warehouse_InventoryListingAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.ctx = ctx;
		tblInventoryListing = (CSTable<InventoryListing>) controllerParam;
	}
	
	public void initialize() {
		try {
			rsInventoryListing = new CSRestService<InventoryListing>("/inventoryListing");
			
			if (mode == 0) {
				inventoryListing = new InventoryListing();
			} else {
				inventoryListing = tblInventoryListing.getSelectedItem();
				tfWarehouseId.setText(String.valueOf(inventoryListing.getWarehouseId()));
				cbEnumerator.selectByItemId(inventoryListing.getEnumeratorUserId());
				tfCountingId.setText(String.valueOf(inventoryListing.getCountingNbr()));
				cbStatus.getSelectionModel().select(inventoryListing.getStatusId() - 1);
				
				tfWarehouseId.setDisable(true);
				tfCountingId.setDisable(true);
				cbStatus.setDisable(false);
			}
			
			dialogButtons.setValidation(new CSEmptyFieldValidator(tfWarehouseId), new CSEmptyFieldValidator(tfCountingId));
			dialogButtons.getSaveButton().setOnAction( e-> {
				inventoryListing.setCountingNbr(tfCountingId.getTextAsInteger());
				inventoryListing.setCreatedByUserId(1); // TODO add proper user
				inventoryListing.setEnumeratorUserId(cbEnumerator.getSelectionModel().getSelectedItem().getId().intValue());
				inventoryListing.setStatusId(cbStatus.getSelectionModel().getSelectedIndex() + 1);
				inventoryListing.setWarehouseId(tfWarehouseId.getTextAsInteger());
				inventoryListing.setCreatedDate(LocalDateTime.now());
				
				InventoryListing insertedItem = rsInventoryListing.addOrUpdate(inventoryListing);
				rsInventoryListing.setUrl("/inventoryListing/" + insertedItem.getId());
				rsInventoryListing.fetch(new ParameterizedTypeReference<JsonResponse<InventoryListing>>(){});
				insertedItem = rsInventoryListing.getDataAsObservableList().get(0);
				
				//da bi updateovao i ova polja, mora refresh na ovaj nacin jer polja snima kao ID, a prikazuje kao NAME
				inventoryListing.setStatus(insertedItem.getStatus());
				inventoryListing.setEnumeratorUser(insertedItem.getEnumeratorUser());
				
				if(mode == 0) {
					tblInventoryListing.addItem(insertedItem);
				}
				
				dialogButtons.closeForm();
			});
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
	
	
}
