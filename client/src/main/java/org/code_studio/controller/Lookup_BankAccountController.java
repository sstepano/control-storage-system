package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.BankAccount;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Lookup_BankAccountController extends BaseController implements Initializable {

	@FXML private CSTable <BankAccount> mainTable;
	CSRestService<BankAccount> mainRestService;
	AtomicInteger pageId;
	private final String defaultUrl = "/bankAccount";
	private final String addEditControllerName = "Lookup_BankAccountAddEditController";
	private Object selectedAddEditTableItem = null;

	public Lookup_BankAccountController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		mainRestService = new CSRestService<>(defaultUrl);
		mainRestService.fetch(new ParameterizedTypeReference<JsonResponse<BankAccount>>() {});
		mainTable.setItems(mainRestService.getDataAsObservableList());

		mainTable.onRowDoubleClick(( _ ) -> {
			mainTable.showDoubleClickDefaultAction = true;
		});

		mainTable.btnAdd.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController(addEditControllerName, null, 0), mainTable, null);
		});

		mainTable.btnEdit.setOnAction( _ -> {
			selectedAddEditTableItem = mainTable.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditControllerName, selectedAddEditTableItem, 1), mainTable, mainTable.getSelectedItem().getAccountNumber());
		});
	}
}
