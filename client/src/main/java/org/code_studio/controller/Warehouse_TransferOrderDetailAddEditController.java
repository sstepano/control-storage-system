package org.code_studio.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.component.ui.CSItemWarehouseTable;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.database.TransferOrder;
import org.code_studio.database.TransferOrderDetail;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class Warehouse_TransferOrderDetailAddEditController extends BaseController implements Initializable {

	@FXML CSDialogButtons dialogButtons;
	
	@FXML CSTable <TransferOrderDetail> tblTransferOrderDetail;
	final String transferOrderDetailAddEditControllerName  = "Warehouse_TransferOrderDetailAddEditDetailController";
	
	@FXML CSItemWarehouseTable tblWarehouseOrigin;
	@FXML CSItemWarehouseTable tblWarehouseDestination;
	
	CSTable<TransferOrder> tblTransferOrder;
	TransferOrder transferOrder;
	CSTable<TransferOrderDetail> tblTransferOrderDetailParent;
	TransferOrderDetail transferOrderDetail;
	
	@SuppressWarnings("unchecked")
	public Warehouse_TransferOrderDetailAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.tblTransferOrderDetailParent = (CSTable<TransferOrderDetail>) controllerParam;
		this.transferOrderDetail = tblTransferOrderDetailParent.getSelectedItem(); // ovo je null ako nemamo itema u prenosnici
		tblTransferOrder = (CSTable<TransferOrder>) tblTransferOrderDetailParent.getParentTable();
		transferOrder = tblTransferOrder.getSelectedItem();
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		tblWarehouseOrigin.setSelectedWarehouseId(transferOrder.getWarehouseIdOrigin());
		tblWarehouseOrigin.fill();

		// ORIGIN
		tblWarehouseOrigin.csTable.topLabel.setText("MAGACIN POREKLA - " + transferOrder.getWarehouseIdOrigin());
		tblWarehouseOrigin.csTable.setAddButtonVisible(false);
		
		tblWarehouseOrigin.csTable.onRowSelectionChanged((oldRow, newRow) -> {
			ItemWarehouse itemWarehouse = (ItemWarehouse) newRow;
			if(newRow != null) {
				tblWarehouseDestination.rsDataFetch.setUrl("/itemWarehouse/allByWarehouseIdAndItemId/" + transferOrder.getWarehouseIdDestination() + "/" + itemWarehouse.getItemId());
				tblWarehouseDestination.rsDataFetch.fetch(new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>() {});
				tblWarehouseDestination.csTable.setItems(tblWarehouseDestination.rsDataFetch.getDataAsObservableList());
				
				tblTransferOrderDetail.btnAdd.setDisable(false);
			} else {
				tblTransferOrderDetail.btnAdd.setDisable(true);
			}
		});

		// DESTINATION
		tblWarehouseDestination.csTable.topLabel.setText("MAGACIN PRIJEMA - " + transferOrder.getWarehouseIdDestination());
		tblWarehouseDestination.csTable.setAddButtonVisible(false);
		tblWarehouseDestination.csTable.setSearchVisible(false);
		tblWarehouseDestination.csTable.setParentTable(tblWarehouseOrigin.csTable);

		// TRANSFER ORDER DETAIL
		tblTransferOrderDetail.onRowDoubleClick((e) -> {
			tblTransferOrderDetail.showDoubleClickDefaultAction = true;
		});
		tblTransferOrderDetail.setParentTable(tblTransferOrderDetailParent);
		tblTransferOrderDetail.setItems(tblTransferOrderDetailParent.tableView.getItems());
		List<Object> transferOrderDetailAddEditControllerParams = new ArrayList<>();
		transferOrderDetailAddEditControllerParams.add(tblTransferOrderDetail);
		transferOrderDetailAddEditControllerParams.add(transferOrder);
		transferOrderDetailAddEditControllerParams.add(tblWarehouseOrigin.csTable);
		transferOrderDetailAddEditControllerParams.add(tblWarehouseDestination.csTable);
		tblTransferOrderDetail.setAddEditDialog(transferOrderDetailAddEditControllerName, transferOrderDetailAddEditControllerParams);
		
	}
}
