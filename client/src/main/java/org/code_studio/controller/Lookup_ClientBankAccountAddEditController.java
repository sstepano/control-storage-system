package org.code_studio.controller;

import java.util.List;
import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Bank;
import org.code_studio.database.Client;
import org.code_studio.database.ClientBankAccount;
import org.code_studio.database.ClientName;
import org.code_studio.database.Currency;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Lookup_ClientBankAccountAddEditController extends BaseController {

	@FXML private CSDialogButtons dialogButtons;
	
	@FXML private TextField tfId;
	@FXML private CSComboBox <Bank> cbBank;
	//@FXML private CSComboBox <Currency> cbCurrency;
	@FXML private TextField tfAccountNumber;
	@FXML private TextField tfIban;
	@FXML private TextArea taDescription;

	private int mode;
	private List<Object> lstControllerParam;
	private CSTable<ClientBankAccount> tblClientBankAccount;
	private ClientBankAccount clientBankAccount;
	private Client client;
	
	private final String urlBank = "/bank";
	CSRestService <Bank> rsBank;
	
	@SuppressWarnings("unused")
	private final String urlCurrency = "/currency";
	CSRestService <Currency> rsCurrency;
	
	private final String urlClientBankAccount = "/clientBankAccount";
	CSRestService <ClientBankAccount> rsClientBankAccount;

	public Lookup_ClientBankAccountAddEditController() {}
	
	@SuppressWarnings("unchecked")
	public Lookup_ClientBankAccountAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		lstControllerParam = (List<Object>) controllerParam;
		this.client = (Client) lstControllerParam.get(0);
		this.tblClientBankAccount = (CSTable<ClientBankAccount>) lstControllerParam.get(1);
		if(mode == 1) {
			clientBankAccount = tblClientBankAccount.getSelectedItem();
		}
	}

	public void initialize() {
		try {
			rsClientBankAccount = new CSRestService<>(urlClientBankAccount);
			rsBank = new CSRestService<>(urlBank);
			rsBank.fetch(new ParameterizedTypeReference<JsonResponse<Bank>>() {});
			cbBank.setItemsAndSelectFirstItem(rsBank.getDataAsObservableList());
			
			/* TODO: dodati u bazi ako treba
			  rsCurrency = new BaseRestService<>("/currency");
			  rsCurrency.fetch(new ParameterizedTypeReference<JsonResponse<Currency>>() {});
			  cbCurrency.getItems().addAll(rsCurrency.getDataAsObservableList());
			 */
			 
			if (mode == 0) {
				clientBankAccount = new ClientBankAccount();
				clientBankAccount.setClientId(client.getId().intValue());
				
				//budz kada dodajemo da imamo ime klijenta
				clientBankAccount.setClient(new ClientName(client.getId().intValue(), client.getName()));
				
			} else {
				tfId.setText(clientBankAccount.getId().toString());
				cbBank.select(clientBankAccount.getBank());
				//cbCurrency.select(clientBankAccount());
				tfAccountNumber.setText(clientBankAccount.getAccountNumber());
				tfIban.setText(clientBankAccount.getIban());
				taDescription.setText(clientBankAccount.getNote());
			}
			
			dialogButtons.getSaveButton().setOnAction( _ -> {
				clientBankAccount.setBank(cbBank.getValue());
				clientBankAccount.setAccountNumber(tfAccountNumber.getText());
				clientBankAccount.setIban(tfIban.getText());
				clientBankAccount.setNote(taDescription.getText());
				
				ClientBankAccount insertedItem = rsClientBankAccount.addOrUpdate(clientBankAccount);
				
				if (mode == 0) {
					tblClientBankAccount.addItem(insertedItem);
				}
				
				dialogButtons.closeForm();
			});
			
			dialogButtons.setValidation(
				  new CSEmptyFieldValidator(tfAccountNumber)
			);
		
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end

}
