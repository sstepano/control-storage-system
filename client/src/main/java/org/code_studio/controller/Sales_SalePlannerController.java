package org.code_studio.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.ApplicationUser;
import org.code_studio.database.Client;
import org.code_studio.database.ClientName;
import org.code_studio.database.SalePlanner;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class Sales_SalePlannerController extends BaseController {

	@FXML Button btnSave;
	@FXML Button btnCancel;
	
	@FXML private CSTable <SalePlanner> tblSalePlanner;
	@FXML TextArea taDescription;
	@FXML CSComboBox <ApplicationUser> cbSaleOfficer;
	
	@Deprecated
	@FXML CSComboBox <String> cbFilter;
	
	@FXML ToggleGroup tgpFilter;
	@FXML CSDatePicker dtPlanDate;
	
	@FXML CSTextField tfMonth;
	@FXML CSTextField tfYear;
	@FXML Button btnMonthlyReview;
	@FXML Button btnSaleOfficerMonthlyReview;
	
	//TODO: TEST ONLY, we need to pass logged in userid (unless it is admin)
	//private Integer loggedInSaleOfficerId = 112;
	
	private int mode; // 0 - Kliknut Meni F2, 1 - Klijenti->Rokovnik, 2 - Klijenti->tblSalePlanner->btnSalePlanner
	
	@Deprecated
	private int saleOfficerId = 112; // Zoran Cucukovic
	
	private final String tblSalePlannerAddEditControllerName = "Sales_SalePlannerAddEditController";
	private final String salePlannerUrl = "/salePlanner/";
	private final String salePlannerDeleteUrl = "/salePlanner";
	private AtomicInteger salePlannerPageId = new AtomicInteger(0);
	private CSRestService <SalePlanner> rsSalePlanner;
	private CSRestService <SalePlanner> rsSalePlannerDelete;
	private CSTable<Client> tblClient;
	private ClientName client;
	private String saleOfficerName = ""; 
	
	/*
	private final String saleOfficersUrl = "/applicationUser/allSaleOfficers";
	private BaseRestService <ApplicationUser> rsSaleOfficers;
	*/
	
	@SuppressWarnings("unchecked")
	public Sales_SalePlannerController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		tblClient = (CSTable<Client>) controllerParam;
		client = tblClient != null 
		? new ClientName(tblClient.getSelectedItem().getId().intValue(), tblClient.getSelectedItem().getName()) 
		: null;
	}

	public void initialize() {
		try {
			rsSalePlanner = new CSRestService<>(salePlannerUrl);
			rsSalePlannerDelete = new CSRestService<>(salePlannerDeleteUrl);
			tblSalePlanner.setRestServiceDelete(rsSalePlannerDelete);

			//rsSaleOfficers = new BaseRestService<>(saleOfficersUrl);
			
			/*
			rsSalePlanner.setUrl(salePlannerUrl + "allPageable");
			rsSalePlanner.fetch(salePlannerPageId.get(), new ParameterizedTypeReference<JsonResponse<SalePlanner>>() {});
			tblSalePlanner.setItems(rsSalePlanner.getDataAsObservableList());
			*/
			
			cbFilter.onSelectionChanged( newValue -> {
				salePlannerPageId.set(0);
				Integer selectedIndex = cbFilter.getSelectionModel().getSelectedIndex();
				rsSalePlanner.setUrl(salePlannerUrl + getSalePlannerUrl(selectedIndex));
				rsSalePlanner.fetch(salePlannerPageId.get(), new ParameterizedTypeReference<JsonResponse<SalePlanner>>() {});
				tblSalePlanner.setItems(rsSalePlanner.getDataAsObservableList());
			});
			
			tgpFilter.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
				Integer selectedIndex = 0;
				RadioButton rbSelected = (RadioButton) newVal;
				switch(rbSelected.getText()) {
					case "Termin":
						selectedIndex = 0;
					break;
					case "Plan":
						selectedIndex = 1;
					break;
					case "Ref + Kom + Termin":
						selectedIndex = 2;
					break;
					case "Kom + Termin":
						selectedIndex = 3;
					break;
					case "Kontaktirao":
						selectedIndex = 4;
					break;
					case "Danas":
						selectedIndex = 5;
					break;		
					case "Danas svi":
						selectedIndex = 6;
					break;
					case "Termin + Svi ref":
						selectedIndex = 7;
					break;
					default:
						selectedIndex = 0;
					break;
				}
				
				rsSalePlanner.setUrl(salePlannerUrl + getSalePlannerUrl(selectedIndex));
				rsSalePlanner.fetch(salePlannerPageId.get(), new ParameterizedTypeReference<JsonResponse<SalePlanner>>() {});
				tblSalePlanner.setItems(rsSalePlanner.getDataAsObservableList());
			});
	
			tblSalePlanner.onRowDoubleClick( e-> {
				tblSalePlanner.showDoubleClickDefaultAction = true;
			});
			
			tblSalePlanner.onRowSelectionChanged( (oldRow, newRow) -> {
				SalePlanner newRowData = (SalePlanner) newRow;
				String description = newRowData == null ? "" : newRowData.getDescription();
				taDescription.setText(description);
				if (newRowData != null) {
					saleOfficerName = newRowData.getSaleOfficerName();
				}
			});
			
			tblSalePlanner.onDataNeeded( ()-> {
				rsSalePlanner.fetch(salePlannerPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<SalePlanner>>() {});
				tblSalePlanner.addItems(rsSalePlanner.getDataAsObservableList());
			});
			
			/**
			 * Na osnovu moda koji je prosledjen formi, odredjujemo kako ce se forma ponasati
			 */
			switch (mode) {
			case 0:
				//cbFilter.getSelectionModel().selectFirst();
				tgpFilter.selectToggle(tgpFilter.getToggles().get(0));
			break;
			case 1:
				//cbFilter.getSelectionModel().select(5);
				tgpFilter.selectToggle(tgpFilter.getToggles().get(5));
			break;
			case 2:
				//cbFilter.getSelectionModel().select(3);
				tgpFilter.selectToggle(tgpFilter.getToggles().get(3));
			break;
		}
	
			if (client == null) {
				client = tblSalePlanner.getSelectedItem().getClient();
			}

			List<Object> lstControllerParam = new ArrayList<>();
			lstControllerParam.add(tblSalePlanner);
			lstControllerParam.add(new ClientName(client.getId().intValue(), client.getName()));
			tblSalePlanner.setAddEditDialog(tblSalePlannerAddEditControllerName, lstControllerParam);
			if (client == null) {
				tblSalePlanner.btnAdd.setDisable(true);
			}
			
			tfMonth.setText(Integer.toString(LocalDate.now().getMonth().getValue()));
			tfYear.setText(Integer.toString(LocalDate.now().getYear()));
			
			btnMonthlyReview.setOnAction( e-> {
				List<Object> lstControllerParams = new ArrayList<>();
				lstControllerParams.add(saleOfficerId);
				lstControllerParams.add(tfMonth.getText());
				lstControllerParams.add(tfYear.getText());
				lstControllerParams.add(saleOfficerName);
				
				Common.displayForm(ControllerFactory.getController("Sales_SalePlannerMonthlyOverviewController", lstControllerParams, 0), btnMonthlyReview, "Mesečni pregled");
			});
			
			btnSaleOfficerMonthlyReview.setOnAction( e-> {
				List<Object> lstControllerParams = new ArrayList<>();
				lstControllerParams.add(saleOfficerId);
				lstControllerParams.add(saleOfficerName);
				
				Common.displayForm(ControllerFactory.getController("Sales_SalePlannerSaleOfficerMonthlyReviewController", lstControllerParams, 0), btnMonthlyReview, "Mesečni pregled");
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}

	
	/**
	 * Builds sale planner URL for fetch service based on cbFilter combo-box selected item
	 * @param selectedCbFilterValue
	 * @return
	 */
	private String getSalePlannerUrl(Integer selectedCbFilterValue) {
		String res = "";
		switch (selectedCbFilterValue) {
			case 0:
				res = "allPageableBySaleOfficerId/" + saleOfficerId;
			break;
			case 1:
				res = "allPageableBySaleOfficerIdOrderByPlanDateDesc/" + saleOfficerId;
			break;
			case 2:
				res = "allPageableBySaleOfficerIdRefKomTermin/" + saleOfficerId;
			break;
			case 3:
				int clientId = 0;
				if (tblSalePlanner.getSelectedItem() != null) {
					clientId = tblSalePlanner.getSelectedItem().getClientId();
				} else if (client != null) {
					clientId = client.getId().intValue();
				}
				res = "allPageableByClientIdAndSaleOfficerId/" + clientId + "/" + saleOfficerId;
			break;
			case 4:
				res = "allPageableByContactedById/" + saleOfficerId;
			break;
			case 5:
				res = "allPageableBySaleOfficerIdAndPlanDate/" + saleOfficerId + "/" + LocalDate.now().toString();
			break;
			case 6:
				res = "allPageableByPlanDate/" + LocalDate.now().toString();
			break;
			case 7:
				res = "allPageableOrderByEventDate/";
			break;
			default:
				return null;
		}
		
		// ako user nema prava, sakrij sve referente (Referenti + Klijenti + Termin)
		//cbFilter.getItems().remove(6, 8);
		
		return res;
	}
	
}
