package org.code_studio.component.InputValidation;

import javafx.scene.control.TextInputControl;

public class CSZeroValueValidator extends CSInputValidator {
	private static String errorMessage = "Polje mora sadržati broj koji je veći od nule";

	public CSZeroValueValidator(TextInputControl control) {
		super(control, () -> !checkCondition(control), errorMessage);

	}
	
	/**
	 * Checks condition, if it is true, then validation is failed, else validation passed
	 * @param control
	 * @return
	 */
	private static Boolean checkCondition (TextInputControl control) {
		if (
			  control.getText().length() == 0 || 
			  control.getText() == null || 
			  control.getText().equalsIgnoreCase("0") ||
			  control.getText().matches("^0[0-9]+") ||
			  control.getText().equalsIgnoreCase("0.00")) {
			return false;
		} else {
			return true;
		}
	}

}
