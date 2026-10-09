package org.code_studio.component.InputValidation;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;

public class CSInputValidator {	
	public TextInputControl control;
	public String errorMessage;
	public CSValidatorBooleanCallBack booleanCallback;
	public BooleanBinding booleanBinding;
	
	public BooleanProperty bpInputValidationProperty;
	
	@FunctionalInterface
    public interface CSValidatorBooleanCallBack {
    	public abstract Boolean booleanCallback();
    }
	
	public CSInputValidator(TextInputControl control, 
			CSValidatorBooleanCallBack booleanCallback
			, String errorMessage) {
		
		this.control = control;
		this.errorMessage = errorMessage;
		this.booleanCallback = booleanCallback;
		this.bpInputValidationProperty = new SimpleBooleanProperty();
		this.addValidationChangeListener();
	}

	/**
	 * Adds validation listener on class creation, which registers text propery change listener
	 * When change occurs, we change property value, which further notifies clients that change occured
	 */
	private void addValidationChangeListener() {
		bpInputValidationProperty.setValue(!checkCondition()); // inicijalni check
		
		this.control.textProperty().addListener((observable, oldVal, newVal) -> {
			bpInputValidationProperty.setValue(!checkCondition());
		});
	}
	
	
	/** 
	 * Call booleanCallback, which is passed to us,
	 * then, if passed condition IS NOT MET,
	 * it applies style class for ERROR
	 * As soon as passed condition is met, it removes ERROR style class from the control
	 * @return
	 */
	private Boolean checkCondition() {
		Tooltip initialTooltip = control.getTooltip();
		if (booleanCallback.booleanCallback()) { // ako je uslov ispunjen, znaci da je validation failed
			// need to check because we do not want to add it multiple times, then we cannot remove it
			if (!control.getStyleClass().contains("validation-error")) {
				control.getStyleClass().add("validation-error");
				Tooltip tp = new Tooltip(errorMessage);
				control.setTooltip(tp);
			}
			return false; // vracamo validation failed
		} else {
			control.getStyleClass().remove("validation-error");
			control.setTooltip(initialTooltip);
			return true;
		}
	}

}
