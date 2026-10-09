package org.code_studio.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.code_studio.component.CSPhotoView;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.Client;
import org.code_studio.database.ClientStoreImage;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Sales_ClientStoreImageAddEditController extends BaseController implements Initializable {

	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	
	@FXML private Button btnSearch;
	@FXML private CSTextField tfImagePath;
	@FXML private CSPhotoView pvImage;

	private int mode;
	private CSTable<Client> tblClient;
	private Client client;
	private CSTable<ClientStoreImage> tblClientStoreImage;
	private ClientStoreImage clientStoreImage;
	private FileChooser fileChooser;
	private File file;
	private String baseImagePath;
	private String baseClientImagePath;
	
	private final String urlClientStoreImage = "/clientStoreImage";
	private CSRestService<ClientStoreImage> rsClientStoreImage;
	
	@SuppressWarnings("unchecked")
	public Sales_ClientStoreImageAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		baseImagePath = Common.applicationProperties.getProperty("client.clientStoreImagePath");
		List<Object> lstControllerParam = (List<Object>) controllerParam;
		tblClient = (CSTable<Client>) lstControllerParam.get(0);
		client = tblClient.getSelectedItem();
		tblClientStoreImage = (CSTable<ClientStoreImage>) lstControllerParam.get(1);
		clientStoreImage = tblClientStoreImage.getSelectedItem();
		fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(baseImagePath));
		baseClientImagePath = client.getId() + "-" + client.getName() + "/";
	}
	
	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		try {
			rsClientStoreImage = new CSRestService<>(urlClientStoreImage);
			pvImage.setMaxAspectRatio();
			if (mode == 0) {
				clientStoreImage = new ClientStoreImage();
				clientStoreImage.setClientId(client.getId().intValue());
			} else {
				tfImagePath.setText(baseImagePath + clientStoreImage.getImagePath());
				pvImage.setImagePath(baseImagePath + clientStoreImage.getImagePath());
			}
	
			btnSearch.setOnAction( e-> {
				fileChooser.setTitle("Izaberite sliku u JPG formatu ...");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Slike u JPG formatu", "*.jpg"));
				file = fileChooser.showOpenDialog(btnSearch.getScene().getWindow());

				if (file != null) {
					tfImagePath.setText(file.getAbsolutePath());
					pvImage.setImagePath(file.getAbsolutePath());
					pvImage.setMaxAspectRatio();
				}
			});

			btnSave.setOnAction(e->{
				if (mode == 0) {
					clientStoreImage.setImagePath((baseClientImagePath + file.getName()).replace("\\", "/"));
				} else {
					//TODO: OVde treba save nove putanje!!!
					clientStoreImage.setImagePath(clientStoreImage.getImagePath().replace("\\", "/"));
				}
				ClientStoreImage insertedItem = rsClientStoreImage.addOrUpdate(clientStoreImage);
				//TODO: call save image to the server
				
				if (mode == 0) {
					tblClientStoreImage.tableView.getItems().add(insertedItem);
				}
				
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
			btnCancel.setOnAction(e->{
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
			btnSave.disableProperty().bind(Bindings.createBooleanBinding(
				() -> !validateInput(),
				tfImagePath.textProperty()
			));
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
	private Boolean validateInput() {
		if (tfImagePath.getText().isBlank()) return false;
		return true;
	}

}
