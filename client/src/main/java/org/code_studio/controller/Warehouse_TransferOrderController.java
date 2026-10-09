package org.code_studio.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.database.TransferOrder;
import org.code_studio.database.TransferOrderDetail;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Warehouse_TransferOrderController extends BaseController implements Initializable {

	int mode;
	
	@FXML CSTable <TransferOrder> tblTransferOrder;
	@FXML CSTable <TransferOrderDetail> tblTransferOrderDetail;
	@FXML Button btnAddDetail;
	@FXML TextArea taDescription;
	@FXML Button btnValidate;

	String urlTransferOrder = "/transferOrder/allPageableByTypeCode/";
	CSRestService<TransferOrder> rsTransferOrder;
	AtomicInteger transferOrderPageId;
	TransferOrder transferOrder;
	
	CSRestService<TransferOrder> rsTransferOrderUpdateStatus;
	
	private final String urlTransferOrderDetail = "/transferOrderDetail/findAllByTransferOrderId/";
	CSRestService<TransferOrderDetail> rsTransferOrderDetail;

	private final String transferOrderAddEditControllerName  = "Warehouse_TransferOrderAddEditController";
	private final String transferOrderDetailAddEditControllerName  = "Warehouse_TransferOrderDetailAddEditController";

	public Warehouse_TransferOrderController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		if (mode == 0) {
			urlTransferOrder += "IP/"; // INTERNA PRENOSNICA
		} else {
			urlTransferOrder += "PK/"; // POVRACAJ KOMISIONA
		}
		
		transferOrderPageId = new AtomicInteger(0);
		rsTransferOrder = new CSRestService<>(urlTransferOrder);
		rsTransferOrder.setParentTable(tblTransferOrder);
		rsTransferOrderDetail = new CSRestService<>(urlTransferOrderDetail);
		rsTransferOrderDetail.setParentTable(tblTransferOrderDetail);
		rsTransferOrderUpdateStatus = new CSRestService<>("/transferOrder");
		
		List<Object> lstControllerParams = new ArrayList<>();
		lstControllerParams.add(tblTransferOrder);
		lstControllerParams.add(taDescription);
		tblTransferOrder.setAddEditDialog(transferOrderAddEditControllerName, lstControllerParams);
				
		rsTransferOrder.fetch(transferOrderPageId.get(), new ParameterizedTypeReference<JsonResponse<TransferOrder>>(){}, () -> {
			tblTransferOrder.setItems(rsTransferOrder.getDataAsObservableList());			
		});

		tblTransferOrder.onRowDoubleClick((rowData) -> {
			tblTransferOrder.showDoubleClickDefaultAction = true;
		});
		
		tblTransferOrder.onDataNeeded( ()-> {
			rsTransferOrder.fetch(transferOrderPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<TransferOrder>>() {});
			tblTransferOrder.addItems(rsTransferOrder.getDataAsObservableList());
		});
		
		tblTransferOrder.onRowSelectionChanged((oldRow, newRow) -> {
			if (newRow != null) {
				transferOrder = (TransferOrder) newRow;
				taDescription.setText(transferOrder.getDescription());
				rsTransferOrderDetail.setUrl(urlTransferOrderDetail + transferOrder.getId());
				rsTransferOrderDetail.fetch(new ParameterizedTypeReference<JsonResponse<TransferOrderDetail>>(){}, () -> {
					tblTransferOrderDetail.setItems(rsTransferOrderDetail.getDataAsObservableList());					
				});
				
				if (transferOrder.getStatusId() == 1) {
					btnValidate.setDisable(false);
				} else {
					btnValidate.setDisable(true);
				}
			}
		});
		
		// TRANSFER ORDER DETAIL
		tblTransferOrderDetail.setParentTable(tblTransferOrder);
		tblTransferOrderDetail.setAddEditDialog(transferOrderDetailAddEditControllerName);
		
		btnValidate.setOnAction( e-> {
			transferOrder.setStatusId(2); // OVEREN
			rsTransferOrderUpdateStatus.addOrUpdate(transferOrder);
			tblTransferOrder.tableView.refresh();
			btnValidate.setDisable(true);
			Common.ShowNotification("INTERNE PRENOSNICE", "Prenosnica overena!", false);
		});

	}
}
