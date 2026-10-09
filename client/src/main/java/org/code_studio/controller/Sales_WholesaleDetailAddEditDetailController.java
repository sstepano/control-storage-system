package org.code_studio.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.code_studio.component.CSTextField;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;

public class Sales_WholesaleDetailAddEditDetailController extends BaseController {
	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	@FXML private CSTextField tfQty;

	private ItemWarehouse itemWarehouse;
	
	public Sales_WholesaleDetailAddEditDetailController(Object controllerParam, int mode, ApplicationContext ctx) {
		itemWarehouse = (ItemWarehouse) controllerParam;
	}
	
	public void initialize() {
		try {
			
			btnSave.disableProperty().bind(Bindings.createBooleanBinding(
				() -> {
					if (
						   tfQty.getText().isBlank()
						|| tfQty.getTextAsInteger() == 0
						|| (tfQty.getTextAsInteger() != null && tfQty.getTextAsInteger() > itemWarehouse.getAvailableQty())
						) {
					  return true;
					}
					return false;
				},
				tfQty.textProperty()
			));
			
			btnSave.setOnAction( e-> {
				this.setReturnValue(Integer.valueOf(tfQty.getTextAsInteger()));
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
			btnCancel.setOnAction(e->{
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
		}
		catch (Exception ex) {
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
}
