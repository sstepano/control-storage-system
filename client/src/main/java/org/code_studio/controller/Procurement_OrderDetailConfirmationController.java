package org.code_studio.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.database.Orders;
import org.code_studio.database.OrdersDetail;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Procurement_OrderDetailConfirmationController extends BaseController implements Initializable {

	@FXML Button btnSave;
	@FXML Button btnCancel;

	@FXML TextField tfOrderId;
	@FXML TextField tfTotalItemCount;
	@FXML TextField tfTotalDeliveredItemCount;
	@FXML TextField tfTotalValue;
	@FXML TextField tfTotalOrderedCount;
	
	@FXML CSTable<OrdersDetail> tblOrdersDetailFxml;
	@FXML CSTable<OrdersDetail> tblOrdersBottomFxml;
	
	private CSTable<OrdersDetail> tblOrdersDetail;
	private CSTable<Orders> tblOrders;
	private Orders order;
	
	CSRestService<OrdersDetail> rsvcPreviousOrders;
	final String urlPreviousOrders = "/ordersDetail/allPreviousOrdersByItemId/";
	
	//Service AddEdit
	CSRestService<Orders> rsAddUpdateService;
	
	
	@SuppressWarnings("unchecked")
	public Procurement_OrderDetailConfirmationController(Object controllerParam, int mode) {
		this.tblOrdersDetail = (CSTable<OrdersDetail>) controllerParam;
		this.tblOrders = (CSTable<Orders>) this.tblOrdersDetail.getParentTable(); 
		tblOrdersDetail.getSelectedItem();
		this.order = tblOrders.getSelectedItem();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rsAddUpdateService = new CSRestService<>("/orders");
		rsvcPreviousOrders = new CSRestService<>(urlPreviousOrders);
		
		tfOrderId.setText(order.getId().toString());
		tblOrdersDetailFxml.setItems(tblOrdersDetail.tableView.getItems());
		
		tblOrdersDetailFxml.onRowSelectionChanged( (oldRow, newRow ) -> {
			Integer itemId = ((OrdersDetail) newRow).getItemId();
			if (itemId != null) {
				rsvcPreviousOrders.setUrl(urlPreviousOrders + itemId.toString());
				rsvcPreviousOrders.fetch(new ParameterizedTypeReference<JsonResponse<OrdersDetail>>() {});
				tblOrdersBottomFxml.setItems(rsvcPreviousOrders.getDataAsObservableList());
			} else {
				tblOrdersBottomFxml.clear();
			}
		});
		
		tfTotalItemCount.setText(Integer.toString(tblOrdersDetailFxml.getRowCount()));
		BigDecimal[] bdArray = new BigDecimal[3];
		bdArray[0] = new BigDecimal(0); //ordered
		bdArray[1] = new BigDecimal(0); //delivered
		bdArray[2] = new BigDecimal(0); //value
		
		tblOrdersDetailFxml.tableView.getItems().forEach(item->{
			if (item.getOrderedQty() != null) {
				bdArray[0] = bdArray[0].add(item.getOrderedQty());
			}
			
			if (item.getDeliveredQty() != null) {
				bdArray[1] = bdArray[1].add(item.getDeliveredQty()); 
			}
			
			if (item.getPrice() != null) {
				bdArray[2] = bdArray[2].add(item.getPrice()); 
			}
			
			
		});
		
		tfTotalOrderedCount.setText(bdArray[0].toString());
		tfTotalDeliveredItemCount.setText(bdArray[1].toString());
		tfTotalValue.setText(bdArray[0].multiply(bdArray[2]).toString());

		btnSave.setOnAction(e-> {
			if (this.tblOrdersDetail != null) {
				this.order.setValidationDate(CSDatePicker.localDateTimeToDate(LocalDateTime.now()));
				this.order.setValidatedByUserId(1); //TODO: USER KOJI JE ULOGOVAN
				rsAddUpdateService.addOrUpdate(this.order);
			}
		
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});

		btnCancel.setOnAction(e->{
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
	}
	
}
