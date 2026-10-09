package org.code_studio.controller;

import javafx.fxml.FXML;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;

import org.code_studio.component.CSTable;
import org.code_studio.database.DiscountWithEntity;
import org.code_studio.database.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

public class Lookup_DiscountController extends BaseController {

	@FXML private CSTable<DiscountWithEntity> tblDiscount;

	private int mode;
	private Client client;
	
	private final String addEditControllerName = "Items_ItemPerCategoryController"; // We use this form to be consistent. Within this form we use AddEdit.
	
	private final String urlDiscount = "/discountWithEntity";
	private final String urlDiscountPerClientId = "/discountWithEntity/allByClientId/";
	private CSRestService<DiscountWithEntity> rsDiscount;

	// ne dozvoljavamo direktan delete iz tabele, vec postavljamo valid_to na now()
	private final String urlDiscountDelete = "/discount/delete";
	private CSRestService<DiscountWithEntity> rsDiscountDelete;
	
	public Lookup_DiscountController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.client = (Client) controllerParam;
		this.mode = mode;
	}
	
	public void initialize() {
		try {
			if (mode == 3 && client != null) { // ako treba da selektujemo po klijentu
				rsDiscount = new CSRestService<DiscountWithEntity>(urlDiscountPerClientId + client.getId());
			} else {
				rsDiscount = new CSRestService<DiscountWithEntity>(urlDiscount);
			}
			
			tblDiscount.setAddEditDialog(addEditControllerName, client, 3); // discount add/edit mode je 3
			rsDiscount.fetch(new ParameterizedTypeReference<JsonResponse<DiscountWithEntity>>() {});
			tblDiscount.setItems(rsDiscount.getDataAsObservableList());
			rsDiscountDelete = new CSRestService<DiscountWithEntity>(urlDiscountDelete);
			tblDiscount.setRestServiceDelete(rsDiscountDelete);
			
			tblDiscount.onRowDoubleClick((row)-> {
				tblDiscount.showDoubleClickDefaultAction = true;
			});
			
			tblDiscount.onRowSelectionChanged((oldRow, newRow)->{
				this.setReturnValue(newRow);
			});
						
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END

}
