package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSPhotoView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.component.ui.CSItemTable;
import org.code_studio.database.Client;
import org.code_studio.database.ClientPricelist;
import org.code_studio.database.Item;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Items_ComparativePricelistPerClientController extends BaseController implements Initializable {

	@FXML private CSClientTable tblClient;
	@FXML private CSItemTable tblItem;
	@FXML private CSTable <ClientPricelist> tblClientPricelist;
	@FXML private CSPhotoView phtItemImage;
	@FXML private CSTextField tfNetPriceDFak;
	@FXML private CSTextField tfNetPriceDFakSpec;
	
	@SuppressWarnings("unused")
	private int mode;
	
	private final String urlClientPricelist = "/clientPricelist/allPageableByItemId/";
	private CSRestService <ClientPricelist> rsClientPricelist;
	private AtomicInteger clientPricelistPageId;

	public Items_ComparativePricelistPerClientController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		clientPricelistPageId = new AtomicInteger(0);
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		rsClientPricelist = new CSRestService<>(urlClientPricelist);
		tblClient.refresh();
		
		tblClient.csTable.onRowSelectionChanged( (_, newRow) -> {
			Client client = (Client) newRow;
			if (client != null) {
				tblItem.setClientId(client.getId().intValue());
				tblItem.refresh();
			}
		});
		
		tblItem.csTable.onRowSelectionChanged( (_, newRow) -> {
			Item item = (Item) newRow;
			if (item != null) {
				clientPricelistPageId.set(0);
				rsClientPricelist.setUrl(urlClientPricelist + item.getId().toString());
				rsClientPricelist.fetch(clientPricelistPageId.get(), new ParameterizedTypeReference<JsonResponse<ClientPricelist>>() {});
				tblClientPricelist.setItems(rsClientPricelist.getDataAsObservableList());
				phtItemImage.setImagePath(item.getImagePath());
				
				tfNetPriceDFak.setText(
						item.getNetPriceDFak() == null ? "0.00" :
						item.getNetPriceDFak().toString());

				tfNetPriceDFakSpec.setText(
						item.getNetPriceDFakSpec() == null ? "0.00" :
						item.getNetPriceDFakSpec().toString());
			} else {
				phtItemImage.loadNoImage();
			}
		});

	}
}
