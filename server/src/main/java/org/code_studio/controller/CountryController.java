package org.code_studio.controller;

import org.code_studio.database.Country;
import org.code_studio.model.CountryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.country}")
public class CountryController extends BaseController <Country> {

	@Autowired
	CountryModel model;
	
	public CountryController (CountryModel model) {
		super(model);
		this.model = model;
	}
	
}
