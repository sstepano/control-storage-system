package org.code_studio.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CSPrintButton extends VBox {
	
	@FXML private Button btnPrint;
	@FXML private CheckBox ckbPrintWithoutDisplay;
	@FXML private HBox hbNumberOfCopies;
	@FXML private CSTextField tfNumberOfCopies;
	
	private String strReportName = null;
	private List<String> lstReportConfig;
	private HashMap<String, Object> hmReportParams;
	
	private boolean isPrintWithoutDisplayVisible = false;
	private boolean isNumberOfCopiesVisible = false;
	private boolean printWithoutDisplaySelected = false;
	
	
	/**
	 * @author gkljajic
	 */
    public CSPrintButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSPrintButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        lstReportConfig = new ArrayList<>();
        hmReportParams = new HashMap<>();

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    /**
	 * @return the reportName
	 */
	public String getReportName() {
		return strReportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.strReportName = reportName;
	}
	
	//REPORT_CONFIG START
	public void addReportConfigParam(String configParam) {
		lstReportConfig.add(configParam);
	}
	
	public void clearReportConfigParams() {
		lstReportConfig.clear();
	}
	//REPORT_CONFIG END

	/**
	 * @return the rptParams
	 */
	public HashMap<String, Object> getRptParams() {
		return hmReportParams;
	}

	/**
	 * @param rptParams the rptParams to set
	 */
	public void setRptParams(HashMap<String, Object> rptParams) {
		this.hmReportParams = rptParams;
	}

	/**
     * Adding params to the report hashmap <String, Object>
     * @param name
     * @param value
     */
    public void addParam(String name, Object value) {
    	if (name == null || name.isBlank() || value == null || name.equals("REPORT_CONFIG")) {
    		throw new RuntimeException("Name or value cannot be empty and name cannot contain REPORT_CONFIG!");
    	}
    	
    	hmReportParams.put(name, value);
    }
    
    public void clearParamList () {
    	hmReportParams.clear();
    }

    
    public String getText() {
		return btnPrint.getText();
	}

	public void setText(String text) {
		btnPrint.setText(text);
	}
	

	/**
	 * @return the isCkbPrintWithoutDisplayVisible
	 */
	public boolean getPrintWithoutDisplayVisible() {
		return isPrintWithoutDisplayVisible;
	}

	/**
	 * @param isPrintWithoutDisplayVisible the isCkbPrintWithoutDisplayVisible to set
	 */
	public void setPrintWithoutDisplayVisible(boolean isPrintWithoutDisplayVisible) {
		this.isPrintWithoutDisplayVisible = isPrintWithoutDisplayVisible;
		ckbPrintWithoutDisplay.setManaged(isPrintWithoutDisplayVisible);
		ckbPrintWithoutDisplay.setVisible(isPrintWithoutDisplayVisible);
	}

	/**
	 * @return the isTfNumberOfCopiesVisible
	 */
	public boolean getNumberOfCopiesVisible() {
		return isNumberOfCopiesVisible;
	}

	/**
	 * @param isNumberOfCopiesVisible the isTfNumberOfCopiesVisible to set
	 */
	public void setNumberOfCopiesVisible(boolean isNumberOfCopiesVisible) {
		this.isNumberOfCopiesVisible = isNumberOfCopiesVisible;
		hbNumberOfCopies.setManaged(isNumberOfCopiesVisible);
		hbNumberOfCopies.setVisible(isNumberOfCopiesVisible);
	}

	/**
	 * @return the printWithoutDIsplaySelected
	 */
	public boolean getPrintWithoutDisplaySelected() {
		return printWithoutDisplaySelected;
	}

	/**
	 * @param printWithoutDIsplaySelected the printWithoutDIsplaySelected to set
	 */
	public void setPrintWithoutDisplaySelected(boolean printWithoutDIsplaySelected) {
		this.printWithoutDisplaySelected = printWithoutDIsplaySelected;
		ckbPrintWithoutDisplay.setSelected(printWithoutDIsplaySelected);
	}
	
	public Node getGraphic () {
		return btnPrint.getGraphic();
	}
	
	public void setGraphic (Node graphic) {
		btnPrint.setGraphic(graphic);
	}

	/************************************************
     * INITIALIZE METHOD
     */
    @FXML public void initialize() {
    	
    	btnPrint.setOnAction( _ -> {
    		if (strReportName == null) {
    			throw new RuntimeException("Report name cannot be blank");
    		}
    		
    		hmReportParams.remove("REPORT_CONFIG");
    		hmReportParams.put("REPORT_CONFIG", lstReportConfig);
			new CSReportView(strReportName, hmReportParams, ckbPrintWithoutDisplay.isSelected(), tfNumberOfCopies.getTextAsInteger()); 
    	});
    }

}
