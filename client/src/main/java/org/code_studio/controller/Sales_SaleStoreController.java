package org.code_studio.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.database.Client;
import org.code_studio.database.ClientStore;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class Sales_SaleStoreController extends BaseController {
	
	@FXML private CSTable <Client> tblClient;
	@FXML private CSTable <ClientStore> tblClientSaleStore;
	
	CSRestService<Client> rsClient;
	CSRestService<Client> rsClientSearch;
	CSRestService<ClientStore> rsClientStore;
	AtomicInteger rsClientPageId;
	
	CSRestService<ClientStore> rsClientStoreDelete;
	
	private final String saleStoreAddEditControllerName = "Sales_SaleStoreAddEditController";
	
	private final String urlClient = "/client/allPageable/";
	private final String urlClientStore = "/clientStore/allByClientId/";
	private final String urlClientSearch = "/client/search/";
	
	private final String urlClientStoreDelete = "/clientStore";
	
	private int mode;
	private Client client;
	private ApplicationContext ctx;
	
	public Sales_SaleStoreController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.client = (Client) controllerParam;
		this.mode = mode;
		this.ctx = ctx;
	}
	

	public void initialize() {
		tblClientSaleStore.setParentTable(tblClient);
		rsClientPageId = new AtomicInteger(0);
		rsClient = new CSRestService<>(urlClient);
		rsClientSearch = new CSRestService<>(urlClientSearch);
		rsClientStore = new CSRestService<>(urlClientStore);
		rsClientStoreDelete = new CSRestService<>(urlClientStoreDelete);

		if (mode == 0 || mode == 1) {
			rsClient.fetch(rsClientPageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.setItems(rsClient.getDataAsObservableList());
		}
				
		tblClient.onDataNeeded(()->{
			rsClient.fetch(rsClientPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.addItems(rsClient.getDataAsObservableList());
		});
		
		tblClient.onRowSelectionChanged((oldRow, newRow)->{
			Client newRowData = (Client) newRow;
			if (newRowData != null) {
				rsClientStore.setUrl(urlClientStore + newRowData.getId().toString());
				rsClientStore.fetch(new ParameterizedTypeReference<JsonResponse<ClientStore>>() {});
				tblClientSaleStore.setItems(rsClientStore.getDataAsObservableList());
			}
		});		

		tblClient.onServerSearch(() -> {
			performSearch();
		});
		
		tblClientSaleStore.setAddEditDialog(saleStoreAddEditControllerName);
		tblClientSaleStore.setRestServiceDelete(rsClientStoreDelete);
		tblClientSaleStore.onRowDoubleClick( e-> {
			if (mode == 0 || mode == 1) {
				tblClientSaleStore.showDoubleClickDefaultAction = true;
			} else if (mode == 2) {
				this.setReturnValue(tblClientSaleStore.getSelectedItem());
				((Stage) ((Node) tblClientSaleStore).getScene().getWindow()).close();
			}
		});
		
		// perform search, TODO: OPTIMIZE
		tblClient.tfSearchBox.setText(client.getName());
		performSearch();
		
	}
	
	private void performSearch() {
		String searchKeyword = tblClient.tfSearchBox.getText();
		
		if (searchKeyword.length() > 0) {
			rsClientSearch.setUrl(urlClientSearch + searchKeyword);
			rsClientSearch.fetch(0, new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.setItems(rsClientSearch.getDataAsObservableList());
		} else {
			//search empty, fetch all data, like when opening form for the first time
			rsClientPageId.set(0);
			rsClient.fetch(rsClientPageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.setItems(rsClient.getDataAsObservableList());
		}
	}

}
