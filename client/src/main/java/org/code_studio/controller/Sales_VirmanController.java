package org.code_studio.controller;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;

public class Sales_VirmanController extends BaseController {
	
	@FXML private CSTable <?> tblVirman;

	private final String addEditControllerName = "Sales_VirmanAddEditController";
	private Object selectedAddEditTableItem = null;

	public Sales_VirmanController(Object controllerParam, int mode, ApplicationContext ctx) {}
	public Sales_VirmanController(Object selectedItem) {}
	
	public void init() {
		tblVirman.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(addEditControllerName, null, 0), tblVirman, null);
		});
	
		tblVirman.btnEdit.setOnAction(e->{
			selectedAddEditTableItem = tblVirman.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditControllerName, selectedAddEditTableItem, 1), tblVirman, "tblVirman.getSelectedItem().getName()");
		});	
	}
	
}
