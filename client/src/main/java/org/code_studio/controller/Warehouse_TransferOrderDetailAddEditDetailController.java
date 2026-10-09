package org.code_studio.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSDialogButtons;
import org.springframework.context.ApplicationContext;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import org.code_studio.database.ItemWarehouse;
import org.code_studio.database.TransferOrder;
import org.code_studio.database.TransferOrderDetail;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.component.CSTable;

public class Warehouse_TransferOrderDetailAddEditDetailController extends BaseController implements Initializable {
	
	int mode;
	
	@FXML private CSDialogButtons dialogButtons;
	@FXML private HBox hbAvailableQty;
	
	@FXML private ComboBox<String> cbPackageType;
	
	@FXML private CSTextField tfPackageQty;
	@FXML private CSTextField tfMasterboxQty;
	@FXML private CSTextField tfCardboardQty;
	@FXML private CSTextField tfPaletteQty;
	@FXML private CSTextField tfContainerQty;
	
	@FXML private TextField tfAvailableQty;
	@FXML private CSTextField tfTransferQty;
	
	private CSRestService<TransferOrderDetail> rsTransferOrderDetail;
	private CSTable<TransferOrderDetail> tblTransferOrderDetail;
	private CSRestService<ItemWarehouse> rsItemWarehouse;
	private CSTable<ItemWarehouse> tblItemWarehouseOrigin;
	private ItemWarehouse itemWarehouseOrigin;
	private CSTable<ItemWarehouse> tblItemWarehouseDestination;
	private ItemWarehouse itemWarehouseDestination;
	private TransferOrderDetail transferOrderDetail;
	private TransferOrder transferOrder;

	@SuppressWarnings("unchecked")
	public Warehouse_TransferOrderDetailAddEditDetailController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		List<Object> lstControllerParam = (List<Object>) controllerParam;
		
		tblTransferOrderDetail = (CSTable<TransferOrderDetail>) lstControllerParam.get(0);
		transferOrder = (TransferOrder) lstControllerParam.get(1);
		tblItemWarehouseOrigin = (CSTable<ItemWarehouse>) lstControllerParam.get(2);
		itemWarehouseOrigin = tblItemWarehouseOrigin.getSelectedItem();
		tblItemWarehouseDestination = (CSTable<ItemWarehouse>) lstControllerParam.get(3);
		itemWarehouseDestination = tblItemWarehouseDestination.getSelectedItem();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		rsTransferOrderDetail = new CSRestService<>("/transferOrderDetail");
		rsItemWarehouse = new CSRestService<>("/itemWarehouse");
		
		if (itemWarehouseOrigin != null) {
			tfPackageQty.setText(itemWarehouseOrigin.getItem().getPackageQty().toString());
			tfMasterboxQty.setText(itemWarehouseOrigin.getItem().getMasterboxUnitQty().toString());
			tfCardboardQty.setText(itemWarehouseOrigin.getItem().getCardboardUnitQty().toString());
			tfPaletteQty.setText(String.valueOf(itemWarehouseOrigin.getItem().getPaletteUnitQty().intValue()));
			tfContainerQty.setText(itemWarehouseOrigin.getItem().getContainer20inchQty().toString());
			
			tfAvailableQty.setText(itemWarehouseOrigin.getAvailableQty().toString());
		}
		
		if (mode == 1) {
			hbAvailableQty.setVisible(false);
			tfTransferQty.setText(tblTransferOrderDetail.getSelectedItem().getQty().toString());
		} else {
			hbAvailableQty.setManaged(true);
			hbAvailableQty.setVisible(true);
		}
		
		cbPackageType.getSelectionModel().selectedIndexProperty().addListener( (observable, oldIndex, newIndex) -> {
			try {
				switch (newIndex.intValue()) {
				    case(0): //komad
				    	tfAvailableQty.setText(itemWarehouseOrigin.getAvailableQty().toString());
					break;
				    case(1): //pakovanje
				    	tfAvailableQty.setText(String.valueOf(itemWarehouseOrigin.getAvailableQty() / tfPackageQty.getTextAsInteger()));
					break;
				    case(2): //masterbox
				    	tfAvailableQty.setText(String.valueOf(itemWarehouseOrigin.getAvailableQty() / tfMasterboxQty.getTextAsInteger()));
					break;
				    case(3): //cardboard
				    	tfAvailableQty.setText(String.valueOf(itemWarehouseOrigin.getAvailableQty() / tfCardboardQty.getTextAsInteger()));
					break;
				    case(4): //paleta
				    	tfAvailableQty.setText(String.valueOf(itemWarehouseOrigin.getAvailableQty() / tfPaletteQty.getTextAsInteger()));
					break;
				    case(5): //kontejner
				    	tfAvailableQty.setText(String.valueOf(itemWarehouseOrigin.getAvailableQty() / tfContainerQty.getTextAsInteger()));
					break;
				}
			} catch (ArithmeticException ex) {
				tfAvailableQty.setText("0");
				Common.ShowNotification("GREŠKA", "Raspoloživa količina po izabranom tipu pakovanja nije postavljena na artiklu!", true);
			}
		});
		
		dialogButtons.getSaveButton().setOnAction(e->{
			if (Integer.parseInt(tfTransferQty.getText()) 
					> Integer.parseInt(tfAvailableQty.getText())
					|| tfTransferQty.getText().equalsIgnoreCase("0")
					|| tfTransferQty.getText().isBlank()
					) {
				Common.ShowNotification("INTERNE PRENOSNICE", "Izabrana količina za prebacivanje nije validna!", true);
			} else {
				
				if (mode == 0) {
					transferOrderDetail = new TransferOrderDetail();
					transferOrderDetail.setTransferOrderId(transferOrder.getId());
					transferOrderDetail.setItem(itemWarehouseOrigin.getItem());
				} else {
					transferOrderDetail = tblTransferOrderDetail.getSelectedItem();
				}
				
				Integer transferQty = tfTransferQty.getTextAsInteger();
				
				switch (cbPackageType.getSelectionModel().getSelectedIndex()) {
				    case(0): //komad
				    	transferQty *= 1;
					break;
				    case(1): //pakovanje
				    	transferQty *= tfPackageQty.getTextAsInteger();
					break;
				    case(2): //masterbox
				    	transferQty *= tfMasterboxQty.getTextAsInteger();
					break;
				    case(3): //cardboard
				    	transferQty *= tfCardboardQty.getTextAsInteger();
					break;
				    case(4): //paleta
				    	transferQty *= tfPaletteQty.getTextAsInteger();
					break;
				    case(5): //kontejner
				    	
				    	transferQty *= tfContainerQty.getTextAsInteger();
					break;
				}
				
				transferOrderDetail.setQty(transferQty);
				transferOrderDetail.setWarehouseIdOrigin(transferOrder.getWarehouseIdOrigin());
				transferOrderDetail.setWarehouseIdDestination(transferOrder.getWarehouseIdDestination());
				TransferOrderDetail insertedItem = rsTransferOrderDetail.addOrUpdate(transferOrderDetail);
				
				if (insertedItem != null) {
					itemWarehouseOrigin.setQty(itemWarehouseOrigin.getQty() - transferQty);
					itemWarehouseDestination.setQty(itemWarehouseDestination.getQty() + transferQty);
					rsItemWarehouse.addOrUpdate(itemWarehouseOrigin);
					rsItemWarehouse.addOrUpdate(itemWarehouseDestination);
					tblItemWarehouseOrigin.refresh();
					tblItemWarehouseDestination.refresh();
				}
				
				if (mode == 0) {
					ObservableList<TransferOrderDetail> olItem = FXCollections.observableArrayList();
					olItem.add(insertedItem);
					
					if (this.tblTransferOrderDetail.tableView.getItems().size() > 0) {
						this.tblTransferOrderDetail.addItem(insertedItem);
					} else {
						this.tblTransferOrderDetail.setItems(olItem);
					}
					
					this.tblTransferOrderDetail.getParentTable().tableView.refresh();
					((CSTable<TransferOrderDetail>) this.tblTransferOrderDetail.getParentTable()).addItem(insertedItem);

					//TODO: ne updateuje poslednji item, verovatno zato sto je forma modal. Videti da resim u CSTable nekako
					((CSTable<TransferOrderDetail>) this.tblTransferOrderDetail.getParentTable()).tableView.getSelectionModel().selectLast();
				}
				
				Common.ShowNotification("INTERNE PRENOSNICE", "Snimljeno!", false);
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			}
		});
		
	}
}
