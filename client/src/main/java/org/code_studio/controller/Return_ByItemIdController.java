package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Return_ByItemIdController extends BaseController implements Initializable {
	
	@FXML private CSTable<?> tblHeader;
	@FXML private CSTable<?> tblDetail;

	private final String tblHeader_AddEditControllerName = "Return_ByItemId_HeaderAddEditController";
	private final String tblDetail_AddEditControllerName = "Return_ByItemId_DetailAddEditController";
	//private Object selectedItem;

	public Return_ByItemIdController(Object controllerParam, int mode, ApplicationContext ctx) {}
	public Return_ByItemIdController(Object selectedItem) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		tblHeader.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(tblHeader_AddEditControllerName, null, 0), tblHeader, "ZAGLAVLJE");
		});

		tblHeader.btnEdit.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(tblHeader_AddEditControllerName, tblHeader.getSelectedItem(), 1), tblHeader, "ZAGLAVLJE");
		});
		
		tblDetail.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(tblDetail_AddEditControllerName, null, 0), tblDetail, "DETALJI");
		});
		
		tblDetail.btnEdit.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(tblDetail_AddEditControllerName, tblDetail.getSelectedItem(), 1), tblDetail, "DETALJI");
		});

	}
	
}
