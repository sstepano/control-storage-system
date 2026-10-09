package org.code_studio.controller;

import java.util.ArrayList;
import java.util.List;

import org.code_studio.Common;
import org.code_studio.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiServerController extends BaseController<String> {

	
	@Value("${dataserver.address}")
	String dataServerAddress;
	
    @Autowired
    WebSocketController webSocketController;

    ApiServerController() {}
    
    /**
     * Is palette waiting for storage, on position 1003?
     * @return
     */
	@GetMapping("/isPaletteWaiting")
	public boolean isPaletteWaiting () {
		return Boolean.valueOf(Common.applicationProperties.getProperty("crane.palette.iswaiting"));
	}
    
	
	@GetMapping("/nextPaletteForStorage")
	public List<String> nextPaletteForStorage () {
		try {
			    final String uri = dataServerAddress + "/palette/nextPaletteForStorage";
			    RestTemplate restTemplate = new RestTemplate();
			    
			    @SuppressWarnings("unchecked")
				JsonResponse<String> responseData = restTemplate.getForObject(uri, JsonResponse.class);
 
			    return responseData.getData();
			    
		    } catch (Exception exception) {
			return new ArrayList<String>();
		}
		
	}
	
	/**
	 * Gets next palette that is prepared for exiting the warehouse
	 * @return
	 */
	@GetMapping("/nextPaletteForOutput")
	public List<String> nextPaletteForOutput () {
		try {
				final String uri = dataServerAddress + "/palette/nextPaletteForOutput";
			    RestTemplate restTemplate = new RestTemplate();
			    
			    @SuppressWarnings("unchecked")
				JsonResponse<String> responseData = restTemplate.getForObject(uri, JsonResponse.class);
 
			    return responseData.getData();
			    
		    } catch (Exception exception) {
			return new ArrayList<String>();
		}
		
	}
	
	@Deprecated
	@GetMapping("/updatePaletteStatus/{paletteRMCoords}/{statusId}")
	public List<String> updatePaletteStatus (@PathVariable String paletteRMCoords, @PathVariable Integer statusId) {
		try {
			final String uri = dataServerAddress + "/palette/updateStatus/" + paletteRMCoords + "/" + statusId;
			    RestTemplate restTemplate = new RestTemplate();
			    
			    @SuppressWarnings("unchecked")
				JsonResponse<String> responseData = restTemplate.getForObject(uri, JsonResponse.class);
			    return responseData.getData();
			    
		    } catch (Exception exception) {
		    	Common.log(getClass(), exception, "ERROR");
		    	return new ArrayList<String>();
		    }
	}
	
	
	/**
	 * @param paletteRMCoords
	 * @param craneId
	 * @param clientId
	 * @param source
	 * @param target
	 * @param currentPosition
	 * @param nextPosition
	 * @param lastCraneMessageStatus
     * @return
	 */
	@GetMapping(
		"/updatePaletteStatusAndCraneQueue"
	  + "/{paletteRMCoords}"
	  + "/{source}"
	  + "/{target}"
	  + "/{currentPosition}"
	  + "/{nextPosition}"
	  + "/{lastCraneMessageStatus}"
	)
	public List<Object> updatePaletteStatusAndCraneQueue (
		@PathVariable String  paletteRMCoords, 
		@PathVariable String  source,
		@PathVariable String  target,
		@PathVariable String  currentPosition,
		@PathVariable String  nextPosition,
		@PathVariable String  lastCraneMessageStatus
	) {
		try {
				final String uri = 
					dataServerAddress 
				  + "/craneQueue/updatePaletteStatusAndCraneQueue/"
				  + paletteRMCoords + "/" 
				  + source + "/"
				  + target + "/"
				  + currentPosition + "/"
				  + nextPosition + "/"
				  + lastCraneMessageStatus
				  ;
			    RestTemplate restTemplate = new RestTemplate();
			    
			    @SuppressWarnings("unchecked")
				JsonResponse<Object> responseData = restTemplate.getForObject(uri, JsonResponse.class);
			    return responseData.getData();
			    
		    } catch (Exception exception) {
		    	Common.log(getClass(), exception, "ERROR");
		    	return new ArrayList<Object>();
		    }
		
	}
	
	
	
	/**
	 * We call this one when we detect barcode data event
	 * It calls SP which creates new DOCUMENT and PALETTE
	 * based on data received from barcode reader on COM port
	 * @param clientId
	 * @param craneRMCoordinates
	 * @return
	 */
	@GetMapping("/barcodeEntryEvent/{clientId}/{craneRMCoordinates}")
	public List<Object> barcodeEntryEvent (@PathVariable Integer clientId, @PathVariable String craneRMCoordinates) {
		try {

			final String uri = 
				dataServerAddress 
				+ "/palette/barcodeEntryEvent"
				+ "/" + clientId
				+ "/" + craneRMCoordinates
				;
	
		    RestTemplate restTemplate = new RestTemplate();
			    
		    @SuppressWarnings("unchecked")
			JsonResponse<Object> responseData = restTemplate.getForObject(uri, JsonResponse.class);
		    return responseData.getData();
		    
	    } catch (Exception exception) {
	    	Common.log(getClass(), exception, "ERROR");
	    	return new ArrayList<Object>();
	    }
		
	}
	
	
	/**
	 * Used in warehouse.js
	 * @param message
	 */
	@GetMapping("/sendStatusMessage/{message}")
	public void sendStatusMessage(@PathVariable String message) {
	    webSocketController.sendStatusMessage(message);
	    Common.log(getClass(), "<tcpclient><send> " + "sendStatusMessage: " + message);
	}


}
