package org.code_studio.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.database.BillOfReceipt;
import org.code_studio.database.Client;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//
public class Warehouse_ReceivingItemsAddEditController extends BaseController implements Initializable {
	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	
	@FXML private CSTable <Client> tblClient;
	@FXML private TextField tfId;
	@FXML private TextField tfName;
	@FXML private TextField tfFullName;
	@FXML private TextField tfPOBox;
	@FXML private TextField tfCity;
	@FXML private TextField tfAddress;
	@FXML private TextField tfCountry;
	@FXML private TextArea taDescription;
	
	@FXML private TextField tfOrderId;
	@FXML private TextField tfOrderYear;
	@FXML private TextField tfOrderNumber;
	@FXML private CSDatePicker dtOrderReceiveDate;
	@FXML private CSDatePicker dtOrderDate;
	
	CSRestService<Client> rsvcClient;
	AtomicInteger clientPageId;
	
	CSRestService<BillOfReceipt> rsvcBillOfReceipt;
	CSTable<BillOfReceipt> tblBillOfReceipt;
	private BillOfReceipt billOfReceipt;
	
	private final String urlClient = "/client/supplier";
	private int mode;
	
	@SuppressWarnings("unchecked")
	public Warehouse_ReceivingItemsAddEditController(Object controllerParam, int mode) {
		this.mode = mode;
		tblBillOfReceipt = (CSTable<BillOfReceipt>) controllerParam;
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		clientPageId = new AtomicInteger(0);
		rsvcClient = new CSRestService<>(urlClient);
		rsvcBillOfReceipt = new CSRestService<>("/billOfReceipt");
		//rsvcClient.fetch(clientPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
		rsvcClient.fetch(new ParameterizedTypeReference<JsonResponse<Client>>() {});
		tblClient.setItems(rsvcClient.getDataAsObservableList());
		
		tfOrderYear.setText(Integer.toString(LocalDate.now().getYear()));
		
		/*
		tblClient.onDataNeeded(()->{
			rsvcClient.fetch(clientPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.addItems(rsvcClient.getDataAsObservableList());
		});
		*/
		
		tblClient.onRowSelectionChanged((oldRow, newRow) -> {
			Client newRowData = (Client) newRow;
			if (newRowData != null) {
				tfId.setText(newRowData.getId().toString());
				tfName.setText(newRowData.getName());
				tfFullName.setText(newRowData.getFullName());
				tfPOBox.setText(newRowData.getPoBox().toString());
				tfCity.setText(newRowData.getCity());
				tfAddress.setText(newRowData.getAddress());
				tfCountry.setText(newRowData.getCountry().getName());
			}
		});
		
		if (this.mode == 0) { // add
			tblClient.setAutoSelectFirstRow(true);
			tblClient.setPadding(new Insets(0, 0, 0, 5));
		}
		else if (this.mode == 1) { // edit
			this.billOfReceipt = tblBillOfReceipt.getSelectedItem();
			tblClient.setManaged(false);
			tblClient.setVisible(false);
			//TODO: ERROR KADA SAM PROMENIO BILL OF RECEIPT 
			//tblClient.select(tblBillOfReceipt.getSelectedItem().getClient(), "Name");
			tfOrderId.setText(tblBillOfReceipt.getSelectedItem().getId().toString());
			taDescription.setText(this.billOfReceipt.getDescription());
			tfOrderNumber.setText(this.billOfReceipt.getOrderDescription());
			dtOrderReceiveDate.setValue(this.billOfReceipt.getLastModifiedDate().toLocalDate());
			dtOrderDate.setValue(this.billOfReceipt.getBillOfReceiptDate());
		}
		
		btnCancel.setOnAction(e->{
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
		
		btnSave.setOnAction(e->{
			if (this.billOfReceipt == null) {
				this.billOfReceipt = new BillOfReceipt();
				this.billOfReceipt.setClientId(tblClient.getSelectedItem().getId().intValue());
			}
			
			this.billOfReceipt.setBillOfReceiptDate(dtOrderDate.getValue());
			this.billOfReceipt.setLastModifiedDate(LocalDateTime.of(dtOrderReceiveDate.getValue(), LocalTime.now()));
			this.billOfReceipt.setBillOfReceiptType("PV");
			this.billOfReceipt.setDescription(taDescription.getText());
			this.billOfReceipt.setOrderDescription(tfOrderNumber.getText());
			//TODO: this.billOfReceipt.setOrderId(1);
			//TODO: this.billOfReceipt.setLastModifiedBy();
			
			//TODO: CHECK da li je user uneo sve neophodne podatke
			/*
			if ( 1 != 1 ) {
				Common.ShowNotification("Neuspešno snimanje prijemnice!", "Molimo proverite da li ste uneli sve neophodne podatke i pokušajte ponovo.", true);
			} else {
			*/
				BillOfReceipt insertedBillOFReceipt = rsvcBillOfReceipt.addOrUpdate(this.billOfReceipt);
				tblBillOfReceipt.tableView.getItems().add(insertedBillOFReceipt);
				Common.ShowNotification("Prijemnica", "Uspešno snimanje prijemnice!", false);
				((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
			//}
			
		});
	}
	
}
