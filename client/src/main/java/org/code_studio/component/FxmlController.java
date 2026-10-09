package org.code_studio.component;

import org.code_studio.controller.BaseController;

public class FxmlController {

	private BaseController controller = null;
    private String fxmlResource = null;

    public FxmlController(Object controller, String fxmlResource) {
    	this.controller = (BaseController) controller;
    	this.fxmlResource = fxmlResource;
    }

	public BaseController getController() {
		return controller;
	}

	public void setController(Object controller) {
		this.controller = (BaseController) controller;
	}

	public String getFxmlResource() {
		return fxmlResource;
	}

	public void setFxmlResource(String fxmlResource) {
		this.fxmlResource = fxmlResource;
	}

}
