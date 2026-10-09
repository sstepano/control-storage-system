package org.code_studio.controller;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Client;
import org.code_studio.database.Item;
import org.code_studio.database.Palette;
import org.code_studio.database.PaletteItem;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Dobanovci_PaletteItemAddEditController extends BaseController {

	@FXML CSDialogButtons dialogButtons;

	@FXML TextField    tfPaletteId;
	@FXML TextField    tfItemId;
	@FXML TextField    tfItemName;
	@FXML CSTextField  tfQuantity;
	@FXML CSTextField  tfWeight;
	@FXML CSDatePicker dtExpiryDate;
	@FXML TextArea     taDescription;
	@FXML Button       btnItemList;

	private int mode;
	private Palette palette;

	private CSTable<PaletteItem> tblPaletteItem;
	private PaletteItem paletteItem;
	private CSRestService<PaletteItem> rsPaletteItem;
	private final String urlPaletteItem = "/paletteItem";
	
	private Client client;
	private Item selectedItem;

	private CSRestService<Item> rsItem;
	private final String urlItem = "/item/allPageableByNameAndClientId/";
	private final String urlItemAddEdit = "/item";
	private CSRestService<Item> rsItemAddEdit;
	private AtomicInteger itemPageId;
	
	@SuppressWarnings("unchecked")
	public Dobanovci_PaletteItemAddEditController(Object controllerParam, int mode) {
		this.mode = mode;
		this.tblPaletteItem = (CSTable<PaletteItem>) controllerParam;
		this.paletteItem = this.tblPaletteItem.getSelectedItem();
		this.palette = (Palette) this.tblPaletteItem.getParentTable().getSelectedItem();
		
		this.client = (Client) this.tblPaletteItem.getParentTable().getParentTable().getParentTable().getSelectedItem();
		itemPageId = new AtomicInteger(0);
	}


	@SuppressWarnings("unused")
	public void initialize() {
		rsPaletteItem = new CSRestService<>(urlPaletteItem);
		tfPaletteId.setText(this.palette.getId().toString());
		
		rsItem = new CSRestService<>(urlItem);
		rsItemAddEdit = new CSRestService<>(urlItemAddEdit);
		
		if (mode == 0) {
			this.paletteItem = new PaletteItem();
			this.paletteItem.setPaletteId(this.palette.getId());
			dtExpiryDate.setValue(LocalDate.now());
		} else {
			dialogButtons.getSaveButton().setDisable(false);
			btnItemList.setDisable(true);
			tfItemId.setText(this.paletteItem.getItem().getId().toString());
			tfItemName.setDisable(true); // Ne zelimo da user menja naziv vec unetog artikla. Moze da menja samo kolicinu.
			tfItemName.setText(this.paletteItem.getItemName());
			tfQuantity.setText(this.paletteItem.getQuantity().toString());
			if (paletteItem.getWeight() != null) {
				tfWeight.setText(paletteItem.getWeight().toString());
			} else {
				tfWeight.setText("0");
			}
			
			if (paletteItem.getExpiryDate() != null) {
				dtExpiryDate.setValue(paletteItem.getExpiryDate());
			}
			taDescription.setText(paletteItem.getDescription());
		}
		
		btnItemList.setOnAction( e -> {
			selectedItem = (Item) Common.displayForm(ControllerFactory.getController("Items_ItemSelectAddEditController", this.client, 2), btnItemList, "Odabir artikla");
			if (selectedItem != null) {
				tfItemId.setText(selectedItem.getId().toString());
				tfItemName.setText(selectedItem.getName());
				tfItemName.setDisable(true); // ne zelimo da user menja naziv artikla koji vec postoji u db
			}
		});
		
		tfItemName.textProperty().addListener((observable, oldValue, newValue) -> {
			if (mode == 1) {
				tfItemName.setDisable(true);
				return;
			}

		});
		

		dialogButtons.getSaveButton().setOnAction( e-> {
			Item item;
			
			if (tfQuantity.getText().length() == 0) {
				Common.ShowNotification("Artikli na paleti - neuspesno snimanje", "Kolicina za zadati artikal nije navedena.", true);
				return;
			}
			
			if (!tfPaletteId.getText().isBlank()) {
				this.paletteItem.setPaletteId(Integer.parseInt(tfPaletteId.getText()));
			}
			
			if (!tfItemId.getText().isBlank() && !tfItemId.getText().equalsIgnoreCase("AUTO")) {
				this.paletteItem.setItem(selectedItem == null ? this.paletteItem.getItem() : selectedItem); // kod edit-a selectedItem je null
			} else {
				// call service to get item with the specified name, if exists. If not, show message.
				// If there are more than one, show message that user needs to select one of existing.
				rsItem.setUrl(urlItem + tfItemName.getText() + "/" + this.client.getId().toString());
				rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
				switch (rsItem.getDataAsObservableList().size()) {
					case 0:
						Common.ShowNotification("Artikli na paleti", "Ne postoji artikal sa zadatim nazivom. Biće dodat.", false);
						//add new item to database
						item = new Item();
						item.setClientId(this.client.getId().intValue());
						item.setName(tfItemName.getText());
						item.setCode(tfItemName.getText());
						Item insertedItem = rsItemAddEdit.addOrUpdate(item);
						this.paletteItem.setItem(insertedItem);
						//System.out.println(insertedItem.getId());
					break;
					case 1:
						System.out.println("Već postoji artikal sa zadatim nazivom. Izabran je automatski.");
						//Common.ShowNotification("Artikli na paleti", "Već postoji artikal sa zadatim nazivom. Izabran je automatski.", false);
						item = rsItem.getDataAsObservableList().get(0);
						this.paletteItem.setItem(item);
					break;
					default:
						Common.ShowNotification("Artikli na paleti", "Postoji više od jednog artikla sa zadatim nazivom. Izaberite jedan sa ekrana 'Lista artikala'", true);
					break;
				}
			}
			
			this.paletteItem.setExpiryDate(dtExpiryDate.getValue());
			this.paletteItem.setDescription(taDescription.getText());
			
			if (!tfQuantity.getText().isBlank()) {
				this.paletteItem.setQuantity(Integer.parseInt(tfQuantity.getText()));
			}

			if (!tfWeight.getText().isBlank()) {
				this.paletteItem.setWeight(Integer.parseInt(tfWeight.getText()));
			}


			PaletteItem insertedPaletteItem = rsPaletteItem.addOrUpdate(paletteItem);
	
			if (mode == 0) {
				this.tblPaletteItem.addItem(insertedPaletteItem);
			}

			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();

		});
		
		
		/**
		 * Form validation
		 */
		dialogButtons.setValidation(
			new CSEmptyFieldValidator(tfQuantity),
			new CSEmptyFieldValidator(tfWeight),
			new CSEmptyFieldValidator(tfItemName)
		);

	} // init end
	
}
