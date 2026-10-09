package org.code_studio.controller;


import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSReportView;
import org.code_studio.component.ui.CSComboBoxSalesOfficer;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Sales_SalePlannerMonthlyOverviewPrintController extends BaseController {
	@FXML private Button btnPrint;
	@FXML private Button btnCancel;
	@FXML private CSComboBoxSalesOfficer cbSalesOfficer; // roles 10, 11, 12, 13
	@FXML private CheckBox ckbAll;
	@FXML private CSDatePicker dtDateFrom;
	@FXML private CSDatePicker dtDateTo;
	@FXML private HBox hbDateTo;
	@FXML private Label lblDateFrom;
	
	private int mode;
	private String baseUrl;

	HashMap<String, Object> rptParams;
	List<String> lstRptConfig;

	public Sales_SalePlannerMonthlyOverviewPrintController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		baseUrl = Common.applicationProperties.getProperty("api.serverurl");
		
		rptParams = new HashMap<>();
		lstRptConfig = new ArrayList<>();
	}
	
	public void initialize() {
		
		cbSalesOfficer.disableProperty().bind(ckbAll.selectedProperty());
		// Group report has custom settings
		if (mode == 1) {
			hbDateTo.setVisible(false);
			lblDateFrom.setText("Za mesec");
		}
		
		dtDateFrom.valueProperty().addListener((e) -> {
			if (dtDateFrom.getValue() != null) {
				dtDateTo.setValue(dtDateFrom.getValue().with(TemporalAdjusters.lastDayOfMonth()));
			}
		});

		btnPrint.setOnAction( e-> {
			Integer saleOfficerId = ckbAll.isSelected() ? 0 : cbSalesOfficer.getValue().getId().intValue();
			String  strDateFrom = dtDateFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String  strDateTo = dtDateTo.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

			lstRptConfig.clear();
			lstRptConfig.add(dtDateFrom.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			lstRptConfig.add(dtDateTo.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			lstRptConfig.add(ckbAll.isSelected() ? "SVI REFERENTI" : cbSalesOfficer.getValue().getName());
			
			rptParams.clear();
			rptParams.put("REPORT_CONFIG", lstRptConfig); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
			
			if (mode == 0) {
				rptParams.put("JSON_DETAIL_URL", baseUrl + "/salePlannerReview/allByDateRange/" + strDateFrom + "/" + strDateTo + "/" + saleOfficerId);
				new CSReportView("sale_planner_monthly_overview", rptParams);
			} else if (mode == 1) {
				rptParams.put("JSON_DETAIL_URL", baseUrl + "/salePlannerMonthByDay/allByDateRangeAndGroup/" + strDateFrom + "/" + strDateTo + "/" + saleOfficerId);
				new CSReportView("sale_planner_monthly_overview_by_group", rptParams);
			}
		});

		btnCancel.setOnAction(e->{
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});
	}

}
