package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Sales_WholesaleReturnController extends BaseController implements Initializable {
	
	@FXML private CSTable<?> tblWholesaleReturn;
	@FXML private CSTable<?> tblWholesaleReturnDetail;

	private final String tblWholesaleReturnAddEditControllerName = "Sales_WholesaleReturnAddEditController";
	private final String tblWholesaleReturnDetailAddEditControllerName = "Sales_WholesaleReturnDetailAddEditController";

	public Sales_WholesaleReturnController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		tblWholesaleReturn.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(tblWholesaleReturnAddEditControllerName, null, 0), tblWholesaleReturn, "VP POVRATNICA");
		});

		tblWholesaleReturn.btnEdit.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(tblWholesaleReturnAddEditControllerName, tblWholesaleReturn.getSelectedItem(), 1), tblWholesaleReturn, "VP POVRATNICA");
		});

		tblWholesaleReturnDetail.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(tblWholesaleReturnDetailAddEditControllerName, null, 0), tblWholesaleReturnDetail, "VP POVRATNICA - DETALJI");
		});

		tblWholesaleReturnDetail.btnEdit.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(tblWholesaleReturnDetailAddEditControllerName, tblWholesaleReturnDetail.getSelectedItem(), 1), tblWholesaleReturnDetail, "VP POVRATNICA - DETALJI");
		});

	}
	
}
