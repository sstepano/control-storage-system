package org.code_studio.controller;

import javafx.fxml.FXML;

import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.database.ClientGroup;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;

import org.springframework.context.ApplicationContext;


public class Lookup_ClientGroupAddEditController extends BaseController {
	@FXML private Button btnSave;
	@FXML private Button btnCancel;

	@FXML private CSTextField tfId;
	@FXML private CSTextField tfName;
	@FXML private CheckBox ckbIsSupplierGroup;
	@FXML private TextArea taDescription;
	
	private ClientGroup clientGroup;
	private CSTable<ClientGroup> tblClientGroup;
	private int mode;
	private CSRestService<ClientGroup> rsAddUpdateService;
	private final String urlClientGroup = "/clientGroup"; 

	public Lookup_ClientGroupAddEditController() {}
	
	@SuppressWarnings("unchecked")
	public Lookup_ClientGroupAddEditController(Object controlerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.tblClientGroup = (CSTable<ClientGroup>) controlerParam;
		this.clientGroup = this.tblClientGroup.getSelectedItem();
	}

	public void initialize() {
		try {
			rsAddUpdateService = new CSRestService<>(urlClientGroup);
			
			if (mode == 0) {
				clientGroup = new ClientGroup();
			} else {
				tfId.setText(clientGroup.getId().toString());
				tfName.setText(clientGroup.getName());
				ckbIsSupplierGroup.setSelected(clientGroup.getIsSupplierGroup());
				taDescription.setText(clientGroup.getDescription());
				btnSave.setDisable(tfName.getText().isBlank());
			}
	
			btnSave.setOnAction(e->{
				clientGroup.setName(tfName.getText());
				clientGroup.setIsSupplierGroup(ckbIsSupplierGroup.isSelected());
				clientGroup.setDescription(taDescription.getText());
				
				ClientGroup insertedItem = rsAddUpdateService.addOrUpdate(clientGroup);
				
				if (mode == 0) {
					tblClientGroup.addItem(insertedItem);
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
