package org.code_studio.component.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTableColumn;
import org.code_studio.database.Item;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class CSItemTable extends VBox {

	@FXML public CSTable <Item> csTable;
	@FXML private CheckBox ckbShowInStockOnly;
	
	private boolean addButtonVisible = true;
	private boolean viewButtonVisible = true;
	private boolean editButtonVisible = true;
	private boolean deleteButtonVisible = true;
	private boolean rowCheckboxVisible = true;
	private boolean topSectionVisible = false;
	private boolean topLabelVisible = false;
	private boolean ckbShowInStockOnlyVisible = false;
	private boolean searchVisible = false;
	private String topLabelText = "";
	private String showInStockOnly = "0";
	
    public final List <CSTableColumn> tableColumns = new ArrayList<>();
	
    // 0 insert, 1 edit, 2 custom koristimo kod prikaza svih itema nezavisno od client id-a
	private int mode = 0;
	
	private Integer clientId;
	private Integer pageSize = 50;
	
	private AtomicInteger pageId;
	private CSRestService <Item> rsItem;

	private String addEditControllerName = "Items_ItemAddEditController";

	public CSItemTable() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSItemTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	

	public void initialize() {
		// base class init. Ako imamo definisane kolone u kontroli koju user koristi,
		// onda postavi njih. Ako ne, postavi defaultne iz kontrole
		if (this.tableColumns.size() > 0) {
			this.csTable.tableColumns = this.tableColumns;
		}
		
		rsItem = new CSRestService<>(buildUrl());
		pageId = new AtomicInteger(0);
		rsItem.setParentTable(csTable);
		
		ckbShowInStockOnly.setOnAction((e) -> {
			CheckBox ckb = (CheckBox) e.getSource();
			showInStockOnly = ckb.isSelected()
					? "1"
					: "0";
			pageId.set(0);
			rsItem.setUrl(buildUrl());
			this.refresh();
		});
		
		csTable.setAddEditDialog(addEditControllerName);
		rsItem.setUrl(buildUrl());

		csTable.onDataNeeded(() -> {
			rsItem.fetch(pageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
				csTable.addItems(rsItem.getDataAsObservableList());
			});
		});
		
		csTable.onServerSearch( () -> {
			this.refresh();
		});
		
		csTable.onRowDoubleClick((rowData) -> {
			csTable.showDoubleClickDefaultAction = true;
		});
		
	} //initialize END

	public List<CSTableColumn> getTableColumns() {
		return tableColumns;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
		//rsItem.setUrl(urlItem + clientId.toString() + "/" + this.showInStockOnly);
		rsItem.setUrl(buildUrl());
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
        rsItem.setUrl(buildUrl());
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
	
	public boolean isRowCheckboxVisible() {
		return rowCheckboxVisible;
	}

	public void setRowCheckboxVisible(boolean rowCheckboxVisible) {
		csTable.setRowCheckboxVisible(rowCheckboxVisible);
		this.rowCheckboxVisible = rowCheckboxVisible;
	}
	
	public String getTopLabelText() {
		return topLabelText;
	}

	public void setTopLabelText(String topLabelText) {
		csTable.setTopLabelText(topLabelText);
		this.topLabelText = topLabelText;
	}

	public void refresh() {
		pageId.set(0);
		rsItem.setUrl(buildUrl());
		csTable.clear();
		rsItem.fetch(pageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Item>>(){}, () -> {
			csTable.setItems(rsItem.getDataAsObservableList());
		});
	}
	
	/**
	 * Used to generate url for all types of situation where component is used
	 */
	private String buildUrl() {
		String url = "";
		String urlItemByClientId ="/item/allByClientIdPageable/";
		String urlItemSearch = "/item/allPageableByIdOrCodeOrNameWithPageSize/";
		String urlItemAll = "/item/allPageable";
		
		String searchText = csTable.tfSearchBox.getText();
		
        if (this.mode == 0 || mode == 1) { // standardno koriscenje PER CLIENT
			 url = urlItemByClientId;
		} else if (this.mode == 2) { // ovde koristimo formu gde nam trebaju SVI ITEMI, nezavisno od CLIENT ID-a
			url = urlItemAll;
		}

		if (searchText.length() > 0) {
			url = urlItemSearch + pageSize + "/" + searchText + "/" + showInStockOnly; 
		} else {
			url = url
				+ (clientId == null ? "" : "/" + clientId.toString()) 
				+ "/" + this.showInStockOnly;
		}
		return url;
	}

	public boolean isTopSectionVisible() {
		return topSectionVisible;
	}

	public void setTopSectionVisible(boolean topSectionVisible) {
		csTable.setTopSectionVisible(topSectionVisible);
		this.topSectionVisible = topSectionVisible;
	}

	public boolean isTopLabelVisible() {
		return topLabelVisible;
	}

	public void setTopLabelVisible(boolean topLabelVisible) {
		csTable.setTopLabelVisible(topLabelVisible);
		this.topLabelVisible = topLabelVisible;
	}

	public boolean isCkbShowInStockOnlyVisible() {
		return ckbShowInStockOnlyVisible;
	}


	public void setCkbShowInStockOnlyVisible(boolean ckbShowInStockOnlyVisible) {
		ckbShowInStockOnly.setVisible(ckbShowInStockOnlyVisible);
		this.ckbShowInStockOnlyVisible = ckbShowInStockOnlyVisible;
	}
	
	public boolean isSearchVisible() {
		return searchVisible;
	}


	public void setSearchVisible(boolean searchVisible) {
		this.searchVisible = searchVisible;
		csTable.setSearchVisible(searchVisible);
	}


	/**
	 * Getter returns Observable list, as it should
	 * @return
	 */
	public ObservableList <Node> getComponentBarItems() {
		return csTable.getComponentBarItems();
	}
	
	/**
	 * Setter accepts ONE NODE, instead of Observable list, because
	 * it is being called as many times, as we have items in the list 
	 * @return
	 */
	public void setComponentBarItems(Node componentBarItem) {
		csTable.componentBar.getChildren().add(componentBarItem);
	}


	/**
	 * @return the csTable
	 */
	public CSTable<Item> getCsTable() {
		return csTable;
	}


	/**
	 * @param csTable the csTable to set
	 */
	public void setCsTable(CSTable<Item> csTable) {
		this.csTable = csTable;
	}
	
}
