package org.code_studio.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.main.Common;

import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;

import org.code_studio.component.CSPhotoView;
import org.code_studio.component.CSPrintButton;
import org.code_studio.component.CSReportView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.CSBarcodeReader;

import org.code_studio.database.ItemWarehouse;
import org.code_studio.database.TransferOrder;
import org.code_studio.database.TransferOrderDetail;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Warehouse_IssuingItemController extends BaseController {
	
	@FXML private CSTable <TransferOrder> tblTransferOrder;
	@FXML private CSTable <TransferOrderDetail> tblTransferOrderDetail;
	
	@FXML private Button btnManualIssuing;
	@FXML private Button btnControl;
	@FXML private CSPrintButton btnPrintDifferences;
	@FXML private Button btnSeparationStarted;
	@FXML private Button btnSeparationCompleted;
	@FXML private Button btnScanningStarted;
	@FXML private Button btnScanningCompleted;
	@FXML private Button btnPackingStarted;
	@FXML private Button btnPackingCompleted;
	@FXML private Button btnWorkStarted;
	@FXML private Button btnWorkCompleted;
	@FXML private Button btnDelivered;
	
	@FXML private Button btnPrintDailyDiary;
	@FXML private Button btnPrintDailyWarehouseDiary;
	@FXML private Button btnPrintDailyItemDiary;
	@FXML private Button btnPrintDomDeclarationTransfer;
	@FXML private Button btnPrintDomDeclaration;
	@FXML private Button btnPrintCountRows;
	@FXML private Button btnPrintEmptyWorking;
	@FXML private Button btnTransferToGeneralLedger;
	
	@FXML private CSTextField tfTotalQty;
	@FXML private CSTextField tfTotalIssuedQty;
	@FXML private TextArea taBlueField;
	@FXML private Label lblClientName;
	@FXML private CSPhotoView pvItemPhoto;
	@FXML private ToggleGroup tgpStatus;

	CSRestService<TransferOrder> rsTransferOrder;
	AtomicInteger transferOrderPageId;
	CSRestService<ItemWarehouse> rsItemWarehouse;
	AtomicInteger itemWarehousePageId;
	CSRestService<TransferOrder> rsTransferOrderSearch;
	
	private final String urlTransferOrder = "/transferOrder/allPageableByStatusId/";
	private final String urlItemWarehouse = "/itemWarehouse/allByWarehouseIdPageable/";
	private final String warehouseManualIssuingControllerName = "Warehouse_IssuingItemsManualIssuingController";
	private final String urlTransferOrderSearch = "/transferOrder/allByInvoiceId/";
	
	@FXML private CSTextField tblTransferOrderSearchBox;
	
	// REPORT VARS START
	private String baseUrl;
	private List<String> lstRptConfig = new ArrayList<>();
	private HashMap<String, Object> rptParams = new HashMap<>();
	// REPORT VARS END

	public Warehouse_IssuingItemController(Object controllerParam, int mode, ApplicationContext ctx) {
		baseUrl = Common.applicationProperties.getProperty("api.serverurl");
	}
	
	public void initialize() {
		tblTransferOrderSearchBox = tblTransferOrder.tfSearchBox;
		rsTransferOrder = new CSRestService<>(urlTransferOrder);
		transferOrderPageId = new AtomicInteger(0);
		rsItemWarehouse = new CSRestService<>(urlItemWarehouse);
		itemWarehousePageId = new AtomicInteger(0);
		rsTransferOrderSearch = new CSRestService<>(urlTransferOrderSearch);
		
		tblTransferOrderDetail.setParentTable(tblTransferOrder);
		
		btnManualIssuing.setOnAction( e-> {
			List<Object> lstControllerParams = new ArrayList<>();
			lstControllerParams.add(tblTransferOrderDetail);
			lstControllerParams.add(tblTransferOrder.getSelectedItem());
			Common.displayForm(ControllerFactory.getController(warehouseManualIssuingControllerName, lstControllerParams, 0), tblTransferOrder, null);
		});
		
		getTransferOrderStatusAndFetchData();
		tblTransferOrder.onDataNeeded(() -> {
			rsTransferOrder.fetch(transferOrderPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<TransferOrder>>() {});
			tblTransferOrder.addItems(rsTransferOrder.getDataAsObservableList());
		});
		tblTransferOrder.onRowDoubleClick((rowData) -> {
			tblTransferOrder.showDoubleClickDefaultAction = true;
		});
		
		tblTransferOrder.onRowSelectionChanged((oldRow, newRow) -> {
			if (newRow != null) {
				TransferOrder to = (TransferOrder) newRow;
				tblTransferOrderDetail.setItems(FXCollections.observableArrayList(to.getTransferOrderDetail()));
				taBlueField.setText(to.getDescription());
				lblClientName.setText(to.getClientName());
				tfTotalQty.setText(to.getTotalQty().toString());
				tfTotalIssuedQty.setText(to.getTotalIssuedQty().toString());
				
				/*** TODO: Enable/disable buttons based on TO status
				btnSeparationStarted.setDisable(to.getStatusId() != 1);
				btnSeparationCompleted.setDisable(to.getStatusId() != 2);
				btnPackingCompleted.setDisable(to.getStatusId() != 3);
				*/
			}
			
		});
		
		tblTransferOrderDetail.onRowSelectionChanged((oldRow, newRow)->{
			TransferOrderDetail tod = (TransferOrderDetail) newRow;
			if (newRow != null) {
				pvItemPhoto.setImagePath(tod.getItem().getImagePath());
			}
		});
		
		tblTransferOrder.onServerSearch(()->{
			String searchKeyword = tblTransferOrder.tfSearchBox.getText();
			
			if (searchKeyword != null && searchKeyword.length() > 0) {
				rsTransferOrderSearch.setUrl(urlTransferOrderSearch + searchKeyword);
				rsTransferOrderSearch.fetch(new ParameterizedTypeReference<JsonResponse<TransferOrder>>(){});
				tblTransferOrder.setItems(rsTransferOrderSearch.getDataAsObservableList());
			} else {
				//search empty, fetch all data, like when opening form for the first time
				transferOrderPageId.set(0);
				rsTransferOrder.fetch(transferOrderPageId.get(), new ParameterizedTypeReference<JsonResponse<TransferOrder>>(){}, () -> {
					tblTransferOrder.setItems(rsTransferOrder.getDataAsObservableList());
				});
			}
		});

		tgpStatus.selectedToggleProperty().addListener((oldVal, newVal, observable) -> {
			getTransferOrderStatusAndFetchData();
		});
		
		btnSeparationStarted.setOnAction( e -> {
			Common.displayForm(ControllerFactory.getController("Warehouse_TransferOrderWorkController", tblTransferOrder, 0), tblTransferOrder, btnSeparationStarted.getText());
		});
		
		btnSeparationCompleted.setOnAction( e -> {
			System.out.println("Update TO status to SeparationCompleted");
		});
		
		btnScanningStarted.setOnAction( e -> {
			Common.displayForm(ControllerFactory.getController("Warehouse_TransferOrderWorkController", tblTransferOrder, 0), tblTransferOrder, btnScanningStarted.getText());
		});
		
		btnScanningCompleted.setOnAction( e -> {
			System.out.println("Update TO status to ScanningCompleted");
		});

		btnPackingStarted.setOnAction( e -> {
			Common.displayForm(ControllerFactory.getController("Warehouse_TransferOrderWorkController", tblTransferOrder, 0), tblTransferOrder, btnPackingStarted.getText());
		});
		
		btnPackingCompleted.setOnAction( e -> {
			System.out.println("Update TO status to PackingCompleted");
		});
		

		//TODO: pomeriti na bolje mesto od ovog
		btnPrintDifferences.addParam("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti)
		btnPrintDifferences.addParam("TRANSFER_ORDER_ID", tblTransferOrder.getSelectedItem().getId());
		btnPrintDifferences.addParam("JSON_DETAIL_URL", baseUrl + "/transferOrderDetail/allDifferencesByTransferOrderId/" + tblTransferOrder.getSelectedItem().getId());			
		
		/*
		btnPrintDifferences.setOnAction( e -> {
			rptParams.clear();
			rptParams.put("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
			rptParams.put("TRANSFER_ORDER_ID", tblTransferOrder.getSelectedItem().getId());
			rptParams.put("JSON_DETAIL_URL", baseUrl + "/transferOrderDetail/allDifferencesByTransferOrderId/" + tblTransferOrder.getSelectedItem().getId());
			new CSReportView("transfer_order_differences", rptParams);
		});
		*/
		
		btnPrintDailyDiary.setOnAction( e -> {
			rptParams.clear();
			rptParams.put("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
			rptParams.put("TRANSFER_ORDER_ID", tblTransferOrder.getSelectedItem().getId());
			rptParams.put("JSON_DETAIL_URL", baseUrl + "/transferOrderDetail/allDifferencesByTransferOrderId/" + tblTransferOrder.getSelectedItem().getId());
			new CSReportView("transfer_order_daily_diary", rptParams);
		});

		btnPrintDailyWarehouseDiary.setOnAction( e -> {
			rptParams.clear();
			rptParams.put("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
			rptParams.put("TRANSFER_ORDER_ID", tblTransferOrder.getSelectedItem().getId());
			rptParams.put("JSON_DETAIL_URL", baseUrl + "/transferOrderDetail/allDifferencesByTransferOrderId/" + tblTransferOrder.getSelectedItem().getId());
			new CSReportView("transfer_order_daily_warehouse_diary", rptParams);
		});

		btnPrintDailyItemDiary.setOnAction( e -> {
			rptParams.clear();
			rptParams.put("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
			rptParams.put("TRANSFER_ORDER_ID", tblTransferOrder.getSelectedItem().getId());
			rptParams.put("JSON_DETAIL_URL", baseUrl + "/transferOrderDetail/allDifferencesByTransferOrderId/" + tblTransferOrder.getSelectedItem().getId());
			new CSReportView("transfer_order_daily_item_diary", rptParams);
		});

		btnPrintDomDeclarationTransfer.setOnAction( e -> {
			rptParams.clear();
			rptParams.put("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
			rptParams.put("TRANSFER_ORDER_ID", tblTransferOrder.getSelectedItem().getId());
			rptParams.put("JSON_DETAIL_URL", baseUrl + "/transferOrderDetail/allDifferencesByTransferOrderId/" + tblTransferOrder.getSelectedItem().getId());
			new CSReportView("transfer_order_differences", rptParams);
		});

		btnPrintDomDeclaration.setOnAction( e -> {
			rptParams.clear();
			rptParams.put("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
			rptParams.put("TRANSFER_ORDER_ID", tblTransferOrder.getSelectedItem().getId());
			rptParams.put("JSON_DETAIL_URL", baseUrl + "/transferOrderDetail/allDifferencesByTransferOrderId/" + tblTransferOrder.getSelectedItem().getId());
			new CSReportView("transfer_order_differences", rptParams);
		});

		btnPrintCountRows.setOnAction( e -> {
			rptParams.clear();
			rptParams.put("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
			rptParams.put("TRANSFER_ORDER_ID", tblTransferOrder.getSelectedItem().getId());
			rptParams.put("JSON_DETAIL_URL", baseUrl + "/transferOrderDetail/allDifferencesByTransferOrderId/" + tblTransferOrder.getSelectedItem().getId());
			new CSReportView("transfer_order_differences", rptParams);
		});

		btnPrintEmptyWorking.setOnAction( e -> {
			rptParams.clear();
			rptParams.put("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
			rptParams.put("TRANSFER_ORDER_ID", tblTransferOrder.getSelectedItem().getId());
			rptParams.put("JSON_DETAIL_URL", baseUrl + "/transferOrderDetail/allDifferencesByTransferOrderId/" + tblTransferOrder.getSelectedItem().getId());
			new CSReportView("transfer_order_differences", rptParams);
		});


	} // initialize END
	
	/**
	 * We must do this in postInit, because getScene() used in CSBarcodeReader
	 * returns value only if window is created and visible
	 */
	@Override
	public void postInitialize() {
		CSBarcodeReader barcodeReader = new CSBarcodeReader(tblTransferOrderSearchBox, false);
		barcodeReader.register();
	}
	
	/**
	 * 
	 */
	private void getTransferOrderStatusAndFetchData() {
		Integer transferOrderStatusId = 1;
		
		String radioButtonText = ((RadioButton) tgpStatus.getSelectedToggle()).getText();
		
		switch (radioButtonText) {
			case "Za rad":
				transferOrderStatusId = 1;
			break;
			case "Započeto odvajanje":
				transferOrderStatusId = 2;
			break;
			case "Završeno odvajanje":
				transferOrderStatusId = 3;
			break;
			case "Započeto skeniranje":
				transferOrderStatusId = 4;
			break;
			case "Završeno skeniranje":
				transferOrderStatusId = 5;
			break;
			case "Započeto pakovanje":
				transferOrderStatusId = 6;
			break;
			case "Završeno pakovanje":
				transferOrderStatusId = 7;
			break;
		}
		
		rsTransferOrder.setUrl(urlTransferOrder + transferOrderStatusId); 
		transferOrderPageId.set(0);
		rsTransferOrder.fetch(transferOrderPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<TransferOrder>>() {});
		tblTransferOrder.setItems(rsTransferOrder.getDataAsObservableList());
	}
	
}  // class END
