package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;

import org.code_studio.component.ControllerFactory;

import org.code_studio.database.ItemWarehouse;
import org.code_studio.database.ItemWarehouseTotals;
import org.code_studio.database.Warehouse;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;


public class Warehouse_ItemWarehouseController extends BaseController implements Initializable {

	private ApplicationContext ctx;
	
	@FXML private CSTable <Warehouse> tblWarehouse;
	@FXML private CSTable <ItemWarehouse> tblItemWarehouse;
	@FXML private Button btnReservation;
	
	@FXML private CSTextField tfTotalQty;
	@FXML private CSTextField tfReservedQty;
	@FXML private CSTextField tfAvailableQty;
	
	CSRestService<Warehouse> rsWarehouse;
	CSRestService<ItemWarehouse> rsItemWarehouse;
	CSRestService<ItemWarehouseTotals> rsItemWarehouseTotals;
	AtomicInteger warehousePageId;
	AtomicInteger itemWarehousePageId;
	
	private final String urlWarehouse = "/warehouse/allPageable/";
	private final String urlItemWarehouse = "/itemWarehouse/allByWarehouseIdPageable/";
	private final String urlItemWarehouseTotals = "/itemWarehouseTotals/allByWarehouseId/";

	private Object itemWarehouseSelectedAddEditTableItem = null;	
	private final String itemWarehouseAddEditControllerName = "Warehouse_ItemWarehouseAddEditController";
	private final String orderReservationControllerName = "Procurement_OrderReservationController";

	public Warehouse_ItemWarehouseController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle resources) {
		warehousePageId          = new AtomicInteger(0);
		itemWarehousePageId      = new AtomicInteger(0);
		rsWarehouse     = new CSRestService<>(urlWarehouse);
		rsItemWarehouse = new CSRestService<>(urlItemWarehouse);
		rsItemWarehouseTotals = new CSRestService<>(urlItemWarehouseTotals);
		
		MainController ctrl = ctx.getBean(MainController.class);
		
		rsWarehouse.fetch(warehousePageId.get(), new ParameterizedTypeReference<JsonResponse<Warehouse>>() {});
		tblWarehouse.setItems(rsWarehouse.getDataAsObservableList());
		
		//WAREHOUSE
		tblWarehouse.onDataNeeded(() -> {
			rsWarehouse.fetch(warehousePageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<Warehouse>>() {});
			tblWarehouse.addItems(rsWarehouse.getDataAsObservableList());
		});
		
		tblWarehouse.onRowSelectionChanged((oldRow, newRow) -> {
			Warehouse warehouse = (Warehouse) newRow;
			itemWarehousePageId.set(0);
			rsItemWarehouse.setUrl(urlItemWarehouse + warehouse.getId());
			rsItemWarehouse.fetch(itemWarehousePageId.get(), new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>() {});
			tblItemWarehouse.setItems(rsItemWarehouse.getDataAsObservableList());
			tblItemWarehouse.setTopLabelText(warehouse.getName());
			
			rsItemWarehouseTotals.setUrl(urlItemWarehouseTotals + warehouse.getId());
			rsItemWarehouseTotals.fetch(new ParameterizedTypeReference<JsonResponse<ItemWarehouseTotals>>() {});
			ItemWarehouseTotals t = rsItemWarehouseTotals.getDataAsObservableList().get(0);
			tfTotalQty.setText(t.getQty() != null ? t.getQty().toString(): "0");
			tfReservedQty.setText(t.getReservedQty() != null ? t.getReservedQty().toString(): "0");
			tfAvailableQty.setText(t.getAvailableQty() != null ? t.getAvailableQty().toString(): "0");
	});
		
		tblWarehouse.btnEdit.setOnAction(e-> {
			ctrl.miWarehouseWarehouseList.fire();
		});
		
		tblWarehouse.onRowDoubleClick((rowData) -> {
			tblWarehouse.showDoubleClickDefaultAction = true;
		});

		//ITEM
		tblItemWarehouse.onDataNeeded(() -> {
			rsItemWarehouse.fetch(itemWarehousePageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>() {});
			tblItemWarehouse.addItems(rsItemWarehouse.getDataAsObservableList());
		});
		
		tblItemWarehouse.btnEdit.setOnAction(e-> {
			itemWarehouseSelectedAddEditTableItem = tblItemWarehouse.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(itemWarehouseAddEditControllerName, itemWarehouseSelectedAddEditTableItem, 1), tblItemWarehouse
					, tblWarehouse.getSelectedItem().getName());
			//TODO: OVde mi fali da se radi refresh samo ako je uspesna izmena podataka.
			//Desi se da dialog izbaci gresku ali da se ovde svakako refrshuju podaci u gridu i updateuju sa novom vrednoscu, ali da se stvarno nisu updateovali u bazi.
			tblItemWarehouse.tableView.refresh();
		});
		
		tblItemWarehouse.onRowDoubleClick((rowData) -> {
			tblItemWarehouse.showDoubleClickDefaultAction = true;
		});
		
		btnReservation.setOnAction( e-> {
			Common.displayForm(ControllerFactory.getController(orderReservationControllerName, tblItemWarehouse.getSelectedItem(), 0), btnReservation
					, "%itemwarehouse.reservation.formtitle.text");			
		});

	}
}
