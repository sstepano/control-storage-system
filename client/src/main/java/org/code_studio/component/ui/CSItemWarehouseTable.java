package org.code_studio.component.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.database.Warehouse;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public final class CSItemWarehouseTable extends VBox implements Initializable {
	@FXML private CSComboBoxWarehouse cbWarehouse;
	@FXML private HBox hbWarehouse;
	@FXML private HBox hbWarehouseType;
	@FXML public CSTable<ItemWarehouse> csTable;
	@FXML private ToggleGroup tgpWarehouseType;
	@FXML private RadioButton rbAll;
	@FXML private RadioButton rbRegular;
	@FXML private RadioButton rbCommission;
	
	private Boolean isWarehouseComboBoxVisible = false;
	private Boolean isWarehouseComboBoxEnabled = false;
	private Boolean isWarehouseTypeVisible = false; // da li je kontrola sa radio buttons visible, koja odredjuje tipove magacina, svi, regular, komisioni
	private Boolean searchBoxVisible = false;
	private Boolean addButtonVisible = false;
	
	private String urlDataFetch = "/itemWarehouse/allPageable";
	private final String urlSearch = "/itemWarehouse/allByWarehouseIdAndItemIdOrItemName/";
	private final String urlDataFetchByItemId = "/itemWarehouse/allByItemId/";
	
	private int itemId = 0;
	
	// @WARNING - rsDataFetch se koristi u Warehouse_TransferOrderDetailAddEditController.java
	public CSRestService<ItemWarehouse> rsDataFetch;
	
	private AtomicInteger pageId;
	@SuppressWarnings("unused")
	private Integer pageSize = 30;
	
	private Integer warehouseId = null;
	
	public CSItemWarehouseTable() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSItemWarehouseTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		rsDataFetch = new CSRestService<>(urlDataFetch);
		rsDataFetch.setParentTable(csTable);
		pageId = new AtomicInteger(0);
		
		tgpWarehouseType.selectedToggleProperty().addListener((observable, unselectedToggle, selectedToggle) -> {
			switch (((RadioButton)selectedToggle).getText()) {
				case "Svi":
					cbWarehouse.setMode(0);
				break;
				case "Regularni":
					cbWarehouse.setMode(1);
				break;
				case "Komisioni":
					cbWarehouse.setMode(2);
				break;
				default:
					cbWarehouse.setMode(0);
				break;
			}
		});
		
		cbWarehouse.onSelectionChanged(newItem -> {
			csTable.clear();
			Warehouse warehouse = (Warehouse) newItem;
			warehouseId = warehouse.getId().intValue();
			if (warehouse != null) {
				pageId.set(0);
				urlDataFetch = "/itemWarehouse/allByWarehouseIdPageable/" + warehouseId;
				rsDataFetch.setUrl(urlDataFetch);
				rsDataFetch.fetch(pageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>(){}, () -> {
					csTable.setItems(rsDataFetch.getDataAsObservableList());
				});
			}
		});
		
		csTable.onDataNeeded(()->{
			rsDataFetch.fetch(pageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>(){}, () -> {
				csTable.addItems(rsDataFetch.getDataAsObservableList());
			});
		});

		csTable.onServerSearch(() -> {
			String searchKeyword = csTable.tfSearchBox.getText();
			
			if (searchKeyword.length() > 0) {
				pageId.set(0);
				rsDataFetch.setUrl(urlSearch + cbWarehouse.getSelectionModel().getSelectedItem().getId() + "/" +  searchKeyword);
				rsDataFetch.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>(){}, () -> {
					csTable.setItems(rsDataFetch.getDataAsObservableList());
				});
			} else {
				//search empty, fetch all data, like when opening form for the first time
				pageId.set(0);
				rsDataFetch.setUrl("/itemWarehouse/allByWarehouseIdPageable/" + warehouseId);
				rsDataFetch.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>(){}, () -> {
					csTable.setItems(rsDataFetch.getDataAsObservableList());					
				});
			}
		});
		
		csTable.setAddEditDialog("Items_ItemAddEdit");
		
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
		urlDataFetch = urlDataFetchByItemId + String.valueOf(itemId);
		rsDataFetch.setUrl(urlDataFetch);
	}

	public String getUrlDataFetch() {
		return urlDataFetch;
	}

	public void setUrlDataFetch(String urlDataFetch) {
		this.urlDataFetch = urlDataFetch;
	}
	
	public Boolean getWarehouseComboBoxVisible () {
		return isWarehouseComboBoxVisible;
	}

	public void setWarehouseComboBoxVisible (Boolean visible) {
		isWarehouseComboBoxVisible = visible;
		hbWarehouse.setVisible(visible);
		hbWarehouse.setManaged(visible);
	}

	public Boolean getWarehouseComboBoxEnabled() {
		return isWarehouseComboBoxEnabled;
	}

	public void setIsWarehouseComboBoxEnabled(Boolean isWarehouseComboBoxEnabled) {
		this.isWarehouseComboBoxEnabled = isWarehouseComboBoxEnabled;
	}
	
	public Boolean getWarehouseTypeVisible () {
		return isWarehouseTypeVisible;
	}

	public void setWarehouseTypeVisible (Boolean visible) {
		isWarehouseTypeVisible = visible;
		hbWarehouseType.setVisible(visible);
		hbWarehouseType.setManaged(visible);
	}

	public Boolean getSearchBoxVisible() {
		return searchBoxVisible;
	}

	public void setSearchBoxVisible(Boolean searchBoxVisible) {
		this.searchBoxVisible = searchBoxVisible;
		csTable.setSearchVisible(searchBoxVisible);
	}

	public Boolean getAddButtonVisible() {
		return addButtonVisible;
	}

	public void setAddButtonVisible(Boolean addButtonVisible) {
		this.addButtonVisible = addButtonVisible;
		csTable.setAddButtonVisible(addButtonVisible);
	}

	/**
	 * @return the warehouseId
	 */
	public Integer getWarehouseId() {
		return warehouseId;
	}

	/**
	 * @param warehouseId the warehouseId to set
	 */
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	/**
	 * @param warehouseId
	 */
	public void setSelectedWarehouseId (Integer warehouseId) {
		cbWarehouse.setSelectedWarehouseId(warehouseId);
	}
	
	public void fill() {
		if (itemId != 0) {
			rsDataFetch.fetch(new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>(){}, true, () -> {
				csTable.setItems(rsDataFetch.getDataAsObservableList());
			});			
		} else {
			rsDataFetch.fetch(pageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>(){}, () -> {
				csTable.setItems(rsDataFetch.getDataAsObservableList());
			});
		}
	}
	
	public Button getViewButton () {
		return csTable.btnView;
	}


}
