package org.code_studio;

/**
 * Custom event which will be fired when barcode reader which is connected
 * to COM port, sends the data to our crane
 */
public class BarcodeDataReceivedEvent {
	
	private String data;

	public BarcodeDataReceivedEvent(Object source, String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}


}
