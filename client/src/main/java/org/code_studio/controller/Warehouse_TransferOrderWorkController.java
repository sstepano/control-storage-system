package org.code_studio.controller;

import java.time.LocalDateTime;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.TransferOrder;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;

public class Warehouse_TransferOrderWorkController extends BaseController {

	@FXML CSDialogButtons dialogButtons;
	@FXML CSTextField tfWorkerName;
	
	private int mode;
	private CSTable<TransferOrder> tblTransferOrder;
	private TransferOrder to;
	private CSRestService<TransferOrder> rsTransferOrder;
	private final String urlTransferOrder = "/transferOrder/";
	
	@SuppressWarnings("unchecked")
	public Warehouse_TransferOrderWorkController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		tblTransferOrder = (CSTable<TransferOrder>) controllerParam;
		to = tblTransferOrder.getSelectedItem();
	}

	public void initialize() {
		
		rsTransferOrder = new CSRestService<>(urlTransferOrder);
		dialogButtons.setValidation(new CSEmptyFieldValidator(tfWorkerName));
		
		dialogButtons.getSaveButton().setOnAction( e -> {
			if (to != null) {
				rsTransferOrder.setUrl(urlTransferOrder + to.getId());
				switch (mode) {
				    case 0: // Uzeto u rad
						to.setStatusId(2); // Obrada u toku				    	
					break;
				    case 1: // Isporuceno
						//TODO: to.setDeliveredByUserId(tfw???);
						to.setDeliveredDate(LocalDateTime.now());
						to.setStatusId(4); // Isporuceno/Uradjeno
					break;
				}

				rsTransferOrder.addOrUpdate(to);
				tblTransferOrder.refresh();
				
			}
			dialogButtons.closeForm();
		});
		
	} // INITIALIZE END
	
} // CLASS END
