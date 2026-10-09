package org.code_studio.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.InputValidation.CSZeroValueValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.BillOfExchange;
import org.code_studio.database.Client;
import org.code_studio.database.ClientName;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class Lookup_BillOfExchangeAddEditController extends BaseController implements Initializable {

	@FXML private CSDialogButtons dialogButtons;
	
	@FXML private TextField tfNumber;
	@FXML private CSTextField tfValue;
	@FXML private CSDatePicker dtDate;
	@FXML private TextArea taDescription;

	private int mode;
	private List<Object> lstControllerParam;
	private Client client;
	private CSTable<BillOfExchange> tblBillOfExchange;
	private BillOfExchange billOfExchange;
	
	private final String urlBillOfExchange = "/billOfExchange";
	CSRestService<BillOfExchange> rsBillOfExchange;

	public Lookup_BillOfExchangeAddEditController() {}
	
	@SuppressWarnings("unchecked")
	public Lookup_BillOfExchangeAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		lstControllerParam = (List<Object>) controllerParam;
		client = (Client) lstControllerParam.get(0);
		tblBillOfExchange = (CSTable<BillOfExchange>) lstControllerParam.get(1);
		billOfExchange = tblBillOfExchange.getSelectedItem();
	}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		rsBillOfExchange = new CSRestService<>(urlBillOfExchange);

		if (mode == 0) {
			billOfExchange = new BillOfExchange();
			billOfExchange.setClientId(client.getId().intValue());
			billOfExchange.setClient(new ClientName(client.getId().intValue(), client.getName())); //budz za naziv klijenta u koloni	
		}
		else {
			tfNumber.setText(billOfExchange.getBillOfExchangeNumber());
			
			if (billOfExchange.getBillOfExchangeValue() != null) {
				tfValue.setText(billOfExchange.getBillOfExchangeValue().toString());
			}
			
			if (billOfExchange.getValueDate() != null) {
				dtDate.setValue(CSDatePicker.dateToLocalDate(billOfExchange.getValueDate()));
			}
			taDescription.setText(billOfExchange.getDescription());
		}
		
		dialogButtons.getSaveButton().setOnAction( _ -> {
			System.out.println(client.getId());
			System.out.println(client.getName());
			billOfExchange.setBillOfExchangeNumber(tfNumber.getText());
			billOfExchange.setBillOfExchangeValue(tfValue.getTextAsBigDecimal());
			if (dtDate.getValue() != null) {
				billOfExchange.setValueDate(CSDatePicker.localDateToDate(dtDate.getValue()));
			}
			billOfExchange.setDescription(taDescription.getText());
			BillOfExchange insertedItem = rsBillOfExchange.addOrUpdate(billOfExchange);
			
			if (mode == 0) {
				tblBillOfExchange.addItem(insertedItem);
			}
			
			dialogButtons.closeForm();
		});
		
		dialogButtons.setValidation(
			  new CSEmptyFieldValidator(tfNumber)
			, new CSZeroValueValidator(tfValue)
		);

	}

}
