package org.code_studio.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSPhotoView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.Client;
import org.code_studio.database.ClientStoreImage;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Sales_ClientStoreImageController extends BaseController implements Initializable {
	
	@FXML private CSTextField tfClientId;
	@FXML private CSTextField tfClientName;
	@FXML private CSTable <ClientStoreImage> tblClientStoreImage;
	@FXML private CSPhotoView phvStoreImage;
	
	@SuppressWarnings("unused")
	private int mode;
	CSTable<Client> tblClient;
	private Client client;

	private final String addEditControllerName = "Sales_ClientStoreImageAddEditController";
	
	private CSRestService<ClientStoreImage> rsClientStoreImage;
	private final String urlClientStoreImage = "/clientStoreImage/allByClientId/";

	@SuppressWarnings("unchecked")
	public Sales_ClientStoreImageController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		tblClient = (CSTable<Client>) controllerParam;
		client = tblClient.getSelectedItem();
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		phvStoreImage.setMaxAspectRatio();
		
		tfClientId.setText(client.getId().toString());
		tfClientName.setText(client.getName());
		
		List<Object> lstControllerParam = new ArrayList<>();
		lstControllerParam.add(tblClient);
		lstControllerParam.add(tblClientStoreImage);
		tblClientStoreImage.setAddEditDialog(addEditControllerName, lstControllerParam);
		rsClientStoreImage = new CSRestService<>(urlClientStoreImage);
		rsClientStoreImage.setUrl(urlClientStoreImage + client.getId());
		rsClientStoreImage.fetch(new ParameterizedTypeReference<JsonResponse<ClientStoreImage>>(){});
		tblClientStoreImage.setItems(rsClientStoreImage.getDataAsObservableList());
		
		tblClientStoreImage.onRowSelectionChanged((oldRow, newRow) -> {
			ClientStoreImage clientStoreImage = (ClientStoreImage) newRow;
			if (clientStoreImage != null) {
				phvStoreImage.setImagePath(clientStoreImage.getImagePath());
			}
		});

	}
}
