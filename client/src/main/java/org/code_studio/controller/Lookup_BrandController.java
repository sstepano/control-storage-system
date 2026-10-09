package org.code_studio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.Brand;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;

public class Lookup_BrandController extends BaseController {

	@FXML private CSTable<Brand> tblMain;
	CSRestService<Brand> rsMain;
	AtomicInteger pageId;
	
	private String urlMain = "/brand";
	private final String addEditControllerName = "Lookup_BrandAddEditController";
	private List<Object> lstControllerParam;
	

	public Lookup_BrandController(Object controllerParam, int mode, ApplicationContext ctx) {}
	

	public void initialize() {
		rsMain = new CSRestService<>(urlMain);
		rsMain.setParentTable(tblMain);
		rsMain.fetch(new ParameterizedTypeReference<JsonResponse<Brand>>(){}, ()->{
			tblMain.setItems(rsMain.getDataAsObservableList());
		});
		
		lstControllerParam = new ArrayList<Object>();
		lstControllerParam.add(tblMain);
		//add url
		//add whatever needed

		tblMain.btnAdd.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController(addEditControllerName, lstControllerParam, 0), tblMain, null);
		});

		tblMain.btnEdit.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController(addEditControllerName, lstControllerParam, 1), tblMain, null);
		});

		tblMain.onRowDoubleClick(( _ ) -> {
			tblMain.showDoubleClickDefaultAction = true;
		});
	}
}
