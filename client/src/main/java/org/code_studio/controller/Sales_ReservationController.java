package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Sales_ReservationController extends BaseController implements Initializable {
	
	@FXML private CSTable <?> tblReservation;
	@FXML private CSTable <?> tblReservationDetail;

	private final String addEditControllerName = "Sales_ReservationAddEditController";
	private final String addEditDetailControllerName = "Sales_ReservationDetailAddEditController";
	private Object selectedAddEditTableItem = null;

	public Sales_ReservationController(Object controllerParam, int mode, ApplicationContext ctx) {}
	public Sales_ReservationController(Object selectedItem) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
	
		tblReservation.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(addEditControllerName, null, 0), tblReservation, null);
		});
	
		tblReservation.btnEdit.setOnAction(e->{
			selectedAddEditTableItem = tblReservation.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditControllerName, selectedAddEditTableItem, 1), tblReservation, "tblReservation.getSelectedItem().getName()");
		});
		
		tblReservationDetail.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(addEditDetailControllerName, null, 0), tblReservationDetail, null);
		});
	
		tblReservationDetail.btnEdit.setOnAction(e->{
			selectedAddEditTableItem = tblReservationDetail.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditDetailControllerName, selectedAddEditTableItem, 1), tblReservationDetail, "tblReservationDetail.getSelectedItem().getName()");
		});		

	}
}
