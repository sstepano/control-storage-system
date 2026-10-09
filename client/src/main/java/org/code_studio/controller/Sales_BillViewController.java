package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.database.SaleType;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class Sales_BillViewController extends BaseController implements Initializable {
	
	@FXML private CSClientTable tblClient;
	@FXML private CSComboBox<SaleType> cbSaleType;
	
	private final String urlSaleType = "/saleType";
	private CSRestService<SaleType> rsSaleType;
	
	
	public Sales_BillViewController(Object controllerParam, int mode, ApplicationContext ctx) {}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		rsSaleType = new CSRestService<SaleType>(urlSaleType);
		rsSaleType.fetch(new ParameterizedTypeReference<JsonResponse<SaleType>>() {});
		cbSaleType.setItems(rsSaleType.getDataAsObservableList());
		
		//tblClient.csTable
	
	}
	
}
