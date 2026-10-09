package org.code_studio.controller;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.ClientCategory;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Lookup_ClientCategoryAddEditController extends BaseController {
	@FXML private Button btnSave;
	@FXML private Button btnCancel;

	@FXML private CSTextField tfId;
	@FXML private CSTextField tfName;
	@FXML private TextArea taDescription;
	
	private ClientCategory clientCategory;
	private CSTable<ClientCategory> tblClientCategory;
	private int mode;
	private CSRestService<ClientCategory> rsAddUpdateService;
	private final String urlClientCategory = "/clientCategory"; 

	public Lookup_ClientCategoryAddEditController() {}
	
	@SuppressWarnings("unchecked")
	public Lookup_ClientCategoryAddEditController(Object controlerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.tblClientCategory = (CSTable<ClientCategory>) controlerParam;
		this.clientCategory = this.tblClientCategory.getSelectedItem();
	}

	public void initialize() {
		try {
			rsAddUpdateService = new CSRestService<>(urlClientCategory);
			
			if (mode == 0) {
				clientCategory = new ClientCategory();
			} else {
				tfId.setText(clientCategory.getId().toString());
				tfName.setText(clientCategory.getName());
				taDescription.setText(clientCategory.getDescription());
				btnSave.setDisable(tfName.getText().isBlank());
			}
	
			btnSave.setOnAction(e->{
				clientCategory.setName(tfName.getText());
				clientCategory.setDescription(taDescription.getText());
				
				ClientCategory insertedItem = rsAddUpdateService.addOrUpdate(clientCategory);
				
				if (mode == 0) {
					tblClientCategory.addItem(insertedItem);
				}
				
				((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
			});
	
			btnCancel.setOnAction(e->{
				((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
			});
			
			tfName.onTextChanged( (_, newText) -> {
				btnSave.setDisable(newText.isBlank());
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END

}
