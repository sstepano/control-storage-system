package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.database.ApplicationUser;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Items_ApplicationUserController extends BaseController implements Initializable {

	@FXML private CSTable <ApplicationUser> mainTable;
	CSRestService<ApplicationUser> mainRestService;
	AtomicInteger pageId;
	private final String defaultUrl = "/applicationUser";

	public Items_ApplicationUserController() {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		mainRestService = new CSRestService<>(defaultUrl);
		mainRestService.fetch(new ParameterizedTypeReference<JsonResponse<ApplicationUser>>() {});
		mainTable.setItems(mainRestService.getDataAsObservableList());

		mainTable.onRowDoubleClick(( _ ) -> {
			mainTable.showDoubleClickDefaultAction = true;
		});
	}
}
