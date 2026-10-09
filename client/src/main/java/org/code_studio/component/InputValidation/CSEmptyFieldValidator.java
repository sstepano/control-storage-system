package org.code_studio.component.InputValidation;

import javafx.scene.control.TextInputControl;

public class CSEmptyFieldValidator extends CSInputValidator {
	private static String errorMessage = "Polje ne sme biti prazno";

	public CSEmptyFieldValidator(TextInputControl control) {
		super(control, () -> !checkCondition(control), errorMessage);

	}
	
	/**
	 * Checks condition, if it is true, then validation is failed, else validation passed
	 * @param control
	 * @return
	 */
	private static Boolean checkCondition (TextInputControl control) {
		if (control.getText() != null && control.getText().length() == 0) {
			return false;
		} else {
			return true;
		}
	}

}
