package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Sales_CommissionTransferOrderController extends BaseController implements Initializable {
	
	@FXML private CSTable <?> tblCommissionTransferOrder;
	@FXML private CSTable <?> tblCommissionTransferOrderDetail;

	private final String addEditControllerName = "Sales_CommissionTransferOrderAddEditController";
	private final String addEditDetailControllerName = "Sales_CommissionTransferOrderDetailAddEditController";
	private Object selectedAddEditTableItem = null;	

	public Sales_CommissionTransferOrderController(Object controllerParam, int mode, ApplicationContext ctx) {}
	public Sales_CommissionTransferOrderController(Object selectedItem) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		tblCommissionTransferOrder.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(addEditControllerName, null, 0), tblCommissionTransferOrder, null);
		});
	
		tblCommissionTransferOrder.btnEdit.setOnAction(e->{
			selectedAddEditTableItem = tblCommissionTransferOrder.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditControllerName, selectedAddEditTableItem, 1), tblCommissionTransferOrder, "tblCommissionTransferOrder.getSelectedItem().getName()");
		});
		
		tblCommissionTransferOrderDetail.btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(addEditDetailControllerName, null, 0), tblCommissionTransferOrderDetail, null);
		});
	
		tblCommissionTransferOrderDetail.btnEdit.setOnAction(e->{
			selectedAddEditTableItem = tblCommissionTransferOrderDetail.getSelectedItem();
			Common.displayForm(ControllerFactory.getController(addEditDetailControllerName, selectedAddEditTableItem, 1), tblCommissionTransferOrderDetail, "tblCommissionTransferOrderDetail.getSelectedItem().getName()");
		});	
		
	}
	
}
