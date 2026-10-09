package org.code_studio.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.ui.CSComboBoxWarehouse;
import org.code_studio.database.Client;
import org.code_studio.database.ClientStore;
import org.code_studio.database.DeliveryAddress;
import org.code_studio.database.Invoice;
import org.code_studio.database.TransferOrder;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class Sales_WholesaleAddEditController extends BaseController {

	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	
	@FXML private TextField tfClientId;
	@FXML private TextField tfClientName;
	@FXML private TextField tfClientFullName;
	@FXML private TextField tfClientPOBox;
	@FXML private TextField tfClientCity;
	@FXML private TextField tfClientCountry;
	@FXML private TextField tfClientAddress;
	
	@FXML private CSComboBox<DeliveryAddress> cbDeliveryAddressMail;
	@FXML private CSComboBox<DeliveryAddress> cbDeliveryAddressGoods;
	@FXML private CSComboBox<String> cbReservationType;
	@FXML private CSComboBoxWarehouse cbWarehouse;
	
	@FXML private CSTextField tfProinvoiceNumber;
	@FXML private CSDatePicker dtProinvoiceBookingDate;
	@FXML private CSDatePicker dtProinvoiceValueDate;
	
	@FXML private CSDatePicker dtInvoiceBookingDate;
	@FXML private CSDatePicker dtInvoiceValueDate;
	@FXML private CSTextField tfInvoiceNumber;
	
	@FXML private CSDatePicker dtDispatchNoteBookingDate;
	@FXML private CSTextField tfDispatchNoteTypeId;
	@FXML private CSTextField tfDispatchNoteId;
	
	@FXML private CSTextField tfDeliveryDays;
	@FXML private CSDatePicker dtDeliveryDate;
	
	@FXML private Button btnClientStoreChoose;
	@FXML private CSTextField tfClientStoreName;
	@FXML private CSTextField tfClientStoreAddress;
	@FXML private ToggleGroup tgpPrice;
	@FXML private RadioButton rbRegularPrice;
	@FXML private RadioButton rbSpecialPrice;
	@FXML private RadioButton rbFreePrice;
	@FXML private RadioButton rbDOMPrice;
	@FXML private CSTextField tfAdvancePaymentAmt;
	
	@FXML private CheckBox ckbForeignClient;
	@FXML private CSTextField tfDiscountRate;
	@FXML private TextArea taDescription;
	
	@FXML private RadioButton rbWholesale;
	@FXML private RadioButton rbCommissionTransferOrder; // komisiona otpremnica
	@FXML private RadioButton rbCommissionSignout; // Odjava komisiona
	
	private int mode;
	private Client client;
	private Invoice invoice;
	private CSTable<Invoice> tblInvoice;
	private TextArea taDescriptionOnParent;
	private String displayMode = "V"; // Veleprodaja inicijalno
	
	private String urlDeliveryAddress = "/deliveryAddress/allByClientId/";
	private CSRestService<DeliveryAddress> rsDeliveryAddress;
	
	private String urlInvoiceAddUpdate = "/invoice";
	private CSRestService<Invoice> rsInvoiceAddUpdate;
	private final String urlMaxClientInvoiceNumber = "/invoice/maxInvoiceNumberByClientId/";
	private CSRestService<Integer> rsMaxClientInvoiceNumber;
	
	private String urlTransferOrder = "/transferOrder/allByInvoiceId/";
	private CSRestService<TransferOrder> rsTransferOrder;

	private String urlTransferOrderAddEdit = "/transferOrder";
	private CSRestService<TransferOrder> rsTransferOrderAddEdit;


	@SuppressWarnings("unchecked")
	public Sales_WholesaleAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		List<Object> lstControllerParams = (List<Object>) controllerParam;
		tblInvoice = (CSTable<Invoice>) lstControllerParams.get(0);
		if (mode == 0) {
			invoice = new Invoice();
		} else {
			invoice = tblInvoice.getSelectedItem();
		}
		client = (Client) lstControllerParams.get(1);
		taDescriptionOnParent = (TextArea) lstControllerParams.get(2);
		displayMode = (String) lstControllerParams.get(3);
	}
	
	public void initialize() {
		
		switch (displayMode) {
			case "V":
				rbWholesale.setSelected(true);
				cbReservationType.getSelectionModel().select(0);
			break;
			case "KO":
				rbCommissionTransferOrder.setSelected(true);
				cbReservationType.getSelectionModel().select(3);
				// setWarehouse, ovo je warehouse od komisiona
			break;
			case "K":
				rbCommissionSignout.setSelected(true);
				cbReservationType.getSelectionModel().select(4);
				// setWarehouse, ovo je warehouse od komisiona
			break;
			default:
			break;
		}
		
		rsDeliveryAddress = new CSRestService<>(urlDeliveryAddress + client.getId());
		rsDeliveryAddress.fetch(new ParameterizedTypeReference<JsonResponse<DeliveryAddress>>(){});
		rsInvoiceAddUpdate = new CSRestService<>(urlInvoiceAddUpdate);
		rsMaxClientInvoiceNumber = new CSRestService<>(urlMaxClientInvoiceNumber + client.getId().toString());
		rsMaxClientInvoiceNumber.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>(){});
		
		rsTransferOrder = new CSRestService<>(urlTransferOrder);
		rsTransferOrderAddEdit = new CSRestService<>(urlTransferOrderAddEdit);
		
		// Stream API, mnogo zanimljiva implementacija, menja for, foreach i slicne loop-ove vrlo efikasno
		if (rsDeliveryAddress.getData() != null) {
			List<DeliveryAddress> lstDeliveryAddressGoods = rsDeliveryAddress.getData().stream().filter(address -> address.getTypeId().equals(1)).collect(Collectors.toList());
			List<DeliveryAddress> lstDeliveryAddressMail = rsDeliveryAddress.getData().stream().filter(address -> address.getTypeId().equals(2)).collect(Collectors.toList());
			cbDeliveryAddressGoods.setItemsAndSelectFirstItem(FXCollections.observableArrayList(lstDeliveryAddressGoods));
			cbDeliveryAddressMail.setItemsAndSelectFirstItem(FXCollections.observableArrayList(lstDeliveryAddressMail));
		}

		tfClientId.setText(client.getId().toString());
		tfClientName.setText(client.getName());
		tfClientFullName.setText(client.getFullName());
		tfClientPOBox.setText(client.getPoBox().toString());
		tfClientCity.setText(client.getCity());
		tfClientCountry.setText(client.getCountry().getName());
		tfClientAddress.setText(client.getAddress());
		
		if (mode == 1) {
			tfClientStoreName.setTextOrEmptyString(invoice.getClientStoreName());
			tfClientStoreAddress.setTextOrEmptyString(invoice.getClientStoreAddress());
			
			switch (invoice.getPriceType()) {
				case "S":
					rbSpecialPrice.setSelected(true);
				break;
				case "L":
					rbFreePrice.setSelected(true);
				break;
				case "D":
					rbDOMPrice.setSelected(true);
				break;
				case "O":
					rbRegularPrice.setSelected(true);
				break;
				default:
					rbRegularPrice.setSelected(true);
				break;
			}
			tfAdvancePaymentAmt.setTextOrEmptyString((invoice.getAdvancePaymentAmt() != null ? invoice.getAdvancePaymentAmt().toString() : "0.00"));
			tfProinvoiceNumber.setTextOrEmptyString(invoice.getProinvoiceNumber() != null 
				? invoice.getProinvoiceNumber().toString()
				: ""
			);
			dtProinvoiceBookingDate.setValue(invoice.getProinvoiceDate());
			dtProinvoiceValueDate.setValue(invoice.getProinvoiceValueDate());
			
			tfInvoiceNumber.setTextOrEmptyString(invoice.getInvoiceNumber() != null 
					? invoice.getInvoiceNumber().toString()
					: ""
				);
			dtInvoiceBookingDate.setValue(invoice.getInvoiceDate());
			dtInvoiceValueDate.setValue(invoice.getInvoiceValueDate());
			
			dtDispatchNoteBookingDate.setValue(invoice.getDispatchNoteDate());
			tfDispatchNoteTypeId.setTextOrEmptyString(
				invoice.getDispatchNoteType() != null 
					? invoice.getDispatchNoteType().toString()
					: ""
			);
			tfDispatchNoteId.setTextOrEmptyString(invoice.getDispatchNoteId() != null
				? invoice.getDispatchNoteId().toString()
				: invoice.getInvoiceNumber().toString()
			);
			
			tfDeliveryDays.setTextOrEmptyString(invoice.getDeliveryDays() != null
				? invoice.getDeliveryDays().toString()
				: ""
			);
			
			// TODO ckbForeignClient
			
			tfDiscountRate.setTextOrEmptyString(invoice.getDiscountRate() != null
				? invoice.getDiscountRate().toString()
				: ""
			);
			
			dtDeliveryDate.setValue(invoice.getDeliveryDate());
			taDescription.setText(invoice.getDescription());
		} else if (mode == 0){
			int delayedValueDays = (client != null && client.getDelayedPaymentDays() != null)
					? client.getDelayedPaymentDays()
					: 0;
			dtProinvoiceValueDate.setValue(LocalDate.now().plusDays(delayedValueDays));
			dtInvoiceValueDate.setValue(LocalDate.now().plusDays(delayedValueDays));
		}
	
		tfDeliveryDays.onFocusChanged((oldValue, newValue)->{
			if (oldValue) { // focus lost
				if (!tfDeliveryDays.getText().isEmpty()) {
					dtDeliveryDate.setValue(LocalDate.now().plusDays(tfDeliveryDays.getTextAsInteger()));
				}
			}
		});
		
		btnClientStoreChoose.setOnAction( e-> {
			ClientStore selectedItem = (ClientStore) Common.displayForm(ControllerFactory.getController("Sales_SaleStoreController", this.client, 2), btnClientStoreChoose, "Prodavnice");
			if (selectedItem != null) {
				tfClientStoreName.setTextOrEmptyString(selectedItem.getName()); 
				tfClientStoreAddress.setTextOrEmptyString(selectedItem.getCity() + ", " + selectedItem.getAddress());
			}
		});
		
		btnSave.setOnAction( e-> {
			invoice.setInvoiceType("V");
			invoice.setReservationType("V");
			invoice.setWarehouseId(5);
			invoice.setVatRate(20);
			invoice.setVatAmt(BigDecimal.ZERO);
			invoice.setNetAmt(BigDecimal.ZERO);
			invoice.setAmountGross(BigDecimal.ZERO);
			invoice.setTotalAmtWithoutDiscount(BigDecimal.ZERO);
			invoice.setLastModifiedDate();
			invoice.setClientStoreName(tfClientStoreName.getTextOrNullIfEmpty());
			invoice.setClientStoreAddress(tfClientStoreAddress.getTextOrNullIfEmpty());
			invoice.setDeliveryAddressMail(
				cbDeliveryAddressMail.getValue() != null 
				? cbDeliveryAddressMail.getValue().getFullAddress()
				: null
			);
			invoice.setDeliveryAddressGoods(
				cbDeliveryAddressGoods.getValue() != null 
				? cbDeliveryAddressGoods.getValue().getFullAddress()
				: null
			);

			switch (((RadioButton) tgpPrice.getSelectedToggle()).getText()) {
				case "Obična":
					invoice.setPriceType("O");
				break;
				case "Specijalna":
					invoice.setPriceType("S");
				break;
				case "Slobodna":
					invoice.setPriceType("L");
				break;
				case "DOM":
					invoice.setPriceType("D");
				break;
				default:
					invoice.setPriceType("O");
				break;
			}
			invoice.setProinvoiceNumber(tfProinvoiceNumber.getTextAsInteger());
			invoice.setProinvoiceDate(dtProinvoiceBookingDate.getValue());
			invoice.setProinvoiceValueDate(dtProinvoiceValueDate.getValue());

			invoice.setInvoiceDate(dtInvoiceBookingDate.getValue());
			invoice.setInvoiceValueDate(dtInvoiceValueDate.getValue());
			
			invoice.setDispatchNoteType(tfDispatchNoteTypeId.getTextAsInteger());
			invoice.setDispatchNoteDate(dtDispatchNoteBookingDate.getValue());
			
			invoice.setAdvancePaymentAmt(tfAdvancePaymentAmt.getTextAsBigDecimal());
			invoice.setDeliveryDays(tfDeliveryDays.getTextAsInteger());
			invoice.setDiscountRate(tfDiscountRate.getTextAsBigDecimal());
			invoice.setDeliveryDate(dtDeliveryDate.getValue());
			invoice.setDescription(taDescription.getText());
			
			// TODO: videti zasto failuje na klijentu iako je insertable false. Kada ovo stavim, sve prodje
			// Bez njega insertuje u bazu ali pukne postForEntity u RestTemplate.
			invoice.setInvoiceDetail(new ArrayList<>()); 
			
			if (mode == 0) {
				invoice.setClientId(client.getId().intValue());
				invoice.setCreatedBy(1);
				invoice.setInvoiceNumber(
						!rsMaxClientInvoiceNumber.getData().isEmpty() 
						? rsMaxClientInvoiceNumber.getData().get(0) 
						: 1
				);
				invoice.setDispatchNoteId(invoice.getInvoiceNumber());
			} else {
				invoice.setDispatchNoteId(tfDispatchNoteId.getTextAsInteger());
			}
			
			Invoice updatedInvoiceItem = rsInvoiceAddUpdate.addOrUpdate(invoice);
			addEditTransferOrder(updatedInvoiceItem);
			
			if (mode == 0 && updatedInvoiceItem != null) {
				tblInvoice.tableView.getItems().add(updatedInvoiceItem);
			} else {
				taDescriptionOnParent.setText(taDescription.getText());
			}
			
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

		btnCancel.setOnAction(e->{
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

	}
	
	private TransferOrder addEditTransferOrder(Invoice invoice) {
		TransferOrder transferOrder;
		rsTransferOrder.setUrl(urlTransferOrder + invoice.getId());
		rsTransferOrder.fetch(new ParameterizedTypeReference<JsonResponse<TransferOrder>>(){});
		transferOrder = (rsTransferOrder.getData() != null && !rsTransferOrder.getData().isEmpty()) 
			? rsTransferOrder.getData().get(0)
			: null;
		
		if (transferOrder == null) {
			transferOrder = new TransferOrder();
			transferOrder.setWarehouseIdOrigin(0); // ne koristi se
			transferOrder.setWarehouseIdDestination(5);
			transferOrder.setInvoiceId(invoice.getId().intValue());
			transferOrder.setCashierInvoiceId(0);
			transferOrder.setBillOfReceiptId(0);
			transferOrder.setTypeCode("NM");
			transferOrder.setTransferOrderNumber(invoice.getInvoiceNumber());
			transferOrder.setTransferOrderDate(LocalDateTime.now());
			/* izbaceno iz transfer ordera
			transferOrder.setCloseDate(null);
			transferOrder.setClosedBy(null);
			*/
			transferOrder.setDueDate(null); // popunjava se iz master forme, plava polja
			transferOrder.setDeliveredDate(null);
			transferOrder.setDeliveredByUserId(null); // TODO: Dodati pravog usera
			transferOrder.setStatusId(0);
			transferOrder.setDescription(null);
		}
		return rsTransferOrderAddEdit.addOrUpdate(transferOrder);
	}
	
}
