package org.code_studio.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.FxmlController;
import org.code_studio.database.DeliveryAddress;
import org.code_studio.database.SalePlannerReview;
import org.code_studio.database.SupplierPricelist;
import org.code_studio.database.BankAccount;
import org.code_studio.database.Client;
import org.code_studio.database.ClientLastPaidAmount;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

@SuppressWarnings("unused")
public class Sales_SalePlannerMonthlyOverviewController extends BaseController {

	@FXML private CSTable<ClientLastPaidAmount> tblClientLastPaidAmount;
	@FXML private Button btnCancel;
	@FXML private CSTextField tfMonth;
	@FXML private CSTextField tfYear;
	@FXML private CSTextField tfSaleOfficerName;
	@FXML private Button btnPrintPerPeriod;
	@FXML private Button btnPrintPerGroup;
	
	@FXML private CSTable<SalePlannerReview> tblSalePlannerReview_part1;
	@FXML private CSTable<SalePlannerReview> tblSalePlannerReview_part2;
	@FXML private CSTable<SalePlannerReview> tblSalePlannerReview_part3;
		
	List<Object> lstControllerParams;
	private int mode;
	private Integer saleOfficerId;
	private String month;
	private String year;
	private Integer clientId;
	private String saleOfficerName;

	private AtomicInteger clientLastPaidAmountPageId;
	
	private final String urlClientLastPaidAmount = "/clientLastPaidAmount/allPageableBySaleOfficerIdAndDateRange/";
	private CSRestService<ClientLastPaidAmount> rsClientLastPaidAmount;
	
	private final String urlSalePlannerReview = "/salePlannerReview/allBySaleOfficerIdAndClientIdAndDateRange/";
	private CSRestService<SalePlannerReview> rsSalePlannerReview_part1;
	private CSRestService<SalePlannerReview> rsSalePlannerReview_part2;
	private CSRestService<SalePlannerReview> rsSalePlannerReview_part3;
	
	@SuppressWarnings("unchecked")
	public Sales_SalePlannerMonthlyOverviewController(Object controllerParam, int mode, ApplicationContext ctx) {
		clientLastPaidAmountPageId = new AtomicInteger(0);
		this.mode = mode;
		lstControllerParams = (List<Object>) controllerParam;
		saleOfficerId = (Integer) lstControllerParams.get(0);
		month = (String) lstControllerParams.get(1);
		month = month.length() == 1 ? "0" + month : month;
		year  = (String) lstControllerParams.get(2);
		saleOfficerName = (String) lstControllerParams.get(3);
	}
	
	public void initialize() {
		try {
			tfMonth.setTextOrEmptyString(month);
			tfYear.setTextOrEmptyString(year);
			tfSaleOfficerName.setTextOrEmptyString(saleOfficerName);
			
			rsClientLastPaidAmount = new CSRestService<>(urlClientLastPaidAmount);
			rsClientLastPaidAmount.setUrl(urlClientLastPaidAmount + saleOfficerId + "/" + month + "/" + year);
			rsClientLastPaidAmount.fetch(clientLastPaidAmountPageId.get(), new ParameterizedTypeReference<JsonResponse<ClientLastPaidAmount>>() {});
			tblClientLastPaidAmount.setItems(rsClientLastPaidAmount.getDataAsObservableList());
			
			rsSalePlannerReview_part1 = new CSRestService<>(urlSalePlannerReview);
			rsSalePlannerReview_part2 = new CSRestService<>(urlSalePlannerReview);
			rsSalePlannerReview_part3 = new CSRestService<>(urlSalePlannerReview);
			
			tblClientLastPaidAmount.onDataNeeded( () -> {
				rsClientLastPaidAmount.fetch(clientLastPaidAmountPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<ClientLastPaidAmount>>() {});
				tblClientLastPaidAmount.addItems(rsClientLastPaidAmount.getDataAsObservableList());
			});
			
			tblClientLastPaidAmount.onRowSelectionChanged( (oldRow, newRow) -> {
				if (newRow != null) {
					clientId = tblClientLastPaidAmount.getSelectedItem().getId().intValue();
					rsSalePlannerReview_part1.setUrl(urlSalePlannerReview + saleOfficerId + "/" + clientId + "/" + month + "/" + year + "/1");
					rsSalePlannerReview_part1.fetch(new ParameterizedTypeReference<JsonResponse<SalePlannerReview>>() {});
					tblSalePlannerReview_part1.setItems(rsSalePlannerReview_part1.getDataAsObservableList());
					
					rsSalePlannerReview_part2.setUrl(urlSalePlannerReview + saleOfficerId + "/" + clientId + "/" + month + "/" + year + "/2");
					rsSalePlannerReview_part2.fetch(new ParameterizedTypeReference<JsonResponse<SalePlannerReview>>() {});
					tblSalePlannerReview_part2.setItems(rsSalePlannerReview_part2.getDataAsObservableList());
					
					rsSalePlannerReview_part3.setUrl(urlSalePlannerReview + saleOfficerId + "/" + clientId + "/" + month + "/" + year + "/3");
					rsSalePlannerReview_part3.fetch(new ParameterizedTypeReference<JsonResponse<SalePlannerReview>>() {});
					tblSalePlannerReview_part3.setItems(rsSalePlannerReview_part3.getDataAsObservableList());
				}
			});
			
			if (tblClientLastPaidAmount.tableView.getItems().isEmpty()) {
				btnPrintPerPeriod.setDisable(true);
				btnPrintPerGroup.setDisable(true);
			}
			
			btnPrintPerPeriod.setOnAction(e->{
				Common.displayForm(ControllerFactory.getController("Sales_SalePlannerMonthlyOverviewPrintController", null, 0), btnPrintPerPeriod, "Štampa za period");
			});
			
			btnPrintPerGroup.setOnAction(e->{
				Common.displayForm(ControllerFactory.getController("Sales_SalePlannerMonthlyOverviewPrintController", null, 1), btnPrintPerGroup, "Štampa po grupi i nazivu");
			});
			
			btnCancel.setOnAction(e->{
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END

}
