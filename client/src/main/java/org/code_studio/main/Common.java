package org.code_studio.main;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.code_studio.component.BaseStage;
import org.code_studio.component.CSThreadPool;
import org.code_studio.component.ConfirmationDialog;
import org.code_studio.component.FxmlController;
import org.code_studio.component.ResourceBundleBean;
import org.code_studio.database.ApplicationUser;
import org.code_studio.database.Dbini;
import org.code_studio.main.StageInitializer.ApplicationContextProvider;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.controlsfx.control.Notifications;
import org.controlsfx.glyphfont.Glyph;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
//TODO: otkomentarisati kada otkucam DEBUG kod ya velicinu ekrana import javafx.stage.Screen;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

@Component
public class Common {
	final static String classpath = "org/code_studio/";
	final static String viewClasspath = classpath + "view/";
	public static Properties applicationProperties = null;
	public static Properties clientProperties = null;
	private static CSThreadPool threadPool;
	private static ProgressBar pbProgressIndicator;
	private static BorderPane mainBorderPane;
	public static Timer timerDatetimeUpdater; // We populate it in MainController when we create it. Then we use it in Common when we want to close app in ApplicationExit method
	public static String baseUrl;
	
	public ApplicationContext ctx;
	private TabPane tabPane;
	
	public static Stage mainStage;

	/*
	 * Holds all open windows or tabs, so we can control whether we will
	 * create a new window/tab on menuitem click, or activate existing one
	 * */
	private Hashtable <String, Object> openWindows;
	
	private static int maxWidth;
	private int maxOpenTabs;
	private static boolean forceMaxSize;
	public static int clientScreenWidth;//(int) Screen.getPrimary().getBounds().getWidth(), puni se iz StageInitializer, posto samo moze da se pristupi iz display threada.
	private static boolean dialogResizable;
	private static ApplicationUser applicationUser; // logovani user
	
	/**
	 * Lista selektovanih <ItemSimple>, na F6. 
	 * Ovako mogu da prenosim podatke izmedju formi
	 * Umesto ItemSimple stavljen je Object da ne bih dodavao database dependency u klijenta
	 * Kod koriscenja mora da se castuje u ItemSimple
	 */
	public static ObservableList<Object>selectedItems;
	
	public Common(ApplicationContext ctx) {
        try {
        	applicationProperties = PropertiesLoader.loadProperties("application.properties");
			//clientProperties = PropertiesLoader.loadProperties("../../../client.properties"); //TODO
        	
    		this.ctx = ctx;
    		openWindows = new Hashtable<>();
    		maxWidth = Integer.parseInt((String) applicationProperties.getProperty("ui.mainwindow.maxWidth", "1000"));
    		maxOpenTabs = Integer.parseInt(applicationProperties.getProperty("ui.maxopentabs", "1"));
    		forceMaxSize = Boolean.parseBoolean(applicationProperties.getProperty("ui.mainwindow.forceMaxSize", "false"));
    		baseUrl = applicationProperties.getProperty("api.serverurl");
    		dialogResizable = Boolean.parseBoolean(applicationProperties.getProperty("ui.dialog.resizable", "false"));
    		selectedItems = FXCollections.observableArrayList();
		} catch (IOException e) {
			logMessage(getClass(), e, "ERROR");
		}
	}
	
	/***
	 * 
	 * @param parent
	 * Must be used in MainController to set manBorderPane as the parent of tabpane
	 */
	public void setParentContainer(Node parent) {
		if (parent instanceof BorderPane) {
			tabPane = new TabPane();
			tabPane.setFocusTraversable(false);
			((BorderPane) parent).setCenter(tabPane);
			tabPane.getStyleClass().add("tab-base");
		}
	}
	
	/***
	 * Used to display child forms
	 * @param fxmlController
	 * @param parent
	 * @param formTitle
	 * @return
	 */
	public static Object displayForm (FxmlController fxmlController, Parent parent, String formTitle) {	
		try {
			BaseStage dialog = new BaseStage();
			FXMLLoader fxmlLoader = null;
			ResourceBundle resourceBundle = null;
			
			if (forceMaxSize) {
				Common.clientScreenWidth = maxWidth;
			} else {
				Common.clientScreenWidth = (int) Screen.getPrimary().getBounds().getWidth();
			}
	
			if (parent != null) {
				dialog.initOwner(parent.getScene().getWindow());
				dialog.initModality(Modality.WINDOW_MODAL);
			}
			
			ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
			resourceBundle = (ResourceBundle) ctx.getBean(ResourceBundleBean.class).getBundle();
			if (formTitle != null) {
				// if title has dot in it, we assume that it is in resourcestring format %label.something.something
				// then, we will set its title from resourcestring, otherwise, just use plain string as title
				if (formTitle.contains("%")) {
					dialog.setTitleFromResourceString(formTitle.substring(1));
				}
				else {
					dialog.setTitle(formTitle);
				}
			}
			else {
				dialog.setTitle(resourceBundle.getString("label.addeditform.title"));
			}
			
			dialog.setResizable(dialogResizable);
			
			try {
				fxmlLoader = new FXMLLoader(Common.class.getClassLoader()
						.getResource(viewClasspath + fxmlController.getFxmlResource()), resourceBundle);
			} catch (Exception e) {
				logMessage(Common.class, e, "ERROR");
			}
			fxmlLoader.setController(fxmlController.getController());
			fxmlController.getController().setDialog(dialog);
			
			try {
				Parent root = fxmlLoader.load();
				dialog.setScene(new Scene(root));
			    
				//reduces font size on smaller screen so all components fits correctly
				if (clientScreenWidth <= maxWidth && !root.getStyleClass().contains("root-small-screen")) {
					//System.out.println("small screen detected");
					root.getStyleClass().add("root-small-screen");
				}
			} catch (IOException ex) {
				logMessage(Common.class, ex, "ERROR");
			}			
		
			//Moramo da registrujemo listener jer center on parent trazi da je dialog prikazan, da bi dobio koordinate
			// dok show and wait blokira thread. Ovako ce da se pozove onshown samo trenutak pre nego sto showandwaid
			//blokira thread. Ovo nam treba da se tableView refreshuje automatski kada se promeni info u dialog-u.
			dialog.setOnShown(_->{
				centerOnParent(parent, dialog);
				fxmlController.getController().postInitialize();
				fxmlController.getController().setUserAccessRights(dialog.getScene().getRoot());
			});
			
			dialog.showAndWait();
			return fxmlController.getController().getReturnValue();
		}
		catch (Exception ex) {
			ShowNotification("GREŠKA", "Došlo je do greške pri prikazivanju forme.", true);
			logMessage(Common.class, ex, "ERROR");
			return null;
		}
	}
	
	/**
	 * Used to display panes in tabpane, called from main menu
	 * @param fxmlController
	 * @return
	 */
	public Object displayForm (FxmlController fxmlController, String formTitle, Node icon, ApplicationContext ctx) {
		try {
			FXMLLoader fxmlLoader = null;
			ResourceBundle resourceBundle = null;
			AnchorPane root = null;
			
			resourceBundle = (ResourceBundle) ctx.getBean(ResourceBundleBean.class).getBundle();
			
			try {
				fxmlLoader = new FXMLLoader(Common.class.getClassLoader()
						.getResource(viewClasspath + fxmlController.getFxmlResource()), resourceBundle);
			} catch (Exception e) {
				logMessage(getClass(), e, formTitle);
			}
			fxmlLoader.setController(fxmlController.getController());
			fxmlLoader.setControllerFactory(ctx::getBean);
			
			if (isWindowOpen(formTitle)) {
				tabPane.getSelectionModel().select((Tab) openWindows.get(formTitle));
			} else { // if NOT openWindows.contains tab
				//do not open new tab if max tabs are already open
				if (openWindows.size() >= maxOpenTabs) {
					Common.ShowNotification(formTitle, resourceBundle.getString("label.errormaxtabsopen.text"), true);
					return null;
				}
	
				try {
					root = fxmlLoader.load();
				} catch (IOException ex) {
					Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
					logMessage(getClass(), ex, formTitle);
					return null;
				}
				
				Tab tab = new Tab(formTitle);
				openWindows.put(formTitle, tab);	
				tab.setContent(root);
	
				//Allows glyph duplication. Since in the JavaFX scenegraph it is not possible to insert the same Node
				//in multiple locations at the same time, this method allows for glyph reuse in several places
				//tab.setGraphic(((Glyph) icon).duplicate());
				try {
					FontIcon _icon = (FontIcon) icon;
					FontIcon icon_duplicated = new FontIcon(_icon.getIconCode());
					icon_duplicated.setIconColor(_icon.getIconColor());
					icon_duplicated.setIconSize(_icon.getIconSize());
					tab.setGraphic(icon_duplicated);
				} catch (Exception ex) {
					// privremeni fix, dok ne zamenim sve ikone sa Ikonli
					tab.setGraphic(((Glyph) icon).duplicate());
				}
				
				tabPane.getTabs().add(tab);
				tabPane.getSelectionModel().select(tab);
				tabPane.getStyleClass().removeAll("tab-base");
				fxmlController.getController().postInitialize();
				fxmlController.getController().setUserAccessRights(root);
	
				tabPane.setOnKeyReleased( e-> {
			        KeyCode keyCode = e.getCode();
			        if (keyCode.equals(KeyCode.ESCAPE)) {
			        	String selectedTabTitle = null;
			        	if (tabPane.getSelectionModel().getSelectedIndex() > -1) {
			        		selectedTabTitle = tabPane.getSelectionModel().getSelectedItem().getText();
			        		openWindows.remove(selectedTabTitle);
			        		tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
			        		tabPane.getSelectionModel().selectPrevious(); // when deleting one, select previously selected tab
	
			        		// vracamo bg imag ako nema otvorenih tabova
			        		if(tabPane.getTabs().size() == 0) {
			        			tabPane.getStyleClass().add("tab-base");
			        		}
			        	}
			        }
				});
	
				//on close we remove it from the hastable
				tab.setOnClosed(_ -> {
					String selectedTabTitle = null;
	        		selectedTabTitle = tab.getText();
	        		openWindows.remove(selectedTabTitle);
	
	        		// vracamo bg imag ako nema otvorenih tabova
	        		if(tabPane.getTabs().size() == 0) {
	        			tabPane.getStyleClass().add("tab-base");
	        		}
				});
			}
		
		} catch (Exception ex) {
			logMessage(Common.class, ex, "ERROR");
		}
		
		return fxmlController;
		
	}
	
	
	/**
	 * 
	 * @param fxmlResource
	 * @return
	 */
	public void closeForm(String formTitle) {
		if (isWindowOpen(formTitle)) {
			tabPane.getTabs().remove((Tab) openWindows.get(formTitle));
			openWindows.remove(formTitle);
		}
	}
	
	
	/*
	 * Returns VIEW FXML resource from string "filename.fxml"
	 * */
	public static URL getResource (String fxmlResource) {
		return Common.class.getClassLoader().getResource(viewClasspath + fxmlResource);
	}

	/**
	 * Shows popup notification in the bottom right corner of the application
	 */
	public static void ShowNotification(String title, String message, boolean isError) {
		Notifications nt = Notifications.create();
		nt.darkStyle();
		nt.title(title);
		nt.text(message);
		nt.hideAfter(new Duration(10000));
		
		if (isError) nt.showError();
		else nt.showInformation();
	}
	
	/*
	 * Sets dialogs on parent center 
	 */
	public static void centerOnParent(Parent parent, BaseStage dialog) {
		dialog.setX(
				parent.getScene().getWindow().getX() + 
				parent.getScene().getWindow().getWidth() / 2 - 
				dialog.getWidth() / 2);

		
		dialog.setY(
				parent.getScene().getWindow().getY() + 
				parent.getScene().getWindow().getHeight() / 2 - 
				dialog.getHeight() / 2);
	}
	
	/**
	 * 
	 * @param parent
	 * @return
	 */
	public static ArrayList<Object> getAllNodes(Parent root) {
	    ArrayList<Object> nodes = new ArrayList<Object>();
	    addAllDescendants(root, nodes);
	    return nodes;
	}

	private static void addAllDescendants(Object parent, ArrayList<Object> nodes) {
		if (parent instanceof MenuBar) {
			//System.out.println("Found MenuBar, adding");
		    for (Menu node : ((MenuBar)parent).getMenus()) {
		        nodes.add(node);
		        //if (node instanceof MenuItem) {
		        	//sSystem.out.println("uso");
		            addAllDescendants((Menu)node, nodes);
		        //}
		    }
		}
		else if (parent instanceof Menu) {
			//System.out.println("Found Menu, adding"); 
		    for (MenuItem node : ((Menu)parent).getItems()) {
		        nodes.add(node); /*
		       if (node instanceof MenuItem) {
		        	System.out.println("uso1");
		            addAllDescendants((MenuItem)node, nodes);
		        }*/
		    }
		}
		else if (parent instanceof MenuItem) {
			//System.out.println("Found MenuItem, doing nothing");
		} else { // all other node types
		    for (Node node : ((Parent)parent).getChildrenUnmodifiable()) {
		        nodes.add(node);
		        if (node instanceof Parent) {
		            addAllDescendants((Parent)node, nodes);
		        }
		    }
		}
	}
	
	/**
	 * Returns true if window with specific title is already open, either as tab or dialog
	 * @param formTitle
	 * @return
	 */
	public boolean isWindowOpen(String formTitle) {
		return openWindows.containsKey(formTitle);
	}
	
	/**
	 * gets handle to the window if it is open
	 * handle can be tab or dialog
	 * @param formTitle
	 * @return Object
	 */
	public Object getWindowIfOpen(String formTitle) {
		return openWindows.containsKey(formTitle) ? 
				openWindows.get(formTitle) : null;
	}
	
	/**
	 * Opens window with specific title
	 */
	public void openWindow() {
		
	}

	
	/*
	 * PROPERTIES LOADER, FOR USING PROPERTIES VALUES WITHOUT SPRING BOOT
	 * , WHERE WE CANNOT USE @COMPONENT ON CLASS, BUT WE NEED VALUE FROM PROPS.
	 * */
	public class PropertiesLoader {
	    public static Properties loadProperties(String resourceFileName) throws IOException {
	        Properties configuration = new Properties();
	        InputStream inputStream = PropertiesLoader.class
	          .getClassLoader()
	          .getResourceAsStream(resourceFileName);
	        configuration.load(inputStream);
	        inputStream.close();
	        configuration.load(new FileInputStream("./client.properties")); // TODO: Promeniti tako da cita iz prosledjenog argumenta metoda
	        return configuration;
	    }
	    
	    public static String encodeProperty(String originalString) {
	    	return Base64.getEncoder().encodeToString(originalString.getBytes());
	    }
	    
	    public static String decodeProperty(String propertyName) {
	    	byte[] decodedBytes = Base64.getDecoder().decode(applicationProperties.getProperty(propertyName));
	    	return new String(decodedBytes);
	    }
	    
	    /**
	     * TODO: Nedovrsena implementacija
	     */
	    public static void persist() {
	    	try {
	    	String resourceFileName = "client.properties";
	        File propertiesFile = new File(resourceFileName);
	        OutputStream outputStream = new FileOutputStream(propertiesFile);
	        DefaultPropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	        propertiesPersister.store(applicationProperties, outputStream, "Header Comment");
	    	} catch (Exception e) {
				logMessage(Common.class, e, "ERROR");
			}
	    }

	}
	
	/*
	 * Logs runtime messages to the file
	 * We use Object for message, because sometimes it is String,
	 * sometimes it is Exception
	 */
	public static void logMessage (Class<?> clazz, Object message, String level) {
		Logger logger = LoggerFactory.getLogger(clazz.getName());
		switch(level.toUpperCase()) {
			case "ERROR":
				logger.error("{}", message);
			break;
			case "WARN":
				logger.warn("{}", message);
			break;
			case "DEBUG":
				logger.debug("{}", message);
			break;
			case "INFO":
				logger.info("{}", message);
			break;
			default:
				logger.info("{}", message);
			break;
		}
	}
	
	/**
	 * 
	 * @return String: Application base path (jarname.jar/BOOT-INF/classes)
	 */
	public static String getApplicationBasepath() {
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}

	public static CSThreadPool getThreadPool() {
		return threadPool;
	}

	public static void setThreadPool(CSThreadPool threadPool) {
		Common.threadPool = threadPool;
	}
	
	/**
	 * Shows file open dialog
	 * TODO: Add input param for file types, for example EXCEL, XML etc.
	 * @param String fileExtension, bez *., samo ekstenzija, kao npr xls ili xlsx
	 * @return
	 */
	public static File getFileChooser(String fileExtension) {
		List<ExtensionFilter> extensionFilters;
    	extensionFilters = new ArrayList<>();
    	extensionFilters.add(new ExtensionFilter("EXCEL", "*." + fileExtension));    	
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Odaberite fajl za učitavanje");
        fileChooser.getExtensionFilters().setAll(extensionFilters);
        File file = fileChooser.showOpenDialog(mainStage);
        return file;
	}

	/**
	 *  main border pane shown in the main stage
	 *  used when we need to set parent for exit dialog box
	 * @return
	 */
	public static void setMainBorderPane(BorderPane mainBorderPane) {
		Common.mainBorderPane = mainBorderPane;
	}
	
	/**
	 *  progress bar on main form at the bottom
	 * @return
	 */
	public static void setProgressIndicator(ProgressBar pbProgressIndicator) {
		Common.pbProgressIndicator = pbProgressIndicator;
	}
	
	public static void showProgressBar(String tooltipText) {
		pbProgressIndicator.setTooltip(new Tooltip(tooltipText));
		pbProgressIndicator.setVisible(true);
	}
	
	public static void hideProgressBar() {
		pbProgressIndicator.setVisible(false);
	}
	// Progress bar END
	
	/***
	 * Custom application exit
	 */
	public static void applicationExit(Event e) {
		ConfirmationDialog exitDialog = new ConfirmationDialog(
			mainBorderPane,
			"IZLAZAK IZ APLIKACIJE",
			"Da li ste sigurni da želite da zatvorite Repromarket aplikaciju?",
			"Zatvori",
			"Odustani"
		);
		if (exitDialog.result == ButtonType.OK) {
			getThreadPool().getExecutorService().shutdownNow();
			timerDatetimeUpdater.cancel();
			//mainStage.fireEvent(new WindowEvent(mainStage, WindowEvent.WINDOW_CLOSE_REQUEST));
			mainStage.close();
			Platform.exit();
		} else {
			e.consume(); // prevents closing the app
		}
	}
	
	/**
	 * Gets applicationUser object which is logged user object (NOT a Spring Boot UserDetails class!)
	 * @return
	 */
	public static ApplicationUser getApplicationUser() {
		return applicationUser != null 
			? applicationUser
			: new ApplicationUser(1)
			;
	}
	/**
	 * Postavlja se applicationUser objekat kod LoginController-a
	 */
	public static void setApplicationUser (ApplicationUser applicationUser) {
		Common.applicationUser = applicationUser;
	}
	
	/**
	 * Returns user ID, if there is one logged in. If not, it returns B's ID.
	 * @return
	 */
	public static Integer getApplicationUserId () {
		return applicationUser == null ? 1 : applicationUser.getId();
	}
	
	/**
	 * Delays for a number of milliseconds and then runs the task provided
	 * @param millis
	 * @param continuation
	 */
    public static void delay(long millis, Runnable method) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(_ -> method.run());
        new Thread(sleeper).start();
      }
    
    
    /** TODO
     * Returns dbini value from database
     * @return
     */
    public static String getDbiniStringValue(String attributeName, String parameterName) {
    	CSRestService<Dbini> rsDbini = new CSRestService<Dbini>("/dbini/allByAttributeNameAndParameterName/" + attributeName + "/" + parameterName);
    	rsDbini.fetch(new ParameterizedTypeReference<JsonResponse<Dbini>>() {});
    	return rsDbini.getData()!= null ? rsDbini.getData().getFirst().getValue() : null;
    }
    
    /**
     * Returns Integer value of dbini param
     * If not found, returns 0
     * @param attributeName
     * @param parameterName
     * @return
     */
    public static Integer getDbiniIntegerValue(String attributeName, String parameterName) {
    	String strRes = getDbiniStringValue(attributeName, parameterName);
    	if (strRes != null) {
    		return Integer.valueOf(strRes);
    	} else {
    		return 0;
    	}
    }
	
}
