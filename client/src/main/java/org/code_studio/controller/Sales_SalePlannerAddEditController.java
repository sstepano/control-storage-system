package org.code_studio.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.code_studio.database.ApplicationUserName;
import org.code_studio.database.Client;
import org.code_studio.database.ClientContact;
import org.code_studio.database.ClientLastPaidAmount;
import org.code_studio.database.ClientName;
import org.code_studio.database.SalePlanner;
import org.code_studio.database.SalePlannerType;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Sales_SalePlannerAddEditController extends BaseController {

	@FXML private CSDialogButtons dialogButtons;

	@FXML CSTextField tfClientId;
	@FXML TextField tfSaleOfficer;
	@FXML CSTextField tfChargedAmount;
	@FXML CSTextField tfDeliveredAmount;
	@FXML CSTextField tfPhone;
	@FXML TextArea taDescription;
	
	@FXML CSDatePicker dtEventDay;
	@FXML CSTextField tfEventTime;
	@FXML CSDatePicker dtEventPlanDay;
	
	@FXML CheckBox ckbIsCame;
	@FXML CheckBox ckbIsCalledTo;
	@FXML CheckBox ckbIsWentTo;
	@FXML CheckBox ckbIsCalled;
	
	@FXML CSComboBox <SalePlannerType> cbSalePlannerType;
	@FXML CSComboBox <ApplicationUserName> cbSaleOfficerContacted;
	@FXML CSComboBox <ClientContact> cbContactWith;
	
	@FXML Label lblContactWith;
	@FXML Button btnClientChoose;
	
	CSRestService <ApplicationUserName> rsSaleOfficer;
	CSRestService <SalePlannerType> rsSalePlannerType;
	
	private final String clientContactUrl = "/clientContact/allByClientId/";
	CSRestService <ClientContact> rsContactWith;
	
	private int mode;
	private ClientName client;
	private CSTable<SalePlanner> tblSalePlanner;
	private SalePlanner salePlanner;
	CSRestService<Client> rsClient;
	private CSRestService<SalePlanner> rsAddUpdateService;
	
	private final String urlClientLastPaidAmount = "/clientLastPaidAmount/allByClientId/";
	private CSRestService<ClientLastPaidAmount> rsClientLastPaidAmount;

	
	@SuppressWarnings("unchecked")
	public Sales_SalePlannerAddEditController(Object controllerParam, int mode) {
		List<Object> lstControllerParam = (List<Object>) controllerParam;
		this.tblSalePlanner = (CSTable<SalePlanner>) lstControllerParam.get(0);
		this.mode = mode;
	}

	@FXML
	public void initialize() {
		try {
			
			btnClientChoose.setOnAction( e-> {
				Client clientSelected = (Client) Common.displayForm(ControllerFactory.getController("Sales_ClientSelectController", null, 0), btnClientChoose, "Odabir klijenta");
				if (clientSelected != null) {
					this.client = new ClientName(clientSelected.getId().intValue(), clientSelected.getName());
					tfClientId.setText(this.client.getId().toString());
				} else {
					tfClientId.setText(null);
				}
			});
			
			rsAddUpdateService = new CSRestService<>("/salePlanner");			
			rsContactWith = new CSRestService<>(clientContactUrl);
			rsClientLastPaidAmount = new CSRestService<ClientLastPaidAmount>(urlClientLastPaidAmount);
			
			salePlanner = tblSalePlanner.getSelectedItem();
			if (salePlanner != null) {
				this.client = (ClientName) tblSalePlanner.getSelectedItem().getClient() ;
			}
			
			rsSalePlannerType = new CSRestService<>("/salePlannerType");
			rsSalePlannerType.fetch(new ParameterizedTypeReference<JsonResponse<SalePlannerType>>() {});
			cbSalePlannerType.setItemsAndSelectFirstItem(rsSalePlannerType.getDataAsObservableList());
	
			rsSaleOfficer = new CSRestService<>("/applicationUser");
			rsSaleOfficer.fetch(new ParameterizedTypeReference<JsonResponse<ApplicationUserName>>() {});
			cbSaleOfficerContacted.setItemsAndSelectFirstItem(rsSaleOfficer.getDataAsObservableList());
	
			tfClientId.onTextChanged((oldVal, newVal) -> {
				if (newVal !=null && newVal.length() > 0) {
					rsContactWith.setUrl(clientContactUrl + this.client.getId());
					rsContactWith.fetch(new ParameterizedTypeReference<JsonResponse<ClientContact>>() {});
					cbContactWith.setItemsAndSelectFirstItem(rsContactWith.getDataAsObservableList());
					
					if (cbContactWith.getItems().size() > 0) {
						tfPhone.setText(((ClientContact) cbContactWith.getSelectionModel().getSelectedItem()).getPhone());
					}
					
					rsClientLastPaidAmount.setUrl(urlClientLastPaidAmount + this.client.getId());
					rsClientLastPaidAmount.fetch(new ParameterizedTypeReference<JsonResponse<ClientLastPaidAmount>>() {});
					tfDeliveredAmount.setText(rsClientLastPaidAmount.getDataAsObservableList().size() > 0 
						? rsClientLastPaidAmount.getDataAsObservableList().get(0).getLastDeliveredAmt().toString()
						: "0.00"
					);
					tfChargedAmount.setText(rsClientLastPaidAmount.getDataAsObservableList().size() > 0 
							? rsClientLastPaidAmount.getDataAsObservableList().get(0).getLastPaidAmt().toString()
							: "0.00"
					);
				}
			});
	
			if (mode == 0) {
				/////////////////////salePlanner = new SalePlanner(client, client.getPrimarySaleOfficer().get(0).getApplicationUser(), LocalDate.now());
				dtEventDay.setValue(LocalDate.now());
				tfEventTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
				dtEventPlanDay.setValue(LocalDate.now());
			} else if (mode == 1) { //Edit mode
				tfClientId.setText(salePlanner.getClientId().toString());
				tfSaleOfficer.setText(salePlanner.getSaleOfficerName());
				dtEventDay.setValue(salePlanner.getEventDate().toLocalDate());
				tfEventTime.setText(salePlanner.getEventDate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
				
				if (salePlanner.getPlanDate() != null) {
					dtEventPlanDay.setValue(salePlanner.getPlanDate().toLocalDate());
				}
				
				ckbIsCame.setSelected(salePlanner.getIsCame());
				ckbIsCalledTo.setSelected(salePlanner.getIsCalledTo());
				ckbIsWentTo.setSelected(salePlanner.getIsWentTo());
				ckbIsCalled.setSelected(salePlanner.getIsCalled());
				
				cbSalePlannerType.select(salePlanner.getSalePlannerType());
				
				if (salePlanner.getSaleOfficerContacted() != null) {
					cbSaleOfficerContacted.select(salePlanner.getSaleOfficerContacted());
				}
				
				if (salePlanner.getClientContact() != null) {
					/////////////////cbContactWith.select(salePlanner.getClientContact());
					tfPhone.setText(salePlanner.getClientContact().getPhone());
				}
				
				if (salePlanner.getDeliveredAmt() != null ) {
					tfDeliveredAmount.setText(salePlanner.getDeliveredAmt().toString());
				}
				
				if (salePlanner.getChargedAmt() != null ) {
					tfChargedAmount.setText(salePlanner.getChargedAmt().toString());
				}
				
				taDescription.setText(salePlanner.getDescription());
			}
			
			cbContactWith.onSelectionChanged( newSelection -> {
				tfPhone.setText(((ClientContact) newSelection).getPhone());
			});
			
			lblContactWith.setOnMouseReleased(e->{
				System.out.println("lbl mouse released. Opening relevant dialog for cliecked properties editing!");
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
	
			
			dialogButtons.getSaveButton().setOnAction( e-> {
				if (mode == 0) {//add
					LocalTime lt = null; 
					try {
						lt = LocalTime.of(Integer.parseInt(tfEventTime.getText(0, 2)), Integer.parseInt(tfEventTime.getText(3, 5)));
					} catch (Exception ex) {
						lt = LocalTime.now();
						Common.ShowNotification("SNIMANJE ROKOVNIKA", "Uneto vreme je neispravno. Postavljeno je na trenutno.", false); // Ne radi notification na dialog formi
					}
					
					LocalDateTime ldt = LocalDateTime.of(dtEventDay.getValue(), lt);
					salePlanner.setEventDate(ldt);
					
					if (dtEventPlanDay.getValue() != null) {
						salePlanner.setPlanDate(
							CSDatePicker.getLocalDateTimeFromLocalDateAndLocalTime(dtEventPlanDay.getValue(), LocalTime.now())
						);
					}
					salePlanner.setIsCame(ckbIsCame.isSelected());
					salePlanner.setIsCalled(ckbIsCalled.isSelected());
					salePlanner.setIsWentTo(ckbIsWentTo.isSelected());
					salePlanner.setIsCalledTo(ckbIsCalledTo.isSelected());
					salePlanner.setSalePlannerType(cbSalePlannerType.getSelectionModel().getSelectedItem());
					salePlanner.setContactedById(cbSaleOfficerContacted.getSelectionModel().getSelectedItem().getId().intValue());
					salePlanner.setDeliveredAmt(tfDeliveredAmount.getTextAsBigDecimal());
					salePlanner.setChargedAmt(tfChargedAmount.getTextAsBigDecimal());
					salePlanner.setClientContactId(cbContactWith.getValue().getId().intValue());
					salePlanner.setDescription(taDescription.getText());
				} else {
					LocalTime lt = null; 
					try {
						lt = LocalTime.of(Integer.parseInt(tfEventTime.getText(0, 2)), Integer.parseInt(tfEventTime.getText(3, 5)));
					} catch (Exception ex) {
						lt = LocalTime.now();
						Common.ShowNotification("SNIMANJE ROKOVNIKA", "Uneto vreme je neispravno. Postavljeno je na trenutno.", false); // Ne radi notification na dialog formi
					}
					LocalDateTime ldt = LocalDateTime.of(dtEventDay.getValue(), lt);
					salePlanner.setEventDate(ldt);
					
					if (dtEventPlanDay.getValue() != null) {
						salePlanner.setPlanDate(
							CSDatePicker.getLocalDateTimeFromLocalDateAndLocalTime(dtEventPlanDay.getValue(), LocalTime.now())
						);
					}
					salePlanner.setIsCame(ckbIsCame.isSelected());
					salePlanner.setIsCalled(ckbIsCalled.isSelected());
					salePlanner.setIsWentTo(ckbIsWentTo.isSelected());
					salePlanner.setIsCalledTo(ckbIsCalledTo.isSelected());
					salePlanner.setSalePlannerType(cbSalePlannerType.getSelectionModel().getSelectedItem());
					salePlanner.setSaleOfficerContacted(cbSaleOfficerContacted.getValue());
					salePlanner.setClientContactId(cbContactWith.getValue().getId().intValue());
					salePlanner.setDeliveredAmt(tfDeliveredAmount.getTextAsBigDecimal());
					salePlanner.setChargedAmt(tfChargedAmount.getTextAsBigDecimal());
					/////salePlanner.setPromisedAmt(tfPromisedAmount.getTextAsBigDecimal());
					salePlanner.setClientContactId(cbContactWith.getValue().getId().intValue());
					salePlanner.setDescription(taDescription.getText());
				}
				
				SalePlanner insertedItem = rsAddUpdateService.addOrUpdate(salePlanner);
				if (mode == 0) {
					tblSalePlanner.addItem(insertedItem);
				}
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}

	}
}
