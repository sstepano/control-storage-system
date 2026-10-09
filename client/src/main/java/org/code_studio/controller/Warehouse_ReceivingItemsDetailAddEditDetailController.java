package org.code_studio.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.BillOfReceiptDetail;
import org.code_studio.database.Item;
import org.code_studio.database.Warehouse;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//
public class Warehouse_ReceivingItemsDetailAddEditDetailController extends BaseController implements Initializable {
	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	
	@FXML private CSTextField tfQuantity;
	@FXML private CSTextField tfPurchasePrice;
	@FXML private TextField   tfRow;
	@FXML private TextField   tfShelf;
	@FXML private TextField   tfVertical;
	@FXML private CSComboBox<Warehouse> cbReceivingWarehouse;
	
	private int mode;
	private CSTable <BillOfReceiptDetail> tblBillOfReceiptDetail;
	private CSTable <Item> tblItem;
	private BillOfReceiptDetail billOfReceiptDetail;
	private Item item;
	
	CSRestService<Warehouse> rsWarehouse;
	CSRestService<BillOfReceiptDetail> rsAddUpdateService;
	
	@SuppressWarnings("unchecked")
	public Warehouse_ReceivingItemsDetailAddEditDetailController(Object controllerParam, int mode) {
		this.mode = mode;
		this.tblItem = (CSTable<Item>) ((List<CSTable <?>>) controllerParam).get(0);
		this.tblBillOfReceiptDetail = (CSTable<BillOfReceiptDetail>) ((List<CSTable <?>>) controllerParam).get(1);
		this.item = tblItem.getSelectedItem();
		this.billOfReceiptDetail = tblBillOfReceiptDetail.getSelectedItem();
		
		if (mode == 0) {
			this.billOfReceiptDetail = new BillOfReceiptDetail();
			this.billOfReceiptDetail.setBillOfReceiptId(tblBillOfReceiptDetail.getSelectedItem().getBillOfReceiptId());
		} //else if (mode == 1) {} nema kod edita nista spec, u ovom trenutku

	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		rsWarehouse = new CSRestService<>("/warehouse");
		rsWarehouse.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>() {});
		cbReceivingWarehouse.getItems().addAll(rsWarehouse.getDataAsObservableList());
		
		rsAddUpdateService = new CSRestService<>("/billOfReceiptDetail");
		
		tfPurchasePrice.setText(this.item.getGrossPriceD().toString());
		//TODO: get values from warehouse for this item
		tfRow.setText(null);
		tfShelf.setText(null);
		tfVertical.setText(null);
		
		if (mode == 0) {
			tfQuantity.setText("1");
			cbReceivingWarehouse.getSelectionModel().select(0);
			
		} else {
			tfQuantity.setText(billOfReceiptDetail.getQuantity().toString());
			
			if (billOfReceiptDetail.getReceiveRowId() != null) {
				tfRow.setText(billOfReceiptDetail.getReceiveRowId().toString());
			}
			if (billOfReceiptDetail.getReceiveShelfId() != null) {
				tfShelf.setText(billOfReceiptDetail.getReceiveShelfId().toString());
			}
			if (billOfReceiptDetail.getReceiveVerticalId() != null) {
				tfVertical.setText(billOfReceiptDetail.getReceiveVerticalId().toString());
			}			
			cbReceivingWarehouse.getSelectionModel().select(0); //TODO: select stvarnu vrednost, pre toga dobaciti do ovde ItemWarehouse za ovaj item
		}
		
		// SAVE & CANCEL
		btnCancel.setOnAction(e->{
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
		
		btnSave.setOnAction(e->{
			if (mode == 0) {
				this.billOfReceiptDetail.setItem(tblItem.getSelectedItem());
			}

			if (!tfQuantity.getText().isBlank()) {
				try {
					billOfReceiptDetail.setQuantity(new BigDecimal(tfQuantity.getText()));
				} catch (Exception ex) {
					//TODO: Better catch block!
					System.out.println(ex.getLocalizedMessage());
				}
			}
			if (tfRow.getText() != null && !tfRow.getText().isBlank()) {
				billOfReceiptDetail.setReceiveRowId(tfRow.getText());
			}
			if (tfShelf.getText() != null && !tfShelf.getText().isBlank()) {
				billOfReceiptDetail.setReceiveShelfId(tfShelf.getText());
			}
			if (tfVertical.getText() != null && !tfVertical.getText().isBlank()) {
				billOfReceiptDetail.setReceiveVerticalId(tfVertical.getText());
			}
			if (cbReceivingWarehouse.getSelectionModel().getSelectedItem() != null) {
				billOfReceiptDetail.setReceiveWarehouse(cbReceivingWarehouse.getSelectionModel().getSelectedItem());
			}
			
			BillOfReceiptDetail rsvcReturnObject = rsAddUpdateService.addOrUpdate(billOfReceiptDetail);
			tblBillOfReceiptDetail.tableView.refresh();
			
			if (mode == 0) {
				tblBillOfReceiptDetail.tableView.getItems().add(rsvcReturnObject);
			}
			
			//TODO: Ne radi showNotification, verovatno zato sto vec imamo modal window u pozadini.
			//Common.ShowNotification("Prijemnica - Detalji", "Uspešno snimanje detalja prijemnice!", true);
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});
		
	}
	
}
