package org.code_studio.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSPhotoView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ui.CSClientTable;
import org.code_studio.database.Client;
import org.code_studio.database.ClientPricelist;
import org.code_studio.database.ClientPricelistTotal;
import org.code_studio.database.Item;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class Items_PricelistPerClientController extends BaseController implements Initializable {
	
	@FXML private CSClientTable tblClient;
	@FXML private CSTable<ClientPricelist> tblClientPricelist;
	@FXML private CSTable<ItemWarehouse> tblItemWarehouse;
	
	@FXML private Button btnDiscountRateChange;
	@FXML private Button btnSelectedChange;
	@FXML private Button btnAllChange;
	@FXML private Button btnDeleteEntirePricelist;
	
	@FXML private CSTextField tfDiscountRate;
	@FXML private CSTextField tfDiscountPercent;
	@FXML private RadioButton rbIncrease;
	@FXML private RadioButton rbDecrease;
	@FXML private CSPhotoView phtItemImage;
	
	@FXML private CSTextField tfNetPriceDFak;
	@FXML private CSTextField tfPriceDifferenceAmt;
	@FXML private CSTextField tfPriceDifferenceRate;
	@FXML private CSTextField tfNetPriceDFakSpec;
	
	@FXML private CSTextField tfClientPricelistTotalCnt;
	@FXML private CSTextField tfClientPricelistTotalAmt;
	@FXML private CSTextField tfClientPricelistActiveCnt;
	@FXML private CSTextField tfClientPricelistActiveAmt;
	@FXML private CSTextField tfClientPricelistInactiveCnt;
	@FXML private CSTextField tfClientPricelistInactiveAmt;
	
	private final String urlClientPricelist = "/clientPricelist/allPageableByClientId/";
	private CSRestService <ClientPricelist> rsClientPricelist;
	private AtomicInteger clientPricelistPageId;
	
	private final String urlClientPricelistUpdateDelete = "/clientPricelist";
	private CSRestService <ClientPricelist> rsClientPricelistUpdateDelete;
	
	private final String urlClientPricelistDeleteAll = "/clientPricelist/deleteAllByClientId/";
	private CSRestService <ClientPricelist> rsClientPricelistDeleteAll;
	
	private final String urlClientPricelistUpdateAllPricesByClientId = "/clientPricelist/updateAllPricesByClientId/";
	private CSRestService <ClientPricelist> rsClientPricelistUpdateAllPricesByClientId;

	private final String urlItemWwarehouse = "/itemWarehouse/allByItemId/";
	private CSRestService <ItemWarehouse> rsItemWwarehouse;
	
	private final String urlClientPricelistTotal = "/clientPricelist/totalByClientId/";
	private CSRestService <ClientPricelistTotal> rsClientPricelistTotal;

	
	public Items_PricelistPerClientController(Object controllerParam, int mode, ApplicationContext ctx) {}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		// BUTTONS UPDATE and DELETE
		rsClientPricelistUpdateDelete = new CSRestService<>(urlClientPricelistUpdateDelete);
		rsClientPricelistUpdateAllPricesByClientId = new CSRestService<>(urlClientPricelistUpdateAllPricesByClientId);
		rsClientPricelistDeleteAll = new CSRestService<>(urlClientPricelistDeleteAll);
		
		// ITEM WAREHOUSE
		rsItemWwarehouse = new CSRestService<>(urlItemWwarehouse);
		
		// CLIENT PRICELIST
		rsClientPricelistTotal = new CSRestService<>(urlClientPricelistTotal);
		
		// CLIENT
		tblClient.refresh();
		tblClient.csTable.onRowDoubleClick( _ -> {
			tblClient.csTable.showDoubleClickDefaultAction = true;
		});
		
		tblClient.csTable.onRowSelectionChanged( (_, newRow) -> {
			Client client = (Client) newRow;
			if (client != null) {
				clientPricelistPageId.set(0);
				rsClientPricelist.setUrl(urlClientPricelist + client.getId().toString());
				rsClientPricelist.fetch(clientPricelistPageId.get(), new ParameterizedTypeReference<JsonResponse<ClientPricelist>>() {});
				tblClientPricelist.setItems(rsClientPricelist.getDataAsObservableList());
				if (tblClientPricelist.tableView.getItems().size() > 0) {
					btnDeleteEntirePricelist.setDisable(false);
					rsClientPricelistTotal.setUrl(urlClientPricelistTotal + client.getId().toString());
					rsClientPricelistTotal.fetch(new ParameterizedTypeReference<JsonResponse<ClientPricelistTotal>>() {});
					tfClientPricelistTotalCnt.setText(rsClientPricelistTotal.getData().get(0).getTotalCnt().toString());
					tfClientPricelistTotalAmt.setText(rsClientPricelistTotal.getData().get(0).getTotalAmt().toString());
					tfClientPricelistActiveCnt.setText(rsClientPricelistTotal.getData().get(0).getActiveCnt().toString());
					tfClientPricelistActiveAmt.setText(rsClientPricelistTotal.getData().get(0).getActiveAmt().toString());
					tfClientPricelistInactiveCnt.setText(rsClientPricelistTotal.getData().get(0).getInactiveCnt().toString());
					tfClientPricelistInactiveAmt.setText(rsClientPricelistTotal.getData().get(0).getInactiveAmt().toString());
				} else {
					btnDeleteEntirePricelist.setDisable(true);
					tfClientPricelistTotalCnt.setText("0");
					tfClientPricelistTotalAmt.setText("0.00");
					tfClientPricelistActiveCnt.setText("0");
					tfClientPricelistActiveAmt.setText("0.00");
					tfClientPricelistInactiveCnt.setText("0");
					tfClientPricelistInactiveAmt.setText("0.00");
				}
			}
		});
		
		// CLIENT PRICELIST
		//tblClientPricelist.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); Ovo cemo ako B. explicitno trazi
		tblClientPricelist.setParentTable(tblClient.csTable);
		tblClientPricelist.setRestServiceDelete(rsClientPricelistUpdateDelete);
		rsClientPricelist = new CSRestService<>(urlClientPricelist);
		clientPricelistPageId = new AtomicInteger(0);

		tblClientPricelist.setAddEditDialog("Items_PricelistPerClientAddEditController");
		tblClientPricelist.onRowDoubleClick( _ -> {
			tblClientPricelist.showDoubleClickDefaultAction = true;
		});

		tblClientPricelist.onDataNeeded(() -> {
			rsClientPricelist.fetch(clientPricelistPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<ClientPricelist>>() {});
			tblClientPricelist.addItems(rsClientPricelist.getDataAsObservableList());
		});
		
		tblClientPricelist.onRowSelectionChanged( (_, newRow) -> {
			ClientPricelist clientPricelist = (ClientPricelist) newRow;
			if (clientPricelist != null) {
				Item item = clientPricelist.getItem();
				phtItemImage.setImagePath(item.getImagePath());
				
				rsItemWwarehouse.setUrl(urlItemWwarehouse + item.getId().toString());
				rsItemWwarehouse.fetch(new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>() {});
				tblItemWarehouse.setItems(rsItemWwarehouse.getDataAsObservableList());
				btnDeleteEntirePricelist.setDisable(false);
				
				//cena
				tfNetPriceDFak.setText(clientPricelist.getItem().getNetPriceDFak().toString());
				//razlika
				tfPriceDifferenceAmt.setText(
						clientPricelist.getItem().getNetPriceDFakSpec()
						.subtract(clientPricelist.getMeasureUnitNetAmt())
						.toString()
				);
				
				//Spec cena, poslednja
				tfNetPriceDFakSpec.setText(clientPricelist.getItem().getNetPriceDFakSpec().toString());
				
				tfPriceDifferenceRate.setText(
						tfNetPriceDFakSpec.getText().equals("0.00") ? "0.00" :
						tfPriceDifferenceAmt.getTextAsBigDecimal().setScale(4)
						.divide(tfNetPriceDFakSpec.getTextAsBigDecimal().setScale(4), RoundingMode.HALF_UP)
						.multiply(new BigDecimal(100))
						.toString()
				);
			} else {
				tblItemWarehouse.clear();
				phtItemImage.loadNoImage();
				btnDeleteEntirePricelist.setDisable(true);
				tfNetPriceDFak.setText("0.00");
				tfPriceDifferenceAmt.setText("0.00");
				tfNetPriceDFakSpec.setText("0.00");
				tfPriceDifferenceRate.setText("0.00");
			}
		});
		
		// Buttons actions
		btnDiscountRateChange.setOnAction( _ -> {
			if (tfDiscountRate.getTextAsBigDecimal().signum() > 0) {
				ClientPricelist clientPricelist = tblClientPricelist.getSelectedItem();
				if (clientPricelist != null) {
					clientPricelist.setDiscountRate(tfDiscountRate.getTextAsBigDecimal());
					//TODO: izmeniti i cene u skladu sa novim rabatom
					rsClientPricelistUpdateDelete.addOrUpdate(clientPricelist);
					tblClientPricelist.tableView.refresh();
				}
			}
		});
		
		btnSelectedChange.setOnAction( _ -> {
			int sign = rbIncrease.isSelected() ? 1 : -1;
			if (tfDiscountPercent.getTextAsBigDecimal().signum() > 0) {
				ClientPricelist clientPricelist = tblClientPricelist.getSelectedItem();
				if (clientPricelist != null) {
					BigDecimal changeAmt = clientPricelist.getMeasureUnitNetAmt()
							.divide(BigDecimal.valueOf(100.00))
							.multiply(tfDiscountPercent.getTextAsBigDecimal()
							.multiply(BigDecimal.valueOf(sign))
					);
					clientPricelist.setMeasureUnitNetAmt((clientPricelist.getMeasureUnitNetAmt().add(changeAmt)));
					rsClientPricelistUpdateDelete.addOrUpdate(clientPricelist);
					tblClientPricelist.tableView.refresh();
				}
			}
		});
		
		btnAllChange.setOnAction( _ -> {
			String increaseDecreaseFlag = rbIncrease.isSelected() ? "true" : "false";
			String clientId = tblClient.csTable.getSelectedItem().getId().toString();
			if (tfDiscountPercent.getTextAsBigDecimal().compareTo(BigDecimal.ZERO) > 0) {
				rsClientPricelistUpdateAllPricesByClientId.execute(clientId, tfDiscountPercent.getText(), increaseDecreaseFlag);
			}
			//tblClientPricelist.tableView.refresh(); REFRESH ne radi jer je promenjeo u bazi i moramo fetch ponovo sa rest-a
			clientPricelistPageId.set(0);
			rsClientPricelist.fetch(clientPricelistPageId.get(), new ParameterizedTypeReference<JsonResponse<ClientPricelist>>() {});
			tblClientPricelist.setItems(rsClientPricelist.getDataAsObservableList());
		});
		
		btnDeleteEntirePricelist.setOnAction( _ -> {
			Client client = tblClient.csTable.getSelectedItem();
			if (client != null) {
				rsClientPricelistDeleteAll.execute(client.getId().toString());
				clientPricelistPageId.set(0);
				rsClientPricelist.fetch(clientPricelistPageId.get(), new ParameterizedTypeReference<JsonResponse<ClientPricelist>>() {});
				tblClientPricelist.setItems(rsClientPricelist.getDataAsObservableList());
			}
		});
		
		//ITEM WAREHOUSE
		tblItemWarehouse.setParentTable(tblClientPricelist);
		tblItemWarehouse.onRowSelectionChanged( (_, _) -> {
		});
		
	}
}
