package org.code_studio.controller;

import javafx.fxml.FXML;
import org.code_studio.main.Common;

import java.util.ArrayList;
import java.util.List;

import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSClientBankAccountTable;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Client;
import org.springframework.context.ApplicationContext;

public class Lookup_ClientBankAccountController extends BaseController {

	@FXML private CSDialogButtons dialogButtons;
	@FXML private CSClientBankAccountTable tblClientBankAccount;
	
	private int mode;
	private CSTable<Client> tblClient;
	private Client client;

	
	@SuppressWarnings("unchecked")
	public Lookup_ClientBankAccountController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.tblClient = (CSTable<Client>) controllerParam;
		this.client = tblClient != null ? tblClient.getSelectedItem() : null;
	}
	
	public void initialize() {
		try {
			// mod da selektujemo bank account
			if(mode == 2) {
				dialogButtons.setSaveButtonText("Odaberi");
			} else { // add/edit mod
				tblClientBankAccount.setClientId(client.getId().intValue());
				dialogButtons.getSaveButton().setVisible(false);
				tblClientBankAccount.csTable.setSearchVisible(false);
				tblClientBankAccount.csTable.setTopLabelText(client.getName());
				tblClientBankAccount.csTable.setAddButtonVisible(true);
				tblClientBankAccount.csTable.setEditButtonVisible(true);
				tblClientBankAccount.csTable.setDeleteButtonVisible(true);
				
				List<Object> lstControllerParam = new ArrayList<>();
				lstControllerParam.add(client);
				lstControllerParam.add(tblClientBankAccount.csTable);
				tblClientBankAccount.csTable.setAddEditDialog("Lookup_ClientBankAccountAddEditController", lstControllerParam);
			}
			
			dialogButtons.getSaveButton().setOnAction( _ -> {
				if (mode == 2) {
					setReturnValueAndCloseForm();
				}
			});
			
			tblClientBankAccount.csTable.onRowSelectionChanged((_, newRow) -> {
				if (newRow != null) {
					dialogButtons.getSaveButton().setDisable(false);
				} else {
					dialogButtons.getSaveButton().setDisable(true);
				}
			});
			
			tblClientBankAccount.csTable.onRowDoubleClick(( _ )-> {
				if (mode == 2) {
					tblClientBankAccount.csTable.showDoubleClickDefaultAction = false;
					setReturnValueAndCloseForm();
				} else {
					tblClientBankAccount.csTable.showDoubleClickDefaultAction = true;
				}
			});
			
			
			// popunjavamo podacima na kraju inicijalizacije
			tblClientBankAccount.refresh();
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END
	
	private void setReturnValueAndCloseForm() {
		this.setReturnValue(tblClientBankAccount.csTable.getSelectedItem());
		dialogButtons.closeForm();
	}
}
