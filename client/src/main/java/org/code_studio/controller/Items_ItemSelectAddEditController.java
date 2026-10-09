package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Client;
import org.code_studio.database.Item;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Items_ItemSelectAddEditController extends BaseController implements Initializable {
	
	@FXML CSDialogButtons dialogButtons;
	@FXML private CSTable <Item> tblItem;
	
	private int mode; // 0 insert, 1 edit, 2 custom koristimo kod prikaza svih itema nezavisno od client id-a
	private Client client;
	private Integer pageSize = 50;
	
	String urlItem;
	private AtomicInteger itemPageId;
	private CSRestService <Item> rsItem;
	
	String urlItemSearch;

	private final String addEditControllerName = "Items_ItemAddEditController";

	public Items_ItemSelectAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.client = (Client) controllerParam;
		
		if (mode == 0 || mode == 1) { // standardno koriscenje PER CLIENT
			 urlItem = "/item/allByClientIdPageable/0/"; // all items, regardless of whether we have it in stock or not
			 urlItemSearch = "/item/allPageableByClientIdAndNameWithPageSize/";
		} else if (mode == 2) { // ovde koristimo formu gde nam trebaju SVI ITEMI, nezavisno od CLIENT ID-a
			urlItem = "/item/allPageable/0/";
			urlItemSearch = "/item/allPageableByIdOrCodeOrNameWithPageSize/";
		}
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		//ITEM
		rsItem = new CSRestService<>(urlItem);
		itemPageId = new AtomicInteger(0);
		
		tblItem.setAddEditDialog(addEditControllerName);

		if (mode == 0 || mode == 1) {
			rsItem.setUrl(urlItem + this.client.getId().toString());
		} else if (mode == 2) {
			rsItem.setUrl(urlItem);
		}
		
		rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
		tblItem.setItems(rsItem.getDataAsObservableList());

		tblItem.onDataNeeded(() -> {
			rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
			tblItem.addItems(rsItem.getDataAsObservableList());
		});
		
		tblItem.onRowDoubleClick( _ -> {
			this.setReturnValue(tblItem.getSelectedItem());
			((Stage) ((Node) tblItem).getScene().getWindow()).close();
		});
		
		tblItem.onServerSearch( () -> {
			String searchPhrase = tblItem.tfSearchBox.getText();
			String urlItemBarcode = null;
			
			if (mode == 0 || mode == 1) {
			    urlItemBarcode = searchPhrase.length() > 0 
					? urlItemSearch + pageSize + "/" + client.getId().toString() + "/" + tblItem.tfSearchBox.getText()
					: this.urlItem + "/" + client.getId().toString();
			} else if (mode == 2) {
			    urlItemBarcode = searchPhrase.length() > 0 
					? urlItemSearch + pageSize + "/" + tblItem.tfSearchBox.getText()
					: this.urlItem;
			}
			    
			if (client != null) {
				itemPageId.set(0);
				rsItem.setUrl(urlItemBarcode);
				rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
				tblItem.setItems(rsItem.getDataAsObservableList());
			}
		});
		
		dialogButtons.getSaveButton().setOnAction( e-> {
			this.setReturnValue(tblItem.getSelectedItem());
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});
		
	} //initialize END
	
}
