package org.code_studio.controller;

import java.util.ArrayList;
import java.util.List;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.component.ui.CSItemTable;
import org.code_studio.database.Client;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;

public class Items_ItemController extends BaseController {

	@FXML private CSClientTable tblSupplier;
	@FXML private CSItemTable tblItem;

	private Client supplier;
	
	final int pageSize = 50;

	private final String addEditControllerName = "Items_ItemAddEditController";

	public Items_ItemController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	public void initialize() {
		try {
			//SUPPLIER
			tblSupplier.refresh();
			
			//rsItem = new CSRestService<>(urlItem);
			//itemPageId = new AtomicInteger(0);
			
			tblSupplier.csTable.onRowSelectionChanged((_, newRow) -> {
				supplier = (Client) newRow;
				if (supplier != null) {
					/*
					itemPageId.set(0);
					rsItem.setUrl(urlItem + supplier.getId().toString());
					rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
					tblItem.setItems(rsItem.getDataAsObservableList());
					*/
					tblItem.setClientId(supplier.getId().intValue());
					tblItem.refresh();
				}
			});
			
			/* TODO: JOS NISAM IMPLEMENTIRAO DEFAULT AKCIJU
			tblSupplier.onRowDoubleClick((rowData) -> {
				tblSupplier.showDoubleClickDefaultAction = true;
			});
			*/
			//----------------------------------

			List<Object> lstControllerParams = new ArrayList<>();
			lstControllerParams.add(tblItem.csTable);
			lstControllerParams.add(tblSupplier);
			tblItem.csTable.setAddEditDialog(addEditControllerName, lstControllerParams);
			
			/*
			tblItem.onDataNeeded(() -> {
				rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
				tblItem.addItems(rsItem.getDataAsObservableList());
			});
			
			tblItem.onRowDoubleClick( e-> {
				tblItem.showDoubleClickDefaultAction = true;
			});
			
			tblItem.onServerSearch( () -> {
				String searchPhrase = tblItem.tfSearchBox.getText();
				String urlItem = searchPhrase.length() > 0 
						? urlItemSearch + supplier.getId().toString() + "/" + tblItem.tfSearchBox.getText()
						: this.urlItem + supplier.getId().toString();
				
				if (supplier != null) {
					itemPageId.set(0);
					rsItem.setUrl(urlItem);
					rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>() {});
					tblItem.setItems(rsItem.getDataAsObservableList());
				}
			});
			*/
			
			} catch (Exception ex) {
				Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
				Common.logMessage(getClass(), ex, "ERROR");
			}
	} // initialize END
	
}
