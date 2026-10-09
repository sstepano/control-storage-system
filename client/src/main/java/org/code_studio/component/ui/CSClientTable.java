package org.code_studio.component.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTableColumn;
import org.code_studio.database.Client;
import org.code_studio.database.Division;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;


public final class CSClientTable extends VBox {
	
	@FXML public CSTable<Client> csTable;
	@FXML private CSComboBox<Division> cbDivision;
	
    public final List <CSTableColumn> tableColumns = new ArrayList<>();
    
	private boolean addButtonVisible = true;
	private boolean viewButtonVisible = true;
	private boolean editButtonVisible = true;
	private boolean deleteButtonVisible = true;
	private boolean topLabelVisible = true;
	
	private int selectedDivisionId = 0;
	private boolean supplier = false;
	
	
	private String urlDataFetch = "/client/allPageableByDivisionIdWithPageSize/";
	private String urlSearch = "/client/search/";
	
	private CSRestService<Client> rsDataFetch;
	private CSRestService<Division> rsDivision;
	CSRestService<Client> rsSearch;
	
	private AtomicInteger pageId;
	private Integer pageSize = 50;
	
	public CSClientTable() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSClientTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

	@FXML public void initialize() {
		// base class init. Ako imamo definisane kolone u kontroli koju user koristi,
		// onda postavi njih. Ako ne, postavi samo defaultne Sifra i Naziv
		if (this.tableColumns.size() > 0) {
			this.csTable.tableColumns = this.tableColumns;
		}
		
		//TODO: Dodati ctx tako da mozemo da pozivamo TAB sa dobavljacima na btnView
		//MainController ctrl = ctx.getBean(MainController.class);
		
		rsDivision = new CSRestService<>("/division");
		rsDivision.fetch(new ParameterizedTypeReference<JsonResponse<Division>>() {});
		cbDivision.getItems().addAll(rsDivision.getDataAsObservableList());
		
		rsDataFetch = new CSRestService<>(urlDataFetch);
		rsDataFetch.setParentTable(csTable);
		pageId = new AtomicInteger(0);
		cbDivision.getSelectionModel().select(this.selectedDivisionId);
		
		rsSearch = new CSRestService<>(urlSearch);
		
		csTable.onDataNeeded(()->{
			rsDataFetch.fetch(pageId.getAndIncrement(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {});
			csTable.addItems(rsDataFetch.getDataAsObservableList());
		});

		cbDivision.onSelectionChanged(newItem -> {
			pageId.set(0);
			rsDataFetch.setUrl(urlDataFetch + cbDivision.getSelectionModel().getSelectedItem().getId().toString());
			rsDataFetch.fetch(pageId.getAndIncrement(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {});
			csTable.setItems(rsDataFetch.getDataAsObservableList());
			
			//ne okida se iz nekog razloga onrowselectionchanged kada se ddl promeni
		});
		
		csTable.onServerSearch(() -> {
			String searchKeyword = csTable.tfSearchBox.getText();
			
			if (searchKeyword.length() > 0) {
				pageId.set(0);
				rsDataFetch.setUrl(urlSearch + searchKeyword);
				rsDataFetch.fetch(pageId.get(), new ParameterizedTypeReference<JsonResponse<Client>>() {});
				csTable.setItems(rsDataFetch.getDataAsObservableList());
			} else {
				//search empty, fetch all data, like when opening form for the first time
				pageId.set(0);
				rsDataFetch.setUrl(urlDataFetch + cbDivision.getSelectionModel().getSelectedItem().getId().toString());
				rsDataFetch.fetch(pageId.get(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {});
				csTable.setItems(rsDataFetch.getDataAsObservableList());
			}
		});
		
		csTable.setAddEditDialog("Sales_ClientAddEdit");
		
	}
	
	public List<CSTableColumn> getTableColumns() {
		return tableColumns;
	}

	public String getUrlDataFetch() {
		return urlDataFetch;
	}

	public void setUrlDataFetch(String urlDataFetch) {
		this.urlDataFetch = urlDataFetch;
	}

	public int getSelectedDivisionId() {
		return selectedDivisionId;
	}

	public void setSelectedDivisionId(int selectedDivisionId) {
		this.selectedDivisionId = selectedDivisionId;
		cbDivision.getSelectionModel().select(this.selectedDivisionId);
	}

	public boolean isSupplier() {
		return supplier;
	}

	public void setSupplier(boolean supplier) {
		this.supplier = supplier;
		
		if (supplier) {
	    	urlDataFetch = "/supplier/allPageableByDivisionIdWithPageSize/";
	    	urlSearch = "/supplier/search/";
	    	csTable.setTopLabelText("DOBAVLJAČI");
		} else {
			urlDataFetch = "/client/allPageableByDivisionIdWithPageSize/";
			urlSearch = "/client/search/";
			csTable.setTopLabelText("KLIJENTI");
		}
	}
	
	public void refresh() {
		pageId.set(0);
		rsDataFetch.setUrl(urlDataFetch + cbDivision.getSelectionModel().getSelectedItem().getId().toString());
		rsDataFetch.fetch(pageId.getAndIncrement(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {}, false, () -> {
			csTable.setItems(rsDataFetch.getDataAsObservableList());
		});
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

	public boolean isTopLabelVisible() {
		return topLabelVisible;
	}

	public void setTopLabelVisible(boolean topLabelVisible) {
		csTable.setTopLabelVisible(topLabelVisible);
		this.topLabelVisible = topLabelVisible;
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

	
}
