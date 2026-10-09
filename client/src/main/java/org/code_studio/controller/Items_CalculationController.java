package org.code_studio.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSTable;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.database.Calculation;
import org.code_studio.database.Client;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Items_CalculationController extends BaseController implements Initializable {

	@FXML private CSClientTable tblClient;
	@FXML private CSTable <Calculation> tblCalculation;
	@FXML private Button btnSetActiveCalculation;
	@FXML private Button btnAddCalculationToAllClients; // sakriveno sa UI do daljnjeg po dogovoru sa B.
	@FXML private Button btnCalculatePerSupplier;
	
	private final String urlCalculation = "/calculation/allByClientId/";
	private CSRestService<Calculation> rsCalculation;
	
	private final String urlCalculationUpdateDelete = "/calculation";
	private CSRestService<Calculation> rsCalculationUpdateDelete;
	private Calculation calculation;
	
	private final String urlCalculationSetActive = "/calculation/setIsActiveByCalculationId/";
	private CSRestService<Calculation> rsCalculationSetActive;
	
	
	public Items_CalculationController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		try {
			rsCalculation = new CSRestService<Calculation>(urlCalculation);
			rsCalculationUpdateDelete = new CSRestService<Calculation>(urlCalculationUpdateDelete);
			rsCalculationSetActive = new CSRestService<Calculation>(urlCalculationSetActive);
			tblClient.refresh();
			
			List<Object> lstControllertParams = new ArrayList<>();
			lstControllertParams.add(tblClient);
			lstControllertParams.add(tblCalculation);
			tblCalculation.setAddEditDialog("Items_CalculationAddEditController", lstControllertParams);
			tblCalculation.setRestServiceDelete(rsCalculationUpdateDelete);
			tblCalculation.onRowDoubleClick(( _ )->{
				tblCalculation.showDoubleClickDefaultAction = true;
			});
			
			tblClient.csTable.onRowSelectionChanged((_, _) -> {
				Client client = tblClient.csTable.getSelectedItem();
				
				if (client != null) {
					rsCalculation.setUrl(urlCalculation + tblClient.csTable.getSelectedItem().getId().toString());
					rsCalculation.fetch(new ParameterizedTypeReference<JsonResponse<Calculation>>() {});
					tblCalculation.setItems(rsCalculation.getDataAsObservableList());
				} else {
					tblCalculation.clear();
				}
			});
			
			tblCalculation.onRowSelectionChanged((_, newRow) -> {
				calculation = (Calculation) newRow;
				if (calculation != null) {
					btnAddCalculationToAllClients.setDisable(false);
					
					if (!calculation.getIsActive()) {
						btnSetActiveCalculation.setDisable(false);
						btnCalculatePerSupplier.setDisable(true);
					} else {
						btnSetActiveCalculation.setDisable(true);
						btnCalculatePerSupplier.setDisable(false);
					}
				} else {
					btnSetActiveCalculation.setDisable(true);
					btnAddCalculationToAllClients.setDisable(true);
					btnCalculatePerSupplier.setDisable(true);
				}
			});
			
			btnAddCalculationToAllClients.setOnAction( _ -> {
				throw new UnsupportedOperationException("Akcija nije implementirana!");
			});
			
			btnSetActiveCalculation.setOnAction( _ -> {
				int selectedIndex = tblCalculation.tableView.getSelectionModel().getSelectedIndex();
				if (calculation != null) {
					rsCalculationSetActive.execute(calculation.getId().toString());
					rsCalculation.fetch(new ParameterizedTypeReference<JsonResponse<Calculation>>() {});
					tblCalculation.setItems(rsCalculation.getDataAsObservableList());
					tblCalculation.tableView.getSelectionModel().select(selectedIndex);
				}
			});
			
			btnCalculatePerSupplier.setOnAction( _ -> {
				throw new UnsupportedOperationException("Akcija nije implementirana!");
			});
	
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške pri pozivanju forme.\n" + ex.getLocalizedMessage(), true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
}
