package org.code_studio;

import org.code_studio.messageflow.LVSDayEndRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCloseListener {
	
	@Value("${applicationcloseevent.waitMSec}")
	private int waitMsec;

	@Autowired(required=false) // will not get the instance if barcode is not enabled and will not blow up. Neet :)
	BarcodeConfig barcodeConfig;
	
    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent(ContextClosedEvent contextClosedEvent) throws InterruptedException {
        Common.log(getClass(), "<tcpserver><info> " + "ApplicationContextClosedEvent detected");
        
        /**
         * Sends DAYCLOSE only if we are successfully connected to crane with proper DAYSTART
         */
        if (Common.getConnectionDayStartEstablished()) {
        	new LVSDayEndRequest().sendWithClient();
        }
        
        //Closes COM port which was used for barcode
        if (barcodeConfig != null) {
        	barcodeConfig.closeComPort();
        }
        
        Common.log(getClass(), "<tcpserver><info> " + "Waiting for " + waitMsec + " msec to receive DAYEND confirmation from CRANE, and then close the TCP connection.");
        Thread.sleep(waitMsec); // wait for 3 sec for crane to respond to day close, and then close the tcp connection
    }

}
