package org.code_studio.controller;

import java.net.Inet4Address;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.code_studio.database.ApplicationUser;
import org.code_studio.component.ControllerFactory;
import org.code_studio.main.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import static org.springframework.util.StringUtils.capitalize;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

@Component
public class MainController extends BaseController {

	@FXML public BorderPane mainBorderPane;
	@FXML private MenuBar mainMenubar;
	@FXML private Label lblUsername;
	@FXML private Label lblClientIPAddress;
	@FXML private Label lblDate;
	@FXML private Label lblTime;
	@FXML private ProgressBar pbProgressIndicator;
	
	@FXML private Button btnSecurity;
	@FXML private Button btnLog;

	private ApplicationContext ctx;

	//TODO: Ne mogu da stavim ovo kao private dok ne resim menuItem.getText i getGraphic 
	public MenuItem miProcurementSuppliers;
	public MenuItem miProcurementOrders;
	public MenuItem miItemsItemCatalog;
	public MenuItem miItemsClientBranches;
	public MenuItem miItemsItemGroups;
	public MenuItem miItemsItemSubGroups;
	public MenuItem miItemsItemType;
	public MenuItem miItemsCatalogBranchSubgroupLink;
	public MenuItem miItems;
	public MenuItem miSalePlanner;
	public MenuItem miItemReservation;
	public MenuItem miVirman;
	
	public Timer timerDatetimeUpdater;
	
	public MenuItem miWarehouseWarehouseList;
	
	@Autowired
	private Common common;
	
	public MainController(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	

	public void initialize() {
		try {
			//----------------------------------------------------------------------
			// Create container (TAB PANE)
			//----------------------------------------------------------------------
			common.setParentContainer(mainBorderPane);
			Common.setProgressIndicator(pbProgressIndicator);
			Common.setMainBorderPane(mainBorderPane);
			
			/******************************************
			 * Menu 1 - SALES
			 *****************************************/
			MenuItem miSaleClients = mainMenubar.getMenus().get(0).getItems().get(0); 
			miSaleClients.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_ClientController", null, 0), miSaleClients.getText(), miSaleClients.getGraphic(), ctx);
			});
	
			miSalePlanner = mainMenubar.getMenus().get(0).getItems().get(1); 
			miSalePlanner.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_SalePlannerController", null, 0), miSalePlanner.getText(), miSalePlanner.getGraphic(), ctx);
			});
	
			MenuItem miClientContact = mainMenubar.getMenus().get(0).getItems().get(2); 
			miClientContact.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_ClientContactController", null, 0), miClientContact.getText(), miClientContact.getGraphic(), ctx);
			});
	
			MenuItem miBillView = mainMenubar.getMenus().get(0).getItems().get(3); 
			miBillView.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_BillViewController", null, 0), miBillView.getText(), miBillView.getGraphic(), ctx);
			});
			
			//Maloprodaja ide ovde
			
			miItemReservation = mainMenubar.getMenus().get(0).getItems().get(5);
			miItemReservation.setOnAction( e-> { 
				common.displayForm(ControllerFactory.getController("Sales_ItemReservationController", null, 0), miItemReservation.getText(), miItemReservation.getGraphic(), ctx);
			});
			
			miVirman = mainMenubar.getMenus().get(0).getItems().get(6);
			miVirman.setOnAction( e-> { 
				common.displayForm(ControllerFactory.getController("Sales_VirmanController", null, 0), miVirman.getText(), miVirman.getGraphic(), ctx);
			});
			
			
			/******************************************
			 * Menu 2 - PROCUREMENT
			 *****************************************/
			miProcurementSuppliers = mainMenubar.getMenus().get(1).getItems().get(0); 
			miProcurementSuppliers.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Procurement_SupplierController", null, 0), miProcurementSuppliers.getText(), miProcurementSuppliers.getGraphic(), ctx);
			});
	
			miProcurementOrders = mainMenubar.getMenus().get(1).getItems().get(1); 
			miProcurementOrders.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Procurement_OrderController", null, 0), miProcurementOrders.getText(), miProcurementOrders.getGraphic(), ctx);
			});
	
			
			/******************************************
			 * Menu 3 - WAREHOUSE
			 *****************************************/
			MenuItem miWarehouseIssuingItems = mainMenubar.getMenus().get(2).getItems().get(0); 
			miWarehouseIssuingItems.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Warehouse_IssuingItemController", null, 0), miWarehouseIssuingItems.getText(), miWarehouseIssuingItems.getGraphic(), ctx);
			});
	
			MenuItem miWarehouseReceivingItems = mainMenubar.getMenus().get(2).getItems().get(1); 
			miWarehouseReceivingItems.setOnAction( e-> { 
				common.displayForm(ControllerFactory.getController("Warehouse_ReceivingItemController", null, 0), miWarehouseReceivingItems.getText(), miWarehouseReceivingItems.getGraphic(), ctx);
			});
			
			MenuItem miWarehouse_InternalTransferOrder = mainMenubar.getMenus().get(2).getItems().get(2); 
			miWarehouse_InternalTransferOrder.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Warehouse_TransferOrderController", null, 0), miWarehouse_InternalTransferOrder.getText(), miWarehouse_InternalTransferOrder.getGraphic(), ctx);
			});
	
			MenuItem miWarehouse_CommissionReturn = mainMenubar.getMenus().get(2).getItems().get(3); 
			miWarehouse_CommissionReturn.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Warehouse_TransferOrderController", null, 1), miWarehouse_CommissionReturn.getText(), miWarehouse_CommissionReturn.getGraphic(), ctx);
			});
	
			MenuItem miWarehouseInventoryListing = mainMenubar.getMenus().get(2).getItems().get(4); 
			miWarehouseInventoryListing.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Warehouse_InventoryListingController", null, 0), miWarehouseInventoryListing.getText(), miWarehouseInventoryListing.getGraphic(), ctx);
			});
			
			miWarehouseWarehouseList = mainMenubar.getMenus().get(2).getItems().get(6); 
			miWarehouseWarehouseList.setOnAction( e-> { 
				common.displayForm(ControllerFactory.getController("Warehouse_WarehouseListController", null, 0), miWarehouseWarehouseList.getText(), miWarehouseWarehouseList.getGraphic(), ctx);
			});
	
			MenuItem miWarehouse_TransferFromReceivingWarehouse = mainMenubar.getMenus().get(2).getItems().get(8); 
			miWarehouse_TransferFromReceivingWarehouse.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Warehouse_TransferFromReceivingWarehouseController", null, 0), miWarehouse_TransferFromReceivingWarehouse.getText(), miWarehouse_TransferFromReceivingWarehouse.getGraphic(), ctx);
			});
			
			MenuItem miWarehouse_ItemWarehouse = mainMenubar.getMenus().get(2).getItems().get(9); 
			miWarehouse_ItemWarehouse.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Warehouse_ItemWarehouseController", null, 0), miWarehouse_ItemWarehouse.getText(), miWarehouse_ItemWarehouse.getGraphic(), ctx);
			});
			
			//Ista forma kao i za prijem robe. Dogovoreno sa B. 2022-09-28
			MenuItem miWarehouseItemCorrection = mainMenubar.getMenus().get(2).getItems().get(10); 
			miWarehouseItemCorrection.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Warehouse_ReceivingItemController", null, 0), miWarehouseItemCorrection.getText(), miWarehouseItemCorrection.getGraphic(), ctx);
			});
			
			MenuItem miWarehouse_BillOfLadingType = mainMenubar.getMenus().get(2).getItems().get(11); 
			miWarehouse_BillOfLadingType.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Warehouse_BillOfLadingTypeController", null, 0), miWarehouse_BillOfLadingType.getText(), miWarehouse_BillOfLadingType.getGraphic(), ctx);
			});
	
			MenuItem miWarehouse_TransferOrderType = mainMenubar.getMenus().get(2).getItems().get(12); 
			miWarehouse_TransferOrderType.setOnAction( e-> { 
				common.displayForm(ControllerFactory.getController("Warehouse_TransferOrderTypeController", null, 0), miWarehouse_TransferOrderType.getText(), miWarehouse_TransferOrderType.getGraphic(), ctx);
			});
	
			// Ovo uzimam iz SALES_ITEM_RESERVATION, ista je forma
			MenuItem miWarehouse_Reservation = mainMenubar.getMenus().get(2).getItems().get(13); 
			miWarehouse_Reservation.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Sales_ItemReservationController", null, 0), miWarehouse_Reservation.getText(), miWarehouse_Reservation.getGraphic(), ctx);
			});
	
	
			/******************************************
			 * MENU 4 - FINANCE
			 *****************************************/
			MenuItem miFinance_ItemBalance = mainMenubar.getMenus().get(3).getItems().get(0);
			miFinance_ItemBalance.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Finance_ItemBalanceController", null, 0), miFinance_ItemBalance.getText(), miFinance_ItemBalance.getGraphic(), ctx);
			});
			MenuItem miFinance_BankStatement = mainMenubar.getMenus().get(3).getItems().get(1);
			miFinance_BankStatement.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Finance_BankStatementController", null, 0), miFinance_BankStatement.getText(), miFinance_BankStatement.getGraphic(), ctx);
			});
			MenuItem miFinance_ElectronicInvoice = mainMenubar.getMenus().get(3).getItems().get(2);
			miFinance_ElectronicInvoice.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Finance_ElectronicInvoiceController", null, 0), miFinance_ElectronicInvoice.getText(), miFinance_ElectronicInvoice.getGraphic(), ctx);
			});
			
			
			/******************************************
			 * MENU 5 - ITEMS
			 *****************************************/
			miItems = mainMenubar.getMenus().get(4).getItems().get(0);
			miItems.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ItemPerCategoryController", null, 0), miItems.getText(), miItems.getGraphic(), ctx);
			});
	
			MenuItem miItemsAddItems = mainMenubar.getMenus().get(4).getItems().get(1);
			miItemsAddItems.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ItemController", null, 0), miItemsAddItems.getText(), miItemsAddItems.getGraphic(), ctx);
			});
			
			miItemsItemCatalog = mainMenubar.getMenus().get(4).getItems().get(2);
			miItemsItemCatalog.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ItemCatalogController", null, 0), miItemsItemCatalog.getText(), miItemsItemCatalog.getGraphic(), ctx);
			});
			
			miItemsClientBranches = mainMenubar.getMenus().get(4).getItems().get(3);
			miItemsClientBranches.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ItemBranchController", null, 0), miItemsClientBranches.getText(), miItemsClientBranches.getGraphic(), ctx);
			});
	
			miItemsCatalogBranchSubgroupLink = mainMenubar.getMenus().get(4).getItems().get(4);
			miItemsCatalogBranchSubgroupLink.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_CatalogBranchSubgroupLinkController", null, 0), miItemsCatalogBranchSubgroupLink.getText(), miItemsCatalogBranchSubgroupLink.getGraphic(), ctx);
			});
	
			miItemsItemType = mainMenubar.getMenus().get(4).getItems().get(5);
			miItemsItemType.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ItemTypeController", null, 0), miItemsItemType.getText(), miItemsItemType.getGraphic(), ctx);
			});
	
			MenuItem miItemsItemStatus = mainMenubar.getMenus().get(4).getItems().get(6);
			miItemsItemStatus.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ItemStatusController", null, 0), miItemsItemStatus.getText(), miItemsItemStatus.getGraphic(), ctx);
			});
			
			miItemsItemGroups = mainMenubar.getMenus().get(4).getItems().get(7);
			miItemsItemGroups.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ItemGroupController", null, 0), miItemsItemGroups.getText(), miItemsItemGroups.getGraphic(), ctx);
			});
	
			miItemsItemSubGroups = mainMenubar.getMenus().get(4).getItems().get(8);
			miItemsItemSubGroups.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ItemSubgroupController", null, 0), miItemsItemSubGroups.getText(), miItemsItemSubGroups.getGraphic(), ctx);
			});
	
			MenuItem miItemsItemBarcode = mainMenubar.getMenus().get(4).getItems().get(9);
			miItemsItemBarcode.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ItemBarcodeController", null, 0), miItemsItemBarcode.getText(), miItemsItemBarcode.getGraphic(), ctx);
			});
	
			/******************************************
			 * Menu 6 - CENOVNICI
			 *****************************************/
			MenuItem miItemsPricelistPerClient = mainMenubar.getMenus().get(5).getItems().get(0);
			miItemsPricelistPerClient.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_PricelistPerClientController", null, 0), miItemsPricelistPerClient.getText(), miItemsPricelistPerClient.getGraphic(), ctx);
			});
			
			MenuItem miItemsComparativePricelistPerClient = mainMenubar.getMenus().get(5).getItems().get(1);
			miItemsComparativePricelistPerClient.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ComparativePricelistPerClientController", null, 0), miItemsComparativePricelistPerClient.getText(), miItemsComparativePricelistPerClient.getGraphic(), ctx);
			});
	
			MenuItem miItemsPerSupplier = mainMenubar.getMenus().get(5).getItems().get(2);
			miItemsPerSupplier.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_ComparativePricelistPerSupplierController", null, 0), miItemsPerSupplier.getText(), miItemsPerSupplier.getGraphic(), ctx);			
			});
	
			MenuItem miItemsCalculation = mainMenubar.getMenus().get(5).getItems().get(3);
			miItemsCalculation.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Items_CalculationController", null, 0), miItemsCalculation.getText(), miItemsCalculation.getGraphic(), ctx);
			});
			
			/******************************************
			 * Menu 7 - SIFARNICI
			 *****************************************/
			MenuItem miLookupCurrency = mainMenubar.getMenus().get(6).getItems().get(0);
			miLookupCurrency.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_CurrencyController", null, 0), miLookupCurrency.getText(), miLookupCurrency.getGraphic(), ctx);
			});
	
			MenuItem miLookupCountry = mainMenubar.getMenus().get(6).getItems().get(1);
			miLookupCountry.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_CountryController", null, 0), miLookupCountry.getText(), miLookupCountry.getGraphic(), ctx);
			});
			
			MenuItem miLookupPaymentTerms = mainMenubar.getMenus().get(6).getItems().get(2);
			miLookupPaymentTerms.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_PaymentTermController", null, 0), miLookupPaymentTerms.getText(), miLookupPaymentTerms.getGraphic(), ctx);
			});
	
			MenuItem miLookupDeliveryType = mainMenubar.getMenus().get(6).getItems().get(3);
			miLookupDeliveryType.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_DeliveryTypeController", null, 0), miLookupDeliveryType.getText(), miLookupDeliveryType.getGraphic(), ctx);
			});
			
			MenuItem miLookupPaymentType = mainMenubar.getMenus().get(6).getItems().get(4);
			miLookupPaymentType.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_PaymentTypeController", null, 0), miLookupPaymentType.getText(), miLookupPaymentType.getGraphic(), ctx);
			});
	
			MenuItem miLookupSaleType = mainMenubar.getMenus().get(6).getItems().get(5);
			miLookupSaleType.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_SaleTypeController", null, 0), miLookupSaleType.getText(), miLookupSaleType.getGraphic(), ctx);
			});
			
			MenuItem miLookupVatGroup = mainMenubar.getMenus().get(6).getItems().get(6);
			miLookupVatGroup.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_VatGroupController", null, 0), miLookupVatGroup.getText(), miLookupVatGroup.getGraphic(), ctx);
			});
	
			MenuItem miLookupCustomsGroup = mainMenubar.getMenus().get(6).getItems().get(7);
			miLookupCustomsGroup.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_CustomsGroupController", null, 0), miLookupCustomsGroup.getText(), miLookupCustomsGroup.getGraphic(), ctx);
			});
	
			MenuItem miLookupApplicationUser = mainMenubar.getMenus().get(6).getItems().get(8);
			miLookupApplicationUser.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_ApplicationUserController", null, 0), miLookupApplicationUser.getText(), miLookupApplicationUser.getGraphic(), ctx);
			});
			
			MenuItem miLookupApplicationRole = mainMenubar.getMenus().get(6).getItems().get(9);
			miLookupApplicationRole.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_ApplicationRoleController", null, 0), miLookupApplicationRole.getText(), miLookupApplicationRole.getGraphic(), ctx);
			});
	
			MenuItem miLookupBankAccount = mainMenubar.getMenus().get(6).getItems().get(10);
			miLookupBankAccount.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_BankAccountController", null, 0), miLookupBankAccount.getText(), miLookupBankAccount.getGraphic(), ctx);
			});
	
			MenuItem miLookupCashRegister = mainMenubar.getMenus().get(6).getItems().get(11);
			miLookupCashRegister.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_CashRegisterController", null, 0), miLookupCashRegister.getText(), miLookupCashRegister.getGraphic(), ctx);
			});
	
			MenuItem miLookupBank = mainMenubar.getMenus().get(6).getItems().get(12);
			miLookupBank.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_BankController", null, 0), miLookupBank.getText(), miLookupBank.getGraphic(), ctx);
			});
	
			MenuItem miLookupMeasureUnit = mainMenubar.getMenus().get(6).getItems().get(13);
			miLookupMeasureUnit.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_MeasurementUnitController", null, 0), miLookupMeasureUnit.getText(), miLookupMeasureUnit.getGraphic(), ctx);
			});
	
			MenuItem miLookupPackaging = mainMenubar.getMenus().get(6).getItems().get(14);
			miLookupPackaging.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_PackageTypeController", null, 0), miLookupPackaging.getText(), miLookupPackaging.getGraphic(), ctx);
			});
	
			MenuItem miLookupClientGroup = mainMenubar.getMenus().get(6).getItems().get(15);
			miLookupClientGroup.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_ClientGroupController", null, 0), miLookupClientGroup.getText(), miLookupClientGroup.getGraphic(), ctx);
			});
			
			MenuItem miLookupClientCategory = mainMenubar.getMenus().get(6).getItems().get(16);
			miLookupClientCategory.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_ClientCategoryController", null, 0), miLookupClientCategory.getText(), miLookupClientCategory.getGraphic(), ctx);
			});
			
			MenuItem miLookupDiscount = mainMenubar.getMenus().get(6).getItems().get(17);
			miLookupDiscount.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_DiscountController", null, 0), miLookupDiscount.getText(), miLookupDiscount.getGraphic(), ctx);
			});
			
			MenuItem miLookupBrand = mainMenubar.getMenus().get(6).getItems().get(18);
			miLookupBrand.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_BrandController", null, 0), miLookupBrand.getText(), miLookupBrand.getGraphic(), ctx);
			});
			
			MenuItem miLookupDbini = mainMenubar.getMenus().get(6).getItems().get(19);
			miLookupDbini.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Lookup_DbiniController", null, 0), miLookupDbini.getText(), miLookupDbini.getGraphic(), ctx);
			});

			
	
			/******************************************
			 * Menu 8 - DOBANOVCI
			 *****************************************/
			MenuItem miDobanovciGoodsIn = mainMenubar.getMenus().get(7).getItems().get(0);
			miDobanovciGoodsIn.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Dobanovci_GoodsInController", null, 0), miDobanovciGoodsIn.getText(), miDobanovciGoodsIn.getGraphic(), ctx);
			});
	
			MenuItem miDobanovciGoodsOut = mainMenubar.getMenus().get(7).getItems().get(1);
			miDobanovciGoodsOut.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Dobanovci_GoodsOutController", null, 0), miDobanovciGoodsOut.getText(), miDobanovciGoodsOut.getGraphic(), ctx);
			});
			
			MenuItem miPaletteMap = mainMenubar.getMenus().get(7).getItems().get(2);
			miPaletteMap.setOnAction( e-> {
				String pageUrl = (String) miPaletteMap.getUserData();
				common.displayForm(ControllerFactory.getController("WebFormController", pageUrl, 0), miPaletteMap.getText(), miPaletteMap.getGraphic(), ctx);
			});
			/**
			MenuItem miDobanovciWarehouse = mainMenubar.getMenus().get(7).getItems().get(3);
			miDobanovciWarehouse.setOnAction( e-> {
				String pageUrl = (String) miDobanovciWarehouse.getUserData();
				common.displayForm(ControllerFactory.getController("WebFormController", pageUrl, 0), miDobanovciWarehouse.getText(), miDobanovciWarehouse.getGraphic(), ctx);
			});
			
			MenuItem miCraneManualMessages = mainMenubar.getMenus().get(7).getItems().get(4);
			miPaletteMap.setOnAction( e-> {
				String pageUrl = (String) miCraneManualMessages.getUserData();
				common.displayForm(ControllerFactory.getController("WebFormController", pageUrl, 0), miCraneManualMessages.getText(), miCraneManualMessages.getGraphic(), ctx);
			});
			**/
	
	
			/******************************************
			 * Menu 9 - BRZA POSTA
			 *****************************************/
			MenuItem miFastmail_SendItem = mainMenubar.getMenus().get(8).getItems().get(0);
			miFastmail_SendItem.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Fastmail_SendItemController", null, 0), miFastmail_SendItem.getText(), miFastmail_SendItem.getGraphic(), ctx);
			});
	
			MenuItem miFastmail_SendMail = mainMenubar.getMenus().get(8).getItems().get(1);
			miFastmail_SendMail.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Fastmail_SendMailController", null, 0), miFastmail_SendMail.getText(), miFastmail_SendMail.getGraphic(), ctx);
			});
	
			MenuItem miFastmail_PrintPackedParcel = mainMenubar.getMenus().get(8).getItems().get(2);
			miFastmail_PrintPackedParcel.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Fastmail_PrintPackedParcelController", null, 0), miFastmail_PrintPackedParcel.getText(), miFastmail_PrintPackedParcel.getGraphic(), ctx);
			});
	
			MenuItem miFastmail_PrintSentParcel = mainMenubar.getMenus().get(8).getItems().get(3);
			miFastmail_PrintSentParcel.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Fastmail_PrintSentParcelController", null, 0), miFastmail_PrintSentParcel.getText(), miFastmail_PrintSentParcel.getGraphic(), ctx);
			});
	
			MenuItem miFastmail_ImportMail = mainMenubar.getMenus().get(8).getItems().get(4);
			miFastmail_ImportMail.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Fastmail_ImportMailController", null, 0), miFastmail_ImportMail.getText(), miFastmail_ImportMail.getGraphic(), ctx);
			});
	
			MenuItem miFastmail_SendInitialItemCount = mainMenubar.getMenus().get(8).getItems().get(5);
			miFastmail_SendInitialItemCount.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Fastmail_SendInitialItemCountController", null, 0), miFastmail_SendInitialItemCount.getText(), miFastmail_SendInitialItemCount.getGraphic(), ctx);			
			});
			
			MenuItem miFastmail_UpdateDeliveredCount = mainMenubar.getMenus().get(8).getItems().get(6);
			miFastmail_UpdateDeliveredCount.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Fastmail_UpdateDeliveredCountController", null, 0), miFastmail_UpdateDeliveredCount.getText(), miFastmail_UpdateDeliveredCount.getGraphic(), ctx);			
			});
			
			MenuItem miFastmail_ControlPttOut = mainMenubar.getMenus().get(8).getItems().get(7);
			miFastmail_ControlPttOut.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Fastmail_ControlPttOutController", null, 0), miFastmail_ControlPttOut.getText(), miFastmail_ControlPttOut.getGraphic(), ctx);			
			});
			
			MenuItem miFastmail_ControlDeliveryOut = mainMenubar.getMenus().get(8).getItems().get(8);
			miFastmail_ControlDeliveryOut.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Fastmail_ControlDeliveryOutController", null, 0), miFastmail_ControlDeliveryOut.getText(), miFastmail_ControlDeliveryOut.getGraphic(), ctx);
			});
			
	
			/******************************************
			 * Menu 10 - POVRAT
			 *****************************************/
			MenuItem miReturn_ByBarcode = mainMenubar.getMenus().get(9).getItems().get(0);
			miReturn_ByBarcode.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Return_ByBarcodeController", null, 0), miReturn_ByBarcode.getText(), miReturn_ByBarcode.getGraphic(), ctx);
			});
	
			MenuItem miReturn_ByItemId = mainMenubar.getMenus().get(9).getItems().get(1);
			miReturn_ByItemId.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Return_ByItemIdController", null, 0), miReturn_ByItemId.getText(), miReturn_ByItemId.getGraphic(), ctx);
			});
			
			MenuItem miReturn_ByDomItemId = mainMenubar.getMenus().get(9).getItems().get(2);
			miReturn_ByDomItemId.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Return_ByDomItemIdController", null, 0), miReturn_ByDomItemId.getText(), miReturn_ByDomItemId.getGraphic(), ctx);
			});
			
			
			/******************************************
			 * Menu 11 - RACUNI
			 *****************************************/
			MenuItem miAccount_Accounts = mainMenubar.getMenus().get(10).getItems().get(0);
			miAccount_Accounts.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Account_AccountsController", null, 0), miAccount_Accounts.getText(), miAccount_Accounts.getGraphic(), ctx);
			});
			
			MenuItem miAccount_ClientPayments = mainMenubar.getMenus().get(10).getItems().get(1);
			miAccount_ClientPayments.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("Account_ClientPaymentController", null, 0), miAccount_ClientPayments.getText(), miAccount_ClientPayments.getGraphic(), ctx);
			});
			
			
			
			/******************************************
			 * BOTTOM BAR
			 *****************************************/
			lblClientIPAddress.setText(Inet4Address.getLocalHost().getHostAddress());
			
			//----------------------------------------------------------------------
			// Date and Time (Clock)
			//----------------------------------------------------------------------
			timerDatetimeUpdater = new Timer();
			Common.timerDatetimeUpdater = timerDatetimeUpdater;
			TimerTask ttDatetime = new TimerTask() {
				public void run() {
	            	DateTimeFormatter fmtDate = DateTimeFormatter.ofPattern("EEEE, dd.MM.yyyy");
	                DateTimeFormatter fmtTime = DateTimeFormatter.ofPattern("HH:mm");
	                
	                Platform.runLater(() -> {
		            	lblDate.setText(capitalize(fmtDate.format(LocalDateTime.now())));
		                lblTime.setText(fmtTime.format(LocalDateTime.now()));
	                });
				}
			};
			timerDatetimeUpdater.scheduleAtFixedRate(ttDatetime, 0, 30000);
			
			/**
			 * Displays client application log - last X lines
			 */
			btnLog.setOnAction( e-> {
				common.displayForm(ControllerFactory.getController("ApplicationLogController", null, 0), "Klijentski aplikacioni log", btnLog.getGraphic(), ctx);
			});
			
		
		} catch (Exception ex) {
			Common.logMessage(getClass(), ex, "ERROR");
		}

	}
	
	@Override
	public void postInitialize() {
		Boolean securityDisable = Boolean.parseBoolean(Common.applicationProperties.getProperty("security.disable", "false"));
		
		if (!securityDisable) {
			mainMenubar.setVisible(false);
			Common.setApplicationUser((ApplicationUser) Common.displayForm(ControllerFactory.getController("LoginController", this, 0), mainBorderPane, "Prijava"));
			if (Common.getApplicationUser() != null) {
				mainMenubar.setVisible(true);
				lblUsername.setText(Common.getApplicationUser().getName());
			}
		}
		
		
		//----------------------------------------------------------------------
		// Main Menu items disable for Dobanovci instance
		//----------------------------------------------------------------------
		Boolean isDobanovciInstance = Boolean.parseBoolean(Common.applicationProperties.getProperty("client.crane.instance", "false"));
		if (isDobanovciInstance) {
			mainMenubar.getMenus().forEach((menu ->  {
				menu.setVisible(false);
			}));
			mainMenubar.getMenus().get(7).setVisible(true);
		}
		


		
	}

}
