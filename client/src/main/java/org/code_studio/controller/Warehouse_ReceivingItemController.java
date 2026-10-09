package org.code_studio.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSReportView;
import org.code_studio.component.CSTable;
import org.code_studio.database.BillOfReceipt;
import org.code_studio.database.BillOfReceiptDetail;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Warehouse_ReceivingItemController extends BaseController implements Initializable {
	@FXML private CSTable <BillOfReceipt> tblBillOfReceipt;
	@FXML private CSTable <BillOfReceiptDetail> tblBillOfReceiptDetail;
	
	@FXML private Button btnPrint;
	
	CSRestService<BillOfReceipt> rsvcBillOfReceipt;
	CSRestService<BillOfReceiptDetail> rsvcBillOfReceiptDetail;
	
	AtomicInteger billOfReceiptPageId;
	AtomicInteger billOfReceiptDetailPageId;
	
	private final String urlBillOfReceipt = "/billOfReceipt/allPageable/";
	private final String urlBillOfReceiptDetail = "/billOfReceiptDetail/allPageableByBillOfReceiptId/";
	
	public Warehouse_ReceivingItemController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		tblBillOfReceipt.setAddEditDialog("Warehouse_ReceivingItemsAddEditController");
		
		billOfReceiptPageId = new AtomicInteger(0);
		billOfReceiptDetailPageId = new AtomicInteger(0);
		
		rsvcBillOfReceipt = new CSRestService<>(urlBillOfReceipt);
		rsvcBillOfReceiptDetail = new CSRestService<>(urlBillOfReceiptDetail);
		
		rsvcBillOfReceipt.fetch(billOfReceiptPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<BillOfReceipt>>() {});
		tblBillOfReceipt.setItems(rsvcBillOfReceipt.getDataAsObservableList());
		
		tblBillOfReceipt.onDataNeeded(()->{
			rsvcBillOfReceipt.fetch(billOfReceiptPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<BillOfReceipt>>() {});
			tblBillOfReceipt.addItems(rsvcBillOfReceipt.getDataAsObservableList());
		});
		
		tblBillOfReceipt.onRowSelectionChanged((oldRow, newRow) -> {
			BillOfReceipt newRowData = (BillOfReceipt) newRow;
			billOfReceiptDetailPageId.set(0);
			rsvcBillOfReceiptDetail.setUrl(urlBillOfReceiptDetail + newRowData.getId().toString());
			rsvcBillOfReceiptDetail.fetch(billOfReceiptDetailPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<BillOfReceiptDetail>>() {});
			tblBillOfReceiptDetail.setItems(rsvcBillOfReceiptDetail.getDataAsObservableList());

		});
		
		tblBillOfReceipt.onRowDoubleClick((row)->{
			tblBillOfReceipt.showDoubleClickDefaultAction = true;
		});
		
		//DETAILS
		tblBillOfReceiptDetail.setParentTable(tblBillOfReceipt);
		tblBillOfReceiptDetail.setAddEditDialog("Warehouse_ReceivingItemsDetailAddEditController");
		
		//NABUDZ da se refreshuje grid kada se u childu edituje/doda. Inace CSTable component refresh ne radi ako je dublje od jednog nivoa
		tblBillOfReceiptDetail.btnEdit.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			rsvcBillOfReceiptDetail.fetch(0, new ParameterizedTypeReference<JsonResponse<BillOfReceiptDetail>>() {});
			tblBillOfReceiptDetail.setItems(rsvcBillOfReceiptDetail.getDataAsObservableList());
		});
		
		tblBillOfReceiptDetail.onRowDoubleClick((row)->{
			tblBillOfReceiptDetail.showDoubleClickDefaultAction = true;
		});
		
		btnPrint.setOnAction( e-> {
			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("JSON_INPUT_1", CSReportView.getJsonDataSourceFromUrl("http://localhost:9999/api/v1/currency/2"));
			new CSReportView("invoice", paramMap);
		});
	}
}
