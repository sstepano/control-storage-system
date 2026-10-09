package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.database.ItemBranch;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Items_ItemBranchController extends BaseController implements Initializable {

	@FXML private CSTable <ItemBranch> mainTable;
	CSRestService<ItemBranch> mainRestService;
	AtomicInteger pageId;
	private final String defaultUrl = "/itemBranch/allPageable/";

	public Items_ItemBranchController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		pageId = new AtomicInteger(0);
		mainRestService = new CSRestService<>(defaultUrl);
		
		mainRestService.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<ItemBranch>>() {});
		mainTable.setItems(mainRestService.getDataAsObservableList());

		mainTable.onDataNeeded(() -> {
			mainRestService.fetch(pageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<ItemBranch>>() {});
			mainTable.addItems(mainRestService.getDataAsObservableList());			
		});
		
		mainTable.onRowDoubleClick(( _ ) -> {
			mainTable.showDoubleClickDefaultAction = true;
		});
		
	} //initialize END
	
}
