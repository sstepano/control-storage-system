package org.code_studio.component;

import java.util.List;

import org.code_studio.controller.BaseController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CSPhotoViewDetail extends BaseController {

	@FXML private TextArea txtaNote;
	@FXML private ImageView imageView;
	@FXML private Label lblHeader;

	private Image image;
	private String imagePath;
	
	public CSPhotoViewDetail() {}

	@SuppressWarnings("unchecked")
	public CSPhotoViewDetail (Object controllerParam) {
		List<Object> lstControllerParams = (List<Object>) controllerParam;
		image = (Image) lstControllerParams.get(0);
		imagePath = (String) lstControllerParams.get(1);
	}

	@FXML public void initialize() {
	    imageView.setFitWidth(640);
	    imageView.setFitHeight(480);
	    imageView.setPreserveRatio(true);
	    imageView.setImage(image);
	    lblHeader.setText(imagePath);
	}

}
