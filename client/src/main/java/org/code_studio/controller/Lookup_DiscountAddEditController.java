package org.code_studio.controller;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.InputValidation.CSZeroValueValidator;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Client;
import org.code_studio.database.DiscountGroup;
import org.code_studio.database.Item;
import org.code_studio.database.ItemBranch;
import org.code_studio.database.ItemCatalog;
import org.code_studio.database.ItemGroup;
import org.code_studio.database.ItemSubgroup;
import org.code_studio.database.ItemType;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

public class Lookup_DiscountAddEditController extends BaseController {
	@FXML private CSDialogButtons dialogButtons;
	@FXML public  Label lblHeader;
	@FXML private CSComboBox<DiscountGroup> cbDiscountGroup;
	@FXML private CSTextField tfDiscountEntityName;
	@FXML private CSTextField tfClientName;
	@FXML private CSTextField tfDiscountRate;
	@FXML private CSDatePicker dtDateFrom;
	@FXML private CSDatePicker dtDateTo;
	@FXML private Button btnClientSelect;

	private Client supplier;
	private ItemCatalog itemCatalog;
	private ItemBranch itemBranch;
	private ItemGroup itemGroup;
	private ItemSubgroup itemSubgroup;
	private ItemType itemType;
	private Item item;
	
	private Object controllerParam;
	private int mode = 0;
	private Client client;
	
	private CSRestService<DiscountGroup> rsDiscountGroup;
	
	@SuppressWarnings("unchecked")
	public Lookup_DiscountAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.controllerParam = controllerParam;
		this.client = (Client) ((List<Object>)controllerParam).get(0);
	}
	
	@SuppressWarnings("unchecked")
	public void initialize() {
		try {
			rsDiscountGroup = new CSRestService<DiscountGroup>("/discountGroup");
			rsDiscountGroup.fetch(new ParameterizedTypeReference<JsonResponse<DiscountGroup>>() {});
			cbDiscountGroup.setItemsAndSelectFirstItem(rsDiscountGroup.getDataAsObservableList());
			
			if (client != null) {
				tfClientName.setText(client.getName());
			}
			
			switch (mode) {
				case 0: // supplier
					supplier = ((CSClientTable) ((List<Object>) controllerParam).get(1)).csTable.getSelectedItem();
					cbDiscountGroup.getSelectionModel().select(0);
					tfDiscountEntityName.setText(supplier != null ? supplier.getName() : "");
				break;
				case 1: // catalog
					itemCatalog = ((CSTable<ItemCatalog>) ((List<Object>) controllerParam).get(1)).getSelectedItem();
					cbDiscountGroup.getSelectionModel().select(1);
					tfDiscountEntityName.setText(itemCatalog != null ? itemCatalog.getName() : "");
				break;
				case 2: // branch
					itemBranch = ((CSTable<ItemBranch>) ((List<Object>) controllerParam).get(1)).getSelectedItem();
					cbDiscountGroup.getSelectionModel().select(2);
					tfDiscountEntityName.setText(itemBranch != null ? itemBranch.getName() : "");
				break;
				case 3: // group
					itemGroup = ((CSTable<ItemGroup>) ((List<Object>) controllerParam).get(1)).getSelectedItem();
					cbDiscountGroup.getSelectionModel().select(3);
					tfDiscountEntityName.setText(itemGroup != null ? itemGroup.getName() : "");
				break;
				case 4: // subgroup
					itemSubgroup = ((CSTable<ItemSubgroup>) ((List<Object>) controllerParam).get(1)).getSelectedItem();
					cbDiscountGroup.getSelectionModel().select(4);
					tfDiscountEntityName.setText(itemSubgroup != null ? itemSubgroup.getName() : "");
				break;
				case 5: // type
					itemType = ((CSTable<ItemType>) ((List<Object>) controllerParam).get(1)).getSelectedItem();
					cbDiscountGroup.getSelectionModel().select(5);
					tfDiscountEntityName.setText(itemType != null ? itemType.getName() : "");
				break;
				case 6: // item
					item = ((CSTable<Item>) ((List<Object>) controllerParam).get(1)).getSelectedItem();
					cbDiscountGroup.getSelectionModel().select(6);
					tfDiscountEntityName.setText(item != null ? item.getName() : "");
				break;
			}
			
			btnClientSelect.setOnAction( e-> {
				client = (Client) Common.displayForm(ControllerFactory.getController("Sales_ClientSelectController", null, 0), (Parent) e.getSource(), "Odabir klijenta");
				if (client != null) {
					tfClientName.setText(client.getName());
				} else {
					tfClientName.setText("SVI KLIJENTI");
				}
			});
			
			dialogButtons.setValidation(
				 new CSZeroValueValidator(tfDiscountRate) 
			);
			
			dialogButtons.getSaveButton().setOnAction( e-> {
				this.setReturnValue(Integer.valueOf(tfDiscountRate.getTextAsInteger()));
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});

		}
		catch (Exception ex) {
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
}
