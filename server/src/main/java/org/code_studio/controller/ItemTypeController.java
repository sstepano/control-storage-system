package org.code_studio.controller;

import org.code_studio.database.ItemType;
import org.code_studio.model.ItemTypeModel;
import org.code_studio.model.ItemTypeModelPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.itemType}")
public class ItemTypeController extends BaseController <ItemType> {
	
	@Autowired
	ItemTypeModel model;
	
	@Autowired
	ItemTypeModelPageable modelPageable;
	
	@Value("${server.pageSize}")
	private int pageSize = 20;	
	
	public ItemTypeController (ItemTypeModel model) {
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
	
}
