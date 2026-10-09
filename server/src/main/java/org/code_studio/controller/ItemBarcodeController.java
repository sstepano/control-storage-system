package org.code_studio.controller;

import org.code_studio.database.ItemBarcode;
import org.code_studio.model.ItemBarcodeModel;
import org.code_studio.model.ItemBarcodeModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.itemBarcode}")
public class ItemBarcodeController extends BaseController <ItemBarcode> {

	//@Value("${server.pageSize}") ovde mi treba vise nego default jer se prikazuje samo jedna tabela u formi
	private int pageSize = 50;
	
	@Autowired
	ItemBarcodeModel model;

	@Autowired
	ItemBarcodeModelPageable modelPageable;
	
	public ItemBarcodeController (ItemBarcodeModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allPageable/{pageId}")
	public ResponseEntity <Object> findAllPageable (@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageable(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByItemId/{itemId}/{pageId}")
	public ResponseEntity <Object> findAllByItemId (@PathVariable Integer itemId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByItemId(itemId, PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	@GetMapping("allPageableByBarcode/{barcode}/{pageId}")
	public ResponseEntity <Object> findAllPageableByBarcode (@PathVariable String barcode, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByBarcode(barcode, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByClientId/{clientId}/{pageId}")
	public ResponseEntity <Object> findAllByClientId (@PathVariable Integer clientId, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllByClientId(clientId, PageRequest.of(pageId, pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	
}
