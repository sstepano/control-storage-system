package org.code_studio.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSReportView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.Brand;
import org.code_studio.database.Client;
import org.code_studio.database.Offer;
import org.code_studio.database.OfferDetail;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public class Sales_OfferController extends BaseController {
	
	@FXML private CSTable <Offer> tblOffer;
	@FXML private CSTable <OfferDetail> tblOfferDetail;
	@FXML private CSTextField tfClientId;
	@FXML private CSTextField tfName;
	@FXML private CSTextField tfFullName;
	@FXML private CSTextField tfPOBox;
	@FXML private CSTextField tfCity;
	@FXML private CSTextField tfAddress;
	@FXML private CSTextField tfCountry;
	@FXML private CSTextField tfLimitEur;
	@FXML private CSTextField tfDebitAmt;
	@FXML private Label       lblClientId;
	@FXML private Label       lblClientName;
	@FXML private Label       lblClientFullName;
	@FXML private Label       lblClientAddress;
	@FXML private TextArea    taClientDescription;
	@FXML private CSTextField tfTotalCount;
	@FXML private CSTextField tfTotalAmount;
	@FXML private TextArea    taOfferDescription;
	@FXML private CSDatePicker dtDate;
	@FXML private CSTextField  tfTime;
	
	@FXML private Button btnExcelExport;
	@FXML private Button btnXmlTehnomedia;
	@FXML private Button btnXmlGigatron;
	@FXML private Button btnAutoOfferLoad;
	@FXML private Button btnBrandOffer;
	
	@FXML private CheckBox ckbRegularPrice;
	@FXML private CheckBox ckbSpecialPrice;
	@FXML private CheckBox ckbDiscount;
	@FXML private CheckBox ckbNetAmt;
	@FXML private CheckBox ckbGrossAmt;
	@FXML private CheckBox ckbImage;
	
	@FXML private CSTextField tfAccountDebitAmt;
	@FXML private CSTextField tfAccountCreditAmt;
	@FXML private CSTextField tfAccountBalanceAmt;
	
	@FXML private CSTextField tfOfferId;
	@FXML private CSTextField tfDispatchNoteId;
	@FXML private Button      btnOfferApprove;

	@FXML private VBox vbButtonsContainer;
	@FXML private Button btnF6;
	
	@FXML private Button btnDeleteOfferDiscount;
	@FXML private Button btnDeleteItemDiscount;
	
	@FXML private Button btnPrintOffer;

	private Client client;
	
	@SuppressWarnings("unused")
	private int mode;
	private ApplicationContext ctx;
	private Common common;
	private String baseUrl;
	private Offer offer;
	
	HashMap<String, Object> rptParamsOffer;
	List<String> lstRptConfigOffer;
	
	private final String tblOfferAddEditControllerName = "Sales_OfferAddEditController";
	private final String tblOfferDetailAddEditControllerName = "Sales_OfferDetailAddEditController";
	
	private String urlOffer = "/offer/allPageableByClientId/";
	private CSRestService<Offer> rsOffer;
	private AtomicInteger OfferPageId;
	
	private final String urlOfferAddUpdate = "/offer";
	private CSRestService<Offer> rsOfferAddUpdate;
	
	private final String urlOfferDetail = "/offerDetail/allByOfferId/";
	private CSRestService<OfferDetail> rsOfferDetail;
	private AtomicInteger OfferDetailPageId;
	
	private final String urlOfferDetailAddUpdateDelete = "/offerDetail";
	private CSRestService<OfferDetail> rsOfferDetailAddUpdateDelete;
	

	public Sales_OfferController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		this.ctx = ctx;
		this.common = ctx.getBean(Common.class);
		this.baseUrl = Common.applicationProperties.getProperty("api.serverurl");
		
		client = (Client) controllerParam;
		OfferPageId = new AtomicInteger(0);
		OfferDetailPageId = new AtomicInteger(0);
		
		rptParamsOffer = new HashMap<>();
		lstRptConfigOffer = new ArrayList<>();
	}
	
	public void initialize() {
		try {
			rsOffer = new CSRestService<>(urlOffer);
			rsOfferAddUpdate = new CSRestService<>(urlOfferAddUpdate);
			rsOfferDetail = new CSRestService<>(urlOfferDetail);
			rsOfferDetailAddUpdateDelete = new CSRestService<>(urlOfferDetailAddUpdateDelete);
			rsOffer.setParentTable(tblOffer);
			tblOffer.setRestServiceDelete(rsOfferAddUpdate);
			rsOfferDetail.setParentTable(tblOfferDetail);
			tblOfferDetail.setParentTable(tblOffer);

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

			List<Object> lstOfferParams = new ArrayList<>();
			lstOfferParams.add(tblOffer);
			lstOfferParams.add(client);
			lstOfferParams.add(taOfferDescription); 
			tblOffer.setAddEditDialog(tblOfferAddEditControllerName, lstOfferParams);
			
			List<Object> lstOfferDetailParams = new ArrayList<>();
			lstOfferDetailParams.add(tblOfferDetail);
			lstOfferDetailParams.add(tblOffer);
			lstOfferDetailParams.add(client);
			tblOfferDetail.setAddEditDialog(tblOfferDetailAddEditControllerName, lstOfferDetailParams);
			
			// prvo mora da dobije vrednost, jer proveravamo da li je null ili ne
			tblOfferDetail.setRestServiceDelete(rsOfferDetailAddUpdateDelete);
			
			rsOffer.setUrl(urlOffer + client.getId());
			rsOffer.fetch(OfferPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Offer>>(){}, true, () -> {
				tblOffer.setItems(rsOffer.getDataAsObservableList());
			});

			tblOffer.onDataNeeded(() -> {
				rsOffer.fetch(OfferPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Offer>>(){}, false, () -> {
					tblOffer.addItems(rsOffer.getDataAsObservableList());
				});
			});
			
			tblOffer.onRowDoubleClick((row) -> {
				tblOffer.showDoubleClickDefaultAction = true;
			});
			
			tblOffer.onRowSelectionChanged( (oldRow, newRow) -> {
				offer = (Offer) newRow;
				if(offer != null) {
					OfferDetailPageId.set(0);
					rsOfferDetail.setUrl(urlOfferDetail + offer.getId());
					rsOfferDetail.fetch(new ParameterizedTypeReference<JsonResponse<OfferDetail>>(){}, true, () -> {
						tblOfferDetail.setItems(rsOfferDetail.getDataAsObservableList());
					});
					taOfferDescription.setText(offer.getDescription());
				} else {
					taOfferDescription.setText(null);
				}
			});
			
			tblOfferDetail.onRowSelectionChanged( (oldRow, newRow) -> {
				OfferDetail OfferDetail = (OfferDetail) newRow;
				if (OfferDetail != null) {
					btnPrintOffer.setDisable(false);
					vbButtonsContainer.setDisable(false);
					btnDeleteOfferDiscount.setDisable(false);
					btnDeleteItemDiscount.setDisable(false);
				} else {
					btnPrintOffer.setDisable(true);
					vbButtonsContainer.setDisable(true);
					btnDeleteOfferDiscount.setDisable(true);
					btnDeleteItemDiscount.setDisable(true);
				}
			});
			
			tblOfferDetail.onDataNeeded(() -> {
				rsOfferDetail.fetch(OfferDetailPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<OfferDetail>>(){}, () -> {
					tblOfferDetail.addItems(rsOfferDetail.getDataAsObservableList());
				});
			});
			
			
			/*** TODO: Iskoristiti ovo za zakljucenje ponude
			btnOfferApprove.setOnAction( e-> {
				Offer.setOfferNumber(tfOfferId.getTextAsInteger());
				//Offer.setDispatchNoteId(tfDispatchNoteId.getTextAsInteger());
				Offer updatedOffer = rsOfferAddUpdate.addOrUpdate(Offer);
				if (updatedOffer != null) {
					Common.ShowNotification("OVERA FAKTURE/OTPREMNICE", "Faktura/Otpremnica je overena.", false);
					btnOfferApprove.setDisable(true);
				}
				tblOffer.tableView.refresh();
			});
			***/
			
			/***
			btnDeleteItemDiscount.setOnAction( e-> {
				OfferDetail OfferDetail = tblOfferDetail.getSelectedItem();
				//OfferDetail updatedOfferDetail = deleteOfferDetailDiscount(OfferDetail);
				if (updatedOfferDetail != null) {
					Common.ShowNotification("BRISANJE RABATA STAVKE", "Podaci uspešno snimljeni!", false);
					tblOfferDetail.tableView.refresh();
				}
			});
			***/
	
			
			
			// delete transfer order detail too
			tblOfferDetail.onRowDeleted((deletedItem) -> {
				@SuppressWarnings("unused")
				OfferDetail id = (OfferDetail) deletedItem;
				//tblOffer.getSelectedItem().getOfferDetail().remove(id);
				tblOffer.refresh();

				// vracamo na stanje ono sto je bilo "prodato"
				//rsItemWarehouseAddUpdate.execute(id.getWarehouseId().toString(), id.getItemId().toString(), id.getItemQty().toString());
				
			});
			
			/***/
			
			btnPrintOffer.setOnAction( e-> {
				buildReportParams(4);
				new CSReportView("offer", rptParamsOffer);
			});
			
			btnBrandOffer.setOnAction( e-> {
				@SuppressWarnings("unused")
				Brand selectedItem = (Brand) Common.displayForm(ControllerFactory.getController("Lookup_BrandController", null, 0), btnBrandOffer, "Odabir brenda");
			});
			
			btnF6.setOnAction( e-> {
				common.closeForm(btnF6.getText());
				common.displayForm(ControllerFactory.getController("Items_ItemPerCategoryController", client, 0), btnF6.getText(), btnF6.getGraphic(), ctx);
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
	
	
	private void buildReportParams(Integer reportTypeId) {
		rptParamsOffer.clear();
		lstRptConfigOffer.clear();
		lstRptConfigOffer.addAll(Arrays.asList(
				offer.getOfferHeaderText() == null
				? "PONUDA " + offer.getId()
				: offer.getOfferHeaderText()
				, ckbRegularPrice.isSelected()
				  ? "1"
				  : "0"
				, ckbSpecialPrice.isSelected()
				  ? "1"
				  : "0"
				, ckbDiscount.isSelected()
				  ? "1"
				  : "0"
				, ckbNetAmt.isSelected()
				  ? "1"
				  : "0"
				, ckbGrossAmt.isSelected()
				  ? "1"
				  : "0"
				, ckbImage.isSelected()
				  ? "1"
				  : "0"
			)
		);
		rptParamsOffer.put("REPORT_CONFIG", lstRptConfigOffer);
		rptParamsOffer.put("JSON_CLIENT_URL", baseUrl + "/client/" + client.getId());
		rptParamsOffer.put("JSON_HEADER_URL", baseUrl + "/offer/" + offer.getId());				
		rptParamsOffer.put("JSON_DETAIL_URL", baseUrl + "/offerDetail/allByOfferId/" + offer.getId());
		
		System.out.println(rptParamsOffer.get("JSON_CLIENT_URL"));
		System.out.println(rptParamsOffer.get("JSON_HEADER_URL"));
		System.out.println(rptParamsOffer.get("JSON_DETAIL_URL"));
	}


}
