package org.code_studio.controller;

import org.code_studio.database.PaletteDocument;
import org.code_studio.model.PaletteDocumentModel;
import org.code_studio.model.PaletteDocumentModelPageable;
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
@RequestMapping("${url.paletteDocument}")
public class PaletteDocumentController extends BaseController <PaletteDocument> {
	
	@Value("${server.pageSize}")
	private int pageSize;

	@Autowired
	PaletteDocumentModel model;
	
	@Autowired
	PaletteDocumentModelPageable modelPageable;
	
	public PaletteDocumentController (PaletteDocumentModel model) {
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
	
	@GetMapping("allPageableByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> allPageableByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByClientId(clientId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByClientIdAndStatusIdAndTypeIdOrderByIdDesc/{clientId}/{statusId}/{typeId}/{pageId}")
	public ResponseEntity <Object> allPageableByClientIdAndStatusIdAndTypeIdOrderByIdDesc (@PathVariable Integer clientId, @PathVariable Integer statusId, @PathVariable Integer typeId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.allPageableByClientIdAndStatusIdAndTypeIdOrderByIdDesc(clientId, statusId, typeId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
}
