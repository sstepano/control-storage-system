package org.code_studio.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.code_studio.component.CSComboBox;
import org.code_studio.component.CSPrintButton;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.InputValidation.CSEmptyFieldValidator;
import org.code_studio.component.ui.CSDialogButtons;

import org.code_studio.database.Client;
import org.code_studio.database.Crane;
import org.code_studio.database.Palette;
import org.code_studio.database.PaletteDocument;
import org.code_studio.database.PaletteStatus;
import org.code_studio.database.StoredProcedureResult;
import org.code_studio.database.Warehouse;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;

import org.springframework.core.ParameterizedTypeReference;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Dobanovci_PaletteAddEditController extends BaseController {

	@FXML CSDialogButtons dialogButtons;

	@FXML TextField tfClientId;
	@FXML TextField tfClientName;
	@FXML TextField tfPaletteId;
	@FXML TextField tfPaletteName;
	@FXML TextField tfPaletteCode;
	@FXML CSTextField tfBarcode;
	@FXML TextField tfClientPaletteId;
	//@FXML CSTextField tfPaletteWeight;
	@FXML CSComboBox<Crane> cbCrane;
	@FXML CSComboBox<Warehouse> cbWarehouse;
	@FXML CSTextField tfWidth;
	@FXML CSTextField tfHeight;
	@FXML CSTextField tfLength;
	@FXML CSTextField tfRowId;
	@FXML CSTextField tfShelfId;
	@FXML CSTextField tfVerticalId;
	@FXML CSPrintButton btnBarcodePrint;
	
	private int mode;
	private Client client;
	private CSTable<Palette> tblPalette;
	private Palette palette;
	private PaletteDocument paletteDocument;
	private CSRestService<Palette> rsPalette;
	private CSRestService<Warehouse> rsWarehouse;
	private final String urlPalette = "/palette";
	private final String urlWarehouse = "/warehouse";
	private CSRestService<Crane> rsCrane;
	private CSRestService<String> rsPaletteFreeSpacesByCrane;
	private final String urlPaletteFreeSpacesByCrane = "/palette/unoccupiedPalettePositionsByCrane/";
	
	private CSRestService<StoredProcedureResult> rsNextFreePaletteStorageLocation;
	private final String urlNextFreePaletteStorageLocation = "/palette/nextFreePaletteLocation/0/0"; // TODO: zakuc za kran i algoritam

	
	@SuppressWarnings("unchecked")
	public Dobanovci_PaletteAddEditController(Object controllerParam, int mode) {
		this.mode = mode;
		this.tblPalette = (CSTable<Palette>) controllerParam;
		this.palette = this.tblPalette.getSelectedItem();
		this.paletteDocument = (PaletteDocument) this.tblPalette.getParentTable().getSelectedItem();
		this.client = (Client) this.paletteDocument.getClient();
	}


	public void initialize() {
		try {
			
			rsPalette = new CSRestService<>(urlPalette);
			tfClientId.setText(this.client.getId().toString());
			tfClientName.setText(this.client.getName());
	
			rsWarehouse = new CSRestService<>(urlWarehouse);
			rsWarehouse.fetch(new ParameterizedTypeReference<JsonResponse<Warehouse>>() {});
			cbWarehouse.getItems().addAll(rsWarehouse.getDataAsObservableList());
			cbWarehouse.getSelectionModel().select(0);
			
			rsCrane = new CSRestService<>("/crane/findAllActive");
			rsCrane.fetch(new ParameterizedTypeReference<JsonResponse<Crane>>() {});
			cbCrane.getItems().addAll(rsCrane.getDataAsObservableList());
			cbCrane.getSelectionModel().select(
					paletteDocument.getCraneId() == null
					? 0 
					: paletteDocument.getCraneId().intValue());
			
			rsNextFreePaletteStorageLocation = new CSRestService<>(urlNextFreePaletteStorageLocation);

			/**
			rsPaletteFreeSpacesByCrane = new CSRestService<>(urlPaletteFreeSpacesByCrane);
			cbCrane.onSelectionChanged(newValue -> {
				Crane selectedCrane = (Crane) newValue;
				rsPaletteFreeSpacesByCrane.setUrl(urlPaletteFreeSpacesByCrane + selectedCrane.getId().toString());
				rsPaletteFreeSpacesByCrane.fetch(new ParameterizedTypeReference<JsonResponse<String>>() {});
				List<String> unoccupiedPaletteSpaces = rsPaletteFreeSpacesByCrane.getData();
				if (unoccupiedPaletteSpaces.size() > 0) {
					tfRowId.setText(unoccupiedPaletteSpaces.get(0).substring(0, 2));
					tfShelfId.setText(unoccupiedPaletteSpaces.get(0).substring(2, 5));
					tfVerticalId.setText(unoccupiedPaletteSpaces.get(0).substring(5, 7));
	
					tfPaletteCode.setText(tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
					tfPaletteName.setText("Paleta" + tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
					tfBarcode.setText(client.getId().toString() + tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
					
					setCoords();
				} else {
					Common.ShowNotification("Dodavanje nove palete", "Za zadati kran ne postoje slobodna mesta!", true);
				}
			});
			**/
			
			
			if (mode == 0) {
				this.palette = new Palette();
				this.palette.setDocumentId(this.paletteDocument.getId());
				rsNextFreePaletteStorageLocation.fetch(new ParameterizedTypeReference<JsonResponse<StoredProcedureResult>>(){}, () -> {
					String res = rsNextFreePaletteStorageLocation.getData().getFirst().getReturnValue();
					tfRowId.setText(res.substring(0, 2));
					tfShelfId.setText(res.substring(2, 5));
					tfVerticalId.setText(res.substring(5, 7));
					tfRowId.selectAll();
				});
				
				/**
				Crane selectedCrane = cbCrane.getValue();
				rsPaletteFreeSpacesByCrane.setUrl(urlPaletteFreeSpacesByCrane + selectedCrane.getId().toString());
				rsPaletteFreeSpacesByCrane.fetch(new ParameterizedTypeReference<JsonResponse<String>>() {});
				List<String> unoccupiedPaletteSpaces = rsPaletteFreeSpacesByCrane.getData();
				if (unoccupiedPaletteSpaces.size() > 0) {
					tfRowId.setText(unoccupiedPaletteSpaces.get(0).substring(0, 2));
					tfShelfId.setText(unoccupiedPaletteSpaces.get(0).substring(2, 5));
					tfVerticalId.setText(unoccupiedPaletteSpaces.get(0).substring(5, 7));
					tfPaletteCode.setText(tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
					tfPaletteName.setText("Paleta" + tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
					tfBarcode.setText(client.getId().toString() + tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
				} else {
					Common.ShowNotification("Dodavanje nove palete", "Za zadati kran ne postoje slobodna mesta!", true);
				}
				**/
			} else {
				tfPaletteId.setText(this.palette.getId().toString());
				tfPaletteName.setText(this.palette.getName());
				tfPaletteCode.setText(this.palette.getPaletteCode());
				tfBarcode.setText(this.palette.getBarcode());
				tfClientPaletteId.setText(this.palette.getClientPaletteCode());
				/**tfPaletteWeight.setText(this.palette.getWeight().toString());**/
				tfWidth.setText(this.palette.getWidth().toString());
				tfHeight.setText(this.palette.getHeight().toString());
				tfLength.setText(this.palette.getLength().toString());
				cbCrane.getSelectionModel().select(
						palette.getCraneId() == null
						? 0
						: palette.getCraneId());
				tfRowId.setText(palette.getRowId());
				tfShelfId.setText(palette.getShelfId());
				tfVerticalId.setText(palette.getVerticalId());
				
				if (palette.getPaletteStatus().getId() != 1) {
					cbCrane.setDisable(true);
				}
			}
	
			dialogButtons.getSaveButton().setOnAction( e-> {
				setCoords();
				this.palette.setName(tfPaletteName.getText());
				this.palette.setPaletteCode(tfPaletteCode.getText());
				this.palette.setBarcode(tfBarcode.getText());
				this.palette.setClientPaletteCode(tfClientPaletteId.getText());
				//weightGrams
				/**
				try {
					this.palette.setWeight(new BigDecimal(tfPaletteWeight.getText()));
				} catch (Exception ex) {
					this.palette.setWeight(new BigDecimal(0));
				}
				**/
				this.palette.setLength(Integer.parseInt(tfLength.getText()));
				this.palette.setWidth(Integer.parseInt(tfWidth.getText()));
				this.palette.setHeight(Integer.parseInt(tfHeight.getText()));
				this.palette.setRowId(tfRowId.getText());
				this.palette.setShelfId(tfShelfId.getText());
				this.palette.setVerticalId(tfVerticalId.getText());
				PaletteStatus paletteStatus = new PaletteStatus();
				paletteStatus.setId(1); // Priprema
				paletteStatus.setName("Ulaz");
				this.palette.setPaletteStatus(paletteStatus);
				this.palette.setCraneId(
					cbCrane.getSelectionModel().getSelectedItem().getId() == null 
					? 0
					: cbCrane.getSelectionModel().getSelectedItem().getId().intValue()
				);
				
				this.palette.setPaletteDocument(this.paletteDocument);
		
				Palette insertedPalette = rsPalette.addOrUpdate(palette);
				if (mode == 0) {
					this.tblPalette.addItem(insertedPalette);
				}
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
	
			});
			
			tfRowId.onFocusChanged( (_, _) -> {
				setCoords();
			});
			
			tfShelfId.onFocusChanged( (_, _) -> {
				setCoords();
			});
			
			tfVerticalId.onFocusChanged( (_, _) -> {
				setCoords();
			});
			
			dialogButtons.setValidation(
					  new CSEmptyFieldValidator(tfRowId),
					  new CSEmptyFieldValidator(tfShelfId),
					  new CSEmptyFieldValidator(tfVerticalId)
			);

		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške pri pozivanju forme.\n" + ex.getLocalizedMessage(), true);
		}
	} // init end
	
	
	/**
	 * 
	 */
	void setCoords() {
		/**
		List<String> unoccupiedPaletteSpaces = rsPaletteFreeSpacesByCrane.getData();
		String strRowId = "01";
		String strShelfId = "001";
		String strVerticalId = "01";
		if (unoccupiedPaletteSpaces != null && unoccupiedPaletteSpaces.size() > 0) {
			strRowId = unoccupiedPaletteSpaces.get(0).substring(0, 2);
			strShelfId = unoccupiedPaletteSpaces.get(0).substring(2, 5);
			strVerticalId = unoccupiedPaletteSpaces.get(0).substring(5, 7);
			
			tfRowId.setText(strRowId);
			tfShelfId.setText(strShelfId);
			tfVerticalId.setText(strVerticalId);
		} else {
			//TODO: RE-fix ... Common.ShowNotification("Dodavanje nove palete", "Za zadati kran ne postoje slobodna mesta!", true);
			
		}
		**/
		
		tfRowId.setText(StringUtils.leftPad(tfRowId.getText(), 2, "0"));
		tfShelfId.setText(StringUtils.leftPad(tfShelfId.getText(), 3, "0"));
		tfVerticalId.setText(StringUtils.leftPad(tfVerticalId.getText(), 2, "0"));

		tfPaletteCode.setText(tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
		tfBarcode.setText(client.getId().toString() + tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
		
		//if (tfPaletteName.getText().isBlank()) {
			tfPaletteName.setText("Paleta" + tfRowId.getText() + tfShelfId.getText() + tfVerticalId.getText());
		//}
		
		btnBarcodePrint.clearReportConfigParams();
		btnBarcodePrint.addReportConfigParam(tfBarcode.getText());

	}

}
