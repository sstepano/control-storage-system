package org.code_studio.component;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

import org.code_studio.main.Common;
import org.code_studio.main.StageInitializer.ApplicationContextProvider;
import org.code_studio.component.ui.CSDialogButtons;

public class ConfirmationDialog extends AnchorPane {
	@FXML private Label topLabel;
	@FXML public CSDialogButtons dialogButtons;

	private BaseStage dialog;
	public ButtonType result;

	// DEBUG ... zakomentarisati !!!
	private static final int maxWidth = 1366; //zakuc jer ne mogu ovde da koristim appProperties, nije jos inicijalizovano

	public ConfirmationDialog(Parent parent) {
		init(parent);
	    dialog.showAndWait();
	}
	
	/***
	 * Used in Common->applicationExit only
	 * @param parent
	 * @param title
	 * @param message
	 * @param okButtonText
	 * @param cancelButtonText
	 */
	public ConfirmationDialog(Parent parent, String title, String message, String okButtonText, String cancelButtonText) {
		init(parent);
		dialog.setTitle(title);
		topLabel.setText(message);
		dialogButtons.getSaveButton().setText(okButtonText);
		dialogButtons.getCancelButton().setText(cancelButtonText);
		dialog.showAndWait();
	}


	public void initialize() {
		dialogButtons.getSaveButton().setText("Obriši");
		
		dialogButtons.getSaveButton().setOnAction( _ -> {
			result = ButtonType.OK;
			dialog.close();
		});

		dialogButtons.getCancelButton().setOnAction( _ -> {
			result = ButtonType.CANCEL;
			dialog.close();
		});
	}
	
	
	private void init(Parent parent) {
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		ResourceBundle bundle = ctx.getBean(ResourceBundleBean.class).getBundle();

		dialog = new BaseStage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle(bundle.getString("label.delete-selected.text"));
		dialog.setResizable(false);

	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfirmationDialog.fxml"), bundle);
	    fxmlLoader.setRoot(this);
	    fxmlLoader.setController(this);

	    try {
	    	Parent root = fxmlLoader.load();
	        dialog.setScene(new Scene(root));

			if (Common.clientScreenWidth <= maxWidth) {
				//System.out.println("small screen detected");
				root.getStyleClass().add("root-small-screen");
			}

	    } catch (IOException exception) {
	        throw new RuntimeException(exception);
	    }

		dialog.setOnShown( _ -> {
			Common.centerOnParent(parent, dialog);
		});
	}

}
