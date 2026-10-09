package org.code_studio.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.database.Client;
import org.code_studio.database.Country;
import org.code_studio.database.Currency;
import org.code_studio.database.Orders;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Procurement_OrderAddEditController extends BaseController implements Initializable {

	@FXML Button btnSave;
	@FXML Button btnCancel;
	
	@FXML TextField tfOrderId;
	@FXML TextField tfYear;
	@FXML TextField tfSupplierId;
	@FXML TextField tfSupplierName;
	@FXML TextField tfSupplierAtt;
	@FXML TextField tfSupplierAttFax;
	@FXML TextField tfSupplierCC;
	@FXML TextField tfSupplierCCFax;
	@FXML TextField tfEmailFrom;
	@FXML TextField tfSupplierOrderId;
	@FXML CheckBox ckbIsImport;
	@FXML CSDatePicker dtOrderDate;
	@FXML CSComboBox<Currency> cbCurrency;
	@FXML TextField tfOrderValue;
	@FXML TextArea taDescription;
	@FXML CSDatePicker dtLoadDate;
	@FXML CSDatePicker dtExpectedDeliveryDate;
	@FXML CSDatePicker dtReceiveDate;
	@FXML CSDatePicker dtOpenDate;
	@FXML CSDatePicker dtValidationDate;
	@FXML TextField tfValidatedByUserName;
	@FXML CSComboBox<Country> cbImportCountry;
	
	private int mode;
	private CSTable<Orders> tblOrders;
	private Orders orders;
	private CSTable<Client> tblSupplier;
	
	CSRestService <Currency> rsCurrency;
	CSRestService<Country> rsClientCountry;
	
	//Service AddEdit
	CSRestService<Orders> rsAddUpdateService;
	
	
	@SuppressWarnings("unchecked")
	public Procurement_OrderAddEditController(Object controllerParam, int mode) {
		this.tblOrders = (CSTable<Orders>) controllerParam;
		this.mode = mode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rsAddUpdateService = new CSRestService<>("/orders");
		this.tblSupplier = (CSTable<Client>) tblOrders.getParentTable();

		rsCurrency = new CSRestService<>("/currency");
		rsCurrency.fetch(new ParameterizedTypeReference<JsonResponse<Currency>>() {});
		cbCurrency.getItems().addAll(rsCurrency.getDataAsObservableList());
		
		ckbIsImport.setOnAction(e->{
			if(ckbIsImport.isSelected()) {
				cbImportCountry.setDisable(false);
			}
			else {
				cbImportCountry.setDisable(true);
			}
		});
		cbImportCountry.setDisable(!ckbIsImport.isSelected());
		
		rsClientCountry = new CSRestService<>("/country");
		rsClientCountry.fetch(new ParameterizedTypeReference<JsonResponse<Country>>() {});
		cbImportCountry.getItems().addAll(rsClientCountry.getDataAsObservableList());
		
		if (mode == 1) { //Edit mode
			this.orders = tblOrders.getSelectedItem();
			tfOrderId.setText(orders.getId().toString());
			tfYear.setText(orders.getOrderDate() == null ? "" : Integer.toString(orders.getOrderDate().getYear()));
			//tfSupplierId.setText(orders.getSupplier() == null ? "" : orders.getSupplier().getId().toString());
			tfSupplierId.setText(tblSupplier.getSelectedItem().getId().toString());
			tfSupplierName.setText(tblSupplier.getSelectedItem().getName());
			tfSupplierAtt.setText(orders.getContactInfo());
			tfSupplierAttFax.setText(orders.getFax());
			tfSupplierCC.setText(orders.getCc());
			tfSupplierCCFax.setText(orders.getCcfax());
			tfEmailFrom.setText(orders.getEmailFrom());
			tfSupplierOrderId.setText(orders.getClientOrderId().toString());
			ckbIsImport.setSelected(orders.getIsImport());
			//tfOrderDate.setText(CSDatePicker.getFormattedDate(orders.getOrderDate()));
			dtOrderDate.setValue(orders.getOrderDate());
			//cbCurrencyCode.SetCurrencyCode...
			tfOrderValue.setText(orders.getOrderAmount() == null ? "" : orders.getOrderAmount().toString()); //videti sa Banettom koja vrednost da se uzme???
			taDescription.setText(orders.getDescription());
			dtLoadDate.setValue(orders.getLoadingDate());
			dtExpectedDeliveryDate.setValue(orders.getDeliveryDate());
			dtReceiveDate.setValue(orders.getReceiveDate());
			dtOpenDate.setValue(CSDatePicker.dateToLocalDate(orders.getOpenDate()));
			dtValidationDate.setValue(CSDatePicker.dateToLocalDate(orders.getValidationDate()));
			tfValidatedByUserName.setText(orders.getValidatedByUser() == null? "" : orders.getValidatedByUser().getName());
		} else if (mode == 0) { //insert
			tfYear.setText(Integer.toString(LocalDate.now().getYear()));
			tblSupplier = (CSTable<Client>) tblOrders.getParentTable();
			tfSupplierId.setText(tblSupplier.getSelectedItem().getId().toString());
			tfSupplierName.setText(tblSupplier.getSelectedItem().getName());
			dtOpenDate.setValue(LocalDate.now());
			//TODO: Otvorio by UserID fali!
		}

		btnSave.setOnAction(e-> {
			if (this.tblSupplier != null) {
				if (mode == 0) { //add
					Orders orders = new Orders(this.tblSupplier.getSelectedItem());
					//orders.setClient(this.tblSupplier.getSelectedItem());
					
					
					//orders.setClientId(this.tblSupplier.getSelectedItem().getId());
					///orders.setSupplier(this.tblSupplier.getSelectedItem()); //TODO: KADA PROSLEDJUJEM SUPPLIER-A, dobijam NULL BODY I NISTA NE UPISE. VIDETI SA BANETOM KO JE SUPPLIER A KO KLIJENT
					//orders.setClientId(tblSupplier.getSelectedItem().getId()); ///ovo mi je duplikat???
					orders.setContactInfo(tfSupplierAtt.getText());
					orders.setFax(tfSupplierAttFax.getText());
					orders.setCc(tfSupplierCC.getText());
					orders.setCcfax(tfSupplierCCFax.getText());
					orders.setEmailFrom(tfEmailFrom.getText());
					orders.setClientOrderId(0); //TODO, napraviti dobavljanje poslednjeg supplier IDa, pa plus 1
					orders.setIsImport(ckbIsImport.isSelected());
					orders.setOrderDate(dtOrderDate.getValue());
					//TODO: orders.setOrderValue()
					orders.setDescription(taDescription.getText());
					orders.setLoadingDate(dtLoadDate.getValue());
					orders.setDeliveryDate(dtExpectedDeliveryDate.getValue());
					orders.setReceiveDate(dtReceiveDate.getValue());
					orders.setOpenDate(CSDatePicker.localDateTimeToDate(LocalDateTime.now()));
					
					/*
					 * TODO: ovde mi fali vreme! ALI ... necemo da ovo koristimo jer imamo posebnu mini formu za postavljanje validation date. 
					 * Open date bi trebalo da punimo iz LocalDate.now()
					 * orders.setOpenDate(CSDatePicker.localDateToDate(dtOpenDate.getValue()));
					 * orders.setValidationDate(CSDatePicker.localDateToDate(dtValidationDate.getValue()));
					 */
					
					this.orders = orders;
					tblOrders.tableView.getItems().add(this.orders); // EXPLICITNI update items-a u gridu ... sr*nje!!!
				} else { //edit
					//this.orders.setSupplier(this.tblSupplier.getSelectedItem());
					this.orders.setClientId(this.tblSupplier.getSelectedItem().getId());
					this.orders.setContactInfo(tfSupplierAtt.getText());
					this.orders.setFax(tfSupplierAttFax.getText());
					this.orders.setCc(tfSupplierCC.getText());
					this.orders.setCcfax(tfSupplierCCFax.getText());
					this.orders.setEmailFrom(tfEmailFrom.getText());
					this.orders.setClientOrderId(0); //TODO, napraviti dobavljanje poslednjeg supplier IDa, pa plus 1
					this.orders.setIsImport(ckbIsImport.isSelected());
					this.orders.setOrderDate(dtOrderDate.getValue());
					//TODO: this.orders.setOrderValue()
					this.orders.setDescription(taDescription.getText());
					this.orders.setLoadingDate(dtLoadDate.getValue());
					this.orders.setDeliveryDate(dtExpectedDeliveryDate.getValue());
					this.orders.setReceiveDate(dtReceiveDate.getValue());
				}
				
				//Ovde se dodaje novi red ako se ne fetchuju podaci sa servera, zato sto je ID = 0 kada se dodaje novi slog.
				// Moracu kod novog dodavanja da fetchujem podatke sa servera nekako!!!
				
				//System.out.println(this.orders);
				rsAddUpdateService.addOrUpdate(this.orders);
			}
		
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});

		btnCancel.setOnAction(e->{
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
	}
	
}
