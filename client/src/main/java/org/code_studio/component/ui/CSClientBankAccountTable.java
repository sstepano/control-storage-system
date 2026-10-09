package org.code_studio.component.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTableColumn;
import org.code_studio.database.ClientBankAccount;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class CSClientBankAccountTable extends VBox implements Initializable {

	@FXML public CSTable <ClientBankAccount> csTable;
	private boolean addButtonVisible = true;
	private boolean viewButtonVisible = true;
	private boolean editButtonVisible = true;
	private boolean deleteButtonVisible = true;
	private String topLabelText = "";
	
    public final List <CSTableColumn> tableColumns = new ArrayList<>();
	
    // 0 insert, 1 edit, 2 custom koristimo kod prikaza svih itema nezavisno od client id-a
	//private int mode = 0;
	@SuppressWarnings("unused")
    private Integer pageSize = 50;
	private AtomicInteger pageId;
	String urlItemSearch = "/clientBankAccount/allPageableByAccountNumber/";
	private CSRestService <ClientBankAccount> rsItem;
	private Integer clientId;
	private String urlItem = "/clientBankAccount/allPageable";
	private String urlItemByClientId = "/clientBankAccount/allPageableByClientId/";
	private String addEditControllerName = "Lookup_ClientBankAccountAddEditController";
	
	private CSRestService <ClientBankAccount> rsItemDelete;
	private String urlItemDelete = "/clientBankAccount";

	public CSClientBankAccountTable() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSClientBankAccountTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		// base class init and defaults
		this.csTable.tableColumns = this.tableColumns;
		pageId = new AtomicInteger(0);
		rsItem = new CSRestService<>(urlItem);
		rsItemDelete = new CSRestService<>(urlItemDelete);
		csTable.setRestServiceDelete(rsItemDelete);
		csTable.setAddEditDialog(addEditControllerName);
		rsItem.setUrl(urlItem);

		csTable.onDataNeeded(() -> {
			rsItem.fetch(pageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ClientBankAccount>>() {});
			csTable.addItems(rsItem.getDataAsObservableList());
		});
		
		csTable.onServerSearch( () -> {
			String searchText = csTable.tfSearchBox.getText();
			String search = null;
			
		    search = searchText.length() > 0 
				? urlItemSearch + searchText
				: this.urlItem;
			    
			pageId.set(0);
			rsItem.setUrl(search);
			rsItem.fetch(pageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ClientBankAccount>>() {});
			csTable.setItems(rsItem.getDataAsObservableList());
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
		
		if (clientId != null) {
			rsItem.setUrl(urlItemByClientId + clientId.toString());
		}
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

	public void refresh() {
		pageId.set(0);
		rsItem.fetch(pageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<ClientBankAccount>>() {});
		csTable.setItems(rsItem.getDataAsObservableList());
	}
	
}
