package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.database.ItemCatalog;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Items_ItemCatalogController extends BaseController implements Initializable {

	@FXML private CSTable <ItemCatalog> mainTable;
	CSRestService<ItemCatalog> mainRestService;
	AtomicInteger pageId;
	private final String defaultUrl = "/itemCatalog";

	public Items_ItemCatalogController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		mainRestService = new CSRestService<>(defaultUrl);
		
		mainRestService.fetch(new ParameterizedTypeReference<JsonResponse<ItemCatalog>>() {});
		mainTable.setItems(mainRestService.getDataAsObservableList());

		mainTable.onRowDoubleClick(( _ ) -> {
			mainTable.showDoubleClickDefaultAction = true;
		});
	}
}
