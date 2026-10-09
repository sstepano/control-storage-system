package org.code_studio.controller;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.F6SelectedItem;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;

public class Items_SelectedItemsAddEditController extends BaseController {

	@FXML CSTextField tfQty;
	@FXML CSDialogButtons dialogButtons;

	@SuppressWarnings("unused")
	private int mode;
	private CSTable<F6SelectedItem> tblSelectedItems;
	private F6SelectedItem selectedItem;
	
	@SuppressWarnings("unchecked")
	public Items_SelectedItemsAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		tblSelectedItems = (CSTable<F6SelectedItem>) controllerParam;
		selectedItem = tblSelectedItems.getSelectedItem();
	}

	public void initialize() {
		tfQty.setText(selectedItem.getQty().toString());
		
		dialogButtons.getSaveButton().setOnAction( _ -> {
			selectedItem.setQty(tfQty.getTextAsInteger());
			dialogButtons.closeForm();
		});
	}
	
}
