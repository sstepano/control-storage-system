package org.code_studio.controller;

import java.util.List;

import org.code_studio.database.ApplicationUser;
import org.code_studio.model.ApplicationUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.applicationUser}")
public class ApplicationUserController extends BaseController <ApplicationUser> {

	@Value("${app.saleOfficerRoleIds}")
	String saleOfficerRoleIDs;
	
	@Value("${app.enumeratorRoleIds}")
	String enumeratorRoleIDs;
	
	@Autowired
	ApplicationUserModel model;
		
	public ApplicationUserController (ApplicationUserModel model) {
		super(model);
		this.model = model;
	}

	@GetMapping("allSaleOfficers")
	public ResponseEntity <Object> allSaleOfficers () {

		try {
			responseData = model.findAllSaleOfficers(saleOfficerRoleIDs.split(","));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	// This is used for LOGIN, NABUDZ da vraca samo listu usera, jer ne koristimo JsonResponse objekat ovde
	@GetMapping("findByUsername/{username}")
	public List<ApplicationUser> findByUsername (
			@PathVariable String username) {
		List<ApplicationUser> res = null;
		try {
			res = model.findByUsername(username);
			return res;
		} catch (Exception exception) {
			return res;
		}
	}
	
	@GetMapping("findByUsernameAndPassword/{username}/{password}")
	public ResponseEntity <Object> findByUsernameAndPassword (
			@PathVariable String username, @PathVariable String password) {
		try {
			responseData = model.findByUsernameAndPassword(username, password);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allSaleOfficersByClientId/{clientId}")
	public ResponseEntity <Object> allSaleOfficersByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findAllSaleOfficersByClientId(saleOfficerRoleIDs.split(","), clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("primarySaleOfficerByClientId/{clientId}")
	public ResponseEntity <Object> primarySaleOfficerByClientId (@PathVariable Integer clientId) {
		try {
			responseData = model.findPrimarySaleOfficerByClientId(saleOfficerRoleIDs.split(","), clientId);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allEnumerators")
	public ResponseEntity <Object> allEnumerators () {
		try {
			responseData = model.findAllEnumerators(enumeratorRoleIDs.split(","));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allEnumeratorsWithoutALL")
	public ResponseEntity <Object> allEnumeratorsWithoutALL () {
		try {
			responseData = model.findAllEnumeratorsWithoutALL(enumeratorRoleIDs.split(","));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
