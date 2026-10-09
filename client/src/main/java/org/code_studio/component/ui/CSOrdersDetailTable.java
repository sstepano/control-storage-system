package org.code_studio.component.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTableColumn;
import org.code_studio.component.CSTextField;
import org.code_studio.database.OrdersDetail;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public final class CSOrdersDetailTable extends VBox {
	
	@FXML public CSTable<OrdersDetail> csTable;
	@FXML CSTextField tfOrdersTotal;

    public final List <CSTableColumn> tableColumns = new ArrayList<>();
	private boolean addButtonVisible = true;
	private boolean viewButtonVisible = true;
	private boolean editButtonVisible = true;
	private boolean deleteButtonVisible = true;
	private boolean topSectionVisible = false;
	private boolean searchVisible = false;
	private String topLabelText = "";
	
	private String urlDataFetch          = "";
	private final String urlDataFetchByOrderId = "/ordersDetail/allByOrderId/";
	private final String urlDataFetchByItemId  = "/ordersDetail/allPreviousOrdersByItemId/";
	private final String urlOrdersTotal = "/ordersDetail/sumQtyByItemId/";
	private final String urlSearch       = "/ordersDetail/search/";
	
	private Integer mode    = 0;
	private Integer orderId = 0;
	private Integer itemId  = 0;
	
	public CSRestService<OrdersDetail> rsDataFetch;
	public CSRestService<Integer> rsTotalsDataFetch;
	CSRestService<OrdersDetail> rsSearch;
	
	public CSOrdersDetailTable() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSOrdersDetailTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	public void fill() {
		rsDataFetch.fetch(new ParameterizedTypeReference<JsonResponse<OrdersDetail>>() {});
		csTable.setItems(rsDataFetch.getDataAsObservableList());
		
		rsTotalsDataFetch.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});
		if (rsDataFetch.getData() != null) {
			tfOrdersTotal.setText(rsTotalsDataFetch.getDataAsObservableList().getFirst().toString());
		} else {
			tfOrdersTotal.setText("0");
		}
	}


	public void initialize() {
		setSearchVisible(false);
		rsDataFetch = new CSRestService<>(urlDataFetch);
		rsSearch = new CSRestService<>(urlSearch);
		rsTotalsDataFetch = new CSRestService<>(urlOrdersTotal);
		
		csTable.onServerSearch(() -> {
			String searchKeyword = csTable.tfSearchBox.getText();
			
			if (searchKeyword.length() > 0) {
				rsDataFetch.setUrl(urlSearch + searchKeyword);
				rsDataFetch.fetch(new ParameterizedTypeReference<JsonResponse<OrdersDetail>>() {});
				csTable.setItems(rsDataFetch.getDataAsObservableList());
			} else {
				//search empty, fetch all data, like when opening form for the first time
				rsDataFetch.setUrl(urlDataFetch);
				rsDataFetch.fetch(new ParameterizedTypeReference<JsonResponse<OrdersDetail>>() {});
				csTable.setItems(rsDataFetch.getDataAsObservableList());
			}
		});
		
		csTable.setAddEditDialog("Procurement_OrderDetailAddEdit");
		
	}
	
	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public String getUrlDataFetch() {
		return urlDataFetch;
	}

	public void setUrlDataFetch(String urlDataFetch) {
		this.urlDataFetch = urlDataFetch;
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		urlDataFetch = urlDataFetchByOrderId + orderId.toString();
		rsDataFetch.setUrl(urlDataFetch);
		this.orderId = orderId;
	}


	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		urlDataFetch = urlDataFetchByItemId + itemId.toString();
		rsDataFetch.setUrl(urlDataFetch);		
		rsTotalsDataFetch.setUrl(urlOrdersTotal + itemId.toString());
		this.itemId = itemId;
	}
	
	// IMPLEMENTACIJA STD METODA U CSTABLE
	public List<CSTableColumn> getTableColumns() {
		return tableColumns;
	}
	public boolean isAddButtonVisible() {
		return addButtonVisible;
	}

	public void setAddButtonVisible(boolean addButtonVisible) {
		csTable.setAddButtonVisible(addButtonVisible);
		this.addButtonVisible = addButtonVisible;
	}

	public boolean isViewButtonVisible() {
		return viewButtonVisible;
	}

	public void setViewButtonVisible(boolean viewButtonVisible) {
		csTable.setViewButtonVisible(viewButtonVisible);
		this.viewButtonVisible = viewButtonVisible;
	}

	public boolean isEditButtonVisible() {
		return editButtonVisible;
	}

	public void setEditButtonVisible(boolean editButtonVisible) {
		csTable.setEditButtonVisible(editButtonVisible);
		this.editButtonVisible = editButtonVisible;
	}

	public boolean isDeleteButtonVisible() {
		return deleteButtonVisible;
	}

	public void setDeleteButtonVisible(boolean deleteButtonVisible) {
		csTable.setDeleteButtonVisible(deleteButtonVisible);
		this.deleteButtonVisible = deleteButtonVisible;
	}
	

	public String getTopLabelText() {
		return topLabelText;
	}

	public void setTopLabelText(String topLabelText) {
		csTable.setTopLabelText(topLabelText);
		this.topLabelText = topLabelText;
	}
	
	public boolean isTopSectionVisible() {
		return topSectionVisible;
	}

	public void setTopSectionVisible(boolean topSectionVisible) {
		csTable.setTopSectionVisible(topSectionVisible);
		this.topSectionVisible = topSectionVisible;
	}

	public boolean isSearchVisible() {
		return searchVisible;
	}

	public void setSearchVisible(boolean searchVisible) {
		this.searchVisible = searchVisible;
		csTable.setSearchVisible(searchVisible);
	}

	public Button getViewButton () {
		return csTable.btnView;
	}

}
