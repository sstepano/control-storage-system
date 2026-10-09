package org.code_studio.controller;

import org.code_studio.database.CraneQueue;
import org.code_studio.model.CraneQueueModel;
import org.code_studio.model.StoredProcedureModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.craneQueue}")
public class CraneQueueController extends BaseController <CraneQueue> {

	@Autowired
	CraneQueueModel model;
	
	@Autowired
	StoredProcedureModel spModel;
	
	public CraneQueueController (CraneQueueModel model) {
		super(model);
		this.model = model;
	}
	

	@GetMapping("allInputByClientId/{clientId}")
	public ResponseEntity <Object> allInputByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.allInputOutputByClientId(clientId, 0);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allOutputByClientId/{clientId}")
	public ResponseEntity <Object> allOutputByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.allInputOutputByClientId(clientId, 1);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	/**
	 * 
	 * @param clientId
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
	public ResponseEntity <Object> updatePaletteStatusAndCraneQueue (
		@PathVariable String  paletteRMCoords, 
		@PathVariable String  source,
		@PathVariable String  target,
		@PathVariable String  currentPosition,
		@PathVariable String  nextPosition,
		@PathVariable String  lastCraneMessageStatus
	) {
		try {
			responseData = spModel.call_SP_CRANE_QUEUE_UPDATE (
				paletteRMCoords, 
				source,
				target,
				currentPosition,
				nextPosition,
				lastCraneMessageStatus
			);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	
}
