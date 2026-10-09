package org.code_studio.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.Warehouse;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Dobanovci_InternalBillOfLadingController extends BaseController {

	@FXML private CSTable <Warehouse> tblBillOfLading;
	@FXML private CSTable <Warehouse> tblBillOfLadingDetail;
	@FXML private Button btnAddDetail;
	CSRestService<Warehouse> mainRestService;
	AtomicInteger pageId;
	//private final String defaultUrl = "/warehouse";

	private final String internalBillOfLadingAddEditControllerName  = "Warehouse_InternalBillOfLadingAddEditController";
	private final String internalBillOfLadingDetailAddEditControllerName  = "Warehouse_InternalBillOfLadingDetailAddEditController";

	public Dobanovci_InternalBillOfLadingController() {
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

		tblBillOfLading.btnAdd.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController(internalBillOfLadingAddEditControllerName, tblBillOfLading.getSelectedItem(), 0), tblBillOfLading
					, "INTERNE PRENOSNICE");
					//tblBillOfLading.tableView.refresh();
		});

		tblBillOfLadingDetail.btnAdd.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController(internalBillOfLadingDetailAddEditControllerName, tblBillOfLadingDetail.getSelectedItem(), 0), tblBillOfLadingDetail
					, "INTERNE PRENOSNICE");
					//tblBillOfLading.tableView.refresh();
		});

		tblBillOfLadingDetail.btnEdit.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController(internalBillOfLadingDetailAddEditControllerName, tblBillOfLadingDetail.getSelectedItem(), 1), tblBillOfLadingDetail
					, "INTERNE PRENOSNICE");
					//tblBillOfLading.tableView.refresh();
		});

		btnAddDetail.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController(internalBillOfLadingDetailAddEditControllerName, null, 0), tblBillOfLadingDetail
					, "INTERNE PRENOSNICE");
		});


	}
}
