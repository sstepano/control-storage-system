package org.code_studio.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.ui.CSComboBoxEnumerator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.InventoryListing;
import org.code_studio.database.InventoryListingDetail;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;


public class Warehouse_InventoryListingController extends BaseController {

	@FXML CSDialogButtons dialogButtons;

	@FXML private CSTable <InventoryListing> tblInventoryListing;
	@FXML private CSTable <InventoryListingDetail> tblInventoryListingDetail;
	@FXML private Button btnInventoryListingVerification;
	@FXML private ToggleGroup tgpStatus;
	@FXML private CSComboBoxEnumerator cbEnumerator;
	@FXML private Label lblWarehouseIdAndName;	
	
	@SuppressWarnings("unused")
	private int mode = 0;
	@SuppressWarnings("unused")
	private ApplicationContext ctx;
	
	private final String inventoryListingAddEditControllerName = "Warehouse_InventoryListingAddEditController";
	private final String inventoryListingDetailAddEditControllerName = "Warehouse_InventoryListingDetailAddEditController";
	private final String inventoryListingVerificationControllerName = "Warehouse_InventoryListingVerificationController";
	
	private final String urlInventoryListing = "/inventoryListing/allPageable";
	private final String urlInventoryListingDelete = "/inventoryListing";
	private final String urlInventoryListingDetail = "/inventoryListingDetail/allPageableByInventoryListingId/";
	private final String urlInventoryListingDetailDelete = "/inventoryListingDetail";
	private final String urlInventoryListingByStatusIdAndEnumeratorId = "/inventoryListing/allPageableByStatusIdAndEnumeratorUserId/";
	
	CSRestService<InventoryListing> rsInventoryListing;
	CSRestService<InventoryListingDetail> rsInventoryListingDetail;
	CSRestService<InventoryListing> rsInventoryListingDelete;
	CSRestService<InventoryListingDetail> rsInventoryListingDetailDelete;
	
	private int statusId;
	private AtomicInteger inventoryListingPageId;
	private AtomicInteger inventoryListingDetailPageId;
	
	public Warehouse_InventoryListingController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.ctx = ctx;
		//controllerParam
	}

	public void initialize() {
		tblInventoryListingDetail.setParentTable(tblInventoryListing);
		inventoryListingPageId = new AtomicInteger(0);
		rsInventoryListing = new CSRestService<InventoryListing>(urlInventoryListing);
		tblInventoryListing.setAddEditDialog(inventoryListingAddEditControllerName, tblInventoryListing);
		tblInventoryListing.onRowDoubleClick( row -> {
			tblInventoryListing.showDoubleClickDefaultAction = true;	
		});
		fetchData();
		
		List<Object> lstControllerParam = new ArrayList<>();
		lstControllerParam.add(tblInventoryListing);
		lstControllerParam.add(tblInventoryListingDetail);
		tblInventoryListingDetail.setAddEditDialog(inventoryListingDetailAddEditControllerName, lstControllerParam);
		
		rsInventoryListingDelete = new CSRestService<InventoryListing>(urlInventoryListingDelete);
		rsInventoryListingDetailDelete = new CSRestService<InventoryListingDetail>(urlInventoryListingDetailDelete);
		tblInventoryListing.setRestServiceDelete(rsInventoryListingDelete);
		tblInventoryListingDetail.setRestServiceDelete(rsInventoryListingDetailDelete);
		
		tblInventoryListingDetail.onRowDoubleClick( row -> {
			tblInventoryListingDetail.showDoubleClickDefaultAction = true;	
		});
		
		inventoryListingDetailPageId = new AtomicInteger(0);
		rsInventoryListingDetail = new CSRestService<InventoryListingDetail>(urlInventoryListingDetail);
		
		tblInventoryListing.onDataNeeded(() -> {
			rsInventoryListing.fetch(inventoryListingPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<InventoryListing>>() {}, () -> {
				tblInventoryListing.setItems(rsInventoryListing.getDataAsObservableList());			
			});
		});
		
		tblInventoryListing.onRowSelectionChanged((oldRow, newRow) -> {
			if (newRow != null) {
				inventoryListingDetailPageId.set(0);
				InventoryListing inventoryListing = (InventoryListing) newRow;
				rsInventoryListingDetail.setUrl(urlInventoryListingDetail + inventoryListing.getId());
				rsInventoryListingDetail.fetch(inventoryListingDetailPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<InventoryListingDetail>>() {});
				tblInventoryListingDetail.setItems(rsInventoryListingDetail.getDataAsObservableList());
				updateTableDetailButtonsState(inventoryListing.getStatusId());
			}
		});
		
		tblInventoryListing.onFocusChanged((oldVal, newVal) -> {
			if (newVal && tblInventoryListing.getSelectedItem() != null) {
				fetchData();
				updateTableDetailButtonsState(tblInventoryListing.getSelectedItem().getStatusId());
			}
		});


		tblInventoryListingDetail.onDataNeeded(() -> {
			rsInventoryListingDetail.fetch(inventoryListingDetailPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<InventoryListingDetail>>() {}, () -> {
				tblInventoryListingDetail.addItems(rsInventoryListingDetail.getDataAsObservableList());			
			});
		});
		
		tblInventoryListingDetail.onRowSelectionChanged((oldRow, newRow) -> {
			InventoryListingDetail ild = (InventoryListingDetail) newRow;
			if (ild != null) {
				lblWarehouseIdAndName.setText(ild.getWarehouseId() + " : " + ild.getWarehouseName());
			}
		});
		
		btnInventoryListingVerification.setOnAction( e-> {
			Common.displayForm(ControllerFactory.getController(inventoryListingVerificationControllerName, tblInventoryListing, 0), tblInventoryListing, "Verifikacija popisa");
			inventoryListingPageId.set(0);
			rsInventoryListing.fetch(inventoryListingPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<InventoryListing>>() {}, () -> {
				tblInventoryListing.setItems(rsInventoryListing.getDataAsObservableList());			
			});			
		});
		
		tgpStatus.selectedToggleProperty().addListener((oldVal, newVal, observable) -> {
			fetchData();
		});
		
		cbEnumerator.onSelectionChanged( newVal -> {
			fetchData();
		});
		
		
		tblInventoryListingDetail.onServerSearch(()->{
			String searchKeyword = tblInventoryListingDetail.tfSearchBox.getText();
			final String urlSearch = "/inventoryListingDetail/search/";
			AtomicInteger aintSearchPageId = new AtomicInteger(0);
			CSRestService<InventoryListingDetail> rsInventoryListingDetailSearch = new CSRestService<InventoryListingDetail>(urlSearch);
			
			if (searchKeyword.length() > 0) {
				tblInventoryListingDetail.clear();
				rsInventoryListingDetailSearch.setUrl(urlSearch + tblInventoryListing.getSelectedItem().getId() + "/" + searchKeyword);
				rsInventoryListingDetailSearch.fetch(0, new ParameterizedTypeReference<JsonResponse<InventoryListingDetail>>(){}, () -> {
					tblInventoryListingDetail.setItems(rsInventoryListingDetailSearch.getDataAsObservableList());
				});
			} else { //search empty, fetch all data, like when opening form for the first time
				if (tblInventoryListing.getSelectedItem() != null) {
					aintSearchPageId.set(0);
					rsInventoryListingDetailSearch.setUrl(urlInventoryListingDetail + tblInventoryListing.getSelectedItem().getId());
					rsInventoryListingDetailSearch.fetch(aintSearchPageId.get(), new ParameterizedTypeReference<JsonResponse<InventoryListingDetail>>(){}, () -> {
						tblInventoryListingDetail.setItems(rsInventoryListingDetailSearch.getDataAsObservableList());
					});
				} else {
					tblInventoryListingDetail.clear();
				}
			}
		});
		
	}
	
	private void getToggleStatusId() {
		statusId = 1;			
		String radioButtonText = ((RadioButton) tgpStatus.getSelectedToggle()).getText();
		
		switch (radioButtonText) {
			case "Za rad":
				statusId = 1;
			break;
			case "U radu":
				statusId = 2;
			break;
			case "Završeno":
				statusId = 3;
			break;
			case "Sve":
				statusId = 0;
			break;
		}
		
	}
	private void fetchData() {
		getToggleStatusId();
		int selectedEnumeratorId = cbEnumerator.getSelectionModel().getSelectedItem().getId().intValue();
		rsInventoryListing.setUrl(urlInventoryListingByStatusIdAndEnumeratorId + String.valueOf(statusId) + "/" + selectedEnumeratorId);
		inventoryListingPageId.set(0);
		rsInventoryListing.fetch(inventoryListingPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<InventoryListing>>() {});
		tblInventoryListing.setItems(rsInventoryListing.getDataAsObservableList());	
	}
	
	/**
	 * Manages add/edit/delete buttons state based on statusid of master row
	 * Ako status nije 2 == U RADU, disableujemo dugmice jer ne moze user da dodaje
	 * detalje na naloge koji su tek dodati ili su zatvoreni
	 */
	private void updateTableDetailButtonsState(int statusId) {
		if (statusId != 2) { // 
			tblInventoryListingDetail.btnAdd.setDisable(true);
			tblInventoryListingDetail.btnEdit.setDisable(true);
			tblInventoryListingDetail.btnDelete.setDisable(true);
		} else {
			tblInventoryListingDetail.btnAdd.setDisable(false);
			tblInventoryListingDetail.btnEdit.setDisable(false);
			tblInventoryListingDetail.btnDelete.setDisable(false);
		}
	}
	
}
