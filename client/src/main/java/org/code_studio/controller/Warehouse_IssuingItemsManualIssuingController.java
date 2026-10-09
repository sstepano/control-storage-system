package org.code_studio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.code_studio.component.CSBarcodeReader;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.TransferOrder;
import org.code_studio.database.TransferOrderDetail;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Warehouse_IssuingItemsManualIssuingController extends BaseController {

	@FXML CSDialogButtons dialogButtons;
	@FXML CSTable<TransferOrderDetail> tblTransferOrderDetail;
	@FXML CSTextField tfBarcode;
	@FXML CSTextField tfLastEntry;
	@FXML CheckBox ckbDisplayOnlyUnissued;
	
	@FXML Button btnIssueSelectedItem;
	@FXML Button btnRevertSelectedItem;
	@FXML Button btnIssueAllItems;
	@FXML Button btnRevertAllItems;
	
	@FXML CSTextField tfTotalQty;
	@FXML CSTextField tfTotalIssuedQty;
	
	private List<Object> lstControllerParams;
	private CSTable<TransferOrderDetail> tblTransferOrderDetailParent;
	private TransferOrder transferOrder;
	
	private final String urlTransferOrderDetail = "/transferOrderDetail";
	private CSRestService<TransferOrderDetail> rsTransferOrderDetail;
	
	@SuppressWarnings("unchecked")
	public Warehouse_IssuingItemsManualIssuingController(Object controllerParam, int mode, ApplicationContext ctx) {
		lstControllerParams = (List<Object>) controllerParam;
		tblTransferOrderDetailParent = (CSTable<TransferOrderDetail>) lstControllerParams.get(0);
		transferOrder = (TransferOrder) lstControllerParams.get(1);
		rsTransferOrderDetail = new CSRestService<>(urlTransferOrderDetail);
	}
	
	public void initialize() {
		try {
			dialogButtons.setSaveButtonVisible(false);
			tblTransferOrderDetail.setItems(FXCollections.observableArrayList(transferOrder.getTransferOrderDetail()));
			tfTotalQty.setText(transferOrder.getTotalQty().toString());
			tfTotalIssuedQty.setText(transferOrder.getTotalIssuedQty().toString());
			
			btnIssueSelectedItem.setOnAction( e -> {
				issueItems(0, null);
			});
			
			btnRevertSelectedItem.setOnAction( e -> {
				revertItems(0);
			});
			
			btnIssueAllItems.setOnAction( e -> {
				issueItems(1, null);
			});
			
			btnRevertAllItems.setOnAction( e -> {
				revertItems(1);
			});
			
			ckbDisplayOnlyUnissued.setOnAction( e -> {
				if (ckbDisplayOnlyUnissued.isSelected()) {
					filterUnissuedOnly();
				} else {
					tblTransferOrderDetail.setItems(FXCollections.observableArrayList(transferOrder.getTransferOrderDetail()));
				}
			});
		
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
		
	} // initialize end
	
	/**
	 * We must do this in postInit, because getScene() used in CSBarcodeReader
	 * returns value only if window is created and visible
	 */
	@Override
	public void postInitialize() {
		CSBarcodeReader barcodeReader = new CSBarcodeReader(tfBarcode, true);
		barcodeReader.register();
		barcodeReader.onBarcodeEntered( barcode -> {
			issueItems(0, barcode);
			tfBarcode.clear();
		});
	}
	
	private void issueItems(int mode, String barcode) {
		TransferOrderDetail tod;
		
		if (barcode == null) {
			tod = tblTransferOrderDetail.getSelectedItem();
		} else {
			try {
			tod = tblTransferOrderDetail.tableView.getItems().stream()
				.filter(item -> item.getBarcode().equals(tfBarcode.getText()))
				.toList().getFirst();
			} catch (Exception ex) {
				Common.ShowNotification("UPOZORENJE", String.format("Barkod \"%s\" ne postoji", tfBarcode.getText()), false);
				return;
			}
		}
				
		if (mode == 0) { // one item
			tod.setIssuedQty(barcode != null ? tod.getIssuedQty() + 1 : tod.getQty());
			if (!ckbDisplayOnlyUnissued.isSelected() && tod.getQtyDifference() == 0) {
				tblTransferOrderDetail.tableView.getSelectionModel().select(
					tblTransferOrderDetail.tableView.getSelectionModel().getSelectedIndex() + 1
				);
			}
		} else { // all items
			tblTransferOrderDetail.tableView.getItems().forEach( item -> {
				item.setIssuedQty(item.getQty());
				rsTransferOrderDetail.addOrUpdate(item);
			});
		}
		
		rsTransferOrderDetail.addOrUpdate(tod);
		tfTotalQty.setText(transferOrder.getTotalQty().toString());
		tfTotalIssuedQty.setText(transferOrder.getTotalIssuedQty().toString());
		tblTransferOrderDetail.refresh();
		tblTransferOrderDetailParent.refresh();
		
		tfLastEntry.setText(
			  "ŠIFRA: " + tod.getItemCode() 
			+ " | BARKOD: " + tod.getBarcode()
			+ " | KOLIČINA: " + tod.getIssuedQty()
			+ " | RAZLIKA: " + tod.getQtyDifference()
		);
		
		if (ckbDisplayOnlyUnissued.isSelected()) {
			ckbDisplayOnlyUnissued.getOnAction().handle(null);
		}
	}
	
	private void revertItems(int mode) {
		TransferOrderDetail tod = tblTransferOrderDetail.getSelectedItem();
		
		if (mode == 0) { // one item
			tod.setIssuedQty(0);
			rsTransferOrderDetail.addOrUpdate(tod);
		} else { // all items
			tblTransferOrderDetail.tableView.getItems().forEach( item -> {
				item.setIssuedQty(0);
				rsTransferOrderDetail.addOrUpdate(item);
			});
		}
		
		tfTotalQty.setText(transferOrder.getTotalQty().toString());
		tfTotalIssuedQty.setText(transferOrder.getTotalIssuedQty().toString());
		tblTransferOrderDetail.refresh();
		tblTransferOrderDetailParent.refresh();
	}
	
	/**
	 * Filter issued/unissued rows in the table
	 */
	private void filterUnissuedOnly () {
		ObservableList<TransferOrderDetail> lstAllItems = tblTransferOrderDetail.tableView.getItems();
		List<TransferOrderDetail> lstFilteredItems = 
				lstAllItems
				.stream()
				.filter( item -> item.getIssuedQty() != item.getQty())
				.collect(Collectors.toList());
		tblTransferOrderDetail.setItems(FXCollections.observableArrayList(lstFilteredItems));
	}
	
} // class end
