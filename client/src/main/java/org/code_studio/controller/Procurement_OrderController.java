package org.code_studio.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.database.Client;
import org.code_studio.database.Orders;
import org.code_studio.database.OrdersDetail;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Procurement_OrderController extends BaseController {

	private ApplicationContext ctx;
	
	@FXML private CSClientTable tblSupplier;
	@FXML private CSComboBox<String> cbOrderStatus;
	
	@FXML private CSTable <Orders> tblOrder;
	@FXML private CSTable <OrdersDetail> tblOrdersDetail;
	
	@FXML private Button btnOrdered;
	@FXML private Button btnOrderConfirmation;
	@FXML private Button btnExpectedDeliveryDate;
	@FXML private Button btnReceiveAndDebitReceivingWarehouse;
	@FXML private Button btnFormBackorder;
	@FXML private Button btnExport;
	
	private final String orderDateAddEditControllerName  = "Procurement_OrderDateAddEditController";
	private final String orderConfirmationControllerName = "Procurement_OrderDetailConfirmationController";

	//private final String defaultUrl = "/client/supplier";
	
	private String urlOrders = "";
	private final String urlOrdersAll = "/orders/allPageableByClientId/";
	private final String urlOrdersNonValidated = "/orders/allPageableNonValidatedByClientId/";
	private final String urlOrdersValidated = "/orders/allPageableValidatedByClientId/";
	private final String urlOrdersDetail = "/ordersDetail/allByOrderId/";
	
	private final String orderAddEditControllerName = "Procurement_OrderAddEditController";
	private final String orderDetailAddEditControllerName = "Procurement_OrderDetailAddEditController";
	
	//BaseRestService<Client> rsvcSupplier;
	CSRestService<Orders> rsvcOrders;
	CSRestService<OrdersDetail> rsvcOrdersDetail;
	
	
	public Procurement_OrderController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	@FXML
	public void initialize() {
		try {
			MainController ctrl = ctx.getBean(MainController.class);
			AtomicInteger ordersPageId = new AtomicInteger(0);
			//rsvcSupplier = new BaseRestService<>(defaultUrl);
			rsvcOrders = new CSRestService<>(urlOrders);
			rsvcOrdersDetail = new CSRestService<>(urlOrdersDetail);
	
			cbOrderStatus.setItems(FXCollections.observableArrayList("NEOVERENI", "OVERENI", "SVI"));
			cbOrderStatus.getSelectionModel().select(0);
			
			// SUPPLIER TABLE START
			tblSupplier.refresh();
			
			tblSupplier.csTable.onRowSelectionChanged((oldRow, newRow) -> {
				Client newRowData = (Client) newRow;
				
				if (newRowData != null) {
					setOrdersUrl();
					ordersPageId.set(0);
					rsvcOrders.setUrl(urlOrders + newRowData.getId().toString() + "/");
					rsvcOrders.fetch(ordersPageId.get(), new ParameterizedTypeReference<JsonResponse<Orders>>() {});
					tblOrdersDetail.clear();
					tblOrder.setItems(rsvcOrders.getDataAsObservableList());
				}
			});
			
			tblSupplier.csTable.btnView.setOnAction( e->{
				ctrl.miProcurementSuppliers.fire();
			});
	
			// SUPPLIER TABLE END
	
			// ORDER HEADER
			tblOrder.setParentTable(tblSupplier.csTable);
			tblOrder.onRowDoubleClick( rowData -> {
				tblOrder.showDoubleClickDefaultAction = true;
			});
			
			tblOrder.setAddEditDialog(orderAddEditControllerName);
			tblOrder.onDataNeeded(()->{
				rsvcOrders.fetch(ordersPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<Orders>>() {});
				tblOrder.addItems(rsvcOrders.getDataAsObservableList());
			});
	
			tblOrder.onRowSelectionChanged((oldRow, newRow) -> {
				Orders newRowData = (Orders) newRow;
				if (newRowData != null && newRowData.getId() != null) { //TODO: budz, jer kad dodam novi red, ID je null dok ne fetchujem iz baze autoincrement
					rsvcOrdersDetail.setUrl(urlOrdersDetail + newRowData.getId().toString());
					rsvcOrdersDetail.fetch(new ParameterizedTypeReference<JsonResponse<OrdersDetail>>() {});
					tblOrdersDetail.setItems(rsvcOrdersDetail.getDataAsObservableList());
					
					if (newRowData.getOrderDate() == null) {
						btnOrdered.setDisable(false);
						btnOrderConfirmation.setDisable(true);
						btnExpectedDeliveryDate.setDisable(false);
						btnReceiveAndDebitReceivingWarehouse.setDisable(true);
						btnFormBackorder.setDisable(false);
						btnOrderConfirmation.setDisable(true);
					} else {
						btnOrdered.setDisable(true);
						btnOrderConfirmation.setDisable(false);
						btnExpectedDeliveryDate.setDisable(false);
						btnReceiveAndDebitReceivingWarehouse.setDisable(true);
						btnFormBackorder.setDisable(true);
					}
					// POTVRDJEN - LOADING DATE
					if (newRowData.getLoadingDate() == null) {
						btnOrderConfirmation.setDisable(false);
						btnExpectedDeliveryDate.setDisable(false);
						btnExpectedDeliveryDate.setDisable(false);
						btnReceiveAndDebitReceivingWarehouse.setDisable(true);
						//btnFormBackorder.setDisable(true);
					} else {
						btnOrdered.setDisable(true);
						btnOrderConfirmation.setDisable(true);
						btnReceiveAndDebitReceivingWarehouse.setDisable(false);
					}
					if (newRowData.getReceiveDate() != null) {
						btnOrdered.setDisable(true);
						btnExpectedDeliveryDate.setDisable(true);
						btnOrderConfirmation.setDisable(true);
						btnReceiveAndDebitReceivingWarehouse.setDisable(true);
					} else {
						//btnReceiveAndDebitReceivingWarehouse.setDisable(false);
					}
					if (newRowData.getOrderDate() == null && newRowData.getLoadingDate() == null) {
						btnOrderConfirmation.setDisable(true);
					}
	
				}
				
				if (tblOrdersDetail.tableView.getItems().size() == 0) {
					btnOrdered.setDisable(true);
					btnOrderConfirmation.setDisable(true);
					btnReceiveAndDebitReceivingWarehouse.setDisable(true);
					btnExport.setDisable(true);
				} else {
					btnExport.setDisable(false);
				}
			});
	
			// ORDER DETAIL
			tblOrdersDetail.setParentTable(tblOrder);
			tblOrdersDetail.setAddEditDialog(orderDetailAddEditControllerName);
			tblOrdersDetail.onRowDoubleClick( row -> {
				tblOrdersDetail.showDoubleClickDefaultAction = true;
			});
			
			// BUTTONS ACTIONS
			btnOrdered.setOnAction( e -> {
				Common.displayForm(ControllerFactory.getController(orderDateAddEditControllerName, this.tblOrder, 0), this.tblOrder, btnOrdered.getText());
			});
			
			btnOrderConfirmation.setOnAction( e -> {
				Common.displayForm(ControllerFactory.getController(orderConfirmationControllerName, this.tblOrdersDetail, 1), this.tblOrder, btnOrderConfirmation.getText());
			});
			
			btnExpectedDeliveryDate.setOnAction( e -> {
				Common.displayForm(ControllerFactory.getController(orderDateAddEditControllerName, this.tblOrder, 2), this.tblOrder, btnExpectedDeliveryDate.getText());
			});
			
			btnReceiveAndDebitReceivingWarehouse.setOnAction( e -> {
				Common.displayForm(ControllerFactory.getController(orderDateAddEditControllerName, this.tblOrder, 3), this.tblOrder, btnReceiveAndDebitReceivingWarehouse.getText());
			});
	
			
			cbOrderStatus.onSelectionChanged(e->{
				Integer supplierId = tblSupplier.csTable.getSelectedItem().getId();
				setOrdersUrl();
				ordersPageId.set(0);
				rsvcOrders.setUrl(urlOrders + supplierId.toString() + "/");
				rsvcOrders.fetch(ordersPageId.get(), new ParameterizedTypeReference<JsonResponse<Orders>>() {});
				tblOrder.setItems(rsvcOrders.getDataAsObservableList());
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END
	
	private void setOrdersUrl () {
			switch (cbOrderStatus.getSelectionModel().getSelectedItem()) {
			case "NEOVERENI":
				this.urlOrders = this.urlOrdersNonValidated;
			break;
			case "OVERENI":
				this.urlOrders = this.urlOrdersValidated;
			break;
			case "SVI":
				this.urlOrders = this.urlOrdersAll;
			break;
			default:
				this.urlOrders = this.urlOrdersAll;
			break;
		}
	}
	
}
