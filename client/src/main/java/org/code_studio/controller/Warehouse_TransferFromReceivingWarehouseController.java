package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.springframework.context.ApplicationContext;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Warehouse_TransferFromReceivingWarehouseController extends BaseController implements Initializable {

	@FXML private CSTable <ObservableList<String>> mainTable;

	public Warehouse_TransferFromReceivingWarehouseController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {}
	
}
