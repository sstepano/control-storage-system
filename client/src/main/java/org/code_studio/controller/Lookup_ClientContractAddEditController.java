package org.code_studio.controller;

import java.util.List;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.ClientContract;
import org.code_studio.database.Client;
import org.code_studio.database.ClientName;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class Lookup_ClientContractAddEditController extends BaseController {
	
	@FXML private CSDialogButtons dialogButtons;
	
	@FXML private TextField tfNumber;
	@FXML private CSTextField tfValue;
	@FXML private CSDatePicker dtValidFrom;
	@FXML private CSDatePicker dtValidTo;
	@FXML private TextArea taDescription;

	private int mode;
	private List<Object> lstControllerParam;
	private Client client;
	private CSTable<ClientContract> tblClientContract;
	private ClientContract clientContract;
	
	private final String urlClientContract = "/clientContract";
	CSRestService<ClientContract> rsClientContract;

	public Lookup_ClientContractAddEditController() {}
	
	@SuppressWarnings("unchecked")
	public Lookup_ClientContractAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		lstControllerParam = (List<Object>) controllerParam;
		client = (Client) lstControllerParam.get(0);
		tblClientContract = (CSTable<ClientContract>) lstControllerParam.get(1);
		clientContract = tblClientContract.getSelectedItem();
	}


	public void initialize() {
		try {
			rsClientContract = new CSRestService<>(urlClientContract);
	
			if (mode == 0) {
				clientContract = new ClientContract();
				clientContract.setClientId(client.getId().intValue());
				clientContract.setClient(new ClientName(client.getId().intValue(), client.getName())); //budz za naziv klijenta u koloni	
			}
			else {
				tfNumber.setText(clientContract.getContractNumber());
				
				if (clientContract.getValidFrom() != null) {
					dtValidFrom.setValue(clientContract.getValidFrom());
				}
				if (clientContract.getValidTo() != null) {
					dtValidTo.setValue(clientContract.getValidFrom());
				}
				taDescription.setText(clientContract.getDescription());
			}
			
			dialogButtons.getSaveButton().setOnAction( _ -> {
				clientContract.setContractNumber(tfNumber.getText());
				if (dtValidFrom.getValue() != null) {
					clientContract.setValidFrom(dtValidFrom.getValue());
				}
				if (dtValidTo.getValue() != null) {
					clientContract.setValidTo(dtValidTo.getValue());
				}

				clientContract.setDescription(taDescription.getText());
				ClientContract insertedItem = rsClientContract.addOrUpdate(clientContract);
				
				if (mode == 0) {
					tblClientContract.addItem(insertedItem);
				}
				
				dialogButtons.closeForm();
			});
			
			dialogButtons.setValidation(
				new CSEmptyFieldValidator(tfNumber)
			);
		
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END

}
