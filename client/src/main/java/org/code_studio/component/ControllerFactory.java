package org.code_studio.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import org.code_studio.controller.*;

import org.code_studio.database.ApplicationRole;
import org.code_studio.database.ApplicationUser;
import org.code_studio.database.Bank;
import org.code_studio.database.BankAccount;
import org.code_studio.database.CashDesk;
import org.code_studio.database.Client;
import org.code_studio.database.ClientContact;
import org.code_studio.database.Country;
import org.code_studio.database.Currency;
import org.code_studio.database.CustomsGroup;
import org.code_studio.database.DeliveryType;
import org.code_studio.database.MeasurementUnit;
import org.code_studio.database.PackageType;
import org.code_studio.database.PaymentTerm;
import org.code_studio.database.PaymentType;
import org.code_studio.database.SaleType;
import org.code_studio.database.VatGroup;

@Component
public class ControllerFactory implements ApplicationContextAware {
	private static ApplicationContext ctx;


	public static FxmlController getController (String controllerName, Object controllerParam, int mode) {
		String resource = null;
		FxmlController controller = null;

		switch (controllerName) {
			/******************************************
			 * LOGIN
			 *****************************************/
			case "LoginController":
				resource = "Login.fxml";
				controller = new FxmlController(new LoginController(controllerParam, mode, ctx), resource);
			break;		
			/******************************************
			 * SALES
			 *****************************************/
			case "Sales_ClientController":
				resource = "Sales_Client.fxml";
				controller = new FxmlController(new Sales_ClientController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_SalePlannerController":
				resource = "Sales_SalePlanner.fxml";
				controller = new FxmlController(new Sales_SalePlannerController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_SalePlannerAddEditController":
				resource = "Sales_SalePlannerAddEdit.fxml";
				controller = new FxmlController(new Sales_SalePlannerAddEditController(controllerParam, mode), resource);
			break;
			case "Sales_SalePlannerMonthlyOverviewController":
				resource = "Sales_SalePlannerMonthlyOverview.fxml";
				controller = new FxmlController(new Sales_SalePlannerMonthlyOverviewController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_SalePlannerMonthlyOverviewPrintController":
				resource = "Sales_SalePlannerMonthlyOverviewPrint.fxml";
				controller = new FxmlController(new Sales_SalePlannerMonthlyOverviewPrintController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_SalePlannerSaleOfficerMonthlyReviewController":
				resource = "Sales_SalePlannerSaleOfficerMonthlyReview.fxml";
				controller = new FxmlController(new Sales_SalePlannerSaleOfficerMonthlyReviewController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_ClientContactController":
				resource = "Sales_ClientContact.fxml";
				controller = new FxmlController(new Sales_ClientContactController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_BillViewController":
				resource = "Sales_BillView.fxml";
				controller = new FxmlController(new Sales_BillViewController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_ItemReservationController":
				resource = "Sales_ItemReservation.fxml";
				controller = new FxmlController(new Sales_ItemReservationController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_ClientNoteEdit":
				resource = "../component/CSTextAreaForm.fxml";
				controller = new FxmlController(new CSTextAreaForm<>((Client) controllerParam), resource);
			break;
			case "Sales_ClientContactNoteEdit":
				resource = "../component/CSTextAreaForm.fxml";
				controller = new FxmlController(new CSTextAreaForm<>((ClientContact) controllerParam), resource);
			break;
			case "Lookup_DeliveryAddressController":
				resource = "../view/Lookup_DeliveryAddress.fxml";
				controller = new FxmlController(new Lookup_DeliveryAddressController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_DeliveryAddressAddEditController":
				resource = "../view/Lookup_DeliveryAddressAddEdit.fxml";
				controller = new FxmlController(new Lookup_DeliveryAddressAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_ClientAddEditController":
				resource = "../view/Sales_ClientAddEdit.fxml";
				controller = new FxmlController(new Sales_ClientAddEditController(controllerParam, mode), resource);
			break;
			case "Sales_ClientSelectController":
				resource = "../view/Sales_ClientSelect.fxml";
				controller = new FxmlController(new Sales_ClientSelectController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_ClientContactAddEditController":
				resource = "Sales_ClientContactAddEdit.fxml";
				controller = new FxmlController(new Sales_ClientContactAddEditController(controllerParam, mode), resource);
			break;
			case "Sales_WholesaleController":
				resource = "Sales_Wholesale.fxml";
				controller = new FxmlController(new Sales_WholesaleController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_WorkplanController":
				resource = "Sales_Workplan.fxml";
				controller = new FxmlController(new Sales_WorkplanController(controllerParam, mode, ctx), resource);
			break;

			case "Sales_DomPricelistController":
				resource = "Sales_DomPricelist.fxml";
				controller = new FxmlController(new Sales_DomPricelistController(controllerParam, mode, ctx), resource);
			break;
			case "Items_PricelistPerClientAddEditController":
				resource = "Items_PricelistPerClientAddEdit.fxml";
				controller = new FxmlController(new Items_PricelistPerClientAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_WholesaleReturnController":
				resource = "Sales_WholesaleReturn.fxml";
				controller = new FxmlController(new Sales_WholesaleReturnController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_WholesaleReturnAddEditController":
				resource = "Sales_WholesaleReturnAddEdit.fxml";
				controller = new FxmlController(new Sales_WholesaleReturnAddEditController(controllerParam), resource);
			break;
			case "Sales_WholesaleReturnDetailAddEditController":
				resource = "Sales_WholesaleReturnDetailAddEdit.fxml";
				controller = new FxmlController(new Sales_WholesaleReturnDetailAddEditController(controllerParam), resource);
			break;
			case "Sales_WholesaleReturnDetailWholesaleDetailAddEditController":
				resource = "Sales_WholesaleReturnDetailWholesaleDetailAddEdit.fxml";
				controller = new FxmlController(new Sales_WholesaleReturnDetailWholesaleDetailAddEditController(controllerParam), resource);
			break;
			case "Sales_WholesaleDetailAddEditDetailController":
				resource = "Sales_WholesaleDetailAddEditDetail.fxml";
				controller = new FxmlController(new Sales_WholesaleDetailAddEditDetailController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_SaleStoreController":
				resource = "Sales_SaleStore.fxml";
				controller = new FxmlController(new Sales_SaleStoreController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_SaleStoreAddEditController":
				resource = "Sales_SaleStoreAddEdit.fxml";
				controller = new FxmlController(new Sales_SaleStoreAddEditController(controllerParam, mode), resource);
			break;
			case "Sales_WholesaleAddEditController":
				resource = "Sales_WholesaleAddEdit.fxml";
				controller = new FxmlController(new Sales_WholesaleAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_WholesaleDetailAddEditController":
				resource = "Sales_WholesaleDetailAddEdit.fxml";
				controller = new FxmlController(new Sales_WholesaleDetailAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_OfferController":
				resource = "Sales_Offer.fxml";
				controller = new FxmlController(new Sales_OfferController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_OfferAddEditController":
				resource = "Sales_OfferAddEdit.fxml";
				controller = new FxmlController(new Sales_OfferAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_OfferDetailAddEditController":
				resource = "Sales_OfferDetailAddEdit.fxml";
				controller = new FxmlController(new Sales_OfferDetailAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_CommissionTransferOrderController":
				resource = "Sales_CommissionTransferOrder.fxml";
				controller = new FxmlController(new Sales_CommissionTransferOrderController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_CommissionTransferOrderAddEditController":
				resource = "Sales_CommissionTransferOrderAddEdit.fxml";
				controller = new FxmlController(new Sales_CommissionTransferOrderAddEditController(controllerParam), resource);
			break;
			case "Sales_CommissionTransferOrderDetailAddEditController":
				resource = "Sales_CommissionTransferOrderDetailAddEdit.fxml";
				controller = new FxmlController(new Sales_CommissionTransferOrderDetailAddEditController(controllerParam), resource);
			break;
			case "Sales_CommissionBillController":
				resource = "Sales_CommissionBill.fxml";
				controller = new FxmlController(new Sales_CommissionBillController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_CommissionBillAddEditController":
				resource = "Sales_CommissionBillAddEdit.fxml";
				controller = new FxmlController(new Sales_CommissionBillAddEditController(controllerParam), resource);
			break;
			case "Sales_CommissionBillDetailAddEditController":
				resource = "Sales_CommissionBillDetailAddEdit.fxml";
				controller = new FxmlController(new Sales_CommissionBillDetailAddEditController(controllerParam), resource);
			break;
			case "Sales_ReservationController":
				resource = "Sales_Reservation.fxml";
				controller = new FxmlController(new Sales_ReservationController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_ReservationAddEditController":
				resource = "Sales_ReservationAddEdit.fxml";
				controller = new FxmlController(new Sales_ReservationAddEditController(controllerParam), resource);
			break;
			case "Sales_ReservationDetailAddEditController":
				resource = "Sales_ReservationDetailAddEdit.fxml";
				controller = new FxmlController(new Sales_ReservationDetailAddEditController(controllerParam), resource);
			break;
			case "Sales_VirmanController":
				resource = "Sales_Virman.fxml";
				controller = new FxmlController(new Sales_VirmanController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_VirmanAddEditController":
				resource = "Sales_VirmanAddEdit.fxml";
				controller = new FxmlController(new Sales_VirmanAddEditController(controllerParam), resource);
			break;
			case "Sales_ClientStoreImageController":
				resource = "Sales_ClientStoreImage.fxml";
				controller = new FxmlController(new Sales_ClientStoreImageController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_ClientStoreImageAddEditController":
				resource = "Sales_ClientStoreImageAddEdit.fxml";
				controller = new FxmlController(new Sales_ClientStoreImageAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_CardController":
				resource = "Sales_Card.fxml";
				controller = new FxmlController(new Sales_CardController(controllerParam, mode, ctx), resource);
			break;
			case "Sales_CardDetailController":
				resource = "Sales_CardDetail.fxml";
				controller = new FxmlController(new Sales_CardDetailController(controllerParam, mode, ctx), resource);
			break;
		
			
			/******************************************
			 * PROCUREMENT
			 *****************************************/
			case "Procurement_SupplierController":
				resource = "Procurement_Supplier.fxml";
				controller = new FxmlController(new Procurement_SupplierController(controllerParam, mode, ctx), resource);
			break;
			case "Procurement_OrderController":
				resource = "Procurement_Order.fxml";
				controller = new FxmlController(new Procurement_OrderController(controllerParam, mode, ctx), resource);
			break;
			case "Procurement_OrderAddEditController":
				resource = "Procurement_OrderAddEdit.fxml";
				controller = new FxmlController(new Procurement_OrderAddEditController(controllerParam, mode), resource);
			break;
			case "Procurement_OrderDetailAddEditController":
				resource = "Procurement_OrderDetailAddEdit.fxml";
				controller = new FxmlController(new Procurement_OrderDetailAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Procurement_OrderWorkOrderDetailAddEditController":
				resource = "Procurement_OrderWorkOrderDetailAddEdit.fxml";
				controller = new FxmlController(new Procurement_OrderWorkOrderDetailAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Procurement_OrderDateAddEditController":
				resource = "Procurement_OrderDateAddEdit.fxml";
				controller = new FxmlController(new Procurement_OrderDateAddEditController(controllerParam, mode), resource);
			break;
			case "Procurement_OrderDetailConfirmationController":
				resource = "Procurement_OrderDetailConfirmation.fxml";
				controller = new FxmlController(new Procurement_OrderDetailConfirmationController(controllerParam, mode), resource);
			break;
			case "Procurement_OrderReservationController":
				resource = "Procurement_OrderReservation.fxml";
				controller = new FxmlController(new Procurement_OrderReservationController(controllerParam), resource);
			break;		

			
			/******************************************
			 * WAREHOUSE
			 *****************************************/
			case "Warehouse_WarehouseListController":
				resource = "Warehouse_WarehouseList.fxml";
				controller = new FxmlController(new Warehouse_WarehouseListController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_WarehouseAddEditController":
				resource = "Warehouse_WarehouseAddEdit.fxml";
				controller = new FxmlController(new Warehouse_WarehouseAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_IssuingItemController":
				resource = "Warehouse_IssuingItem.fxml";
				controller = new FxmlController(new Warehouse_IssuingItemController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_ReceivingItemController":
				resource = "Warehouse_ReceivingItem.fxml";
				controller = new FxmlController(new Warehouse_ReceivingItemController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_InventoryListingController":
				resource = "Warehouse_InventoryListing.fxml";
				controller = new FxmlController(new Warehouse_InventoryListingController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_InventoryListingAddEditController":
				resource = "Warehouse_InventoryListingAddEdit.fxml";
				controller = new FxmlController(new Warehouse_InventoryListingAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_InventoryListingDetailAddEditController":
				resource = "Warehouse_InventoryListingDetailAddEdit.fxml";
				controller = new FxmlController(new Warehouse_InventoryListingDetailAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_InventoryListingVerificationController":
				resource = "Warehouse_InventoryListingVerification.fxml";
				controller = new FxmlController(new Warehouse_InventoryListingVerificationController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_TransferFromReceivingWarehouseController":
				resource = "Warehouse_TransferFromReceivingWarehouse.fxml";
				controller = new FxmlController(new Warehouse_TransferFromReceivingWarehouseController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_ItemWarehouseController":
				resource = "Warehouse_ItemWarehouse.fxml";
				controller = new FxmlController(new Warehouse_ItemWarehouseController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_BillOfLadingTypeController": //PROVERITI DA LI MI TREBA, ili je ovo transfer order tj prenosnica
				resource = "Warehouse_BillOfLadingType.fxml";
				controller = new FxmlController(new Warehouse_BillOfLadingTypeController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_TransferOrderTypeController":
				resource = "Warehouse_TransferOrderType.fxml";
				controller = new FxmlController(new Warehouse_TransferOrderTypeController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_ItemWarehouseAddEditController":
				resource = "Warehouse_ItemWarehouseAddEdit.fxml";
				controller = new FxmlController(new Warehouse_ItemWarehouseAddEditController(controllerParam), resource);
			break;
			case "Warehouse_IssuingItemsManualIssuingController":
				resource = "Warehouse_IssuingItemsManualIssuing.fxml";
				controller = new FxmlController(new Warehouse_IssuingItemsManualIssuingController(controllerParam, mode, ctx), resource);
			break;
			
			case "Warehouse_TransferOrderController":
				resource = "Warehouse_TransferOrder.fxml";
				controller = new FxmlController(new Warehouse_TransferOrderController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_TransferOrderAddEditController":
				resource = "Warehouse_TransferOrderAddEdit.fxml";
				controller = new FxmlController(new Warehouse_TransferOrderAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_TransferOrderDetailAddEditController":
				resource = "Warehouse_TransferOrderDetailAddEdit.fxml";
				controller = new FxmlController(new Warehouse_TransferOrderDetailAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_TransferOrderDetailAddEditDetailController":
				resource = "Warehouse_TransferOrderDetailAddEditDetail.fxml";
				controller = new FxmlController(new Warehouse_TransferOrderDetailAddEditDetailController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_TransferOrderWorkController":
				resource = "Warehouse_TransferOrderWork.fxml";
				controller = new FxmlController(new Warehouse_TransferOrderWorkController(controllerParam, mode, ctx), resource);
			break;
			case "Warehouse_ReceivingItemsAddEditController":
				resource = "Warehouse_ReceivingItemsAddEdit.fxml";
				controller = new FxmlController(new Warehouse_ReceivingItemsAddEditController(controllerParam, mode), resource);
			break;
			case "Warehouse_ReceivingItemsDetailAddEditController":
				resource = "Warehouse_ReceivingItemsDetailAddEdit.fxml";
				controller = new FxmlController(new Warehouse_ReceivingItemsDetailAddEditController(controllerParam, mode), resource);
			break;
			case "Warehouse_ReceivingItemsDetailAddEditDetailController":
				resource = "Warehouse_ReceivingItemsDetailAddEditDetail.fxml";
				controller = new FxmlController(new Warehouse_ReceivingItemsDetailAddEditDetailController(controllerParam, mode), resource);
			break;

			
			/******************************************
			 * FINANCE
			 *****************************************/
			case "Finance_ItemBalanceController":
				resource = "Finance_ItemBalance.fxml";
				controller = new FxmlController(new Finance_ItemBalanceController(controllerParam, mode, ctx), resource);
			break;
			case "Finance_BankStatementController":
				resource = "Finance_BankStatement.fxml";
				controller = new FxmlController(new Finance_BankStatementController(controllerParam, mode, ctx), resource);
			break;
			case "Finance_BankStatementAddEditController":
				resource = "Finance_BankStatementAddEdit.fxml";
				controller = new FxmlController(new Finance_BankStatementAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Finance_BankStatementDetailAddEditController":
				resource = "Finance_BankStatementDetailAddEdit.fxml";
				controller = new FxmlController(new Finance_BankStatementDetailAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Finance_ElectronicInvoiceController":
				resource = "Finance_ElectronicInvoice.fxml";
				controller = new FxmlController(new Finance_ElectronicInvoiceController(controllerParam, mode, ctx), resource);
			break;
			
			
			/******************************************
			 * ITEMS
			 *****************************************/
			case "Items_ItemPerCategoryController":
				resource = "Items_ItemPerCategory.fxml";
				controller = new FxmlController(new Items_ItemPerCategoryController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ItemController":
				resource = "Items_Item.fxml";
				controller = new FxmlController(new Items_ItemController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ItemCatalogController":
				resource = "Items_ItemCatalog.fxml";
				controller = new FxmlController(new Items_ItemCatalogController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ItemBranchController":
				resource = "Items_ItemBranch.fxml";
				controller = new FxmlController(new Items_ItemBranchController(controllerParam, mode, ctx), resource);
			break;
			case "Items_CatalogBranchSubgroupLinkController":
				resource = "Items_CatalogBranchSubgroupLink.fxml";
				controller = new FxmlController(new Items_CatalogBranchSubgroupLinkController(controllerParam, mode, ctx), resource);
			break;
			case "Items_PricelistPerClientController":
				resource = "Items_PricelistPerClient.fxml";
				controller = new FxmlController(new Items_PricelistPerClientController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ComparativePricelistPerClientController":
				resource = "Items_ComparativePricelistPerClient.fxml";
				controller = new FxmlController(new Items_ComparativePricelistPerClientController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ComparativePricelistPerSupplierController":
				resource = "Items_ComparativePricelistPerSupplier.fxml";
				controller = new FxmlController(new Items_ComparativePricelistPerSupplierController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ComparativePricelistPerSupplierAddEditController":
				resource = "Items_ComparativePricelistPerSupplierAddEdit.fxml";
				controller = new FxmlController(new Items_ComparativePricelistPerSupplierAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Items_CalculationController":
				resource = "Items_Calculation.fxml";
				controller = new FxmlController(new Items_CalculationController(controllerParam, mode, ctx), resource);
			break;
			case "Items_CalculationAddEditController":
				resource = "Items_CalculationAddEdit.fxml";
				controller = new FxmlController(new Items_CalculationAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ItemTypeController":
				resource = "Items_ItemType.fxml";
				controller = new FxmlController(new Items_ItemTypeController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ItemStatusController":
				resource = "Items_ItemStatus.fxml";
				controller = new FxmlController(new Items_ItemStatusController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ItemGroupController":
				resource = "Items_ItemGroup.fxml";
				controller = new FxmlController(new Items_ItemGroupController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ItemSubgroupController":
				resource = "Items_ItemSubgroup.fxml";
				controller = new FxmlController(new Items_ItemSubgroupController(controllerParam, mode, ctx), resource);
			break;

			case "Items_ItemSetController":
				resource = "Items_ItemSet.fxml";
				controller = new FxmlController(new Items_ItemSetController(), resource);
			break;
			case "Items_ItemAccessoryController":
				resource = "Items_ItemAccessory.fxml";
				controller = new FxmlController(new Items_ItemAccessoryController(), resource);
			break;
			case "Items_ItemAddEditController":
				resource = "Items_ItemAddEdit.fxml";
				controller = new FxmlController(new Items_ItemAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ItemSelectAddEditController": // Used when we want to select/add/edit item based on passed Client ID
				resource = "Items_ItemSelectAddEdit.fxml";
				controller = new FxmlController(new Items_ItemSelectAddEditController(controllerParam, mode, ctx), resource);
			break;
			
			case "Items_ItemBarcodeController":
				resource = "Items_ItemBarcode.fxml";
				controller = new FxmlController(new Items_ItemBarcodeController(controllerParam, mode, ctx), resource);
			break;
			case "Items_ItemBarcodeAddEditController":
				resource = "Items_ItemBarcodeAddEdit.fxml";
				controller = new FxmlController(new Items_ItemBarcodeAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Items_SelectedItemsAddEditController":
				resource = "Items_SelectedItemsAddEdit.fxml";
				controller = new FxmlController(new Items_SelectedItemsAddEditController(controllerParam, mode, ctx), resource);
			break;
			
			/******************************************
			 * LOOKUP
			 *****************************************/
			case "Lookup_CurrencyController":
				resource = "Lookup_Currency.fxml";
				controller = new FxmlController(new Lookup_CurrencyController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_CountryController":
				resource = "Lookup_Country.fxml";
				controller = new FxmlController(new Lookup_CountryController(controllerParam, mode, ctx), resource);
			break;	
			case "Lookup_PaymentTermController":
				resource = "Lookup_PaymentTerm.fxml";
				controller = new FxmlController(new Lookup_PaymentTermController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_DeliveryTypeController":
				resource = "Lookup_DeliveryType.fxml";
				controller = new FxmlController(new Lookup_DeliveryTypeController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_PaymentTypeController":
				resource = "Lookup_PaymentType.fxml";
				controller = new FxmlController(new Lookup_PaymentTypeController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_SaleTypeController":
				resource = "Lookup_SaleType.fxml";
				controller = new FxmlController(new Lookup_SaleTypeController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_VatGroupController":
				resource = "Lookup_VatGroup.fxml";
				controller = new FxmlController(new Lookup_VatGroupController(controllerParam, mode, ctx), resource);
			break;			
			case "Lookup_CustomsGroupController":
				resource = "Lookup_CustomsGroup.fxml";
				controller = new FxmlController(new Lookup_CustomsGroupController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_ApplicationUserController":
				resource = "Lookup_ApplicationUser.fxml";
				controller = new FxmlController(new Lookup_ApplicationUserController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_ApplicationRoleController":
				resource = "Lookup_ApplicationRole.fxml";
				controller = new FxmlController(new Lookup_ApplicationRoleController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_BankAccountController":
				resource = "Lookup_BankAccount.fxml";
				controller = new FxmlController(new Lookup_BankAccountController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_CashRegisterController":
				resource = "Lookup_CashRegister.fxml";
				controller = new FxmlController(new Lookup_CashRegisterController(controllerParam, mode, ctx), resource);
			break;	
			case "Lookup_BankController":
				resource = "Lookup_Bank.fxml";
				controller = new FxmlController(new Lookup_BankController(controllerParam, mode, ctx), resource);
			break;	
			case "Lookup_MeasurementUnitController":
				resource = "Lookup_MeasurementUnit.fxml";
				controller = new FxmlController(new Lookup_MeasurementUnitController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_PackageTypeController":
				resource = "Lookup_PackageType.fxml";
				controller = new FxmlController(new Lookup_PackageTypeController(controllerParam, mode, ctx), resource);
			break;	
			
			case "Lookup_CurrencyAddEditController":
				resource = "../view/Lookup_CurrencyAddEdit.fxml";
				controller = new FxmlController(new Lookup_CurrencyAddEditController((Currency) controllerParam), resource);
			break;
			case "Lookup_CountryAddEditController":
				resource = "../view/Lookup_CountryAddEdit.fxml";
				controller = new FxmlController(new Lookup_CountryAddEditController((Country) controllerParam), resource);
			break;
			case "Lookup_PaymentTermAddEditController":
				resource = "../view/Lookup_PaymentTermAddEdit.fxml";
				controller = new FxmlController(new Lookup_PaymentTermAddEditController((PaymentTerm) controllerParam), resource);
			break;
			case "Lookup_DeliveryTypeAddEditController":
				resource = "../view/Lookup_DeliveryTypeAddEdit.fxml";
				controller = new FxmlController(new Lookup_DeliveryTypeAddEditController((DeliveryType) controllerParam), resource);
			break;
			case "Lookup_PaymentTypeAddEditController":
				resource = "../view/Lookup_PaymentTypeAddEdit.fxml";
				controller = new FxmlController(new Lookup_PaymentTypeAddEditController((PaymentType) controllerParam), resource);
			break;
			case "Lookup_SaleTypeAddEditController":
				resource = "../view/Lookup_SaleTypeAddEdit.fxml";
				controller = new FxmlController(new Lookup_SaleTypeAddEditController((SaleType) controllerParam), resource);
			break;
			case "Lookup_VatGroupAddEditController":
				resource = "../view/Lookup_VatGroupAddEdit.fxml";
				controller = new FxmlController(new Lookup_VatGroupAddEditController((VatGroup) controllerParam), resource);
			break;
			case "Lookup_CustomsGroupAddEditController":
				resource = "../view/Lookup_CustomsGroupAddEdit.fxml";
				controller = new FxmlController(new Lookup_CustomsGroupAddEditController((CustomsGroup) controllerParam), resource);
			break;
			case "Lookup_ApplicationUserAddEditController":
				resource = "../view/Lookup_ApplicationUserAddEdit.fxml";
				controller = new FxmlController(new Lookup_ApplicationUserAddEditController((ApplicationUser) controllerParam), resource);
			break;
			case "Lookup_ApplicationRoleAddEditController":
				resource = "../view/Lookup_ApplicationRoleAddEdit.fxml";
				controller = new FxmlController(new Lookup_ApplicationRoleAddEditController((ApplicationRole) controllerParam), resource);
			break;
			case "Lookup_BankAccountAddEditController":
				resource = "../view/Lookup_BankAccountAddEdit.fxml";
				controller = new FxmlController(new Lookup_BankAccountAddEditController((BankAccount) controllerParam), resource);
			break;
			case "Lookup_CashDeskAddEditController":
				resource = "../view/Lookup_CashDeskAddEdit.fxml";
				controller = new FxmlController(new Lookup_CashDeskAddEditController((CashDesk) controllerParam), resource);
			break;
			case "Lookup_BankAddEditController":
				resource = "../view/Lookup_BankAddEdit.fxml";
				controller = new FxmlController(new Lookup_BankAddEditController((Bank) controllerParam), resource);
			break;
			case "Lookup_MeasurementUnitAddEditController":
				resource = "../view/Lookup_MeasurementUnitAddEdit.fxml";
				controller = new FxmlController(new Lookup_MeasurementUnitAddEditController((MeasurementUnit) controllerParam), resource);
			break;
			case "Lookup_PackageTypeAddEditController":
				resource = "../view/Lookup_PackageTypeAddEdit.fxml";
				controller = new FxmlController(new Lookup_PackageTypeAddEditController((PackageType) controllerParam), resource);
			break;
			case "Lookup_ClientBankAccountController":
				resource = "../view/Lookup_ClientBankAccount.fxml";
				controller = new FxmlController(new Lookup_ClientBankAccountController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_ClientBankAccountAddEditController":
				resource = "../view/Lookup_ClientBankAccountAddEdit.fxml";
				controller = new FxmlController(new Lookup_ClientBankAccountAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_BillOfExchangeController":
				resource = "../view/Lookup_BillOfExchange.fxml";
				controller = new FxmlController(new Lookup_BillOfExchangeController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_BillOfExchangeAddEditController":
				resource = "../view/Lookup_BillOfExchange_AddEdit.fxml";
				controller = new FxmlController(new Lookup_BillOfExchangeAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_ClientContractController":
				resource = "../view/Lookup_ClientContract.fxml";
				controller = new FxmlController(new Lookup_ClientContractController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_ClientContractAddEditController":
				resource = "../view/Lookup_ClientContract_AddEdit.fxml";
				controller = new FxmlController(new Lookup_ClientContractAddEditController(controllerParam, mode, ctx), resource);
			break;
			
			case "Lookup_ClientGroupController":
				resource = "../view/Lookup_ClientGroup.fxml";
				controller = new FxmlController(new Lookup_ClientGroupController(controllerParam, mode, ctx), resource);
			break;
			
			case "Lookup_ClientCategoryController":
				resource = "../view/Lookup_ClientCategory.fxml";
				controller = new FxmlController(new Lookup_ClientCategoryController(controllerParam, mode, ctx), resource);
			break;
			
			case "Lookup_ClientGroupAddEditController":
				resource = "../view/Lookup_ClientGroupAddEdit.fxml";
				controller = new FxmlController(new Lookup_ClientGroupAddEditController(controllerParam, mode, ctx), resource);
			break;
			
			case "Lookup_ClientCategoryAddEditController":
				resource = "../view/Lookup_ClientCategoryAddEdit.fxml";
				controller = new FxmlController(new Lookup_ClientCategoryAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_DiscountController":
				resource = "../view/Lookup_Discount.fxml";
				controller = new FxmlController(new Lookup_DiscountController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_DiscountAddEditController":
				resource = "../view/Lookup_DiscountAddEdit.fxml";
				controller = new FxmlController(new Lookup_DiscountAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_BrandController":
				resource = "../view/Lookup_Brand.fxml";
				controller = new FxmlController(new Lookup_BrandController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_BrandAddEditController":
				resource = "../view/Lookup_BrandAddEdit.fxml";
				controller = new FxmlController(new Lookup_BrandAddEditController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_DbiniController":
				resource = "../view/Lookup_Dbini.fxml";
				controller = new FxmlController(new Lookup_DbiniController(controllerParam, mode, ctx), resource);
			break;
			case "Lookup_DbiniAddEditController":
				resource = "../view/Lookup_DbiniAddEdit.fxml";
				controller = new FxmlController(new Lookup_DbiniAddEditController(controllerParam, mode, ctx), resource);
			break;


			/******************************************
			 * DOBANOVCI
			 *****************************************/
			case "Dobanovci_GoodsInController":
				resource = "Dobanovci_GoodsIn.fxml";
				controller = new FxmlController(new Dobanovci_GoodsInController(controllerParam, mode, ctx), resource);
			break;
			case "Dobanovci_GoodsOutController":
				resource = "Dobanovci_GoodsOut.fxml";
				controller = new FxmlController(new Dobanovci_GoodsOutController(controllerParam, mode, ctx), resource);
			break;
			
			case "Dobanovci_PaletteDocumentAddEditController":
				resource = "Dobanovci_PaletteDocumentAddEdit.fxml";
				controller = new FxmlController(new Dobanovci_PaletteDocumentAddEditController(controllerParam, mode), resource);
			break;

			case "Dobanovci_PaletteAddEditController":
				resource = "Dobanovci_PaletteAddEdit.fxml";
				controller = new FxmlController(new Dobanovci_PaletteAddEditController(controllerParam, mode), resource);
			break;
			case "Dobanovci_PaletteGroupAddEditController":
				resource = "Dobanovci_PaletteGroupAddEdit.fxml";
				controller = new FxmlController(new Dobanovci_PaletteGroupAddEditController(controllerParam, mode), resource);
			break;
			case "Dobanovci_PaletteItemAddEditController":
				resource = "Dobanovci_PaletteItemAddEdit.fxml";
				controller = new FxmlController(new Dobanovci_PaletteItemAddEditController(controllerParam, mode), resource);
			break;
			case "WebFormController":
				resource = "WebForm.fxml";
				controller = new FxmlController(new WebFormController(controllerParam, mode, ctx), resource);
			break;			

			
			/******************************************
			 * FAST MAIL
			 *****************************************/
			case "Fastmail_SendItemController":
				resource = "Fastmail_SendItem.fxml";
				controller = new FxmlController(new Fastmail_SendItemController(controllerParam, mode, ctx), resource);
			break;
			case "Fastmail_SendMailController":
				resource = "Fastmail_SendMail.fxml";
				controller = new FxmlController(new Fastmail_SendMailController(controllerParam, mode, ctx), resource);
			break;
			case "Fastmail_PrintPackedParcelController":
				resource = "Fastmail_PrintPackedParcel.fxml";
				controller = new FxmlController(new Fastmail_PrintPackedParcelController(controllerParam, mode, ctx), resource);
			break;
			case "Fastmail_PrintSentParcelController":
				resource = "Fastmail_PrintSentParcel.fxml";
				controller = new FxmlController(new Fastmail_PrintSentParcelController(controllerParam, mode, ctx), resource);
			break;
			case "Fastmail_ImportMailController":
				resource = "Fastmail_ImportMail.fxml";
				controller = new FxmlController(new Fastmail_ImportMailController(controllerParam, mode, ctx), resource);
			break;
			case "Fastmail_SendInitialItemCountController":
				resource = "Fastmail_SendInitialItemCount.fxml";
				controller = new FxmlController(new Fastmail_SendInitialItemCountController(controllerParam, mode, ctx), resource);
			break;
			case "Fastmail_ControlPttOutController":
				resource = "Fastmail_ControlPttOut.fxml";
				controller = new FxmlController(new Fastmail_ControlPttOutController(controllerParam, mode, ctx), resource);
			break;
			case "Fastmail_UpdateDeliveredCountController":
				resource = "Fastmail_UpdateDeliveredCount.fxml";
				controller = new FxmlController(new Fastmail_UpdateDeliveredCountController(controllerParam, mode, ctx), resource);
			break;
			case "Fastmail_ControlDeliveryOutController":
				resource = "Fastmail_ControlDeliveryOut.fxml";
				controller = new FxmlController(new Fastmail_ControlDeliveryOutController(controllerParam, mode, ctx), resource);
			break;
			
			
			/******************************************
			 * COMPONENTS
			 *****************************************/
			case "ItemDescriptionController":
				resource = "../component/CSTextAreaForm.fxml";
				controller = new FxmlController(new CSTextAreaForm<Object>(controllerParam, mode, ctx), resource);
			break;
			case "ItemRequiredDoumentsController":
				resource = "../component/CSTextAreaForm.fxml";
				controller = new FxmlController(new CSTextAreaForm<Object>(controllerParam, mode, ctx), resource);
			break;
			case "ItemTechnicalCharacteristicsController":
				resource = "../component/CSTextAreaForm.fxml";
				controller = new FxmlController(new CSTextAreaForm<Object>(controllerParam, mode, ctx), resource);
			break;

			case "CSPhotoViewDetail":
				resource = "../component/CSPhotoViewDetail.fxml";
				controller = new FxmlController(new CSPhotoViewDetail(controllerParam), resource);
			break;


			/******************************************
			 * RETURN
			 *****************************************/
			case "Return_ByBarcodeController":
				resource = "Return_ByBarcode.fxml";
				controller = new FxmlController(new Return_ByBarcodeController(controllerParam, mode, ctx), resource);
			break;
			case "Return_ByItemIdController":
				resource = "Return_ByItemId.fxml";
				controller = new FxmlController(new Return_ByItemIdController(controllerParam, mode, ctx), resource);
			break;
			case "Return_ByDomItemIdController":
				resource = "Return_ByDomItemId.fxml";
				controller = new FxmlController(new Return_ByDomItemIdController(controllerParam, mode, ctx), resource);
			break;			
			
			case "Return_ByBarcode_HeaderAddEditController":
				resource = "Return_ByBarcode_HeaderAddEdit.fxml";
				controller = new FxmlController(new Return_ByBarcode_HeaderAddEditController(), resource);
			break;
			case "Return_ByItemId_HeaderAddEditController":
				resource = "Return_ByItemId_HeaderAddEdit.fxml";
				controller = new FxmlController(new Return_ByItemId_HeaderAddEditController(), resource);
			break;
			case "Return_ByDomItemId_HeaderAddEditController":
				resource = "Return_ByDomItemId_HeaderAddEdit.fxml";
				controller = new FxmlController(new Return_ByDomItemId_HeaderAddEditController(), resource);
			break;
			case "Return_ByBarcode_DetailAddEditController":
				resource = "Return_ByBarcode_DetailAddEdit.fxml";
				controller = new FxmlController(new Return_ByBarcode_DetailAddEditController(), resource);
			break;
			case "Return_ByItemId_DetailAddEditController":
				resource = "Return_ByItemId_DetailAddEdit.fxml";
				controller = new FxmlController(new Return_ByItemId_DetailAddEditController(), resource);
			break;
			case "Return_ByDomItemId_DetailAddEditController":
				resource = "Return_ByDomItemId_DetailAddEdit.fxml";
				controller = new FxmlController(new Return_ByDomItemId_DetailAddEditController(), resource);
			break;

			
			/******************************************
			 * ACCOUNT
			 * Ovo mi vise lici na neke racune tipa bill
			 * , a ne account ... verovatno cu promeniti
			 *****************************************/
			case "Account_AccountsController":
				resource = "Account_Accounts.fxml";
				controller = new FxmlController(new Account_AccountsController(controllerParam, mode, ctx), resource);
			break;
			case "Account_ClientPaymentController":
				resource = "Account_ClientPayment.fxml";
				controller = new FxmlController(new Account_ClientPaymentController(controllerParam, mode, ctx), resource);
			break;	
			

			/******************************************
			 * APPLICATION LOG
			 * dugme je na dnu sa desne strane
			 *****************************************/
			case "ApplicationLogController":
				resource = "ApplicationLog.fxml";
				controller = new FxmlController(new ApplicationLogController(controllerParam, mode, ctx), resource);
			break;
			
			
			default:
				//controller = null;
			break;
		}


		return controller;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ControllerFactory.ctx = applicationContext;
		
	}
}
