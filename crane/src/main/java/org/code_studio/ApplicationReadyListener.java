package org.code_studio;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyListener {

	@Value("${server.address}")
	String webserverAddress;
	
	@Value("${server.port}")
	Integer webserverPort;
	
	@Value("${applicationreadyevent.startbrowser}")
	boolean applicationReadyEventStartBrowser;

	
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent(ApplicationReadyEvent applicationReadyEvent) {
    	
    	boolean messageflowAutomationDebug = Boolean.valueOf(Common.applicationProperties.getProperty("messageflow.automation.debug"));
    	if (messageflowAutomationDebug) {
    		Common.log(getClass(), "<tcpsvr><info> " + "Message Flow Automation Debug is ENABLED", "WARN");
    	}
        
        // start app URL in browser if configured
        if (applicationReadyEventStartBrowser) {
    		try {
    			String url = "http://" + webserverAddress + ":" + webserverPort.toString();
    			
    			Runtime rt = Runtime.getRuntime();
    			rt.exec(new String[] { "rundll32", "url.dll,FileProtocolHandler", url });
    		} catch (IOException ex) {
    			Common.log(getClass(), ex);
    		}
        }
    }

}
