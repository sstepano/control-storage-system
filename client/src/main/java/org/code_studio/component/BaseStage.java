package org.code_studio.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import org.code_studio.main.StageInitializer.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BaseStage extends Stage {

	private ResourceBundle resourceBundle = null;

	public BaseStage () {

		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		resourceBundle = ctx.getBean(ResourceBundleBean.class).getBundle();

		//TODO: napraviti da svi stagevi izlaze iz ovog. Za sad osnovni stage je samo Stage.
		InputStream resource = null;
		try {
			resource = new ClassPathResource("rm-icon.png").getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getIcons().add(new Image(resource));

		//TODO: Razmisliti: Propagacija ESCAPE-a se ne nastavlja dalje do drugog, parent stage-a, vec se
		// zaustavlja ovde. To znaci da cu mozda imati problem ako hocu da na escape ovde
		// imam akciju u parent stageu jer se u parentu nece generisati.
	    this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
	        if (event.getCode() == KeyCode.ESCAPE) {
	        	this.fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST));
	            this.close();
	        }
	    });

	}

	/*
	 * Sets dialog title taken from resoureBUndleString format. String is defined in strings_en/sr.properties file.
	 * titleString is in format label.something.something
	 */
	public void setTitleFromResourceString(String titleString) {
		this.setTitle(resourceBundle.getString(titleString));
	}

}
