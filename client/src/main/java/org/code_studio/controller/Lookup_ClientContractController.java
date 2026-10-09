package org.code_studio.controller;

import javafx.fxml.FXML;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;

import java.util.ArrayList;
import java.util.List;

import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.ClientContract;
import org.code_studio.database.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

public class Lookup_ClientContractController extends BaseController {

	@FXML private CSDialogButtons dialogButtons;
	@FXML private CSTable<ClientContract> tblClientContract;

	@SuppressWarnings("unused")
	private int mode;
	private final String addEditControllerName = "Lookup_ClientContractAddEditController";
	private CSTable<Client> tblClient;
	private Client client;
	
	private final String urlClientContract = "/clientContract/allByClientId/";
	private CSRestService<ClientContract> rsClientContract;
	
	private final String urlClientContractDelete = "/clientContract";
	private CSRestService<ClientContract> rsClientContractDelete;
	
	@SuppressWarnings("unchecked")
	public Lookup_ClientContractController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.tblClient = (CSTable<Client>) controllerParam;
		this.client = tblClient != null ? tblClient.getSelectedItem() : null;
	}
	
	public void initialize() {
		try {
			List<Object> lstControllerParam = new ArrayList<Object>();
			lstControllerParam.add(client);
			lstControllerParam.add(tblClientContract);
			
			tblClientContract.setAddEditDialog(addEditControllerName, lstControllerParam);
			tblClientContract.setTopLabelText(client.getName());
			rsClientContract = new CSRestService<ClientContract>(urlClientContract);
			rsClientContract.setUrl(urlClientContract + client.getId());
			rsClientContract.fetch(new ParameterizedTypeReference<JsonResponse<ClientContract>>() {});
			tblClientContract.setItems(rsClientContract.getDataAsObservableList());
			
			rsClientContractDelete = new CSRestService<ClientContract>(urlClientContractDelete);
			tblClientContract.setRestServiceDelete(rsClientContractDelete);
			
			//nemamo save button ovde
			//btnSave.setOnAction(e->{});
			
			dialogButtons.getCancelButton().setOnAction( _ -> {
				if (tblClientContract.tableView.getItems().size() > 0) {
					this.setReturnValue(tblClientContract.tableView.getItems().get(0));
				}
				dialogButtons.closeForm();
			});
			
			tblClientContract.onRowDoubleClick(( _ )-> {
				tblClientContract.showDoubleClickDefaultAction = true;
			});
			
			tblClientContract.onRowSelectionChanged((_, newRow)->{
				this.setReturnValue(newRow);
			});
						
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END

}
