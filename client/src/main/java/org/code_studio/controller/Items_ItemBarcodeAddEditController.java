package org.code_studio.controller;

import java.util.List;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import org.springframework.context.ApplicationContext;

import javafx.scene.Node;
import javafx.scene.control.Button;
import org.code_studio.database.Client;
import org.code_studio.database.Item;
import org.code_studio.database.ItemBarcode;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;

public class Items_ItemBarcodeAddEditController extends BaseController {
	@FXML Button btnSave;
	@FXML Button btnCancel;
	
	@FXML CSTextField tfItemName;
	@FXML CSTextField tfBarcode;
	@FXML Button btnItemList;
	
	private int mode;
	private CSTable<ItemBarcode> tblItemBarcode;
	private ItemBarcode itemBarcode;
	private Item selectedItem;
	
	private CSTable<Client> tblSupplier;
	private Client supplier;
	
	private final String urlAddOrUpdateItemBarcode = "/itemBarcode";
	private CSRestService<ItemBarcode> rsAddOrUpdateItemBarcode;
	
	@SuppressWarnings("unchecked")
	public Items_ItemBarcodeAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		List<Object> lstControllerParams = (List<Object>) controllerParam;
		this.tblItemBarcode = (CSTable<ItemBarcode>) lstControllerParams.get(0);
		this.itemBarcode = this.tblItemBarcode.getSelectedItem();
		
		this.tblSupplier = (CSTable<Client>) lstControllerParams.get(1);
		supplier = tblSupplier.getSelectedItem();
	}


	public void initialize() {
		try {
			rsAddOrUpdateItemBarcode = new CSRestService<ItemBarcode>(urlAddOrUpdateItemBarcode);
			
			tfItemName.textProperty().addListener( _ -> {
				btnSave.setDisable(tfBarcode.getText().length() == 0 || tfItemName.getText().length() == 0);
			});
			
			tfBarcode.textProperty().addListener( _ -> {
				btnSave.setDisable(tfBarcode.getText().length() == 0 || tfItemName.getText().length() == 0);
			});
			
			if (mode == 0) { //add
				btnItemList.setDisable(false);
				this.itemBarcode = new ItemBarcode();
			} else { //edit
				btnItemList.setDisable(true);
				//TODO: tfItemName.setText(itemBarcode.getItemName());
				tfBarcode.setText(itemBarcode.getBarcode());
			}
			
			btnItemList.setOnAction( _ -> {
				selectedItem = (Item) Common.displayForm(ControllerFactory.getController("Items_ItemSelectAddEditController", this.supplier, 0), btnItemList, "Odabir artikla");
				if (selectedItem != null) {
					//tfItemId.setText(selectedItem.getId().toString());
					tfItemName.setText(selectedItem.getName());
					this.itemBarcode.setItemId(selectedItem.getId().intValue());
				}
			});
	
			btnSave.setOnAction( e-> {
				itemBarcode.setBarcode(tfBarcode.getText());
				
				ItemBarcode insertedItem = rsAddOrUpdateItemBarcode.addOrUpdate(itemBarcode);
				if (mode == 0) {
					this.tblItemBarcode.addItem(insertedItem);
				}
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
	
			});
	
			btnCancel.setOnAction(e->{
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
}
