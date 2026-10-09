package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.main.Common;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Sales_WholesaleReturnDetailAddEditController extends BaseController implements Initializable {

	@FXML Button btnSave;
	@FXML Button btnCancel;

	@FXML CSTable<?> tblDetail;

	@FXML TextField tfWarehouseId;
	@FXML TextField tfItemId;
	@FXML TextField tfWarehouseName;
	@FXML TextField tfItemName;
	@FXML TextField tfItemClientId;
	@FXML TextField tfFromRowId;
	@FXML TextField tfFromShelfId;
	@FXML TextField tfFromVerticalId;
	@FXML TextField tfToRowId;
	@FXML TextField tfToShelfId;
	@FXML TextField tfToVerticalId;
	@FXML Spinner<Integer> spTotal;
	
	private final String wholesaleReturnDetailAddEditControllerrName = "Sales_WholesaleReturnDetailWholesaleDetailAddEditController";
	private Object selectedItem;
	
	public Sales_WholesaleReturnDetailAddEditController(Object selectedItem) {
		this.selectedItem = (ItemWarehouse) selectedItem;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (selectedItem != null) { //update or delete action

		} else { // insert action

		}

		tblDetail.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(wholesaleReturnDetailAddEditControllerrName, null, 0), tblDetail, "STAVKA POVRATNICE");
		});

		tblDetail.btnEdit.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(wholesaleReturnDetailAddEditControllerrName, tblDetail.getSelectedItem(), 1), tblDetail, "STAVKA POVRATNICE");
		});

		btnSave.setOnAction( e-> {
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

		btnCancel.setOnAction(e->{
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

	}
	
}
