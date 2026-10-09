package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Client;
import org.code_studio.database.ClientContact;
import org.code_studio.rest.CSRestService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Sales_ClientContactAddEditController extends BaseController implements Initializable {

	@FXML private CSDialogButtons dialogButtons;

	@FXML private TextField tfTitle;
	@FXML private TextField tfName;
	@FXML private TextField tfEmail;
	@FXML private TextField tfPhone;
	@FXML private TextField tfMobile;
	@FXML private CheckBox  ckbIncludeInCC; 
	@FXML private CheckBox  ckbIncludeInATT;
	@FXML private TextArea taDescription;

	private int mode;
	private Client client;
	private CSTable<ClientContact> tblClientContact;
	private ClientContact clientContact;
	
	//Service AddEdit
	CSRestService<Client> rsClient;
	CSRestService<ClientContact> rsAddUpdateService;
	
	public Sales_ClientContactAddEditController() {}
	
	@SuppressWarnings("unchecked")
	public Sales_ClientContactAddEditController(Object controllerParam, int mode) {
		// controllerParam UVEK mora da bude tabela od koje je potekao add/edit
		// na osnovu nje uvek mogu da nadjem selected item, kao i parent selected item
		// Ovo gore omogucavamo tako sto cemo da koristimo setAddEditDialog CSTable.setAddEdit metod
		// u formama gde definisemo tabelu (u ovom slucaju Sales_ClientController)
		this.tblClientContact = (CSTable<ClientContact>) controllerParam;
		this.mode = mode;
	}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		rsClient = new CSRestService<>("/client");
		rsAddUpdateService = new CSRestService<>("/clientContact");
		
		clientContact = tblClientContact.getSelectedItem();
		
		if (tblClientContact.getParentTable() != null) { //poziv iz client forme, clientcontact child tabele
			this.client = (Client) tblClientContact.getParentTable().getSelectedItem();
		}

		if (mode == 1) { //Edit mode
			tfTitle.setText(clientContact.getTitle());
			tfName.setText(clientContact.getName());
			tfEmail.setText(clientContact.getEmail());
			tfPhone.setText(clientContact.getPhone());
			tfMobile.setText(clientContact.getMobile());
			ckbIncludeInCC.setSelected(clientContact.getIncludeInEmailCc());
			ckbIncludeInATT.setSelected(clientContact.getIncludeInAttachment());
			taDescription.setText(clientContact.getDescription());
		}
		
		dialogButtons.getSaveButton().setOnAction(e-> {
			if (this.client != null) {
				if (mode == 0) { //add
					ClientContact clientContact = new ClientContact();
					clientContact.setClientId(this.client.getId());
					clientContact.setTitle(tfTitle.getText());
					clientContact.setName(tfName.getText());
					clientContact.setEmail(tfEmail.getText());
					clientContact.setPhone(tfPhone.getText());
					clientContact.setMobile(tfMobile.getText());
					clientContact.setIncludeInEmailCc(ckbIncludeInCC.isSelected());
					clientContact.setIncludeInAttachment(ckbIncludeInATT.isSelected());
					clientContact.setDescription(taDescription.getText());
					this.clientContact = clientContact;
					tblClientContact.tableView.getItems().add(clientContact); // EXPLICITNI update items-a u gridu ... sr*nje!!!
				} else { //edit
					clientContact.setClientId(this.client.getId());
					clientContact.setTitle(tfTitle.getText());
					clientContact.setName(tfName.getText());
					clientContact.setEmail(tfEmail.getText());
					clientContact.setPhone(tfPhone.getText());
					clientContact.setMobile(tfMobile.getText());
					clientContact.setIncludeInEmailCc(ckbIncludeInCC.isSelected());
					clientContact.setIncludeInAttachment(ckbIncludeInATT.isSelected());
					clientContact.setDescription(taDescription.getText());
				}
				
				//OVde se dodaje novi red ako se ne fetchuju podaci sa servera, zato sto je ID = 0 kada se dodaje novi slog.
				// Moracu kod novog dodavanja da fetchujem podatke sa servera nekako!!!
				//rsClient.addOrUpdate(this.client);
				rsAddUpdateService.addOrUpdate(clientContact);
			}
		
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
		
		dialogButtons.setValidation(
				  new CSEmptyFieldValidator(tfName)
				, new CSEmptyFieldValidator(tfTitle)
			);

	}

}
