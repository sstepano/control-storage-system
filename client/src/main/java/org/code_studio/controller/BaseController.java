package org.code_studio.controller;

import java.util.ArrayList;

import org.code_studio.component.BaseStage;
import org.code_studio.component.UserAccess.CSUserRole;
import org.code_studio.main.Common;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class BaseController {
	
	private Object returnValue;
	private BaseStage dialog;
	
	public Object getReturnValue() {
		return this.returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	
	/***********************************
	 * Kad budem imao vremena, da promenim sve kontrolere da imaju init,
	 * a da se iz samog kontrolera poziva ovaj base metod initialize, koji ce da
	 * poziva derived init
	 * Tako cu da forsiram try catch i neku jos
	 * default funkcionalnost za kontroler
	 **********************************
	public abstract void init();
	
	protected void initialize () {
		this.init();
	}
	**********************************/
	
	/**
	 * Executed after stage initialization has completed
	 * and the stage has been shown on the screen
	 * It should be overridden for custom behavior
	 */
	public void postInitialize() {}
	
	/***
	 * @return dialog that was created and assotiated with the controller
	 * Dialog instance is created in Common.class and then after creation, assigned to the controller
	 */
	public BaseStage getDialog() {
		return this.dialog;
	}
	
	/***
	 * Sets dialog instance in Common.class after creation
	 * @param dialog
	 */
	public void setDialog(BaseStage dialog) {
		this.dialog = dialog;
	}
	
	/**
	 * Loops through all the components and hides/disables them
	 * if user does not have the right to operate on it, based
	 * on ROLE(s) which user has.
	 * Executed after stage initialization has completed
	 * and the stage has been shown on the screen
	 * It can be overridden for custom behavior
	 */
	@SuppressWarnings("unchecked")
	public void setUserAccessRights(Parent root) {
		final CSUserRole myUserRole = new CSUserRole();
		myUserRole.setValue("ROLE11");
		
		ArrayList<Object> nodes = Common.getAllNodes(root);
		for (Object objNode : nodes) {
			//System.out.println("Node found! Node type: " + objNode.getClass().getSimpleName());
			if (objNode instanceof Menu) {
				Menu node = (Menu)objNode;
				if (node.getUserData() != null && node.getUserData() instanceof ObservableList) {
					
					for (CSUserRole userRole : (ObservableList<CSUserRole>) node.getUserData()) {
						if (userRole.getValue().equals(myUserRole.getValue())) {
							//System.out.println("Role found. Node will be visible");
							node.setVisible(true);
							return;
						} else {
							//System.out.println("Role not found. Node will be not visible");
							node.setAccelerator(null); //disable shortcut such as F3, if exists
							node.setVisible(false);
						}
					}
				}
			} else if (objNode instanceof MenuItem) {
				MenuItem node = (MenuItem)objNode;
				if (node.getUserData() != null && node.getUserData() instanceof ObservableList) {
					
					for (CSUserRole userRole : (ObservableList<CSUserRole>) node.getUserData()) {
						if (userRole.getValue().equals(myUserRole.getValue())) {
							System.out.println("Role found. Node will be visible");
							node.setVisible(true);
							return;
						} else {
							//System.out.println("Role not found. Node will be not visible");
							node.setAccelerator(null); //disable shortcut such as F3, if exists
							node.setVisible(false);
						}
					}
				}
			} else {
				//System.out.println("Node found! Node type: " + objNode.getClass().getSimpleName());
				Node node = (Node)objNode;
				if (node.getUserData() != null && node.getUserData() instanceof ObservableList) {
					
					for (CSUserRole userRole : (ObservableList<CSUserRole>) node.getUserData()) {
						if (userRole.getValue().equals(myUserRole.getValue())) {
							//System.out.println("Role found. Node will be visible");
							node.setVisible(true);
							return;
						} else {
							//System.out.println("Role not found. Node will be not visible");
							node.setVisible(false);
						}
					}
				}
			}
		}
	}

}
