package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.database.ApplicationRole;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Lookup_ApplicationRoleAddEditController extends BaseController implements Initializable {
	@FXML private Button btnSave;
	@FXML private Button btnCancel;

	// FORM SPECIFIC FIELDS
	@FXML private TextField tfId;
	@FXML private TextField tfName;
	@FXML private TextField tfDescription;
	
	private ApplicationRole selectedItem;

	public Lookup_ApplicationRoleAddEditController() {}
	
	public Lookup_ApplicationRoleAddEditController(ApplicationRole selectedItem) {
		this.selectedItem = selectedItem;
	}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		// ako smo dobili poziv sa selektovanom vrednoscu, to znaci da prikazujemo
		// EDIT formu.LookupApplicationRoleAddEditController U spurptnom je INSERT.
		if (selectedItem != null) {
			tfId.setText(selectedItem.getId().toString());
			tfName.setText(selectedItem.getName());
			tfDescription.setText(selectedItem.getDescription());
		} else {
			//omoguci da user upise ID ... hm, da li ??? Ovde valjda ide AUTOINCREMENT
			//id.setDisable(false);
		}

		btnSave.setOnAction( _ -> {
			System.out.println("SAVE");
		});

		btnCancel.setOnAction(e->{
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
	}

}
