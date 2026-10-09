package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.database.Bank;
import org.code_studio.database.Country;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Lookup_BankAddEditController extends BaseController implements Initializable {
	@FXML private Button btnSave;
	@FXML private Button btnCancel;

	// FORM SPECIFIC FIELDS
	@FXML private TextField tfId;
	@FXML private TextField tfName;
	@FXML private TextField tfBankIdentificatorNumber;
	@FXML private TextField tfAddress;
	@FXML private TextField tfCity;
	@FXML private TextField tfPhone;
	@FXML private TextField tfFax;
	@FXML private ComboBox <Country> cbCountry;
	@FXML private TextField tfDescription;
	
	private Bank selectedItem;

	public Lookup_BankAddEditController() {}
	
	public Lookup_BankAddEditController(Bank selectedItem) {
		this.selectedItem = selectedItem;
	}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		// ako smo dobili poziv sa selektovanom vrednoscu, to znaci da prikazujemo
		// EDIT formu.LookupBankAddEditController U spurptnom je INSERT.
		if (selectedItem != null) {
			tfId.setText(selectedItem.getId().toString());
			tfName.setText(selectedItem.getName());
			tfBankIdentificatorNumber.setText(selectedItem.getBankIdentificatorNumber() != null ? selectedItem.getBankIdentificatorNumber().toString() : "");
			tfAddress.setText(selectedItem.getAddress());
			tfCity.setText(selectedItem.getCity());
			tfPhone.setText(selectedItem.getPhone());
			tfFax.setText(selectedItem.getFax());
			//cbCountry.setText(selectedItem.getAddress());
			tfDescription.setText(selectedItem.getAddress());

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
