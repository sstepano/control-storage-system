package org.code_studio.controller;

import java.util.List;
import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Client;
import org.code_studio.database.Country;
import org.code_studio.database.DeliveryAddress;
import org.code_studio.database.DeliveryAddressType;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Lookup_DeliveryAddressAddEditController extends BaseController {

	@FXML private CSDialogButtons dialogButtons;
	
	@FXML private CSComboBox <Country> cbCountry;
	@FXML private CSComboBox <DeliveryAddressType> cbDeliveryAddressType;
	@FXML private TextField tfAddress;
	@FXML private TextField tfCity;
	
	private List<Object> lstControllerParam;
	
	private CSTable<DeliveryAddress> tblDeliveryAddress;
	private int mode;
	private DeliveryAddress deliveryAddress;
	private Client client;
	
	CSRestService <Country> rsCountry;
	CSRestService <DeliveryAddressType> rsDeliveryAddressType;
	CSRestService <DeliveryAddress> rsDeliveryAddress;

	public Lookup_DeliveryAddressAddEditController() {}
	

	@SuppressWarnings("unchecked")
	public Lookup_DeliveryAddressAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		lstControllerParam = (List<Object>) controllerParam;
		client = (Client) lstControllerParam.get(0);
		tblDeliveryAddress = (CSTable<DeliveryAddress>) lstControllerParam.get(1);
	}

	public void initialize() {
		rsDeliveryAddress = new CSRestService<>("/deliveryAddress");
		deliveryAddress = tblDeliveryAddress.getSelectedItem();
		
		rsCountry = new CSRestService<>("/country");
		rsCountry.fetch(new ParameterizedTypeReference<JsonResponse<Country>>() {});
		cbCountry.setItemsAndSelectFirstItem(rsCountry.getDataAsObservableList());
		
		rsDeliveryAddressType = new CSRestService<>("/deliveryAddressType");
		rsDeliveryAddressType.fetch(new ParameterizedTypeReference<JsonResponse<DeliveryAddressType>>() {});
		cbDeliveryAddressType.setItemsAndSelectFirstItem(rsDeliveryAddressType.getDataAsObservableList());
		
		if (mode == 1) {
			tfCity.setText(deliveryAddress.getCity());
			tfAddress.setText(deliveryAddress.getAddress());
			cbCountry.select(deliveryAddress.getCountry());
			cbDeliveryAddressType.select(deliveryAddress.getDeliveryAddressType());
		}
		
		dialogButtons.getSaveButton().setOnAction(e-> {
			if (this.client != null) {
				if (mode == 0) {
					deliveryAddress = new DeliveryAddress();
					deliveryAddress.setCountry(cbCountry.getSelectionModel().getSelectedItem());
					deliveryAddress.setCity(tfCity.getText());
					deliveryAddress.setAddress(tfAddress.getText());
					deliveryAddress.setDeliveryAddressType(cbDeliveryAddressType.getSelectionModel().getSelectedItem());
					deliveryAddress.setClientId(client.getId().intValue());
				} else {
					deliveryAddress.setCountry(cbCountry.getSelectionModel().getSelectedItem());
					deliveryAddress.setCity(tfCity.getText());
					deliveryAddress.setAddress(tfAddress.getText());
					deliveryAddress.setDeliveryAddressType(cbDeliveryAddressType.getSelectionModel().getSelectedItem());
				}

				DeliveryAddress insertedItem = rsDeliveryAddress.addOrUpdate(deliveryAddress);

				if (mode == 0) {
					tblDeliveryAddress.addItem(insertedItem);
				}
			}
		
			dialogButtons.closeForm();
		});
		
		dialogButtons.setValidation(
			  new CSEmptyFieldValidator(tfCity)
			, new CSEmptyFieldValidator(tfAddress)
		);
		
	}

}
