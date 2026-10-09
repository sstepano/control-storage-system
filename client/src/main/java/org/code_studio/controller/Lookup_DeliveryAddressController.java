package org.code_studio.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;

import java.util.ArrayList;
import java.util.List;

import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.DeliveryAddress;
import org.code_studio.database.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

public class Lookup_DeliveryAddressController extends BaseController {

	@FXML private CSDialogButtons dialogButtons;
	@FXML private CSTable<DeliveryAddress> tblDeliveryAddress;
	
	@SuppressWarnings("unused")
	private int mode;
	private final String addEditControllerName = "Lookup_DeliveryAddressAddEditController";
	private CSTable<Client> tblClient;
	private Client client;
	
	private CSTable<DeliveryAddress> tblDeliveryAddressOrigin;
	
	private final String urlDeliveryAddress = "/deliveryAddress/allByClientId/";
	private CSRestService<DeliveryAddress> rsDeliveryAddress;
	
	private final String urlDeliveryAddressDelete = "/deliveryAddress";
	private CSRestService<DeliveryAddress> rsDeliveryAddressDelete;
	
	List<Object> lstControllerParam;
	
	@SuppressWarnings("unchecked")
	public Lookup_DeliveryAddressController(Object controllerParam, int mode, ApplicationContext ctx) {
		lstControllerParam = (List<Object>) controllerParam;
		this.mode = mode;
		this.tblDeliveryAddressOrigin = (CSTable<DeliveryAddress>) lstControllerParam.get(0);
		this.tblClient = (CSTable<Client>) tblDeliveryAddressOrigin.getParentTable();
		this.client = tblClient != null ? tblClient.getSelectedItem() : null;
	}
	
	public void initialize() {
		try {
			List<Object> lstControllerParam = new ArrayList<Object>();
			lstControllerParam.add(client);
			lstControllerParam.add(tblDeliveryAddress);
			
			tblDeliveryAddress.setAddEditDialog(addEditControllerName, lstControllerParam);
			tblDeliveryAddress.setTopLabelText(client.getName());
			rsDeliveryAddress = new CSRestService<DeliveryAddress>(urlDeliveryAddress);
			rsDeliveryAddress.setUrl(urlDeliveryAddress + client.getId());
			rsDeliveryAddress.fetch(new ParameterizedTypeReference<JsonResponse<DeliveryAddress>>() {});
			tblDeliveryAddress.setItems(rsDeliveryAddress.getDataAsObservableList());
			
			rsDeliveryAddressDelete = new CSRestService<DeliveryAddress>(urlDeliveryAddressDelete);
			tblDeliveryAddress.setRestServiceDelete(rsDeliveryAddressDelete);
			
			//nemamo save button ovde
			//btnSave.setOnAction(e->{});
			
			dialogButtons.getCancelButton().setOnAction(e->{
				if (tblDeliveryAddress.tableView.getItems().size() > 0) {
					this.setReturnValue(tblDeliveryAddress.tableView.getItems().get(0));
				}
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
			tblDeliveryAddress.onRowDoubleClick((row)-> {
				tblDeliveryAddress.showDoubleClickDefaultAction = true;
			});
			
			tblDeliveryAddress.onRowSelectionChanged((oldRow, newRow)->{
				this.setReturnValue(newRow);
			});
						
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END

}
