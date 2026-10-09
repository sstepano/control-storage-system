package org.code_studio.controller;

import org.code_studio.database.PaletteItem;
import org.code_studio.model.PaletteItemModel;
import org.code_studio.model.PaletteItemModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.paletteItem}")
public class PaletteItemController extends BaseController <PaletteItem> {
	
	@Value("${server.pageSize}")
	private int pageSize;

	@Autowired
	PaletteItemModel model;
	
	@Autowired
	PaletteItemModelPageable modelPageable;
	
	public PaletteItemController (PaletteItemModel model) {
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
	
	@GetMapping("allPageableByPaletteId/{paletteId}/{pageId}")
	public ResponseEntity <Object> allPageableByPaletteId (@PathVariable Integer paletteId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByPaletteId(paletteId, PageRequest.of(pageId, this.pageSize));
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

}
