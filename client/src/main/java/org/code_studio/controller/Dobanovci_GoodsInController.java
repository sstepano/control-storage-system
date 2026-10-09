package org.code_studio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSTextField;
import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.ControllerFactory;
import org.code_studio.database.Client;
import org.code_studio.database.CraneQueue;
import org.code_studio.database.Division;
import org.code_studio.database.Palette;
import org.code_studio.database.PaletteDocument;
import org.code_studio.database.PaletteItem;
import org.code_studio.database.StoredProcedureResult;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Dobanovci_GoodsInController extends BaseController {
	private ApplicationContext ctx;
	final int pageSize = 50; //clientPageSize, to populate entire height
	
	@FXML private CSTable <Client> tblClient;
	@FXML private CSTable <PaletteDocument> tblPaletteDocument;
	@FXML private CSTable <Palette> tblPalette;
	@FXML private CSTable <PaletteItem> tblPaletteItem;
	@FXML private CSTable <CraneQueue> tblCraneQueue;
	@FXML private CSComboBox<Division> cbDivision;
	@FXML private Button btnMultiplePalettesAdd;
	@FXML private Button btnAllDocumentPalettesImport;
	@FXML private Button btnWarehouseGraphicDisplay;
	@FXML private Button btnPaletteImport;
	@FXML private ToggleGroup tgpClients;
	@FXML private ToggleGroup tgpDocumentStatus;
	@FXML private RadioButton rbClientWithQty;
	@FXML private RadioButton rbClientWithoutQty;
	@FXML private CSTextField tfStoredPaletteCountByClientId;
	@FXML private RadioButton rbDocumentPreparing;
	@FXML private RadioButton rbDocumentEntryStarted;
	@FXML private RadioButton rbDocumentOnCrane;
	@FXML private RadioButton rbDocumentAll;

	private final String urlClient = "/client/allPageableByDivisionIdAndHasQtyWithPageSize/";
	private final String urlClientSearch = "/client/search/";
	private final String urlPaletteDocumentByClientIdAndStatusIdAndTypeId = "/paletteDocument/allPageableByClientIdAndStatusIdAndTypeIdOrderByIdDesc/";
	private final String urlPalette = "/palette/allPageableByDocumentId/";
	private final String urlPaletteItem = "/paletteItem/allPageableByPaletteId/";
	private final String paletteDocumentAddEditControllerName = "Dobanovci_PaletteDocumentAddEditController";
	private final String paletteAddEditControllerName = "Dobanovci_PaletteAddEditController";
	private final String paletteItemAddEditControllerName = "Dobanovci_PaletteItemAddEditController";
	private final String urlPaletteDocumentDelete = "/paletteDocument";
	private final String urlPaletteDelete = "/palette";
	private final String urlPaletteItemDelete = "/paletteItem";
	private final String urlStoredPaletteCountByClientId = "/palette/storedPaletteCountByClientId/";
	private final String urlPaletteStore = "/palette/store/";
	private final String urlCraneQueue = "/craneQueue/allInputByClientId/";
	
	CSRestService<Client> rsvcClient;
	CSRestService<Client> rsClientSearch;
	CSRestService<PaletteDocument> rsvcPaletteDocument;
	CSRestService<Palette> rsvcPalette;
	CSRestService<PaletteItem> rsvcPaletteItem;
	CSRestService<PaletteDocument> rsPaletteDocumentDelete;
	CSRestService<Palette> rsPaletteDelete;
	CSRestService<PaletteItem> rsPaletteItemDelete;
	CSRestService<Division> rsDivision;
	CSRestService<Integer> rsStoredPaletteCountByClientId;
	CSRestService<CraneQueue> rsvcCraneQueue;
	CSRestService<StoredProcedureResult> rsvcPaletteStore;
	
	Boolean showClientswithQty = false;
	List<Object> paletteDocumentAddEditControllerParams; 
	
	public Dobanovci_GoodsInController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	public void initialize() {
		MainController ctrl = ctx.getBean(MainController.class);

		AtomicInteger clientPageId = new AtomicInteger(0);
		AtomicInteger paletteDocumentPageId = new AtomicInteger(0);
		AtomicInteger palettePageId = new AtomicInteger(0);
		AtomicInteger itemPageId = new AtomicInteger(0);

		rsvcClient = new CSRestService<>(urlClient);
		rsClientSearch = new CSRestService<>(urlClientSearch);
		rsvcPaletteDocument = new CSRestService<>(urlPaletteDocumentByClientIdAndStatusIdAndTypeId);
		rsPaletteDocumentDelete = new CSRestService<>(urlPaletteDocumentDelete);
		rsPaletteDelete = new CSRestService<>(urlPaletteDelete);
		rsvcPalette = new CSRestService<>(urlPalette);
		rsvcPaletteItem = new CSRestService<>(urlPaletteItem);
		rsPaletteItemDelete = new CSRestService<>(urlPaletteItemDelete);
		rsDivision = new CSRestService<>("/division");
		rsDivision.fetch(new ParameterizedTypeReference<JsonResponse<Division>>() {});
		rsStoredPaletteCountByClientId = new CSRestService<>(urlStoredPaletteCountByClientId);
		rsvcPaletteStore = new CSRestService<StoredProcedureResult>(urlPaletteStore);
		rsvcCraneQueue = new CSRestService<CraneQueue>(urlCraneQueue);
		rsvcCraneQueue.setParentTable(tblCraneQueue);
		
		cbDivision.getItems().addAll(rsDivision.getDataAsObservableList());
		//cbDivision.getSelectionModel().selectFirst(); // selektujemo prvi po defaultu
		cbDivision.getSelectionModel().select(1); // selektujemo DRUGI, ZARAD TESTIRANJA
		
		// CLIENT TABLE START
		
		if (tgpClients.getSelectedToggle() == (RadioButton) rbClientWithQty) {
			showClientswithQty = true;
		} else {
			showClientswithQty = false;
		}
		
		rsvcClient.setUrl(urlClient + cbDivision.getSelectionModel().getSelectedItem().getId().toString() + "/" + showClientswithQty);
		rsvcClient.fetch(clientPageId.getAndIncrement(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {});
		tblClient.setItems(rsvcClient.getDataAsObservableList());
		tblClient.onDataNeeded(()->{
			rsvcClient.fetch(clientPageId.getAndIncrement(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.addItems(rsvcClient.getDataAsObservableList());
		});

		tblClient.onServerSearch(() -> {
			String searchKeyword = tblClient.tfSearchBox.getText();
			
			if (searchKeyword.length() > 0) {
				rsClientSearch.setUrl(urlClientSearch + searchKeyword);
				rsClientSearch.fetch(0, new ParameterizedTypeReference<JsonResponse<Client>>() {});
				tblClient.setItems(rsClientSearch.getDataAsObservableList());
			} else {
				//search empty, fetch all data, like when opening form for the first time
				clientPageId.set(0);
				rsvcClient.fetch(clientPageId.get(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {});
				tblClient.setItems(rsvcClient.getDataAsObservableList());
			}
		});
		
		tblClient.onRowSelectionChanged((_, newRow) -> {
			// rucno setovanje queue, posto ne mozemo da imamo vise child tabela
			//tblCraneQueue.clear();
			
			Client selectedClient = (Client) newRow;
			if (selectedClient != null) {
				refreshTblCraneQueue(selectedClient.getId());

				Integer statusId = getSelectedDocumentStatusId();

				paletteDocumentPageId.set(0);
				rsvcPaletteDocument.setUrl(urlPaletteDocumentByClientIdAndStatusIdAndTypeId + selectedClient.getId().toString() + "/"+ statusId + "/1");
				rsvcPaletteDocument.fetch(paletteDocumentPageId.get(), new ParameterizedTypeReference<JsonResponse<PaletteDocument>>(){}, true, ()-> {
					tblPaletteDocument.setItems(rsvcPaletteDocument.getDataAsObservableList());	
				});
				
				//Palette Count
				if (tblClient.getSelectedItem() != null) {
				rsStoredPaletteCountByClientId.setUrl(urlStoredPaletteCountByClientId + tblClient.getSelectedItem().getId().toString());
				rsStoredPaletteCountByClientId.fetch( new ParameterizedTypeReference<JsonResponse<Integer>>() {});
				tfStoredPaletteCountByClientId.setText(
						rsStoredPaletteCountByClientId.getDataAsObservableList() == null || rsStoredPaletteCountByClientId.getDataAsObservableList().size() == 0
						? "0"
						: rsStoredPaletteCountByClientId.getDataAsObservableList().get(0).toString());
				}
			}
		});
		
		//TODO: code duplication
		tgpDocumentStatus.selectedToggleProperty().addListener((_, _, _) -> {
			Integer statusId = getSelectedDocumentStatusId();
			Client selectedClient = tblClient.getSelectedItem();
			paletteDocumentPageId.set(0);
			
			if (selectedClient != null) {
				rsvcPaletteDocument.setUrl(urlPaletteDocumentByClientIdAndStatusIdAndTypeId + selectedClient.getId().toString() + "/"+ statusId + "/1");
				rsvcPaletteDocument.fetch(paletteDocumentPageId.get(), new ParameterizedTypeReference<JsonResponse<PaletteDocument>>() {});
				tblPaletteDocument.setItems(rsvcPaletteDocument.getDataAsObservableList());
			} else {
				tblPaletteDocument.clear();
			}
		});
		
		tblClient.btnView.setOnAction( _ -> {
			ctrl.miProcurementSuppliers.fire();
		});
		
		tblClient.setAddEditDialog("SalesClientsAddEdit");
		
		List<Object> lstTblClientAdditionalParams = new ArrayList<>();
		lstTblClientAdditionalParams.add(tblClient);
		tblClient.setAddEditDialog("Sales_ClientAddEditController", lstTblClientAdditionalParams);
		// CLIENT TABLE END
		
		tgpClients.selectedToggleProperty().addListener((_, _, newValue) -> {
			if (newValue == (RadioButton) rbClientWithQty) {
				showClientswithQty = true;
			} else {
				showClientswithQty = false;
			}
			rsvcClient.setUrl(urlClient + cbDivision.getSelectionModel().getSelectedItem().getId().toString() + "/" + showClientswithQty);
			clientPageId.set(0);
			rsvcClient.fetch(clientPageId.getAndIncrement(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.setItems(rsvcClient.getDataAsObservableList());
		});
		
		cbDivision.onSelectionChanged(_ -> {
			rsvcClient.setUrl(urlClient + cbDivision.getSelectionModel().getSelectedItem().getId().toString() + "/" + showClientswithQty);
			clientPageId.set(0);
			rsvcClient.fetch(clientPageId.getAndIncrement(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.setItems(rsvcClient.getDataAsObservableList());
			
			//Palette Count
			if (tblClient.getSelectedItem() != null) {
			rsStoredPaletteCountByClientId.setUrl(urlStoredPaletteCountByClientId + tblClient.getSelectedItem().getId().toString());
			rsStoredPaletteCountByClientId.fetch( new ParameterizedTypeReference<JsonResponse<Integer>>() {});
			tfStoredPaletteCountByClientId.setText(
					rsStoredPaletteCountByClientId.getDataAsObservableList() == null || rsStoredPaletteCountByClientId.getDataAsObservableList().size() == 0
					? "0"
					: rsStoredPaletteCountByClientId.getDataAsObservableList().get(0).toString());
			}
		});
		
		// PALETTE DOCUMENT
		// sets palette document parents
		tblPaletteDocument.setParentTable(tblClient);
		tblPaletteDocument.setRestServiceDelete(rsPaletteDocumentDelete);
		rsvcPaletteDocument.setParentTable(tblPaletteDocument);
		paletteDocumentAddEditControllerParams = new ArrayList<>();
		paletteDocumentAddEditControllerParams.add(tblPaletteDocument);
		paletteDocumentAddEditControllerParams.add(0); //ULAZ
		tblPaletteDocument.onRowDoubleClick( _ -> {
			tblPaletteDocument.showDoubleClickDefaultAction = true;
		});
		
		tblPaletteDocument.setAddEditDialog(paletteDocumentAddEditControllerName, paletteDocumentAddEditControllerParams);
		tblPaletteDocument.onDataNeeded(()->{
			rsvcPaletteDocument.fetch(paletteDocumentPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<PaletteDocument>>() {});
			tblPaletteDocument.addItems(rsvcPaletteDocument.getDataAsObservableList());
		});
		
		tblPaletteDocument.onRowSelectionChanged((_, newRow) -> {
			PaletteDocument newRowData = (PaletteDocument) newRow;
			
			if (newRowData != null && newRowData.getId() != null) { //TODO: budz, jer kad dodam novi red, ID je null dok ne fetchujem iz baze autoincrement
					palettePageId.set(0);
					rsvcPalette.setUrl(urlPalette + newRowData.getId().toString());
					rsvcPalette.fetch(palettePageId.get(), new ParameterizedTypeReference<JsonResponse<Palette>>() {});
					tblPalette.setItems(rsvcPalette.getDataAsObservableList());
					
					if (newRowData.getPaletteDocumentStatus().getId() == 1) { // U RADU
					    tblPaletteDocument.btnEdit.setDisable(false);
					    tblPaletteDocument.btnDelete.setDisable(false);
					    tblPalette.btnAdd.setDisable(false);
					    btnMultiplePalettesAdd.setDisable(false);
					    btnPaletteImport.setDisable(false);
					    btnAllDocumentPalettesImport.setDisable(false);
					    if (tblPalette.getSelectedItem()!= null) {
						    tblPalette.btnEdit.setDisable(false);
						    tblPalette.btnDelete.setDisable(false);
					    }
					} else { // smesten dokument
					    tblPaletteDocument.btnEdit.setDisable(true);
					    tblPaletteDocument.btnDelete.setDisable(true);
					    tblPalette.btnAdd.setDisable(true);
					    tblPalette.btnEdit.setDisable(true);
					    tblPalette.btnDelete.setDisable(true);
					    btnMultiplePalettesAdd.setDisable(true);
					    btnPaletteImport.setDisable(true);
					    btnAllDocumentPalettesImport.setDisable(false);
					}
			} else {
			    tblPalette.btnAdd.setDisable(true);
			    tblPalette.btnEdit.setDisable(true);
			    tblPalette.btnDelete.setDisable(true);
			    btnMultiplePalettesAdd.setDisable(true);
			    btnPaletteImport.setDisable(true);
			    btnAllDocumentPalettesImport.setDisable(true);
			}
		});
		
		tblPaletteDocument.onDataNeeded(()->{
			rsvcPaletteDocument.fetch(paletteDocumentPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<PaletteDocument>>() {});
			tblPaletteDocument.addItems(rsvcPaletteDocument.getDataAsObservableList());
		});

		// PALETTE
		tblPalette.setParentTable(tblPaletteDocument);
		tblPalette.setAddEditDialog(paletteAddEditControllerName);
		tblPalette.setRestServiceDelete(rsPaletteDelete);

		tblPalette.onRowDoubleClick( _ -> {
			tblPalette.showDoubleClickDefaultAction = true;
		});

		tblPalette.onRowSelectionChanged((_, newRow) -> {
			itemPageId.set(0);
			Palette newRowData = (Palette) newRow;
			if (newRowData != null && newRowData.getId() != null) {
				rsvcPaletteItem.setUrl(urlPaletteItem + newRowData.getId().toString());
				rsvcPaletteItem.fetch(itemPageId.get(), new ParameterizedTypeReference<JsonResponse<PaletteItem>>() {});
				tblPaletteItem.setItems(rsvcPaletteItem.getDataAsObservableList());
				
				if (newRowData.getPaletteStatus().getId() == 1) { // Priprema
				    tblPalette.btnEdit.setDisable(false);
				    tblPalette.btnDelete.setDisable(false);
				    tblPaletteItem.btnAdd.setDisable(false);
				    btnPaletteImport.setDisable(false);
				    btnAllDocumentPalettesImport.setDisable(false);
				    
				    if (tblPaletteItem.getSelectedItem()!= null) {
					    tblPaletteItem.btnEdit.setDisable(false);
					    tblPaletteItem.btnDelete.setDisable(false);
				    }
				}
				else { // smestena paleta ili u procesu ulaza
					tblPalette.btnEdit.setDisable(true);
					tblPalette.btnDelete.setDisable(true);
					tblPaletteItem.btnAdd.setDisable(true);
					tblPaletteItem.btnEdit.setDisable(true);
					tblPaletteItem.btnDelete.setDisable(true);
				    btnPaletteImport.setDisable(true);
				    btnAllDocumentPalettesImport.setDisable(true);

				}
			} else {
			    tblPaletteItem.btnAdd.setDisable(true);
			    tblPaletteItem.btnEdit.setDisable(true);
			    tblPaletteItem.btnDelete.setDisable(true);
			}
		});
		
		btnMultiplePalettesAdd.setOnAction( _ -> {
			List<Object> lstsControllerParam = new ArrayList<>();
			lstsControllerParam.add(tblPalette);
			lstsControllerParam.add(tblPaletteItem);
			Common.displayForm(ControllerFactory.getController("Dobanovci_PaletteGroupAddEditController", lstsControllerParam, 0), tblPalette, "%label.add.text");
		});

		
		// PALETTE ITEM
		tblPaletteItem.setParentTable(tblPalette);
		tblPaletteItem.setAddEditDialog(paletteItemAddEditControllerName);
		tblPaletteItem.setRestServiceDelete(rsPaletteItemDelete);
		
		tblPaletteItem.onDataNeeded(()->{
			rsvcPaletteItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<PaletteItem>>() {});
			tblPaletteItem.addItems(rsvcPaletteItem.getDataAsObservableList());
		});
		
		tblPaletteItem.onRowDoubleClick((_)->{
			tblPaletteItem.showDoubleClickDefaultAction = true;
		});
		
		tblPaletteItem.onRowSelectionChanged((_, _)->{
			if (tblPalette.getSelectedItem()!= null && tblPalette.getSelectedItem().getPaletteStatus().getId() != 1) {
				tblPaletteItem.btnAdd.setDisable(true);
			}
		});

		//stores selected palette
		btnPaletteImport.setOnAction( (_) -> {
			if (tblPalette.getSelectedItem() != null) {
				storePalette(tblPalette.getSelectedItem().getPaletteCode());
			}
		});
		
		//stores all palettes in the doc, which are in status 1
		btnAllDocumentPalettesImport.setOnAction( _ -> {
			for (Palette palette : tblPalette.tableView.getItems()) {
				if (palette.getPaletteStatus().getId() == 1) {
					storePalette(palette.getPaletteCode());
				}
			}
			tblPalette.refresh();
			//Common.ShowNotification("INFO", "Zahtev za ulaz svih paleta označenog dokumenta je registrovan.\n" + "Palete je neophodno stavljati na traku ispravnim redosledom." , false);
		});
		
		
		// CRANE QUEUE
		tblCraneQueue.setParentTable(tblClient); // ne moze dve child tabele. Ovu cu rucno da resetujem dok ne napravim listu child tabela u CSTable
		rsvcCraneQueue.setParentTable(tblCraneQueue);
		
		tblCraneQueue.btnRefresh.setOnAction( (_) -> {
			refreshTblCraneQueue(tblClient.getSelectedItem().getId());
		});
		
		
		// bind buttons enable/disable
		/**
		BooleanProperty tableIsEmpty = new SimpleBooleanProperty(!tblPalette.tableView.getItems().isEmpty());
		btnPaletteImport.disableProperty().bind(
				////
			Bindings.when(
					tableIsEmpty
				//.or(new SimpleBooleanProperty(tblPalette.getSelectedItem().getStatusId() != 1))
			)
			.then(true)
			.otherwise(false)
			////
			Bindings.size(tblPalette.tableView.getItems()).lessThan(1)
		);
		*/

		
	} // initialize END
	

	/**
	 * Stores palette with the given code
	 * calls SP in DB
	 * @param paletteCode
	 */
	private void storePalette(String paletteCode) {
		rsvcPaletteStore.setUrl(urlPaletteStore + paletteCode);
		rsvcPaletteStore.fetch(new ParameterizedTypeReference<JsonResponse<StoredProcedureResult>>() {});
		Common.ShowNotification("INFO", "Zahtev za ulaz palete " + paletteCode + "\nje poslat na kran" , false);
	}
	
	/**
	 * Refreshes tblCraneQueue
	 */
	private void refreshTblCraneQueue (Integer clientId) {
		if (clientId != null) {
			rsvcCraneQueue.setUrl(urlCraneQueue + clientId);
			rsvcCraneQueue.fetch(new ParameterizedTypeReference<JsonResponse<CraneQueue>>() {}, true, () -> {
				tblCraneQueue.setItems(rsvcCraneQueue.getDataAsObservableList());				
			} );	
		}
	}
	
	private Integer getSelectedDocumentStatusId () {
		Integer statusId = 1;
		if (tgpDocumentStatus.getSelectedToggle() == (RadioButton) rbDocumentPreparing) {
			statusId = 1;
		} else if (tgpDocumentStatus.getSelectedToggle() == (RadioButton) rbDocumentEntryStarted) {
			statusId = 2;
		} else if (tgpDocumentStatus.getSelectedToggle() == (RadioButton) rbDocumentOnCrane) {
			statusId = 6;
		} else if (tgpDocumentStatus.getSelectedToggle() == (RadioButton) rbDocumentAll) {
			statusId = 0;
		}
		
		return statusId;
	}

	
}
