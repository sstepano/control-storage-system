package org.code_studio.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.code_studio.component.CSPhotoView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;
import org.code_studio.component.InputValidation.CSZeroValueValidator;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.component.ui.CSItemTable;

import org.code_studio.database.Client;
import org.code_studio.database.ClientPricelist;
import org.code_studio.database.F6SelectedItem;
import org.code_studio.database.Item;
import org.code_studio.database.ItemSimple;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.database.Offer;
import org.code_studio.database.OfferDetail;
import org.code_studio.main.Common;

import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class Sales_OfferDetailAddEditController extends BaseController {
	@FXML private CSDialogButtons dialogButtons;

	@FXML private CSTable<OfferDetail> tblOfferDetail;
	@FXML private CSItemTable tblItem;
	@FXML private CSTable<ItemWarehouse> tblItemWarehouse;
	@FXML private CSTable<ClientPricelist> tblClientPricelist;
	
	@FXML private VBox vbRadioButtons;
	@FXML private ToggleGroup tgpPrice;
	@FXML private RadioButton rbRegularPrice;
	@FXML private RadioButton rbSpecialPrice;
	@FXML private RadioButton rbFreePrice;
	@FXML private RadioButton rbDOMPrice;
	
	@FXML private CSTextField tfQty;
	@FXML private CSTextField tfMeasureUnitNetAmt;
	@FXML private CSTextField tfDiscountRate;
	@FXML private CSTextField tfDiscountAmt;
	@FXML private CSTextField tfVatAmt;
	@FXML private CSTextField tfMeasureUnitGrossAmt;
	@FXML private CSTextField tfTotalAmt;
	
	@FXML private CSTextField tfRMAmt;
	@FXML private CSTextField tfDisNetAmt;
	@FXML private CSTextField tfDisGrossAmt;
	@FXML private CSTextField tfNetAmt;
	@FXML private CSTextField tfGrossAmt;
	@FXML private CSTextField tfRMAmtSpec;
	@FXML private CSTextField tfDisNetAmtSpec;
	@FXML private CSTextField tfDisGrossAmtSpec;
	@FXML private CSTextField tfNetAmtSpec;
	@FXML private CSTextField tfGrossAmtSpec;
	
	@FXML private CSTextField tfRepromarketAvailableQty;
	@FXML private CSTextField tfRepromarketReservedQty;
	@FXML private CSTextField tfRepromarketTotalQty;
	
	@FXML private CSTextField tfCommissionAvailableQty;
	@FXML private CSTextField tfCommissionReservedQty;
	@FXML private CSTextField tfCommissionTotalQty;
	
	@FXML private CSTextField tfTotalAvailableQty;
	@FXML private CSTextField tfTotalReservedQty;
	@FXML private CSTextField tfTotalTotalQty;
	
	@FXML private CSTextField tfTotalSoldQty;
	
	@FXML private Button btnItemWarehouseAddSingle;
	@FXML private Button btnItemWarehouseAdd;
	@FXML private Button btnItemWarehouseEdit;
	@FXML private Button btnItemWarehouseDelete;
	
	@FXML private Button btnAddItemsFromF6;
	
	@FXML private CSPhotoView pvImage;
	
	@SuppressWarnings("unused")
	private int mode;
	private Client client;
	private Offer offer;
	private Item item;
	//private TransferOrder transferOrder;
	private CSTable<Offer> tblOfferFromParent;

	@SuppressWarnings("unused")
	private OfferDetail offerDetail;
	
	private CSTable<OfferDetail> tblOfferDetailFromParent;
	
	private String urlClientPricelist = "/clientPricelist/allByClientIdAndItemId/";
	private CSRestService<ClientPricelist> rsClientPriceList;
	
	private String urlItemWarehouse = "/itemWarehouse/allByItemId/";
	private CSRestService<ItemWarehouse> rsItemWarehouse;
	
	private String urlItemWarehouseDelete = "/itemWarehouse";
	private CSRestService<ItemWarehouse> rsItemWarehouseAddUpdateDelete;
	
	private String urlItemWarehouseSum = "/itemWarehouse/sumByItemId/";
	private CSRestService<ItemWarehouse> rsItemWarehouseSum;
	
	private String urlOfferDetailAddUpdate = "/offerDetail";
	private CSRestService<OfferDetail> rsOfferDetailAddUpdate;
	
	private String urlOfferAddUpdate = "/offer";
	@SuppressWarnings("unused")
	private CSRestService<Offer> rsOfferAddUpdate;

	//private String urlTransferOrder = "/transferOrder/allByInvoiceId/";
	//private CSRestService<TransferOrder> rsTransferOrder;
	
	//private String urlTransferOrderDetail = "/transferOrderDetail/findByInvoiceDetailId/";
	//private CSRestService<TransferOrderDetail> rsTransferOrderDetail;
	
	//private String urlTransferOrderDetailAddEdit = "/transferOrderDetail";
	//private CSRestService<TransferOrderDetail> rsTransferOrderDetailAddEdit;
	
	private List<ItemWarehouse> lstItemWarehouseChangedItems;	

	@SuppressWarnings("unchecked")
	public Sales_OfferDetailAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		List<Object> lstControllerParams = (List<Object>) controllerParam;
		tblOfferDetailFromParent = (CSTable<OfferDetail>) lstControllerParams.get(0);
		tblOfferFromParent = (CSTable<Offer>) lstControllerParams.get(1);
		client  = (Client)  lstControllerParams.get(2);
		offer = tblOfferFromParent.getSelectedItem();
		offerDetail = tblOfferDetailFromParent.getSelectedItem();
		lstItemWarehouseChangedItems = new ArrayList<>();
	}

	public void initialize() {
	
		try {		
			tblItem.refresh();
			rsClientPriceList = new CSRestService<>(urlClientPricelist);
			rsItemWarehouse = new CSRestService<>(urlItemWarehouse);
			rsItemWarehouseAddUpdateDelete = new CSRestService<>(urlItemWarehouseDelete);
			rsItemWarehouseSum = new CSRestService<>(urlItemWarehouseSum);
			tblItemWarehouse.setRestServiceDelete(rsItemWarehouseAddUpdateDelete);
			rsOfferDetailAddUpdate = new CSRestService<>(urlOfferDetailAddUpdate);
			rsOfferAddUpdate = new CSRestService<>(urlOfferAddUpdate);
			/**
			rsTransferOrder = new CSRestService<>(urlTransferOrder);
			rsTransferOrderDetail = new CSRestService<>(urlTransferOrderDetail);
			rsTransferOrderDetailAddEdit = new CSRestService<>(urlTransferOrderDetailAddEdit);
			
			rsTransferOrder.setUrl(urlTransferOrder + offer.getId());
			rsTransferOrder.fetch(new ParameterizedTypeReference<JsonResponse<TransferOrder>>(){});
			transferOrder = rsTransferOrder.getData() != null ? rsTransferOrder.getData().get(0) : null;
			**/
			
			btnAddItemsFromF6.setDisable(Common.selectedItems.isEmpty());
			
			tgpPrice.selectedToggleProperty().addListener((arg, oldVal, newVal)->{
				setAmounts(tblItem.csTable.getSelectedItem(), ((RadioButton) newVal).getText());
				if(((RadioButton) tgpPrice.getSelectedToggle()).getText().equals("Slobodna")) {
					tfMeasureUnitNetAmt.setDisable(false);
					tfDiscountRate.setDisable(false);
				} else {
					tfMeasureUnitNetAmt.setDisable(true);
					tfDiscountRate.setDisable(true);
				}
			});
			
			switch (offer.getPriceType()) {
				case "S":
					rbSpecialPrice.setSelected(true);
				break;
				case "L":
					rbFreePrice.setSelected(true);
				break;
				case "D":
					rbDOMPrice.setSelected(true);
				break;
				case "O":
					rbRegularPrice.setSelected(true);
				break;
				default:
					rbRegularPrice.setSelected(true);
				break;
			}
			
			if (client.getSupplierPriceOnly()) {
				rbDOMPrice.setSelected(true);
				vbRadioButtons.setDisable(true);
			}
			
			tblItem.csTable.onRowSelectionChanged((oldRow, newRow) -> {
				btnItemWarehouseDelete.fire();
				if (newRow != null) {
					item = (Item) newRow;
					rsClientPriceList.setUrl(urlClientPricelist + client.getId() + "/" + item.getId());
					rsClientPriceList.fetch(new ParameterizedTypeReference<JsonResponse<ClientPricelist>>(){});
					tblClientPricelist.setItems(rsClientPriceList.getDataAsObservableList());
					
					tfRMAmt.setText(item.getGrossPriceRmD().toString());
					tfDisNetAmt.setText(item.getNetPriceRmD().toString());
					tfDisGrossAmt.setText(item.getGrossPriceD().toString());
					tfNetAmt.setText(item.getNetPriceDFak().toString());
					tfGrossAmt.setText(item.getGrossPriceDFak().toString());
	
					tfRMAmtSpec.setText(item.getGrossPriceRmDSpec().toString());
					tfDisNetAmtSpec.setText(item.getNetPriceRmDSpec().toString());
					tfDisGrossAmtSpec.setText(item.getGrossPriceDSpec().toString());
					tfNetAmtSpec.setText(item.getNetPriceDFakSpec().toString());
					tfGrossAmtSpec.setText(item.getGrossPriceDFakSpec().toString());
					
					tfDiscountRate.setText("0.00"); // ako je dom, onda se postavi ova verdnost i posle se ne resetuje

					// TBL ITEM WAREHOUSE
					rsItemWarehouse.setUrl(urlItemWarehouse + item.getId());
					rsItemWarehouse.fetch(new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>(){});
					tblItemWarehouse.setItems(rsItemWarehouse.getDataAsObservableList());
					setAmounts(item, ((RadioButton)tgpPrice.getSelectedToggle()).getText());
					pvImage.setImagePath(item.getImagePath());
					
					setAvailableTextFields(item);
				}
			});
			
			tblItemWarehouse.onRowSelectionChanged((oldRow, newRow)->{
				if (newRow != null) {
					btnItemWarehouseAdd.setDisable(false);
					btnItemWarehouseEdit.setDisable(false);
				} else {
					btnItemWarehouseAdd.setDisable(true);
					btnItemWarehouseEdit.setDisable(true);
				}
			});

			btnItemWarehouseAddSingle.setOnAction(e->{
				if (tblItemWarehouse.getSelectedItem() == null) {
					throw new RuntimeException("Nema na stanju");
				}
				
				Integer itemQty = tfQty.getTextAsInteger();
				if (itemQty > 0) {
					addItem(itemQty, null);
					tblItemWarehouse.refresh();
					tblOfferDetail.refresh();
					tblOfferDetailFromParent.refresh();
				} else {
					System.err.println("itemQty <= 0");
				}
			});
			
			btnItemWarehouseAdd.setOnAction(e->{
				Integer itemQty = tfQty.getTextAsInteger();
				
				for (int i=0; i<tblItemWarehouse.tableView.getItems().size(); i++) {
					if (itemQty == 0)
					break;
					addItem(itemQty, i);
				}
				
				tblItemWarehouse.refresh();
				setAvailableTextFields(tblItemWarehouse.getSelectedItem().getItem());
			});
			
			btnItemWarehouseEdit.setOnAction(e->{
				Integer itemQty = (Integer) Common.displayForm(ControllerFactory.getController("Sales_WholesaleDetailAddEditDetailController", tblItemWarehouse.getSelectedItem(), 1), tblItemWarehouse, "Izmena");
				if (itemQty != null) {
					ItemWarehouse iw = tblItemWarehouse.getSelectedItem();
					lstItemWarehouseChangedItems.add(iw);
					iw.setReservedQty(iw.getReservedQty() + itemQty);
					iw.setSoldQty(itemQty);
					ItemWarehouse changedItem = rsItemWarehouseAddUpdateDelete.addOrUpdate(iw);
					tblItemWarehouse.refresh();
					setAvailableTextFields(changedItem.getItem());
				}
			});
			
			btnItemWarehouseDelete.setOnAction( e-> {
				lstItemWarehouseChangedItems.forEach(item -> {
					item.setReservedQty(0);
					item.setSoldQty(0);
					ItemWarehouse changedItem = rsItemWarehouseAddUpdateDelete.addOrUpdate(item);
					setAvailableTextFields(changedItem.getItem());
				});
				lstItemWarehouseChangedItems.clear();
				tblItemWarehouse.refresh();
			});
			
			tfQty.onFocusChanged( (oldValue, newValue) -> {
				if (oldValue) { // focus lost
					setAmounts(tblItem.csTable.getSelectedItem(), ((RadioButton)tgpPrice.getSelectedToggle()).getText());
				}
			});
			
			tfDiscountRate.onFocusChanged( (oldValue, newValue) -> {
				if (oldValue) { // focus lost
					setAmounts(tblItem.csTable.getSelectedItem(), ((RadioButton)tgpPrice.getSelectedToggle()).getText());
				}
			});
			
			tblOfferDetail.setItems(tblOfferDetailFromParent.tableView.getItems());
			
			tblClientPricelist.onRowSelectionChanged((oldRow, newRow) -> {
				if (newRow == null) {
					rbDOMPrice.setDisable(true);
					//TODO: if client nije dom only, onda ovo dole
					rbSpecialPrice.setSelected(true);
				} else {
					rbDOMPrice.setDisable(false);
				}
			}); 
		

			/***
			 * Rucni binding za unos i add dugmad
			 */
			btnItemWarehouseAddSingle.disableProperty().bind(Bindings.createBooleanBinding(
				() -> {
					if (tfQty.getTextAsInteger() <= 0 || tblItemWarehouse.getSelectedItem() == null) {
						return true;
					}
					return false;
				},
				tfQty.textProperty(),
				tblItemWarehouse.tableView.getSelectionModel().selectedItemProperty()
			));
			
			dialogButtons.getSaveButton().setVisible(false);
			
			dialogButtons.getCancelButton().setOnAction(e->{
				dialogButtons.closeForm();
			});
			
			tblItem.csTable.onRowDoubleClick (e-> {
				tfQty.requestFocus();
			});
			
			tfQty.setOnKeyReleased( e-> {
				if (e.getCode() == KeyCode.ENTER && !btnItemWarehouseAddSingle.isDisabled()) {
					btnItemWarehouseAddSingle.fire();
				}
			});
			
			dialogButtons.setValidation( new CSZeroValueValidator(tfQty) );
			tblItem.getCsTable().setSearchRequestFocus(true);
			
			/**
			 * Add all simple items to invoice detail
			 */
			btnAddItemsFromF6.setOnAction( e-> {
				addF6SelectedItem();
			});
		}
		catch (Exception ex) {
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize END
	
	@Override
	public void postInitialize() {
		addEscapeListener();
	}
	
	/**
	 * 
	 */
	private void setAmounts(Item item, String priceType) {
		if (item == null) {return;}
		
		switch (priceType) {
			case "Specijalna":
				tfMeasureUnitNetAmt.setText(item.getNetPriceDFakSpec().toString());
			break;
			case "Slobodna":
				tfMeasureUnitNetAmt.setText(item.getNetPriceDFak().toString());
			break;
			case "DOM":
				if (tblClientPricelist.getRowCount() > 0) {
					ClientPricelist clientPricelist = tblClientPricelist.getSelectedItem();
					tfMeasureUnitNetAmt.setText(clientPricelist.getMeasureUnitNetAmt().toString());
					tfDiscountRate.setText(clientPricelist.getDiscountRate().toString());
				}
			break;
			case "Obična":
				tfMeasureUnitNetAmt.setText(item.getNetPriceDFak().toString());
			break;
			default:
			break;
		}
		
		tfDiscountAmt.setText(
				   !(tfDiscountRate.getText().equals("0.00") || tfDiscountRate.getText().isEmpty())
			 		  ? tfMeasureUnitNetAmt.getTextAsBigDecimal().multiply(
		 				  tfDiscountRate.getTextAsBigDecimal().divide(BigDecimal.valueOf(100.00))
				      )
			 		  .setScale(2, RoundingMode.HALF_UP)
				  	  .toString()
				    : "0.00"
				);
				
		tfVatAmt.setText(
			tfMeasureUnitNetAmt.getTextAsBigDecimal().subtract(
				tfDiscountAmt.getTextAsBigDecimal()
			).multiply(new BigDecimal("0.20"))
			.setScale(2, RoundingMode.HALF_UP)
			.toString()
		);
		
		tfMeasureUnitGrossAmt.setText(
			tfMeasureUnitNetAmt.getTextAsBigDecimal()
			.subtract(
				tfDiscountAmt.getTextAsBigDecimal()
			).add(
				tfVatAmt.getTextAsBigDecimal()
			)
			.toString()
		);
		
		tfTotalAmt.setText(
			tfMeasureUnitGrossAmt.getTextAsBigDecimal().multiply(
				tfQty.getTextAsBigDecimal()
			)
			.toString()
		);
	
	}


	private void addEscapeListener () {
		dialogButtons.getCancelButton().getScene().addEventFilter(KeyEvent.KEY_RELEASED, e -> {
	        if (e.getCode() == KeyCode.ESCAPE) {
	        	btnItemWarehouseDelete.fire();
	        }
	    });

	}
	
	
	/**
	 * Vraca iz baze stanja prosledjenog artikla. Getter only.
	 */
	private void setAvailableTextFields(Item item) {
		rsItemWarehouseSum.setUrl(urlItemWarehouseSum + item.getId());
		rsItemWarehouseSum.fetch(new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>(){});
		
		//TOTAL 1
		List<ItemWarehouse> lstItemWarehouseRepromarket = 
			rsItemWarehouseSum.getData()
			.stream()
			.filter(itemWarehouse -> itemWarehouse.getId().intValue() == 1)
			.collect(Collectors.toList()
		);
		
		if (lstItemWarehouseRepromarket.size() == 1) {
			tfRepromarketAvailableQty.setText(
				lstItemWarehouseRepromarket.get(0)
				.getAvailableQty()
			    .toString()
			);
			
			tfRepromarketReservedQty.setText(
				lstItemWarehouseRepromarket.get(0).getReservedQty() != null
				? lstItemWarehouseRepromarket.get(0).getReservedQty().toString()
				: "0"
		    );
			
			tfRepromarketTotalQty.setText(
					lstItemWarehouseRepromarket.get(0)
					.getQty()
				    .toString()
				);
		}
		
		// TOTAL 2
		List<ItemWarehouse> lstItemWarehouseCommission = 
				rsItemWarehouseSum.getData()
				.stream()
				.filter(itemWarehouse -> itemWarehouse.getId().intValue() == 2)
				.collect(Collectors.toList()
			);
			
		if (lstItemWarehouseCommission.size() == 1) {
			tfCommissionAvailableQty.setText(
					lstItemWarehouseCommission.get(0)
				.getAvailableQty()
			    .toString()
			);
			
			tfCommissionReservedQty.setText(
				lstItemWarehouseCommission.get(0).getReservedQty() != null
				? lstItemWarehouseCommission.get(0).getReservedQty().toString()
				: "0"
			);
			
			tfCommissionTotalQty.setText(
					lstItemWarehouseCommission.get(0)
				.getQty()
			    .toString()
			);
	
		}
		
		// TOTAL 3
		List<ItemWarehouse> lstItemWarehouseTotal = 
				rsItemWarehouseSum.getData()
				.stream()
				.filter(itemWarehouse -> itemWarehouse.getId().intValue() == 3)
				.collect(Collectors.toList()
			);
	
		tfTotalAvailableQty.setText(
			Integer.toString(
				tfRepromarketAvailableQty.getTextAsInteger() + tfCommissionAvailableQty.getTextAsInteger()
			)
		);
		
		tfTotalReservedQty.setText(
				Integer.toString(
					tfRepromarketReservedQty.getTextAsInteger() + tfCommissionReservedQty.getTextAsInteger()
				)
			);
		
		tfTotalTotalQty.setText(
			Integer.toString(
				tfRepromarketTotalQty.getTextAsInteger() + tfCommissionTotalQty.getTextAsInteger()
			)
		);
		
		tfTotalSoldQty.setText(
			Integer.toString(
					lstItemWarehouseTotal.get(0).getSoldQty() != null
					? lstItemWarehouseTotal.get(0).getSoldQty()
					: 0
			)
		);
	}

	
	/**
	 * Adds one Item to the OfferDetail list
	 * @param itemQty
	 * @param itemIndex
	 */
	private void addItem (Integer itemQty, Integer itemIndex) {
		ItemWarehouse iw = itemIndex == null ? tblItemWarehouse.getSelectedItem() : tblItemWarehouse.tableView.getItems().get(itemIndex);
		if (iw == null) {
			return;
		}
		if (iw.getAvailableQty() > 0 && iw.getAvailableQty() >= itemQty) { // ako ima dovoljno na stanju
			iw.setReservedQty(iw.getReservedQty() + itemQty);
			iw.setSoldQty(itemQty);
			@SuppressWarnings("unused")
			ItemWarehouse changedItem = rsItemWarehouseAddUpdateDelete.addOrUpdate(iw);
			itemQty = 0;
			lstItemWarehouseChangedItems.add(iw);
			setAvailableTextFields(tblItemWarehouse.getSelectedItem().getItem());
		} else { // ako nema dovoljno na stanju, uzimamo sve sto ima i idemo na sledeci red
			Integer availableQty = iw.getAvailableQty();
			itemQty = itemQty - availableQty;
			iw.setReservedQty(iw.getReservedQty() + availableQty);
			iw.setSoldQty(availableQty);
			@SuppressWarnings("unused")
			ItemWarehouse changedItem = rsItemWarehouseAddUpdateDelete.addOrUpdate(iw);
			lstItemWarehouseChangedItems.add(iw);
			setAvailableTextFields(tblItemWarehouse.getSelectedItem().getItem());
		}
		
		if (tfTotalSoldQty.getTextAsInteger() > 0) {
			lstItemWarehouseChangedItems.forEach(changedItemWarehouse -> {
				OfferDetail offerDetail = new OfferDetail();
				offerDetail.setOfferId(offer.getId());
				offerDetail.setPriceType(
					((RadioButton)tgpPrice.getSelectedToggle()).getUserData().toString()
				);
				offerDetail.setItemId(item.getId());
				offerDetail.setItem(item); // transient, treba mi za upstream grid zbog item imena
				offerDetail.setQty(tfQty.getTextAsInteger());
				offerDetail.setSpecQty(tfQty.getTextAsInteger()); // TODO: ovde mi fali neka logika, ne znam sta je spec qty, pitati B.
				offerDetail.setVatRate(BigDecimal.valueOf(20));
				offerDetail.setUnitNetAmt(tfNetAmt.getTextAsBigDecimal());
				offerDetail.setUnitNetAmtSpec(item.getNetPriceDFakSpec());
				offerDetail.setDiscountAmt(tfDiscountAmt.getTextAsBigDecimal());
				offerDetail.setDiscountAmtSpec(tfDiscountAmt.getTextAsBigDecimal());
				offerDetail.setDiscountRate(tfDiscountRate.getTextAsBigDecimal());
				offerDetail.setDiscountRateSpec(tfDiscountRate.getTextAsBigDecimal());
				offerDetail.setUnitVatAmt(tfVatAmt.getTextAsBigDecimal());
				offerDetail.setUnitVatAmtSpec(tfVatAmt.getTextAsBigDecimal()); // TODO: ???
				offerDetail.setUnitGrossAmt(item.getGrossPriceDFak());
				offerDetail.setUnitGrossAmtSpec(item.getGrossPriceDFakSpec());
				offerDetail.setNetAmt(tfNetAmt.getTextAsBigDecimal());
				offerDetail.setNetAmtSpec(tfNetAmtSpec.getTextAsBigDecimal());
				offerDetail.setVatAmt(tfVatAmt.getTextAsBigDecimal());
				offerDetail.setVatAmtSpec(tfVatAmt.getTextAsBigDecimal());
				offerDetail.setGrossAmt(item.getGrossPriceDFak());
				offerDetail.setGrossAmtSpec(item.getGrossPriceDFakSpec());
				offerDetail.setTotalAmt(tfTotalAmt.getTextAsBigDecimal());
				offerDetail.setTotalAmtSpec(tfTotalAmt.getTextAsBigDecimal());
				offerDetail.setGrossAmtRm(item.getGrossPriceRmD());
				offerDetail.setGrossAmtRmSpec(item.getGrossPriceRmD());
				offerDetail.setNetAmtDinFak(item.getNetPriceDFak());
				offerDetail.setNetAmtDinFakSpec(item.getNetPriceDFakSpec());
				offerDetail.setNetAmtDinRmDis(item.getGrossPriceRmD());
				offerDetail.setNetAmtDinRmDisSpec(item.getGrossPriceRmDSpec());
				offerDetail.setVatAmtDinRmDis(tfVatAmt.getTextAsBigDecimal());
				offerDetail.setGrossAmtDinRmDis(item.getGrossPriceRmD());
				offerDetail.setGrossAmtDinRmDisSpec(item.getGrossPriceRmDSpec());
				offerDetail.setLastModifiedBy(Common.getApplicationUserId());
				offerDetail.setLastModifiedDate(LocalDateTime.now());
				
				OfferDetail insertedOfferDetail = rsOfferDetailAddUpdate.addOrUpdate(offerDetail);
				tblOfferDetail.addItem(insertedOfferDetail);
				tblOfferDetailFromParent.addItem(insertedOfferDetail);
				if (offer.getOfferDetail() != null) {
					offer.getOfferDetail().add(insertedOfferDetail);
				}
				changedItemWarehouse.setReservedQty(changedItemWarehouse.getReservedQty() - changedItemWarehouse.getSoldQty());
				changedItemWarehouse.setQty(changedItemWarehouse.getQty() - changedItemWarehouse.getSoldQty());
				changedItemWarehouse.setSoldQty(0);
				@SuppressWarnings("unused")
				ItemWarehouse changedItem = rsItemWarehouseAddUpdateDelete.addOrUpdate(changedItemWarehouse);
			}); // lstItemWarehouseChangedItems.forEach END
			
			tblOfferDetail.refresh();
			tblOfferFromParent.refresh();
		}
	}
	
	
	/** TODO: Duplication of code
	 * Creates InvoiceDetail from ItemSimple and adds all of them to the tblInvoiceDetail
	 */
	private void addItemFromF6(ItemSimple itemSimple) {
		CSRestService<Item> rs = new CSRestService<Item>("/item/" + itemSimple.getId());
		rs.fetch(new ParameterizedTypeReference<JsonResponse<Item>>() {});
		Item item = rs.getDataAsObservableList().getFirst();
		
		OfferDetail offerDetail = new OfferDetail();
		offerDetail.setOfferId(offer.getId());
		offerDetail.setPriceType("O"); // obicna cena
		offerDetail.setItemId(item.getId());
		offerDetail.setItem(item); // transient, treba mi za upstream grid zbog item imena
		/** TODO: Dodati sve artibute u offer detail pre snimanja
		offerDetail.setItemQty(itemSimple.getSelectedQty());
		offerDetail.setUnitOfMeasureNetValue(item.getNetPriceDFak());
		offerDetail.setDiscountRate(0);
		offerDetail.setAmountDiscount(BigDecimal.ZERO);
		offerDetail.setVatRate(20);
		offerDetail.setAmountVat(
			 item.getNetPriceDFak()
			.subtract(BigDecimal.ZERO)
			.multiply(new BigDecimal("0.20"))
			.setScale(2, RoundingMode.HALF_UP)
		);
		offerDetail.setAmountNet(item.getNetPriceDFak());
		offerDetail.setAmountGross(item.getGrossPriceDFak());
		offerDetail.setWarehouseId(itemSimple.getWarehouseId());
		**/
		offerDetail.setLastModifiedBy(
			Common.getApplicationUserId() != null
			? Common.getApplicationUserId()
			: 1
		);
		offerDetail.setLastModifiedDate(LocalDateTime.now());
		
		OfferDetail insertedOfferDetail = rsOfferDetailAddUpdate.addOrUpdate(offerDetail);
		tblOfferDetail.addItem(insertedOfferDetail);
		tblOfferDetailFromParent.addItem(insertedOfferDetail);
		offer.getOfferDetail().add(insertedOfferDetail);
	}
	
	/** 
	 * Used to populate items from selected items
	 * This code is used in OfferDetailAddEdit too!!!
	 * TODO: Duplication of code 
	 */
	private void addF6SelectedItem() {
		for (Object iterF6SelectedItem: Common.selectedItems) {
			if (((F6SelectedItem)iterF6SelectedItem).getTypeId() == 0) { // if selected object is ITEM
				ItemSimple itemSimple = (ItemSimple) ((F6SelectedItem) iterF6SelectedItem).getObjectInstance();
				addItemFromF6(itemSimple);
			} else { // if selected object is SUBGROUP
				
			}			
		}
		
		Common.selectedItems.clear();
		btnAddItemsFromF6.setDisable(true);
	}
	
} // class end
