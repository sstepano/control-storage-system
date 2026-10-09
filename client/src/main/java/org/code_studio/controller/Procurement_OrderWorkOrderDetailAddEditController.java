package org.code_studio.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;

import javafx.stage.Stage;

import javafx.scene.Node;
import javafx.scene.control.Button;

import org.code_studio.main.Common;
import org.code_studio.database.Item;
import org.code_studio.database.OrdersDetail;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSComboBoxCountry;


public class Procurement_OrderWorkOrderDetailAddEditController extends BaseController {

	@FXML Button btnSave;
	@FXML Button btnCancel;
	
	@FXML CSTextField tfItemId;
	@FXML CSTextField tfItemName;
	@FXML CSTextField tfItemNameEng;
	@FXML CSTextField tfBackorderQty;
	@FXML CSTextField tfReservedQty;
	@FXML CSTextField tfRmQty;
	@FXML CSTextField tfAmtOrder; // cena (order)
	@FXML CSTextField tfMinQty;
	@FXML CSTextField tfSpecAmt;
	@FXML CSTextField tfAmt;
	@FXML CSTextField tfOrderedQty; // naruceno
	@FXML CSTextField tfTotalOrderAmt;
	@FXML CSComboBoxCountry cbCountryOfImport;
	@FXML CSComboBoxCountry tfcbCountryOfOrigin;
	
	CSTable<Item> tblItem;
	CSTable<OrdersDetail> tblOrdersDetail;
	private Item item;
	
	@SuppressWarnings("unchecked")
	public Procurement_OrderWorkOrderDetailAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		List<Object> lstControllerParams = (List<Object>) controllerParam;
		tblItem = (CSTable<Item>) lstControllerParams.get(0);
		tblOrdersDetail = (CSTable<OrdersDetail>) lstControllerParams.get(1);
		item = tblItem.getSelectedItem();
	}

	@FXML public void initialize() {
		try {
				tfRmQty.onTextChanged((oldText, newText) -> {
					if (newText.length() > 0 && !newText.startsWith("0")) { // ne dozvoljavamo useru da snimo order sa 0 narucenih artikala
						try {
							BigDecimal newValue = new BigDecimal(newText);
							tfTotalOrderAmt.setText(newValue.multiply(tfAmtOrder.getTextAsBigDecimal()).toString());
							
							// ako user mrlja sa iznosima pa npr stavi cenu 0 a posle promeni broj, postavljamo na spec cenu opet
							if (tfAmtOrder.getTextAsBigDecimal() == BigDecimal.ZERO) {
								tfAmtOrder.setText(item.getPurchasePriceSpec() == null ? "0.00" : item.getPurchasePriceSpec().toString());
							}
						} catch (Exception ex) {
							Common.ShowNotification("GREŠKA", "Naručena količina nije validan broj", true);
							Common.logMessage(getClass(), ex, "ERROR");
						}
						tfOrderedQty.setText(newText);
					} else {
						tfOrderedQty.setText("0");
						tfTotalOrderAmt.setText("0.00");
					}
				});
				
				tfAmtOrder.onTextChanged((oldText, newText) -> {
					if (newText.length() > 0 && !newText.startsWith("0")) { // ne dozvoljavamo useru da snimo order sa 0 narucenih artikala
						try {
							BigDecimal newValue = new BigDecimal(newText);
							tfTotalOrderAmt.setText(newValue.multiply(tfRmQty.getTextAsBigDecimal()).toString());
							
							if (tfRmQty.getText().length() > 0 && !tfRmQty.getText().startsWith("0")
									&& !tfAmtOrder.getText().startsWith("0")) {
							}
						} catch (Exception ex) {
							Common.ShowNotification("GREŠKA", "Naručena količina/cena ordera nije validan broj", true);
							Common.logMessage(getClass(), ex, "ERROR");
							btnSave.setDisable(true);
						}
					} else {
						tfTotalOrderAmt.setText("0.00");
					}
				});

				tfTotalOrderAmt.onTextChanged((oldValue, newValue) -> {
					if (newValue != null && newValue.length() > 0 && !newValue.startsWith("0")) {
						btnSave.setDisable(false);
					} else {
						btnSave.setDisable(true);
					}
				});
			
				if (item != null) {
					tfItemId.setText(item.getId().toString());
					tfItemName.setText(item.getName());
					tfItemNameEng.setText(item.getNameEng());
					// TODO: nemam implementiran backorder u item. tfBackorderQty.setText(item.getBackorderQty());
					tfBackorderQty.setText("0");
					
					// TODO: nemam implementiran reserved u item. tfReservedQty.setText(item.getReservedQty());
					tfReservedQty.setText("0");
					
					tfAmtOrder.setText(item.getPurchasePriceSpec() == null ? "0.00" : item.getPurchasePriceSpec().toString());
					tfMinQty.setText(item.getMinQty() == null ? "0" : item.getMinQty().toString());
					tfSpecAmt.setText(item.getPurchasePriceSpec() == null ? "0" : item.getPurchasePriceSpec().toString());
					tfAmt.setText(item.getPurchasePrice() == null ? "0" : item.getPurchasePrice().toString());
				}

				btnCancel.setOnAction(e->{
					((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
				});
			} catch (Exception ex) {
				Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
				Common.logMessage(getClass(), ex, "ERROR");
			}
	} // initialize END
	
}
