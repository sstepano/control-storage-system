package org.code_studio.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.database.TransferOrder;
import org.code_studio.database.Warehouse;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Warehouse_TransferOrderAddEditController extends BaseController implements Initializable {

	int mode;
	ApplicationContext ctx;
	Object controllerParam;
	
	@FXML Button btnSave;
	@FXML Button btnCancel;

	@FXML TextField tfOrderId;
	@FXML TextField tfTypeCode;
	@FXML TextField tfYear;
	@FXML TextField tfNumber;
	@FXML CSDatePicker dtOpenedDate;
	@FXML CSComboBox<Warehouse> cbWarehouseOrigin;
	@FXML CSComboBox<Warehouse> cbWarehouseDestination;
	@FXML CSDatePicker dtDueDate;
	@FXML TextArea taDescription;
	
	CSTable <TransferOrder> tblTransferOrder;
	TransferOrder transferOrder;
	
	CSRestService<Warehouse> rsWarehouse;
	final String urlWarehouse = "/warehouse";
	
	private final String urlTransferOrder = "/transferOrder";
	CSRestService<TransferOrder> rsTransferOrder;
	
	private final String urlTransferOrderMaxNumber = "/transferOrder/findMaxCurrentNumber";
	CSRestService<Integer> rsTransferOrderMaxNumber;
	
	private TextArea taDescriptionFromParent;
	private List<Object> lstControllerParams;
	
	@SuppressWarnings("unchecked")
	public Warehouse_TransferOrderAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.ctx = ctx;
		this.lstControllerParams = (List<Object>) controllerParam; 
		this.tblTransferOrder = (CSTable<TransferOrder>) this.lstControllerParams.get(0);
		transferOrder = tblTransferOrder.getSelectedItem();
		taDescriptionFromParent = (TextArea) this.lstControllerParams.get(1);
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		rsWarehouse = new CSRestService<>(urlWarehouse);
		rsWarehouse.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>() {});
		cbWarehouseOrigin.setItems(rsWarehouse.getDataAsObservableList());
		cbWarehouseDestination.setItems(rsWarehouse.getDataAsObservableList());
		cbWarehouseOrigin.getSelectionModel().select(0);
		cbWarehouseDestination.getSelectionModel().select(1);
		
		rsTransferOrder = new CSRestService<>(urlTransferOrder);
		rsTransferOrderMaxNumber = new CSRestService<>(urlTransferOrderMaxNumber);
		
		if (mode == 0) {
			tfTypeCode.setText(transferOrder.getTypeCode()); // Interna prenosnica (IP) ili Povracaj komisiona (PK)
			tfYear.setText(Integer.toString(dtOpenedDate.getValue().getYear()));
		} else {
			tfOrderId.setText(transferOrder.getId().toString());
			tfTypeCode.setText(transferOrder.getTypeCode());
			tfYear.setText(transferOrder.getYear().toString());
			tfNumber.setText(transferOrder.getTransferOrderNumber().toString());
			dtOpenedDate.setValue(transferOrder.getTransferOrderDate()== null ? null : transferOrder.getTransferOrderDate().toLocalDate());
			dtDueDate.setValue(transferOrder.getDueDate() == null ? null : transferOrder.getDueDate().toLocalDate());
			taDescription.setText(transferOrder.getDescription());
			
			cbWarehouseOrigin.getSelectionModel().select(transferOrder.getWarehouseIdOrigin());
			cbWarehouseDestination.getSelectionModel().select(transferOrder.getWarehouseIdDestination());
		}
		
		btnSave.setOnAction( e-> {
			
			if (mode == 0) {
				transferOrder = new TransferOrder();
				transferOrder.setTypeCode("IP");
				
				rsTransferOrderMaxNumber.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});
				transferOrder.setTransferOrderNumber(rsTransferOrderMaxNumber.getDataAsObservableList().size() > 0 
						? rsTransferOrderMaxNumber.getDataAsObservableList().get(0) + 1
						: 1
				);
				
				transferOrder.setTransferOrderDate(LocalDateTime.now());
				transferOrder.setStatusId(1); //Otvoren
			} else {
				transferOrder.setTransferOrderDate(LocalDateTime.of(dtOpenedDate.getValue(), LocalTime.now()));
			}
			
			transferOrder.setDueDate(LocalDateTime.of(dtDueDate.getValue(), LocalTime.now()));
			transferOrder.setWarehouseIdOrigin(cbWarehouseOrigin.getSelectionModel().getSelectedIndex());
			transferOrder.setWarehouseIdDestination(cbWarehouseDestination.getSelectionModel().getSelectedIndex());
			transferOrder.setDescription(taDescription.getText());
			
			TransferOrder updatedObject = rsTransferOrder.addOrUpdate(transferOrder);
			taDescriptionFromParent.setText(updatedObject.getDescription());
			
			if (mode == 0) {
				this.tblTransferOrder.addItem(updatedObject);
			}
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			
		});
		
		btnCancel.setOnAction(e->{
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

		
	}
}
