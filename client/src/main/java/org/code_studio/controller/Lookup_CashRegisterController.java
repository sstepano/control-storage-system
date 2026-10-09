package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.CashDesk;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Lookup_CashRegisterController extends BaseController implements Initializable {

	@FXML private CSTable <CashDesk> mainTable;
	CSRestService<CashDesk> mainRestService;
	AtomicInteger pageId;
	private final String defaultUrl = "/cashDesk";
	private final String addEditControllerName = "Lookup_CashDeskAddEditController";
	private Object selectedAddEditTableItem = null;

	public Lookup_CashRegisterController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		mainRestService = new CSRestService<>(defaultUrl);
		mainRestService.fetch(new ParameterizedTypeReference<JsonResponse<CashDesk>>() {});
		mainTable.setItems(mainRestService.getDataAsObservableList());

		mainTable.onRowDoubleClick(( _ ) -> {
			mainTable.showDoubleClickDefaultAction = true;
		});

		mainTable.btnAdd.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController(addEditControllerName, null, 0), mainTable, null);
		});

		mainTable.btnEdit.setOnAction( _ -> {
			selectedAddEditTableItem = mainTable.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditControllerName, selectedAddEditTableItem, 1), mainTable, mainTable.getSelectedItem().getName());
		});
		
	}
}
