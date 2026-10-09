package org.code_studio.controller;

import javafx.fxml.FXML;

import org.code_studio.database.ClientCategory;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.code_studio.component.CSTable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;


public class Lookup_ClientCategoryController extends BaseController {

	@FXML private CSTable <ClientCategory> tblClientCategory;
	CSRestService<ClientCategory> rsClientCategory;
	private final String urlClientCategory = "/clientCategory";
	private final String addEditControllerName = "Lookup_ClientCategoryAddEditController";

	public Lookup_ClientCategoryController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	public void initialize() {
		try {
			rsClientCategory = new CSRestService<>(urlClientCategory);
			rsClientCategory.fetch(new ParameterizedTypeReference<JsonResponse<ClientCategory>>() {});
			tblClientCategory.setItems(rsClientCategory.getDataAsObservableList());
			
			tblClientCategory.setAddEditDialog(addEditControllerName);
			tblClientCategory.setRestServiceDelete(rsClientCategory);
	
			tblClientCategory.onRowDoubleClick(( _ ) -> {
				tblClientCategory.showDoubleClickDefaultAction = true;
			});
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END
}
