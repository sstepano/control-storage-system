package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.database.Warehouse;
import org.code_studio.rest.CSRestService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Dobanovci_ShowDeclarationController extends BaseController implements Initializable {

	@FXML private CSTable <Warehouse> tblBillOfLading;
	@FXML private CSTable <Warehouse> tblBillOfLadingDetail;
	@FXML private Button btnAddDetail;
	CSRestService<Warehouse> mainRestService;
	AtomicInteger pageId;
	//private final String defaultUrl = "/warehouse";

	public Dobanovci_ShowDeclarationController() {
		//mainRestService = new BaseRestService<>(defaultUrl);
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
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
