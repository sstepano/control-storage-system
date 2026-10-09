package org.code_studio.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.List;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Dbini;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

public class Lookup_DbiniAddEditController extends BaseController {
	@FXML private CSDialogButtons dialogButtons;
	@FXML private CSTextField tfId;
	@FXML private CSTextField tfAttributeName;
	@FXML private CSTextField tfParameterName;
	@FXML private CSTextField tfValue;
	@FXML private TextArea taDescription;
	
	private int mode = 0;
	private Dbini Dbini;
	
	private final String urlMain = "/dbini";
	private CSRestService<Dbini> rsMain;
	
	private List<Object> lstControllerParam;
	private CSTable<Dbini> tblMain;
	
	
	@SuppressWarnings("unchecked")
	public Lookup_DbiniAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		lstControllerParam = (List<Object>) controllerParam;
		tblMain = (CSTable<Dbini>) lstControllerParam.get(0);
	}
	
	public void initialize() {
		try {
			
			if (mode == 0) {
				Dbini = new Dbini();
			} else {
				Dbini = tblMain.getSelectedItem();
				tfId.setText(Dbini.getId().toString());
				tfAttributeName.setText(Dbini.getAttributeName());
				tfParameterName.setText(Dbini.getParameterName());
				tfValue.setText(Dbini.getValue());
				taDescription.setText(Dbini.getDescription());
			}
			
			dialogButtons.setValidation(
				 new CSEmptyFieldValidator(tfAttributeName),
				 new CSEmptyFieldValidator(tfParameterName)
			);
			
			dialogButtons.getSaveButton().setOnAction( e-> {
				rsMain = new CSRestService<Dbini>(urlMain);
				Dbini.setAttributeName(tfAttributeName.getText());
				Dbini.setParameterName(tfParameterName.getText());
				Dbini.setValue(tfValue.getText());
				Dbini.setDescription(taDescription.getText());
				
				this.setReturnValue(rsMain.addOrUpdate(Dbini));
				tblMain.refresh();
				dialogButtons.closeForm();
			});

		}
		catch (Exception ex) {
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
}
