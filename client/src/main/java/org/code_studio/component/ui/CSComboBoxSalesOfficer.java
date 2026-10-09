package org.code_studio.component.ui;

import org.code_studio.component.CSComboBox;
import org.code_studio.database.ApplicationUser;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

public class CSComboBoxSalesOfficer extends CSComboBox <ApplicationUser> {
	
	private CSRestService<ApplicationUser> rsService;
	private final String urlServiceUrl = "/applicationUser/allSaleOfficers";
	private final String factoryMethodName = "Name";
	
	/**
	 * Component is used for creating specific CSComboBox to show APPLICATION USER (SALES OFFICER) POJO
	 */
	public CSComboBoxSalesOfficer() {
		setInitFactory(true);
		setFactoryMethodName(factoryMethodName);
		rsService = new CSRestService<>(urlServiceUrl);
		rsService.fetch(new ParameterizedTypeReference<JsonResponse<ApplicationUser>>() {});
		setItemsAndSelectFirstItem(rsService.getDataAsObservableList());
	}

}
