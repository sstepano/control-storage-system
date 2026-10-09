package org.code_studio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSPhotoView;
import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSItemTable;
import org.code_studio.database.Client;
import org.code_studio.database.CustomsGroup;
import org.code_studio.database.Item;
import org.code_studio.database.ItemGroup;
import org.code_studio.database.ItemSubgroup;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.database.Orders;
import org.code_studio.database.OrdersDetail;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Procurement_OrderDetailAddEditController extends BaseController {

	@FXML Button btnSave;
	@FXML Button btnCancel;

	@FXML TextField tfOrderId;
	@FXML TextField tfTotalItemCount;
	@FXML TextField tfTotalDeliveredItemCount;
	@FXML TextField tfTotalValue;
	@FXML TextField tfTotalOrderedCount;
	@FXML TextField tfFullSupplierName;
	@FXML TextField tfPOBox;
	@FXML TextField tfCity;
	@FXML TextField tfAddress;
	@FXML TextField tfSupplierOrderId;
	@FXML TextField tfBottomOrderId;
	@FXML TextField tfYear;
	@FXML CSDatePicker dtOrderOpenDate;
	
	@FXML TextField tfTotalOrderedQty;
	@FXML TextField tfTotalDeliveredQty;
	@FXML TextField tfGroupName;
	@FXML TextField tfSubgroupName;
	@FXML TextField tfAvailableQty;
	@FXML TextField tfNameEng;
	@FXML TextField tfCustomsTariffId;
	@FXML TextField tfVAT;
	
	@FXML CSTable<OrdersDetail> tblOrdersDetailFxml;
	@FXML CSTable<OrdersDetail> tblOrdersFxml;
	@FXML CSItemTable tblItem;
	@FXML CSTable<OrdersDetail> tblPreviousOrders;
	@FXML CSTable<Item> tblItemPrice;
	@FXML CSTable<ItemWarehouse> tblItemWarehouse;
	
	@FXML private CSPhotoView pvImage;
	

	@SuppressWarnings("unused")
	private int mode;
	
	private CSTable<OrdersDetail> tblOrdersDetail;
	private CSTable<Orders> tblOrders;
	private Orders order;
	private Client supplier;
	
	CSRestService<OrdersDetail> rsPreviousOrders;
	final String urlPreviousOrders = "/ordersDetail/allPreviousOrdersByItemId/";
	
	CSRestService<Item> rsvcItem;
	final String urlItem = "/item/allByClientIdPageable/";
	AtomicInteger itemPageId = new AtomicInteger(0);
	
	CSRestService<ItemWarehouse> rsItemWarehouse;
	CSRestService<CustomsGroup> rsCustomsGroup;
	CSRestService<ItemGroup> rsItemGroup;
	CSRestService<ItemSubgroup> rsItemSubgroup;
	CSRestService<Client> rsSupplier;
	CSRestService<Orders> rsAddUpdateService;
	
	@SuppressWarnings("unchecked")
	public Procurement_OrderDetailAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.tblOrdersDetail = (CSTable<OrdersDetail>) controllerParam;
		this.tblOrders = (CSTable<Orders>) this.tblOrdersDetail.getParentTable(); 
		this.order = tblOrders.getSelectedItem();
	}

	@FXML public void initialize() {
		try {
			rsSupplier = new CSRestService<>("/client/");
			rsSupplier.setUrl("/client/" + this.order.getClientId().toString());
			rsSupplier.fetch(new ParameterizedTypeReference<JsonResponse<Client>>() {});
			
			List<Object> lstControllerParams = new ArrayList<>();
			lstControllerParams.add(tblItem.csTable);
			lstControllerParams.add(tblOrdersDetail);
			tblItem.csTable.setAddEditDialog("Procurement_OrderWorkOrderDetailAddEditController", lstControllerParams);
			tblItem.csTable.onRowDoubleClick( row -> {
				tblItem.csTable.showDoubleClickDefaultAction = true;
			});
			tblItem.refresh();
	
			if (rsSupplier.getDataAsObservableList().size() > 0) {
				this.supplier = rsSupplier.getDataAsObservableList().get(0);
				tfFullSupplierName.setText(this.supplier.getFullName());
				tfPOBox.setText(this.supplier.getPoBox().toString());
				tfCity.setText(this.supplier.getPoBox().toString());
				tfAddress.setText(this.supplier.getAddress());
			}
	
			tfSupplierOrderId.setText(order.getClientOrderId() == null ? "0" : order.getClientOrderId().toString());
			tfBottomOrderId.setText(this.order.getId().toString());
			tfYear.setText(this.order.getOrderDate() == null ? "" : Integer.toString(this.order.getOrderDate().getYear()));
			dtOrderOpenDate.setValue(CSDatePicker.dateToLocalDate(this.order.getOpenDate()));
			
			rsItemWarehouse = new CSRestService<>("/customsGroup/");
			rsCustomsGroup = new CSRestService<>("/itemWarehouse/");
			rsItemGroup = new CSRestService<>("/itemGroup/");
			rsItemSubgroup = new CSRestService<>("/itemSubgroup/");
			
			rsAddUpdateService = new CSRestService<>("/orders");
			rsPreviousOrders = new CSRestService<>(urlPreviousOrders);
			ObservableList<Item> olItem = FXCollections.observableArrayList();
			
			olItem.clear();
			olItem.add(tblItem.csTable.getSelectedItem());
			tblItemPrice.setItems(olItem);
			tblOrdersDetailFxml.setItems(tblOrdersDetail.tableView.getItems());
			
			tblItem.csTable.onRowSelectionChanged( (oldRow, newRow ) -> {
				Item item = (Item) newRow;
				if (item != null) {
					tblItem.csTable.btnAdd.setDisable(false);
					Integer itemId = item.getId();
					rsPreviousOrders.setUrl(urlPreviousOrders + itemId.toString());
					rsPreviousOrders.fetch(new ParameterizedTypeReference<JsonResponse<OrdersDetail>>() {});
					tblPreviousOrders.setItems(rsPreviousOrders.getDataAsObservableList());
					
					//CustomsGroup (Tariff and VAT)
					if (item.getTariffGroupId() != null) {
						rsCustomsGroup.setUrl("/customsGroup/" + item.getTariffGroupId().toString());
						rsCustomsGroup.fetch(new ParameterizedTypeReference<JsonResponse<CustomsGroup>>() {});
						tfCustomsTariffId.setText(((CustomsGroup)rsCustomsGroup.getDataAsObservableList().get(0)).getTariffNumber());
						tfVAT.setText(((CustomsGroup)rsCustomsGroup.getDataAsObservableList().get(0)).getCustomsRate().toString());
					}
					
					//ItemGroup
					if (item.getGroupId() != null) {
						rsItemGroup.setUrl("/itemGroup/" + item.getGroupId().toString());
						rsItemGroup.fetch(new ParameterizedTypeReference<JsonResponse<ItemGroup>>() {});
						if (rsItemGroup.getDataAsObservableList().size() > 0) {
							tfGroupName.setText(((ItemGroup)rsItemGroup.getDataAsObservableList().get(0)).getName());	
						}
					}
					
					//ItemSubgroup
					if (item.getSubgroupId() != null) {
						rsItemSubgroup.setUrl("/itemSubgroup/" + item.getSubgroupId().toString());
						rsItemSubgroup.fetch(new ParameterizedTypeReference<JsonResponse<ItemSubgroup>>() {});
						if (rsItemSubgroup.getDataAsObservableList().size() > 0) {
							tfSubgroupName.setText(((ItemSubgroup)rsItemSubgroup.getDataAsObservableList().get(0)).getName());
						}
					}
					
					olItem.clear();
					olItem.add(tblItem.csTable.getSelectedItem());
					tblItemPrice.setItems(olItem);
					
					rsItemWarehouse.setUrl("/itemWarehouse/" + itemId.toString());
					rsItemWarehouse.fetch(new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>() {});
					tblItemWarehouse.setItems(rsItemWarehouse.getDataAsObservableList());
					tfAvailableQty.setText(tblItemWarehouse.getSelectedItem() == null ? "" : tblItemWarehouse.getSelectedItem().getAvailableQty().toString());
					tfNameEng.setText(item.getNameEng());
					
					pvImage.setImagePath(item.getImagePath());
				} else {
					tblPreviousOrders.clear();
					olItem.clear();
					tblItem.csTable.btnAdd.setDisable(true);
				}
			});
			
			btnCancel.setOnAction( e-> {
				((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
			});
	
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END
	
}
