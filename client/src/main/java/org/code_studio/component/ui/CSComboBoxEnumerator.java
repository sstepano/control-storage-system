package org.code_studio.component.ui;

import org.code_studio.component.CSComboBox;
import org.code_studio.database.ApplicationUser;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

public class CSComboBoxEnumerator extends CSComboBox <ApplicationUser> {
	
	private CSRestService<ApplicationUser> rsService;
	private final String urlServiceUrl0 = "/applicationUser/allEnumerators";
	private final String urlServiceUrl1 = "/applicationUser/allEnumeratorsWithoutALL";
	private final String factoryMethodName = "Name";
	
	private int mode = 0;
	
	/**
	 * Component is used for creating specific CSComboBox to show APPLICATION USER (SALES OFFICER) POJO
	 */
	public CSComboBoxEnumerator() {
		setInitFactory(true);
		setFactoryMethodName(factoryMethodName);
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
		rsService = new CSRestService<>(mode == 0 ? urlServiceUrl0 : urlServiceUrl1);
		rsService.fetch(new ParameterizedTypeReference<JsonResponse<ApplicationUser>>() {});
		setItemsAndSelectFirstItem(rsService.getDataAsObservableList());
	}

}
