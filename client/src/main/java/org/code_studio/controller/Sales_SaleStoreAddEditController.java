package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.database.Client;
import org.code_studio.database.ClientStore;
import org.code_studio.rest.CSRestService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Sales_SaleStoreAddEditController extends BaseController implements Initializable {

	@FXML Button btnSave;
	@FXML Button btnCancel;
	
	@FXML TextField tfClientName;
	@FXML TextField tfStoreName;
	@FXML TextField tfCity;
	@FXML TextField tfAddress;
	
	private CSTable<ClientStore> tblClientStore;
	private CSTable<Client> tblClient;
	private final String urlSave = "/clientStore";
	private CSRestService<ClientStore> rsSave;
	private int mode;
	private Client client;
	private ClientStore clientStore;
	
	@SuppressWarnings("unchecked")
	public Sales_SaleStoreAddEditController(Object controllerParam, int mode) {
		this.mode = mode;
		this.tblClientStore = (CSTable<ClientStore>)  controllerParam;
		this.tblClient = (CSTable<Client>) tblClientStore.getParentTable();
		this.client = this.tblClient.getSelectedItem();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rsSave = new CSRestService<ClientStore>(urlSave);
		
		if (mode == 0) {
			clientStore = new ClientStore();
			clientStore.setClient(this.client);
		} else { 
			clientStore = tblClientStore.getSelectedItem();
		}

		//set values to the form fields
		tfClientName.setText(clientStore.getClient().getName());
		tfStoreName.setText(clientStore.getName());
		tfCity.setText(clientStore.getCity());
		tfAddress.setText(clientStore.getAddress());
		
		btnSave.setOnAction( e-> {
			clientStore.setName(tfStoreName.getText());
			clientStore.setCity(tfCity.getText());
			clientStore.setAddress(tfAddress.getText());
			ClientStore savedClientStore = rsSave.addOrUpdate(clientStore);
			
			if (mode == 0) {
				tblClientStore.tableView.getItems().add(savedClientStore);
			}
			
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});


		btnCancel.setOnAction(e->{
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

	}
	
}
