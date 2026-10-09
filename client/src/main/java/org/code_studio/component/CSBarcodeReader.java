package org.code_studio.component;

import org.code_studio.component.CSTextField.VALIDATION_TYPE;

import javafx.geometry.Pos;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CSBarcodeReader {

	private CSTextField tfTextField;
	private boolean lockFocus;
	
	/**
	 * If true, binds global key event to the scene to which barcode field belongs
	 * Useful when there is barcode field that requires minimal user interaction
	 */
	private boolean bindGlobalKeys = true;
	
    /*
     * Used for mapping listener when barcode is entered an pressed <ENTER> on a textfield
     * */
	@FunctionalInterface
    public interface CSBarcodeEnteredListener {
    	public abstract void onBarcodeEntered(String strBarcode);
    }
	
	private CSBarcodeEnteredListener barcodeEnteredListener = null;
	
	public CSBarcodeReader(CSTextField tfTextField, boolean lockFocus) {
		this.tfTextField = tfTextField;
		this.lockFocus = lockFocus;
	}
	
	/**
	 * 
	 */
	public void register() {
		tfTextField.setValidationType(VALIDATION_TYPE.NUMBER_ONLY);
		tfTextField.setAlignment(Pos.CENTER);
		
		if (lockFocus) {
			tfTextField.onFocusChanged((_, newVal) -> {
				if (newVal == false) {
					tfTextField.requestFocus();
				}
			});
		}
		
		/*
		tfTextField.setOnKeyReleased(e -> {
			this.bindKeyEvents(e);
		});
		*/
		
		/*
		EventHandler<KeyEvent> keyReleasedEventHandler = event -> {
			this.bindKeyEvents(event);
		};
		
		tfTextField.addEventHandler(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
		*/
		
		if (bindGlobalKeys) {
			tfTextField.getParent().getScene().setOnKeyPressed(e -> {
				this.bindKeyEvents(e);
			});
		} else {
			tfTextField.setOnKeyPressed(e -> {
				this.bindKeyEvents(e);
			});
		}
	}
	
	//Preumoran sam sad da shvatim da ovo radi. Nije mi jasno kako OK radi. Shvatiti.
	private void bindKeyEvents(KeyEvent e) {
		if (e.getCode().isDigitKey() && !(e.getSource() instanceof CSTextField)) {
			//tfTextField.appendText(e.getText());
		} else if (e.getCode() == KeyCode.BACK_SPACE) {
			tfTextField.setText(null);
			return;
		} else if (e.getCode() == KeyCode.ENTER) {
			if (barcodeEnteredListener != null) {
				barcodeEnteredListener.onBarcodeEntered(tfTextField.getText());
			}
		}
		e.consume();
	}
	
    public final void onBarcodeEntered(CSBarcodeEnteredListener listener) {
    	barcodeEnteredListener = listener;
    }

	public boolean getBindGlobalKeys() {
		return bindGlobalKeys;
	}

	public void setBindGlobalKeys(boolean bindGlobalKeys) {
		this.bindGlobalKeys = bindGlobalKeys;
	}

}
