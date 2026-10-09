package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.database.Client;
import org.code_studio.database.ClientContact;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Sales_ClientContactController extends BaseController implements Initializable {

	@FXML private CSClientTable tblClient;
	@FXML private CSTable <ClientContact> tblClientContact;
	
	private final String urlClientContact = "/clientContact/allByClientId/";
	CSRestService <ClientContact> rsvcClientContact;

	public Sales_ClientContactController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		rsvcClientContact = new CSRestService<>(urlClientContact);
		tblClient.refresh();
		
		//CLIENT
		tblClient.csTable.onRowSelectionChanged((oldRow, newRow) -> {
			if (newRow != null) {
				rsvcClientContact.setUrl(urlClientContact + ((Client)newRow).getId().toString()); 
				rsvcClientContact.fetch(new ParameterizedTypeReference<JsonResponse<ClientContact>>() {});
				tblClientContact.setItems(rsvcClientContact.getDataAsObservableList());
			} else {
				tblClientContact.clear();
			}
		});
		

		//CLIENT CONTACT
		tblClientContact.setAddEditDialog("Sales_ClientContactAddEditController");
		tblClientContact.setParentTable(tblClient.csTable);//treba nam da bi dobili parenta, u ovom slucaju Client objekat u addedit formi
		tblClientContact.onRowDoubleClick((rowData) -> {
			tblClientContact.showDoubleClickDefaultAction = true;
		});
		
		/*
		//local search: mainTable.addSearchListener("Name");
		//TODO: Figure out how to move this method to CSTable.
		// Now I cannot use it because <T> cannot be cast in BaseService and returns jibberish
		tblContact.onServerSearch(()->{
			String searchKeyword = tblContact.tfSearchBox.getText();
			
			if (searchKeyword.length() > 0) {
				searchRestService.setUrl(searchUrl + searchKeyword);
				searchRestService.fetch(0, new ParameterizedTypeReference<JsonResponse<ClientContact>>() {});
				tblContact.setItems(searchRestService.getDataAsObservableList());
			} else {
				//search empty, fetch all data, like when opening form for the first time
				pageId.set(0);
				mainRestService.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<ClientContact>>() {});
				tblContact.setItems(mainRestService.getDataAsObservableList());
			}
		});
		*/

	} //initialize END
	
}
