package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class SelectionByItemsController extends BaseController implements Initializable {

	@FXML private CSTable <ObservableList<String>> mainTable;

	public SelectionByItemsController() {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {}
	
}
