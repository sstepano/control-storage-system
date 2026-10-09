package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.Currency;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Lookup_CurrencyController extends BaseController implements Initializable {

	@FXML private CSTable <Currency> mainTable;
	CSRestService<Currency> mainRestService;
	AtomicInteger pageId;
	private final String defaultUrl = "/currency";
	private final String addEditControllerName = "Lookup_CurrencyAddEditController";
	private Object selectedAddEditTableItem = null;

	public Lookup_CurrencyController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		mainRestService = new CSRestService<>(defaultUrl);
		mainRestService.fetch(new ParameterizedTypeReference<JsonResponse<Currency>>() {});
		mainTable.setItems(mainRestService.getDataAsObservableList());
		
		mainTable.btnAdd.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController(addEditControllerName, selectedAddEditTableItem, 0), mainTable, null);
		});

		mainTable.btnEdit.setOnAction( _ -> {
			selectedAddEditTableItem = mainTable.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditControllerName, selectedAddEditTableItem, 1), mainTable, mainTable.getSelectedItem().getDescription());
		});

		mainTable.onRowDoubleClick(( _ ) -> {
			mainTable.showDoubleClickDefaultAction = true;
		});
	}
}
