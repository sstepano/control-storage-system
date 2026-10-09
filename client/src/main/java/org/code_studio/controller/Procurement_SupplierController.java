package org.code_studio.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.Bank;
import org.code_studio.database.BillOfExchange;
import org.code_studio.database.Client;
import org.code_studio.database.ClientBankAccount;
import org.code_studio.database.ClientCategory;
import org.code_studio.database.ClientContact;
import org.code_studio.database.ClientContract;
import org.code_studio.database.ClientGroup;
import org.code_studio.database.Composite_BankBOFContract;
import org.code_studio.database.DeliveryAddress;
import org.code_studio.database.Division;
import org.code_studio.database.SalePlanner;
import org.code_studio.main.Common;
import org.code_studio.main.JfxMainApplication;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Procurement_SupplierController extends BaseController implements Initializable {

	@FXML private CSTable <Client> tblClient;
	@FXML private CSTable <ClientContact> tblClientContact;
	@FXML private CSTable <DeliveryAddress> tblDeliveryAddress;
	@FXML private CSTable <Composite_BankBOFContract> bankBillContractTable;
	@FXML private CSTable <SalePlanner> tblSalePlanner;
	@FXML private TextArea txtaClientNote;
	@FXML private Label lblNameAndSurname;
	@FXML private TextArea txtaContactNote;
	@FXML private Button btnClientNoteEdit;
	@FXML private Button btnContactNoteEdit;
	@FXML private TextField tfSearchBox;
	@FXML private CSComboBox<ClientGroup> cbClientGroup;
	@FXML private CSComboBox<ClientCategory> cbClientCategory;
	@FXML private Label lblClientNote;
	@FXML private Button btnItems;
	@FXML private TextField tfParentId;
	@FXML private TextField tfSalesOfficerName;
	
	@FXML private Button btnSalePlanner;
	@FXML private Button btnWholesale;
	@FXML private Button btnOffer;
	@FXML private Button btnVirman;
	@FXML private Button btnPhotograph;
	@FXML private Button btnPerCommercialist;
	@FXML private Button btnWorkplan;
	@FXML private Button btnRevers;
	@FXML private Button btnSalesReservation;
	@FXML private Button btnWholesaleReturn;
	@FXML private Button btnCard;
	@FXML private Button btnSaleStore;
	@FXML private Button btnCommissionTransferOrder;
	@FXML private Button btnCommissionBill;
	@FXML private Button btnDomPricelist;
	@FXML private CSComboBox<Division> cbDivision;
	@FXML private Button btnGoodsIn;
	@FXML private Button btnGoodsOut;
	@FXML private Button btnContactsEmail;
	@FXML private Button btnSalePlannerEdit;
	
	@FXML private CSTextField tfCurrency;
	@FXML private CSTextField tfLimitEur;
	@FXML private CSTextField tfAccountBalance;
	@FXML private CSTextField tfPanelsGiven;
	@FXML private CSTextField tfContractNumber;
	@FXML private CSTextField tfContractValidFrom;
	@FXML private CSTextField tfContractValidTo;
	@FXML private CSTextField tfBillOFExchangeNumber;
	@FXML private CSTextField tfBillOFExchangeValue;
	@FXML private CheckBox ckbIsVat;
	
	private ApplicationContext ctx;
	private Common common;
	
	private final String urlClientAll = "/supplier/allPageable";
	private final String urlClient = "/supplier/allPageableByDivisionId/";
	private final String searchUrl = "/supplier/search/";
	private final String urlClientContact = "/clientContact/allByClientId/";
	private final String salePlannerUrl = "/salePlanner/allByClientId/";
	private AtomicInteger salePlannerPageId = new AtomicInteger(0);
	
	private final String urlClientGroup = "/clientGroup/allByIsSupplierGroup/true";
	private final String urlClientCategory = "/clientCategory";
	
	//delete svc
	private final String urlClientContactDelete = "/clientContact";
	private final String urlDeliveryAddressDelete = "/deliveryAddress";
	
	CSRestService <Client> rsClient;
	CSRestService <Client> restsvcClientSearch;
	CSRestService <Client> cbRestService;
	CSRestService <ClientGroup> rsClientGroup;
	CSRestService <ClientCategory> rsClientCategory;
	CSRestService <SalePlanner> rsSalePlanner;
	CSRestService <ClientContact> rsClientContact;
	CSRestService<Division> rsDivision;
	AtomicInteger pageId;
	
	// svc delete
	CSRestService <ClientContact> rsClientContactDelete;
	CSRestService <DeliveryAddress> rsDeliveryAddressDelete;
	
	private final String urlDeliveryAddress = "/deliveryAddress/allCompositeByClientId/";
	CSRestService <DeliveryAddress> rsDeliveryAddress;
	
	String bankName = null;
	String billOfExchangeName = null;
	String contractName = null;
	ObservableList<Composite_BankBOFContract> cData;
	
	public Procurement_SupplierController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
		this.common = ctx.getBean(Common.class); 
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		try {
			rsClient = new CSRestService<>(urlClient);
			pageId = new AtomicInteger(0);
			restsvcClientSearch = new CSRestService<>(searchUrl);
			rsClientGroup = new CSRestService<>(urlClientGroup);
			rsClientCategory = new CSRestService<>(urlClientCategory);
			rsSalePlanner = new CSRestService<>(salePlannerUrl);
			rsClientContact = new CSRestService<>(urlClientContact);
			rsDeliveryAddress = new CSRestService<>(urlDeliveryAddress);
			
			rsClientContactDelete = new CSRestService<>(urlClientContactDelete);
			rsDeliveryAddressDelete = new CSRestService<>(urlDeliveryAddressDelete);
						
			MainController ctrl = ctx.getBean(MainController.class);
			rsDivision = new CSRestService<>("/division");
			rsDivision.fetch(new ParameterizedTypeReference<JsonResponse<Division>>() {});
	
			cbDivision.getItems().addAll(rsDivision.getDataAsObservableList());
			cbDivision.getSelectionModel().selectFirst(); // selektujemo prvi po defaultu
			cbDivision.onSelectionChanged((newItem) -> {
				pageId.set(0);
				rsClient.setUrl(urlClient + cbDivision.getSelectionModel().getSelectedItem().getId().toString());
				rsClient.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
				tblClient.setItems(rsClient.getDataAsObservableList());
				
				cbClientGroup.getSelectionModel().selectFirst();
				cbClientCategory.getSelectionModel().selectFirst();
			});
			
			// MAIN TABLE START
			List<Object> lstTblClientAdditionalParams = new ArrayList<>();
			lstTblClientAdditionalParams.add(tblClient);
			lstTblClientAdditionalParams.add(txtaClientNote);
			tblClient.setAddEditDialog("Sales_ClientAddEditController", lstTblClientAdditionalParams);
			tblClient.onRowDoubleClick( rowData -> {
				tblClient.showDoubleClickDefaultAction = true;
			});
			
			rsClient.setUrl(urlClient + cbDivision.getSelectionModel().getSelectedItem().getId().toString());
			rsClient.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.setItems(rsClient.getDataAsObservableList());
	
			tblClient.onDataNeeded(() -> {
				rsClient.fetch(pageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
				tblClient.addItems(rsClient.getDataAsObservableList());
			});
			
			//hack za local variable in enclosing scope must be final (koristim array i tada moze)
			ClientBankAccount[] clientBankAccount = new ClientBankAccount[1];
			BillOfExchange[] clientBillOfExchange = new BillOfExchange[1];
			ClientContract[] clientContract = new ClientContract[1];
			
			tblClient.onRowSelectionChanged((oldRow, newRow) -> {
				Client newRowData = (Client) newRow;
				
				if (newRowData != null) {
					//hbox sa detaljima
					//tfCurrency.setText(newRowData.getCurrency());
					tfLimitEur.setText(newRowData.getlimitEur().toString());
					tfAccountBalance.setText(newRowData.getAccountBalance().toString());
					tfPanelsGiven.setText(newRowData.getPanelsGiven().toString());
					tfContractNumber.setText(newRowData.getClientContracts().size() > 0 ? newRowData.getClientContracts().get(0).getContractNumber().toString() : "");
					tfContractValidFrom.setText(newRowData.getClientContracts().size() > 0 && newRowData.getClientContracts().get(0).getValidFrom() != null ? newRowData.getClientContracts().get(0).getValidFrom().toString() : "");
					tfContractValidTo.setText(newRowData.getClientContracts().size() > 0 && newRowData.getClientContracts().get(0).getValidTo() != null ? newRowData.getClientContracts().get(0).getValidTo().toString() : "");
					tfBillOFExchangeNumber.setText(newRowData.getBillOfExchanges().size() > 0 ? newRowData.getBillOfExchanges().get(0).getBillOfExchangeNumber().toString() : "");
					tfBillOFExchangeValue.setText(newRowData.getBillOfExchanges().size() > 0 ? newRowData.getBillOfExchanges().get(0).getBillOfExchangeValue().toString() : "0.00");
					ckbIsVat.setSelected(newRowData.getIsVat());
					
					txtaClientNote.setText(newRowData.getDescription());
					lblNameAndSurname.setText("IME I PREZIME");
					txtaContactNote.clear();
					
					// DELIVERY ADDRESS
					tblDeliveryAddress.setRestServiceDelete(rsDeliveryAddressDelete);
					rsDeliveryAddress.setUrl(urlDeliveryAddress + newRowData.getId());
					rsDeliveryAddress.fetch(new ParameterizedTypeReference<JsonResponse<DeliveryAddress>>(){});
					tblDeliveryAddress.setItems(rsDeliveryAddress.getDataAsObservableList());
					
					// CLIENT CONTACT
					tblClientContact.setRestServiceDelete(rsClientContactDelete);
					rsClientContact.setUrl(urlClientContact + newRowData.getId().toString());
					rsClientContact.fetch(new ParameterizedTypeReference<JsonResponse<ClientContact>>() {});
					tblClientContact.setItems(rsClientContact.getDataAsObservableList());
					btnContactNoteEdit.setDisable(rsClientContact.getDataAsObservableList().isEmpty());
	
					//Make composite objects for bank/bill/contracts table
					//TODO: Remove name zakuc-s
					bankName = null;
					billOfExchangeName = null;
					contractName = null;
					
					if (newRowData.getClientBankAccounts().size() > 0) {
						// Some clients have accounts that are NOT connected to banks. This is fix for those cases.
						Bank bank = newRowData.getClientBankAccounts().get(0).getBank();
						clientBankAccount[0] = newRowData.getClientBankAccounts().get(0);
						bankName = bank != null ? bank.getName() : "NEPOVEZAN RACUN";
						bankName = bankName + ": " + newRowData.getClientBankAccounts().get(0).getAccountNumber();
					} else {
						clientBankAccount[0] = null;
					}
					
					if (newRowData.getBillOfExchanges().size() > 0) {
						billOfExchangeName = newRowData.getBillOfExchanges().get(0).getBillOfExchangeNumber();
						clientBillOfExchange[0] = newRowData.getBillOfExchanges().get(0);
					} else {
						clientBillOfExchange[0] = null;
					}
					
					if (newRowData.getClientContracts().size() > 0) {
						contractName = newRowData.getClientContracts().get(0).getContractNumber();
						clientContract[0] = newRowData.getClientContracts().get(0);
					} else {
						clientContract[0] = null;
					}
					
					cData = FXCollections.observableArrayList(
							new Composite_BankBOFContract("BANKA", bankName),
							new Composite_BankBOFContract("MENICA", billOfExchangeName),
							new Composite_BankBOFContract("UGOVOR", contractName)
					);
					bankBillContractTable.setItems(cData);
	
					tfParentId.setText(newRowData.getParentId().toString());
					tfSalesOfficerName.setText(newRowData.getPrimarySaleOfficer().get(0).getApplicationUser().getName());
					
					//SALE PLANNER TABLE
					rsSalePlanner.setUrl(salePlannerUrl + newRowData.getId().toString());
					salePlannerPageId.set(0);
					rsSalePlanner.fetch(salePlannerPageId.get(), new ParameterizedTypeReference<JsonResponse<SalePlanner>>() {});
					tblSalePlanner.setItems(rsSalePlanner.getDataAsObservableList());
					//tblSalePlanner.setAddEditControllerName("SalesClientsAddEdit");
					tblSalePlanner.onRowDoubleClick( e-> {
						tblSalePlanner.showDoubleClickDefaultAction = true;
					});
	
				}  else { // if (newRowData != null) { END
					tfParentId.setText(null);
					tfSalesOfficerName.setText(null);
				}
			});
			
			//local search: mainTable.addSearchListener("Name");
			//TODO: Figure out how to move this method to CSTable.
			// Now I cannot use it because <T> cannot be cast in BaseService and returns jibberish
			tblClient.onServerSearch(()->{
				String searchKeyword = tblClient.tfSearchBox.getText();
				
				if (searchKeyword.length() > 0) {
					restsvcClientSearch.setUrl(searchUrl + searchKeyword);
					restsvcClientSearch.fetch(0, new ParameterizedTypeReference<JsonResponse<Client>>() {});
					tblClient.setItems(restsvcClientSearch.getDataAsObservableList());
				} else {
					//search empty, fetch all data, like when opening form for the first time
					pageId.set(0);
					rsClient.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
					tblClient.setItems(rsClient.getDataAsObservableList());
				}
			});
			// MAIN TABLE END
			
			// ADDRESS TABLE START
			//addressTable.setAddEditControllerName("DeliveryAddressAddEditController"); //OVO moze da se stavi i U FXML
			tblDeliveryAddress.setParentTable(tblClient);
			tblDeliveryAddress.setAddEditDialog("Lookup_DeliveryAddressController");
			tblDeliveryAddress.onRowDoubleClick((rowData) -> {
				tblDeliveryAddress.showDoubleClickDefaultAction = true;
			});
			// ADDRESS TABLE END
			
			// CLIENT CONTACT TABLE START
			tblClientContact.setParentTable(tblClient);
			tblClientContact.setAddEditDialog("Sales_ClientContactAddEditController");
			//contactsTable.setAddEditControllerName("Sales_ClientContactAddEditController");
			tblClientContact.onRowDoubleClick( e-> {
				tblClientContact.showDoubleClickDefaultAction = true;
			});
			tblClientContact.onRowSelectionChanged( (oldRow, newRow) -> {
				if (newRow != null) {
					ClientContact newRowData = (ClientContact) newRow;
					txtaContactNote.setText(newRowData.getDescription());
					lblNameAndSurname.setText(newRowData.getName());
					
					if (newRowData.getEmail() != null && !newRowData.getEmail().isBlank()) {
						btnContactsEmail.setDisable(false);
					} else {
						btnContactsEmail.setDisable(true);
					}
				}
			});
			// CLIENT CONTACT TABLE END
			
			// BANK, BILL, CONTRACT TABLE START
			// BANK, BILL, CONTRACT TABLE END
	
			btnClientNoteEdit.setOnAction(e->{
				Client controllerParam = tblClient.getSelectedItem();
				Common.displayForm(ControllerFactory.getController("SalesClientsNoteEdit", controllerParam, 0), btnClientNoteEdit, "%label.note.text");
				txtaClientNote.setText(tblClient.getSelectedItem().getDescription());
			});
			
			btnContactNoteEdit.setOnAction(e->{
				ClientContact controllerParam = tblClientContact.getSelectedItem();
				Common.displayForm(ControllerFactory.getController("SalesClientsContactNoteEdit", controllerParam, 0), btnContactNoteEdit, lblNameAndSurname.getText());
				txtaContactNote.setText(tblClientContact.getSelectedItem().getDescription());
			});
			
			/* disableovali smo sve osim edit, tu ce user da dobije novi ekran gde moze da dodaje, menja i brise.
			 * nema smisla da ovde vrsimo onda kontrolu
			bankBillContractTable.onRowSelectionChanged( (oldRow, newRow) -> {
				if (newRow != null) {
					Composite_BankBOFContract newRowData = (Composite_BankBOFContract) newRow;
					
					if (newRowData.getObjectValue() == null || newRowData.getObjectValue() == "") {
							bankBillContractTable.btnEdit.setDisable(true);
							bankBillContractTable.btnDelete.setDisable(true);
					}
					else {
						bankBillContractTable.btnEdit.setDisable(false);
						bankBillContractTable.btnDelete.setDisable(false);
					}
				}
			});
			*/
			
			bankBillContractTable.onRowDoubleClick( e-> {
				bankBillContractTable.showDoubleClickDefaultAction = true;
			});
			
			bankBillContractTable.btnEdit.setOnAction(e-> {
				String controllerName = null;
				Object controllerParam = tblClient;
				if (bankBillContractTable.getSelectedItem() != null) {
					switch(bankBillContractTable.getSelectedItem().getObjectType()) {
						case "BANKA":
							controllerName = "Lookup_ClientBankAccountController";
							Common.displayForm(ControllerFactory.getController(controllerName, controllerParam, 1), bankBillContractTable, bankBillContractTable.getSelectedItem().getObjectType());							
						break;
						case "MENICA":
							controllerName = "Lookup_BillOfExchangeController";
							BillOfExchange boc = (BillOfExchange) Common.displayForm(ControllerFactory.getController(controllerName, controllerParam, 1), bankBillContractTable, bankBillContractTable.getSelectedItem().getObjectType());
							
							if (boc == null) {
								cData.get(1).setObjectValue("");
							} else {
								cData.get(1).setObjectValue(boc.getBillOfExchangeNumber());
							}
						break;
						case "UGOVOR":
							controllerName = "Lookup_ClientContractController";
							ClientContract cc = (ClientContract) Common.displayForm(ControllerFactory.getController(controllerName, controllerParam, 1), bankBillContractTable, bankBillContractTable.getSelectedItem().getObjectType());
							
							if (cc == null) {
								cData.get(2).setObjectValue("");
							} else {
								cData.get(2).setObjectValue(cc.getContractNumber());
							}
						break;
						default:
						break;
					}

					bankBillContractTable.setItems(cData);
				}
			});
			
			// CLIENTGROUP COMBOBOX
			rsClientGroup.fetch(new ParameterizedTypeReference<JsonResponse<ClientGroup>>() {});
			cbClientGroup.getItems().addAll(rsClientGroup.getDataAsObservableList());
			
			cbClientGroup.onSelectionChanged(newItem->{
				ClientGroup clientGroup = (ClientGroup) newItem;
				
				if (clientGroup == null) {
					rsClient.setUrl(urlClientAll);
				} else {
					cbClientCategory.getSelectionModel().select(0);
					rsClient.setUrl("/client/allByGroupId/" + clientGroup.getId());
				}
				
				pageId.set(0);
				rsClient.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
				tblClient.setItems(rsClient.getDataAsObservableList());
			});
					
			// CLIENTCATEGORY COMBOBOX
			rsClientCategory.fetch(new ParameterizedTypeReference<JsonResponse<ClientCategory>>() {});
			cbClientCategory.getItems().addAll(rsClientCategory.getDataAsObservableList());
			
			cbClientCategory.onSelectionChanged(newItem->{
				ClientCategory clientCategory = (ClientCategory) newItem;
				if (clientCategory == null) {
					rsClient.setUrl(urlClientAll);
				} else {
					cbClientGroup.getSelectionModel().select(0);
					rsClient.setUrl("/client/allByCategoryId/" + clientCategory.getId());
				}
				
				pageId.set(0);
				rsClient.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
				tblClient.setItems(rsClient.getDataAsObservableList());
			});	
			
			btnItems.setOnAction(e->{
				ctrl.miItems.fire();
			});
			
			//SALE PLANNER ------------------------
			tblSalePlanner.setAddEditDialog("Sales_SalePlannerAddEditController");
			tblSalePlanner.setParentTable(tblClient);
			tblSalePlanner.onDataNeeded(() -> {
				rsSalePlanner.fetch(salePlannerPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<SalePlanner>>() {});
				tblSalePlanner.addItems(rsSalePlanner.getDataAsObservableList());
			});
			
			btnSalePlanner.setOnAction(e->{
				common.displayForm(ControllerFactory.getController("Sales_SalePlannerController", tblClient, 1), ctrl.miSalePlanner.getText(), ctrl.miSalePlanner.getGraphic(), ctx);
			});
			//-------------------------------------
	
			btnWholesale.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_WholesaleController", tblClient, 0), btnWholesale.getText(), btnWholesale.getGraphic(), ctx);
			});
	
			btnOffer.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_OfferController", null, 0), btnOffer.getText(), btnOffer.getGraphic(), ctx);
			});
	
			btnVirman.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_VirmanController", null, 0), btnVirman.getText(), btnVirman.getGraphic(), ctx);
			});
	
			btnPhotograph.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_ClientStoreImageController", tblClient, 0), btnPhotograph.getText(), btnPhotograph.getGraphic(), ctx);
			});
	
			//TODO: Ovo mi izgleda kao REPORT a ne kao forma. Nisam napravio fxml i kontroler.
			btnPerCommercialist.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_PerCommercialistController", null, 0), btnPerCommercialist.getText(), btnPerCommercialist.getGraphic(), ctx);
			});
	
			btnWorkplan.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_WorkplanController", null, 0), btnWorkplan.getText(), btnWorkplan.getGraphic(), ctx);
			});
	
			btnRevers.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_ReversController", null, 0), btnRevers.getText(), btnRevers.getGraphic(), ctx);
			});
	
			btnSalesReservation.setOnAction(e->{
				common.displayForm(ControllerFactory.getController("Sales_ReservationController", null, 0), btnSalesReservation.getText(), btnSalesReservation.getGraphic(), ctx);
			});
	
			btnWholesaleReturn.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_WholesaleReturnController", null, 0), btnWholesaleReturn.getText(), btnWholesaleReturn.getGraphic(), ctx);
			});
	
			btnCard.setOnAction( e-> {
				Client selectedClient = tblClient.getSelectedItem();
				common.displayForm(ControllerFactory.getController("Sales_CardController", selectedClient, 0), btnCard.getText(), btnCard.getGraphic(), ctx);
				
			});
	
			btnSaleStore.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_SaleStoreController", tblClient.getSelectedItem(), 0), btnSaleStore.getText(), btnSaleStore.getGraphic(), ctx);
			});
			
			btnCommissionTransferOrder.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_CommissionTransferOrderController", null, 0), btnCommissionTransferOrder.getText(), btnCommissionTransferOrder.getGraphic(), ctx);
			});
			
			btnCommissionBill.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_CommissionBillController", null, 0), btnCommissionBill.getText(), btnCommissionBill.getGraphic(), ctx);
			});
			
			btnDomPricelist.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_DomPricelistController", null, 0), btnDomPricelist.getText(), btnDomPricelist.getGraphic(), ctx);
			});
			
			btnGoodsIn.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Dobanovci_GoodsInController", null, 0), btnGoodsIn.getText(), btnGoodsIn.getGraphic(), ctx);
			});
			
			btnGoodsOut.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Dobanovci_GoodsOutController", null, 0), btnGoodsOut.getText(), btnGoodsOut.getGraphic(), ctx);
			});
			
			btnContactsEmail.setOnAction( e-> {
				String mailto  = "mailto:";
				String email   = tblClientContact.getSelectedItem().getEmail();
				String subject = "sabdzekt1";
				String cc      = "";
				String body    = "";
				
				for(ClientContact clientContact : tblClientContact.tableView.getItems()) {
					if (clientContact.getEmail()!= null 
							&& !clientContact.getEmail().isBlank() 
							&& (clientContact.getIncludeInEmailCc()
									|| clientContact.getIncludeInAttachment())
					) {
						cc = cc.concat(clientContact.getEmail() + ",");
					}
				}
				
				if (email != null) {
					JfxMainApplication mainApp = ctx.getBean(JfxMainApplication.class);					
					mainApp.getHostServices().showDocument(
							  mailto 
							+ email
							+ "?subject="
							+ subject
							+ "&cc="
							+ cc
							+ "&body="
							+ body
					);
				}
			});
			
			//button in tblSalePlanner (book)
			btnSalePlannerEdit.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_SalePlannerController", tblClient, 2), ctrl.miSalePlanner.getText(), ctrl.miSalePlanner.getGraphic(), ctx);				
			});
		
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END
	
}
