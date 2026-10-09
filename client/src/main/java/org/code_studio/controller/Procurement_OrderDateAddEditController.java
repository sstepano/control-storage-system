package org.code_studio.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.database.Orders;
import org.code_studio.rest.CSRestService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Procurement_OrderDateAddEditController extends BaseController implements Initializable {

	@FXML Button btnSave;
	@FXML Button btnCancel;

	@FXML CSDatePicker dtOrderDate;
	
	private CSTable<Orders> tblOrders;
	private Orders order;
	private int mode;
	
	private final String urlAddUpdate = "/orders";
	private CSRestService<Orders> rsvcAddUpdate;
	
	@SuppressWarnings("unchecked")
	public Procurement_OrderDateAddEditController(Object controllerParam, int mode) {
		this.mode = mode;
		tblOrders = (CSTable<Orders>) controllerParam;
		this.order = tblOrders.getSelectedItem();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rsvcAddUpdate = new CSRestService<Orders>(urlAddUpdate);
		
		//initial, uzimamo danas
		dtOrderDate.setValue(LocalDate.now());
		
		if (this.order != null) {
			if (this.mode == 0) {
				if (this.order.getOrderDate() != null) {
					dtOrderDate.setValue(this.order.getOrderDate());
				}
			} else if (this.mode == 2) {
				if (this.order.getDeliveryDate() != null) {				
					dtOrderDate.setValue(this.order.getDeliveryDate());
				}
			}
		}

		btnSave.setOnAction( e-> {
			if (this.mode == 0) { // datum ordera
				this.order.setOrderDate(dtOrderDate.getValue());
			} else if (this.mode == 2) { // ocekivani datum isporuke
				this.order.setDeliveryDate(dtOrderDate.getValue());
			} else if (this.mode == 3) { //Prijem i zaduzenje prijemnog magacina
				this.order.setReceiveDate(dtOrderDate.getValue());
			}
			
			rsvcAddUpdate.addOrUpdate(this.order);
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			
			this.tblOrders.tableView.refresh();
		});

		btnCancel.setOnAction(e->{
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});
	}
	
}
