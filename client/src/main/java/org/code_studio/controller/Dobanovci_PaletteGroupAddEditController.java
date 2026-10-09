package org.code_studio.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.Client;
import org.code_studio.database.Crane;
import org.code_studio.database.Item;
import org.code_studio.database.Palette;
import org.code_studio.database.PaletteDocument;
import org.code_studio.database.PaletteItem;
import org.code_studio.database.PaletteStatus;
import org.code_studio.database.Warehouse;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Dobanovci_PaletteGroupAddEditController extends BaseController {

	@FXML CSDialogButtons dialogButtons;

	@FXML TextField tfClientId;
	@FXML TextField tfClientName;
	@FXML TextField tfPaletteId;
	@FXML TextField tfPaletteName;
	@FXML TextField tfPaletteCode;
	@FXML CSTextField tfBarcode;
	@FXML TextField tfClientPaletteId;
	@FXML CSComboBox<Crane> cbCrane;
	@FXML CSComboBox<Warehouse> cbWarehouse;
	@FXML CSTextField tfWidth;
	@FXML CSTextField tfHeight;
	@FXML CSTextField tfLength;
	@FXML CSTextField tfRowId;
	@FXML CSTextField tfShelfId;
	@FXML CSTextField tfVerticalId;
	@FXML Button btnItemList;
	@FXML TextField tfItemId;
	@FXML TextField tfItemName;
	@FXML CSTextField tfPaletteCount;
	@FXML CSTextField tfItemQty;
	
	private int mode;
	private Client client;
	private CSTable<Palette> tblPalette;
	private Palette palette;
	private PaletteDocument paletteDocument;
	private CSRestService<Palette> rsPalette;
	private CSRestService<Warehouse> rsWarehouse;
	private final String urlPalette = "/palette";
	private final String urlWarehouse = "/warehouse";
	private CSRestService<Crane> rsCrane;
	private CSRestService<String> rsPaletteFreeSpacesByCrane;
	private final String urlPaletteFreeSpacesByCrane = "/palette/unoccupiedPalettePositionsByCrane/";
	List<String> unoccupiedPaletteSpaces;
	private final String urlItemAddEdit = "/item";
	private CSRestService<Item> rsItemAddEdit;
	private AtomicInteger itemPageId;
	@SuppressWarnings("unused") private CSTable<PaletteItem> tblPaletteItem;
	private PaletteItem paletteItem;
	private CSRestService<PaletteItem> rsPaletteItem;
	private final String urlPaletteItem = "/paletteItem";
	private CSRestService<Item> rsItem;
	private final String urlItem = "/item/allPageableByNameAndClientId/";

	
	@SuppressWarnings("unchecked")
	public Dobanovci_PaletteGroupAddEditController(Object controllerParam, int mode) {
		this.mode = mode;
		List<Object> lstControllerParam = (List<Object>) controllerParam;
		this.tblPalette = (CSTable<Palette>) lstControllerParam.get(0);
		this.tblPaletteItem = (CSTable<PaletteItem>) lstControllerParam.get(1);
		this.palette = this.tblPalette.getSelectedItem();
		this.paletteDocument = (PaletteDocument) this.tblPalette.getParentTable().getSelectedItem();
		this.client = (Client) this.paletteDocument.getClient();
		this.paletteItem = new PaletteItem();
		itemPageId = new AtomicInteger(0);
	}

	
	public void initialize() {
		rsPalette = new CSRestService<>(urlPalette);
		tfClientId.setText(this.client.getId().toString());
		tfClientName.setText(this.client.getName());
		rsItemAddEdit = new CSRestService<>(urlItemAddEdit);
		rsItem = new CSRestService<>(urlItem);
		rsPaletteItem = new CSRestService<>(urlPaletteItem);

		rsWarehouse = new CSRestService<>(urlWarehouse);
		rsWarehouse.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>() {});
		cbWarehouse.getItems().addAll(rsWarehouse.getDataAsObservableList());
		cbWarehouse.getSelectionModel().select(0);
		
		rsCrane = new CSRestService<>("/crane/findAllActive");
		rsCrane.fetch(new ParameterizedTypeReference<JsonResponse<Crane>>() {});
		cbCrane.getItems().addAll(rsCrane.getDataAsObservableList());
		cbCrane.getSelectionModel().select(
				paletteDocument.getCraneId() == null
				? 0 
				: paletteDocument.getCraneId().intValue());
		
		rsPaletteFreeSpacesByCrane = new CSRestService<>(urlPaletteFreeSpacesByCrane);
		setCraneProperties(0);
		
		cbCrane.onSelectionChanged( _ -> {
			setCraneProperties(0);
		});
		
		if (mode == 0) {
			this.palette = new Palette();
			this.palette.setDocumentId(this.paletteDocument.getId());
			setCraneProperties(0);
		} else {
			throw new NoSuchMethodError("Editing of palette groups is not supported!");
		}
		
 		btnItemList.setOnAction( _ -> {
			Item selectedItem = (Item) Common.displayForm(ControllerFactory.getController("Items_ItemSelectAddEditController", this.client, 0), btnItemList, "Odabir artikla");
			if (selectedItem != null) {
				tfItemId.setText(selectedItem.getId().toString());
				tfItemName.setText(selectedItem.getName());
				tfItemName.setDisable(true); // ne zelimo da user menja naziv artikla koji vec postoji u db
			}
		});
 		
		tfItemName.textProperty().addListener((_, _, _) -> {
			if (mode == 1) {
				tfItemName.setDisable(true);
				return;
			}

		});
		
		dialogButtons.setValidation(
			new CSEmptyFieldValidator(tfPaletteCount),
			new CSEmptyFieldValidator(tfItemName)
		);

		dialogButtons.getSaveButton().setOnAction( e-> {
			for (int i=0; i < Integer.valueOf(tfPaletteCount.getText()); i++) {
				System.out.println("i: " + i);
				this.palette = new Palette();
				System.out.println("Dokument: " + this.paletteDocument.getId());
				this.palette.setDocumentId(this.paletteDocument.getId());
				setCraneProperties(i);
				this.palette.setRowId(tfRowId.getText());
				this.palette.setShelfId(tfShelfId.getText());
				this.palette.setVerticalId(tfVerticalId.getText());
				PaletteStatus paletteStatus = new PaletteStatus();
				paletteStatus.setId(1); // U radu
				paletteStatus.setName("Ulaz");
				this.palette.setPaletteStatus(paletteStatus);
				this.palette.setCraneId(
						cbCrane.getSelectionModel().getSelectedItem().getId() == null 
						? 0
						: cbCrane.getSelectionModel().getSelectedItem().getId().intValue());
				
				this.palette.setPaletteCode(tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
				this.palette.setClientPaletteCode(tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
				this.palette.setName("Paleta" + tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
				this.palette.setBarcode(client.getId().toString() + "D" + tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
				this.palette.setLength(Integer.parseInt(tfLength.getText()));
				this.palette.setWidth(Integer.parseInt(tfWidth.getText()));
				this.palette.setHeight(Integer.parseInt(tfHeight.getText()));	
				this.palette.setPaletteDocument(this.paletteDocument);
				Palette insertedPalette = rsPalette.addOrUpdate(palette);
				if (mode == 0) {
					this.tblPalette.addItem(insertedPalette);
				}
			
				//ITEM SAVE
				Item item;
				
				if (insertedPalette != null) {
					this.paletteItem.setPaletteId(insertedPalette.getId());
				}
				
				// call service to get item with the specified name, if exists. If not, show message.
				// If there are more than one, show message that user needs to select one of existing.
				rsItem.setUrl(urlItem + tfItemName.getText() + "/" + this.client.getId().toString());
				System.out.println(rsItem.getUrl());
				rsItem.fetch(itemPageId.get(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
				switch (rsItem.getDataAsObservableList().size()) {
					case 0:
						Common.ShowNotification("Artikli na paleti", "Ne postoji artikal sa zadatim nazivom. Biće dodat.", false);
						//add new item to database
						item = new Item();
						item.setClientId(this.client.getId().intValue());
						item.setName(tfItemName.getText());
						item.setCode(tfItemName.getText());
						item.setGrossPriceRmD(new BigDecimal(0.00));
						item.setGrossPriceRmDSpec(new BigDecimal(0.00));
						item.setNetPriceRmD(new BigDecimal(0.00));
						item.setNetPriceRmDSpec(new BigDecimal(0.00));
						item.setGrossPriceD(new BigDecimal(0.00));
						item.setGrossPriceDSpec(new BigDecimal(0.00));
						item.setNetPriceDFak(new BigDecimal(0.00));
						item.setNetPriceDFakSpec(new BigDecimal(0.00));
						item.setGrossPriceDFak(new BigDecimal(0.00));
						item.setGrossPriceDFakSpec(new BigDecimal(0.00));						
						Item insertedItem = rsItemAddEdit.addOrUpdate(item);
						this.paletteItem.setItem(insertedItem);
					break;
					case 1:
						Common.ShowNotification("Artikli na paleti", "Već postoji artikal sa zadatim nazivom. Izabran je automatski.", false);
						item = rsItem.getDataAsObservableList().get(0);
						this.paletteItem.setItem(item);
					break;
					default:
						Common.ShowNotification("Artikli na paleti", "Postoji više od jednog artikla sa zadatim nazivom. Izaberite jedan sa ekrana 'Lista artikala'", true);
					break;
				}
				
				if (!tfItemQty.getText().isBlank()) {
					this.paletteItem.setQuantity(Integer.parseInt(tfItemQty.getText()));
				}

				PaletteItem insertedPaletteItem = rsPaletteItem.addOrUpdate(paletteItem);
				if(insertedPaletteItem != null) {
					Common.ShowNotification("Artikli na paleti", "Artikal je dodat na paletu'", false);
				}

			} // for END
			
			this.tblPalette.tableView.getSelectionModel().select(0);
			
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

	}
	
	void setCraneProperties(int freeSpaceIndex) {
		Crane selectedCrane = this.cbCrane.getValue();
		rsPaletteFreeSpacesByCrane.setUrl(urlPaletteFreeSpacesByCrane + selectedCrane.getId().toString());
		rsPaletteFreeSpacesByCrane.fetch(new ParameterizedTypeReference<JsonResponse<String>>() {});
		unoccupiedPaletteSpaces = rsPaletteFreeSpacesByCrane.getData();
		if (unoccupiedPaletteSpaces.size() > 0) {
			//System.out.println(unoccupiedPaletteSpaces);
			//uvek uzimammo index 0 sto je ustvari PRVI SLEDECI slobodan. Svaki put fetchujemo info sa servera
			// u slucaju da neko u medjuvremenu preuzme poziciju palete.
			tfRowId.setText(unoccupiedPaletteSpaces.get(0).substring(0, 2));
			tfShelfId.setText(unoccupiedPaletteSpaces.get(0).substring(2, 5));
			tfVerticalId.setText(unoccupiedPaletteSpaces.get(0).substring(5, 7));
		} else {
			Common.ShowNotification("Dodavanje nove palete", "Za zadati kran ne postoje slobodna mesta!", true);
		}
		
	}
	
}
