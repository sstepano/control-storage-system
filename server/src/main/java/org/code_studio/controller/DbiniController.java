package org.code_studio.controller;

import org.code_studio.database.Dbini;
import org.code_studio.model.DbiniModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${url.dbini}")
public class DbiniController extends BaseController <Dbini> {
	
	@Autowired
	DbiniModel model;
	
	public DbiniController (DbiniModel model) {
		super(model);
		this.model = model;
	}
	
	@GetMapping("allByAttributeNameAndParameterName/{attributeName}/{parameterName}")
	public ResponseEntity <Object> findAllByAttributeNameAndParameterName (@PathVariable String attributeName, @PathVariable String parameterName) {
		try {
			responseData = model.findAllByAttributeNameAndParameterName(attributeName, parameterName);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
