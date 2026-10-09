package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.Warehouse;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Warehouse_WarehouseListController extends BaseController implements Initializable {

	@FXML private CSTable <Warehouse> mainTable;
	@FXML private ToggleGroup tgpActiveInactive;
	@FXML private RadioButton rbAll;
	@FXML private RadioButton rbactive;
	@FXML private RadioButton rbInactive;
	@FXML private ToggleGroup tgpWarehouseType;
	@FXML private RadioButton rbAllTypes;
	@FXML private RadioButton rbRegular;
	@FXML private RadioButton rbCommission;
	@FXML private CSDialogButtons dialogButtons;
	@FXML private HBox hbActiveInactive;
	@FXML private HBox hbWarehouseType;
	
	CSRestService<Warehouse> mainRestService;
	private final String defaultUrl = "/warehouse/allByIsActiveAndIsCommission/1/2";
	
	CSRestService<Warehouse> svcDelete;
	private final String urlDelete = "/warehouse";
	private final String addEditControllerName = "Warehouse_WarehouseAddEditController"; 
	private int mode;

	public Warehouse_WarehouseListController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		mainTable.setAddEditDialog(addEditControllerName);
		mainRestService = new CSRestService<>(defaultUrl);
		mainRestService.setParentTable(mainTable);
		mainRestService.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>(){}, () -> {
			mainTable.setItems(mainRestService.getDataAsObservableList());
		});
		svcDelete = new CSRestService<>(urlDelete);
		mainTable.setRestServiceDelete(svcDelete);
		
		// mainTable.onDataNeeded ovde nije potreban jer fetchujemo SVE rezultate odmah. Mislim da nigde vise to ne radimo, vec uzimamo pageovane podatke.
		
		mainTable.onRowDoubleClick((rowData) -> {
			mainTable.showDoubleClickDefaultAction = true;
		});
		
		tgpActiveInactive.selectedToggleProperty().addListener((observable, unselectedToggle, selectedToggle) -> {
			mainRestService.setUrl(getFetchUrl());
			mainTable.clear();
			mainRestService.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>(){}, () -> {
				mainTable.setItems(mainRestService.getDataAsObservableList());
			});
		});
		
		tgpWarehouseType.selectedToggleProperty().addListener((observable, unselectedToggle, selectedToggle) -> {
			mainRestService.setUrl(getFetchUrl());
			mainTable.clear();
			mainRestService.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>(){}, () -> {
				mainTable.setItems(mainRestService.getDataAsObservableList());
			});
		});
		
		// ako je mode SELECT
		if (mode == 2) {
			hbActiveInactive.setVisible(false);
			hbWarehouseType.setVisible(false);
			hbActiveInactive.setManaged(false);
			hbWarehouseType.setManaged(false);
			dialogButtons.getCancelButton().setVisible(false);
			dialogButtons.getCancelButton().setManaged(false);
			dialogButtons.getSaveButton().setText("Odaberi");
			dialogButtons.getSaveButton().setOnAction(e -> {
				this.setReturnValue(mainTable.getSelectedItem());
				((Stage) ((Node) mainTable).getScene().getWindow()).close();				
			});
			
			mainTable.onRowDoubleClick( e-> {
				this.setReturnValue(mainTable.getSelectedItem());
				((Stage) ((Node) mainTable).getScene().getWindow()).close();
			});
		}
	}
	
	/**
	 * 
	 * @return url for fetch service
	 */
	private String getFetchUrl() {
		String res = "/warehouse/allByIsActiveAndIsCommission/";
		int activeInactiveSelectedIndex = tgpActiveInactive.getToggles().indexOf(tgpActiveInactive.getSelectedToggle());
		int warehouseTypeSelectedIndex = tgpWarehouseType.getToggles().indexOf(tgpWarehouseType.getSelectedToggle());
		res = res + activeInactiveSelectedIndex + "/" + warehouseTypeSelectedIndex;
		return res;
	}
}
