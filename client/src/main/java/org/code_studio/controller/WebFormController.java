package org.code_studio.controller;

import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;

import com.sun.javafx.webkit.WebConsoleListener;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class WebFormController extends BaseController {

	@FXML private WebView webView;
	private String craneWebServerUrl;
	private String pageUrl;


	public WebFormController(Object controllerParam, int mode, ApplicationContext ctx) {
		pageUrl = (String) controllerParam;
		craneWebServerUrl = Common.applicationProperties.getProperty("crane.webServerUrl");
	}
	
	@SuppressWarnings("unused")
	public void initialize() {
		try {
				
			WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
			    System.out.println(message + "[at " + lineNumber + "]" + ", sourceId: " + sourceId);
			});					
				webView.getEngine().load(craneWebServerUrl + "/" + pageUrl);
				//System.out.println(craneWebServerUrl + "/" + pageUrl);
			
			} catch (Exception ex) {
				Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
				Common.logMessage(getClass(), ex, "ERROR");
			}

	} // initialize END
	
}
