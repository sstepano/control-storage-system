package org.code_studio.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTableColumn;
import org.code_studio.database.ClientCatalog;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Items_ClientCatalogController extends BaseController implements Initializable {

	@FXML private CSTable <ClientCatalog> mainTable;

	public Items_ClientCatalogController() {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		AtomicInteger pageId = new AtomicInteger(0);
		CSRestService<ClientCatalog> restService = new CSRestService<>("/clientCatalog/allPageable/");
		restService.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<ClientCatalog>>() {});
		/*
		LinkedHashMap<String, String> clientColumns = new LinkedHashMap<>();
		clientColumns.put("Šifra", "Id");
		clientColumns.put("Šifra kataloga", "Code");
		clientColumns.put("Naziv", "Name");
		clientColumns.put("Šifra klijenta", "ClientId");
		clientColumns.put("Napomena", "Description");
		mainTable.setColumns(clientColumns, "ClientCatalog");
		*/
		List<CSTableColumn> columnsList = new ArrayList<>();
		columnsList.addAll(Arrays.asList(
				new CSTableColumn("Šifra", "Id"),
				new CSTableColumn("Šifra kataloga", "Code"),
				new CSTableColumn("Naziv", "Name"),
				new CSTableColumn("Šifra klijenta", "ClientId"),
				new CSTableColumn("Napomena", "Description", 300)
				));
		mainTable.setColumns(columnsList);
		
		mainTable.setItems(restService.getDataAsObservableList());
		/*
		mainTable.tableView.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true) {
					ScrollBar sb = mainTable.getVerticalScrollbar();
					sb.valueProperty().addListener((arg, oldVal, newVal)-> {
						if (newVal.doubleValue() > 0.75) {
							System.out.println("Scroll passed 3/4");
							restService.fetch(pageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<ClientCatalog>>() {});
							mainTable.tableView.getItems().addAll(restService.getDataAsObservableList());
						}
					});
				}
			}
		});
		*/
		
	} //initialize END
	
}
