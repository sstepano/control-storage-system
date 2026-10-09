package org.code_studio.component;

public class CSTableColumn {

	private String displayName;
	private String tooltip;
	private String methodName;
	private int width;
	private String backgroundColor;
	private String textColor;
	private String[] conditionalFormattingParams;
	private Boolean isVisible = true;
	private String alignment = "";

	@FunctionalInterface
    public interface CSTableColumnCellValueObserver {
    	public abstract void onCellValue(Object cellValue);
    }


	public CSTableColumn() {}

	public CSTableColumn(String displayName, String methodName) {
		this.displayName = displayName;
		this.methodName = methodName;
	}

	public CSTableColumn(String displayName, String callableMethodName, int size) {
		this.displayName = displayName;
		this.methodName = callableMethodName;
		this.width = size;
	}

	public CSTableColumn(String displayName, String tooltip, String callableMethodName, int size) {
		this.displayName = displayName;
		this.tooltip = tooltip;
		this.methodName = callableMethodName;
		this.width = size;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String[] getConditionalFormattingParams() {
		return conditionalFormattingParams;
	}

	public void setConditionalFormattingParams(String[] conditionalFormattingParams) {
			this.conditionalFormattingParams = conditionalFormattingParams;
	}

	/**
	 * Used to inspect cell value in given column and to execute code
	 * @param observer
	 *
	 *
	public final void onCellValue (CSTableColumnCellValueObserver observer) {
		observer.onCellValue(null);
	}
	*/
	
	public Boolean isVisible() {
		return this.isVisible;
	}
	
	public void setVisible(Boolean visible) {
		this.isVisible = visible;	
	}

	/**
	 * 
	 * @return
	 */
	public String getAlignment() {
		String styleClass = "";
		
		switch(alignment) {
		case "RIGHT":
			styleClass = "column-center-right";
		break;
	}
		return styleClass;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
	
}
