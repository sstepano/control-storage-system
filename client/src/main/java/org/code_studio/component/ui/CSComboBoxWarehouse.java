package org.code_studio.component.ui;

import org.code_studio.component.CSComboBox;
import org.code_studio.database.Warehouse;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

public class CSComboBoxWarehouse extends CSComboBox <Warehouse> {
	private CSRestService<Warehouse> rsWarehouse;
	private int mode; // 0 - svi, 1 - regularni, 2 - komisioni magacini
	private String urlWarehouse;//= "/warehouse/allByIsActive/True";
	
	/**
	 * Component is used for creating specific CSComboBox to show Warehouse POJO
	 */
	public CSComboBoxWarehouse() {
		setInitFactory(true);
		setFactoryMethodName("NameAndNumber");
		rsWarehouse = new CSRestService<>(urlWarehouse);
		setMode(0); // default prikazi sve
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
		switch (mode) {
			case 0:
				urlWarehouse = "/warehouse/allByIsActive/True"; // all
			break;
			case 1:
				urlWarehouse = "/warehouse/allActiveCommissionOrRegular/false"; // regular only
			break;
			case 2:
				urlWarehouse = "/warehouse/allActiveCommissionOrRegular/true"; // commission only
			break;
			default:
				urlWarehouse = "/warehouse/allByIsActive/True";
			break;
		}
		refresh();
	}
	
	public void refresh() {
		rsWarehouse.setUrl(urlWarehouse);
		rsWarehouse.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>() {});
		this.getItems().clear();
		setItemsAndSelectFirstItem(rsWarehouse.getDataAsObservableList());		
	}
	
	/***
	 * Programatically changes selection of underlying CSComboBox based on provided Warehouse ID
	 * @param warehouseId
	 */
	public void setSelectedWarehouseId (Integer warehouseId) {
		this.selectByItemId(warehouseId);
	}

}
