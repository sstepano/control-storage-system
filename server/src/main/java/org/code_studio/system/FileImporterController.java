package org.code_studio.system;

import org.code_studio.controller.BaseController;
import org.code_studio.database.StoredProcedureResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${url.fileImporter}")
public class FileImporterController extends BaseController <StoredProcedureResult> {
	
	@Value("${fileImporterLoadFolder}")
	private String fileImporterLoadFolder;
	
	@Autowired
	ApplicationContext ctx;
	
	@Autowired
	GigatronOrderFile gigatronOrderFile;
	
	@Autowired
	TehnomanijaOrderFile tehnomanijaOrderFile;
	
	@Autowired
	TehnomediaOrderFile tehnomediaOrderFile;
	
	@Autowired
	TelekomOrderFile telekomOrderFile;
	
	public FileImporterController () {
		super(null);
	}
	
	@PostMapping({"import/{fileTemplateId}", "import/{fileTemplateId}/{clientId}"})
	public ResponseEntity <Object> importFile (
			@PathVariable Integer fileTemplateId, 
			@PathVariable (required=false) Integer clientId, 
			@RequestParam("file") MultipartFile file) {

		switch(fileTemplateId) {
			case 1: // GIGATRON MASS CLIENT ORDER
			    try {
			    	responseData = gigatronOrderFile.loadFileAndSaveToDatabase(file);
			    } catch (Exception ex) {
			    	return generateResponse(ex);
			    }
			break;
			case 2: // TEHNOMANIJA MASS CLIENT ORDER
			    try {
			    	tehnomanijaOrderFile.setClientId(clientId);
			    	responseData = tehnomanijaOrderFile.loadFileAndSaveToDatabase(file);
			    } catch (Exception ex) {
			    	return generateResponse(ex);
			    }
			break;
			case 3: // TEHNOMEDIA MASS CLIENT ORDER
			    try {
			    	responseData = tehnomediaOrderFile.loadFileAndSaveToDatabase(file);
			    } catch (Exception ex) {
			    	return generateResponse(ex);
			    }
			break;
			case 4: // TELEKOM MASS CLIENT ORDER
			    try {
			    	responseData = telekomOrderFile.loadFileAndSaveToDatabase(file);
			    } catch (Exception ex) {
			    	return generateResponse(ex);
			    }
			break;
			default:
			break;
		}
		return generateResponse(responseData);
		
	}
}
