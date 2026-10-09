package org.code_studio;

import org.apache.commons.lang3.StringUtils;
import org.code_studio.controller.ApiServerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listens to data received from barcode reader
 */
@Component
public class BarcodeDataReceivedListener {
	
	@Autowired
	ApiServerController apiServerController;
	
    /** Create new DOCUMENT, and define new PALETTE with client and coordinates
     * which are received from barcode reader, in form xxxxx.xxyyyzz
     * where first 5 chars is client ID, such as 50008, and the rest is crane coordinates 
     * Then, send input message so newly created palette gets imported to the warehouse
     */
    @EventListener(BarcodeDataReceivedEvent.class)
    public void onEvent(BarcodeDataReceivedEvent event) {
    	String dataReceived = event.getData();
    	Integer clientId;
    	String craneCoords;
    	
    	try {
	    	clientId = Integer.parseInt(StringUtils.substring(dataReceived, 0, dataReceived.length() - 8));
	    	craneCoords = StringUtils.right(dataReceived, 8);
	    	apiServerController.barcodeEntryEvent(clientId, craneCoords);
	        Common.log(getClass(), "<tcpsvr><info> " + "BarcodeDataReceived detected: " + event.getData() + "; Document and palette created.");
    	} catch (Exception ex) {
    		Common.log(getClass(), "<tcpsvr><err> " + ex, "ERROR");
		}
                
    }

}
