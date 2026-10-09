package org.code_studio.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.database.Warehouse;
import org.code_studio.rest.CSRestService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Dobanovci_ItemReceiveByBarcodeController extends BaseController {

	@FXML private CSTable <Warehouse> tblBillOfLading;
	@FXML private CSTable <Warehouse> tblBillOfLadingDetail;
	@FXML private Button btnAddDetail;
	CSRestService<Warehouse> mainRestService;
	AtomicInteger pageId;
	//private final String defaultUrl = "/warehouse";

	public Dobanovci_ItemReceiveByBarcodeController() {
		//mainRestService = new BaseRestService<>(defaultUrl);
	}
	
	public void initialize() {
		/*
		mainRestService.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>() {});
		mainTable.setItems(mainRestService.getDataAsObservableList());

		mainTable.onRowDoubleClick((rowData) -> {
			mainTable.showDoubleClickDefaultAction = true;
		});
		*/

		/***
		tblBillOfLading.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(internalBillOfLadingAddEditControllerName, tblBillOfLading.getSelectedItem()), tblBillOfLading
					, "INTERNE PRENOSNICE");
					//tblBillOfLading.tableView.refresh();
		});

		tblBillOfLadingDetail.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(internalBillOfLadingDetailAddEditControllerName, tblBillOfLadingDetail.getSelectedItem()), tblBillOfLadingDetail
					, "INTERNE PRENOSNICE");
					//tblBillOfLading.tableView.refresh();
		});

		tblBillOfLadingDetail.btnEdit.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(internalBillOfLadingDetailAddEditControllerName, tblBillOfLadingDetail.getSelectedItem()), tblBillOfLadingDetail
					, "INTERNE PRENOSNICE");
					//tblBillOfLading.tableView.refresh();
		});

		btnAddDetail.setOnAction( e-> {
			Common.displayForm(ControllerFactory.getController(internalBillOfLadingDetailAddEditControllerName, null), tblBillOfLadingDetail
					, "INTERNE PRENOSNICE");
		});
		***/


	}
}
