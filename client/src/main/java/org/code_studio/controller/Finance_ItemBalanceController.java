package org.code_studio.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.database.ItemBalance;
import org.code_studio.database.ItemWarehouse;
import org.code_studio.database.Warehouse;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Finance_ItemBalanceController extends BaseController implements Initializable {

	@FXML private CSTable <ItemWarehouse> tblItemWarehouse;
	@FXML private CSTable <ItemWarehouse> tblItemWarehouseSupplies;
	@FXML private CSTable <Warehouse> tblWarehouse;
	@FXML private CSTable <ItemBalance> tblItemBalance;
	@FXML private RadioButton rbCurrentYearBalance;
	@FXML private RadioButton rbCompleteBalance;
	@FXML private TextField tfInQty;
	@FXML private TextField tfOutQty;
	@FXML private TextField tfQty;

	CSRestService<ItemWarehouse> rsItemWarehouse;
	CSRestService<ItemWarehouse> rsItemWarehouseSupplies;
	CSRestService<Warehouse> rsWarehouse;
	CSRestService<ItemBalance> rsItemBalance;
	CSRestService<ItemBalance> rsTotalItemBalance;
	
	final String urlItemWarehouse = "/itemWarehouse/allPageable";
	final String urlItemWarehouseSupplies = "/itemWarehouse/allByItemId/";
	final String urlWarehouse = "/warehouse";
	final String urlItemBalanceCurrentYearByItemId = "/itemBalance/allPageableItemBalanceForCurrentYearByWarehouseIdAndItemId/";
	final String urlItemBalanceByItemId = "/itemBalance/allPageableItemBalanceByWarehouseIdAndItemId/";
	final String urlTotalItemBalanceCurrentYearByItemId = "/itemBalance/allPageableTotalItemBalanceForCurrentYearByWarehouseIdAndItemId/";
	final String urlTotalItemBalanceByItemId = "/itemBalance/allPageableTotalItemBalanceByWarehouseIdAndItemId/";
	
	AtomicInteger itemPageId;
	AtomicInteger itemBalancePageId;

	public Finance_ItemBalanceController(Object controllerParam, int mode, ApplicationContext ctx) {
		itemPageId = new AtomicInteger(0);
		itemBalancePageId = new AtomicInteger(0);
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		rsItemWarehouse = new CSRestService<>(urlItemWarehouse);
		rsItemWarehouseSupplies = new CSRestService<>(urlItemWarehouseSupplies);
		rsWarehouse = new CSRestService<>(urlWarehouse);
		rsItemBalance = new CSRestService<>(urlItemBalanceCurrentYearByItemId);
		rsTotalItemBalance = new CSRestService<>(urlTotalItemBalanceCurrentYearByItemId);
		
		rsItemWarehouse.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>() {});
		tblItemWarehouse.setItems(rsItemWarehouse.getDataAsObservableList());
		
		rsWarehouse.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>() {});
		tblWarehouse.setItems(rsWarehouse.getDataAsObservableList());
		
		tblItemWarehouse.onRowSelectionChanged((_, newRow) -> {
			if (newRow != null) {
				ItemWarehouse itemWarehouse = (ItemWarehouse) newRow;
				rsItemWarehouseSupplies.setUrl(urlItemWarehouseSupplies + itemWarehouse.getItem().getId());
				rsItemWarehouseSupplies.fetch(new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>() {});
				tblItemWarehouseSupplies.setItems(rsItemWarehouseSupplies.getDataAsObservableList());
				updateTblItemBalance();
			}
		});
		
		tblItemWarehouse.onDataNeeded(() -> {
			rsItemWarehouse.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ItemWarehouse>>() {});
			tblItemWarehouse.addItems(rsItemWarehouse.getDataAsObservableList());			
		});
		
		tblWarehouse.onRowSelectionChanged( (_, newRow) -> {
			if (newRow != null) {
				Warehouse warehouse = (Warehouse) newRow;
				tblItemBalance.setTopLabelText("ULAZ - IZLAZ ZA MAGACIN: " + warehouse.getName());
				updateTblItemBalance();
			}
		});
		
		rbCurrentYearBalance.setOnAction( _ -> {
			updateTblItemBalance();
		});
		
		rbCompleteBalance.setOnAction( _ -> {
			updateTblItemBalance();
		});
		
	}
	
	private void updateTblItemBalance() {
		String url = rbCurrentYearBalance.isSelected() ? urlItemBalanceCurrentYearByItemId : urlItemBalanceByItemId;
		String urlTotal = rbCurrentYearBalance.isSelected() ? urlTotalItemBalanceCurrentYearByItemId : urlTotalItemBalanceByItemId;
		Warehouse warehouse = tblWarehouse.getSelectedItem();
		ItemWarehouse itemWarehouse = tblItemWarehouse.getSelectedItem();
		itemBalancePageId.set(0);

		if (warehouse != null && itemWarehouse != null) {
			rsItemBalance.setUrl(url + "" + warehouse.getId() + "/" + itemWarehouse.getItem().getId());
			rsItemBalance.fetch(itemBalancePageId.get(), new ParameterizedTypeReference<JsonResponse<ItemBalance>>() {});
			tblItemBalance.setItems(rsItemBalance.getDataAsObservableList());
			
			rsTotalItemBalance.setUrl(urlTotal + "" + warehouse.getId() + "/" + itemWarehouse.getItem().getId());
			rsTotalItemBalance.fetch(0, new ParameterizedTypeReference<JsonResponse<ItemBalance>>() {});
			System.out.println(rsTotalItemBalance.getDataAsObservableList());
			
			if (rsTotalItemBalance.getDataAsObservableList().size() > 0) {
				Integer inQty = rsTotalItemBalance.getDataAsObservableList().get(0).getInQty();
				Integer outQty = rsTotalItemBalance.getDataAsObservableList().get(0).getOutQty();
				Integer qty = inQty - outQty; 
				tfInQty.setText(inQty.toString());
				tfOutQty.setText(outQty.toString());
				tfQty.setText(qty.toString());
			} else {
				tfInQty.setText("0");
				tfOutQty.setText("0");
				tfQty.setText("0");
			}
		}
	}
}
