package org.code_studio.controller;

import org.code_studio.component.ui.CSClientTable;
import org.code_studio.component.ui.CSDialogButtons;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;

public class Sales_ClientSelectController extends BaseController {
	
	@FXML CSDialogButtons dialogButtons;
	@FXML private CSClientTable tblClient;

	public Sales_ClientSelectController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	
	public void initialize() {
		tblClient.refresh();
		
		tblClient.csTable.onRowDoubleClick( e-> {
			System.out.println("rowdblclick");
			this.setReturnValue(tblClient.csTable.getSelectedItem());
			dialogButtons.closeForm();
		});
		
		dialogButtons.getSaveButton().setOnAction( e-> {
			this.setReturnValue(tblClient.csTable.getSelectedItem());
			dialogButtons.closeForm();
		});
		
	} //initialize END
	
	@Override
	public void postInitialize() {
		tblClient.csTable.tableView.requestFocus();
	}
	
}
