package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.database.ApplicationUser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Lookup_ApplicationUserAddEditController extends BaseController implements Initializable {
	@FXML private Button btnSave;
	@FXML private Button btnCancel;

	// FORM SPECIFIC FIELDS
	@FXML private TextField tfId;
	@FXML private TextField tfUsername;
	@FXML private PasswordField pfPassword;
	@FXML private TextField tfName;
	@FXML private ComboBox<?> cbRolename; //TODO: Promeniti u pravi type umesto wildcard
	
	private ApplicationUser selectedItem;

	public Lookup_ApplicationUserAddEditController() {}
	
	public Lookup_ApplicationUserAddEditController(ApplicationUser selectedItem) {
		this.selectedItem = selectedItem;
	}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		// ako smo dobili poziv sa selektovanom vrednoscu, to znaci da prikazujemo
		// EDIT formu.LookupApplicationUserAddEditController U spurptnom je INSERT.
		if (selectedItem != null) {
			tfId.setText(selectedItem.getId().toString());
			tfUsername.setText(selectedItem.getUsername());
			pfPassword.setText(selectedItem.getPassword());
			tfName.setText(selectedItem.getName());
			//cbRolename.setSelectedItem(?);
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
