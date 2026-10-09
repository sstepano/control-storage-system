package org.code_studio.component;


import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRExpressionEvalException;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import org.code_studio.main.Common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;

public class CSReportView {

	@FXML private Button btnPrint;
	@FXML private Button btnClose;
	
	private boolean printWithoutReportDisplay = false;
	private Integer numberOfCopies = 1;
	private MediaSizeName mediaSizeName = MediaSizeName.ISO_A4;
	private Chromaticity chromaticity = Chromaticity.COLOR;
	
	private HashMap<String, Object> mapParams; // mapParams list object must be cloned to this one, because jasper fills it with shis mumbo-jumbo when creating report, and next time report does not work correctly
	
	FXMLLoader fxmlLoader;
	Parent fxmlHierarchy;

	private int sceneWidth;
	private int sceneHeight;
	private String initialDirectory;
	private String initialFileName;
	
	private JasperPrint jasperPrint;
	
	private final String baseUrl = Common.applicationProperties.getProperty("api.serverurl");

	private Service<Boolean> service = new Service<Boolean>() {
		@Override
		protected Task<Boolean> createTask() {
			return new Task<Boolean>() {
				@Override
				protected Boolean call() throws Exception {
					try {
						loadPages();
						return true;
					} catch (Exception e) {
						Common.logMessage(getClass(), e, "ERROR");
						return false;
					}
				}
			};
		}
	};

	/**
	@Deprecated
	public CSReportView() {
		this(800, 600, "./", "");
	}

	@Deprecated
	public CSReportView(int sceneWidth, int sceneHeight, String initialDirectory, String initialFileName) {
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
		this.initialDirectory = initialDirectory;
		this.initialFileName = initialFileName;
		
		fxmlLoader = new FXMLLoader(getClass().getResource("CSReportView.fxml"));
        fxmlLoader.setController(this);

        try {
        	fxmlHierarchy = fxmlLoader.load();
        } catch (IOException ex) {
			Common.logMessage(getClass(), ex, "ERROR");
            throw new RuntimeException(ex);
        }
	}

	/***
	 * 
	 * @param reportFileName
	 * @param mapParams
	 */
	public CSReportView(String reportFileName, HashMap<String, Object> mapParams) {
		this.mapParams = new HashMap<>();
		this.mapParams.putAll(mapParams);
		init(reportFileName, this.mapParams, null, null);
	}
	
	public CSReportView(String reportFileName, HashMap<String, Object> mapParams, Boolean printWithoutReportDisplay, Integer numberOfCopies) {
		this.mapParams = new HashMap<>();
		this.mapParams.putAll(mapParams);
		init(reportFileName, this.mapParams, printWithoutReportDisplay, numberOfCopies);
	}
	
	/**
	 * actual init method of the class
	 * @param reportFileName
	 * @param mapParams
	 */
	private void init(String reportFileName, HashMap<String, Object> mapParams, Boolean printWithoutReportDisplay, Integer numberOfCopies) {
		this.sceneWidth = 800;
		this.sceneHeight = 600;
		
    	this.printWithoutReportDisplay = printWithoutReportDisplay != null ? printWithoutReportDisplay : this.printWithoutReportDisplay;
    	this.numberOfCopies = numberOfCopies != null ? numberOfCopies : this.numberOfCopies;
		
		// fake datasource, ako je null report se ne renderuje, samo prazna strana
		JsonDataSource ds = CSReportView.getJsonDataSourceFromUrl(baseUrl + "/currency/1");

		fxmlLoader = new FXMLLoader(getClass().getResource("CSReportView.fxml"));
        fxmlLoader.setController(this);

        try {
        	fxmlHierarchy = fxmlLoader.load();
        } catch (IOException ex) {
			Common.logMessage(getClass(), ex, "ERROR");
            throw new RuntimeException(ex);
        }
        
        final String path = "../../../reports/";
        //String path = Common.getApplicationBasepath() + "reports/"; Ne radi nesto ...
        String pathToJasperFile = path + reportFileName + ".jasper";
        
        try {
        	/**
        	 * Ne moze file zato sto kada je u jaru, mora da se uzima kao stream
        	 * https://stackoverflow.com/questions/60021886/jasperreport-file-not-found-error-in-fat-jar
			 * JasperReport rpt = (JasperReport)JRLoader.loadObject(new File(pathToJasperFile));
        	 */
        	JasperReport rpt = (JasperReport)JRLoader.loadObject(getClass().getResourceAsStream(pathToJasperFile));
			jasperPrint = JasperFillManager.fillReport(rpt, mapParams, ds);
			Stage viewerStage = buildStage(jasperPrint);
			viewerStage.show();
        } catch (JRExpressionEvalException jrex) {
			Common.ShowNotification("PRIKAZ IZVEŠTAJA", "Ne postoje podaci za izveštaj sa zadatim parametrima.\n", false);
			Common.logMessage(getClass(), jrex, "INFO");        	
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške pri pozivanju izveštaja.\n" + ex.getLocalizedMessage(), true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
	
	public static JsonDataSource getJsonDataSourceFromUrl(String url) {
		InputStream is = null;
		JsonDataSource ds = null;
		
		try {
			//deprecated since Java 20
			//is = new URL(url).openStream();
			is = new URI(url).toURL().openStream();
			ds = new JsonDataSource(is);
		} catch (Exception e) {
			Common.logMessage(CSReportView.class, e, "ERROR");
		}

		return ds;
	}
	
	//****************************************************//

	private void onShow(Stage stage) {
		service.setOnSucceeded(event -> {
			final Object result = event.getSource().getValue();
			if (null != result && (boolean) result) {
				displayPages();
			} else {
				stage.close();
				// stage.fireEvent(new JRViewerEvent(JR_REPORT_LOAD_FAILED));
				System.err.println("Report load failed");
			}
		});

		service.start();
	}

	public Stage buildStage(JasperPrint jasperPrint) {
		final Stage retval = new Stage();
		try {
			retval.setOnShown( _ -> onShow(retval));
			Scene scene = new Scene(fxmlHierarchy, sceneWidth, sceneHeight);
			scene.setOnKeyReleased( e -> {
				if (e.getCode() == KeyCode.ESCAPE) {
					btnClose.fire();
				}
			});
			retval.setScene(scene);
			retval.setFullScreen(true);
			retval.setFullScreenExitHint("");
			retval.initModality(Modality.APPLICATION_MODAL);
			retval.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			this.init();
			setJasperPrint(jasperPrint);

			return retval;
		} catch (Exception e) {
			// logger.error("Could not view report", e);
			Common.logMessage(getClass(), e, "ERROR");
			throw new RuntimeException(e);
		}
	}
	
	//******************************************************

    private static final double SCREEN_DPI = 96;
    private static final double JASPER_DPI = 72;
    private static final double JASPER_TO_SCREEN_DPI_FIX = SCREEN_DPI / JASPER_DPI;

    //private JasperPrint jasperPrint;
    private List<ExtensionFilter> extensionFilters;

    private Double zoomFactor = 1d;
    private double vvalue;
    private double originalPageHeight;
    private double originalPageWidth;

    private List<ImageView> pages = new ArrayList<>();

    private PrintService printService;

    @FXML
    protected BorderPane view;
    @FXML
    private ComboBox<Integer> pageList;
    @FXML
    private Slider zoomLevel;
    @FXML
    private StackPane imageHolder;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vbox;
    @FXML
    public ImageView loadingIcon;

    @FXML
    private Button firstPageBtn;
    @FXML
    private Button btnPrevPage;
    @FXML
    private Button btnNextPage;
    @FXML
    private Button lastPageBtn;

    public boolean busy() {
        return printService.isRunning();
    }

    private double pageToScrollValue(int pageNumber) {
        final double nodeY = vbox.getChildren().get(pageNumber).getLayoutY();
        final double contentH = imageHolder.getHeight();
        final double viewportH = scrollPane.getViewportBounds().getHeight();
        final double calcH = contentH - viewportH;
        return nodeY / calcH;
    }

    /**
     * number of page which body crosses middle of viewport
     *
     * @param value scroll v value
     * @return page number
     */
    private int scrollValueToPage(double value) {
        final double imageHolderH = imageHolder.getHeight();
        final double viewportH = scrollPane.getViewportBounds().getHeight();
        final double checkLinePos = viewportH / 2;
        final double calcH = imageHolderH - viewportH;
        final double testY = checkLinePos + calcH * value; // under middle of viewport

        for (int i = 0; i < vbox.getChildren().size(); i++) {
            final Node node = vbox.getChildren().get(i);
            final double nodeY = node.getLayoutY();
            final double nodeH = ((ImageView) node).getFitHeight() + vbox.getSpacing();
            if (testY >= nodeY && testY <= (nodeY + nodeH)) return i + 1;
        }
        return 1;
    }

    private ImageView scaleImageView(ImageView imageView) {
        imageView.setFitHeight(originalPageHeight * zoomFactor);
        imageView.setFitWidth(originalPageWidth * zoomFactor);
        return imageView;
    }

    /**
     * Load report and create a list of images for pages
     */
    public void loadPages() {
        final int pagesCount = jasperPrint.getPages().size();
        for (int i = 0; i < pagesCount; i++) {
            final Image image = getImage(jasperPrint, i);
            pages.add(scaleImageView(new ImageView(image)));
        }
        loadingIcon.setVisible(false);
    }

    /**
     * Put images list into container on GUI
     */
    public void displayPages() {
        vbox.getChildren().setAll(pages);
        //view.setDisable(false);
        disableNextBtns(pages.size() <= 1);
    }


    public void initialize() {
    	pageList.setOnAction( _ -> {
    		pageListSelected();
    	});
    	
        btnPrevPage.setOnAction( e-> {
        	goPrevPage(e);
        });
        
        btnNextPage.setOnAction( e-> {
        	goNextPage(e);
        });

    	btnClose.setOnAction( e-> {
    		((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
    	});
    	
    	btnPrint.setOnAction( _ -> {
    		print();
    		btnPrint.setDisable(true);
    		Common.ShowNotification("ŠTAMPA IZVEŠTAJA", "Izveštaj je poslat na štampač.", false);
    		Common.delay(5000, () -> {
    			btnPrint.setDisable(false);	
    		});
    	});
    	
        //view.setDisable(true);
        view.disableProperty().addListener((_, _, newValue) -> { //observable, oldValue, newValue
            if (!newValue) scrollPane.requestFocus();
        });

        imageHolder.heightProperty()
                .addListener((_, _, _) -> scrollPane.setVvalue(vvalue));

        imageHolder.setOnMousePressed(_ -> imageHolder.setCursor(Cursor.CLOSED_HAND));
        imageHolder.setOnDragDetected(_ -> imageHolder.setCursor(Cursor.CLOSED_HAND));
        imageHolder.setOnMouseReleased(_ -> imageHolder.setCursor(Cursor.OPEN_HAND));

        zoomLevel.valueProperty().addListener((_, _, newValue) -> {
            zoomFactor = newValue.doubleValue() / 100;
            vvalue = scrollPane.getVvalue();

            vbox.getChildren().forEach(node -> scaleImageView((ImageView) node));
        });

        scrollPane.vvalueProperty().addListener((_, _, newValue) -> {
            final int value = scrollValueToPage(newValue.doubleValue());
            final EventHandler<ActionEvent> onAction = pageList.getOnAction();
            pageList.setOnAction(null);
            pageList.setValue(value);
            pageList.setOnAction(onAction);
            disablePrevBtns(1 == value);
            disableNextBtns(pages.size() == value);
        });

        printService = new PrintService();
        //printService.onDialogClosed(event -> view.setDisable(false));
        //printService.onDialogError(event -> System.err.println("Could not print report"));
        
    } // initialize END

    private void disablePrevBtns(boolean disable) {
        firstPageBtn.setDisable(disable);
        btnPrevPage.setDisable(disable);
    }

    private void disableNextBtns(boolean disable) {
        btnNextPage.setDisable(disable);
        lastPageBtn.setDisable(disable);
    }

    public void init() {
        originalPageHeight = jasperPrint.getPageHeight() * JASPER_TO_SCREEN_DPI_FIX;
        originalPageWidth = jasperPrint.getPageWidth() * JASPER_TO_SCREEN_DPI_FIX;

        final List<Integer> pages = new ArrayList<>();
        final int pagesCount = jasperPrint.getPages().size();
        for (int i = 0; i < pagesCount; ) pages.add(++i);
        pageList.setItems(FXCollections.observableArrayList(pages));
        pageList.getSelectionModel().select(0);
    }

    @FXML
    private void onScrollKeyPressed(KeyEvent event) {
        Integer mod = null;
        if (KeyCode.PAGE_UP.equals(event.getCode())) mod = -1;
        if (KeyCode.PAGE_DOWN.equals(event.getCode())) mod = 1;

        if (null != mod) {
            final double contentH = imageHolder.getHeight();
            final double viewportH = scrollPane.getViewportBounds().getHeight();
            final double vvalue = scrollPane.getVvalue();
            scrollPane.setVvalue(vvalue + mod * (viewportH / contentH));
            event.consume();
        }
    }

    @FXML
    private void save() {
    	extensionFilters = new ArrayList<>();
    	extensionFilters.add(new ExtensionFilter("PDF", "*.pdf"));
    	
        FileChooser fileChooser = new FileChooser();
        File directory = new File(null != initialDirectory ? initialDirectory : "");
        if (!directory.exists()) directory = new File("");
        fileChooser.setInitialDirectory(directory);
        fileChooser.setInitialFileName(initialFileName);
        fileChooser.setTitle("save.file.title");
        fileChooser.getExtensionFilters().setAll(extensionFilters);

        File file = fileChooser.showSaveDialog(view.getScene().getWindow());
        if (null == file) return;

        ExtensionFilter selectedExtensionFilter = fileChooser.getSelectedExtensionFilter();
        if (null != selectedExtensionFilter) {

            // Windows XP does not append extension to file name
            final String ext = selectedExtensionFilter.getExtensions().get(0)
                    .replace("*", "").toLowerCase();
            final String absolutePath = file.getAbsolutePath();
            if (!absolutePath.toLowerCase().endsWith(ext)) file = new File(absolutePath + ext);

            try {
                if (selectedExtensionFilter.getExtensions().contains("*.pdf")) {
                    savePdf(jasperPrint, file);
                } else if (selectedExtensionFilter.getExtensions().contains("*.png")) {
                    savePng(jasperPrint, file);
                } else if (selectedExtensionFilter.getExtensions().contains("*.html")) {
                    saveHtml(jasperPrint, file);
                } else if (selectedExtensionFilter.getExtensions().contains("*.docx")) {
                    saveDocx(jasperPrint, file);
                } else if (selectedExtensionFilter.getExtensions().contains("*.xlsx")) {
                    saveXlsx(jasperPrint, file);
                }
            } catch (Exception e) {
    			Common.logMessage(getClass(), e, "ERROR");
            }
        }
    }

    //@FXML
    private void print() {
        //view.setDisable(true);
        printService.showPrintDialog(jasperPrint);
    }

    //@FXML
    private void pageListSelected() {
        final int pageNumber = pageList.getSelectionModel().getSelectedItem() - 1;
        scrollPane.setVvalue(pageToScrollValue(pageNumber));
    }

    @FXML
    public void goFirstPage(ActionEvent actionEvent) {
        pageList.getSelectionModel().selectFirst();
    }

    //@FXML
    public void goPrevPage(ActionEvent actionEvent) {
        pageList.getSelectionModel().selectPrevious();
    }

    //@FXML
    public void goNextPage(ActionEvent actionEvent) {
        pageList.getSelectionModel().selectNext();
    }

    @FXML
    public void goLastPage(ActionEvent actionEvent) {
        pageList.getSelectionModel().selectLast();
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public void setInitialDirectory(String initialDirectory) {
        this.initialDirectory = initialDirectory;
    }

    public void setInitialFileName(String initialFileName) {
        this.initialFileName = initialFileName;
    }

    public void setExtensionFilters(List<ExtensionFilter> extensionFilters) {
        this.extensionFilters = extensionFilters;
    }
	
	

	/*********************************************
	 * 
	 */
	private void exportReport(JasperPrint jasperPrint, File file,
			JRAbstractExporter<?, ?, OutputStreamExporterOutput, ?> exporter) throws JRException {
		ExporterInput inp = new SimpleExporterInput(jasperPrint);
		exporter.setExporterInput(inp);

		OutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(file);
		exporter.setExporterOutput(output);

		exporter.exportReport();
	}

	public void saveXlsx(JasperPrint jasperPrint, File file) throws JRException {
		JRAbstractExporter<?, ?, OutputStreamExporterOutput, ?> exporter = new JRXlsxExporter();
		exportReport(jasperPrint, file, exporter);
	}

	public void saveDocx(JasperPrint jasperPrint, File file) throws JRException {
		JRAbstractExporter<?, ?, OutputStreamExporterOutput, ?> exporter = new JRDocxExporter();
		exportReport(jasperPrint, file, exporter);
	}

	public void saveHtml(JasperPrint jasperPrint, File file) throws JRException {
		JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getAbsolutePath());
	}

	public void savePng(JasperPrint jasperPrint, File file) throws IOException {
		for (int i = 0; i < jasperPrint.getPages().size(); i++) {
			String fileNumber = "0000" + Integer.toString(i + 1);
			fileNumber = fileNumber.substring(fileNumber.length() - 4, fileNumber.length());
			WritableImage image = getImage(jasperPrint, i);
			String[] fileTokens = file.getAbsolutePath().split("\\.");
			String filename = "";

			// add number to filename
			if (fileTokens.length > 0) {
				for (int i2 = 0; i2 < fileTokens.length - 1; i2++) {
					filename = filename + fileTokens[i2] + ((i2 < fileTokens.length - 2) ? "." : "");
				}
				filename = filename + fileNumber + "." + fileTokens[fileTokens.length - 1];
			} else {
				filename = file.getAbsolutePath() + fileNumber;
			}
			File imageFile = new File(filename);
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
		}
	}

	public void savePdf(JasperPrint jasperPrint, File file) throws JRException {
		JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
	}
	
    public WritableImage getImage(JasperPrint jasperPrint, int pageNumber) {
        BufferedImage image;
        try {
            image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageNumber, 2);
        } catch (Exception e) {
			Common.logMessage(getClass(), e, "ERROR");
            throw new RuntimeException(e);
        }
        WritableImage fxImage = new WritableImage(jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
        return SwingFXUtils.toFXImage(image, fxImage);
    }
    
    /**********************
     * 
     */
    public class PrintService extends Service<Boolean> {
        private JasperPrint jasperPrint;

        @Override
        protected Task<Boolean> createTask() {
            return new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    try {
                    	
                    	//Set the printing settings
                    	PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                    	printRequestAttributeSet.add(mediaSizeName);
                    	printRequestAttributeSet.add(new Copies(numberOfCopies));
                    	printRequestAttributeSet.add(chromaticity);

                    	SimplePrintServiceExporterConfiguration psConfiguration = new SimplePrintServiceExporterConfiguration();
                    	psConfiguration.setPrintRequestAttributeSet(printRequestAttributeSet);
                    	psConfiguration.setDisplayPageDialog(false);
                    	psConfiguration.setDisplayPrintDialog(false);
                    	psConfiguration.setPrintService(PrintServiceLookup.lookupDefaultPrintService());

                    	JRPrintServiceExporter printServiceExporter = new JRPrintServiceExporter();
                    	printServiceExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    	printServiceExporter.setConfiguration(psConfiguration);

                    	printServiceExporter.exportReport();
                    	
                        return true; 
                    } catch (Exception e) {
            			Common.logMessage(getClass(), e, "ERROR");
                        return false;
                    }
                }
            };
        }

        public void showPrintDialog(JasperPrint jasperPrint) {
            this.jasperPrint = jasperPrint;
            setOnSucceeded(event -> {
                final Object result = event.getSource().getValue();
                if (null == result || !(boolean) result) {
                    //fireEvent(new JRViewerPrintEvent(JR_PRINT_DLG_ERROR));
                	System.err.println("JR_PRINT_DLG_ERROR");
                }
                //fireEvent(new JRViewerPrintEvent(JR_PRINT_DLG_CLOSED));
                // System.out.println("JR_PRINT_DLG_CLOSED"); legacy kod - ovo mi nista ne znaci
            });
            restart();
        }
        
    }

}
