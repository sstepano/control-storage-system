package org.code_studio.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSComboBoxWarehouse;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.ApplicationUser;
import org.code_studio.database.BillOfExchange;
import org.code_studio.database.Client;
import org.code_studio.database.ClientCategory;
import org.code_studio.database.ClientGroup;
import org.code_studio.database.ClientName;
import org.code_studio.database.ClientSaleOfficerLink;
import org.code_studio.database.Country;
import org.code_studio.database.Division;
import org.code_studio.database.Warehouse;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Sales_ClientAddEditController extends BaseController implements Initializable {
	@FXML private CSDialogButtons dialogButtons;

	// FORM SPECIFIC FIELDS
	@FXML private CSTextField tfClientId;
	@FXML private CSTextField tfClientName;
	@FXML private CSTextField tfFullName;
	@FXML private CSComboBox<ClientGroup> cbClientGroup;
	@FXML private CSComboBox<ClientCategory> cbClientCategory;
	@FXML private CSTextField tfAdditionalName;
	@FXML private CSTextField tfIdentificationId;
	@FXML private CSComboBox <Country> cbCountry;
	@FXML private CSTextField tfCity;
	@FXML private CSTextField tfMunicipality;
	@FXML private CSTextField tfTaxId;
	@FXML private CSTextField tfAddress;
	@FXML private CSTextField tfClientPhone;
	@FXML private CSTextField tfClientFax;
	@FXML private CSTextField tfClientEmail;
	@FXML private CSTextField tfDirectorName;
	@FXML private CSTextField tfDirectorPhone;
	@FXML private CSTextField tfDirectorCellphone;
	@FXML private CSTextField tfActivityCode;
	@FXML private CSTextField tfActivity;
	@FXML private CSTextField tfWebsite;
	@FXML private CSTextField tfLimitDIN;
	@FXML private CSTextField tfLimitEUR;
	@FXML private CSTextField tfSaldo;
	@FXML private CSTextField tfContactFrom;
	@FXML private CSTextField tfContactEnded;
	@FXML private CSTextField tfContract;
	@FXML private CSDatePicker dtContractDateFrom;
	@FXML private CSDatePicker dtContractDateTo;
	@FXML private CSTextField tfBillOfExchangeId;
	@FXML private CSTextField tfBillOfExchangeValue;
	@FXML private CSTextField tfPanelsGiven;
	@FXML private CSComboBox<ApplicationUser> cbSalesOfficer; // roles 10, 11, 12, 13
	@FXML private CSComboBoxWarehouse cbWarehouse;
	@FXML private CheckBox ckbWholesale;
	@FXML private CheckBox ckbRetailSale;
	@FXML private CheckBox ckbDiscountSale;
	@FXML private CheckBox ckbCommissionSale;
	@FXML private CheckBox ckbIsVAT;
	@FXML private CheckBox ckbIsSupplier;
	@FXML private CheckBox ckbIsSupplierPriceOnly;
	@FXML private CheckBox ckbIsForeignClient;
	@FXML private CSTextField tfRateEURDOM;
	@FXML private CSTextField tfRateEURWorking;
	@FXML private TextArea tfDescription;
	@FXML private CSTextField tfPOBox;
	@FXML private CSComboBox<Division> cbDivision;
	@FXML private CSTextField tfParentId;
	@FXML private Button btnParentChoose;
	@FXML private CSTextField tfDelayedPaymentDays;
	@FXML private HBox hbWarehouse;
	@FXML private Button btnWarehouseSelect;
	@FXML private Button btnDiscount;
	@FXML private ComboBox<String> cbInvoiceGenType;
	
	private int mode;
	private CSTable<Client> tblClient;
	private Client client;
	
	private TextArea taClientDescription;
	
	CSRestService<ClientGroup> rsClientGroup;
	CSRestService<ClientCategory> rsClientCategory;
	CSRestService<Country> rsClientCountry;
	CSRestService<ApplicationUser> rsSalesOfficer;
	CSRestService<Division> rsDivision;
	
	//Service AddEdit
	CSRestService<Client> rsClient;
	CSRestService<ClientSaleOfficerLink> rsClientSaleOfficerLink;

	public Sales_ClientAddEditController() {}
	
	@SuppressWarnings("unchecked")
	public Sales_ClientAddEditController(Object controllerParam, int mode) {
		List<Object> lstControllerParam = (List<Object>) controllerParam;
		this.tblClient = (CSTable<Client>) lstControllerParam.get(0);
		if (lstControllerParam.size() > 1) {
			this.taClientDescription = (TextArea) lstControllerParam.get(1);
		}
		this.mode = mode;
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		try {
			rsClientGroup = new CSRestService<>("/clientGroup");
			rsClientGroup.fetch(new ParameterizedTypeReference<JsonResponse<ClientGroup>>() {});
			cbClientGroup.getItems().addAll(rsClientGroup.getDataAsObservableList());
			rsClientCategory = new CSRestService<>("/clientCategory");
			rsClientCategory.fetch(new ParameterizedTypeReference<JsonResponse<ClientCategory>>() {});
			cbClientCategory.getItems().addAll(rsClientCategory.getDataAsObservableList());
			rsClientCountry = new CSRestService<>("/country");
			rsClientCountry.fetch(new ParameterizedTypeReference<JsonResponse<Country>>() {});
			cbCountry.getItems().addAll(rsClientCountry.getDataAsObservableList());
			rsSalesOfficer = new CSRestService<>("/applicationUser");
			rsSalesOfficer.fetch(new ParameterizedTypeReference<JsonResponse<ApplicationUser>>() {});
			cbSalesOfficer.getItems().addAll(rsSalesOfficer.getDataAsObservableList());
			rsDivision = new CSRestService<>("/division");
			rsDivision.fetch(new ParameterizedTypeReference<JsonResponse<Division>>() {});
			cbDivision.getItems().addAll(rsDivision.getDataAsObservableList());
			cbDivision.getSelectionModel().selectFirst(); // selektujemo prvi po defaultu
	
			//Add/Edit
			rsClient = new CSRestService<>("/client");
			rsClientSaleOfficerLink = new CSRestService<>("/clientSaleOfficerLink");
	
			//ovo mora da radi i kada dodajemo i kada editujemo klijenta
			ckbCommissionSale.setOnAction(e->{
				if(ckbCommissionSale.isSelected()) {
				hbWarehouse.setDisable(false);
				}
				else {
					hbWarehouse.setDisable(true);
				}
			});
	
			if (mode == 1) { //edit
				this.client = tblClient.getSelectedItem();
				tfClientId.setText(this.client.getId().toString());
				tfClientName.setText(this.client.getName());
				tfFullName.setText(this.client.getFullName());
				tfAdditionalName.setText(this.client.getAdditionalName());
				tfIdentificationId.setText(this.client.getIdentificationId());
				tfCity.setText(this.client.getCity() == null ? "" : this.client.getCity());
				tfMunicipality.setText(this.client.getMunicipality() == null ? "" : this.client.getMunicipality());
				tfPOBox.setText(this.client.getPoBox() == null ? "" : this.client.getPoBox().toString());
				tfLimitDIN.setText(this.client.getlimitDin().toString());
				tfLimitEUR.setText(this.client.getlimitEur().toString());
				tfSaldo.setText(this.client.getAccountBalance() == null? "0.00" : this.client.getAccountBalance().toString());
				tfPanelsGiven.setText(this.client.getPanelsGiven().toString());
				tfRateEURDOM.setText(this.client.getDomRate().toString());
				tfRateEURWorking.setText(this.client.getWorkRate().toString());
				ckbDiscountSale.setSelected(this.client.getIsDiscountSale());
				
				tfTaxId.setText(this.client.getTaxId());
				tfAddress.setText(this.client.getAddress());
				tfClientPhone.setText(this.client.getCentralPhoneNumber());
				tfClientFax.setText(this.client.getFaxNumber());
				tfClientEmail.setText(this.client.getWebsite()); //TODO: Fali email u bazi
				tfActivityCode.setText(this.client.getActivityCode());
				tfActivity.setText(this.client.getActivity());
				tfWebsite.setText(this.client.getWebsite());
				ckbWholesale.setSelected(this.client.getIsWholesale());
				ckbRetailSale.setSelected(this.client.getIsRetail());
				ckbDiscountSale.setSelected(this.client.getIsDiscountSale());
				tfParentId.setText(client.getParentId().toString());
	
				ckbCommissionSale.setSelected(this.client.getIsCommissionSale());
				
				ckbIsVAT.setSelected(this.client.getIsVat());
				ckbIsSupplier.setSelected(this.client.getIsSupplier());
				ckbIsSupplierPriceOnly.setSelected(this.client.getSupplierPriceOnly());
				//ckbIsForeignClient.setSelected(this.client.get()); TODO: FALI !!!
				
				tfDescription.setText(this.client.getDescription());
				cbClientGroup.select(this.client.getClientGroup());
				cbClientCategory.select(this.client.getClientCategory());
				cbCountry.select(this.client.getCountry());
				cbInvoiceGenType.getSelectionModel().select(client.getInvoiceGenType() - 1);
				tfDelayedPaymentDays.setTextOrEmptyString(
					client.getDelayedPaymentDays() != null
					    ? client.getDelayedPaymentDays().toString()
					    : null
				);
				
				if (this.client.getPrimarySaleOfficer() != null) {
					cbSalesOfficer.select(this.client.getPrimarySaleOfficer().get(0).getApplicationUser());
				} else {
					cbSalesOfficer.getSelectionModel().selectFirst(); // biramo prvog, nije savrseno
				}
				cbWarehouse.getSelectionModel().select(0); //TEMP, dok ne prepravim property komisioni magacin
			
				if (this.client.getBillOfExchanges() != null && this.client.getBillOfExchanges().size() > 0) {
					tfBillOfExchangeId.setText(this.client.getBillOfExchanges().get(0).getBillOfExchangeNumber());
					BigDecimal billValue = this.client.getBillOfExchanges().get(0).getBillOfExchangeValue();
					if (billValue != null) {
						tfBillOfExchangeValue.setText(billValue.toString());
					}
				}
				
				if (this.client.getClientContracts() != null && this.client.getClientContracts().size() > 0) {
					tfContract.setText(this.client.getClientContracts().get(0).getContractNumber());
					
					if (this.client.getClientContracts().get(0).getValidFrom() != null) {
						dtContractDateFrom.setValue(this.client.getClientContracts().get(0).getValidFrom());
					}
					if (this.client.getClientContracts().get(0).getValidTo() != null) {
						dtContractDateTo.setValue(this.client.getClientContracts().get(0).getValidTo());
					}
				}
				
				cbDivision.select(this.client.getDivision());
	
			} else { //insert
				cbClientGroup.getSelectionModel().select(0);
				cbClientCategory.getSelectionModel().select(0);
				cbCountry.getSelectionModel().select(0);
				cbSalesOfficer.getSelectionModel().select(0);
				cbWarehouse.getSelectionModel().select(0); //TEMP, dok ne prepravim property komisioni magacin
			}
			
			btnParentChoose.setOnAction( e-> {
				Client client = (Client) Common.displayForm(ControllerFactory.getController("Sales_ClientSelectController", null, 0), btnParentChoose, "Odabir klijenta");
				if (client != null) {
					tfParentId.setText(client.getParentId().toString());
				} else {
					tfParentId.setText(null);
				}
			});
	
			btnWarehouseSelect.setOnAction( e-> {
				Warehouse warehouse = (Warehouse) Common.displayForm(ControllerFactory.getController("Warehouse_WarehouseListController", null, 2), btnWarehouseSelect, "Magacini");
				cbWarehouse.refresh();
				if (warehouse != null) {
					cbWarehouse.select(warehouse);
				}
			});
			
			btnDiscount.setOnAction( e-> {
				// za edit rabata, mode forme je 3
				Common.displayForm(ControllerFactory.getController("Items_ItemPerCategoryController", null, 3), btnDiscount, "Dodavanje/izmena rabata klijentima");
			});
	
			dialogButtons.getSaveButton().setOnAction(e->{
				//ako pravimo novog klijenta
				if (mode == 0) {
					this.client = new Client();
				}
	
				this.client.setClientGroup(cbClientGroup.getSelectionModel().getSelectedItem());
				this.client.setClientCategory(cbClientCategory.getSelectionModel().getSelectedItem());
				this.client.setIdentificationId(tfIdentificationId.getText());
				this.client.setTaxId(tfTaxId.getText());
				this.client.setCentralPhoneNumber(tfClientPhone.getText());
				this.client.setActivityCode(tfActivityCode.getText());
				
				//limit din selectedItem.setLimitDin(tfLimitDIN.getText());
				//TODO: fali u bazi kontakt od selectedItem.setContactFrom(tfContactFrom.getText());
				//TODO: fali u bazi prekinut selectedItem.setContactEnded(tfContactEnded.getText());
				//menica  selectedItem.settfBillOfExchangeId(tfBillOfExchangeId.getText());
				
	
				if (this.client.getBillOfExchanges() != null && this.client.getBillOfExchanges().size() > 0) {
					this.client.getBillOfExchanges().get(0).setBillOfExchangeNumber(tfBillOfExchangeId.getText());
					this.client.getBillOfExchanges().get(0).setBillOfExchangeValue(new BigDecimal(tfBillOfExchangeValue.getTextUnformatted()));
				} else if (this.client.getBillOfExchanges() == null /*&& this.client.getBillOfExchanges().size() == 0*/ && !tfBillOfExchangeId.getText().isBlank() ) {
					this.client.getBillOfExchanges().add(new BillOfExchange (
							  new ClientName(client.getId().intValue(), client.getName()), 
							  tfBillOfExchangeId.getText(), new BigDecimal(tfBillOfExchangeValue.getText()), null, null)
							);
				}
				
				
				//vrednost selectedItem.settfBillOfExchangeValue(tfBillOfExchangeValue.getText()); 
				this.client.setIsWholesale(ckbWholesale.isSelected());
				this.client.setIsRetail(ckbRetailSale.isSelected());
				this.client.setIsDiscountSale(ckbDiscountSale.isSelected());
				this.client.setIsCommissionSale(ckbCommissionSale.isSelected());
				this.client.setIsVat(ckbIsVAT.isSelected());
				this.client.setIsSupplier(ckbIsSupplier.isSelected());
				this.client.setSupplierPriceOnly(ckbIsSupplierPriceOnly.isSelected());
				//inostrani kupac -> gleda se ako je iz strane zemlje, nema poseban property
				this.client.setName(tfClientName.getText());
				this.client.setFullName(tfFullName.getText());
				this.client.setAdditionalName(tfAdditionalName.getText());
				//drzava
				//mesto this.client.setCity(tfCity.getText());
				//postanski broj
				this.client.setAddress(tfAddress.getText());
				this.client.setFaxNumber(tfClientFax.getText());
				//email this.client.setEmail(tfClientEmail.getText());
				this.client.setActivityCode(tfActivityCode.getText());
				this.client.setWebsite(tfWebsite.getText());
				//limit din this.client.setLimitEur(tfLimitEUR.getText());
				//contract
				//contract date from this.client.setContractDateFrom(tfContractDateFrom.getText());
				//contract date to this.client.setContractDateTo(tfContractDateTo.getText());
				if (!tfPanelsGiven.getText().isBlank()) {
					this.client.setPanelsGiven(Integer.parseInt(tfPanelsGiven.getText()));
				}
				//magacin this.client.setWarehouse(cbWarehouse.getSelectionModel().getthis.client());
				//kurs eur this.client.setRateEURDOM(tfRateEURDOM.getText());
				//kurs eur radni this.client.setRateEURWorking(tfRateEURWorking.getText());
				this.client.setDescription(tfDescription.getText());
				this.client.setDivision(cbDivision.getSelectionModel().getSelectedItem());
				this.client.setDelayedPaymentDays(tfDelayedPaymentDays.getTextAsInteger());
				this.client.setInvoiceGenType(cbInvoiceGenType.getSelectionModel().getSelectedIndex() + 1);
				
				//CHECK da li je user uneo sve neophodne podatke
				if ( cbClientGroup.getSelectionModel().getSelectedItem() == null 
						|| cbClientCategory.getSelectionModel().getSelectedItem() == null
						|| tfCity.getText().isBlank()
						|| cbCountry.getSelectionModel().getSelectedItem() == null
						|| (ckbCommissionSale.isSelected() && cbWarehouse.getSelectionModel().getSelectedItem() == null)
				) {
					Common.ShowNotification("Neuspešno snimanje klijenta!", "Molimo proverite da li ste uneli sve neophodne podatke i pokušajte ponovo.", true);
				} else {
					/////selectedItem.getPrimarySaleOfficer().setApplicationUser(cbSalesOfficer.getSelectionModel().getSelectedItem());
					Client insertedClient = rsClient.addOrUpdate(this.client);
					if (taClientDescription != null) { // sometimes we do not have this control in caller (like Dobanovci_GoodsIn)
						taClientDescription.setText(insertedClient.getDescription());
					}
					
					if (mode == 0) {
						tblClient.tableView.getItems().add(insertedClient);
					}
					
					((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
				}
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške pri prikazivanju forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
		
		/** FORM INPUT VALIDATION
		 *  Validation disables SAVE button if any of the defined conditions are TRUE
		 */
		dialogButtons.setValidation(
			/*
			new CSInputValidator(tfAddress,
				() -> tfAddress.getText().equalsIgnoreCase("AAA"),	
				"Adresa ne sme biti AAA"
			),
			new CSInputValidator(tfAdditionalName,
				() -> tfAdditionalName.getText().length() == 0,	
				"Dodatni naziv ne sme biti prazan"
			),
			  new CSEmptyFieldValidator(tfContract)
			, new CSZeroValueValidator(tfBillOfExchangeValue)
			*/
			  new CSEmptyFieldValidator(tfIdentificationId)
			, new CSEmptyFieldValidator(tfTaxId)
			, new CSEmptyFieldValidator(tfClientName)
			, new CSEmptyFieldValidator(tfFullName)
			, new CSEmptyFieldValidator(tfAdditionalName)
			, new CSEmptyFieldValidator(tfAddress)
			, new CSEmptyFieldValidator(tfCity)
			, new CSEmptyFieldValidator(tfMunicipality)
			, new CSEmptyFieldValidator(tfPOBox)
		);
		
		
	} // initialize end
	
}
