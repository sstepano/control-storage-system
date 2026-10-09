package org.code_studio.controller;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSReportView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.Client;
import org.code_studio.database.ClientAccountBalanceCard;
import org.code_studio.database.Invoice;
import org.code_studio.database.InvoiceDetail;
import org.code_studio.database.InvoiceDetailSum;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.database.StoredProcedureResult;
import org.code_studio.database.TransferOrder;
import org.code_studio.database.TransferOrderDetail;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Sales_WholesaleController extends BaseController {
	
	@FXML private CSTable<Invoice> tblInvoice;
	@FXML private CSTable<InvoiceDetail> tblInvoiceDetail;
	@FXML private CSTable<TransferOrderDetail> tblTransferOrderDetail;
	
	@FXML private CSTextField tfClientId;
	@FXML private CSTextField tfName;
	@FXML private CSTextField tfFullName;
	@FXML private CSTextField tfPOBox;
	@FXML private CSTextField tfCity;
	@FXML private CSTextField tfAddress;
	@FXML private CSTextField tfCountry;
	@FXML private CSTextField tfLimitEur;
	@FXML private CSTextField tfDebitAmt;
	@FXML private TextArea    taBlueField;
	@FXML private Label       lblClientFullName;
	@FXML private Label       lblClientName;
	@FXML private Label       lblClientId;
	@FXML private Label       lblClientAddress;
	@FXML private TextArea    taClientDescription;
	@FXML private CSTextField tfTotalCount;
	@FXML private CSTextField tfTotalAmount;
	@FXML private TextArea    taInvoiceDescription;
	@FXML private CSDatePicker dtDate;
	@FXML private CSTextField  tfTime;
	
	@FXML private ToggleGroup tgpPrimerak;
	@FXML private CheckBox    chkWithStamp;
	@FXML private CheckBox    chkWithBarcode;
	@FXML private CheckBox    chkDisplayDiscount;
	@FXML private CheckBox    chkWithDOMCode;
	
	@FXML private Button btnPreinvoice;
	@FXML private Button btnInvoice;
	@FXML private Button btnInvoiceNoYear;
	@FXML private Button btnInvoiceDispatchNote;
	@FXML private Button btnInvoiceDispatchNoteNoYear;
	@FXML private Button btnDispatchNote;
	@FXML private Button btnDispatchNoteNoPrices;
	
	@FXML private Button btnExcelExport;
	@FXML private Button btnInvoiceToFK;
	@FXML private Button btnXmlTehnomedia;
	@FXML private Button btnXmlGigatron;
	@FXML private Button btnAutoInvoiceLoad;
	
	@FXML private CSTextField tfAccountDebitAmt;
	@FXML private CSTextField tfAccountCreditAmt;
	@FXML private CSTextField tfAccountBalanceAmt;
	
	@FXML private CSTextField tfInvoiceId;
	@FXML private CSTextField tfDispatchNoteId;
	
	@FXML private Button btnInvoiceApprove;
	@FXML private Button btnDeleteInvoiceDiscount;
	@FXML private Button btnDeleteItemDiscount;
	@FXML private Button btnTransferOrderPrint;
	@FXML private Button btnSaveTransferOrderDueDateAndDescription;
	@FXML private Button btnElectronicInvoice;
	@FXML private Button btnF6;
	
	@FXML private Button btnClientChange;
	
	@FXML private CSComboBox<String> cbDeliveryType;
	@FXML private HBox hbReservationAndRevers;
	@FXML private VBox vbTransferOrder;

	private Client client;
	
	private int mode;
	private ApplicationContext ctx;
	private Common common;
	private String baseUrl;
	private Invoice invoice;
	
	HashMap<String, Object> rptParamsInvoice;
	List<String> lstRptConfigInvoice;
	
	HashMap<String, Object> rptParamsTransferOrder;
	List<String> lstRptConfigTransferOrder;
	
	private TransferOrder transferOrder;
	
	private final String tblWholesaleAddEditControllerName = "Sales_WholesaleAddEditController";
	private final String tblWholesaleDetailAddEditControllerName = "Sales_WholesaleDetailAddEditController";
	
	private String urlInvoice = null; // dinamicki se dodeljuje
	private CSRestService<Invoice> rsInvoice;
	private AtomicInteger invoicePageId;
	private String displayMode = ""; // veleprodaja, kotpremnice, kracuni, predracun ... prosledjuje se add-edit formi zbog radio boxeva
	
	private final String urlInvoiceAddUpdate = "/invoice";
	private CSRestService<Invoice> rsInvoiceAddUpdate;
	
	private final String urlInvoiceDetail = "/invoiceDetail/allPageableByInvoiceId/";
	private CSRestService<InvoiceDetail> rsInvoiceDetail;
	private AtomicInteger invoiceDetailPageId;
	
	private final String urlInvoiceDetailAddUpdateDelete = "/invoiceDetail";
	private CSRestService<InvoiceDetail> rsInvoiceDetailAddUpdateDelete;
	
	private final String urlInvoiceDetailSum = "/invoiceDetail/sumByInvoiceId/";
	private CSRestService<InvoiceDetailSum> rsInvoiceDetailSum;
	
	private final String urlTransferOrder = "/transferOrder/allByInvoiceId/";
	private CSRestService<TransferOrder> rsTransferOrder;
	
	private final String urlTransferOrderAddUpdate = "/transferOrder";
	private CSRestService<TransferOrder> rsTransferOrderAddUpdate;
	
	private final String urlClientAccountBalanceCard = "/clientAccountBalance/debitCreditByClientIdAndValueDate/%s/1900-10-10/3000-01-01";
	private CSRestService<ClientAccountBalanceCard> rsClientAccountBalanceCard;

	private final String urlMaxClientInvoiceNumber = "/invoice/maxInvoiceNumberByClientId/";
	private CSRestService<Integer> rsMaxClientInvoiceNumber;

	private final String urlTransferOrderDetail = "/transferOrderDetail/findByInvoiceDetailId/";
	private CSRestService<TransferOrderDetail> rsTransferOrderByInvoiceDetailId;
	
	private final String urlItemWarehouseAddUpdate = "/itemWarehouse/appendItemQtyByItemWarehouseIdAndItemId";
	private CSRestService<ItemWarehouse> rsItemWarehouseAddUpdate;

	private String urlOrderFileImport = "/fileImporter/import/";
	private CSRestService<StoredProcedureResult> rsOrderFileImport;
	private String orderFileExtension = null;
	
	private String urlInvoiceFileExport = "/fileExporter/export/";
	private CSRestService<Object> rsInvoiceFileExport;
	
	
	public Sales_WholesaleController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.ctx = ctx;
		this.common = ctx.getBean(Common.class);
		baseUrl = Common.applicationProperties.getProperty("api.serverurl");

		client = (Client) controllerParam;
		invoicePageId = new AtomicInteger(0);
		invoiceDetailPageId = new AtomicInteger(0);
		
		rptParamsInvoice = new HashMap<>();
		lstRptConfigInvoice = new ArrayList<>();
		
		rptParamsTransferOrder = new HashMap<>();
		lstRptConfigTransferOrder = new ArrayList<>();
	}
	
	public void initialize() {
		try {

			cbDeliveryType.getSelectionModel().selectFirst();
			
			// WHOLESALE - Veleprodaja
			if (mode == 0) {
				urlInvoice = "/invoice/allPageableByClientId/";
				btnElectronicInvoice.setVisible(true);
			}
			// COMMISION TRANSFER ORDER - Komisiona otpremnica
			else if (mode == 1) {
				urlInvoice = "/invoice/allPageableByInvoiceTypeAndClientId/KO/";
				tblInvoice.setTopLabelText("KOMISIONE OTPREMNICE");
				displayMode = "KO";
				
				btnPreinvoice.setVisible(false);
				btnInvoice.setVisible(false);
				btnInvoiceDispatchNote.setVisible(false);
				btnDispatchNoteNoPrices.setVisible(false);
			}
			// COMMISION BILL - Komisioni racuni
			else if (mode == 2) {
				urlInvoice = "/invoice/allPageableByInvoiceTypeAndClientId/K/";
				tblInvoice.setTopLabelText("KOMISIONI RAČUNI");
				displayMode = "K";
			}
			// RESERVATION - Rezervacije
			else if (mode == 3) {
				urlInvoice = "/invoice/allPageableByInvoiceTypeAndClientId/R/";
				tblInvoice.setTopLabelText("REZERVACIJE");
				displayMode = "R";
				
				hbReservationAndRevers.setManaged(true);
				hbReservationAndRevers.setVisible(true);
				btnInvoice.setVisible(false);
				btnInvoiceDispatchNote.setVisible(false);
			}
			// PREINVOICE - Predracun
			else if (mode == 4) {
				urlInvoice = "/invoice/allPageableByInvoiceTypeAndClientId/PR/";
				tblInvoice.setTopLabelText("PREDRAČUN");
				displayMode = "PR";

				vbTransferOrder.setManaged(false);
				vbTransferOrder.setVisible(false);
				hbReservationAndRevers.setManaged(false);
				hbReservationAndRevers.setVisible(false);

				btnInvoice.setVisible(false);
				btnInvoiceNoYear.setVisible(false);
				btnInvoiceDispatchNote.setVisible(false);
				btnInvoiceDispatchNoteNoYear.setVisible(false);
				btnDispatchNote.setVisible(false);
				btnDispatchNoteNoPrices.setVisible(false);
				btnExcelExport.setVisible(false);
				btnInvoiceToFK.setVisible(false);
				btnXmlTehnomedia.setVisible(false);
				btnXmlGigatron.setVisible(false);
				btnAutoInvoiceLoad.setVisible(false);
			}
			// REVERS - Revers
			else if (mode == 5) {
				urlInvoice = "/invoice/allPageableByInvoiceTypeAndClientId/RV/";
				tblInvoice.setTopLabelText("REVERS");
				displayMode = "RV";

				vbTransferOrder.setManaged(false);
				vbTransferOrder.setVisible(false);
				hbReservationAndRevers.setManaged(false);
				hbReservationAndRevers.setVisible(false);

				btnInvoice.setVisible(false);
				btnInvoiceNoYear.setVisible(false);
				btnInvoiceDispatchNote.setVisible(false);
				btnInvoiceDispatchNoteNoYear.setVisible(false);
				btnDispatchNote.setVisible(false);
				btnDispatchNoteNoPrices.setVisible(false);
				btnExcelExport.setVisible(false);
				btnInvoiceToFK.setVisible(false);
				btnXmlTehnomedia.setVisible(false);
				btnXmlGigatron.setVisible(false);
				btnAutoInvoiceLoad.setVisible(false);
			}// ADVANCE PAYMENT - Avans
			else if (mode == 6) {
				urlInvoice = "/invoice/allPageableByInvoiceTypeAndClientId/AD/";
				tblInvoice.setTopLabelText("AVANS");
				displayMode = "AD";

				vbTransferOrder.setManaged(false);
				vbTransferOrder.setVisible(false);
				hbReservationAndRevers.setManaged(false);
				hbReservationAndRevers.setVisible(false);

				btnInvoice.setVisible(false);
				btnInvoiceNoYear.setVisible(false);
				btnInvoiceDispatchNote.setVisible(false);
				btnInvoiceDispatchNoteNoYear.setVisible(false);
				btnDispatchNote.setVisible(false);
				btnDispatchNoteNoPrices.setVisible(false);
				btnExcelExport.setVisible(false);
				btnInvoiceToFK.setVisible(false);
				btnXmlTehnomedia.setVisible(false);
				btnXmlGigatron.setVisible(false);
				btnAutoInvoiceLoad.setVisible(false);
			}
			
			tfClientId.setText(client.getId().toString());
			tfName.setText(client.getName());  
			tfFullName.setText(client.getFullName());
			tfPOBox.setText(client.getPoBox().toString());
			tfCity.setText(client.getCity());
			tfAddress.setText(client.getAddress());
			tfCountry.setText(client.getCountry().getName());
			tfLimitEur.setText(client.getlimitEur().toString());
			tfDebitAmt.setText(client.getAccountBalance().toString());
			lblClientFullName.setText(client.getFullName());
			lblClientName.setText(client.getName());
			lblClientId.setText(client.getId().toString());
			lblClientAddress.setText(client.getCity() + ", " + client.getAddress());
			taClientDescription.setText(client.getDescription());

			List<Object> lstInvoiceParams = new ArrayList<>();
			lstInvoiceParams.add(tblInvoice);
			lstInvoiceParams.add(client);
			lstInvoiceParams.add(taInvoiceDescription);
			lstInvoiceParams.add(displayMode); 
			tblInvoice.setAddEditDialog(tblWholesaleAddEditControllerName, lstInvoiceParams);
			
			List<Object> lstInvoiceDetailParams = new ArrayList<>();
			lstInvoiceDetailParams.add(tblInvoiceDetail);
			lstInvoiceDetailParams.add(tblInvoice);
			lstInvoiceDetailParams.add(client);
			tblInvoiceDetail.setAddEditDialog(tblWholesaleDetailAddEditControllerName, lstInvoiceDetailParams);
			
			tblInvoiceDetail.setParentTable(tblInvoice);
			tblTransferOrderDetail.setParentTable(tblInvoiceDetail);
			
			rsInvoice = new CSRestService<>(urlInvoice);
			rsInvoice.setParentTable(tblInvoice);
			rsInvoiceAddUpdate = new CSRestService<>(urlInvoiceAddUpdate);
			rsInvoiceDetail = new CSRestService<>(urlInvoiceDetail);
			rsInvoiceDetail.setParentTable(tblInvoiceDetail);
			rsInvoiceDetailAddUpdateDelete = new CSRestService<>(urlInvoiceDetailAddUpdateDelete);
			rsInvoiceDetailSum = new CSRestService<>(urlInvoiceDetailSum);
			rsTransferOrder = new CSRestService<>(urlTransferOrder);
			rsTransferOrderAddUpdate = new CSRestService<>(urlTransferOrderAddUpdate);
			rsItemWarehouseAddUpdate = new CSRestService<>(urlItemWarehouseAddUpdate);
			
			rsClientAccountBalanceCard = new CSRestService<>(String.format(urlClientAccountBalanceCard, client.getId()));
			rsMaxClientInvoiceNumber = new CSRestService<>(urlMaxClientInvoiceNumber + client.getId().toString());
			rsTransferOrderByInvoiceDetailId = new CSRestService<>(urlTransferOrderDetail);
			rsTransferOrderByInvoiceDetailId.setParentTable(tblTransferOrderDetail);
			rsOrderFileImport = new CSRestService<>(urlOrderFileImport);
			rsInvoiceFileExport = new CSRestService<>(urlInvoiceFileExport);
			
			// prvo mora da dobije vrednost, jer proveravamo da li je null ili ne
			tblInvoiceDetail.setRestServiceDelete(rsInvoiceDetailAddUpdateDelete);
			
			rsInvoice.setUrl(urlInvoice + client.getId());
			rsInvoice.fetch(invoicePageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Invoice>>(){}, true, () -> {
				tblInvoice.setItems(rsInvoice.getDataAsObservableList());
			});
			
			tblInvoice.onDataNeeded(() -> {
				rsInvoice.fetch(invoicePageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Invoice>>(){}, () -> {
					tblInvoice.addItems(rsInvoice.getDataAsObservableList());
				});
			});
			
			tblInvoice.onRowDoubleClick((row) -> {
				tblInvoice.showDoubleClickDefaultAction = true;
			});
			
			tblInvoice.onRowSelectionChanged( (oldRow, newRow) -> {
				invoice = (Invoice) newRow;
				if(invoice != null) {
					invoiceDetailPageId.set(0);
					rsInvoiceDetail.setUrl(urlInvoiceDetail + invoice.getId());
					rsInvoiceDetail.fetch(invoiceDetailPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<InvoiceDetail>>(){});
					tblInvoiceDetail.setItems(rsInvoiceDetail.getDataAsObservableList());
					
					rsTransferOrder.setUrl(urlTransferOrder + invoice.getId());
					rsTransferOrder.fetch(new ParameterizedTypeReference<JsonResponse<TransferOrder>>(){}, () -> {
						if (rsTransferOrder.getData() != null && rsTransferOrder.getData().size() > 0) {
							transferOrder = rsTransferOrder.getData().get(0);
							taBlueField.setText(transferOrder.getDescription());
							dtDate.setValue(transferOrder.getDueDate() != null ? transferOrder.getDueDate().toLocalDate() : null);
							tfTime.setText(transferOrder.getDueDate() != null ? transferOrder.getDueDate().toLocalTime().toString() : null);
						} else {
							taBlueField.setText(null);
							dtDate.setValue(null);
							tfTime.setText(null);
						}
					});
					
					
					rsInvoiceDetailSum.setUrl(urlInvoiceDetailSum + invoice.getId());
					rsInvoiceDetailSum.fetch(new ParameterizedTypeReference<JsonResponse<InvoiceDetailSum>>(){}, () -> {
						tfTotalCount.setText(
								rsInvoiceDetailSum.getData().size() > 0 
								? ((InvoiceDetailSum)rsInvoiceDetailSum.getData().get(0)).getCnt().toString()
								: "0"
						);
					});

					tfTotalAmount.setText(invoice.getTotalAmt().toString());
					taInvoiceDescription.setText(invoice.getDescription());
					
					if (invoice.getInvoiceNumber() != null) {
						tfInvoiceId.setText(invoice.getInvoiceNumber().toString());
						tfDispatchNoteId.setText(invoice.getInvoiceNumber().toString());
						btnInvoiceApprove.setDisable(true);
						btnDeleteInvoiceDiscount.setDisable(true);
						btnDeleteItemDiscount.setDisable(true);
					} else {
						rsMaxClientInvoiceNumber.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>(){});
						tfInvoiceId.setText(
							!rsMaxClientInvoiceNumber.getData().isEmpty() 
								? rsMaxClientInvoiceNumber.getData().get(0).toString() 
								: "0"
						);
						tfDispatchNoteId.setText(
								!rsMaxClientInvoiceNumber.getData().isEmpty() 
									? rsMaxClientInvoiceNumber.getData().get(0).toString() 
									: "0"
						);
						btnInvoiceApprove.setDisable(false);
						btnDeleteInvoiceDiscount.setDisable(false);
						btnDeleteItemDiscount.setDisable(false);
					}

				}
			});
			
			tblInvoiceDetail.onRowSelectionChanged( (oldRow, newRow) -> {
				InvoiceDetail invoiceDetail = (InvoiceDetail) newRow;
				if (invoiceDetail != null) {
					rsTransferOrderByInvoiceDetailId.setUrl(urlTransferOrderDetail + invoiceDetail.getId().toString());
					rsTransferOrderByInvoiceDetailId.fetch(new ParameterizedTypeReference<JsonResponse<TransferOrderDetail>>(){}, () -> {
						tblTransferOrderDetail.setItems(rsTransferOrderByInvoiceDetailId.getDataAsObservableList());
					});
										
					btnTransferOrderPrint.setDisable(false);
					btnExcelExport.setDisable(false);
					btnInvoiceToFK.setDisable(false);
					btnAutoInvoiceLoad.setDisable(false);
					btnPreinvoice.setDisable(false);
					btnInvoice.setDisable(false);
					btnInvoiceNoYear.setDisable(false);
					btnInvoiceDispatchNote.setDisable(false);
					btnInvoiceDispatchNoteNoYear.setDisable(false);
					btnDispatchNote.setDisable(false);
					btnDispatchNoteNoPrices.setDisable(false);
					
				} else {
					btnTransferOrderPrint.setDisable(true);
					btnExcelExport.setDisable(true);
					btnInvoiceToFK.setDisable(true);
					btnAutoInvoiceLoad.setDisable(true);
					btnPreinvoice.setDisable(true);
					btnInvoice.setDisable(true);
					btnInvoiceNoYear.setDisable(true);
					btnInvoiceDispatchNote.setDisable(true);
					btnInvoiceDispatchNoteNoYear.setDisable(true);
					btnDispatchNote.setDisable(true);
					btnDispatchNoteNoPrices.setDisable(true);
				}
				
				rsInvoiceDetailSum.setUrl(urlInvoiceDetailSum + invoice.getId());
				rsInvoiceDetailSum.fetch(new ParameterizedTypeReference<JsonResponse<InvoiceDetailSum>>(){}, () -> {
					if (rsInvoiceDetailSum.getData() != null && rsInvoiceDetailSum.getData().size() > 0) {
						tfTotalCount.setText(((InvoiceDetailSum)rsInvoiceDetailSum.getData().get(0)).getCnt().toString());
					} else tfTotalCount.setText("0");
				});
				tfTotalAmount.setText(invoice.getTotalAmt().toString());
			});
			
			tblInvoiceDetail.onDataNeeded(() -> {
				rsInvoiceDetail.fetch(invoiceDetailPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<InvoiceDetail>>(){}, () -> {
					tblInvoiceDetail.addItems(rsInvoiceDetail.getDataAsObservableList());
				});
			});
			
			btnPreinvoice.setOnAction( e-> {
				buildInvoiceReportParams(1);
				new CSReportView("invoice", rptParamsInvoice);
			});
			
			btnInvoice.setOnAction( e-> {
				buildInvoiceReportParams(2);
				new CSReportView("invoice", rptParamsInvoice);
			});
			
			btnInvoiceNoYear.setOnAction( e-> {
				buildInvoiceReportParams(3);
				new CSReportView("invoice", rptParamsInvoice);
			});
			
			btnInvoiceDispatchNote.setOnAction( e-> {
				buildInvoiceReportParams(4);
				new CSReportView("invoice", rptParamsInvoice);
			});
			
			btnInvoiceDispatchNoteNoYear.setOnAction( e-> {
				buildInvoiceReportParams(5);
				new CSReportView("invoice", rptParamsInvoice);
			});
			
			btnDispatchNote.setOnAction( e-> {
				buildInvoiceReportParams(6);
				new CSReportView("invoice", rptParamsInvoice);
			});
			
			btnDispatchNoteNoPrices.setOnAction( e-> {
				buildInvoiceReportParams(6); //tODO
				new CSReportView("invoice", rptParamsInvoice);
			});
			
			rsClientAccountBalanceCard.fetch(new ParameterizedTypeReference<JsonResponse<ClientAccountBalanceCard>>(){}, () -> {
				tfAccountDebitAmt.setText(
						!rsClientAccountBalanceCard.getData().isEmpty() 
							? rsClientAccountBalanceCard.getData().get(0).getDebitAmt().toString() 
							: "0.00"
					);
					tfAccountCreditAmt.setText(
						!rsClientAccountBalanceCard.getData().isEmpty() 
							? rsClientAccountBalanceCard.getData().get(0).getCreditAmt().toString() 
							: "0.00"
					);
					tfAccountBalanceAmt.setText(
						!rsClientAccountBalanceCard.getData().isEmpty() 
							? rsClientAccountBalanceCard.getData().get(0).getBalanceAmt().toString() 
							: "0.00"
					);
			});
			
			//TODO: implement this!
			btnInvoiceApprove.setOnAction( e-> {
				invoice.setInvoiceNumber(tfInvoiceId.getTextAsInteger());
				invoice.setDispatchNoteId(tfDispatchNoteId.getTextAsInteger());
				Invoice updatedInvoice = rsInvoiceAddUpdate.addOrUpdate(invoice);
				if (updatedInvoice != null) {
					Common.ShowNotification("OVERA FAKTURE/OTPREMNICE", "Faktura/Otpremnica je overena.", false);
					btnInvoiceApprove.setDisable(true);
				}
				tblInvoice.tableView.refresh();
			});
			
			btnDeleteInvoiceDiscount.setOnAction( e-> {
				tblInvoiceDetail.tableView.getItems().forEach( id -> {
					deleteInvoiceDetailDiscount(id);
				});
				Common.ShowNotification("BRISANJE SVIH RABATA FAKTURE", "Podaci uspešno snimljeni!", false);
				tblInvoiceDetail.tableView.refresh();
			});
				
			btnDeleteItemDiscount.setOnAction( e-> {
				InvoiceDetail invoiceDetail = tblInvoiceDetail.getSelectedItem();
				InvoiceDetail updatedInvoiceDetail = deleteInvoiceDetailDiscount(invoiceDetail);
				if (updatedInvoiceDetail != null) {
					Common.ShowNotification("BRISANJE RABATA STAVKE", "Podaci uspešno snimljeni!", false);
					tblInvoiceDetail.tableView.refresh();
				}
			});
			
			btnTransferOrderPrint.setOnAction( e-> {
				if (tblTransferOrderDetail.getSelectedItem() != null) {
					buildTransferOrderReportParams(tblTransferOrderDetail.getSelectedItem().getTransferOrderId().intValue());
					new CSReportView("invoice_transfer_order", rptParamsTransferOrder);
				}
			});
			
			btnInvoiceToFK.setOnAction( e-> {
				String url = urlInvoiceFileExport.concat("1/");
				url = url.concat(tblInvoice.getSelectedItem() != null ? tblInvoice.getSelectedItem().getId().toString() : "0");
				rsInvoiceFileExport.setUrl(url);
				rsInvoiceFileExport.execute();
				Common.ShowNotification("EXPORT FAKTURE U FK", "XML fajl sa fakturom za finansijsko knjigovodstvo je generisan.", false);
			});
			
			btnXmlTehnomedia.setDisable(!client.getName().toLowerCase().contains("tehnomedia"));
			btnXmlTehnomedia.setOnAction( e-> {
				String url = urlInvoiceFileExport.concat("4/");
				url = url.concat(tblInvoice.getSelectedItem() != null ? tblInvoice.getSelectedItem().getId().toString() : "0");
				rsInvoiceFileExport.setUrl(url);
				rsInvoiceFileExport.execute();
				Common.ShowNotification("EXPORT TEHNOMEDIA FAKTURE U XML", "Tehnomedia XML fajl sa fakturom je generisan.", false);
			});

			btnXmlGigatron.setDisable(!client.getName().toLowerCase().contains("gigatron"));
			btnXmlGigatron.setOnAction( e-> {
				String url = urlInvoiceFileExport.concat("2/");
				url = url.concat(tblInvoice.getSelectedItem() != null ? tblInvoice.getSelectedItem().getId().toString() : "0");
				rsInvoiceFileExport.setUrl(url);
				rsInvoiceFileExport.execute();
				Common.ShowNotification("EXPORT GIGATRON FAKTURE U XML", "Gigatron XML fajl sa fakturom je generisan.", false);
			});
			
			btnExcelExport.setOnAction( e-> {
				String url = urlInvoiceFileExport.concat("3/");
				url = url.concat(tblInvoice.getSelectedItem() != null ? tblInvoice.getSelectedItem().getId().toString() : "0");
				rsInvoiceFileExport.setUrl(url);
				rsInvoiceFileExport.downloadFile();
				Common.ShowNotification("EXPORT FAKTURE U EXCEL", "XLSX fajl sa fakturom je generisan.", false);
			});
			
			/**
			 * AUTOMATIC ORDER FILE IMPORT
			 * Enable invoice autoload only for
			 * Gigatron, parentID: 7493
			 * Tehnomanija, parentID: 5047
			 * Tehnomedia, parentID: 8847
			 * Telekom, parentID: 3415
			*/
			int[] parentID = {7493, 5047, 3415, 8847};
			if (IntStream.of(parentID).anyMatch(x -> x == client.getParentId())) {
				btnAutoInvoiceLoad.setVisible(true);
			}
			
			switch (client.getParentId()) {
				case 7493:
					rsOrderFileImport.setUrl(urlOrderFileImport + "1");
					orderFileExtension = "xlsx";
				break;
				case 5047:
					rsOrderFileImport.setUrl(urlOrderFileImport + "2/" + client.getId());
					orderFileExtension = "xls";
				break;
				case 8847:
					rsOrderFileImport.setUrl(urlOrderFileImport + "3");
					orderFileExtension = "xlsx";
				break;
				case 3415:
					rsOrderFileImport.setUrl(urlOrderFileImport + "4");
					orderFileExtension = "xlsx";
				break;
			}
			
			btnAutoInvoiceLoad.setOnAction( e-> {
				try {
					File file = Common.getFileChooser(orderFileExtension);
	
					if (file != null) {
						/**
						 * Necemo da proveravamo u app.
						 * Dozvolicemo useru da odabere Tehnomanija prodavnicu u F3, zatim da ode na veleprodaju
						 * i tu ucita fajl kad aklikne na automatska faktura.
						 * App ce iz forme da preuzme CLIENT_ID
						if (client.getParentId() == 5047) {  // tehnomanija
						    String strClientId = file.getName().substring(0, file.getName().indexOf("_"));
							if (strClientId == null || !strClientId.chars().allMatch(Character::isDigit)) {
								throw new Exception("Ime fajla ne sadrzi sifru klijenta na pocetku naziva odvojenu karakterom '_'");
							}
					    }
						*/

						Common.showProgressBar("AUTOMATSKI IMPORT ORDERA");
						Common.ShowNotification("AUTOMATSKI IMPORT ORDERA", "Import je započet, molim sačekajte.", false);
						rsOrderFileImport.postUpload(file, ()->{
							Common.ShowNotification("AUTOMATSKI IMPORT ORDERA", "Fajl sa orderima je uspešno procesiran.", false);
							Common.hideProgressBar();
						});
					}
				} catch (Exception ex) {
					Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme\n" + ex, true);
					Common.logMessage(getClass(), ex, "ERROR");
				}
			});
			
			btnSaveTransferOrderDueDateAndDescription.setOnAction( e-> {
				transferOrder.setDueDate(LocalDateTime.of(
					dtDate.getValue() != null 
						? dtDate.getValue() 
						: LocalDate.now(), 
					LocalTime.parse(tfTime.getText() != null 
						? tfTime.getText() 
						: "18:00"))
				);
				transferOrder.setDescription(taBlueField.getText());
				TransferOrder updatedTransferOrder = rsTransferOrderAddUpdate.addOrUpdate(transferOrder);
				
				invoice.setDescription(taInvoiceDescription.getText());
				Invoice updatedInvoice = rsInvoiceAddUpdate.addOrUpdate(invoice);
				
				if (updatedTransferOrder != null && updatedInvoice != null) {
					Common.ShowNotification("SNIMANJE ROKA I PLAVOG POLJA", "Podaci uspešno snimljeni!", false);
					dtDate.setValue(LocalDate.from(updatedTransferOrder.getDueDate()));
					tfTime.setText(LocalTime.from(updatedTransferOrder.getDueDate()).toString());
				}
			});
			
			
			// delete transfer order detail too
			tblInvoiceDetail.onRowDeleted((deletedItem) -> {
				InvoiceDetail id = (InvoiceDetail) deletedItem;
				tblInvoice.getSelectedItem().getInvoiceDetail().remove(id);
				tblInvoice.refresh();

				// vracamo na stanje ono sto je bilo "prodato"
				rsItemWarehouseAddUpdate.execute(id.getWarehouseId().toString(), id.getItemId().toString(), id.getItemQty().toString());
				
			});
			
			tblInvoiceDetail.onRowDoubleClick((row) -> {
				tblInvoiceDetail.showDoubleClickDefaultAction = true;
			});
			
			btnElectronicInvoice.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Finance_ElectronicInvoiceController", null, 0), "E - Fakture", btnElectronicInvoice.getGraphic(), ctx);
			});
			
			btnF6.setOnAction( e-> {
				common.closeForm(btnF6.getText());
				common.displayForm(ControllerFactory.getController("Items_ItemPerCategoryController", client, 0), btnF6.getText(), btnF6.getGraphic(), ctx);
			});
			
			btnClientChange.setOnAction( e -> {
				Client clientSelected = (Client) Common.displayForm(ControllerFactory.getController("Sales_ClientSelectController", null, 0), btnClientChange, "Odabir klijenta");
				if (clientSelected != null) {
					client = clientSelected;
					tblInvoice.clear();
					invoicePageId.set(0);
					invoiceDetailPageId.set(0);
					tfInvoiceId.clear();
					tfDispatchNoteId.clear();
					taBlueField.clear();
					taInvoiceDescription.clear();
					dtDate.setValue(null);
					tfTime.clear();
					tfTotalCount.clear();
					tfTotalAmount.clear();
					this.initialize();
				}
			});

			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
	private void buildInvoiceReportParams(Integer reportTypeId) {
		rptParamsInvoice.clear();
		lstRptConfigInvoice.clear();
		lstRptConfigInvoice.addAll(Arrays.asList(
					reportTypeId.toString(), 
					chkDisplayDiscount.isSelected() ? "1" : "0",
					client.getCountry().getName().equals("SRBIJA") ? "0" : "1",
					((RadioButton)tgpPrimerak.getSelectedToggle()).getText().equals("Ništa")
						? ""
						: ((RadioButton)tgpPrimerak.getSelectedToggle()).getText().toUpperCase(),
					chkWithBarcode.isSelected() ? "1" : "0"
				)
		); // report tip (predracun, racun) | print_vat_rate | poresko oslobodjenje ako je inostrani kupac, KOPIRA - ORIGINAL, barcode
		rptParamsInvoice.put("REPORT_CONFIG", lstRptConfigInvoice);
		rptParamsInvoice.put("JSON_CLIENT_URL", baseUrl + "/client/" + client.getId());
		rptParamsInvoice.put("JSON_INVOICE_HEADER_URL", baseUrl + "/invoice/" + invoice.getId());				
		rptParamsInvoice.put("JSON_INVOICE_DETAIL_URL", baseUrl + "/invoiceDetail/allPageableByInvoiceId/" + invoice.getId() + "/0");
		rptParamsInvoice.put("JSON_DELIVERY_ADDRESS_URL", baseUrl + "/deliveryAddress/deliveryAddressByClientId/" + client.getId());
	}
	
	private void buildTransferOrderReportParams(Integer transferOrderId) {
		rptParamsTransferOrder.clear();
		lstRptConfigTransferOrder.clear();
		rptParamsTransferOrder.put("REPORT_CONFIG", null); // ne koristimo u ovom reportu, ali imamo zbog toga da su svi reporti isti
		rptParamsTransferOrder.put("JSON_CLIENT_URL", baseUrl + "/client/" + client.getId());
		rptParamsTransferOrder.put("JSON_INVOICE_URL", baseUrl + "/invoice/" + invoice.getId());
		rptParamsTransferOrder.put("JSON_TRANSFER_ORDER_URL", baseUrl + "/transferOrder/allByInvoiceId/" + invoice.getId());
		rptParamsTransferOrder.put("JSON_TRANSFER_ORDER_DETAIL_URL", baseUrl + "/transferOrderDetail/findAllByTransferOrderId/" + transferOrderId);
	}
	
	/*** REMOVE in next release
	@Deprecated
	private File getInvoiceAutoImportFile_toDelete() {
		List<ExtensionFilter> extensionFilters;
    	extensionFilters = new ArrayList<>();
    	extensionFilters.add(new ExtensionFilter("EXCEL", "*.xlsx"));
    	
        FileChooser fileChooser = new FileChooser();
        //File directory = new File(null != initialDirectory ? initialDirectory : "");
        //if (!directory.exists()) directory = new File("");
        //fileChooser.setInitialDirectory(directory);
        //fileChooser.setInitialFileName(initialFileName);
        fileChooser.setTitle("Odaberite EXCEL (samo xlsx) fajl za generisanje automatskih faktura");
        fileChooser.getExtensionFilters().setAll(extensionFilters);

        File file = fileChooser.showOpenDialog(tblInvoice.getScene().getWindow());
        return file;
        //if (null == file) return;
	}
	***/
	
	private InvoiceDetail deleteInvoiceDetailDiscount(InvoiceDetail invoiceDetail) {
		invoiceDetail.setDiscountRate(0);
		invoiceDetail.setAmountDiscount(BigDecimal.ZERO);
		// ((i.UNIT_OF_MEASURE_NET_VALUE * i.ITEM_QTY) - i.AMOUNT_DISCOUNT * i.ITEM_QTY) * (i.VAT_RATE /100
		invoiceDetail.setAmountVat(
			invoiceDetail.getUnitOfMeasureNetValue().multiply(BigDecimal.valueOf(invoiceDetail.getItemQty()))
			.multiply(BigDecimal.valueOf(invoiceDetail.getVatRate() / 100.00))
		);
		// (i.UNIT_OF_MEASURE_NET_VALUE * i.ITEM_QTY) - i.AMOUNT_DISCOUNT * i.ITEM_QTY + i.AMOUNT_VAT calc_gross
		invoiceDetail.setAmountGross(
			invoiceDetail.getUnitOfMeasureNetValue().multiply(BigDecimal.valueOf(invoiceDetail.getItemQty()))
			.add(invoiceDetail.getAmountVat()) // vat amount smo izracunali gore, tako da mozemo odavde da ga uzmemo				
		);
		// (i.UNIT_OF_MEASURE_NET_VALUE * i.ITEM_QTY) - i.AMOUNT_DISCOUNT * i.ITEM_QTY calc_net
		invoiceDetail.setAmountNet(
			invoiceDetail.getUnitOfMeasureNetValue().multiply(BigDecimal.valueOf(invoiceDetail.getItemQty()))
		);
		invoiceDetail.setLastModifiedDate(LocalDateTime.now());
		
		InvoiceDetail updatedInvoiceDetail = rsInvoiceDetailAddUpdateDelete.addOrUpdate(invoiceDetail);
		
		return updatedInvoiceDetail;
		/*
		if (updatedInvoiceDetail != null) {
			Common.ShowNotification("BRISANJE RABATA STAVKE", "Podaci uspešno snimljeni!", false);
			tblInvoiceDetail.tableView.refresh();
		}
		*/
	}
}
