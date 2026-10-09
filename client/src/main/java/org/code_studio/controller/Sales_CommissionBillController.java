package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Sales_CommissionBillController extends BaseController implements Initializable {
	
	@FXML private CSTable <?> tblCommissionBill;
	@FXML private CSTable <?> tblCommissionBillDetail;

	private final String addEditControllerName = "Sales_CommissionBillAddEditController";
	private final String addEditDetailControllerName = "Sales_CommissionBillDetailAddEditController";
	private Object selectedAddEditTableItem = null;

	public Sales_CommissionBillController(Object controllerParam, int mode, ApplicationContext ctx) {}
	public Sales_CommissionBillController(Object selectedItem) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
	
		tblCommissionBill.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(addEditControllerName, null, 0), tblCommissionBill, null);
		});
	
		tblCommissionBill.btnEdit.setOnAction(e->{
			selectedAddEditTableItem = tblCommissionBill.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditControllerName, selectedAddEditTableItem, 1), tblCommissionBill, "tblCommissionBill.getSelectedItem().getName()");
		});
		
		tblCommissionBillDetail.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(addEditDetailControllerName, null, 0), tblCommissionBillDetail, null);
		});
	
		tblCommissionBillDetail.btnEdit.setOnAction(e->{
			selectedAddEditTableItem = tblCommissionBillDetail.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditDetailControllerName, selectedAddEditTableItem, 1), tblCommissionBillDetail, "tblCommissionBillDetail.getSelectedItem().getName()");
		});		

	}
}
