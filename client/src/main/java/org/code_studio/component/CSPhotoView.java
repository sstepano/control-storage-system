package org.code_studio.component;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class CSPhotoView extends AnchorPane {

	private @FXML ImageView imageView;
	private @FXML Button btnView;
	private @FXML VBox vbImageContainer;
	
	private int mode;
	private Boolean showMissingItemImageError = false;
	private Boolean logMissingItemImageError = false;

	private InputStream inputStream;
	private FileInputStream fileInputStream;
	private Image image;
	private String baseImagePath;
	private String imagePath;
	List<Object> lstControllerParams;
	
	private CSRestService<String> rsImage;
	private final String urlImage = "/item/itemImageByItemId/0";

	public CSPhotoView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSPhotoView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        showMissingItemImageError = Boolean.valueOf(Common.applicationProperties.getProperty("client.showMissingItemImageError"));
        logMissingItemImageError = Boolean.valueOf(Common.applicationProperties.getProperty("client.logMissingItemImageError"));

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        loadNoImage();
	}

	/**
	 * 
	 * @return
	 */
	public int getMode() {
		return mode;
	}
	
	/**
	 * Ovde sam morao da stavim kod koji bi inace isao u initialize, ali posto se setMode postavlja tek nakon initialize, ove varijable bi imale pogresnu putanju
	 * @param mode
	 */
	public void setMode(int mode) {
		this.mode = mode;
		
		if (mode == 0) {
			baseImagePath = Common.applicationProperties.getProperty("client.itemImagePath");
		} else if (mode == 1) {
			baseImagePath = Common.applicationProperties.getProperty("client.clientStoreImagePath");
		} else if (mode == 2) { // prikazujemo neku sliku iz sistema koju je user odabra, sto znaci da nema base path, vec image path sadrzi celu putanju
			baseImagePath = "";
		}
		
		
        lstControllerParams = new ArrayList<>();

		btnView.setOnAction( _ -> {
			lstControllerParams.clear();
	        lstControllerParams.add(image);
	        lstControllerParams.add(baseImagePath + imagePath);
			Common.displayForm(ControllerFactory.getController("CSPhotoViewDetail", lstControllerParams, 0), this.getParent(), "FOTO DETALJI");
		});

	}
	
	public void loadNoImage() {
	    try {
			inputStream = new ClassPathResource("no-image.jpg").getInputStream();
		    image = new Image(inputStream);
		    imageView.setImage(image);
		    inputStream.close();
		    btnView.setDisable(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Loads image from the absolute path
	 * TODO: Deluje mi neefikasno ovo pravljenje objekata svaki put kada pozovem metodu
	 * Probati da napravim da se jednom samo naprave inputstream i image
	 */
	public void loadImage(String imagePath) {
		try {
			clearImage();
		    fileInputStream = new FileInputStream(baseImagePath + imagePath);
		    image = new Image(fileInputStream);
		    //imageView.setFitWidth(150);
		    //imageView.setFitHeight(200);
		    //imageView.setPreserveRatio(true);
		    imageView.setImage(image);
		    fileInputStream.close();

	        if (image != null) {
	        	btnView.setDisable(false);
	        } else {
	        	btnView.setDisable(true);
	        }
		} catch (IOException ex) {
			if (showMissingItemImageError) {
				Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja slike artikla.\nSlika je definisana, ali fajl sa tom putanjom ne postoji u sistemu:\n"
					+ imagePath, true);
			}
			
			if (logMissingItemImageError) {
				Common.logMessage(getClass(), ex, "ERROR");
			}
		} finally {
			// TODO: Still needs fine tuning ... ask server for this image and save it to images folder.
			if (image == null && imagePath != null) {
				rsImage = new CSRestService<>(urlImage);
				rsImage.fetch(new ParameterizedTypeReference<JsonResponse<String>>() {});
				List<String> lstImage = rsImage.getData();

				// Ako ima slike na serveru
				if (lstImage != null) {
					byte[] imageBytes = Base64.getDecoder().decode(lstImage.get(0));
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
					image = new Image(byteArrayInputStream);
					
				    imageView.setFitWidth(150);
				    imageView.setFitHeight(200);
				    imageView.setPreserveRatio(true);
					imageView.setImage(image);
				} else {
					loadNoImage();
				}
			}
		}
	}

	/*
	 * Clears already loaded image from the control
	 */
	public void clearImage() {
		try {
			if (fileInputStream != null) {
				fileInputStream.close();
				fileInputStream = null;
			}
			image = null;
		} catch (IOException ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja slike artikla.", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}

	//SETMODE SE POOZIVA TEK NAKON INITIALIZE< STO OVDE ONDA MODE NEMA PRAVU VREDNOST
	@FXML public void initialize() {
		setMode(0); // ako ovo ne stavim, za item-e nece da radi
	    imageView.setFitWidth(150);
	    imageView.setFitHeight(200);
	    imageView.setPreserveRatio(true);
	}

	
	/*
	 * Used to set and get image path for the control to display
	 */
	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
		if (imagePath != null) { // ako uopste ima slike za artikal
			loadImage(imagePath);
		} else {
			loadNoImage();
		}
	}
	
	public void setMaxAspectRatio() {
		imageView.setFitWidth(vbImageContainer.getWidth());
		imageView.setFitHeight(vbImageContainer.getHeight());
		imageView.setPreserveRatio(true);
	}

}
