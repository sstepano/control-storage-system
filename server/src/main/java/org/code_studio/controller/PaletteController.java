package org.code_studio.controller;

import java.util.ArrayList;
import java.util.List;
import org.code_studio.database.Palette;
import org.code_studio.database.StoredProcedureResult;
import org.code_studio.model.PaletteDocumentModel;
import org.code_studio.model.PaletteModel;
import org.code_studio.model.PaletteModelPageable;
import org.code_studio.model.StoredProcedureModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.palette}")
@CrossOrigin // dozvoljavamo da CRANE pristupi metodama ovog kontrolera
public class PaletteController extends BaseController <Palette> {

	
	@Autowired
	ApplicationContext ctx;

	enum CRANEFILLORDER {
		  ROW_START_TO_MIDDLE //from row ends toward middle of the warehouse
		, ROW_END_TO_MIDDLE //from row ends toward middle of the warehouse. Not yet implemented!
		, ROW_MIDDLE_TO_END //from row middle of the warehouse toward the ends 
	};
	
	enum CRANE {
		  ALL    //0 -> both cranes
		, CRANE1 //1 -> first crane
		, CRANE2 //2 -> second crane
	};
	
	enum CRANEFILLORDINAL {
		SEQUENTIAL // take all palettes for the first crane, then go to palettes for the crane 2
	  , ALTERNATE // take one for crane 1, then another one for crane 2, then go back to crane 1, crane 2 etc.
	}
	
	// ALTERNATE je default ako nije definisano u server.properties
	@Value("${craneFillOrdinal:SEQUENTIAL}")
	CRANEFILLORDINAL craneFillOrdinal;

	@Value("${craneFillOrder:ROW_START_TO_MIDDLE}")
	CRANEFILLORDER craneFillOrder;
	
	@Value("${maxShelf:1}")
	Integer maxShelfId;
	
	@Value("${maxVertical:1}")
	Integer maxVerticalId;
	
	@Value("${crane1StartRow:1}")
	Integer crane1StartRowId;
	
	@Value("${crane1EndRow:1}")
	Integer crane1EndRowId;
	
	@Value("${crane2StartRow:1}")
	Integer crane2StartRowId;
	
	@Value("${crane2EndRow:1}")
	Integer crane2EndRowId;
	
	@Value("${server.pageSize}")
	private int pageSize;
	
	@Value("${craneserver.address}")
	private String craneServerAddress;

	@Autowired
	PaletteModel model;

	@Autowired
	PaletteDocumentModel documentModel;

	
	@Autowired
	PaletteModelPageable modelPageable;
	
	@Autowired
	StoredProcedureModel spModel;
	
	
	public PaletteController (PaletteModel model) {
		super(model);
		this.model = model;
	}
	
	
	@GetMapping("allPageable/{pageId}")
	public ResponseEntity <Object> allPageable (@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageable(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByDocumentId/{documentId}/{pageId}")
	public ResponseEntity <Object> allPageableByDocumentId (@PathVariable Integer documentId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByDocumentId(documentId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> allPageableByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByClientId(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("storedPaletteCountByClientId/{clientId}")
	public ResponseEntity <Object> storedPaletteCountByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.storedPaletteCountByClientId(clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableStoredByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> allPageableStoredByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableStoredByClientId(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	
	/**
	 * Gets number of free spaces for storage per crane. 0-both, 1-first, 2-second
	 */
	@Deprecated
	@GetMapping("unoccupiedPalettePositionsByCrane/{craneId}")
	public ResponseEntity <Object> unoccupiedPalettePositionsByCrane (@PathVariable Integer craneId) {
		/*
		final CRANEFILLORDINAL craneFillOrdinal = this.craneFillOrdinal != null ? this.craneFillOrdinal : CRANEFILLORDINAL.SEQUENTIAL;
		final CRANEFILLTYPE craneFillType = this.craneFillType != null ? this.craneFillType : CRANEFILLTYPE.ROW_START_TO_MIDDLE;
		//final Integer maxRowId       = 8;  // 1-8
		final Integer maxShelfId       = this.maxShelfId != null ? this.maxShelfId : 1; // 1-54
		final Integer maxVerticalId    = 7;  // 1-7
		final Integer crane1StartRowId = 1;
		final Integer crane1EndRowId   = 4;
		final Integer crane2StartRowId = 5;
		final Integer crane2EndRowId   = 8;  // 5-8
		*/
		
		List<String> finalPaletteMap = new ArrayList<String>();
		List<String> filledPalettes = new ArrayList<String>();

		try {
			responseData = model.storedPalettes();
			@SuppressWarnings("unchecked") 
			List<Palette> lstStoredPalettes = (List<Palette>) responseData;

			String tmpRes;
			List<String> crane1PaletteMap = new ArrayList<String>();
			List<String> crane2PaletteMap = new ArrayList<String>();
			
			//fill all taken palettes
			for (Palette palette : lstStoredPalettes) {
			    int row = Integer.parseInt(palette.getRowId().trim());
			    int shelf = Integer.parseInt(palette.getShelfId().trim());
			    int vert = Integer.parseInt(palette.getVerticalId().trim());
			    String formattedDbKey = String.format("%02d%03d%02d", 
			            row, 
			            shelf, 
			            vert
			        );
				//filledPalettes.add(palette.getRowId() + palette.getShelfId() + palette.getVerticalId());
			    filledPalettes.add(formattedDbKey);
			}
			
			switch (craneFillOrder) {
				case ROW_START_TO_MIDDLE:
					
					//Crane 1
					for (int i=crane1StartRowId; i<=crane1EndRowId; i++) {
						tmpRes = "";
						String tmpI = "";
						String tmpJ = "";
						String tmpK = "";
						
						for (int j=1; j<=maxShelfId; j++) {
							for (int k=1; k<=maxVerticalId; k++) {
								if (i <= 9) {
									tmpI = "0" + i;
								}
								if (j>=10 && j < 100) {
									tmpJ = "0" + j;
								} else if (j <= 9) {
									tmpJ = "00" + j;
								}
								if (k <= 9) {
									tmpK = "0" + k;
								}
								tmpRes = tmpI + tmpJ + tmpK;
								crane1PaletteMap.add(tmpRes);
							}
						}
					}
						
						//Crane 2
						for (int i = crane2StartRowId; i <= crane2EndRowId; i++) {
							tmpRes = "";
							String tmpI   = "";
							String tmpJ   = "";
							String tmpK   = "";
							
							for (int j=1; j<=maxShelfId; j++) {
								for (int k=1; k<=maxVerticalId; k++) {
									if (i <= 9) {
										tmpI = "0" + i;
									}
									if (j>=10 && j < 100) {
										tmpJ = "0" + j;
									} else if (j <= 9) {
										tmpJ = "00" + j;
									}
									if (k <= 9) {
										tmpK = "0" + k;
									}
									tmpRes = tmpI + tmpJ + tmpK;
									crane2PaletteMap.add(tmpRes);
								}
							}
						}
				break;
				case ROW_MIDDLE_TO_END:
					
					//Crane 1
					for (int i=crane1EndRowId; i>=crane1StartRowId; i--) {
						tmpRes = "";
						String tmpI = "";
						String tmpJ = "";
						String tmpK = "";
						
						for (int j=1; j<=maxShelfId; j++) {
							for (int k=1; k<=maxVerticalId; k++) {
								if (i <= 9) {
									tmpI = "0" + i;
								}
								if (j>=10 && j < 100) {
									tmpJ = "0" + j;
								} else if (j <= 9) {
									tmpJ = "00" + j;
								}
								if (k <= 9) {
									tmpK = "0" + k;
								}
								tmpRes = tmpI + tmpJ + tmpK;
								crane1PaletteMap.add(tmpRes);
							}
						}
					}
						
					//Crane 2
					for (int i=crane2EndRowId; i>=crane2StartRowId; i--) {
						tmpRes = "";
						String tmpI = "";
						String tmpJ = "";
						String tmpK = "";
						
						for (int j=1; j<=maxShelfId; j++) {
							for (int k=1; k<=maxVerticalId; k++) {
								if (i <= 9) {
									tmpI = "0" + i;
								}
								if (j>=10 && j < 100) {
									tmpJ = "0" + j;
								} else if (j <= 9) {
									tmpJ = "00" + j;
								}
								if (k <= 9) {
									tmpK = "0" + k;
								}
								tmpRes = tmpI + tmpJ + tmpK;
								crane2PaletteMap.add(tmpRes);
							}
						}
					}
				break;
				default:
					throw new java.lang.NoSuchMethodError("CRANE FILL TYPE Not implemented!");
			}
			
			//remove all already taken, we will get just free spaces here
			crane1PaletteMap.removeAll(filledPalettes);
			crane2PaletteMap.removeAll(filledPalettes);
			
			// How we send the palettes to the cranes. All at once to one crane, then to another one when first completes.
			// Or alternate, one palette to crane 1, second palette to crane 2
			switch (craneFillOrdinal) {
				case SEQUENTIAL:
					finalPaletteMap.addAll(crane1PaletteMap);
					finalPaletteMap.addAll(crane2PaletteMap);
				break;
				case ALTERNATE:
					int mapSize = crane1PaletteMap.size() > crane2PaletteMap.size() ? crane1PaletteMap.size() : crane2PaletteMap.size();
					for (int i = 0; i < mapSize; i++) {
						if (crane1PaletteMap.size()-1 > i) {
							finalPaletteMap.add(crane1PaletteMap.get(i));							
						}
						if (crane2PaletteMap.size()-1 > i) {
							finalPaletteMap.add(crane2PaletteMap.get(i));							
						}
					}
				break;
				default:
					throw new java.lang.NoSuchMethodError("CRANE FILL ORDINAL Not implemented!");
			}

			/* Call to save the file
			 * We will disable it for now. It creates file with palettes in it.
			
			CraneStorageController ctrl = ctx.getBean(CraneStorageController.class);
			ctrl.paletteStore(finalPaletteMap);
			
			*/

			switch(craneId) {
				case 0:
					return generateResponse(finalPaletteMap);
				case 1:
					return generateResponse(crane1PaletteMap);
				case 2:
					return generateResponse(crane2PaletteMap);
			default:
				throw new IllegalArgumentException("Unexpected value: " + craneId);
			}
			
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	/**
	 * U procesu ulaza
	 * @param clientId
	 * @param pageId
	 * @return
	 */
	@GetMapping("allPageableStoringByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> allPageableStoringByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableStoringByClientId(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allPageableByItemId/{itemId}/{pageId}")
	public ResponseEntity <Object> allPageableByItemId (@PathVariable Integer itemId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByClientId(itemId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableStoredByItemId/{itemId}/{pageId}")
	public ResponseEntity <Object> allPageableStoredByItemId (@PathVariable Integer itemId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableStoredByItemId(itemId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}	
	
	@GetMapping("allPageableByDescription/{description}/{pageId}")
	public ResponseEntity <Object> allPageableByDescription (@PathVariable String description, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableStoredByDescription(description, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	

	/**
	 * Called from CRANE, updates occupancy of palette storage
	 * @param clientId
	 * @return
	 */
	@GetMapping("setOccupied/{paletteRMCoords}")
	public ResponseEntity <Object> setOccupied (@PathVariable String paletteRMCoords) {
		try {
			responseData = spModel.call_SPPaletteStatusUpdate(paletteRMCoords, 1);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/**
	 * Called from CRANE, updates occupancy of palette storage
	 * @param clientId
	 * @return
	 */
	@GetMapping("setFree/{paletteRMCoords}")
	public ResponseEntity <Object> setFree (@PathVariable String paletteRMCoords) {
		try {
			responseData = spModel.call_SPPaletteStatusUpdate(paletteRMCoords, 2);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/**
	 * Called from CRANE, updates occupancy of palette storage
	 * @param clientId
	 * @return
	 */
	@GetMapping("setLocked/{paletteRMCoords}")
	public ResponseEntity <Object> setLocked (@PathVariable String paletteRMCoords) {
		try {
			responseData = spModel.call_SPPaletteStatusUpdate(paletteRMCoords, 3);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}	
	
	/**
	 * Called from CRANE, updates occupancy of palette storage
	 * @param clientId
	 * @return
	 */
	@SuppressWarnings("unused")
	@GetMapping("store/{paletteRMCoords}")
	public ResponseEntity <Object> store (@PathVariable String paletteRMCoords) {
		try {
			
			//mark document as waiting as next for input
		    responseData = spModel.call_SPStorePalette(paletteRMCoords);

		    /** 
		     * CHECK IF PALETTE IS ALREADY IN 1003 AND ONLY CALL CRANE INPUT
		     */
		    final String uriIsPaletteWaiting = craneServerAddress + "/isPaletteWaiting";
		    RestTemplate restTemplateIsPaletteWaiting = new RestTemplate();
		    Boolean resIsPaletteWaiting = restTemplateIsPaletteWaiting.getForObject(uriIsPaletteWaiting, Boolean.class);
		    
		    if (resIsPaletteWaiting) {
			    final String uri = craneServerAddress + "/sendMessage/I" + paletteRMCoords + "OK";
			    RestTemplate restTemplate = new RestTemplate();
			    Object result = restTemplate.getForObject(uri, Object.class);
		    }

			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("fetch/{paletteRMCoords}")
	public ResponseEntity <Object> fetch (@PathVariable String paletteRMCoords) {
		try {
			// call crane sendMessage OUTPUT
		    final String uri = craneServerAddress + "/sendMessage/O" + paletteRMCoords + "OK";
		    RestTemplate restTemplate = new RestTemplate();
		    Object _ = restTemplate.getForObject(uri, Object.class);
		    
		    //TODO: Proveriti da li je potrebno???
		    //responseData = spModel.call_SPPaletteStatusUpdate(paletteRMCoords, 5);
		    //HACK
		    List<Integer> res = new ArrayList<>();
		    res.add(0);
		    
			return generateResponse(res);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allOccupiedByRowId/{rowId}")
	public ResponseEntity <Object> allOccupiedByRowId (@PathVariable Integer rowId) {
		try {
		    
		    responseData = model.findAllOccupiedByRowId(rowId);
		    
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	/**
	 * Returns List<Integer>, where first item is total count, second is stored count, 3rd is free palette count
	 * @param rowId
	 * @return
	 */
	@GetMapping("countTotalStoredFreeByRowId/{rowId}")
	public ResponseEntity <Object> countTotalStoredFreeByRowId (@PathVariable Integer rowId) {
		try {
		    responseData = model.findCountTotalStoredFreeByRowId(rowId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	/**
	 * Gets next prepared palette to insert into warehouse
	 * @param rowId
	 * @return
	 */
	@CrossOrigin
	@GetMapping("nextPaletteForStorage")
	public ResponseEntity <Object> findNextPaletteForStorage () {
		try {
		    responseData = model.findNextPaletteForStorage();
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	/**
	 * Gets next prepared palette to output from the warehouse
	 * @return
	 */
	@CrossOrigin
	@GetMapping("nextPaletteForOutput")
	public ResponseEntity <Object> findNextPaletteForOutput () {
		try {
		    responseData = model.findNextPaletteForOutput();
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	
	@Deprecated
	@CrossOrigin
	@GetMapping("updateStatus/{paletteRMCoords}/{paletteStatusId}")
	public ResponseEntity <Object> updateStatus (@PathVariable String paletteRMCoords, @PathVariable Integer paletteStatusId) {
		try {
		    responseData = model.updateStatus(paletteRMCoords, paletteStatusId);
		    model.updateDocumentStatus(paletteRMCoords, paletteStatusId); // update doc ako su sve palete smestene
		    //HACK
		    List<Integer> res = new ArrayList<>();
		    res.add((Integer)responseData);
			return generateResponse(res);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	// TODO: DUPLIKAT dohvatanja podataka, imam dva metoda koja isto rade !!! 
	@SuppressWarnings("unchecked")
	@CrossOrigin
	@GetMapping("fetchAllByDocumentId/{documentId}")
	public ResponseEntity <Object> fetchAllByDocumentId (@PathVariable Integer documentId) {
		try {
			List<Palette> lstPalettes;
		    responseData = model.findAllByDocumentId(documentId);
		    lstPalettes = (List<Palette>) responseData;
		    
		    lstPalettes.forEach( el -> {
		    	fetch(el.getPaletteCode());
		    });
		    
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/**
	 * Called from CRANE, updates occupancy of palette storage
	 * @param clientId
	 * @return
	 */
	@SuppressWarnings( {"unchecked", "unused"} )
	@CrossOrigin
	@GetMapping("outputAllByDocumentId/{documentId}")
	public ResponseEntity <Object> outputAllByDocumentId (@PathVariable Integer documentId) {
		try {
			Object response;
			List<Palette> lstPalettes;
			Palette nextPaletteForOutput;
			response = model.findAllByDocumentIdAndStatusId(documentId, 4); // stored
		    lstPalettes = (List<Palette>) response;
		    
		    if (lstPalettes.size() > 0) {
		    	nextPaletteForOutput = lstPalettes.getFirst();

		    	responseData = documentModel.markForOutput(documentId);
		    	
				//call crane sendMessage INPUT
			    final String uri = craneServerAddress + "/sendMessage/O" + nextPaletteForOutput.getPaletteCode() + "OK";
			    RestTemplate restTemplate = new RestTemplate();
			    Object result = restTemplate.getForObject(uri, Object.class);
		    }
		    
		    if (responseData == null) {
		    	responseData = 0;
		    }
		    
	    	List<Integer> res = new ArrayList<>();
		    res.add((Integer)responseData);
		    
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	
	@CrossOrigin
	@GetMapping("inputAllByDocumentId/{documentId}")
	public ResponseEntity <Object> inputAllByDocumentId (@PathVariable Integer documentId) {
		try {
	    	responseData = documentModel.markForInput(documentId);
	    	//TODO: Ako je paleta vec na 1003 i ceka, moracemo da je pokrenemo odatle
	    	
	    	List<Integer> res = new ArrayList<>();
		    res.add((Integer)responseData);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	/**
	 * Koristi se kod GoodsOut, kada se prebacuje paleta u pripremu za izlaz i kada se vraca nazad u prethodni status, ako user ne zeli da je izbaci
	 * @param documentId
	 * @return
	 */
	@GetMapping("updateDocumentId/{paletteId}/{documentId}")
	public ResponseEntity <Object> updateDocumentId (@PathVariable Integer paletteId, @PathVariable Integer documentId) {
		try {
			
			responseData = model.updateDocumentId(paletteId, documentId);
	    	//responseData = documentModel.markForInput(documentId);
	    	
	    	List<Integer> res = new ArrayList<>();
		    res.add((Integer)responseData);
			return generateResponse(res);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	
	/**
	 * Creates new DOC and PALETTE, then imports palette to WH
	 * @param clientId
	 * @param craneRMCoordinates
	 * @return
	 */
	@GetMapping("barcodeEntryEvent/{clientId}/{craneRMCoordinates}"
	)
	public ResponseEntity <Object> barcodeEntryEvent (@PathVariable Integer clientId, @PathVariable String craneRMCoordinates) {
		try {
			List<StoredProcedureResult> responseData = spModel.call_SP_BARCODE_EVENT (clientId, craneRMCoordinates);
			if (responseData.getFirst().getResultCode() == 1) { // success
				store(craneRMCoordinates);
			}
			
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	/**
	 * Creates new DOC and PALETTE, then imports palette to WH
	 * @param clientId
	 * @param craneRMCoordinates
	 * @return
	 */
	@GetMapping("nextFreePaletteLocation/{craneId}/{algorithmId}")
	public ResponseEntity <Object> getNextFreePaletteLocation (@PathVariable Integer craneId, @PathVariable Integer algorithmId) {
		try {
			List<StoredProcedureResult> responseData = spModel.call_SP_NEXT_FREE_STORAGE_GET (craneId, algorithmId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
}
