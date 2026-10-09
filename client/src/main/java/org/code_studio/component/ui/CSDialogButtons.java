package org.code_studio.component.ui;

import java.io.IOException;
import org.code_studio.component.InputValidation.CSInputValidator;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CSDialogButtons extends HBox {
	
	@FXML private Button btnSave;
	@FXML private Button btnCancel;

	public CSDialogButtons() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSDialogButtons.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

	}

	public void initialize() {
		
		// default action is to close form. User can override it
		btnCancel.setOnAction( e-> {
			((Stage) ((Button) e.getSource()).getScene().getWindow()).close();	
		});
	}
	
	/**
	 * Returns SAVE button instance from the control
	 * @return
	 */
	public Button getSaveButton() {
		return btnSave;
	}
	
	/**
	 * Returns CANCEL button instance from the control
	 * @return
	 */
	public Button getCancelButton() {
		return btnCancel;
	}
	
	/**
	 * Sets validator to ALL the controls passed to this method
	 * We can have multiple. For each one we set DISABLE binding,
	 * so, whenever any of the controls does not meet condition,
	 * Save button will be disabled and user will not be able to save data
	 * @param validatorList
	 */
	public void setValidation(CSInputValidator... validatorList) {
		ObservableList<BooleanProperty> lst = FXCollections.observableArrayList();
		for (CSInputValidator csValidator : validatorList) {
			lst.add(csValidator.bpInputValidationProperty);
		}

		btnSave.disableProperty().bind(conjunction(lst));
	}
	
	/**
	 * Due to negate logic, we must use OR, istead of AND
	 * @param list
	 * @return
	 */
	private static BooleanBinding conjunction(ObservableList<BooleanProperty> list){   
		BooleanBinding or = new SimpleBooleanProperty(true).and(list.get(0));
		for(int i = 1; i < list.size(); i++){
			or = or.or(list.get(i));
		} 
		return or;
	}
	
	/**
	 * Used when we want to show this control, but just CANCEL button.
	 * @param visible
	 */
	public boolean getSaveButtonVisible() {
		return btnSave.isVisible();
	}
	
	public void setSaveButtonVisible(boolean visible) {
		btnSave.setVisible(visible);
	}
	
	/**
	 * Used when we want to show this control, but just SAVE button.
	 * @param visible
	 */
	
	public boolean getCancelButtonVisible() {
		return btnCancel.isVisible();
	}
	
	public void setCancelButtonVisible(boolean visible) {
		btnCancel.setVisible(visible);
	}

	/**
	 * Sets button display text
	 * @param text
	 */
	public String getSaveButtonText() {
		return btnSave.getText();
	}

	public void setSaveButtonText(String text) {
		btnSave.setText(text);
	}

	/**
	 * Sets button display text
	 * @param text
	 */
	public String getCancelButtonText() {
		return btnCancel.getText();
	}

	public void setCancelButtonText(String text) {
		btnCancel.setText(text);
	}
	
	/**
	 * Closes the form
	 */
	public void closeForm() {
		((Stage) ((Button) btnSave).getScene().getWindow()).close();
	}
	
}
