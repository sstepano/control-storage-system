package org.code_studio.controller;

import java.util.List;
import javafx.stage.Stage;

import javafx.fxml.FXML;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.scene.Node;
import javafx.scene.control.TextArea;

import org.code_studio.database.Client;
import org.code_studio.database.Country;
import org.code_studio.database.Item;
import org.code_studio.database.ItemStatus;
import org.code_studio.database.ItemType;
import org.code_studio.database.MeasurementUnit;
import org.code_studio.database.PackageType;
import org.code_studio.database.VatGroup;
import org.code_studio.database.InternetCatalog;

import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSPhotoView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.component.ui.CSDialogButtons;

public class Items_ItemAddEditController extends BaseController {
	@FXML CSDialogButtons dialogButtons;

	@FXML CSComboBox<ItemType> cbItemType;
	@FXML CSComboBox<MeasurementUnit> cbMeasurementUnit;
	@FXML CSComboBox<PackageType> cbPackageType;
	@FXML CSComboBox<ItemStatus> cbItemStatus;
	@FXML CSComboBox<VatGroup> cbVatGroup;
	@FXML CSComboBox<Country> cbCountryOfOrigin;
	
	@FXML CSTextField tfId;
	@FXML CSTextField tfCode;
	@FXML CSTextField tfName;
	@FXML CSTextField tfNameEng;
	@FXML CSTextField tfQuantity;
	@FXML CSTextField tfWeight;
	@FXML CSTextField tfVolume;
	@FXML CSTextField tfPurchasePrice;
	@FXML CSTextField tfPurchasePriceSpec;
	@FXML CSTextField tfMinQty;
	@FXML CSTextField tfFree; // izgleda suvisno, pricati sa B.
	@FXML CSTextField tfFreeQty;
	@FXML CSTextField tfBarcode;
	@FXML CSTextField tfTariffNumberSER;
	@FXML CSTextField tfTariffNumberEU;
	@FXML CSTextField tfTariffNumberUS;
	@FXML CSTextField tfPackageQty;
	@FXML CSTextField tfMasterBoxQty;
	@FXML CSTextField tfCardboardUnits;
	@FXML CSTextField tfPaletteUnits;
	@FXML CSTextField tfContainer20InchQty;
	
	@FXML TextArea taCommercial; // ovde se uzima na UI kao POTREBNA DOKUMENTA ... skroz sve izmesano
	@FXML TextArea taTechCharasteristics;
	@FXML TextArea taDescription;
	@FXML CSPhotoView phItemImage;
	@FXML CSDatePicker dtExpirationDate;
	@FXML CSComboBox<InternetCatalog> cbInternetCatalog;
	@FXML 
	
	private int mode;
	private CSTable<Item> tblItem;
	private Item item;
	
	private CSClientTable tblSupplier;
	private Client supplier;
	
	private final String urlAddOrUpdateItem = "/item";
	private CSRestService<Item> rsAddOrUpdateItem;
	
	private CSRestService<ItemType> rsItemType;
	private CSRestService<MeasurementUnit> rsMeasurementUnit;
	private CSRestService<PackageType> rsPackageType;
	private CSRestService<ItemStatus> rsItemStatus;
	private CSRestService<VatGroup> rsVatGroup;
	private CSRestService<Country> rsCountryOfOrigin;
	private CSRestService<InternetCatalog> rsInternetCatalog;
	
	@SuppressWarnings("unchecked")
	public Items_ItemAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		List<Object> lstControllerParams = (List<Object>) controllerParam;
		this.tblItem = (CSTable<Item>) lstControllerParams.get(0);
		this.item = this.tblItem.getSelectedItem();
		
		this.tblSupplier = (CSClientTable) lstControllerParams.get(1);
		supplier = tblSupplier.csTable.getSelectedItem();
	}

	public void initialize() {
		try {
			rsAddOrUpdateItem = new CSRestService<Item>(urlAddOrUpdateItem);
			rsItemType = new CSRestService<ItemType>("/itemType");
			rsMeasurementUnit = new CSRestService<MeasurementUnit>("/measurementUnit");
			rsPackageType = new CSRestService<PackageType>("/packageType");
			rsItemStatus = new CSRestService<ItemStatus>("/itemStatus");
			rsVatGroup = new CSRestService<VatGroup>("/vatGroup");
			rsCountryOfOrigin = new CSRestService<Country>("/country");
			rsInternetCatalog = new CSRestService<InternetCatalog>("/internetCatalog/findAllByOrderByOrdinalOrderAsc");
			
			rsItemType.fetch(new ParameterizedTypeReference<JsonResponse<ItemType>>() {});
			rsMeasurementUnit.fetch(new ParameterizedTypeReference<JsonResponse<MeasurementUnit>>() {});
			rsPackageType.fetch(new ParameterizedTypeReference<JsonResponse<PackageType>>() {});
			rsItemStatus.fetch(new ParameterizedTypeReference<JsonResponse<ItemStatus>>() {});
			rsVatGroup.fetch(new ParameterizedTypeReference<JsonResponse<VatGroup>>() {});
			rsCountryOfOrigin.fetch(new ParameterizedTypeReference<JsonResponse<Country>>() {});
			rsInternetCatalog.fetch(new ParameterizedTypeReference<JsonResponse<InternetCatalog>>() {});
			
			cbItemType.setItemsAndSelectFirstItem(rsItemType.getDataAsObservableList());
			cbMeasurementUnit.setItemsAndSelectFirstItem(rsMeasurementUnit.getDataAsObservableList());
			cbPackageType.setItemsAndSelectFirstItem(rsPackageType.getDataAsObservableList());
			cbItemStatus.setItemsAndSelectFirstItem(rsItemStatus.getDataAsObservableList());
			cbVatGroup.setItemsAndSelectFirstItem(rsVatGroup.getDataAsObservableList());
			cbCountryOfOrigin.setItemsAndSelectFirstItem(rsCountryOfOrigin.getDataAsObservableList());
			cbInternetCatalog.setItemsAndSelectFirstItem(rsInternetCatalog.getDataAsObservableList());
			
			if (mode == 0) { //add
				this.item = new Item();
				
				if (supplier != null) {
					item.setClientId(supplier.getId().intValue());
				} else {
					tblItem.btnAdd.setDisable(true);
					tblItem.btnEdit.setDisable(true);
				}
			} else { //edit
				tfId.setText(item.getId().toString());
				tfCode.setText(item.getCode());
				tfName.setText(item.getName());
				tfNameEng.setText(item.getNameEng());
				//tfQuantity.setText(item.getQty); // NEMA KOLICINA, u RM nigde nije podeseno pa nisam migrirao.
				tfWeight.setText(item.getWeight() == null ? "0" : item.getWeight().toString());
				tfVolume.setText(item.getVolume() == null ? "0" : item.getVolume().toString());
				tfPurchasePrice.setText(item.getPurchasePrice() == null ? "0" : item.getPurchasePrice().toString());
				tfPurchasePriceSpec.setText(item.getPurchasePriceSpec() == null ? "0" : item.getPurchasePriceSpec().toString());
				tfMinQty.setText(item.getMinQty() == null ? "0" : item.getMinQty().toString());
				//Besplatni ... nisam migrirao, nema info nigde u RM
				tfFreeQty.setText(item.getFreeQty() == null ? "0" : item.getFreeQty().toString());
				//barkod, nisam jos implementirao
				//tarifa SRB, implementirano kao grupa, moram da vucem iz lookup tabele
				tfTariffNumberEU.setText(item.getTariffNumberEu());
				tfTariffNumberUS.setText(item.getTariffNumberUs());
				tfPackageQty.setText(item.getPackageQty().toString());
				tfMasterBoxQty.setText(item.getMasterboxUnitQty() == null ? "0" : item.getMasterboxUnitQty().toString());
				tfCardboardUnits.setText(item.getCardboardUnitQty() == null ? "0" : item.getCardboardUnitQty().toString());
				tfPaletteUnits.setText(item.getPaletteUnitQty() == null ? "0" : item.getPaletteUnitQty().toString());
				tfContainer20InchQty.setText(item.getContainer20inchQty() == null ? "0" : item.getContainer20inchQty().toString());
				
				taCommercial.setText(item.getCommercial());
				taTechCharasteristics.setText(item.getTechnicalCharacteristics());
				taDescription.setText(item.getPurpose()); // description je PURPOSE, takodje izmesano
				
				cbItemType.selectByItemId(item.getTypeId());
				cbMeasurementUnit.selectByItemId(item.getMeasurementUnitId());
				cbPackageType.selectByItemId(item.getPackageTypeId());
				cbItemStatus.selectByItemId(item.getStatusId());
				//NISAM MIGRIRAO cbVatGroup.selectByItemId(item.getVatGroupId());
				cbCountryOfOrigin.selectByItemId(item.getCountryOfOriginId());
				if (item.getExpirationDate() != null) {
					dtExpirationDate.setValue(item.getExpirationDate());
				}
				cbInternetCatalog.selectByItemId(item.getInternetCatalogId());
				phItemImage.setImagePath(item.getImagePath());
			}
	
			dialogButtons.getSaveButton().setOnAction( e-> {
				item.setCode(tfCode.getTextOrNullIfEmpty());
				item.setName(tfName.getTextOrNullIfEmpty());
				item.setNameEng(tfNameEng.getTextOrNullIfEmpty());
				item.setWeight(tfWeight.getTextAsBigDecimal());
				item.setVolume(tfVolume.getTextAsBigDecimal());
				item.setPurchasePrice(tfPurchasePrice.getTextAsBigDecimal());
				item.setPurchasePriceSpec(tfPurchasePriceSpec.getTextAsBigDecimal());
				item.setMinQty(tfMinQty.getTextAsInteger());
				item.setFreeQty(tfFreeQty.getTextAsInteger());
				//tarirff SRB
				item.setTariffNumberEu(tfTariffNumberEU.getTextOrNullIfEmpty());
				item.setTariffNumberUs(tfTariffNumberUS.getTextOrNullIfEmpty());
				item.setMasterboxUnitQty(tfMasterBoxQty.getTextAsInteger());
				item.setCardboardUnitQty(tfCardboardUnits.getTextAsInteger());
				item.setPaletteUnitQty(tfPaletteUnits.getTextAsBigDecimal());
				item.setContainer20inchQty(tfContainer20InchQty.getTextAsBigDecimal());
				
				item.setCommercial(taCommercial.getText().isEmpty() ? null : taCommercial.getText());
				item.setTechnicalCharacteristics(taTechCharasteristics.getText().isEmpty() ? null : taTechCharasteristics.getText());
				item.setPurpose(taDescription.getText().isEmpty() ? null : taDescription.getText());
				
				item.setTypeId(cbItemType.getSelectionModel().getSelectedIndex() + 1);
				item.setMeasurementUnitId(cbMeasurementUnit.getSelectionModel().getSelectedIndex() + 1);
				item.setPackageTypeId(cbPackageType.getSelectionModel().getSelectedIndex() + 1);
				item.setStatusId(cbItemStatus.getSelectionModel().getSelectedIndex() + 1);
				//NISAM MIGRIRAO cbVatGroup
				item.setCountryOfOriginId(cbCountryOfOrigin.getSelectionModel().getSelectedIndex() + 1);
				item.setExpirationDate(dtExpirationDate.getValue());
				item.setInternetCatalogId(cbInternetCatalog.getSelectionModel().getSelectedItem().getId().intValue());
				
				Item insertedItem = rsAddOrUpdateItem.addOrUpdate(item);
				if (mode == 0) {
					this.tblItem.addItem(insertedItem);
				}
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
	
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
}
