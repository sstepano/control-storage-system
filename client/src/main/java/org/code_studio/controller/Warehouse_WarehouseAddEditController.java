package org.code_studio.controller;

import javafx.stage.Stage;

import javafx.fxml.FXML;
import org.springframework.context.ApplicationContext;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import org.code_studio.database.Warehouse;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSDialogButtons;

public class Warehouse_WarehouseAddEditController extends BaseController {
	@FXML CSDialogButtons dialogButtons;
	
	@FXML CSTextField tfWarehouseNumber;
	@FXML CSTextField tfName;
	@FXML CheckBox ckbRetail;
	@FXML CSTextField tfRetailOrdinalNumber;
	@FXML CheckBox ckbWholesale;
	@FXML CSTextField tfWholesaleOrdinalNumber;
	@FXML CheckBox ckbDiscountRetail;
	@FXML CSTextField tfDiscountRetailOrdinalNumber;
	@FXML CheckBox ckbDiscountWholesale;
	@FXML CSTextField tfDiscountWholesaleOrdinalNumber;
	@FXML CheckBox ckbCommission;
	@FXML CSTextField tfCommissionOrdinalNumber;
	@FXML CheckBox ckbOffered;
	@FXML CheckBox ckbInternet;
	@FXML CheckBox ckbActive;
	
	private int mode;
	private CSTable<Warehouse> tblWarehouse;
	private Warehouse warehouse;
	
	private final String urlAddOrUpdateWarehouse = "/warehouse";
	private CSRestService<Warehouse> rsAddOrUpdateWarehouse;
	
	@SuppressWarnings("unchecked")
	public Warehouse_WarehouseAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.tblWarehouse = (CSTable<Warehouse>) controllerParam;
		this.warehouse = this.tblWarehouse.getSelectedItem();
	}

	public void initialize() {
		try {
			rsAddOrUpdateWarehouse = new CSRestService<Warehouse>(urlAddOrUpdateWarehouse);
			
			ckbRetail.selectedProperty().addListener((observable, oldValue, newValue) -> {
				tfRetailOrdinalNumber.setDisable((!newValue));
			});
			
			ckbWholesale.selectedProperty().addListener((observable, oldValue, newValue) -> {
				tfWholesaleOrdinalNumber.setDisable((!newValue));
			});
			
			ckbDiscountRetail.selectedProperty().addListener((observable, oldValue, newValue) -> {
				tfDiscountRetailOrdinalNumber.setDisable((!newValue));
			});
			
			ckbDiscountWholesale.selectedProperty().addListener((observable, oldValue, newValue) -> {
				tfDiscountWholesaleOrdinalNumber.setDisable((!newValue));
			});
			
			ckbCommission.selectedProperty().addListener((observable, oldValue, newValue) -> {
				tfCommissionOrdinalNumber.setDisable((newValue));
			});
			
			if (mode == 0) { //add

			} else { //edit
				tfWarehouseNumber.setText(warehouse.getWarehouseNumber().toString());
				tfName.setText(warehouse.getName());
				ckbRetail.setSelected(warehouse.getIsRetail());
				tfRetailOrdinalNumber.setTextOrEmptyString(
					warehouse.getRetailSerialNo() != null
					? warehouse.getRetailSerialNo().toString()
					: "0"
				);
				ckbWholesale.setSelected(warehouse.getIsWholesale());
				tfWholesaleOrdinalNumber.setTextOrEmptyString(
					warehouse.getWholesaleSerialNo() != null
					? warehouse.getWholesaleSerialNo().toString()
					: "0"
				);
				ckbDiscountRetail.setSelected(warehouse.getIsDiscountRetail());
				tfDiscountRetailOrdinalNumber.setTextOrEmptyString(
					warehouse.getDiscountRetailSerialNo() != null
					? warehouse.getDiscountRetailSerialNo().toString()
					: "0"
				);
				ckbDiscountWholesale.setSelected(warehouse.getIsDiscountWholesale());
				tfDiscountWholesaleOrdinalNumber.setTextOrEmptyString(
					warehouse.getDiscountWholesaleSerialNo() != null
					? warehouse.getDiscountWholesaleSerialNo().toString()
					: "0"
				);

				ckbCommission.setSelected(warehouse.getIsCommission());
				tfCommissionOrdinalNumber.setTextOrEmptyString(
					warehouse.getCommissionSerialNo() != null
					? warehouse.getCommissionSerialNo().toString()
					: "0"
				);
				ckbOffered.setSelected(warehouse.getIsOfferred());
				ckbInternet.setSelected(warehouse.getIsInternet());
				ckbActive.setSelected(warehouse.getIsActive());
			} // EDIT END
						
			dialogButtons.getSaveButton().setOnAction( e-> {
				/*
				ItemBarcode insertedItem = rsAddOrUpdateItemBarcode.addOrUpdate(itemBarcode);
				if (mode == 0) {
					this.tblItemBarcode.addItem(insertedItem);
				}
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		 		*/
				warehouse.setWarehouseNumber(tfWarehouseNumber.getTextAsInteger());
				warehouse.setName(tfName.getText());
				warehouse.setIsRetail(ckbRetail.isSelected());
				warehouse.setRetailSerialNo(
					tfRetailOrdinalNumber.getTextAsInteger() == 0
					? null
					: tfRetailOrdinalNumber.getTextAsInteger()
				);
				warehouse.setIsWholesale(ckbWholesale.isSelected());
				warehouse.setWholesaleSerialNo(
					tfWholesaleOrdinalNumber.getTextAsInteger() == 0
					? null
					: tfWholesaleOrdinalNumber.getTextAsInteger()
				);
				warehouse.setIsActive(ckbActive.isSelected());
				
				rsAddOrUpdateWarehouse.addOrUpdate(warehouse);
				((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
}
