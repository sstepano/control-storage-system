package org.code_studio.controller;

import java.util.List;

import org.code_studio.component.ui.CSDialogButtons;
import org.code_studio.database.ApplicationUser;
import org.code_studio.database.JwtResponse;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

public class LoginController extends BaseController {

	@SuppressWarnings("unused")
	private ApplicationContext ctx;
	
	@FXML private TextField tfUsername;
	@FXML private PasswordField tfPassword;
	@FXML private CSDialogButtons dialogButtons;
	
	private CSRestService<ApplicationUser> rsApplicationUser;
	private final String urlApplicationUser = "/applicationUser/findByUsernameAndPassword/";
	private ApplicationUser applicationUser;
	
	public LoginController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.ctx = ctx;
	}
	

	public void initialize() {
		try {
			this.getDialog().initStyle(StageStyle.UNDECORATED);
			rsApplicationUser = new CSRestService<>(urlApplicationUser);
			CSRestService<JwtResponse> csAuth = new CSRestService<>("/authenticate");
			
			dialogButtons.getSaveButton().setOnAction( _ -> {
				if (csAuth.authenticate(tfUsername.getText(), tfPassword.getText())) { //authenticated
					rsApplicationUser.setUrl(rsApplicationUser.getUrl() + tfUsername.getText() + "/" + tfPassword.getText());				
					rsApplicationUser.fetch(new ParameterizedTypeReference<JsonResponse<ApplicationUser>>() {});
					List<ApplicationUser> lstApplicationUser = rsApplicationUser.getData();
					if (lstApplicationUser != null) {
						applicationUser = lstApplicationUser.get(0);
					}
					setReturnValue(applicationUser); // vracamo logovanog usera
					dialogButtons.closeForm();
				} else {
					// Ovde mora samo show notification posto je login modal i nece se drugacie poruka videti
					Common.ShowNotification("GREŠKA KOD PRIJAVE KORISNIKA", "Korisnik ili šifra nisu ispravni.\nProverite unete vrednosti ili kontaktirajte administratora aplikacije.", true);
				}
			});	
			
			/***
			 * Close app if user clicks "CLOSE" and does not authenticate
			 */
			dialogButtons.getCancelButton().setOnAction(e->{
				Common.applicationExit(e);
			});
			
			this.getDialog().setOnCloseRequest( e-> {
				Common.applicationExit(e);
			});
			
			// --- MOVE FOCUS ON ENTER --- START ---
			tfUsername.setOnKeyReleased( e-> {
				if (e.getCode() == KeyCode.ENTER) {
					tfPassword.requestFocus();
				}
			});
			
			tfPassword.setOnKeyReleased( e-> {
				if (e.getCode() == KeyCode.ENTER) {
					dialogButtons.getSaveButton().fire();
					tfUsername.requestFocus();
				}
			});
			// --- MOVE FOCUS ON ENTER --- END ---
			
			// --- Bane & Admin shortcuts START ---
			tfUsername.setOnKeyPressed(e -> {
				activateKeyCombination(e);
			});
			
			tfPassword.setOnKeyPressed(e -> {
				activateKeyCombination(e);
			});
			// --- Bane & Admin shortcuts END ---

		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške pri pozivanju forme.\n" + ex.getLocalizedMessage(), true);
			Common.logMessage(getClass(), ex, "ERROR");
		} 
	}// initialize end
	
	private void activateKeyCombination(KeyEvent e) {
		KeyCombination baneKeyCombo = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_ANY, KeyCombination.ALT_ANY, KeyCombination.SHIFT_ANY);
		// TODO: videti koji je bese admin combo --- KeyCombination adminKeyCombo = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_ANY, KeyCombination.ALT_ANY, KeyCombination.SHIFT_ANY);
		if (baneKeyCombo.match(e)) {
			tfUsername.setText("Bane");
			tfPassword.setText("2468");
			dialogButtons.getSaveButton().fire();
		}		
	}
	
	
}
