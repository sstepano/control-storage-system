package org.code_studio.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.main.Common;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ApplicationLogController extends BaseController implements Initializable {

	@FXML private CSDialogButtons dialogButtons;
	@FXML private Label lblHeader;
	@FXML private TextArea taLogContent;
	
	public ApplicationLogController(Object controllerParam, int mode, ApplicationContext ctx) {}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		
		try {
			int linesToRead = Integer.parseInt(Common.applicationProperties.getProperty("applicationlog.numberoflinestoread", "100"));
			taLogContent.setText(getClientLogFromFile("logs/client.log", linesToRead));
			taLogContent.end();
		} catch (IOException ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod čitanja log fajla.", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
	
	
	private String getClientLogFromFile (String filePath, int lastLinesToRead) throws IOException {
	    try (ReversedLinesFileReader rlfReader = ReversedLinesFileReader.builder()
    			.setPath(filePath)
    			.setBufferSize(4096)
    			.setCharset(StandardCharsets.UTF_8)
    			.get()) {
	    	
	        List<String> lastLines = rlfReader.readLines(lastLinesToRead);
	        StringBuilder stringBuilder = new StringBuilder();
	        Collections.reverse(lastLines);
	        lastLines.forEach(
	          line -> stringBuilder.append(line).append("\n")
	        );
	        
	        return stringBuilder.toString();
	    }
	}

}
