package org.code_studio.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import javafx.stage.Stage;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;

import org.code_studio.database.ClientSimple;
import org.code_studio.database.ClientPricelist;
import org.code_studio.database.Item;

import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

public class Items_PricelistPerClientAddEditController extends BaseController implements Initializable {

	@FXML Button btnSave;
	@FXML Button btnCancel;

	@FXML CSTextField tfClientId;
	@FXML CSTextField tfClientName;
	@FXML CSTextField tfItemId;
	@FXML CSTextField tfItemName;
	@FXML CSTextField tfNetAmt;
	@FXML CSTextField tfDiscountRate;
	@FXML CSTextField tfBaseDiscountAmt;
	@FXML CSTextField tfBaseVatAmt; //osnovica PDV
	@FXML CSTextField tfVatAmt;     // PDV JM
	@FXML CSTextField tfGrossAmt;
	@FXML CSTextField tfMpcAmt;
	
	@FXML CheckBox  ckbIsActive;
	@FXML CheckBox  ckbIsDelivered;
	@FXML CheckBox  ckbIsNew;
	@FXML Button    btnItemList;
	
	private int mode;
	private final Double vatRate = 0.2;

	private CSTable<ClientPricelist> tblClientPricelist;
	private ClientPricelist clientPricelist;
	private CSRestService<ClientPricelist> rsClientPricelistAddUpdate;
	private final String urlclientPricelistAddUpdate = "/clientPricelist";
	
	private ClientSimple client;
	private Item selectedItem;
	
	@SuppressWarnings("unchecked")
	public Items_PricelistPerClientAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		System.out.println(mode);
		this.tblClientPricelist = (CSTable<ClientPricelist>) controllerParam;
		this.client = (ClientSimple) this.tblClientPricelist.getParentTable().getSelectedItem();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		rsClientPricelistAddUpdate = new CSRestService<>(urlclientPricelistAddUpdate);
		tfClientId.setText(client.getId().toString());
		tfClientName.setText(client.getName());
		
		if (mode == 0) {
			clientPricelist = new ClientPricelist();
			clientPricelist.setClient(client);
		} else if (mode == 1) {
	    	btnSave.setDisable(false);
	    	ckbIsActive.setDisable(false);
	    	ckbIsDelivered.setDisable(false);
	    	ckbIsNew.setDisable(false);
			
			clientPricelist = tblClientPricelist.getSelectedItem();
			tfItemId.setText(clientPricelist.getItem().getId().toString());
			tfItemName.setText(clientPricelist.getItem().getName());
			tfNetAmt.setText(clientPricelist.getMeasureUnitNetAmt().toString());
			tfDiscountRate.setText(clientPricelist.getDiscountRate().toString());
			tfBaseDiscountAmt.setText(clientPricelist.getDiscountAmt().toString());
			tfBaseVatAmt.setText(clientPricelist.getMeasureUnitBaseVatAmt().toString());
			tfVatAmt.setText(clientPricelist.getMeasureUnitVatAmt().toString());
			tfGrossAmt.setText(clientPricelist.getMeasureUnitGrossAmt().toString());
			ckbIsActive.setSelected(clientPricelist.getIsActive());
			ckbIsDelivered.setSelected(clientPricelist.getIsDelivered());
			ckbIsNew.setSelected(clientPricelist.getIsNew());
		} 
		
		btnItemList.setOnAction( _ -> {
			selectedItem = (Item) Common.displayForm(ControllerFactory.getController("Items_ItemSelectAddEditController", this.client, 2), btnItemList, "Odabir artikla");
			if (selectedItem != null) {
				tfItemId.setText(selectedItem.getId().toString());
				tfItemName.setText(selectedItem.getName());
				tfNetAmt.setText(selectedItem.getNetPriceDFak().toString());
				calculateAmounts(true);
				tfDiscountRate.requestFocus();
			}
		});
		
		tfNetAmt.onFocusChanged( (_, newValue) -> {
			if (newValue) { // focus gained
				tfDiscountRate.setText("0.00");
				tfBaseDiscountAmt.setText("0.00");
			}
			calculateAmounts(true);
		});
		
		tfBaseVatAmt.onFocusChanged( (_, _) -> {
			calculateAmounts(false);
		});
		
		tfDiscountRate.onFocusChanged( (_, newValue) -> {
			if (!newValue) { // focus lost
				calculateAmounts(true);
			}
		});
		
		tfItemName.onTextChanged( (_, _) -> {
		    if (tfItemName.getText().length() > 0) {
		    	btnSave.setDisable(false);
		    	ckbIsActive.setDisable(false);
		    	ckbIsDelivered.setDisable(false);
		    	ckbIsNew.setDisable(false);
		    } else {
		    	btnSave.setDisable(true);
		    	ckbIsActive.setDisable(true);
		    	ckbIsDelivered.setDisable(true);
		    	ckbIsNew.setDisable(true);
		    }
		});
		

		btnSave.setOnAction( e-> {
			if (selectedItem != null) {
				clientPricelist.setItem(selectedItem);
			}
			clientPricelist.setMeasureUnitNetAmt(tfNetAmt.getTextAsBigDecimal());
			clientPricelist.setDiscountRate(tfDiscountRate.getTextAsBigDecimal());
			clientPricelist.setDiscountAmt(tfBaseDiscountAmt.getTextAsBigDecimal());
			clientPricelist.setMeasureUnitBaseVatAmt(tfBaseVatAmt.getTextAsBigDecimal());
			clientPricelist.setMeasureUnitVatAmt(tfVatAmt.getTextAsBigDecimal());
			clientPricelist.setMeasureUnitGrossAmt(tfGrossAmt.getTextAsBigDecimal());
			clientPricelist.setIsActive(ckbIsActive.isSelected());
			clientPricelist.setIsDelivered(ckbIsDelivered.isSelected());
			clientPricelist.setIsNew(ckbIsNew.isSelected());
			/* TODO:
				clientPricelist.setCreatedDate();
				clientPricelist.setCreatedBy();
			*/
			ClientPricelist insertedClientPricelist = rsClientPricelistAddUpdate.addOrUpdate(clientPricelist);
			
			if (mode == 0) {
				this.tblClientPricelist.addItem(insertedClientPricelist);
			}

			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();

		});
		
		btnCancel.setOnAction(e->{
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

	}
	
	//TODO: Optimizovati, vrlo los ponavljajuci kod
	private void calculateAmounts(boolean useDiscountPercentForCalculation) {
		BigDecimal baseDiscountAmt = null;
		BigDecimal baseDiscountPct = null;
		
		if (useDiscountPercentForCalculation) {
			baseDiscountAmt = tfNetAmt.getTextAsBigDecimal()
					.divide(BigDecimal.valueOf(100.00))
					.multiply(tfDiscountRate.getTextAsBigDecimal());
			tfBaseDiscountAmt.setText(baseDiscountAmt.setScale(2, RoundingMode.HALF_EVEN).toString());
		} else {
			BigDecimal divider = tfNetAmt.getTextAsBigDecimal().subtract(tfBaseVatAmt.getTextAsBigDecimal());
			if (divider.compareTo(BigDecimal.valueOf(0.00)) != 0) {
				baseDiscountPct = divider
					.divide(tfNetAmt.getTextAsBigDecimal(), MathContext.DECIMAL32)
					.multiply(BigDecimal.valueOf(100.00));
			} else {
				baseDiscountPct = BigDecimal.ZERO;
			}

			tfDiscountRate.setText(baseDiscountPct.setScale(2, RoundingMode.HALF_EVEN).toString());
			baseDiscountAmt = tfNetAmt.getTextAsBigDecimal()
					.divide(BigDecimal.valueOf(100.00))
					.multiply(tfDiscountRate.getTextAsBigDecimal());
	
			tfBaseDiscountAmt.setText(baseDiscountAmt.setScale(2, RoundingMode.HALF_EVEN).toString());
		}

		tfBaseVatAmt.setText(
				tfNetAmt.getTextAsBigDecimal()
				.subtract(tfBaseDiscountAmt.getTextAsBigDecimal())
				.setScale(2, RoundingMode.HALF_EVEN)
				.toString()
		);
		
		tfVatAmt.setText(tfBaseVatAmt.getTextAsBigDecimal()
				.multiply(BigDecimal.valueOf(vatRate))
				.setScale(2, RoundingMode.HALF_EVEN)
				.toString()
		);
		
		tfGrossAmt.setText(tfBaseVatAmt.getTextAsBigDecimal()
				.add(tfVatAmt.getTextAsBigDecimal())
				.setScale(2, RoundingMode.HALF_EVEN)
				.toString()
		);
	}
	
}
