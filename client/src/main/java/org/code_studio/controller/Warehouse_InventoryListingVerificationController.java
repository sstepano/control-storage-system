package org.code_studio.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;
import java.util.List;
import org.code_studio.component.CSPrintButton;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSComboBoxWarehouse;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.InventoryListing;
import org.code_studio.database.InventoryListingDetail;
import org.code_studio.database.InventoryListingDetailSum;
import org.code_studio.database.StoredProcedureResult;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;


public class Warehouse_InventoryListingVerificationController extends BaseController {

	@FXML CSDialogButtons dialogButtons;
	@FXML private CSTable <InventoryListingDetailSum> tblInventoryListingDetailSum;
	@FXML private CSTable <InventoryListingDetail> tblInventoryListingDetail;
	@FXML private ToggleGroup tgpStatus;
	@FXML private CSComboBoxWarehouse cbWarehouse;
	@FXML private CSTextField tfQty;
	@FXML private CSTextField tfSum;
	@FXML private CSPrintButton btnReportMinuses;
	@FXML private CSPrintButton btnReportPluses;
	@FXML private CSPrintButton btnReportAllDifferences;
	@FXML private Button btnCreateOneListing;
	@FXML private Button btnCreateAllListings;
	
	
	@SuppressWarnings("unused")
	private int mode = 0;
	@SuppressWarnings("unused")
	private ApplicationContext ctx;
	
	private final String inventoryListingDetailAddEditControllerName = "Warehouse_InventoryListingDetailAddEditController";
	
	private final String urlInventoryListingDetailSum = "/inventoryListingDetailSum/allByDiffAndWarehouseIdAndItemId/";
	private CSRestService<InventoryListingDetailSum> rsInventoryListingDetailSum;
	
	private final String urlInventoryListingDetail = "/inventoryListingDetail/allByItemIdAndRowIdAndShelfIdAndVerticalId/";
	
	private CSRestService<InventoryListingDetail> rsInventoryListingDetail;
	private CSTable<InventoryListing> tblInventoryListingParent;
	
	private final String urlInventoryListingAdd = "/businessLogicService/spInventoryListingAdd";
	private final String urlInventoryListingAllDiscrepanciesAdd = "/businessLogicService/spInventoryListingAllDiscrepanciesAdd";
	private CSRestService <StoredProcedureResult> rsInventoryListingAdd;
	
	private List<Object> lstControllerParam;
	
	@SuppressWarnings("unchecked")
	public Warehouse_InventoryListingVerificationController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.ctx = ctx;
		this.tblInventoryListingParent = (CSTable<InventoryListing>) controllerParam;
	}

	public void initialize() {
		rsInventoryListingDetailSum = new CSRestService<InventoryListingDetailSum>(urlInventoryListingDetailSum);
		rsInventoryListingDetailSum.setParentTable(tblInventoryListingDetailSum);
		
		rsInventoryListingDetail = new CSRestService<InventoryListingDetail>(urlInventoryListingDetail);
		rsInventoryListingAdd  = new CSRestService<StoredProcedureResult>(urlInventoryListingAdd);
		
		tblInventoryListingDetail.setParentTable(tblInventoryListingDetailSum);
		tblInventoryListingDetail.onRowDoubleClick( row -> {
			tblInventoryListingDetail.showDoubleClickDefaultAction = true;	
		});
		
		fetchData();
		
		tblInventoryListingDetailSum.onRowSelectionChanged((oldRow, newRow) -> {
			if (newRow != null && ((InventoryListingDetailSum) newRow).getItemId() != null) {
				InventoryListingDetailSum inventoryListingDetailSum = (InventoryListingDetailSum) newRow;
				rsInventoryListingDetail.setUrl(
						urlInventoryListingDetail 
						+ inventoryListingDetailSum.getItemId() + "/"
						+ inventoryListingDetailSum.getRowId() + "/"
						+ inventoryListingDetailSum.getShelfId() + "/"
						+ inventoryListingDetailSum.getVerticalId()
						);
				rsInventoryListingDetail.fetch(new ParameterizedTypeReference<JsonResponse<InventoryListingDetail>>() {}, () -> {
					tblInventoryListingDetail.setItems(rsInventoryListingDetail.getDataAsObservableList());
				});
				
				tfQty.setText(String.valueOf(inventoryListingDetailSum.getQty()));
				tfSum.setText(String.valueOf(inventoryListingDetailSum.getCountedQty()));
				
				if (inventoryListingDetailSum.getQty() != inventoryListingDetailSum.getCountedQty()) {
				  tfSum.getStyleClass().remove("lightgreen-background");
				  tfSum.getStyleClass().remove("red-field");
				  tfSum.getStyleClass().add("red-field");
				} else {
					tfSum.getStyleClass().remove("red-field");
					tfSum.getStyleClass().remove("lightgreen-background");
					tfSum.getStyleClass().add("lightgreen-background");
				}
			} else {
				tfQty.setText(null);
				tfSum.setText(null);
			}
		});
		
		lstControllerParam = new ArrayList<>();
		lstControllerParam.add(tblInventoryListingParent);
		lstControllerParam.add(tblInventoryListingDetail);
		tblInventoryListingDetail.setAddEditDialog(inventoryListingDetailAddEditControllerName, lstControllerParam, 2);
		
		tblInventoryListingDetail.onRowSelectionChanged((oldRow, newRow) -> {
			btnCreateOneListing.setDisable(tblInventoryListingDetail.tableView.getItems().isEmpty());
			btnCreateAllListings.setDisable(tblInventoryListingDetail.tableView.getItems().isEmpty());
			btnReportMinuses.setDisable(tblInventoryListingDetail.tableView.getItems().isEmpty());
			btnReportPluses.setDisable(tblInventoryListingDetail.tableView.getItems().isEmpty());
			btnReportAllDifferences.setDisable(tblInventoryListingDetail.tableView.getItems().isEmpty());
		});
				
		tgpStatus.selectedToggleProperty().addListener((oldVal, newVal, observable) -> {
			fetchData();
		});
		
		cbWarehouse.onSelectionChanged(newVal -> {
			fetchData();
		});
		
		/*
		btnChangeEnumeratedCount.setOnAction( e-> {
			Common.displayForm(ControllerFactory.getController(inventoryListingDetailAddEditControllerName, lstControllerParam, 1), tblInventoryListingDetail, "Izmena detalja popisa");
		});
		*/
		
		/*
		tblInventoryListingDetailSum.onServerSearch(()->{
			fetchData();
		});
		*/
		
		btnCreateOneListing.setOnAction( e-> {
			InventoryListingDetail inventoryListingDetail = tblInventoryListingDetail.getSelectedItem();
			rsInventoryListingAdd.setUrl(urlInventoryListingAdd);
			rsInventoryListingAdd.callStoredProcedure(new ParameterizedTypeReference<JsonResponse<StoredProcedureResult>>(){},
				  String.valueOf(inventoryListingDetail.getId()) // p_in_int_inventory_listing_detail_id
				, Common.getApplicationUserId().toString() // p_in_int_created_by_user_id
			);
			
			// TODO: Refresh table, ali mi se ne svidja zbog performansi
			rsInventoryListingDetail.fetch(new ParameterizedTypeReference<JsonResponse<InventoryListingDetail>>() {}, () -> {
				tblInventoryListingDetail.setItems(rsInventoryListingDetail.getDataAsObservableList());
				tblInventoryListingDetail.selectLastRow();
			});
		});
		
		btnCreateAllListings.setOnAction( e-> {
			rsInventoryListingAdd.setUrl(urlInventoryListingAllDiscrepanciesAdd);
			rsInventoryListingAdd.callStoredProcedure(new ParameterizedTypeReference<JsonResponse<StoredProcedureResult>>(){},
				Common.getApplicationUserId().toString() // p_in_int_created_by_user_id
			);
			
			//refresh detail tabele
			rsInventoryListingDetail.fetch(new ParameterizedTypeReference<JsonResponse<InventoryListingDetail>>() {}, () -> {
				tblInventoryListingDetail.setItems(rsInventoryListingDetail.getDataAsObservableList());
				tblInventoryListingDetail.selectLastRow();
			});
			
		});
		
		
		// REPORTS
		btnReportMinuses.addReportConfigParam("MINUSI");
		btnReportMinuses.addParam("JSON_DETAIL_URL", Common.baseUrl + "/inventoryListingDetailSum/allByDifferenceIndicator/0");			

		btnReportPluses.addReportConfigParam("PLUSEVI");
		btnReportPluses.addParam("JSON_DETAIL_URL", Common.baseUrl + "/inventoryListingDetailSum/allByDifferenceIndicator/1");
		
		btnReportAllDifferences.addReportConfigParam("SVE RAZLIKE");
		btnReportAllDifferences.addParam("JSON_DETAIL_URL", Common.baseUrl + "/inventoryListingDetailSum/allByDifferenceIndicator/2");
		
	}
	
	private int getToggleStatusId() {
		int diff = 0;			
		String radioButtonText = ((RadioButton) tgpStatus.getSelectedToggle()).getText();
		
		switch (radioButtonText) {
			case "Razlike":
				diff = 1;
			break;
			case "Sve":
				diff = 0;
			break;
			case "Nepopisano":
				diff = 2;
			break;
		}
		return diff;
	}
	
	/**
	 * Used to get table search text. If we use text directly in constructing the url, it throws err.
	 * @return
	 */
	private String getSearchText() {
		if (tblInventoryListingDetailSum.tfSearchBox.getText().equals(null)
				|| tblInventoryListingDetailSum.tfSearchBox.getText().isBlank()) {
			return "0";
		}
		else return tblInventoryListingDetailSum.tfSearchBox.getText();
	}
	
	/**
	 * Fetches data for the first table when toggle changes or warehouse ddl changes
	 */
	private void fetchData() {
		tblInventoryListingDetailSum.clear();
		rsInventoryListingDetailSum.setUrl(
				urlInventoryListingDetailSum 
				+ getToggleStatusId() + "/"
				+ cbWarehouse.getSelectionModel().getSelectedItem().getId() + "/"
				+ getSearchText()
			);
			
		rsInventoryListingDetailSum.fetch(new ParameterizedTypeReference<JsonResponse<InventoryListingDetailSum>>() {}, () -> {
			tblInventoryListingDetailSum.setItems(rsInventoryListingDetailSum.getDataAsObservableList());			
		});		
	}
	
}
