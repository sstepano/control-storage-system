package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.Client;
import org.code_studio.database.ClientAccountBalanceCard;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Sales_CardDetailController extends BaseController implements Initializable {

	@FXML private CSTextField tfId;
	@FXML private CSTextField tfPOBox;
	@FXML private CSTextField tfName;
	@FXML private CSTextField tfCity;
	@FXML private CSTextField tfAddress;
	@FXML private CSTextField tfAmount;
	@FXML private CSTextField tfPayment;
	@FXML private CSTextField tfSaldo;
	@FXML private CSDatePicker dtValueDateFrom;
	@FXML private CSDatePicker dtValueDateTo;
	@FXML private Button btnFilter;
	@FXML private CSTable<ClientAccountBalanceCard> tblClientCard;

	ApplicationContext ctx;
	int mode;
	Client client;
	
	final String urlCard = "/clientAccountBalance/accountBalanceByClientId/";
	CSRestService<ClientAccountBalanceCard> rsCard;
	
	final String urlCardWithValueDate = "/clientAccountBalance/accountBalanceByClientIdAndValueDate/";
	
	final String urlTotalAmount = "/clientAccountBalance/totalBalanceByClientId/";
	CSRestService<ClientAccountBalanceCard> rsTotalAmount;
	
	public Sales_CardDetailController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
		this.mode = mode;
		//this.client = (Client) controllerParam;
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		if (this.mode == 2) { // View
			//TODO: postavi sve kontrole na disabled
		}
		
		/*
		rsCard = new BaseRestService<>(urlCard);
		rsTotalAmount = new BaseRestService<>(urlTotalAmount);
		
		rsCard.setUrl(urlCard + client.getId().toString());
		rsCard.fetch(new ParameterizedTypeReference<JsonResponse<ClientAccountBalanceCard>>() {});
		tblClientCard.setItems(rsCard.getDataAsObservableList());
				
		tfId.setText(client.getId().toString());
		tfPOBox.setText(client.getPoBox().toString());
		tfCity.setText(client.getCity());
		tfName.setText(client.getName());
		tfAddress.setText(client.getAddress());
		
		// TOTAL AMOUNT
		rsTotalAmount.setUrl(urlTotalAmount + client.getId().toString());
		rsTotalAmount.fetch(new ParameterizedTypeReference<JsonResponse<ClientAccountBalanceCard>>() {});
		if (rsTotalAmount.getDataAsObservableList().size() > 0) {
			tfAmount.setText(rsTotalAmount.getDataAsObservableList().get(0).getAmount().toString());
			tfPayment.setText(rsTotalAmount.getDataAsObservableList().get(0).getPayment().toString());
			tfSaldo.setText(rsTotalAmount.getDataAsObservableList().get(0).getSaldo().toString());
		}
		
		/*****************************************************
		 * TODO:
		 * Ovaj kod radi. MEdjutim, treba na SQL nivou da resim sledece: 
		 * Posto trenutno kartica radi tako sto uzme prvo stanje pa kalemi sve nadalje, ako filtriram, nece uzeti dobro pocetno stanje
		 * tako da svaki saldo nece biti ispravan.
		 * Moracu da uzimam uvek pocetno stanje pre filterisanog datuma, i onda bi trebalo da je ok 
		 * Dok to ne resim, ovo ce da bude disableovano u FXML.
		 * 
		btnFilter.setOnAction( e-> {
			String valueDateFrom = dtValueDateFrom.getValue() == null ? "1900-01-01" : dtValueDateFrom.getValue().toString();
			String valueDateTo = dtValueDateTo.getValue() == null ? "1900-01-01" : dtValueDateTo.getValue().toString();
			
			rsCard.setUrl(urlCardWithValueDate + client.getId().toString() + "/" + valueDateFrom + "/" + valueDateTo);
			rsCard.fetch(new ParameterizedTypeReference<JsonResponse<ClientAccountBalanceCard>>() {});
			tblClientCard.setItems(rsCard.getDataAsObservableList());
		});
		*/
	}
	
}
