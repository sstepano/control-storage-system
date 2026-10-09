package org.code_studio.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;

import org.code_studio.database.Client;
import org.code_studio.database.SupplierPricelist;
import org.code_studio.database.Item;

import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

public class Items_ComparativePricelistPerSupplierAddEditController extends BaseController {

	@FXML Button btnSave;
	@FXML Button btnCancel;

	@FXML CSTextField tfClientId;
	@FXML CSTextField tfClientName;
	@FXML CSTextField tfItemId;
	@FXML CSTextField tfItemName;
	@FXML CSTextField tfSupplierItemCode;
	@FXML CSTextField tfPurchaseAmt;
	@FXML CSTextField tfPurchaseAmtSpec;
	@FXML CSTextField tfMinQty;
	@FXML Button      btnItemList;
	
	private int mode;

	private CSTable<SupplierPricelist> tblSupplierPricelist;
	private SupplierPricelist supplierPricelist;
	private CSRestService<SupplierPricelist> rsSupplierPricelistAddUpdate;
	private final String urlSupplierPricelistAddUpdate = "/supplierPricelist";
	
	private CSRestService<Client> rsClient;
	private final String urlClient = "/client/";
	
	private Client client;
	private Item selectedItem;
	
	@SuppressWarnings("unchecked")
	public Items_ComparativePricelistPerSupplierAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.tblSupplierPricelist = (CSTable<SupplierPricelist>) controllerParam;
		this.client = (Client) this.tblSupplierPricelist.getParentTable().getSelectedItem();
	}


	public void initialize() {
		rsSupplierPricelistAddUpdate = new CSRestService<>(urlSupplierPricelistAddUpdate);
		
		rsClient = new CSRestService<>(urlClient);
		
		if (mode == 0) {
			supplierPricelist = new SupplierPricelist();
			supplierPricelist.setClientId(client.getId().intValue());
			
			tfClientId.setText(client.getId().toString());
			tfClientName.setText(client.getName());
		} else if (mode == 1) {
	    	btnSave.setDisable(false);

	    	supplierPricelist = tblSupplierPricelist.getSelectedItem();
			tfItemId.setText(supplierPricelist.getItem().getId().toString());
			tfItemName.setText(supplierPricelist.getItem().getName());
			
			//fetch client from saved item
			rsClient.setUrl(urlClient + supplierPricelist.getClientId());
			rsClient.fetch(new ParameterizedTypeReference<JsonResponse<Client>>() {});
			Client client = rsClient.getData() != null 
					? rsClient.getData().get(0)
					: null;
			
			if (client != null) {
				tfClientId.setText(client.getId().toString());
				tfClientName.setText(client.getName());
			} else {
				tfClientId.setText(null);
				tfClientName.setText(null);
				btnSave.setDisable(true);
			}
			
			tfSupplierItemCode.setText(supplierPricelist.getSupplierItemCode());
			tfPurchaseAmt.setText(supplierPricelist.getPurchaseAmt().toString());
			tfPurchaseAmtSpec.setText(supplierPricelist.getPurchaseAmtSpec().toString());
			tfMinQty.setText(supplierPricelist.getMinQty().toString());
		} 
		
		btnItemList.setOnAction( _ -> {
			selectedItem = (Item) Common.displayForm(ControllerFactory.getController("Items_ItemSelectAddEditController", this.client, 2), btnItemList, "Odabir artikla");
			if (selectedItem != null) {
				tfItemId.setText(selectedItem.getId().toString());
				tfItemName.setText(selectedItem.getName());
				supplierPricelist.setItem(selectedItem);
			}
		});
		
		
		tfItemName.onTextChanged( (_, _) -> {
		    if (tfItemName.getText().length() > 0) {
		    	btnSave.setDisable(false);
		    } else {
		    	btnSave.setDisable(true);
		    }
		});
		

		btnSave.setOnAction( e-> {
			if (supplierPricelist.getItem() != null) {
				//set previous values
				supplierPricelist.setPurchaseAmtPrevious(supplierPricelist.getPurchaseAmt());
				supplierPricelist.setPurchaseAmtSpecPrevious(supplierPricelist.getPurchaseAmtSpec());
				supplierPricelist.setMinQtyPrevious(supplierPricelist.getMinQty());
				
				supplierPricelist.setSupplierItemCode(tfSupplierItemCode.getText());
				supplierPricelist.setPurchaseAmt(tfPurchaseAmt.getTextAsBigDecimal());
				supplierPricelist.setPurchaseAmtSpec(tfPurchaseAmtSpec.getTextAsBigDecimal());
				supplierPricelist.setMinQty(tfMinQty.getTextAsInteger());

				SupplierPricelist insertedSupplierPricelist = rsSupplierPricelistAddUpdate.addOrUpdate(supplierPricelist);
				
				if (mode == 0) {
					this.tblSupplierPricelist.addItem(insertedSupplierPricelist);
				}
			}
			
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();

		});
		
		btnCancel.setOnAction(e->{
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

	}
	
}
