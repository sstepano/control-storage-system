package org.code_studio.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.BillOfReceipt;
import org.code_studio.database.BillOfReceiptDetail;
import org.code_studio.database.Item;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Warehouse_ReceivingItemsDetailAddEditController extends BaseController implements Initializable {

	@FXML private CSTable <Item> tblItem;
	@FXML private CSTable <BillOfReceiptDetail> tblBillOfReceiptDetail;
	
	private CSTable <BillOfReceipt> tblBillOfReceipt;

	CSRestService<Item> rsvcItem;
	CSRestService<BillOfReceiptDetail> rsvcBillOfReceiptDetail;
	
	AtomicInteger itemPageId;
	AtomicInteger billOfReceiptDetailPageId;
	
	private final String urlItem = "/item/allPageable/";
	private final String urlBillOfReceiptDetail = "/billOfReceiptDetail/allPageableByBillOfReceiptId/";
	
	private int mode;
	private final String addEditDetailControllerName = "Warehouse_ReceivingItemsDetailAddEditDetailController";
	
	@SuppressWarnings("unchecked")
	public Warehouse_ReceivingItemsDetailAddEditController(Object controllerParam, int mode) {
		this.mode = mode;
		tblBillOfReceiptDetail = (CSTable<BillOfReceiptDetail>) controllerParam;
		tblBillOfReceipt = (CSTable <BillOfReceipt>) tblBillOfReceiptDetail.getParentTable();
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		//LEFT - ITEM
		itemPageId = new AtomicInteger(0);
		rsvcItem = new CSRestService<>(urlItem);
		rsvcItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
		tblItem.setItems(rsvcItem.getDataAsObservableList());

		tblItem.onDataNeeded(()->{
			rsvcItem.fetch(itemPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
			tblItem.addItems(rsvcItem.getDataAsObservableList());
		});

		
		//RIGHT - BILL OF RECEIPT DETAIL
		billOfReceiptDetailPageId = new AtomicInteger(0);
		rsvcBillOfReceiptDetail = new CSRestService<>(urlBillOfReceiptDetail);

		BillOfReceipt billOfReceipt = tblBillOfReceipt.getSelectedItem();
		rsvcBillOfReceiptDetail.setUrl(urlBillOfReceiptDetail + billOfReceipt.getId().toString());
		rsvcBillOfReceiptDetail.fetch(billOfReceiptDetailPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<BillOfReceiptDetail>>() {});
		tblBillOfReceiptDetail.setItems(rsvcBillOfReceiptDetail.getDataAsObservableList());
		
		tblBillOfReceiptDetail.onRowDoubleClick((row)->{
			tblBillOfReceiptDetail.showDoubleClickDefaultAction = true;
		});
		
		// insert
		if (mode == 0) {
		}
		else { //edit
		}

		//Trebaju mi oebe tabele u prosledjivanju
		List<CSTable<?>> lstObjectParam = new ArrayList<CSTable<?>>();
		lstObjectParam.add(tblItem);
		lstObjectParam.add(tblBillOfReceiptDetail);
		
		//tblBillOfReceiptDetail.setAddEditDialog(addEditDetailControllerName);//ovde ne mozemo ovo jer nam treba razlicit item za prosledjivanje od insert i edit-a
		tblBillOfReceiptDetail.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(addEditDetailControllerName, lstObjectParam, 0), tblItem, "DODAVANJE STAVKE PRENOSNICE");
			//TODO: OVde se ne refreshuje detail grid kada editujemo ili dodamo novi red
		});

		tblBillOfReceiptDetail.btnEdit.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(addEditDetailControllerName, lstObjectParam, 1), tblBillOfReceiptDetail, "IZMENA STAVKE PRENOSNICE");
		});

	}
}
