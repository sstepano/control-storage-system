package org.code_studio.controller;

import javafx.fxml.FXML;

import org.code_studio.database.ClientGroup;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.code_studio.component.CSTable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;


public class Lookup_ClientGroupController extends BaseController {

	@FXML private CSTable <ClientGroup> tblClientGroup;
	CSRestService<ClientGroup> rsClientGroup;
	private final String urlClientGroup = "/clientGroup";
	private final String addEditControllerName = "Lookup_ClientGroupAddEditController";

	public Lookup_ClientGroupController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	public void initialize() {
		try {
			rsClientGroup = new CSRestService<>(urlClientGroup);
			rsClientGroup.fetch(new ParameterizedTypeReference<JsonResponse<ClientGroup>>() {});
			tblClientGroup.setItems(rsClientGroup.getDataAsObservableList());
			
			tblClientGroup.setAddEditDialog(addEditControllerName);
			tblClientGroup.setRestServiceDelete(rsClientGroup);
	
			tblClientGroup.onRowDoubleClick(( _ ) -> {
				tblClientGroup.showDoubleClickDefaultAction = true;
			});
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END
}
