package org.code_studio.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.database.Calculation;
import org.code_studio.database.Client;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;


public class Items_CalculationAddEditController extends BaseController { //implements Initializable {

	ArrayList<Node> lstCSTextFieldNodes = new ArrayList<Node>();
	
	@FXML Button btnSave;
	@FXML Button btnCancel;
	
	@FXML private AnchorPane  apMaster;
	@FXML private CSTextField tfClientId;
	@FXML private CSTextField tfClientName;
	@FXML private CSTextField tfClientCalculationId;
	@FXML private CSTextField tfCalculationName;
	@FXML private CSTextField tfCustomsExchangeRate;
	@FXML private CSTextField tfBankBuyExchangeRate;
	@FXML private CSTextField tfBankSellExchangeRate;
	@FXML private CSTextField tfDiscountExchangeRate;
	@FXML private CSTextField tfDailyExchangeRate; // ovo se cita iz neke glavne lookup tabele za kurseve tj dnevne parametre. Nisam prebacio.
	@FXML private CSTextField tfCustomsRate;
	@FXML private CSTextField tfForwarderRate;
	@FXML private CSTextField tfBankRate;
	@FXML private CSTextField tfOtherRate;
	@FXML private CSTextField tfIncomeTaxRate;
	@FXML private CSTextField tfVatRate;
	
	//nabavna cena
	@FXML private CSTextField tfPurchasePriceRm;
	@FXML private CSTextField tfPurchasePriceDinRm;
	@FXML private CSTextField tfPurchasePriceDiscountRate;
	@FXML private CSTextField tfPurchasePriceDisDiscountedAmt;
	@FXML private CSTextField tfPurchasePriceDinRmDis;
	@FXML private CSTextField tfPurchasePriceDinFak;
	
	// transport
	@FXML private CSTextField tfTransportCostRateRm;
	@FXML private CSTextField tfTransportCostRateKgRm;
	@FXML private CSTextField tfTransportCostRm;
	@FXML private CSTextField tfTransportCostDinRm;
	@FXML private CSTextField tfTransportCostRateFak;
	@FXML private CSTextField tfTransportCostFak;
	@FXML private CSTextField tfTransportCostDinRmDisSpec;
	@FXML private CSTextField tfTransportCostDinFak;
	
	// carinska osnovica
	@FXML private CSTextField tfCustomsBaseRm;
	@FXML private CSTextField tfCustomsBaseDinRm;
	@FXML private CSTextField tfCustomsBaseDinRmDis;
	@FXML private CSTextField tfCustomsBaseDinFak;
	
	// carina
	@FXML private CSTextField tfCustomsRm;
	@FXML private CSTextField tfCustomsDinRm;
	@FXML private CSTextField tfCustomsDinRmDis;
	@FXML private CSTextField tfCustomsDinFak;
	
	// spediter
	@FXML private CSTextField tfForwarderRm;
	@FXML private CSTextField tfForwarderDinRm;
	@FXML private CSTextField tfForwarderDinRmDis;
	@FXML private CSTextField tfForwarderDinFak;
	
	// uvozna cena
	@FXML private CSTextField tfImportPriceRm;
	@FXML private CSTextField tfImportPriceDinRm;
	@FXML private CSTextField tfImportPriceDinRmDis;
	@FXML private CSTextField tfImportPriceDinFak;
	
	// bankarski troskovi
	@FXML private CSTextField tfBankingCostRm;
	@FXML private CSTextField tfBankingCostDinRm;
	@FXML private CSTextField tfBankingCostDinRmDis;
	@FXML private CSTextField tfBankingCostDinFak;
	
	// reklama i sl
	@FXML private CSTextField tfOtherCostRm;
	@FXML private CSTextField tfOtherCostDinRm;
	@FXML private CSTextField tfOtherCostDinRmDis;
	@FXML private CSTextField tfOtherCostDinFak;
	
	// ukupni troskovi nabavke
	@FXML private CSTextField tfTotalPurchaseCostDinRm;
	@FXML private CSTextField tfTotalPurchaseCostDinDis;
	@FXML private CSTextField tfTotalPurchaseCostDinRmDis;
	@FXML private CSTextField tfTotalPurchaseCostDinFak;
	
	// marza
	@FXML private CSTextField tfMarginRateRm;
	@FXML private CSTextField tfMarginRateWholesaleRm;
	@FXML private CSTextField tfMarginRm;
	@FXML private CSTextField tfMarginDinRm;
	@FXML private CSTextField tfMarginRateRmDis;
	@FXML private CSTextField tfMarginDinRmDis;
	@FXML private CSTextField tfMarginDinFak;
	@FXML private CSTextField tfMarginRateFak;
	@FXML private CSTextField tfMarginRateDinFak;
	//TODO:
	//@FXML private CSTextField tf
	//@FXML private CSTextField tf
	
	// porez na dobit
	@FXML private CSTextField tfIncomeTaxRm;
	@FXML private CSTextField tfIncomeTaxDinRm;
	@FXML private CSTextField tfIncomeTaxDinRmDis;
	@FXML private CSTextField tfIncomeTaxDinFax;

	// neto vp cena
	@FXML private CSTextField tfNetAmtRm;
	@FXML private CSTextField tfNetAmtDinRm;
	@FXML private CSTextField tfNetAmtDinRmDis;
	@FXML private CSTextField tfNetAmtDinFak;
	
	// pdv
	@FXML private CSTextField tfVatRm;
	@FXML private CSTextField tfVatDinRm;
	@FXML private CSTextField tfVatDinRmDis;
	@FXML private CSTextField tfVatDinFak;
	
	// bruto mp cena
	@FXML private CSTextField tfGrossAmtRm;
	@FXML private CSTextField tfGrossAmtDinRm;
	@FXML private CSTextField tfGrossAmtDinRmDis;
	@FXML private CSTextField tfGrossAmtDinFak;
	
	// nabavna cena spec
	@FXML private CSTextField tfPurchaseAmtRmSpec;
	@FXML private CSTextField tfPurchaseAmtDinRmSpec;
	@FXML private CSTextField tfPurchaseAmtDiscountedSpec;
	@FXML private CSTextField tfPurchaseAmtDinDisSpec;
	@FXML private CSTextField tfPurchaseAmtDinFakSpec;
	
	/* neto cena spec
	 * ovo su izgleda kombinacije i procenti osim za ovaj prvi koji je bele boje
	@FXML private CSTextField tf
	@FXML private CSTextField tf
	@FXML private CSTextField tf
	@FXML private CSTextField tf
	
	// bruto cena spec
	@FXML private CSTextField tf
	@FXML private CSTextField tf
	@FXML private CSTextField tf
	@FXML private CSTextField tf
	*/
	
	private int mode;
	private Client client;
	private CSTable<Calculation> tblCalculation;
	private Calculation calculation;
	
	private final String urlCalculationAddUpdate = "/calculation";
	private CSRestService<Calculation> rsCalculationAddUpdate;
	
	private final String urlCalculationGetLastClientCalculationId = "/calculation";
	private CSRestService<Calculation> rsCalculationGetLastClientCalculationId;
		
	@SuppressWarnings("unchecked")
	public Items_CalculationAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		List<Object> lstControllerParams = (List<Object>) controllerParam;
		this.client = ((CSClientTable) lstControllerParams.get(0)).csTable.getSelectedItem();
		this.tblCalculation = (CSTable<Calculation>) lstControllerParams.get(1);
		this.calculation = tblCalculation.getSelectedItem();
	}

	
	@FXML public void initialize() { // novi nacin inicijalizacije fxml-a, bez implementacije Initializable interfejsa
		try {
			getAllCSTextFieldNodes(apMaster, lstCSTextFieldNodes);
			
			rsCalculationAddUpdate = new CSRestService<>(urlCalculationAddUpdate);
			rsCalculationGetLastClientCalculationId = new CSRestService<>(urlCalculationGetLastClientCalculationId);
			
			if (this.client != null) {
				tfClientId.setText(client.getId().toString());
				tfClientName.setText(client.getName());
			}
			
			if (mode == 0) {
				this.calculation = new Calculation();
			}
			else if (mode == 1) {
				if (this.calculation != null) {
					tfClientCalculationId.setText(calculation.getClientCalculationId().toString());
					tfCalculationName.setText(calculation.getName());
					tfCustomsExchangeRate.setText(calculation.getCustomsExchangeRate() == null ? "" : calculation.getCustomsExchangeRate().toString());
					tfBankBuyExchangeRate.setText(calculation.getBuyingExchangeRate() == null ? "" : calculation.getBuyingExchangeRate().toString());
					tfBankSellExchangeRate.setText(calculation.getSellingExchangeRate() == null ? "" : calculation.getSellingExchangeRate().toString());
					tfDiscountExchangeRate.setText(calculation.getDiscountExchangeRate() == null ? "" : calculation.getDiscountExchangeRate().toString());
					//TODO: tfDailyExchangeRate.setText(getDailyExchageRate());
					tfCustomsRate.setText(calculation.getCustomsRate() == null ? "" : calculation.getCustomsRate().toString());
					tfForwarderRate.setText(calculation.getForwarderRate() == null ? "" : calculation.getForwarderRate().toString());
					tfBankRate.setText(calculation.getBankingCostRateRm() == null ? "" : calculation.getBankingCostRateRm().toString());
					tfOtherRate.setText(calculation.getOtherCostRateRm() == null ? "" : calculation.getOtherCostRateRm().toString());
					tfIncomeTaxRate.setText(calculation.getIncomeTaxRate() == null ? "" : calculation.getIncomeTaxRate().toString());
					tfVatRate.setText(calculation.getVatRate() == null ? "" : calculation.getVatRate().toString());
					
					//nabavna cena
					tfPurchasePriceRm.setText(calculation.getPurchasePriceRm().toString());
					tfPurchasePriceDinRm.setText(calculation.getPurchasePriceDinRm().toString());
					tfPurchasePriceDisDiscountedAmt.setText(calculation.getPurchasePriceRm().toString());
					tfPurchasePriceDinRmDis.setText(calculation.getPurchasePriceDinRmDis().toString());
					tfPurchasePriceDinFak.setText(calculation.getPurchasePriceDinFak().toString());
					
					//transport
					tfTransportCostRateRm.setText(calculation.getTransportCostRateRm().toString());
					tfTransportCostRateKgRm.setText(calculation.getTransportCostKgRm().toString());
					tfTransportCostRm.setText(calculation.getTransportCostRm().toString());
					tfTransportCostDinRm.setText(calculation.getTransportCostDinRm().toString());
					tfTransportCostRateFak.setText(calculation.getTransportCostRateFak().toString());
					tfTransportCostFak.setText(calculation.getTransportCostFak().toString());
					tfTransportCostDinRmDisSpec.setText(calculation.getTransportCostDinRmDisSpec().toString());
					tfTransportCostDinFak.setText(calculation.getTransportCostDinFak().toString());
					
					//carinska osnovica
					tfCustomsBaseRm.setText(calculation.getCustomsBaseRm().toString());
					tfCustomsBaseDinRm.setText(calculation.getCustomsBaseDinRm().toString());
					tfCustomsBaseDinRmDis.setText(calculation.getCustomsBaseDinRmDis().toString());
					tfCustomsBaseDinFak.setText(calculation.getCustomsBaseDinFak().toString());
					
					// carina
					tfCustomsRm.setText(calculation.getCustomsRm().toString());
					tfCustomsDinRm.setText(calculation.getCustomsDinRm().toString());
					tfCustomsDinRmDis.setText(calculation.getCustomsDinRmDis().toString());
					tfCustomsDinFak.setText(calculation.getCustomsDinFak().toString());
					
					// spediter
					tfForwarderRm.setText(calculation.getForwarderRm().toString());
					tfForwarderDinRm.setText(calculation.getForwarderDinRm().toString());
					tfForwarderDinRmDis.setText(calculation.getForwarderDinRmDis().toString());
					tfForwarderDinFak.setText(calculation.getForwarderDinFak().toString());
					
					// uvozna cena
					tfImportPriceRm.setText(calculation.getImportPriceRm().toString());
					tfImportPriceDinRm.setText(calculation.getImportPriceDinRm().toString());
					tfImportPriceDinRmDis.setText(calculation.getImportPriceDinRmDis().toString());
					tfImportPriceDinFak.setText(calculation.getImportPriceDinFak().toString());
					
					// bankarski troskovi
					tfBankingCostRm.setText(calculation.getBankingCostRm().toString());
					tfBankingCostDinRm.setText(calculation.getBankingCostDinRm().toString());
					tfBankingCostDinRmDis.setText(calculation.getBankingCostDinRmDis().toString());
					tfBankingCostDinFak.setText(calculation.getBankingCostDinFak().toString());
					
					// reklama i sl
					tfOtherCostRm.setText(calculation.getOtherCostRm().toString());
					tfOtherCostDinRm.setText(calculation.getOtherCostDinRm().toString());
					tfOtherCostDinRmDis.setText(calculation.getOtherCostDinRmDis().toString());
					tfOtherCostDinFak.setText(calculation.getOtherCostDinFak().toString());
					
					// ukupni troskovi nabavke
					tfTotalPurchaseCostDinRm.setText(calculation.getTotalPurchaseCostDinRm().toString());
					tfTotalPurchaseCostDinDis.setText(calculation.getTotalPurchaseCostDinDis().toString());
					//TODO: tfTotalPurchaseCostDinRmDis.setText(calculation.getTotalPurchaseCostDinRmDis().toString());
					tfTotalPurchaseCostDinFak.setText(calculation.getTotalPurchaseCostDinFak().toString());
					
					// marza
					tfMarginRateRm.setText(calculation.getMarginRateRm().toString());
					tfMarginRateWholesaleRm.setText(calculation.getMarginRateWholesaleRm().toString());
					tfMarginRm.setText(calculation.getMarginRm().toString());
					tfMarginDinRm.setText(calculation.getMarginDinRm().toString());
					tfMarginRateRmDis.setText(calculation.getMarginRateRmDis().toString());
					tfMarginDinRmDis.setText(calculation.getMarginDinRmDis().toString());
					tfMarginDinFak.setText(calculation.getMarginDinFak().toString());
					
					// porez na dobit
					tfIncomeTaxRm.setText(calculation.getIncomeTaxRm().toString());
					tfIncomeTaxDinRm.setText(calculation.getIncomeTaxDinRm().toString());
					tfIncomeTaxDinRmDis.setText(calculation.getIncomeTaxDinRmDis().toString());
					tfIncomeTaxDinFax.setText(calculation.getIncomeTaxDinFak().toString());

					// neto vp cena
					tfNetAmtRm.setText(calculation.getNetAmtRm().toString());
					tfNetAmtDinRmDis.setText(calculation.getNetPriceDinRmDis().toString());
					
					// pdv
					tfVatRm.setText(calculation.getVatRm().toString());
					tfVatDinRmDis.setText(calculation.getVatDinRmDis().toString());
					
					// bruto mp cena
					tfGrossAmtRm.setText(calculation.getGrossAmtRm().toString());
					tfGrossAmtDinRmDis.setText(calculation.getGrossPriceDinRmDis().toString());
					
					// nabavna cena spec // TODO: ovo izgleda nije dobro, imena iz baze nisu ok
					tfPurchaseAmtRmSpec.setText(calculation.getPurchasePriceDinRmSpec().toString());
					//tfPurchaseamt.setText(calculation.getPurchasePriceFakSpec().toString());
					//tfPurchasePriceDinFakSpec.setText(calculation.getPurchasePriceDinFakSpec().toString());
					
					// neto cena spec
					
					// bruto cena spec
					
					// NON-DISABLED CSTEXTFIELDS ONCHANGE EVENTS START ---------------------
					lstCSTextFieldNodes.forEach(node -> {
						if (node instanceof CSTextField && !node.isDisable() && !node.getId().equals("tfCalculationName")) {
							((CSTextField) node).onTextChanged((_ , _) -> {
								recalculate();
							});
						}
					});
					// TEXTFIELD ONCHANGE EVENTS END ---------------------
				}
			}
			
			
			btnSave.setOnAction( e-> {
				setCalculationItems();
				Calculation addedItem = rsCalculationAddUpdate.addOrUpdate(calculation);
				
				if (mode == 0) {
					this.tblCalculation.addItem(addedItem);
				}
				
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
			btnCancel.setOnAction(e->{
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});

		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške pri pozivanju forme.\n" + ex.getLocalizedMessage(), true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
	
	/**
	 * Izdvojen metod za postavljanje svih parametara kalkulacije, samo zbog
	 * toga sto je broj parametara izuzetno velik, tako da odrzimo kod citljivijim
	 */
	private void setCalculationItems() {
		calculation.setClientId(client.getId().intValue());
		
		rsCalculationGetLastClientCalculationId.fetch(new ParameterizedTypeReference<JsonResponse<Calculation>>(){});
		if (mode == 0) {
			Calculation lastCalc = rsCalculationGetLastClientCalculationId.getDataAsObservableList().size() == 0 
					? null : rsCalculationGetLastClientCalculationId.getDataAsObservableList().get(0);
			calculation.setClientCalculationId(lastCalc.getClientCalculationId() + 1);
		} else {
			calculation.setClientCalculationId(tfClientCalculationId.getTextAsInteger());
		}
		calculation.setName(tfCalculationName.getText());
		calculation.setCustomsExchangeRate(tfCustomsExchangeRate.getTextAsBigDecimal());
		calculation.setBuyingExchangeRate(tfBankBuyExchangeRate.getTextAsBigDecimal());
		calculation.setSellingExchangeRate(tfBankSellExchangeRate.getTextAsBigDecimal());
		calculation.setDiscountExchangeRate(tfDiscountExchangeRate.getTextAsBigDecimal());
		//TODO: calculation.setDailyExchageRate(tfDailyExchangeRate.getTextAsBigDecimal());
		calculation.setIncomeTaxRate(tfIncomeTaxRate.getTextAsBigDecimal());
		calculation.setVatRate(tfVatRate.getTextAsBigDecimal());
		calculation.setCustomsRate(tfCustomsRate.getTextAsBigDecimal());
		calculation.setForwarderRate(tfForwarderRate.getTextAsBigDecimal());
		
		//Nabavna cena
		calculation.setPurchasePriceRm(tfPurchasePriceRm.getTextAsBigDecimal());
		calculation.setPurchasePriceDinRm(tfPurchasePriceDinRm.getTextAsBigDecimal());
		calculation.setDiscountRateFak(tfPurchasePriceDisDiscountedAmt.getTextAsBigDecimal());
		calculation.setPurchasePriceFak(tfPurchasePriceDiscountRate.getTextAsBigDecimal());
		calculation.setPurchasePriceDinRmDis(tfPurchasePriceDinRmDis.getTextAsBigDecimal());
		calculation.setPurchasePriceDinFak(tfPurchasePriceDinFak.getTextAsBigDecimal());

		//transport
		calculation.setTransportCostRateRm(tfTransportCostRateRm.getTextAsBigDecimal());
		calculation.setTransportCostKgRm(tfTransportCostRateKgRm.getTextAsBigDecimal());
		calculation.setTransportCostRm(tfTransportCostRm.getTextAsBigDecimal());
		calculation.setTransportCostDinRm(tfTransportCostDinRm.getTextAsBigDecimal());
		calculation.setTransportCostRateFak(tfTransportCostRateFak.getTextAsBigDecimal());
		calculation.setTransportCostFak(tfTransportCostFak.getTextAsBigDecimal());
		calculation.setTransportCostDinRmDisSpec(tfTransportCostDinRmDisSpec.getTextAsBigDecimal());
		calculation.setTransportCostDinFak(tfTransportCostDinFak.getTextAsBigDecimal());
		
		//carinska osnovica
		calculation.setCustomsBaseRm(tfCustomsBaseRm.getTextAsBigDecimal());
		calculation.setCustomsBaseDinRm(tfCustomsBaseDinRm.getTextAsBigDecimal());
		calculation.setCustomsBaseDinRmDis(tfCustomsBaseDinRmDis.getTextAsBigDecimal());
		calculation.setCustomsBaseDinFak(tfCustomsBaseDinFak.getTextAsBigDecimal());
	
		// carina
		calculation.setCustomsRm(tfCustomsRm.getTextAsBigDecimal());
		calculation.setCustomsDinRm(tfCustomsDinRm.getTextAsBigDecimal());
		calculation.setCustomsDinRmDis(tfCustomsDinRmDis.getTextAsBigDecimal());
		calculation.setCustomsDinFak(tfCustomsDinFak.getTextAsBigDecimal());
		
		// spediter
		calculation.setForwarderRm(tfForwarderRm.getTextAsBigDecimal());
		calculation.setForwarderDinRm(tfForwarderDinRm.getTextAsBigDecimal());
		calculation.setForwarderDinRmDis(tfForwarderDinRmDis.getTextAsBigDecimal());
		calculation.setForwarderDinFak(tfForwarderDinFak.getTextAsBigDecimal());
		
		// uvozna cena
		calculation.setImportPriceRm(tfImportPriceRm.getTextAsBigDecimal());
		calculation.setImportPriceDinRm(tfImportPriceDinRm.getTextAsBigDecimal());
		calculation.setImportPriceDinRmDis(tfImportPriceDinRmDis.getTextAsBigDecimal());
		calculation.setImportPriceDinFak(tfImportPriceDinFak.getTextAsBigDecimal());
		
		// bankarski troskovi
		calculation.setBankingCostRm(tfBankingCostRm.getTextAsBigDecimal());
		calculation.setBankingCostDinRm(tfBankingCostDinRm.getTextAsBigDecimal());
		calculation.setBankingCostDinRmDis(tfBankingCostDinRmDis.getTextAsBigDecimal());
		calculation.setBankingCostDinFak(tfBankingCostDinFak.getTextAsBigDecimal());
		
		// reklama i sl
		calculation.setOtherCostRm(tfOtherCostRm.getTextAsBigDecimal());
		calculation.setOtherCostDinRm(tfOtherCostDinRm.getTextAsBigDecimal());
		calculation.setOtherCostDinRmDis(tfOtherCostDinRmDis.getTextAsBigDecimal());
		calculation.setOtherCostDinFak(tfOtherCostDinFak.getTextAsBigDecimal());
		
		// ukupni troskovi nabavke
		calculation.setTotalPurchaseCostDinRm(tfTotalPurchaseCostDinRm.getTextAsBigDecimal());
		calculation.setTotalPurchaseCostDinDis(tfTotalPurchaseCostDinDis.getTextAsBigDecimal());
		//TODO: calculation.setTotalPurchaseCostDinRmDis(tfTotalPurchaseCostDinRmDis.getTextAsBigDecimal());
		calculation.setTotalPurchaseCostDinFak(tfTotalPurchaseCostDinFak.getTextAsBigDecimal());
		
		// marza
		calculation.setMarginRateRm(tfMarginRateRm.getTextAsBigDecimal());
		calculation.setMarginRateWholesaleRm(tfMarginRateWholesaleRm.getTextAsBigDecimal());
		calculation.setMarginRm(tfMarginRm.getTextAsBigDecimal());
		calculation.setMarginDinRm(tfMarginDinRm.getTextAsBigDecimal());
		calculation.setMarginRateRmDis(tfMarginRateRmDis.getTextAsBigDecimal());
		calculation.setMarginDinRmDis(tfMarginDinRmDis.getTextAsBigDecimal());
		calculation.setMarginDinFak(tfMarginDinFak.getTextAsBigDecimal());
		
		// porez na dobit
		calculation.setIncomeTaxRm(tfIncomeTaxRm.getTextAsBigDecimal());
		calculation.setIncomeTaxDinRm(tfIncomeTaxDinRm.getTextAsBigDecimal());
		calculation.setIncomeTaxDinRmDis(tfIncomeTaxDinRmDis.getTextAsBigDecimal());
		calculation.setIncomeTaxDinFak(tfIncomeTaxDinFax.getTextAsBigDecimal());
		
		// neto vp cena
		calculation.setNetAmtRm(tfNetAmtRm.getTextAsBigDecimal());
		calculation.setNetPriceDinRmDis(tfNetAmtDinRmDis.getTextAsBigDecimal());
		
		// pdv
		calculation.setVatRm(tfVatRm.getTextAsBigDecimal());
		calculation.setVatDinRmDis(tfVatDinRmDis.getTextAsBigDecimal());
		
		// bruto mp cena
		calculation.setGrossAmtRm(tfGrossAmtRm.getTextAsBigDecimal());
		calculation.setGrossPriceDinRmDis(tfGrossAmtDinRmDis.getTextAsBigDecimal());
		
		// nabavna cena spec
		calculation.setPurchasePriceDinRmSpec(tfPurchaseAmtRmSpec.getTextAsBigDecimal());
		//calculation.setPurchasePriceFakSpec(tfPurchaseamt.getTextAsBigDecimal());
		//calculation.setPurchasePriceDinFakSpectfPurchasePriceDinFakSpec(.getTextAsBigDecimal());
		
		// neto cena spec
		
		// bruto cena spec
	}
	
	private void getAllCSTextFieldNodes(Parent parent, ArrayList<Node> nodes) {
	    for (Node node : parent.getChildrenUnmodifiable()) {
	    	lstCSTextFieldNodes.add(node);
	        if (node instanceof Parent)
	            getAllCSTextFieldNodes((Parent)node, nodes);
	    }
	}

	/**
	 * Racalculates all fields base on formulas
	 */
	private void recalculate() {
		
		// Nabavna cena
		//tfPurchasePriceRm -> ovo se upisuje na formi
		tfPurchasePriceDinRm.setText((tfBankSellExchangeRate.getTextAsBigDecimal().multiply(tfPurchasePriceRm.getTextAsBigDecimal())).toString());
		//tfPurchasePriceDiscountRate -> ovo se upisuje na formi
		tfPurchasePriceDisDiscountedAmt.setText(( //nabavna cena * (1 - (procenat popusta / 100))
				tfPurchasePriceRm.getTextAsBigDecimal()
				.multiply(
						  (new BigDecimal(1)
						    .subtract(tfPurchasePriceDiscountRate.getTextAsBigDecimal().divide(new BigDecimal(100)))
					 	  )
						)
		.toString()));
		tfPurchasePriceDinRmDis.setText(
			 tfPurchasePriceDisDiscountedAmt.getTextAsBigDecimal()
			.multiply(tfDiscountExchangeRate.getTextAsBigDecimal())
			.toString()
		);
		tfPurchasePriceDinFak.setText(
				 tfPurchasePriceDisDiscountedAmt.getTextAsBigDecimal()
				.multiply(tfCustomsExchangeRate.getTextAsBigDecimal())
				.toString()
			);
		
		// Transport
		tfTransportCostRm.setText((tfPurchasePriceRm.getTextAsBigDecimal().multiply(tfTransportCostRateRm.getTextAsBigDecimal()).divide(new BigDecimal(100))).toString());
		tfTransportCostDinRm.setText((tfBankSellExchangeRate.getTextAsBigDecimal().multiply(tfTransportCostRm.getTextAsBigDecimal())).toString());
		tfTransportCostFak.setText((
			 tfPurchasePriceDisDiscountedAmt.getTextAsBigDecimal()
			.divide(new BigDecimal(100))
		    .multiply(tfTransportCostRateFak.getTextAsBigDecimal())
			.toString())
		);
		tfTransportCostDinRmDisSpec.setText(
				tfTransportCostFak.getTextAsBigDecimal()
				.multiply(tfDiscountExchangeRate.getTextAsBigDecimal())
				.toString()
		);
		tfTransportCostDinFak.setText(
				tfTransportCostFak.getTextAsBigDecimal()
				.multiply(tfCustomsExchangeRate.getTextAsBigDecimal())
				.toString()
		);

		// Carina osnovica
		tfCustomsBaseRm.setText(tfPurchasePriceRm.getTextAsBigDecimal().add(tfTransportCostRm.getTextAsBigDecimal()).toString());
		tfCustomsBaseDinRm.setText(tfCustomsBaseRm.getTextAsBigDecimal().multiply(tfBankSellExchangeRate.getTextAsBigDecimal()).toString());
		tfCustomsBaseDinRmDis.setText(tfPurchasePriceDinRmDis.getTextAsBigDecimal().add(tfTransportCostDinRmDisSpec.getTextAsBigDecimal()).toString());
		tfCustomsBaseDinFak.setText(tfPurchasePriceDinFak.getTextAsBigDecimal().add(tfTransportCostDinFak.getTextAsBigDecimal()).toString());
		
		//Carina
		//mora prvo eur deo, jer se posle racuna na osnovu njega procenat carine
		tfCustomsDinRmDis.setText(
				tfCustomsExchangeRate.getText().equals("0.00") ? "0.00" : 
					tfCustomsBaseDinRmDis.getTextAsBigDecimal()
					.divide(tfCustomsExchangeRate.getTextAsBigDecimal(), RoundingMode.HALF_UP)
				.toString()
		);
		tfCustomsRm.setText(
				tfCustomsDinRmDis.getTextAsBigDecimal()
				  .divide(new BigDecimal(100), RoundingMode.HALF_UP) //uvek moramo round kada delimo, jer inace CSTextField ne prikazuje iz nekog razloga vrednosti
				  .multiply(tfCustomsRate.getTextAsBigDecimal())
				  .toString()
		);
		tfCustomsDinRm.setText(
				tfCustomsRm.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal())
			    .toString()
		);
		tfCustomsDinRmDis.setText(
				tfCustomsBaseDinRmDis.getTextAsBigDecimal()
				  .divide(new BigDecimal(100), RoundingMode.HALF_UP)
				  .multiply(tfCustomsRate.getTextAsBigDecimal())
				  .toString()
		);
		tfCustomsDinFak.setText(
				tfCustomsBaseDinFak.getTextAsBigDecimal()
				  .divide(new BigDecimal(100), RoundingMode.HALF_UP)
				  .multiply(tfCustomsRate.getTextAsBigDecimal())
				  .toString()
		);
	
		// Spediter
		tfForwarderDinRmDis.setText(
				tfCustomsBaseDinRmDis.getTextAsBigDecimal()
				  .divide(new BigDecimal(100), RoundingMode.HALF_UP) //uvek moramo round kada delimo, jer inace CSTextField ne prikazuje iz nekog razloga vrednosti
				  .multiply(tfForwarderRate.getTextAsBigDecimal())
				  .toString()
		);
		tfForwarderRm.setText(
				tfBankSellExchangeRate.getText().equals("0.00") ? "0.00" : 
				  (tfForwarderDinRmDis.getTextAsBigDecimal()
				  .divide(tfBankSellExchangeRate.getTextAsBigDecimal(), RoundingMode.HALF_UP)
				  .toString()
				)
		);
		tfForwarderDinRm.setText(
				tfForwarderRm.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal())
			    .toString()
		);
		tfForwarderDinFak.setText(
				tfCustomsBaseDinFak.getTextAsBigDecimal()
				  .divide(new BigDecimal(100), RoundingMode.HALF_UP) //uvek moramo round kada delimo, jer inace CSTextField ne prikazuje iz nekog razloga vrednosti
				  .multiply(tfForwarderRate.getTextAsBigDecimal())
				  .toString()
		);
		
		//Uvozna cena
		tfImportPriceRm.setText(
				tfCustomsBaseRm.getTextAsBigDecimal()
				.add(tfCustomsRm.getTextAsBigDecimal())
				.add(tfForwarderRm.getTextAsBigDecimal())
				.toString()
		);
		tfImportPriceDinRm.setText(
				tfCustomsBaseDinRm.getTextAsBigDecimal()
				.add(tfCustomsDinRm.getTextAsBigDecimal())
				.add(tfForwarderDinRm.getTextAsBigDecimal())
				.toString()
		);
		tfImportPriceDinRmDis.setText(
				tfCustomsBaseDinRmDis.getTextAsBigDecimal()
				.add(tfCustomsDinRmDis.getTextAsBigDecimal())
				.add(tfForwarderDinRmDis.getTextAsBigDecimal())
				.toString()
		);
		tfImportPriceDinFak.setText(
				tfCustomsBaseDinFak.getTextAsBigDecimal()
				.add(tfCustomsDinFak.getTextAsBigDecimal())
				.add(tfForwarderDinFak.getTextAsBigDecimal())
				.toString()
		);
		
		//Bankarski troskovi
		tfBankingCostRm.setText(
				tfImportPriceRm.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP) //uvek moramo round kada delimo, jer inace CSTextField ne prikazuje iz nekog razloga vrednosti
				.multiply(tfBankRate.getTextAsBigDecimal())
				.toString()
		);
		tfBankingCostDinRm.setText(
				tfBankingCostRm.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal())
				.toString()
		);
		tfBankingCostDinRmDis.setText(
				tfCustomsBaseDinRmDis.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP) 
				.multiply(tfBankRate.getTextAsBigDecimal())
				.toString()
		);
		tfBankingCostDinFak.setText(
				tfImportPriceDinFak.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP) 
				.multiply(tfBankRate.getTextAsBigDecimal())
				.toString()
		);
		
		// Reklama i sl.
		tfOtherCostRm.setText(
				tfImportPriceRm.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP) //uvek moramo round kada delimo, jer inace CSTextField ne prikazuje iz nekog razloga vrednosti
				.multiply(tfOtherRate.getTextAsBigDecimal())
				.toString()
		);
		tfOtherCostDinRm.setText(
				tfOtherCostRm.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal())
				.toString()
		);
		tfOtherCostDinRmDis.setText(
				tfCustomsBaseDinRmDis.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP) 
				.multiply(tfOtherRate.getTextAsBigDecimal())
				.toString()
		);
		tfOtherCostDinFak.setText(
				tfImportPriceDinFak.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP) 
				.multiply(tfOtherRate.getTextAsBigDecimal())
				.toString()
		);
		
		// Ukupni troskovi nabavke
		tfTotalPurchaseCostDinRm.setText(
				tfImportPriceRm.getTextAsBigDecimal()
				.add(tfBankingCostRm.getTextAsBigDecimal())
				.add(tfOtherCostRm.getTextAsBigDecimal())
				.toString()
		);
		tfTotalPurchaseCostDinDis.setText(
				tfTotalPurchaseCostDinRm.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal())
				.toString()
		);
		tfTotalPurchaseCostDinRmDis.setText(
				tfImportPriceDinRmDis.getTextAsBigDecimal()
				.add(tfBankingCostDinRmDis.getTextAsBigDecimal())
				.add(tfOtherCostDinRmDis.getTextAsBigDecimal())
				.toString()
		);
		tfTotalPurchaseCostDinFak.setText(
				tfImportPriceDinFak.getTextAsBigDecimal()
				.add(tfBankingCostDinFak.getTextAsBigDecimal())
				.add(tfOtherCostDinFak.getTextAsBigDecimal())
				.toString()
		);
		
		// Marza
		// tfMarginRateRm -> ovo upisujemo
		// tfMarginRateWholesaleRm -> ovo ne znam od cega je procenat i kako se racuna
		tfMarginRm.setText(
				tfTotalPurchaseCostDinRm.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP)
				.multiply(tfMarginRateRm.getTextAsBigDecimal())
				.toString()
		);
		tfMarginDinRm.setText(
				tfMarginRm.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal())
				.toString()
		);
		// tfMarginRateRmDis -> ovo se upisuje
		tfMarginDinRmDis.setText(
				tfImportPriceDinRmDis.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP)
				.multiply(tfMarginRateRmDis.getTextAsBigDecimal())
				.toString()
		);
		// tfMarginDinFak -> ovo ne znam kako se racuna
		// tfMarginRateFak -> ovo se upisuje
		// tfMarginRateDinFak -> ovo ne znam kako se racuna

		// Porez na dobit
		tfIncomeTaxDinRmDis.setText(
				tfMarginDinRmDis.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP)
				.multiply(tfIncomeTaxRate.getTextAsBigDecimal())
				.toString()
		);
		tfIncomeTaxRm.setText(
				tfIncomeTaxDinRmDis.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP)
				.multiply(tfIncomeTaxRate.getTextAsBigDecimal())
				.toString()
		);
		tfIncomeTaxDinRm.setText(
				tfIncomeTaxRm.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal())
				.toString()
		);
		
		// Neto VP Cena
		tfNetAmtRm.setText(
				tfTotalPurchaseCostDinRm.getTextAsBigDecimal()
				.add(tfMarginRm.getTextAsBigDecimal())
				.add(tfIncomeTaxRm.getTextAsBigDecimal())
				.toString()
		);
		tfNetAmtDinRm.setText(
				tfNetAmtRm.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal()) //TODO: ovde moramo dnevni kurs da koristimo!
				.toString()
		);
		tfNetAmtDinRmDis.setText(
				tfTotalPurchaseCostDinRmDis.getTextAsBigDecimal()
				.add(tfMarginDinRmDis.getTextAsBigDecimal())
				.add(tfIncomeTaxDinRmDis.getTextAsBigDecimal())
				.toString()
		);
		// TODO: tfNetAmtDinFak
		
		// PDV
		tfVatDinRmDis.setText(
				tfNetAmtDinRmDis.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP)
				.multiply(tfVatRate.getTextAsBigDecimal())
				.toString()
		);
		tfVatRm.setText(
				tfDiscountExchangeRate.getText().equals("0.00") ? "0.00" : 
				tfNetAmtDinRmDis.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP)
				.divide(tfDiscountExchangeRate.getTextAsBigDecimal(), RoundingMode.HALF_UP) //TODO: Koristi se dnevni kurs
				.multiply(tfVatRate.getTextAsBigDecimal())
				.toString()
		);
		tfVatDinRm.setText(
				tfVatRm.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal()) // TODO: Da li se i ovde koristi DNEVNI kurs?
				.toString()
		);
		tfVatDinFak.setText(
				tfVatDinFak.getTextAsBigDecimal()
				.divide(new BigDecimal(100), RoundingMode.HALF_UP)
				.multiply(tfVatRate.getTextAsBigDecimal())
				.toString()
		);
		
		//Bruto cena
		tfGrossAmtRm.setText(
				tfNetAmtRm.getTextAsBigDecimal()
				.add(tfVatRm.getTextAsBigDecimal())
				.toString()
		);
		tfGrossAmtDinRm.setText(
				tfNetAmtDinRm.getTextAsBigDecimal()
				.add(tfVatDinRm.getTextAsBigDecimal())
				.toString()
		);
		tfGrossAmtDinRmDis.setText(
				tfNetAmtDinRmDis.getTextAsBigDecimal()
				.add(tfVatDinRmDis.getTextAsBigDecimal())
				.toString()
		);
		tfGrossAmtDinFak.setText(
				tfNetAmtDinFak.getTextAsBigDecimal()
				.add(tfVatDinFak.getTextAsBigDecimal())
				.toString()
		);
		
		// Nabavna cena spec
		//ovo upisujemo, a po defaultu cemo da stavimo nabavnu cenu sa pocetka forme
		tfPurchaseAmtRmSpec.setText(tfPurchasePriceRm.getText());
		tfPurchaseAmtDinRmSpec.setText(
				tfPurchaseAmtRmSpec.getTextAsBigDecimal()
				.multiply(tfBankSellExchangeRate.getTextAsBigDecimal())
				.toString()
		);
		tfPurchaseAmtDiscountedSpec.setText(tfPurchasePriceDisDiscountedAmt.getText());
		tfPurchaseAmtDinDisSpec.setText(
				tfPurchaseAmtDiscountedSpec.getTextAsBigDecimal()
				.multiply(tfDiscountExchangeRate.getTextAsBigDecimal())
				.toString()
		);
		tfPurchaseAmtDinFakSpec.setText(
				tfPurchaseAmtDiscountedSpec.getTextAsBigDecimal()
				.multiply(tfCustomsExchangeRate.getTextAsBigDecimal())
				.toString()
		);
		
		//ostale spec cene cu da napravim kada se definise ispravno kalkulacija za sve
	}

}
