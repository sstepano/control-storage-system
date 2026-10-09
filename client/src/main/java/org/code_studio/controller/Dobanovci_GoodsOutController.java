package org.code_studio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.Client;
import org.code_studio.database.CraneQueue;
import org.code_studio.database.Division;
import org.code_studio.database.Palette;
import org.code_studio.database.PaletteDocument;
import org.code_studio.database.PaletteItem;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Dobanovci_GoodsOutController extends BaseController {
	private ApplicationContext ctx;
	final int pageSize = 50; //clientPageSize, to populate entire height
	
	@FXML private CSTable <Client> tblClient;
	@FXML private CSTable <PaletteDocument> tblPaletteDocument;
	@FXML private CSTable <Palette> tblPalette;
	@FXML private CSTable <Palette> tblPaletteByItemId;
	@FXML private CSTable <Palette> tblPaletteByDocumentId;
	@FXML private CSTable <PaletteItem> tblPaletteItem;
	@FXML private CSTable <PaletteItem> tblPaletteItemByClientId;
	@FXML private CSTable <CraneQueue> tblCraneQueue;
	@FXML private CSComboBox<Division> cbDivision;
	@FXML private Button btnPaletteExport;
	@FXML private Button btnWarehouseGraphicDisplay;
	@FXML private CSTextField tfStoredPaletteCountByClientId;
	
	@FXML private ToggleGroup tgpDocumentStatus;
	@FXML private RadioButton rbDocumentOutPreparing;
	@FXML private RadioButton rbDocumentOutStarted;
	@FXML private RadioButton rbDocumentProcessing;
	@FXML private RadioButton rbDocumentAll;
	
	@FXML private Button btnPaletteMarkForExit;
	@FXML private Button btnAllPalettesMarkForExit;
	@FXML private Button btnPaletteMarkForExitConsist;
	@FXML private Button btnAllPalettesMarkForExitConsist;

	private final String urlClient = "/client/allPageableByDivisionIdWithPageSize/";
	private final String urlClientSearch = "/client/search/";
	private final String urlPaletteDocumentByClientIdAndStatusIdAndTypeId = "/paletteDocument/allPageableByClientIdAndStatusIdAndTypeIdOrderByIdDesc/";
	private final String urlPalette = "/palette/allPageableStoredByClientId/";
	private final String urlPaletteByItemId = "/palette/allPageableStoredByItemId/";	
	private final String urlPaletteFetchUpToDateObject = "/palette";
	private final String urlPaletteByDocumentId = "/palette/allPageableByDocumentId/";
	private final String urlPaletteItem = "/paletteItem/allPageableByPaletteId/";
	private final String urlPaletteItemByClientId = "/paletteItem/allPageableByClientId/";
	
	private final String paletteDocumentAddEditControllerName = "Dobanovci_PaletteDocumentAddEditController";
	private final String paletteAddEditControllerName = "Dobanovci_PaletteAddEditController";
	private final String paletteItemAddEditControllerName = "Dobanovci_PaletteItemAddEditController";
	private final String urlPaletteDocumentDelete = "/paletteDocument";
	private final String urlPaletteDelete = "/palette";
	private final String urlPaletteItemDelete = "/paletteItem";
	private final String urlStoredPaletteCountByClientId = "/palette/storedPaletteCountByClientId/";
	private final String urlPaletteFetch = "/palette/fetch/";
	private final String urlCraneQueue = "/craneQueue/allOutputByClientId/";
	
	private final String urlPaletteUpdateDocumentId = "/palette/updateDocumentId/";
	
	CSRestService<Client> rsvcClient;
	CSRestService<Client> rsClientSearch;
	CSRestService<PaletteDocument> rsvcPaletteDocument;
	CSRestService<Palette> rsvcPalette;
	CSRestService<Palette> rsvcPaletteByItemId;
	CSRestService<Palette> rsvcPaletteByDocumentId;
	CSRestService<PaletteItem> rsvcPaletteItem;
	CSRestService<PaletteItem> rsvcPaletteItemByClientId;
	CSRestService<PaletteDocument> rsPaletteDocumentDelete;
	CSRestService<Palette> rsPaletteDelete;
	CSRestService<PaletteItem> rsPaletteItemDelete;
	CSRestService<Client> rsClientIn;
	CSRestService<Client> rsClientOut;
	CSRestService<Division> rsDivision;
	CSRestService<Integer> rsStoredPaletteCountByClientId;
	CSRestService<CraneQueue> rsvcCraneQueue;
	CSRestService<Integer> rsvcPaletteFetch;
	
	CSRestService<Integer> rsvcUpdatePaletteDocumentId;
	CSRestService<Palette> rsvcPaletteFetchUpToDateObject;
	
	List<Object> paletteDocumentAddEditControllerParams; 
	
	public Dobanovci_GoodsOutController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	public void initialize() {
		MainController ctrl = ctx.getBean(MainController.class);
		/**
		 * Jos uvek nije skroz spreman drag and drop !
		tblPalette.setDragAndDropEnabled(true, Palette.class);
		tblPaletteByDocumentId.setDragAndDropEnabled(true, Palette.class);
		**/
		tblPaletteByDocumentId.setDeleteRestServiceAllowedMissing(true);
		
		AtomicInteger clientPageId = new AtomicInteger(0);
		AtomicInteger paletteDocumentPageId = new AtomicInteger(0);
		AtomicInteger paletteByDocumentIdPageId = new AtomicInteger(0);
		AtomicInteger palettePageId = new AtomicInteger(0);
		AtomicInteger itemPageId = new AtomicInteger(0);
		AtomicInteger paletteByItemIdPageId = new AtomicInteger(0);
		AtomicInteger itemByClientIdPageId = new AtomicInteger(0);

		rsvcClient = new CSRestService<>(urlClient);
		rsClientSearch = new CSRestService<>(urlClientSearch);
		rsvcPaletteDocument = new CSRestService<>(urlPaletteDocumentByClientIdAndStatusIdAndTypeId);
		rsPaletteDocumentDelete = new CSRestService<>(urlPaletteDocumentDelete);
		rsPaletteDelete = new CSRestService<>(urlPaletteDelete);
		rsvcPalette = new CSRestService<>(urlPalette);
		rsvcPaletteByItemId = new CSRestService<>(urlPaletteByItemId);
		rsvcPaletteByDocumentId = new CSRestService<>(urlPaletteByDocumentId);
		rsvcPaletteItem = new CSRestService<>(urlPaletteItem);
		rsvcPaletteItemByClientId = new CSRestService<>(urlPaletteItemByClientId);
		rsPaletteItemDelete = new CSRestService<>(urlPaletteItemDelete);
		rsDivision = new CSRestService<>("/division");
		rsDivision.fetch(new ParameterizedTypeReference<JsonResponse<Division>>() {});
		rsStoredPaletteCountByClientId = new CSRestService<>(urlStoredPaletteCountByClientId);
		
		rsvcPaletteFetch = new CSRestService<Integer>(urlPaletteFetch);
		rsvcCraneQueue = new CSRestService<CraneQueue>(urlCraneQueue);
		rsvcCraneQueue.setParentTable(tblCraneQueue);
		
		rsvcUpdatePaletteDocumentId = new CSRestService<Integer>(urlPaletteUpdateDocumentId);
		rsvcPaletteFetchUpToDateObject = new CSRestService<Palette>(urlPaletteFetchUpToDateObject);

		cbDivision.getItems().addAll(rsDivision.getDataAsObservableList());
		//cbDivision.getSelectionModel().selectFirst(); // selektujemo prvi po defaultu
		cbDivision.getSelectionModel().select(1); // selektujemo DRUGI, ZARAD TESTIRANJA
		
		// CLIENT TABLE START
		rsvcClient.setUrl(urlClient + cbDivision.getSelectionModel().getSelectedItem().getId().toString());
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
			Client selectedClient = (Client) newRow;
			if (selectedClient != null) {
				refreshTblCraneQueue(selectedClient.getId());

				Integer statusId = getSelectedDocumentStatusId();
				
				paletteDocumentPageId.set(0);
				rsvcPaletteDocument.setUrl(urlPaletteDocumentByClientIdAndStatusIdAndTypeId + selectedClient.getId().toString() + "/"+ statusId + "/2");
				rsvcPaletteDocument.fetch(paletteDocumentPageId.get(), new ParameterizedTypeReference<JsonResponse<PaletteDocument>>() {});
				tblPaletteDocument.setItems(rsvcPaletteDocument.getDataAsObservableList());
				
				//Palette Count
				if (tblClient.getSelectedItem() != null) {
				rsStoredPaletteCountByClientId.setUrl(urlStoredPaletteCountByClientId + tblClient.getSelectedItem().getId().toString());
				rsStoredPaletteCountByClientId.fetch( new ParameterizedTypeReference<JsonResponse<Integer>>() {});
				tfStoredPaletteCountByClientId.setText(
						rsStoredPaletteCountByClientId.getDataAsObservableList() == null || rsStoredPaletteCountByClientId.getDataAsObservableList().size() == 0
						? "0"
						: rsStoredPaletteCountByClientId.getDataAsObservableList().get(0).toString());
				}
				
				palettePageId.set(0);
				rsvcPalette.setUrl(urlPalette + selectedClient.getId());
				rsvcPalette.fetch(palettePageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Palette>>() {});
				tblPalette.setItems(rsvcPalette.getDataAsObservableList());
				
				itemByClientIdPageId.set(0);
				rsvcPaletteItemByClientId.setUrl(urlPaletteItemByClientId + selectedClient.getId().toString());
				rsvcPaletteItemByClientId.fetch(itemByClientIdPageId.get(), new ParameterizedTypeReference<JsonResponse<PaletteItem>>() {});
				tblPaletteItemByClientId.setItems(rsvcPaletteItemByClientId.getDataAsObservableList());
			}
		});
		
		//TODO: code duplication
		tgpDocumentStatus.selectedToggleProperty().addListener((_, _, _) -> {
			Integer statusId = getSelectedDocumentStatusId();
			Client selectedClient = tblClient.getSelectedItem();
			paletteDocumentPageId.set(0);
			
			if (selectedClient != null) {
				rsvcPaletteDocument.setUrl(urlPaletteDocumentByClientIdAndStatusIdAndTypeId + selectedClient.getId().toString() + "/"+ statusId + "/2");
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
		
		/** Drag and drop nije jos spreman !
		tblClient.onFocusChanged((_, newValue) -> {
			if (newValue) {
				tblPalette.setDragAndDropEnabled(true, Palette.class);
				tblPaletteByDocumentId.setDragAndDropEnabled(true, Palette.class);
				tblPaletteByDocumentId.setDeleteRestServiceAllowedMissing(true);
			}
		});
		**/
		// CLIENT TABLE END
		
		cbDivision.onSelectionChanged(_ -> {
			rsvcClient.setUrl(urlClient + cbDivision.getSelectionModel().getSelectedItem().getId().toString());
			clientPageId.set(0);
			rsvcClient.fetch(clientPageId.getAndIncrement(), pageSize, new ParameterizedTypeReference<JsonResponse<Client>>() {});
			tblClient.setItems(rsvcClient.getDataAsObservableList());
		});
		
		// PALETTE DOCUMENT
		paletteDocumentAddEditControllerParams = new ArrayList<>();
		paletteDocumentAddEditControllerParams.add(tblPaletteDocument);
		paletteDocumentAddEditControllerParams.add(1); //IZLAZ
		tblPaletteDocument.setParentTable(tblClient);
		tblPaletteDocument.setRestServiceDelete(rsPaletteDocumentDelete);
		tblPaletteDocument.onRowDoubleClick( _ -> {
			tblPaletteDocument.showDoubleClickDefaultAction = true;
		});
		
		tblPaletteDocument.setAddEditDialog(paletteDocumentAddEditControllerName, paletteDocumentAddEditControllerParams);
		tblPaletteDocument.onDataNeeded(()->{
			rsvcPaletteDocument.fetch(paletteDocumentPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<PaletteDocument>>() {});
			tblPaletteDocument.addItems(rsvcPaletteDocument.getDataAsObservableList());
		});

		tblPaletteDocument.onRowSelectionChanged((_, newRow) -> {
			PaletteDocument selectedPaletteDocument = (PaletteDocument) newRow;
			if (selectedPaletteDocument != null && selectedPaletteDocument.getId() != null) { //TODO: budz, jer kad dodam novi red, ID je null dok ne fetchujem iz baze autoincrement
				rsvcPaletteByDocumentId.setUrl(urlPaletteByDocumentId + selectedPaletteDocument.getId().toString());
				rsvcPaletteByDocumentId.fetch(paletteByDocumentIdPageId.get(), new ParameterizedTypeReference<JsonResponse<Palette>>() {});
				tblPaletteByDocumentId.setItems(rsvcPaletteByDocumentId.getDataAsObservableList());
			}
		});
		
		tblPaletteDocument.onDataNeeded(()->{
			rsvcPaletteDocument.fetch(paletteDocumentPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<PaletteDocument>>() {});
			tblPaletteDocument.addItems(rsvcPaletteDocument.getDataAsObservableList());
		});

		// PALETTE
		tblPalette.setParentTable(tblClient);
		tblPalette.setAddEditDialog(paletteAddEditControllerName);
		tblPalette.setRestServiceDelete(rsPaletteDelete);

		tblPalette.onRowDoubleClick(( _ )->{
			tblPalette.showDoubleClickDefaultAction = true;
		});

		tblPalette.onRowSelectionChanged((_, newRow) -> {
			itemPageId.set(0);
			Palette newRowData = (Palette) newRow;
			if (newRowData != null && newRowData.getId() != null) {
				rsvcPaletteItem.setUrl(urlPaletteItem + newRowData.getId().toString());
				rsvcPaletteItem.fetch(itemPageId.get(), new ParameterizedTypeReference<JsonResponse<PaletteItem>>() {});
				tblPaletteItem.setItems(rsvcPaletteItem.getDataAsObservableList());
				btnPaletteMarkForExit.setDisable(false);
				btnAllPalettesMarkForExit.setDisable(false);
			} else {
				btnPaletteMarkForExit.setDisable(true);
				btnAllPalettesMarkForExit.setDisable(true);
			}
		});
		
		tblPalette.onDataNeeded(() -> {
			rsvcPalette.fetch(palettePageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Palette>>() {});
			tblPalette.addItems(rsvcPalette.getDataAsObservableList());
		});
		
		btnPaletteExport.setOnAction( _ -> {
			//Common.displayForm(ControllerFactory.getController("Dobanovci_PaletteGroupAddEditController", tblPalette, 0), tblPalette, "%label.add.text");
			for (Palette palette : tblPaletteByDocumentId.tableView.getItems()) {
				fetchPalette(palette.getPaletteCode());
			}
		});
		
		// PALETTE OUT
		tblPaletteByDocumentId.setParentTable(tblPaletteDocument);
		tblPaletteByDocumentId.onRowSelectionChanged((_, newRow) -> {
			if (newRow == null) {
				btnPaletteExport.setDisable(true);
			} else {
				btnPaletteExport.setDisable(false);
			}
		});
		
		tblPaletteByDocumentId.onRowDeleted((row) -> {
			Palette palette = (Palette) row;
			rsvcPaletteFetchUpToDateObject.setUrl(urlPaletteFetchUpToDateObject + "/" + palette.getId());
			rsvcPaletteFetchUpToDateObject.fetch(new ParameterizedTypeReference<JsonResponse<Palette>>() {});
			Palette fetchedPalette = rsvcPaletteFetchUpToDateObject.getData().getFirst();
			rsvcUpdatePaletteDocumentId.setUrl(urlPaletteUpdateDocumentId + palette.getId() + "/" + fetchedPalette.getPreviousDocumentId());
			rsvcUpdatePaletteDocumentId.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});	
			if (tblPalette.findById(palette.getId()) == null) {
				tblPalette.addItem((Palette) row);
			}
		});
		
		// PALETTE ITEM
		tblPaletteItem.setParentTable(tblPalette);
		tblPaletteItem.setAddEditDialog(paletteItemAddEditControllerName);
		tblPaletteItem.setRestServiceDelete(rsPaletteItemDelete);
		
		tblPaletteItem.onDataNeeded(()->{
			rsvcPaletteItem.fetch(itemPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<PaletteItem>>() {});
			tblPaletteItem.addItems(rsvcPaletteItem.getDataAsObservableList());
		});
		
		tblPaletteItem.onRowDoubleClick(( _ )->{
			tblPaletteItem.showDoubleClickDefaultAction = true;
		});
		
		tblPaletteItem.onRowSelectionChanged((_, _)->{
			if (tblPalette.getSelectedItem()!= null && tblPalette.getSelectedItem().getPaletteStatus().getId() != 1) {
				tblPaletteItem.btnAdd.setDisable(true);
			}
		});

		//stores selected palette
		btnPaletteMarkForExit.setOnAction( (_) -> {
			if (tblPalette.getSelectedItem() != null && tblPaletteByDocumentId.findById(tblPalette.getSelectedItem().getId()) == null) {
				Palette palette = tblPalette.getSelectedItem();
				rsvcUpdatePaletteDocumentId.setUrl(urlPaletteUpdateDocumentId + palette.getId() + "/" + tblPaletteDocument.getSelectedItem().getId());
				rsvcUpdatePaletteDocumentId.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});
				palette.setPreviousDocumentId(tblPaletteDocument.getSelectedItem().getId());
				palette.setDocumentId(tblPaletteDocument.getSelectedItem().getId());
				tblPaletteByDocumentId.addItem(palette);
				tblPalette.removeItem(palette);
			}
		});
		
		//stores all palettes in the doc, which are in status 1
		btnAllPalettesMarkForExit.setOnAction( _ -> {
			List<Palette> lstPalettes = new ArrayList<>();
			lstPalettes.addAll(tblPalette.tableView.getItems());
			
			for (Palette palette : lstPalettes) {
				if (palette.getPaletteStatus().getId() == 3) {
					rsvcUpdatePaletteDocumentId.setUrl(urlPaletteUpdateDocumentId + palette.getId() + "/" + tblPaletteDocument.getSelectedItem().getId());
					rsvcUpdatePaletteDocumentId.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});
					palette.setPreviousDocumentId(tblPaletteDocument.getSelectedItem().getId());
					palette.setDocumentId(tblPaletteDocument.getSelectedItem().getId());
					tblPaletteByDocumentId.addItem(palette);
					tblPalette.removeItem(palette);
					//update status to prepared and document id to the new one !!!
				}
			}
			//Common.ShowNotification("INFO", "Zahtev za ulaz svih paleta označenog dokumenta je registrovan.\n" + "Palete je neophodno stavljati na traku ispravnim redosledom." , false);
		});
		
		
		// CRANE QUEUE
		tblCraneQueue.setParentTable(tblClient);
		rsvcCraneQueue.setParentTable(tblCraneQueue);
		
		tblCraneQueue.btnRefresh.setOnAction( (_) -> {
			refreshTblCraneQueue(tblClient.getSelectedItem().getId());
		});
		
		
		// PALETTE ITEM BY CLIENTID
		tblPaletteItemByClientId.setParentTable(tblClient);
		tblPaletteItemByClientId.setAddEditDialog(paletteItemAddEditControllerName);
		tblPaletteItemByClientId.setRestServiceDelete(rsPaletteItemDelete);
		
		tblPaletteItemByClientId.onDataNeeded(()->{
			rsvcPaletteItemByClientId.fetch(itemByClientIdPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<PaletteItem>>() {});
			tblPaletteItemByClientId.addItems(rsvcPaletteItemByClientId.getDataAsObservableList());
		});
		
		tblPaletteItemByClientId.onRowDoubleClick(( _ )->{
			tblPaletteItemByClientId.showDoubleClickDefaultAction = true;
		});
		
		tblPaletteItemByClientId.onRowSelectionChanged((_, _)->{
			if (tblClient.getSelectedItem()!= null && tblPaletteItemByClientId.getSelectedItem() != null && tblPaletteByItemId.findById(tblPaletteItemByClientId.getSelectedItem().getPaletteId()) != null && tblPaletteByItemId.findById(tblPaletteItemByClientId.getSelectedItem().getPaletteId()).getPaletteStatus().getId() != 1) {
				tblPaletteItemByClientId.btnAdd.setDisable(true);
			}
		});

		//stores selected palette
		btnPaletteMarkForExitConsist.setOnAction( (_) -> {
			if (tblPaletteByItemId.getSelectedItem() != null && tblPaletteByDocumentId.findById(tblPaletteByItemId.getSelectedItem().getId()) == null) {
				Palette palette = tblPaletteByItemId.getSelectedItem();
				rsvcUpdatePaletteDocumentId.setUrl(urlPaletteUpdateDocumentId + palette.getId() + "/" + tblPaletteDocument.getSelectedItem().getId());
				rsvcUpdatePaletteDocumentId.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});
				palette.setPreviousDocumentId(tblPaletteDocument.getSelectedItem().getId());
				palette.setDocumentId(tblPaletteDocument.getSelectedItem().getId());
				tblPaletteByDocumentId.addItem(palette);
				tblPaletteByItemId.removeItem(palette);
			}
		});
		
		//stores all palettes in the doc, which are in status 1
		btnAllPalettesMarkForExitConsist.setOnAction( _ -> {
			List<Palette> lstPalettes = new ArrayList<>();
			lstPalettes.addAll(tblPaletteByItemId.tableView.getItems());
			
			for (Palette palette : lstPalettes) {
				if (palette.getPaletteStatus().getId() == 3) {
					rsvcUpdatePaletteDocumentId.setUrl(urlPaletteUpdateDocumentId + palette.getId() + "/" + tblPaletteDocument.getSelectedItem().getId());
					rsvcUpdatePaletteDocumentId.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});
					palette.setPreviousDocumentId(tblPaletteDocument.getSelectedItem().getId());
					palette.setDocumentId(tblPaletteDocument.getSelectedItem().getId());
					tblPaletteByDocumentId.addItem(palette);
					tblPaletteByItemId.removeItem(palette);
					//update status to prepared and document id to the new one !!!
				}
			}
			//Common.ShowNotification("INFO", "Zahtev za ulaz svih paleta označenog dokumenta je registrovan.\n" + "Palete je neophodno stavljati na traku ispravnim redosledom." , false);
		});		
		
		
		// PALETTE BY ITEMID
		tblPaletteByItemId.setParentTable(tblPaletteItemByClientId);
		tblPaletteByItemId.setAddEditDialog(paletteAddEditControllerName);
		tblPaletteByItemId.setRestServiceDelete(rsPaletteDelete);

		tblPaletteByItemId.onRowDoubleClick(( _ )->{
			tblPaletteByItemId.showDoubleClickDefaultAction = true;
		});

		tblPaletteByItemId.onRowSelectionChanged((_, newRow) -> {
			Palette newRowData = (Palette) newRow;
			if (newRowData != null && newRowData.getId() != null) {
				btnPaletteMarkForExitConsist.setDisable(false);
				btnAllPalettesMarkForExitConsist.setDisable(false);
			} else {
				btnPaletteMarkForExitConsist.setDisable(true);
				btnAllPalettesMarkForExitConsist.setDisable(true);
			}
		});
		
		tblPaletteByItemId.onDataNeeded(() -> {
			rsvcPaletteByItemId.fetch(paletteByItemIdPageId.getAndIncrement(), new ParameterizedTypeReference<JsonResponse<Palette>>() {});
			tblPaletteByItemId.addItems(rsvcPaletteByItemId.getDataAsObservableList());
		});
		
	} // initialize END
	
	
	/**
	 * Stores palette with the given code
	 * calls SP in DB
	 * @param paletteCode
	 */
	private void fetchPalette(String paletteCode) {
		rsvcPaletteFetch.setUrl(urlPaletteFetch + paletteCode);
		rsvcPaletteFetch.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});
		Common.ShowNotification("INFO", "Zahtev za izlaz palete " + paletteCode + "\nje poslat na kran" , false);
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
		if (tgpDocumentStatus.getSelectedToggle() == (RadioButton) rbDocumentOutPreparing) {
			statusId = 1;
		} else if (tgpDocumentStatus.getSelectedToggle() == (RadioButton) rbDocumentOutStarted) {
			statusId = 4;
		} else if (tgpDocumentStatus.getSelectedToggle() == (RadioButton) rbDocumentProcessing) {
			statusId = 6;
		} else if (tgpDocumentStatus.getSelectedToggle() == (RadioButton) rbDocumentAll) {
			statusId = 0;
		}
		
		return statusId;
	}	
	

	
}
