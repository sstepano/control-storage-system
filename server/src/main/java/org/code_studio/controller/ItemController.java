package org.code_studio.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.Base64;
import org.code_studio.database.Item;
import org.code_studio.model.ItemModel;
import org.code_studio.model.ItemModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.item}")
public class ItemController extends BaseController <Item> {

	@Value("${server.pageSize}")
	private int pageSize = 20;
	
	@Value("${server.itemImagePath}")
	private String baseImagePath = "D:/dev/java-ws/repro-market/assets/misc/RM-PRODUKCIJA/ArtikliSlike/";
	
	@Autowired
	ItemModel model;

	@Autowired
	ItemModelPageable modelPageable;
	
	public ItemController (ItemModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping(value={"allPageable/{pageId}", "allPageable/{showInStockOnly}/{pageId}"})
	public ResponseEntity <Object> findAllPageable (
			@PathVariable (name="showInStockOnly", required=false) Boolean showInStockOnly,
			@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageable(showInStockOnly, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableWithPageSize/{pageSize}/{pageId}")
	public ResponseEntity <Object> findAllPageable (@PathVariable Integer pageSize, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageable(false, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping(value={"allByClientIdPageable/{clientId}/{pageId}",
			"allByClientIdPageable/{clientId}/{showInStockOnly}/{pageId}"})
	public ResponseEntity <Object> findAllByClientId (
			@PathVariable Integer clientId,
	        @PathVariable (name="showInStockOnly", required=false) Boolean showInStockOnly,
			@PathVariable Integer pageId) {
		try {
			showInStockOnly = showInStockOnly == null ? false : true;
			responseData = modelPageable.findAllByClientId(clientId, showInStockOnly, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping(value={"allByClientIdPageableWithPageSize/{pageSize}/{clientId}/{pageId}", "allByClientIdPageableWithPageSize/{pageSize}/{clientId}/{showInStockOnly}/{pageId}"})
	public ResponseEntity <Object> findAllByClientIdWithPageSize (
			@PathVariable Integer pageSize, 
			@PathVariable Integer clientId,
			@PathVariable (name="showInStockOnly", required=false) Boolean showInStockOnly,
			@PathVariable Integer pageId) {
		try {
			showInStockOnly = showInStockOnly == null ? false : showInStockOnly;
			responseData = modelPageable.findAllByClientId(clientId, showInStockOnly, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}


	@GetMapping("allByCatalogIdPageable/{catalogId}/{pageId}")
	public ResponseEntity <Object> findAllByCatalogId (@PathVariable Integer catalogId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByCatalogId(catalogId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByBranchIdPageable/{branchId}/{pageId}")
	public ResponseEntity <Object> findAllByBranchId (@PathVariable Integer branchId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByBranchId(branchId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByGroupIdPageable/{groupId}/{pageId}")
	public ResponseEntity <Object> findAllByGroupId (@PathVariable Integer groupId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByGroupId(groupId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allBySubgroupIdPageable/{subgroupId}/{pageId}")
	public ResponseEntity <Object> findAllBySubgroupId (@PathVariable Integer subgroupId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllBySubgroupId(subgroupId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allBySubgroupId/{subgroupId}")
	public ResponseEntity <Object> findAllBySubgroupId (@PathVariable Integer subgroupId) {
		try {
			responseData = model.findBySubgroupId(subgroupId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByTypeIdPageable/{typeId}/{pageId}")
	public ResponseEntity <Object> findAllByTypeId (@PathVariable Integer typeId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByTypeId(typeId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByNameAndClientId/{name}/{clientId}/{pageId}")
	public ResponseEntity <Object> findAllByNameAndClientId (@PathVariable String name, @PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			//responseData = modelPageable.findAllByClientIdAndNameContaining(clientId, name, PageRequest.of(pageId, this.pageSize));
			responseData = modelPageable.findAllByNameAndClientId(name, clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByClientIdAndNameWithPageSize/{pageSize}/{clientId}/{name}/{pageId}")
	public ResponseEntity <Object> findAllByNameAndClientId (@PathVariable Integer pageSize, @PathVariable Integer clientId, @PathVariable String name, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientIdAndNameContaining(clientId, name, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allPageableByName/{name}/{pageId}")
	public ResponseEntity <Object> findAllByNameContaining (@PathVariable String name, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByNameContaining(name, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByNameWithPageSize/{pageSize}/{name}/{pageId}")
	public ResponseEntity <Object> findAllByNameContaining (@PathVariable Integer pageSize,  @PathVariable String name, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByNameContaining(name, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping(value={"allPageableByIdOrCodeOrNameWithPageSize/{pageSize}/{searchPhrase}/{pageId}",
			"allPageableByIdOrCodeOrNameWithPageSize/{pageSize}/{searchPhrase}/{showInStockOnly}/{pageId}"})
	public ResponseEntity <Object> findAllByIdOrCodeOrName (
			@PathVariable Integer pageSize,  
			@PathVariable String searchPhrase,
			@PathVariable (name="showInStockOnly", required=false) Boolean showInStockOnly,
			@PathVariable Integer pageId) {
		try {
			showInStockOnly = showInStockOnly == null ? false : showInStockOnly;
			responseData = modelPageable.findAllByIdOrCodeOrNameContaining(searchPhrase, showInStockOnly, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	/**
	 * Gets image for the item id in pathvariable ID
	 * @param id Item ID
	 * @return generated response with encoded Base64 image in DATA tag
	 */
	@GetMapping("itemImageByItemId/{id}")
	public ResponseEntity <Object> itemImageByItemId (@PathVariable Integer id) {
		try {
			String imagePath = null;
		    InputStream inputStream = null;
		    FileSystemResource imgFile = null;
		    
			Optional<Item> item = model.findById(id);
			if (item.isPresent() && item.get().getImagePath() != null) {
				imagePath = item.get().getImagePath();
			    imgFile = new FileSystemResource(baseImagePath + imagePath);
			    
				try {
					inputStream = imgFile.getInputStream();
				} catch (IOException e) {
					responseData = Optional.empty();
				}
			}
	
			if (inputStream != null) {
				List <String> lstBase64EncodedImage = new ArrayList<>();
				lstBase64EncodedImage.add(Base64.getEncoder().encodeToString(inputStream.readAllBytes()));
				responseData = lstBase64EncodedImage;
			} else {
				responseData = Optional.empty();
			}
			
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}		
	}
	
	
	/**
	 * Finds ONE item by code provided
	 * @param code
	 * @return
	 */
	@GetMapping("findByCode/{code}")
	public ResponseEntity <Object> findByCode (@PathVariable String code) {
		try {
			responseData = model.findByCode(code);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
}
