package org.code_studio.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

import java.time.LocalDateTime;
import java.util.List;

import org.code_studio.component.CSBarcodeReader;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.InventoryListing;
import org.code_studio.database.InventoryListingDetail;
import org.code_studio.database.Item;
import org.code_studio.database.ItemBarcode;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;


public class Warehouse_InventoryListingDetailAddEditController extends BaseController {
	@FXML CSTextField tfEnumeratorName;
	@FXML CSTextField tfWarehouseId;
	@FXML CSTextField tfRowId;
	@FXML CSTextField tfShelfId;
	@FXML CSTextField tfVerticalId;
	@FXML CSTextField tfBarcode;
	@FXML CSTextField tfItemCode;
	@FXML CSTextField tfCountedQty;
	@FXML CheckBox ckbAcceptedCounting;
	@FXML CSDialogButtons dialogButtons;
	
	private int mode = 0;
	@SuppressWarnings("unused")
	private ApplicationContext ctx;
	
	private InventoryListing inventoryListing;
	private CSTable<InventoryListing> tblInventoryListing;
	
	private InventoryListingDetail inventoryListingDetail;
	private CSTable<InventoryListingDetail> tblInventoryListingDetail;
	private CSRestService<InventoryListingDetail> rsInventoryListingDetail;
	
	private final String urlItemBarcode = "/itemBarcode/allPageableByBarcode/";
	private CSRestService<ItemBarcode> rsItemBarcode;
	
	private final String urlBarcodeByItem = "/itemBarcode/allPageableByItemId/";
	private CSRestService<ItemBarcode> rsBarcodeByItem;
	
	private final String urlItemWarehouse = "/itemWarehouse/allByWarehouseIdAndItemIdAndRowIdAndShelfIdAndVerticalId/";
	private CSRestService<ItemWarehouse> rsItemWarehouse;
	
	private final String urlItemWarehouseUpdate = "/itemWarehouse";
	private CSRestService<ItemWarehouse> rsItemWarehouseUpdate;
	
	private boolean tfItemCodeRequestFocus = false;
	
	/* Ako je novo brojanje, countedQty je null i kada user unese vrednosti za ovakav slog,
	 * moramo da sakrijemo row, tako da mu u gridu ostane samo ono sto preostaje da uradi	
	 */
	private boolean isNewEnumeration = false; 
	
	private final String urlItem = "/item/findByCode/";
	private CSRestService<Item> rsItem;
	
	/**
	 * Populated when user enters in tfItemCode item code which exists in the database, otherwise NULL
	 */
	private Item foundItem;
	
	@SuppressWarnings("unchecked")
	public Warehouse_InventoryListingDetailAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.ctx = ctx;
		List<Object> lstControllerParam = (List<Object>) controllerParam;
		tblInventoryListing = (CSTable<InventoryListing>) lstControllerParam.get(0);
		tblInventoryListingDetail = (CSTable<InventoryListingDetail>) lstControllerParam.get(1);
		inventoryListing = tblInventoryListing.getSelectedItem();
		isNewEnumeration = 
				tblInventoryListingDetail != null && 
				tblInventoryListingDetail.getSelectedItem() != null && 
				tblInventoryListingDetail.getSelectedItem().getCountedQty() == null;
	}

	public void initialize() {
		try {
			rsInventoryListingDetail = new CSRestService<InventoryListingDetail>("/inventoryListingDetail");
			rsItemBarcode = new CSRestService<ItemBarcode>(urlItemBarcode);
			rsBarcodeByItem = new CSRestService<ItemBarcode>(urlBarcodeByItem);
			rsItemWarehouse = new CSRestService<ItemWarehouse>(urlItemWarehouse);
			rsItemWarehouseUpdate = new CSRestService<ItemWarehouse>(urlItemWarehouseUpdate);
			rsItem = new CSRestService<Item>(urlItem);
			
			dialogButtons.setValidation(
				new CSEmptyFieldValidator(tfRowId),
				new CSEmptyFieldValidator(tfItemCode),
				new CSEmptyFieldValidator(tfCountedQty)
			);
			
			if (mode == 0) {
				inventoryListingDetail = new InventoryListingDetail();
			} else {
				inventoryListingDetail = tblInventoryListingDetail.getSelectedItem();
				foundItem = inventoryListingDetail.getItem();
				tfRowId.setText(inventoryListingDetail.getRowId());
				tfShelfId.setText(inventoryListingDetail.getShelfId());
				tfVerticalId.setText(inventoryListingDetail.getVerticalId());
				tfBarcode.setText(inventoryListingDetail.getItemBarcode());
				tfItemCode.setText(inventoryListingDetail.getItem().getCode());
				tfCountedQty.setText(inventoryListingDetail.getCountedQty() == null ? "" : inventoryListingDetail.getCountedQty().toString());
				ckbAcceptedCounting.setSelected(inventoryListingDetail.getAccepted());
			}
			
			if (mode == 2) { // admin edit na verification strani
				ckbAcceptedCounting.setVisible(true);
				ckbAcceptedCounting.setManaged(true);
				tfRowId.setDisable(true);
				tfShelfId.setDisable(true);
				tfVerticalId.setDisable(true);
				tfBarcode.setDisable(true);
				inventoryListing = inventoryListingDetail.getInventoryListing(); // fix za accepted counting mode
			}
			
			tfEnumeratorName.setText(inventoryListing.getEnumeratorUsername());
			tfWarehouseId.setText(inventoryListing.getWarehouseName());
			
			// setOnAction je isto sto i on Enter pressed
			tfItemCode.setOnAction( e-> {
				rsItem.setUrl(urlItem + tfItemCode.getText());
				rsItem.fetch(new ParameterizedTypeReference<JsonResponse<Item>>() {});
				
				if (!rsItem.getDataAsObservableList().isEmpty()) {
					foundItem = rsItem.getDataAsObservableList().getFirst();
					rsBarcodeByItem.setUrl(urlBarcodeByItem + foundItem.getId() + "/0");
					rsBarcodeByItem.fetch(new ParameterizedTypeReference<JsonResponse<ItemBarcode>>() {});
					if (!rsBarcodeByItem.getDataAsObservableList().isEmpty()) {
						tfBarcode.setText(rsBarcodeByItem.getDataAsObservableList().getFirst().getBarcode());
					}
					
					Robot eventRobot = new Robot();
		        	eventRobot.keyPress(KeyCode.TAB);
		        	eventRobot.keyRelease(KeyCode.TAB);
				} else {
					foundItem = null;
					Common.ShowNotification("GREŠKA", "Nepostojeća šifra artikla!!!", true);
					tfItemCode.selectAll();
				}
			});
			
			tfItemCode.setOnMouseClicked( e-> {
				tfItemCodeRequestFocus = true;
			});
			
			tfBarcode.setOnMouseClicked( e-> {
				tfItemCodeRequestFocus = false;
			});
			
			dialogButtons.getSaveButton().setOnAction( e-> {
				inventoryListingDetail.setInventoryListing(inventoryListing);
				inventoryListingDetail.setItemId(foundItem.getId());
				inventoryListingDetail.setRowId(tfRowId.getText());
				inventoryListingDetail.setShelfId(tfShelfId.getText());
				inventoryListingDetail.setVerticalId(tfVerticalId.getText());
				inventoryListingDetail.setCountedQty(tfCountedQty.getTextAsInteger());
				inventoryListingDetail.setCreatedByUserId(1);
				inventoryListingDetail.setCreatedDate(LocalDateTime.now());
				inventoryListingDetail.setAccepted(ckbAcceptedCounting.isSelected());
				
				if (ckbAcceptedCounting.isSelected()) {
					//Create transfer from and to the same warehouse?
					//ASK!
					
					//Change item qty to the one that admin accepted
					rsItemWarehouse.setUrl(urlItemWarehouse 
						+ inventoryListing.getWarehouseId() + "/"
						+ foundItem.getId() + "/"
						+ inventoryListingDetail.getRowId() + "/"
						+ inventoryListingDetail.getShelfId() + "/"
						+ inventoryListingDetail.getVerticalId()
					);
					rsItemWarehouse.fetch(new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>() {});
					ItemWarehouse iw = rsItemWarehouse.getDataAsObservableList().isEmpty()
						? null
						: rsItemWarehouse.getDataAsObservableList().getFirst();
					
					if (iw != null) {
						iw.setQty(inventoryListingDetail.getCountedQty());
						rsItemWarehouseUpdate.addOrUpdate(iw);
					} else {
						ckbAcceptedCounting.setSelected(false);
						String msg = "Artikal nije pronađen na datoj poziciji u magacinu!";
						Common.ShowNotification("GREŠKA", msg, true);
						throw new IllegalArgumentException(msg);
					}
				}
				
				InventoryListingDetail insertedItem = rsInventoryListingDetail.addOrUpdate(inventoryListingDetail);
				if (insertedItem != null) {
					rsInventoryListingDetail.setUrl("/inventoryListingDetail/" + insertedItem.getId());
					rsInventoryListingDetail.fetch(new ParameterizedTypeReference<JsonResponse<InventoryListingDetail>>(){});
					insertedItem = rsInventoryListingDetail.getDataAsObservableList().get(0);
					if(mode == 0) {
						tblInventoryListingDetail.addItem(insertedItem);
					} else if (mode == 1 && isNewEnumeration == true) {
						tblInventoryListingDetail.tableView.getItems().remove(tblInventoryListingDetail.getSelectedItem());
					}
				} else {
					Common.ShowNotification("GREŠKA", "Nepostojeća šifra artikla!", true);
				}
				
				if (mode == 0) {
					resetForm(); // we do not want to close form, but to clear it so user can continue adding items
				} else { //edit
					dialogButtons.closeForm();
				}
				
			});
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
	
	@Override
	public void postInitialize() {
		CSBarcodeReader barcodeReader = new CSBarcodeReader(tfBarcode, false);
		barcodeReader.setBindGlobalKeys(false);
		barcodeReader.register();
		barcodeReader.onBarcodeEntered( barcode -> {
			if (barcode.length() > 0) {
				rsItemBarcode.setUrl(urlItemBarcode + barcode + "/0");
				rsItemBarcode.fetch(new ParameterizedTypeReference<JsonResponse<ItemBarcode>>() {});
				if (!rsItemBarcode.getDataAsObservableList().isEmpty()) {
					tfItemCode.setText(rsItemBarcode.getDataAsObservableList().getFirst().getItemId().toString());
				}
			}
		});
	}
	
	public void resetForm() {
		tfBarcode.clear();
		tfItemCode.clear();
		tfCountedQty.clear();
		rsInventoryListingDetail = new CSRestService<InventoryListingDetail>("/inventoryListingDetail");
		
		if (tfItemCodeRequestFocus) {
			tfItemCode.requestFocus();
		} else {
			tfBarcode.requestFocus();
		}
	}
	
}
