package org.code_studio.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.database.ApplicationUser;
import org.code_studio.database.ApplicationUserName;
import org.code_studio.database.Client;
import org.code_studio.database.SalePlanner;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

public class Sales_WorkplanController extends BaseController implements Initializable {
	
	@FXML private Button btnInsertDate;
	@FXML private Button btnTransferToSalePlanner;
	
	@FXML private CSTable <Client> tblClient;
	@FXML private CSDatePicker dtPlanDate;
	@FXML CSComboBox <ApplicationUser> cbSaleOfficer;
	
	private final String clientBySaleOfficerIdUrl = "/client/";
	private AtomicInteger clientPageId = new AtomicInteger(0);
	private CSRestService <Client> rsClient;
	private final int pageSize = 40;
	
	private final String saleOfficersUrl = "/applicationUser/allSaleOfficers";
	private CSRestService <ApplicationUser> rsSaleOfficers;
	
	private final String urlClientAddUpdate = "/client";
	private CSRestService <Client> rsClientAddUpdate;
	
	private final String urlSalePlannerAddUpdate = "/salePlanner";
	private CSRestService <SalePlanner> rsSalePlannerAddUpdate;
	
	
	public Sales_WorkplanController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		try {
			rsClient = new CSRestService<>(getClientUrl());
			rsSaleOfficers = new CSRestService<>(saleOfficersUrl);
			rsClientAddUpdate = new CSRestService<>(urlClientAddUpdate);
			rsSalePlannerAddUpdate = new CSRestService<>(urlSalePlannerAddUpdate);
			
			rsSaleOfficers.fetch(new ParameterizedTypeReference<JsonResponse<ApplicationUser>>() {});
			cbSaleOfficer.getItems().addAll(rsSaleOfficers.getDataAsObservableList());
			
			dtPlanDate.setValue(LocalDate.now());
			
			rsClient.fetch(clientPageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.setItems(rsClient.getDataAsObservableList());
			
			tblClient.onDataNeeded( ()-> {
				rsClient.fetch(clientPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
				tblClient.addItems(rsClient.getDataAsObservableList());
			});
			
			cbSaleOfficer.setOnAction( e-> {
				rsClient.setUrl(getClientUrl());
				rsClient.fetch(clientPageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
				tblClient.setItems(rsClient.getDataAsObservableList());
			});
			
			tblClient.setOnKeyReleased(e-> {
				if (e.getCode() == KeyCode.ENTER) {
					insertDate();
				}
			});
			
			btnInsertDate.setOnAction( e-> {
				insertDate();
			});
			
			/**
			 * Transfer to CLIENT.PLAN_DATE and create new row in SalePlanner for that Client/SalesOfficer 
			 */
			btnTransferToSalePlanner.setOnAction( e-> {
				SalePlanner salePlanner = null;
				ApplicationUser saleOfficer = (ApplicationUser) cbSaleOfficer.getSelectionModel().getSelectedItem();
				
				for (Client client : tblClient.tableView.getItems()) {
					if (client.getPlanDate() != null && (client.getPlanDate().isEqual(LocalDate.now()) || client.getPlanDate().isAfter(LocalDate.now()))) {
						rsClientAddUpdate.addOrUpdate(client);
						
						// ako je izabrani sale officer null iz comboboxa, tj ako je cb vrednost SVI KLIJENTI, onda uzimamo defaultnog sale officera za tog klijenta
						if (saleOfficer == null) {
							saleOfficer = client.getPrimarySaleOfficer().get(0).getApplicationUser();
						}
						salePlanner = new SalePlanner(client.getId().intValue()
								, new ApplicationUserName(
										saleOfficer.getId()
									  , saleOfficer.getUsername()
									  , saleOfficer.getName()
									  , saleOfficer.getRoleId()
							    ), CSDatePicker.getLocalDateTimeFromLocalDateAndLocalTime(client.getPlanDate(), LocalTime.now()));
						salePlanner.setEventDate(LocalDateTime.of(client.getPlanDate(), LocalDateTime.now().toLocalTime()));
						rsSalePlannerAddUpdate.addOrUpdate(salePlanner);
					}
				}
				Common.ShowNotification("Prenos u rokovnik", "Plan rada prenesen u rokovnik.", false);
			});
		
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
	
	//group code to use in two places
	private void insertDate () {
		tblClient.getSelectedItem().setPlanDate(dtPlanDate.getValue());
		tblClient.tableView.refresh();
		int selectedIndex = tblClient.tableView.getSelectionModel().getSelectedIndex();
		
		if (tblClient.tableView.getItems().size() >= selectedIndex) {
			tblClient.tableView.getSelectionModel().select(selectedIndex + 1);
		}
	}
	
	/**
	 * Build URL based on ApplicationUser DDL or LoggedIn user ID
	 */
	private String getClientUrl() {
		String res = "";
		int loggedInSaleOfficerId = 112; // TODO: zakuc, ovde cemo da imamo stvarnog logovanog korisnika
		ApplicationUser applicationUser = (ApplicationUser) cbSaleOfficer.getSelectionModel().getSelectedItem();
		String appUserId = applicationUser == null ? String.valueOf(loggedInSaleOfficerId) : applicationUser.getId().toString();

		res = clientBySaleOfficerIdUrl + "allBySaleOfficerIdWithPageSize/" 
				+ appUserId 
				+ "/" 
				+ pageSize;

		
		return res;
	}
	
}
