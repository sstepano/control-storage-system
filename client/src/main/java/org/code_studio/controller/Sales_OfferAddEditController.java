package org.code_studio.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Client;
import org.code_studio.database.Offer;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class Sales_OfferAddEditController extends BaseController {
	
	@FXML CSDialogButtons dialogButtons;
	@FXML CSTextField tfClientId;
	@FXML CSTextField tfClientName;
	@FXML CSTextField tfClientFullName;
	@FXML CSTextField tfClientPOBox;
	@FXML CSTextField tfClientCityName;
	@FXML CSTextField tfClientCountryName;
	@FXML CSTextField tfClientAddress;
	@FXML TextArea taDescription;
	
	@FXML CSDatePicker dtOfferDate;
	@FXML CSDatePicker dtOfferExpirationDate;
	@FXML CSTextField tfAdvancePaymentAmt;
	//@FXML CSTextField tfDiscountRate;
	@FXML CSTextField tfDeliveryDays;
	@FXML CSTextField tfOfferHeader;
	@FXML private CheckBox ckbReserveItems;

	private CSTable<Offer> tblOffer;
	private Client client;
	private Offer offer;
	
	private int mode;

	private final String urlOffer = "/offer";
	private CSRestService<Offer> rsOffer;
	
	
	@SuppressWarnings("unchecked")
	public Sales_OfferAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		List<Object> lstControllerParam = (List<Object>) controllerParam;
		tblOffer = (CSTable<Offer>) lstControllerParam.get(0);
		client = (Client) lstControllerParam.get(1);
		//taDescriptionParent = (TextArea) lstControllerParam.get(2);
		offer = tblOffer.getSelectedItem();
	}
	
	public void initialize() {
		rsOffer = new CSRestService<Offer>(urlOffer);
		tfClientId.setTextOrEmptyString(client.getId().toString());
		tfClientName.setTextOrEmptyString(client.getName());
		tfClientFullName.setTextOrEmptyString(client.getFullName());
		tfClientPOBox.setTextOrEmptyString(client.getPoBox().toString());
		tfClientCityName.setTextOrEmptyString(client.getCity());
		tfClientCountryName.setTextOrEmptyString(client.getCountry().getName());
		tfClientAddress.setTextOrEmptyString(client.getAddress());
		
		if (mode == 0) {
			tfDeliveryDays.setText("0");
			offer = new Offer();
			dtOfferExpirationDate.setValue(LocalDate.now().plusDays(Common.getDbiniIntegerValue("PONUDA", "BROJ DANA VAŽENJA")));
		}
		else if (mode == 1) {
			if (offer != null) {
				dtOfferDate.setValue(offer.getOfferDate());
				dtOfferExpirationDate.setValue(offer.getExpirationDate());
				tfAdvancePaymentAmt.setTextOrEmptyString(offer.getAdvancePaymentAmt().toString());
				//tfDiscountRate.setTextOrEmptyString("0.00"); // nema u headeru, discount je u detaljima
				tfDeliveryDays.setTextOrEmptyString(offer.getDeliveryDays().toString());
				taDescription.setText(offer.getDescription());
				tfOfferHeader.setText(offer.getOfferHeaderText());
				
				// We do not allow chaning item reservation
				ckbReserveItems.setVisible(false);
			}
		}
		
		dialogButtons.getSaveButton().setOnAction( e-> {
			offer.setClientId(client.getId());
			offer.setOfferType("V");
			offer.setPriceType("L"); //TODO: Videti sta znaci L
			offer.setOfferNumber(1);//TODO: Mislim da je ovo redni broj ponude za klijenta, ali treba proveriti sa B.
			offer.setIsClosed(true);
			offer.setOfferDate(dtOfferDate.getValue());
			offer.setExpirationDate(dtOfferExpirationDate.getValue());
			offer.setAdvancePaymentAmt(tfAdvancePaymentAmt.getTextAsBigDecimal());
			offer.setDeliveryDays(tfDeliveryDays.getTextAsInteger());
			offer.setOfferHeaderText(tfOfferHeader.getText());
			offer.setReserveItems(ckbReserveItems.isSelected());
			offer.setLastModifiedBy(Common.getApplicationUserId());
			offer.setUserModifiedBy(Common.getApplicationUser().getApplicationUserName());
			offer.setLastModifiedDate(LocalDateTime.now());
			offer.setDescription(taDescription.getText());
			
			Offer insertedItem = rsOffer.addOrUpdate(offer);
			//insertedItem.setUserModifiedBy(offer.getUserModifiedBy());
			if (mode == 0) {
				tblOffer.addItem(insertedItem); 				
			}
			
			dialogButtons.closeForm();
			
		});

	}
	
}
