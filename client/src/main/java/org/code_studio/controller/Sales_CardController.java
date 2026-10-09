package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.Client;
import org.code_studio.database.ClientAccountBalanceCard;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Sales_CardController extends BaseController implements Initializable {

	@FXML private CSTextField tfId;
	@FXML private CSTextField tfPOBox;
	@FXML private CSTextField tfName;
	@FXML private CSTextField tfCity;
	@FXML private CSTextField tfAddress;
	@FXML private CSTextField tfTotalDebitAmt;
	@FXML private CSTextField tfTotalCreditAmt;
	@FXML private CSTextField tfBalanceAmt;
	@FXML private CSTextField tfTodayBalanceAmt;
	@FXML private CSDatePicker dtValueDateFrom;
	@FXML private CSDatePicker dtValueDateTo;
	@FXML private Button btnFilter;
	@FXML private CSTable<ClientAccountBalanceCard> tblClientCard;

	ApplicationContext ctx;
	int mode;
	Client client;
	
	final String urlCardWithValueDate = "/clientAccountBalance/accountBalanceByClientIdAndValueDate/";
	CSRestService<ClientAccountBalanceCard> rsCard;
	
	final String urlDebitCredit = "/clientAccountBalance/debitCreditByClientIdAndValueDate/";
	final String urlTotalAmount = "/clientAccountBalance/totalBalanceByClientIdAndValueDate/";
	CSRestService<ClientAccountBalanceCard> rsTotalAmount;
	
	String valueDateFrom = "1900-01-01";
	String valueDateTo   = "3000-01-01";
	
	public Sales_CardController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
		this.mode = mode;
		this.client = (Client) controllerParam;
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		rsCard = new CSRestService<>(urlCardWithValueDate);
		rsTotalAmount = new CSRestService<>(urlTotalAmount);
		tblClientCard.setAddEditDialog("Sales_CardDetailController");
		tblClientCard.onRowDoubleClick((row) -> {
			tblClientCard.showDoubleClickDefaultAction = true;
		});
		
		valueDateFrom = dtValueDateFrom.getValue() == null ? "1900-01-01" : dtValueDateFrom.getValue().toString();
		valueDateTo = dtValueDateTo.getValue() == null ? "3000-01-01" : dtValueDateTo.getValue().toString();
		
		rsCard.setUrl(urlCardWithValueDate + client.getId().toString() + "/" + valueDateFrom + "/" + valueDateTo);
		rsCard.fetch(new ParameterizedTypeReference<JsonResponse<ClientAccountBalanceCard>>() {});
		tblClientCard.setItems(rsCard.getDataAsObservableList());
				
		tfId.setText(client.getId().toString());
		tfPOBox.setText(client.getPoBox().toString());
		tfCity.setText(client.getCity());
		tfName.setText(client.getName());
		tfAddress.setText(client.getAddress());

		// TOTAL AMOUNT
		populateFilteredTotalAmt();
		
		// TODAY AMOUNT
		rsTotalAmount.setUrl(urlTotalAmount + client.getId().toString() + "/" + valueDateTo);
		rsTotalAmount.fetch(new ParameterizedTypeReference<JsonResponse<ClientAccountBalanceCard>>() {});
		if (rsTotalAmount.getDataAsObservableList().size() > 0) {
			tfTodayBalanceAmt.setText(rsTotalAmount.getDataAsObservableList().get(0).getBalanceAmt().toString());
		}

		btnFilter.setOnAction( e-> {
			valueDateFrom = dtValueDateFrom.getValue() == null ? "1900-01-01" : dtValueDateFrom.getValue().toString();
			valueDateTo   = dtValueDateTo.getValue()   == null ? "3000-01-01" : dtValueDateTo.getValue().toString();
			
			rsCard.setUrl(urlCardWithValueDate + client.getId().toString() + "/" + valueDateFrom + "/" + valueDateTo);
			rsCard.fetch(new ParameterizedTypeReference<JsonResponse<ClientAccountBalanceCard>>() {});
			tblClientCard.setItems(rsCard.getDataAsObservableList());
			
			populateFilteredTotalAmt();
		});
	}
	
	private void populateFilteredTotalAmt() {
		valueDateFrom = dtValueDateFrom.getValue() == null ? "1900-01-01" : dtValueDateFrom.getValue().toString();
		valueDateTo   = dtValueDateTo.getValue()   == null ? "3000-01-01" : dtValueDateTo.getValue().toString();
		
		rsTotalAmount.setUrl(urlDebitCredit + client.getId().toString() + "/" + valueDateFrom + "/" + valueDateTo);
		rsTotalAmount.fetch(new ParameterizedTypeReference<JsonResponse<ClientAccountBalanceCard>>() {});
		if (rsTotalAmount.getDataAsObservableList().size() > 0) {
			tfTotalDebitAmt.setText(rsTotalAmount.getDataAsObservableList().get(0).getDebitAmt().toString());
			tfTotalCreditAmt.setText(rsTotalAmount.getDataAsObservableList().get(0).getCreditAmt().toString());
		}
		
		rsTotalAmount.setUrl(urlTotalAmount + client.getId().toString() + "/" + valueDateTo);
		rsTotalAmount.fetch(new ParameterizedTypeReference<JsonResponse<ClientAccountBalanceCard>>() {});
		if (rsTotalAmount.getDataAsObservableList().size() > 0) {
			tfBalanceAmt.setText(rsTotalAmount.getDataAsObservableList().get(0).getBalanceAmt().toString());
		}
	}
	
}
