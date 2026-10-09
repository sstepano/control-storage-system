package org.code_studio.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.Client;
import org.code_studio.database.Crane;
import org.code_studio.database.PaletteDocument;
import org.code_studio.database.PaletteDocumentStatus;
import org.code_studio.database.PaletteDocumentType;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Dobanovci_PaletteDocumentAddEditController extends BaseController implements Initializable {

	@FXML Button btnSave;
	@FXML Button btnCancel;

	@FXML TextField tfClientId;
	@FXML TextField tfClientName;
	@FXML CSTextField tfPaletteDocumentId;
	@FXML CSDatePicker dtPaletteDocumentDate;
	@FXML CSComboBox<PaletteDocumentType> cbPaletteDocumentType;
	@FXML CSComboBox<Crane> cbCrane;
	
	private int mode;
	private int paletteDocumentMode; // 0 - ULAZ, 1 - IZLAZ
	private Client client;
	private CSTable<PaletteDocument> tblPaletteDocument;
	private PaletteDocument paletteDocument;
	private final String urlPaletteDocument = "/paletteDocument";
	CSRestService <PaletteDocument> rsPaletteDocument;
	CSRestService<Client> rsClient;
	CSRestService<Crane> rsCrane;
	
	@SuppressWarnings("unchecked")
	public Dobanovci_PaletteDocumentAddEditController(Object controllerParam, int mode) {
		List<Object> lstControllerParam = (List<Object>) controllerParam;
		this.tblPaletteDocument = (CSTable<PaletteDocument>) lstControllerParam.get(0);
		this.paletteDocumentMode = (int) lstControllerParam.get(1);
		this.mode = mode;
		this.client = (Client) this.tblPaletteDocument.getParentTable().getSelectedItem();
		this.paletteDocument = tblPaletteDocument.getSelectedItem();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rsClient = new CSRestService<>("/client");
		rsPaletteDocument = new CSRestService<>(urlPaletteDocument);

		tfClientId.setText(this.client.getId().toString());
		tfClientName.setText(this.client.getName().toString());
		
		rsCrane = new CSRestService<>("/crane/findAllActive");
		rsCrane.fetch(new ParameterizedTypeReference<JsonResponse<Crane>>() {});
		cbCrane.getItems().addAll(rsCrane.getDataAsObservableList());
		cbCrane.getSelectionModel().selectFirst(); // selektujemo prvi po defaultu
		
		if (paletteDocumentMode == 0) { // ULAZ
			cbPaletteDocumentType.getSelectionModel().select(0);
			cbCrane.getSelectionModel().select(0); // oba krana po defaultu
		} else { // IZLAZ
			cbPaletteDocumentType.getSelectionModel().select(1);
			cbCrane.setDisable(true);
		}
		
		if (mode == 1) { //Edit mode
			tfPaletteDocumentId.setText(paletteDocument.getId().toString());
			dtPaletteDocumentDate.setValue(paletteDocument.getCreateDate());
			////cbPaletteDocumentType.setValue(paletteDocument.getType());
			cbCrane.getSelectionModel().select(
					paletteDocument.getCraneId() == null
					? 0 
					: paletteDocument.getCraneId().intValue());
		}

		
		btnSave.setOnAction( e-> {
			if (mode == 0) {//add
				this.paletteDocument = new PaletteDocument();
				this.paletteDocument.setClient(this.client);
				PaletteDocumentStatus paletteDocumentStatus = new PaletteDocumentStatus("Priprema");				
				paletteDocumentStatus.setId(1);
				PaletteDocumentType paletteDocumentType;
				
				if (paletteDocumentMode == 0) {
					paletteDocumentType = new PaletteDocumentType("Ulaz");
					paletteDocumentType.setId(1);
				} else { // IZLAZ
					paletteDocumentType = new PaletteDocumentType("Izlaz");
					paletteDocumentType.setId(2);
				}
				
				this.paletteDocument.setPaletteDocumentStatus(paletteDocumentStatus);
				this.paletteDocument.setStatusId(paletteDocumentStatus.getId());
				this.paletteDocument.setPaletteDocumentType(paletteDocumentType);
				this.paletteDocument.setTypeId(paletteDocumentType.getId());
			}
			
			paletteDocument.setCreateDate(dtPaletteDocumentDate.getValue());
			paletteDocument.setCraneId(cbCrane.getSelectionModel().getSelectedItem().getId());
			PaletteDocument insertedPaletteDocument = rsPaletteDocument.addOrUpdate(paletteDocument);
			if (mode == 0) {
				this.tblPaletteDocument.addItem(insertedPaletteDocument);
			}
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();

		});

		btnCancel.setOnAction(e->{
			((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
		});

	} // init end
	
}
