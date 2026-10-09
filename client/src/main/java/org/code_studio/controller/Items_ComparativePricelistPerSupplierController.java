package org.code_studio.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.component.ui.CSItemTable;
import org.code_studio.database.Client;
import org.code_studio.database.Item;
import org.code_studio.database.SupplierPricelist;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Items_ComparativePricelistPerSupplierController extends BaseController {
	@FXML private CSClientTable tblClient;
	@FXML private CSItemTable   tblItemAll;
	@FXML private CSTable<SupplierPricelist> tblSupplierPricelist;
	
	@SuppressWarnings("unused")
	private int mode;
	
	private final String urlSupplierPricelistByClientId = "/supplierPricelist/allPageableByClientId/";
	private final String urlSupplierPricelistByItemId = "/supplierPricelist/allPageableByItemId/";
	private final String urlSupplierPricelistDelete = "/supplierPricelist";
	private CSRestService <SupplierPricelist> rsSupplierPricelist;
	private AtomicInteger supplierPricelistPageId;
	private CSRestService <SupplierPricelist> rsSupplierPricelistDelete;

	public Items_ComparativePricelistPerSupplierController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		supplierPricelistPageId = new AtomicInteger(0);
	}
	
	public void initialize() {
		try {
			rsSupplierPricelist = new CSRestService<>(urlSupplierPricelistByClientId);
			rsSupplierPricelistDelete = new CSRestService<>(urlSupplierPricelistDelete);
			tblItemAll.refresh();

			//ITEM-ALL TABLE
			tblItemAll.csTable.onRowSelectionChanged((_, newRow) -> {
				if (tblItemAll.csTable.tableView.isFocused()) {
					if (newRow != null) {
						Item item = (Item) newRow;
						tblSupplierPricelist.setItems(getItemData(null, item.getId().intValue()));
					} else {
						tblSupplierPricelist.clear();
					}
				}
			});
			
			tblItemAll.csTable.onFocusChanged((_, newVal) -> {
				if (newVal) {
					tblItemAll.csTable.topLabel.setStyle("-fx-text-fill: RED");
					tblClient.csTable.topLabel.setStyle("-fx-text-fill: -fx-text-base-color");
					
					/* Ovaj deo fixuje to sto se u initialize tblItemAll.onRowChanged okida UVEK nakon
					 * tblClient.onRowChanged, i tako osvezava desnu tabelu kasnije, umesto da tblClient to radi
					 * Ultimativno bi trebalo optimizovati tako da se npr generise onRendered event u
					 * CSTable, i onda da imamo neki metod onAfterRender gde cemo biti sigurni da
					 * ce se samo tada i to jednom izvrsiti kod umesto ovako dva puta.
					 */
					Item item = tblItemAll.csTable.getSelectedItem();
					if (item != null) {
						tblSupplierPricelist.setItems(getItemData(null, item.getId().intValue()));
					} else {
						tblSupplierPricelist.clear();
					}
				} else {
					tblItemAll.csTable.topLabel.setStyle("-fx-text-fill: -fx-text-base-color");
				}
			});
			
			// CLIENT TABLE
			//TODO: optimizovati. Ovde ulazi u oba metoda onRowSelectionChanged kada se inicijalizuje forma
			tblClient.csTable.onRowSelectionChanged((_, newRow) -> {
				if (newRow != null) {
					Client client = (Client) newRow;
					tblSupplierPricelist.setItems(getItemData(client.getId().intValue(), null));
				} else {
					tblSupplierPricelist.clear();
				}
			});
			
			tblClient.csTable.onFocusChanged((_, newVal) -> {
				if (newVal) {
					tblClient.csTable.topLabel.setStyle("-fx-text-fill: RED");
					tblItemAll.csTable.topLabel.setStyle("-fx-text-fill: -fx-text-base-color");
					
					Client client = tblClient.csTable.getSelectedItem();
					if (client != null) {
						tblSupplierPricelist.setItems(getItemData(client.getId().intValue(), null));
					} else {
						tblSupplierPricelist.clear();
					}
				} else {
					tblClient.csTable.topLabel.setStyle("-fx-text-fill: -fx-text-base-color");
				}
			});
			tblClient.refresh();
			tblClient.csTable.topLabel.setStyle("-fx-text-fill: RED");
			
			//SUPPLIER PRICELIST
			tblSupplierPricelist.setParentTable(tblClient.csTable);
			tblSupplierPricelist.setRestServiceDelete(rsSupplierPricelistDelete);
			tblSupplierPricelist.setAddEditDialog("Items_ComparativePricelistPerSupplierAddEditController");
			tblSupplierPricelist.onRowDoubleClick( _ -> {
				tblSupplierPricelist.showDoubleClickDefaultAction = true;	
			});
			
			tblSupplierPricelist.onDataNeeded(() -> {
				rsSupplierPricelist.fetch(supplierPricelistPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<SupplierPricelist>>() {});
				tblSupplierPricelist.addItems(rsSupplierPricelist.getDataAsObservableList());
			});

			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
	private ObservableList<SupplierPricelist> getItemData(Integer clientId, Integer itemId) {
		supplierPricelistPageId.set(0);
		String urlDataFetch;
		
		if (clientId != null) {
			urlDataFetch = urlSupplierPricelistByClientId + clientId;
		} else {
			urlDataFetch = urlSupplierPricelistByItemId + itemId;
		}
		
		rsSupplierPricelist.setUrl(urlDataFetch);
		rsSupplierPricelist.fetch(supplierPricelistPageId.get(), new ParameterizedTypeReference<JsonResponse<SupplierPricelist>>() {});
		return rsSupplierPricelist.getDataAsObservableList();
	}
}
