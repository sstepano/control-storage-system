package org.code_studio.controller;

import javafx.fxml.FXML;

import java.util.List;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.InputValidation.CSZeroValueValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Brand;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

public class Lookup_BrandAddEditController extends BaseController {
	@FXML private CSDialogButtons dialogButtons;
	@FXML private CSTextField tfId;
	@FXML private CSTextField tfName;
	
	private int mode = 0;
	private Brand brand;
	
	private final String urlMain = "/brand";
	private CSRestService<Brand> rsMain;
	
	private List<Object> lstControllerParam;
	private CSTable<Brand> tblMain;
	
	
	@SuppressWarnings("unchecked")
	public Lookup_BrandAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		lstControllerParam = (List<Object>) controllerParam;
		tblMain = (CSTable<Brand>) lstControllerParam.get(0);
	}
	
	public void initialize() {
		try {
			
			if (mode == 0) {
				brand = new Brand();
			} else {
				brand = tblMain.getSelectedItem();
				tfId.setText(brand.getId().toString());
				tfName.setText(brand.getName());
			}
			
			dialogButtons.setValidation(
				 new CSZeroValueValidator(tfName) 
			);
			
			dialogButtons.getSaveButton().setOnAction( _ -> {
				rsMain = new CSRestService<Brand>(urlMain);
				brand.setName(tfName.getText());
				this.setReturnValue(rsMain.addOrUpdate(brand));
				tblMain.refresh();
				dialogButtons.closeForm();
			});

		}
		catch (Exception ex) {
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
}
