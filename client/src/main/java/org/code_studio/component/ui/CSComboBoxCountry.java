package org.code_studio.component.ui;

import org.code_studio.component.CSComboBox;
import org.code_studio.database.Country;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

public class CSComboBoxCountry extends CSComboBox <Country> {
	CSRestService<Country> rsCountry;

	/**
	 * Component is used for creating specific CSComboBox to show COUNTRY POJO
	 */
	public CSComboBoxCountry() {
		setInitFactory(true);
		setFactoryMethodName("Name");
		rsCountry = new CSRestService<>("/country");
		rsCountry.fetch(new ParameterizedTypeReference<JsonResponse<Country>>() {});
		setItemsAndSelectFirstItem(rsCountry.getDataAsObservableList());
	}

}
