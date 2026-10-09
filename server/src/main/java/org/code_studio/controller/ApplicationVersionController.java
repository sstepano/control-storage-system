package org.code_studio.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.code_studio.database.ApplicationVersion;
import org.code_studio.model.ApplicationVersionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.applicationVersion}")
public class ApplicationVersionController extends BaseController <ApplicationVersion> {

	@Autowired
	ApplicationVersionModel model;
	
	public ApplicationVersionController (ApplicationVersionModel model) {
		super(model);
		this.model = model;
	}
	
	@Override
	@GetMapping("")
	public ResponseEntity <Object> findAll () {
		try {
			List<ApplicationVersion> lstVersion = new ArrayList<ApplicationVersion>();
			lstVersion.add(new ApplicationVersion("hsa8213hSADH", 191, LocalDate.now()));
			responseData = lstVersion;
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
}
