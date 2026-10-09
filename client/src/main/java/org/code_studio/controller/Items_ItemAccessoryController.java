package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Items_ItemAccessoryController extends BaseController implements Initializable {

	//@FXML private CSTable <ClientCatalog> mainTable;
	@FXML private Button btnClose;

	public Items_ItemAccessoryController() {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {

		btnClose.setOnAction(e->{
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
	} //initialize END
	
}
