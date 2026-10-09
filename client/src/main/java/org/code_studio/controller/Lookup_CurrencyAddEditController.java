package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.database.Currency;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Lookup_CurrencyAddEditController extends BaseController implements Initializable {

	@FXML private TextField id;
	@FXML private TextField name;
	@FXML private TextField iso_currency_code;
	@FXML private TextField symbol;
	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	
	private Currency selectedCurrency;

	public Lookup_CurrencyAddEditController() {}
	
	public Lookup_CurrencyAddEditController(Currency selectedCurrency) {
		this.selectedCurrency = selectedCurrency;
	}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		// ako smo dobili poziv sa selektovanom vrednoscu, to znaci da prikazujemo
		// EDIT formu.LookupCurrencyAddEditController U spurptnom je INSERT.
		if (selectedCurrency != null) {
			id.setText(selectedCurrency.getId().toString());
			name.setText(selectedCurrency.getDescription());
			iso_currency_code.setText(selectedCurrency.getIsoCurrencyCode());
			symbol.setText(selectedCurrency.getSymbol().toString());
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
