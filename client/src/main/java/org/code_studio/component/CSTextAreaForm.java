package org.code_studio.component;

import java.util.List;

import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.controller.BaseController;
import org.code_studio.database.Client;
import org.code_studio.database.ClientContact;
import org.code_studio.rest.CSRestService;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class CSTextAreaForm<T> extends BaseController {

	@FXML private CSDialogButtons dialogButtons;
	@FXML private Label lblNote;
	@FXML private TextArea taDescription;

	private T entity;
	private CSRestService<T> restSvc;
	private Client client = null;
	private ClientContact clientContact = null;
	
	@SuppressWarnings("unused")
	private ApplicationContext ctx;
	@SuppressWarnings("unused")
	private int mode;
	
	private String labelText;
	private String descriptionText;

	public CSTextAreaForm(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
		this.mode = mode;
		@SuppressWarnings("unchecked")
		List<Object> lstControllerParam = (List<Object>) controllerParam;
		labelText = (String) lstControllerParam.get(0);
		descriptionText = (String) lstControllerParam.get(1);
	}

	public CSTextAreaForm (T entity) {
		if (entity instanceof Client) {
			client = (Client) entity;
			restSvc = new CSRestService<>("/client");
		} else if (entity instanceof ClientContact) {
			clientContact = (ClientContact) entity;
			restSvc = new CSRestService<>("/clientContact");
		}
	}


	@SuppressWarnings("unchecked")
	public void initialize() {
		if (client != null) {
			taDescription.setText(client.getDescription());
			lblNote.setText(client.getName());
		} else if (clientContact != null) {
			taDescription.setText(clientContact.getDescription());
			lblNote.setText(clientContact.getName());
		} else {
			lblNote.setText(labelText);
			taDescription.setText(descriptionText);
			taDescription.setEditable(false);
			dialogButtons.getSaveButton().setVisible(false);
		}

		dialogButtons.getSaveButton().setOnAction(e->{
			if (client != null) {
				this.client.setDescription(taDescription.getText());
				entity = (T) this.client;
			} else if (clientContact != null) {
				this.clientContact.setDescription(taDescription.getText());
				entity = (T) this.clientContact;
			}

			if (entity != null) {
				this.restSvc.addOrUpdate(this.entity);
			}
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
		});

	}

}
