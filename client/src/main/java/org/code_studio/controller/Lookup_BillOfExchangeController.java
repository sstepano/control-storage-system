package org.code_studio.controller;

import javafx.fxml.FXML;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;

import java.util.ArrayList;
import java.util.List;

import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.BillOfExchange;
import org.code_studio.database.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

public class Lookup_BillOfExchangeController extends BaseController {

	@FXML private CSDialogButtons dialogButtons;
	@FXML private CSTable<BillOfExchange> tblBillOfExchange;
	
	@SuppressWarnings("unused")
	private int mode;
	private final String addEditControllerName = "Lookup_BillOfExchangeAddEditController";
	private CSTable<Client> tblClient;
	private Client client;
	
	private final String urlBillOfExchange = "/billOfExchange/allByClientId/";
	private CSRestService<BillOfExchange> rsBillOfExchange;
	
	private final String urlBillOfExchangeDelete = "/billOfExchange";
	private CSRestService<BillOfExchange> rsBillOfExchangeDelete;
	
	@SuppressWarnings("unchecked")
	public Lookup_BillOfExchangeController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.tblClient = (CSTable<Client>) controllerParam;
		this.client = tblClient != null ? tblClient.getSelectedItem() : null;
	}
	
	public void initialize() {
		try {
			List<Object> lstControllerParam = new ArrayList<Object>();
			lstControllerParam.add(client);
			lstControllerParam.add(tblBillOfExchange);
			
			tblBillOfExchange.setAddEditDialog(addEditControllerName, lstControllerParam);
			tblBillOfExchange.setTopLabelText(client.getName());
			rsBillOfExchange = new CSRestService<BillOfExchange>(urlBillOfExchange);
			rsBillOfExchange.setUrl(urlBillOfExchange + client.getId());
			rsBillOfExchange.fetch(new ParameterizedTypeReference<JsonResponse<BillOfExchange>>() {});
			tblBillOfExchange.setItems(rsBillOfExchange.getDataAsObservableList());
			
			rsBillOfExchangeDelete = new CSRestService<BillOfExchange>(urlBillOfExchangeDelete);
			tblBillOfExchange.setRestServiceDelete(rsBillOfExchangeDelete);
			
			//nemamo save button ovde
			//btnSave.setOnAction(e->{});
			
			dialogButtons.getCancelButton().setOnAction( _ -> {
				if (tblBillOfExchange.tableView.getItems().size() > 0) {
					this.setReturnValue(tblBillOfExchange.tableView.getItems().get(0));
				}
				dialogButtons.closeForm();
			});
			
			tblBillOfExchange.onRowDoubleClick(( _ )-> {
				tblBillOfExchange.showDoubleClickDefaultAction = true;
			});
			
			tblBillOfExchange.onRowSelectionChanged((_, newRow)->{
				this.setReturnValue(newRow);
			});
						
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END

}
