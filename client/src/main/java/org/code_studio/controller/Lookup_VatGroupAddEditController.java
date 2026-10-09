package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.database.VatGroup;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Lookup_VatGroupAddEditController extends BaseController implements Initializable {
	@FXML private Button btnSave;
	@FXML private Button btnCancel;

	// FORM SPECIFIC FIELDS
	@FXML private TextField tfId;
	@FXML private TextField tfVatRate;
	@FXML private TextField tfDescription;
	
	private VatGroup selectedItem;

	public Lookup_VatGroupAddEditController() {}
	
	public Lookup_VatGroupAddEditController(VatGroup selectedItem) {
		this.selectedItem = selectedItem;
	}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		// ako smo dobili poziv sa selektovanom vrednoscu, to znaci da prikazujemo
		// EDIT formu.LookupVatGroupAddEditController U spurptnom je INSERT.
		if (selectedItem != null) {
			tfId.setText(selectedItem.getId().toString());
			tfVatRate.setText(selectedItem.getVatRate().toString());
			tfDescription.setText(selectedItem.getDescription());
			
		} else {
			//omoguci da user upise ID ... hm, da li ??? Ovde valjda ide AUTOINCREMENT
			//id.setDisable(false);
		}

		btnSave.setOnAction(e->{
			System.out.println("SAVE");
		});

		btnCancel.setOnAction(e->{
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
	}

}
