package org.code_studio.controller;

import org.code_studio.database.ApplicationLanguage;
import org.code_studio.model.ApplicationLanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.applicationLanguage}")
public class ApplicationLanguageController extends BaseController <ApplicationLanguage> {

	@Autowired
	ApplicationLanguageModel model;
	
	public ApplicationLanguageController (ApplicationLanguageModel model) {
		super(model);
		this.model = model;
	}
}
