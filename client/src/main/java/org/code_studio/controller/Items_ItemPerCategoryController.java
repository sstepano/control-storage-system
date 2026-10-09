package org.code_studio.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSPhotoView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.RowSelectionChangedEvent;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.component.ui.CSItemTable;
import org.code_studio.component.ui.CSItemWarehouseTable;
import org.code_studio.component.ui.CSOrdersDetailTable;
import org.code_studio.database.Client;
import org.code_studio.database.F6SelectedItem;
import org.code_studio.database.Item;
import org.code_studio.database.ItemAccessory;
import org.code_studio.database.ItemBranch;
import org.code_studio.database.ItemCatalog;
import org.code_studio.database.ItemGroup;
import org.code_studio.database.ItemSet;
import org.code_studio.database.ItemSimple;
import org.code_studio.database.ItemSubgroup;
import org.code_studio.database.ItemType;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

public class Items_ItemPerCategoryController extends BaseController implements Initializable {

	@FXML private CSClientTable tblSupplier;
	@FXML private CSTable <ItemCatalog> tblCatalog;
	@FXML private CSTable <ItemBranch> tblItemBranch;
	@FXML private CSTable <ItemGroup> tblItemGroup;
	@FXML private CSTable <ItemSubgroup> tblItemSubgroup;
	@FXML private CSItemTable tblItem;
	@FXML private CSTable<F6SelectedItem> tblSelectedItems;
	@FXML private CSTable <ItemType> tblItemType;
	@FXML private CSItemWarehouseTable tblWarehouse;
	@FXML private Label lblItemDescription;
	@FXML private Button btnItemDescriptionView;
	@FXML private TextArea txtaItemDescription;
	@FXML private Label lblRequiredDocuments;
	@FXML private Button btnRequiredDocumentsView;
	@FXML private TextArea txtaRequiredDocuments;
	@FXML private Label lblTechnicalCharacteristics;
	@FXML private Button btnTechnicalCharacteristicsView;
	@FXML private TextArea txtaTechnicalCharacteristics;
	@FXML private CSTable <ItemSet> tblItemSet;
	@FXML private CSTable <ItemAccessory> tblItemAccessory;
	@FXML private CSOrdersDetailTable tblOrder;
	@FXML private CSPhotoView pvImage;
	@FXML private Button btnSupplierDiscount;
	@FXML private Button btnCatalogDiscount;
	@FXML private Button btnItemBranchDiscount;
	@FXML private Button btnItemGroupDiscount;
	@FXML private Button btnSubgroupDiscount;
	@FXML private Button btnItemTypeDiscount;
	@FXML private Button btnItemDiscount;
	@FXML private Button btnClientSelect;
	@FXML private Button btnOffer;
	@FXML private Button btnWholesale;
	@FXML private Button btnRevers;
	@FXML private Button btnReservation;
	@FXML private Button btnPreinvoice;
	@FXML private Button btnAdvancePayment;
	@FXML private CSTextField tfClientId;
	@FXML private CSTextField tfClientName;
	@FXML private CSTextField tfClientAddress;
	@FXML private CSTextField tfClientPoBoxAndCity;
	@FXML private TabPane tpClientItems;
	
	private ApplicationContext ctx;
	final String itemCatalogURL           = "/itemCatalog/allByClientIdPageable/";
	final String itemBranchURL            = "/itemBranch/allByClientId/";
	final String itemGroupURL             = "/itemGroup/allByClientId/";
	final String itemSubgroupByClientURL  = "/itemSubgroup/allByClientIdPageable/";
	final String itemSubgroupByCatalogURL = "/itemSubgroup/allByCatalogIdPageable/";
	final String itemSubgroupByBranchURL  = "/itemSubgroup/allByBranchIdPageable/";
	final String itemSubgroupByGroupURL   = "/itemSubgroup/allByGroupIdPageable/";
	
	final String itemByClientURL   = "/item/allByClientIdPageable/";
	final String itemByCatalogURL  = "/item/allByCatalogIdPageable/";
	final String itemByBranchURL   = "/item/allByBranchIdPageable/";
	final String itemByGroupURL    = "/item/allByGroupIdPageable/";
	final String itemBySubgroupURL = "/item/allBySubgroupIdPageable/";
	final String itemByTypeURL     = "/item/allByTypeIdPageable/";
	
	final String itemSetByItemIdURL = "/itemSet/allByItemId/";
	final String itemAccessoryByParentItemIdURL = "/itemAccessory/allByParentItemId/";
	
	final String urlItemType        = "/itemType/allPageable/";

	private AtomicInteger itemCatalogPageId;
	private AtomicInteger itemSubgroupPageId;
	private AtomicInteger itemPageId;
	private AtomicInteger itemTypePageId;

	private CSRestService <ItemCatalog> rsItemCatalog;
	private CSRestService <ItemBranch> rsItemBranch;
	private CSRestService <ItemGroup> rsItemGroup;
	private CSRestService <ItemSubgroup> rsItemSubgroup;
	private CSRestService <Item> rsItem;
	private CSRestService <ItemSet> rsItemSet;
	private CSRestService <ItemAccessory> rsItemAccessory;
	private CSRestService <ItemType> rsItemType;
	
	private int mode;
	private Common common;
	private Client client;
	private Client clientSelected;

	public Items_ItemPerCategoryController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
		this.mode = mode;
		this.client = (Client) controllerParam;
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		//TODO: Fill table from Common.selectedItems!!!
		// ovaj mod je za dodeljivanje rabata kupcima po grupi/podgrupi itd
		if (mode == 3) {
			showDiscountButtons(true);
		}
		
		if (client != null) {
			clientSelected = client;
			selectClient();
		}
		
		common = ctx.getBean(Common.class);
		MainController ctrl = ctx.getBean(MainController.class);
		rsItemCatalog = new CSRestService<>(itemCatalogURL);
		rsItemCatalog.setParentTable(tblCatalog);
		rsItemBranch = new CSRestService<>(itemBranchURL);
		rsItemBranch.setParentTable(tblItemBranch);
		rsItemGroup = new CSRestService<>(itemGroupURL);
		rsItemGroup.setParentTable(tblItemGroup);
		rsItemSubgroup = new CSRestService<>(itemSubgroupByClientURL);
		rsItemSubgroup.setParentTable(tblItemSubgroup);
		rsItem = new CSRestService<>(itemByClientURL);
		rsItem.setParentTable(tblItem.csTable);
		rsItemSet = new CSRestService<>(itemSetByItemIdURL);
		rsItemSet.setParentTable(tblItemSet);
		rsItemAccessory = new CSRestService<>(itemAccessoryByParentItemIdURL);
		rsItemAccessory.setParentTable(tblItemAccessory);
		rsItemType = new CSRestService<>(urlItemType);

		itemCatalogPageId = new AtomicInteger(0);
		itemSubgroupPageId = new AtomicInteger(0);
		itemPageId = new AtomicInteger(0);
		itemTypePageId = new AtomicInteger(0);
		
		setLabelPaths(0);

		tblSupplier.csTable.onRowSelectionChanged((_, newRow) -> {
			Client client = (Client) newRow;
			if (client != null) {
				tblItem.setClientId(client.getId().intValue());
				itemCatalogPageId.set(0);
				rsItemCatalog.setUrl(itemCatalogURL + client.getId());
				rsItemCatalog.fetch(itemCatalogPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemCatalog>>(){}, true, () -> {
					tblCatalog.setItems(rsItemCatalog.getDataAsObservableList());
				});

				/*
				rsItemBranch.setUrl(itemBranchURL + client.getId());
				rsItemBranch.fetch(new ParameterizedTypeReference<JsonResponse<ItemBranch>>(){}, () -> {
					tblItemBranch.setItems(rsItemBranch.getDataAsObservableList());
				});
				*/
				
				rsItemGroup.setUrl(itemGroupURL + client.getId());
				rsItemGroup.fetch(new ParameterizedTypeReference<JsonResponse<ItemGroup>>(){}, true, () -> {
					tblItemGroup.setItems(rsItemGroup.getDataAsObservableList());
				});
				
				/*
				itemSubgroupPageId.set(0);
				rsItemSubgroup.setUrl(itemSubgroupByClientURL + client.getId());
				rsItemSubgroup.fetch(itemSubgroupPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemSubgroup>>(){}, () -> {
					tblItemSubgroup.setItems(rsItemSubgroup.getDataAsObservableList());
				});
				tblItemSubgroup.onDataNeeded(() -> {
					rsItemSubgroup.fetch(itemSubgroupPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemSubgroup>>(){}, () -> {
						tblItemSubgroup.addItems(rsItemSubgroup.getDataAsObservableList());
					});
				});
				
				itemPageId.set(0);
				rsItem.setUrl(itemByClientURL + client.getId());
				rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
					tblItem.csTable.setItems(rsItem.getDataAsObservableList());
				});
				*/
			}
		});
		
		tblSupplier.csTable.onFocusChanged((_, newVal) -> {
			if (newVal) {
				setLabelPaths(0);
				tblSupplier.fireEvent(new RowSelectionChangedEvent(tblSupplier.csTable.getSelectedItem(), tblSupplier.csTable.getSelectedItem()));
			}
		});
		
		tblCatalog.onDataNeeded(() -> {
			rsItemCatalog.fetch(itemCatalogPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemCatalog>>(){}, () -> {
				tblCatalog.addItems(rsItemCatalog.getDataAsObservableList());
			});
		});
			
		tblItemSubgroup.onDataNeeded(() -> {
			rsItemSubgroup.fetch(itemSubgroupPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemSubgroup>>(){}, () -> {
				tblItemSubgroup.addItems(rsItemSubgroup.getDataAsObservableList());
			});
		});
		
		/**
		tblItem.csTable.onDataNeeded(() -> {
			rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
				tblItem.csTable.addItems(rsItem.getDataAsObservableList());
			});
		});
		**/


		//CATALOG
		tblCatalog.onRowSelectionChanged((_, newRow) -> {
			////if (oldRow != null) { // do this only if table wasn't previously empty
				ItemCatalog itemCatalog = (ItemCatalog) newRow;
				if (itemCatalog != null ) {
					// Fetchujem branches zajedno sa catalogs, tako da mi ne treba rest call
					if (itemCatalog.getItemBranches().size() > 0) {
						tblItemBranch.setItems(FXCollections.observableList(itemCatalog.getItemBranches()));
					} else {
						tblItemBranch.clear();
					}
					
					itemSubgroupPageId.set(0);
					rsItemSubgroup.setUrl(itemSubgroupByCatalogURL + itemCatalog.getId());
					rsItemSubgroup.fetch(itemSubgroupPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemSubgroup>>(){}, true, () -> {
						tblItemSubgroup.setItems(rsItemSubgroup.getDataAsObservableList());
					});
					
					itemPageId.set(0);
					rsItem.setUrl(itemByCatalogURL + itemCatalog.getId());
					rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, true, () -> {
						tblItem.csTable.setItems(rsItem.getDataAsObservableList());
					});
				} else {
					tblCatalog.clear();
					tblItemBranch.clear();
				}
			////}
		});
		
		tblCatalog.onFocusChanged((_, newValue) -> {
			if (newValue) {
				setLabelPaths(1);
				tblCatalog.fireEvent(new RowSelectionChangedEvent(tblCatalog.getSelectedItem(), tblCatalog.getSelectedItem()));
			}
		});
		
		tblItemBranch.onFocusChanged((_, newValue) -> {
			if (newValue) {
				setLabelPaths(1);
				tblItemBranch.fireEvent(new RowSelectionChangedEvent(tblItemBranch.getSelectedItem(), tblItemBranch.getSelectedItem()));
			}
		});
		
		tblItemGroup.onFocusChanged((_, newValue) -> {
			if (newValue) {
				setLabelPaths(2);
				tblItemGroup.fireEvent(new RowSelectionChangedEvent(tblItemGroup.getSelectedItem(), tblItemGroup.getSelectedItem()));
			}
		});
		
		tblItemType.onFocusChanged((_, newValue) -> {
			if (newValue) {
				tblItemType.fireEvent(new RowSelectionChangedEvent(tblItemType.getSelectedItem(), tblItemType.getSelectedItem()));
				setLabelPaths(3);
			}
		});

		//BRANCH
		tblItemBranch.onRowSelectionChanged((oldRow, newRow) -> {
			ItemBranch itemBranch = (ItemBranch) newRow;
			if (oldRow != null) { // do this only if table wasn't previously empty
				//tblItemSubgroup.setTopLabelText("PODGRUPA");
				itemSubgroupPageId.set(0);
				rsItemSubgroup.setUrl(itemSubgroupByBranchURL + itemBranch.getId());
				rsItemSubgroup.fetch(itemSubgroupPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemSubgroup>>(){}, () -> {
					tblItemSubgroup.setItems(rsItemSubgroup.getDataAsObservableList());
				});
				
				// TODO: VRLO SPORO RADI QUERY NA BAZU ZA OVAJ DEO, tj query iz squirrel radi ok, dok hibernate crkava dok izvrsava
				itemPageId.set(0);
				rsItem.setUrl(itemByBranchURL + itemBranch.getId());
				rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
					tblItem.csTable.setItems(rsItem.getDataAsObservableList());
				});
				/**
				tblItem.csTable.onDataNeeded(() -> {
					rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
						tblItem.csTable.addItems(rsItem.getDataAsObservableList());
					});
				});
				**/
			}
		});
		
		//GROUP
		tblItemGroup.onRowSelectionChanged((oldRow, newRow) -> {
			ItemGroup itemGroup = (ItemGroup) newRow;
			if (oldRow != null) { // do this only if table wasn't previously empty
				tblItemSubgroup.setTopLabelText("PODGRUPE GRUPA");
				itemSubgroupPageId.set(0);
				rsItemSubgroup.setUrl(itemSubgroupByGroupURL + itemGroup.getId());
				rsItemSubgroup.fetch(itemSubgroupPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemSubgroup>>(){}, () -> {
					tblItemSubgroup.setItems(rsItemSubgroup.getDataAsObservableList());
				});
				/**
				tblItemSubgroup.onDataNeeded(() -> {
					rsItemSubgroup.fetch(itemSubgroupPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemSubgroup>>(){}, () -> {
						tblItemSubgroup.addItems(rsItemSubgroup.getDataAsObservableList());
					});
				});
				**/
				
				itemPageId.set(0);
				rsItem.setUrl(itemByBranchURL + itemGroup.getId());
				rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
					tblItem.csTable.setItems(rsItem.getDataAsObservableList());
				});
				/**
				tblItem.csTable.onDataNeeded(() -> {
					rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
						tblItem.csTable.addItems(rsItem.getDataAsObservableList());
					});
				});
				**/
			}
		});
		//SUBGROUP
		tblItemSubgroup.onRowSelectionChanged((oldRow, newRow) -> {
			ItemSubgroup itemGroup = (ItemSubgroup) newRow;
			if (oldRow != null) { // do this only if table wasn't previously empty
				itemPageId.set(0);
				rsItem.setUrl(itemBySubgroupURL + itemGroup.getId());
				rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
					tblItem.csTable.setItems(rsItem.getDataAsObservableList());
				});
				/**
				tblItem.csTable.onDataNeeded(() -> {
					rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
						tblItem.csTable.addItems(rsItem.getDataAsObservableList());
					});
				});
				**/
			}
		});
		
		//ITEM TYPE
		tblItemType.onDataNeeded(() -> {
			rsItemType.fetch(itemTypePageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemType>>(){}, () -> {
				tblItemType.addItems(rsItemType.getDataAsObservableList());
			});
		});

		tblItemType.onRowSelectionChanged((oldRow, newRow) -> {
			if (oldRow != null) {
				ItemType itemType = (ItemType) newRow;
				itemPageId.set(0);
				rsItem.setUrl(itemByTypeURL + itemType.getId());
				rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, true, () -> {
					tblItem.csTable.setItems(rsItem.getDataAsObservableList());
				});
				/**
				tblItem.csTable.onDataNeeded(() -> {
					rsItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
						tblItem.csTable.addItems(rsItem.getDataAsObservableList());
					});
				});
				**/
			}
		});
		
		tblItem.csTable.onRowSelectionChanged((_, newRow) -> {
			Item item = (Item) newRow;
			if (item != null) {
				txtaItemDescription.setText(item.getPurpose()); //TODO: Izmeniti u bazi NAMENA u OPIS tj. DESCRIPTION.
				txtaRequiredDocuments.setText(item.getPurpose()); // Vidi ovaj TODO gore.
				txtaTechnicalCharacteristics.setText(item.getTechnicalCharacteristics());
	
				rsItemSet.setUrl(itemSetByItemIdURL + item.getId());
				rsItemSet.fetch(new ParameterizedTypeReference<JsonResponse<ItemSet>>(){}, () -> {
					tblItemSet.setItems(rsItemSet.getDataAsObservableList());
				});
				
				rsItemAccessory.setUrl(itemAccessoryByParentItemIdURL + item.getId());
				rsItemAccessory.fetch(new ParameterizedTypeReference<JsonResponse<ItemAccessory>>(){}, () -> {
					tblItemAccessory.setItems(rsItemAccessory.getDataAsObservableList());
				});
				pvImage.setImagePath(item.getImagePath());
				
				tblOrder.setItemId(item.getId());
				tblOrder.fill();
				
				tblWarehouse.setItemId(item.getId());
				tblWarehouse.fill();
			} else {
				txtaItemDescription.clear();
				txtaRequiredDocuments.clear();
				txtaTechnicalCharacteristics.clear();
				tblOrder.csTable.clear();
				tblWarehouse.csTable.clear();
				tblItemSet.clear();
				tblItemAccessory.clear();
				pvImage.clearImage();
			}
		});
		
		tblSelectedItems.setAddEditDialog("Items_SelectedItemsAddEditController", tblSelectedItems);
		tblSelectedItems.onRowDoubleClick( ( _ ) -> {
			tblSelectedItems.showDoubleClickDefaultAction = true;
		});
		
		tblItem.csTable.onRowChecked(checkedItem -> {
			ItemSimple item = ((Item) checkedItem).getItemSimple();
			item.setWarehouseId(
					tblWarehouse.csTable.getSelectedItem() != null
					? tblWarehouse.csTable.getSelectedItem().getWarehouseId()
					: 0);
			
			// add ITEM to the selected table
			createAddF6Item(item, 1, 0);
		});
		
		tblItem.csTable.onRowUnchecked(uncheckedItem -> {
			Item item = ((Item) uncheckedItem);
			F6SelectedItem i = tblSelectedItems.findById(item.getId());
			tblSelectedItems.removeItem(i);
			Common.selectedItems.remove(i);
		});
		
		tblSelectedItems.onRowDeleted( row-> {
			F6SelectedItem i = (F6SelectedItem) row;
			Common.selectedItems.remove(i);
		});
		
		tblItemSubgroup.onRowChecked(checkedItem -> {
			ItemSubgroup itemSubgroup = (ItemSubgroup) checkedItem;
			//TODO: missing warehouse!!!
			// add ITEM to the selected table
			createAddF6Item(itemSubgroup, 1, 1);
		});
		
		tblItemSubgroup.onRowUnchecked(uncheckedItem -> {
			ItemSubgroup itemSubgroup = (ItemSubgroup) uncheckedItem;
			F6SelectedItem i = tblSelectedItems.findById(itemSubgroup.getId());
			tblSelectedItems.removeItem(i);
			Common.selectedItems.remove(i);
		});
		
		tblItemGroup.onRowChecked(checkedItem -> {
			ItemGroup itemGroup = (ItemGroup) checkedItem;
			//TODO: missing warehouse!!!
			// add ITEM to the selected table
			createAddF6Item(itemGroup, 1, 2);
		});
		
		tblItemGroup.onRowUnchecked(uncheckedItem -> {
			ItemGroup itemGroup = (ItemGroup) uncheckedItem;
			F6SelectedItem i = tblSelectedItems.findById(itemGroup.getId());
			tblSelectedItems.removeItem(i);
			Common.selectedItems.remove(i);
		});
		
		tblItemType.onRowChecked(checkedItem -> {
			ItemType itemType = (ItemType) checkedItem;
			//TODO: missing warehouse!!!
			// add ITEM to the selected table
			createAddF6Item(itemType, 1, 3);
		});
		
		tblItemType.onRowUnchecked(uncheckedItem -> {
			ItemType itemType = (ItemType) uncheckedItem;
			F6SelectedItem i = tblSelectedItems.findById(itemType.getId());
			tblSelectedItems.removeItem(i);
			Common.selectedItems.remove(i);
		});

		tblItemSet.btnView.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController("Items_ItemSetController", null, 0), tblItemSet, "%label.itemset.text");
		});
		
		tblItemAccessory.btnView.setOnAction( _ -> {
			Common.displayForm(ControllerFactory.getController("Items_ItemAccessoryController", null, 0), tblItemAccessory, "%label.accessories.text");
		});

		btnItemDescriptionView.setOnAction( _ -> {
			List<Object> controllerParam = new ArrayList<Object>();
			controllerParam.add("OPIS ARTIKLA");
			controllerParam.add(txtaItemDescription.getText());
			Common.displayForm(ControllerFactory.getController("ItemDescriptionController", controllerParam, 0), btnItemDescriptionView, "OPIS ARTIKLA");
		});
		
		btnRequiredDocumentsView.setOnAction( _ -> {
			List<Object> controllerParam = new ArrayList<Object>();
			controllerParam.add("POTREBNA DOKUMENTA");
			controllerParam.add(txtaRequiredDocuments.getText());
			Common.displayForm(ControllerFactory.getController("ItemRequiredDoumentsController", controllerParam, 0), btnRequiredDocumentsView, "POTREBNA DOKUMENTA");
		});

		btnTechnicalCharacteristicsView.setOnAction( _ -> {
			List<Object> controllerParam = new ArrayList<Object>();
			controllerParam.add("TEHNIČKE KARAKTERISTIKE");
			controllerParam.add(txtaTechnicalCharacteristics.getText());
			Common.displayForm(ControllerFactory.getController("ItemTechnicalCharacteristicsController", controllerParam, 0), btnTechnicalCharacteristicsView, "TEHNIČKE KARAKTERISTIKE");
		});
		
		tblItemType.btnView.setOnAction( _ -> {
			ctrl.miItemsItemType.fire();			
		});
		
		tblCatalog.btnView.setOnAction( _ -> {
			ctrl.miItemsCatalogBranchSubgroupLink.fire();
		});
		
		tblItemBranch.btnView.setOnAction( _ -> {
			ctrl.miItemsCatalogBranchSubgroupLink.fire();
		});
		
		tblItemSubgroup.btnView.setOnAction( _ -> {
			ctrl.miItemsCatalogBranchSubgroupLink.fire();
		});

		tblOrder.getViewButton().setOnAction( _ -> {
			ctrl.miProcurementOrders.fire();
		});
		
		// Setting discount based on mode
		btnSupplierDiscount.setOnAction( _ -> {
			setDiscount(0, tblSupplier);
		});
		
		btnCatalogDiscount.setOnAction( _ -> {
			setDiscount(1, tblCatalog);
		});
		
		btnItemBranchDiscount.setOnAction( _ -> {
			setDiscount(2, tblItemBranch);
		});
		
		btnItemGroupDiscount.setOnAction( _ -> {
			setDiscount(3, tblItemGroup);
		});
		
		btnSubgroupDiscount.setOnAction( _ -> {
			setDiscount(4, tblItemSubgroup);
		});
		
		btnItemTypeDiscount.setOnAction( _ -> {
			setDiscount(5, tblItemType);
		});
		
		btnItemDiscount.setOnAction( _ -> {
			setDiscount(6, tblItem.csTable);
		});
		
		// User must choose client first. before creating a wholsesale
		btnClientSelect.setOnAction( _ -> {
			Client c = (Client) Common.displayForm(ControllerFactory.getController("Sales_ClientSelectController", null, 0), btnWholesale, "Odabir klijenta");
			clientSelected = c == null ? clientSelected : c;
			if (clientSelected != null) {
				selectClient();
			}
		});
		
		// ako je user odabrao klijenta, tek tada moze da otvara fakturu sa odabranim artiklima
		btnWholesale.setOnAction( _ -> {
			if(clientSelected != null) {
				common.closeForm(btnWholesale.getText());
				common.displayForm(ControllerFactory.getController("Sales_WholesaleController", client, 0), btnWholesale.getText(), btnWholesale.getGraphic(), ctx);
			}
		});
		
		btnOffer.setOnAction( _ -> {
			if(clientSelected != null) {
				common.closeForm(btnOffer.getText());
				common.displayForm(ControllerFactory.getController("Sales_OfferController", client, 0), btnOffer.getText(), btnOffer.getGraphic(), ctx);
			}
		});
		
		btnReservation.setOnAction( _ -> {
			if(clientSelected != null) {
				common.closeForm(btnReservation.getText());
				common.displayForm(ControllerFactory.getController("Sales_WholesaleController", client, 3), btnReservation.getText(), btnReservation.getGraphic(), ctx);
			}
		});
		
		btnPreinvoice.setOnAction( _ -> {
			if(clientSelected != null) {
				common.closeForm(btnPreinvoice.getText());
				common.displayForm(ControllerFactory.getController("Sales_WholesaleController", client, 4), btnPreinvoice.getText(), btnPreinvoice.getGraphic(), ctx);
			}
		});
		
		btnRevers.setOnAction( _ -> {
			if(clientSelected != null) {
				common.closeForm(btnRevers.getText());
				common.displayForm(ControllerFactory.getController("Sales_WholesaleController", client, 5), btnRevers.getText(), btnRevers.getGraphic(), ctx);
			}
		});
		
		btnAdvancePayment.setOnAction( _ -> {
			if(clientSelected != null) {
				common.closeForm(btnAdvancePayment.getText());
				common.displayForm(ControllerFactory.getController("Sales_WholesaleController", client, 6), btnAdvancePayment.getText(), btnAdvancePayment.getGraphic(), ctx);
			}
		});

	} // initialize END
	
	@Override
	public void postInitialize() {
		tblSupplier.refresh();
		rsItemType.fetch(itemTypePageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemType>>(){}, () -> {
			tblItemType.setItems(rsItemType.getDataAsObservableList());
		});
	}

	/**
	 * Shows or hides discount buttons on all tables in the form
	 * These buttons are used if mode is 3, where user adds discount to clients per group/subgroup etc.
	 * @param Boolean show
	 */
	private void showDiscountButtons (Boolean show) {
		btnSupplierDiscount.setVisible(show);
		btnSupplierDiscount.setManaged(show);
		btnCatalogDiscount.setVisible(show);
		btnCatalogDiscount.setManaged(show);
		btnItemBranchDiscount.setVisible(show);
		btnItemBranchDiscount.setManaged(show);
		btnItemGroupDiscount.setVisible(show);
		btnItemGroupDiscount.setManaged(show);
		btnSubgroupDiscount.setVisible(show);
		btnSubgroupDiscount.setManaged(show);
		btnItemTypeDiscount.setVisible(show);
		btnItemTypeDiscount.setManaged(show);
		btnItemDiscount.setVisible(show);
		btnItemDiscount.setManaged(show);
		
		tblSupplier.csTable.btnView.setVisible(!show);
		tblCatalog.btnView.setVisible(!show);
		tblItemBranch.btnView.setVisible(!show);
		tblItemGroup.btnView.setVisible(!show);
		tblItemSubgroup.btnView.setVisible(!show);
		tblItemType.btnView.setVisible(!show);
		//tblOrder.btnView.setVisible(!show);
		tblOrder.setAddButtonVisible(!show);
		tblWarehouse.getViewButton().setVisible(!show);
		tblItemSet.btnView.setVisible(!show);
		tblItemAccessory.btnView.setVisible(!show);
	}
	
	/**
	 * Displays discount form and sets discount based on mode
	 * @param mode
	 */
	private void setDiscount(int mode, Object controllerParam) {
		List<Object> lstControllerParam = new ArrayList<Object>();
		lstControllerParam.add(client);
		lstControllerParam.add(controllerParam);
		Common.displayForm(ControllerFactory.getController("Lookup_DiscountAddEditController", lstControllerParam, mode), tblSupplier, "Dodavanje/Izmena rabata");
	}
	
	
	/**
	 * Selects client and populates the data in the form
	 */
	private void selectClient() {
		client = clientSelected;
		tfClientId.setTextOrEmptyString(clientSelected.getId().toString());
		tfClientName.setTextOrEmptyString(clientSelected.getName());
		tfClientAddress.setTextOrEmptyString(clientSelected.getAddress());
		tfClientPoBoxAndCity.setTextOrEmptyString(clientSelected.getPoBox().toString() + " " + clientSelected.getCity());
		btnOffer.setDisable(false);
		btnWholesale.setDisable(false);
		btnRevers.setDisable(false);
		btnReservation.setDisable(false);
		btnPreinvoice.setDisable(false);
		btnAdvancePayment.setDisable(false);
	}
	
	
	/**
	 * 
	 * @return
	 */
	private void createAddF6Item(Object checkedItem, Integer qty, Integer typeId) {
		Integer itemId = null;
		final Integer[] itemIdFake = {0};
		String itemCodeName = null;
		switch (typeId) {
			case 0:
				ItemSimple item = (ItemSimple) checkedItem;
				itemId = item.getId();
				itemIdFake[0] = itemId;
				itemCodeName = item.getCode();
			break;
			case 1:
				ItemSubgroup itemSubgroup = (ItemSubgroup) checkedItem;
				itemId = itemSubgroup.getId();
				itemIdFake[0] = itemId;
				itemCodeName = itemSubgroup.getName();
			break;
			case 2:
				ItemGroup itemGroup = (ItemGroup) checkedItem;
				itemId = itemGroup.getId();
				itemIdFake[0] = itemId;
				itemCodeName = itemGroup.getName();
			break;
			case 3:
				ItemType itemType = (ItemType) checkedItem;
				itemId = itemType.getId();
				itemIdFake[0] = itemId;
				itemCodeName = itemType.getName();
			break;
		}
		
		if (tblSelectedItems.tableView.getItems().stream().filter(i -> i.getId().equals(itemIdFake[0])).count() == 0) { // ako ne nadje ID itema da je vec dodat, dodaj novi
			F6SelectedItem f6SelectedItem = new F6SelectedItem(itemId, itemCodeName, qty, typeId, checkedItem);
			tblSelectedItems.addItem(f6SelectedItem);
			Common.selectedItems.add(f6SelectedItem);
			tpClientItems.getSelectionModel().selectLast();
		} else {
			Common.ShowNotification("GREŠKA", String.format("%s je već dodat u listu!", itemId), true);
		}
	}
	
	/**
	 * Sets red labels on tables, based on selected path
	 * mode 0: Client -> Catalog -> Branch -> Subgroup -> Item
	 * mode 1: Client -> Catalog -> Subgroup -> Item
	 * mode 2: ItemType -> Item
	 * Bane razgovor: 
	 * 1. Client -> Catalog -> Branch -> Subgroup -> Item
	 * 2. 1: Client -> Group -> Subgroup -> Item
	 */
	private void setLabelPaths(int mode) {
		tblSupplier.csTable.topLabel.getStyleClass().remove("red-text");
		tblCatalog.topLabel.getStyleClass().remove("red-text");
		tblItemBranch.topLabel.getStyleClass().remove("red-text");
		tblItemGroup.topLabel.getStyleClass().remove("red-text");
		tblItemSubgroup.topLabel.getStyleClass().remove("red-text");
		tblItemType.topLabel.getStyleClass().remove("red-text");
		
		switch (mode) {
			case 0:
				tblCatalog.topLabel.getStyleClass().add("red-text");
				tblItemBranch.topLabel.getStyleClass().add("red-text");
				tblItemSubgroup.topLabel.getStyleClass().add("red-text");
				// Ako kliknemo klijenta, treba da prikazujemo sve njegove branse, sto treba da se vidi na labelu grida
				tblItemBranch.setTopLabelText("BRANŠA KLIJENTA");
				tblItemSubgroup.setTopLabelText("PODGRUPA KLIJENTA");
			break;
			case 1:
				tblCatalog.topLabel.getStyleClass().add("red-text");
				tblItemBranch.topLabel.getStyleClass().add("red-text");
				tblItemSubgroup.topLabel.getStyleClass().add("red-text");
				tblItemBranch.setTopLabelText("BRANŠA");
				tblItemSubgroup.setTopLabelText("PODGRUPA");
			break;
			case 2:
				tblItemGroup.topLabel.getStyleClass().add("red-text");
				tblItemSubgroup.topLabel.getStyleClass().add("red-text");
				tblItemBranch.setTopLabelText("BRANŠA");
				tblItemSubgroup.setTopLabelText("PODGRUPA");
			break;
			case 3:
				tblItemType.topLabel.getStyleClass().add("red-text");
			break;
		}
	}
	
	
}
