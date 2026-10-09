package org.code_studio.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import org.code_studio.component.ResourceBundleBean;
import org.code_studio.controller.MainController;
import org.code_studio.main.JfxMainApplication.StageReadyEvent;
import org.code_studio.component.CSThreadPool;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
	@Value("${ui.language}") String uiLanguage;
	@Value("${debugmode}") boolean debugmode;
	@Value("${ui.mainwindow.maxwidth}") int maxWidth;
	@Value("${ui.mainwindow.maxHeight}") int maxHeight;
	@Value("${ui.mainwindow.resizable}") boolean mainWindowResizable;
	@Value("${ui.mainwindow.maximizeonstart}") private boolean mainWindowMaximizeOnStart;
	
	@Value("classpath:org/code_studio/view/Main.fxml")
	private Resource resource;
	private ApplicationContext ctx;
	
	@Autowired
	BuildProperties buildProperties;
	
	private int clientScreenWidth = (int) Screen.getPrimary().getBounds().getWidth();
	private int clientScreenHeight = (int) Screen.getPrimary().getBounds().getHeight();
	private Stage stage;

	public StageInitializer(ApplicationContext ctx) {
        this.ctx = ctx;
        
        //hack za resource bundles, dobijanje app contexta, dok ne provalim kako inicijalno dobijam app context .. treba mi za globalne beanove
        // kojima pristupam iz klasa koje ja instanciram, a ne SB.
        ApplicationContextProvider appCtx = new ApplicationContextProvider();
        appCtx.setApplicationContext(ctx);
        
        //Fixed thread pool for executing data fetch from the server
        CSThreadPool threadPool = CSThreadPool.getInstance();
        Common.setThreadPool(threadPool);
    }
	
	@Override
	public void onApplicationEvent(StageReadyEvent event) {
		try {
			ResourceBundle bundle = (ResourceBundle) ctx.getBean(ResourceBundleBean.class).getBundle();
			FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL(), bundle);
			
			//cemu ovo sluzi kad radi i bez njega? 
			// Sluzi tome da spring boot moze da injectuje npr @Value. Bez ovog to ne uradi!!!
			fxmlLoader.setControllerFactory(controllerClass -> ctx.getBean(controllerClass));
			Parent parent = fxmlLoader.load();
			stage = event.getStage();
			Common.mainStage = stage;
			this.setMasterIcon(stage);
			stage.setScene(new Scene(parent, clientScreenWidth, clientScreenHeight));
			
			if (debugmode) {
				clientScreenWidth = maxWidth;
				clientScreenHeight = maxHeight;
				stage.setWidth(maxWidth);
				stage.setHeight(maxHeight);
			}
			
			//reduces font size on smaller screen so all components fits correctly
			if (clientScreenWidth <= maxWidth && !stage.getScene().getRoot().getStyleClass().contains("root-small-screen")) {
				stage.getScene().getRoot().getStyleClass().add("root-small-screen");
			}
			
			//stage.setTitle(buildProperties.getName() + " v." + buildProperties.getVersion());
			stage.setTitle(buildProperties.getName() + " " + Common.applicationProperties.getProperty("app.version"));
			stage.setMaximized(mainWindowMaximizeOnStart);
			stage.setResizable(mainWindowResizable);
			((MainController)fxmlLoader.getController()).setUserAccessRights(parent);
			stage.show();
			// Nabudz da radi login screen
			((MainController)fxmlLoader.getController()).postInitialize();
			
			//close all open application windows, if we have any
			stage.setOnCloseRequest(e->{
				Common.applicationExit(e);
			});
		} catch (Exception ex) {
			Common.logMessage(Common.class, ex, "ERROR");
		}
	}
	
	//TODO: koristiti BaseStage jer ona ima vec ovo
	public void setMasterIcon (Stage stage) {
		InputStream resource = null;
		try {
			resource = new ClassPathResource("rm-icon.png").getInputStream();
		} catch (IOException e) {
			Common.logMessage(getClass(), e, "ERROR");
		}
		stage.getIcons().add(new Image(resource));
	}
	
	//HACK za postavljanje globalnog app contexta. Za sad ne znam kako da dobijem
	// bez ove staticke klase u klasama koje ja instanciram.
	public static class ApplicationContextProvider {
	    private static ApplicationContext ctx;
	     
	    public static ApplicationContext getApplicationContext() {
	        return ctx;
	    }
	     
	    
	    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
    		ApplicationContextProvider.ctx = ctx;
		}
	}
	
}
