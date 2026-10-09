package org.code_studio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.database.Client;
import org.code_studio.database.ItemBarcode;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;

public class Items_ItemBarcodeController extends BaseController {

	@FXML private CSClientTable tblSupplier;
	@FXML private CSTable <ItemBarcode> tblItemBarcode;

	private Client supplier;
	
	final int pageSize = 50;
	final String urlItemBarcode = "/itemBarcode/allPageableByClientId/";
	final String urlItemBarcodeSearch = "/itemBarcode/allPageableByBarcode/";
	private AtomicInteger itemBarcodePageId;
	private CSRestService <ItemBarcode> rsItemBarcode;
	
	private final String urlDeleteItemBarcode = "/itemBarcode";
	private CSRestService<ItemBarcode> rsDeleteItemBarcode;
	
	private final String addEditControllerName = "Items_ItemBarcodeAddEditController";

	public Items_ItemBarcodeController(Object controllerParam, int mode, ApplicationContext ctx) {}
	

	public void initialize() {
		try {
			tblSupplier.refresh();
			rsItemBarcode = new CSRestService<>(urlItemBarcode);
			itemBarcodePageId = new AtomicInteger(0);
			rsDeleteItemBarcode = new CSRestService<ItemBarcode>(urlDeleteItemBarcode);
			tblItemBarcode.setRestServiceDelete(rsDeleteItemBarcode);
	
			tblSupplier.csTable.onRowSelectionChanged((_, newRow) -> {
				supplier = (Client) newRow;
				if (supplier != null) {
					itemBarcodePageId.set(0);
					rsItemBarcode.setUrl(urlItemBarcode + supplier.getId().toString());
					rsItemBarcode.fetch(itemBarcodePageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemBarcode>>() {});
					tblItemBarcode.setItems(rsItemBarcode.getDataAsObservableList());
				}
			});
			
			//ITEMBARCODE
			List<Object> lstControllerParams = new ArrayList<>();
			lstControllerParams.add(tblItemBarcode);
			lstControllerParams.add(tblSupplier.csTable);
			tblItemBarcode.setAddEditDialog(addEditControllerName, lstControllerParams);
	
			tblItemBarcode.onDataNeeded(() -> {
				rsItemBarcode.fetch(itemBarcodePageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemBarcode>>() {});
				tblItemBarcode.addItems(rsItemBarcode.getDataAsObservableList());
			});
			
			tblItemBarcode.onRowDoubleClick( _ -> {
				tblItemBarcode.showDoubleClickDefaultAction = true;
			});
			
			tblItemBarcode.onServerSearch( () -> {
				String searchPhrase = tblItemBarcode.tfSearchBox.getText();
				String urlItemBarcode = searchPhrase.length() > 0 
						? urlItemBarcodeSearch + tblItemBarcode.tfSearchBox.getText()
						: this.urlItemBarcode + supplier.getId().toString();
				
				if (supplier != null) {
					itemBarcodePageId.set(0);
					rsItemBarcode.setUrl(urlItemBarcode);
					rsItemBarcode.fetch(itemBarcodePageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemBarcode>>() {});
					tblItemBarcode.setItems(rsItemBarcode.getDataAsObservableList());
				}
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
}
