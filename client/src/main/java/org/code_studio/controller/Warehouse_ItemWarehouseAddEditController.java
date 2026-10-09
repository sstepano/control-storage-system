package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.database.ItemWarehouse;
import org.code_studio.rest.CSRestService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Warehouse_ItemWarehouseAddEditController extends BaseController implements Initializable {

	@FXML Button btnSave;
	@FXML Button btnCancel;
	
	private ItemWarehouse selectedItem;
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
	
	private final String putUrl = "/itemWarehouse";
	private CSRestService<ItemWarehouse> rsvcPut;
	
	public Warehouse_ItemWarehouseAddEditController(Object selectedItem) {
		this.selectedItem = (ItemWarehouse) selectedItem;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rsvcPut = new CSRestService<ItemWarehouse>(putUrl);
		
		if (selectedItem != null) { //update or delete action
			tfWarehouseId.setText(selectedItem.getWarehouseId().toString());
			//tfWarehouseName.setText(selectedItem.getWarehouse().getName());
			tfItemId.setText(selectedItem.getItemId().toString());
			//tfItemName.setText(selectedItem.getItem().getName());
			tfItemClientId.setText(selectedItem.getItem().getClientId().toString());
			
			tfFromRowId.setText(selectedItem.getRowId());
			tfFromShelfId.setText(selectedItem.getShelfId());
			tfFromVerticalId.setText(selectedItem.getVerticalId());
			
			//Here we add existing values, just to prevent user to save all nulls by mistake
			tfToRowId.setText(selectedItem.getRowId());
			tfToShelfId.setText(selectedItem.getShelfId());
			tfToVerticalId.setText(selectedItem.getVerticalId());
			
			spTotal.getValueFactory().setValue(selectedItem.getQty());
			((SpinnerValueFactory.IntegerSpinnerValueFactory) spTotal.getValueFactory()).setMax(selectedItem.getQty());
		} else { // insert action

		}

		btnSave.setOnAction( e-> {
			selectedItem.setRowId(tfToRowId.getText());
			selectedItem.setShelfId(tfToShelfId.getText());
			selectedItem.setVerticalId(tfToVerticalId.getText());
			//TODO: ovde fali jos i da skinem sa stanja na prethodnoj poziciji
			//takodje, da ako je preostalo stanje na staroj poziciji, da je obrisem ako je u configu tako definisano
			
			//String rsvcRes = "OK";
			rsvcPut.addOrUpdate(selectedItem);
			
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
			
			// on success create a popup to the user so he knows that everything was OK with the update
			/*
			Notifications nt = Notifications.create()
	            .title(((Stage) ((Button) e.getSource()).getScene().getWindow()).getTitle());
			
			if (rsvcRes == "OK") {
				nt.text(resources.getString("label.successedit.text"));
				nt.showInformation();
			} else {
				nt.text(resources.getString("label.erroredit.text") + ": " + rsvcRes);
				nt.showError();
			}
			*/
		});

		btnCancel.setOnAction(e->{
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
	}
	
}
