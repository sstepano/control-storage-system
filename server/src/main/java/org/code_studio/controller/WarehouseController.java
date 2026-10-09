package org.code_studio.controller;

import org.code_studio.database.Warehouse;
import org.code_studio.model.WarehouseModel;
import org.code_studio.model.WarehousePageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller i @ResponseBody == @RestController
@RestController
@RequestMapping("${url.warehouse}")
public class WarehouseController extends BaseController <Warehouse> {

	@Value("${server.pageSize}")
	private int pageSize;
	
	@Autowired WarehouseModel    model;
	@Autowired WarehousePageable modelPageable;
	
	private final JpaRepository <Warehouse, Integer> repository;
	
	public WarehouseController (WarehouseModel model) {
		super(model);
		this.model = model;
		this.repository = super.getRepository();
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
	
	@GetMapping("allPageableByOrderByWarehouseNumber/{pageId}")
	public ResponseEntity <Object> findAllPageableByOrderByWarehouseNumber (@PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByOrderByWarehouseNumber(PageRequest.of(pageId, this.pageSize));
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	@GetMapping("allByIsActive/{isActive}")
	public ResponseEntity <Object> findAllByIsActive (@PathVariable Boolean isActive) {
		try {
			responseData = model.findAllByIsActive(isActive);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allPageableByIsActive/{isActive}/{pageId}")
	public ResponseEntity <Object> findAllPageableByIsActive (@PathVariable Boolean isActive, @PathVariable Integer pageId) {
		try {
			responseData = modelPageable.findAllPageableByIsActive(PageRequest.of(pageId, this.pageSize), isActive);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allActiveCommissionOrRegular/{commission}")
	public ResponseEntity <Object> findAllActiveCommissionOrRegular (@PathVariable Boolean commission) {
		try {
			responseData = model.findAllActiveCommissionOrRegular(commission);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@GetMapping("allByIsActiveAndIsCommission/{isActiveIndex}/{isCommissionIndex}")
	public ResponseEntity <Object> findAllByIsActiveAndIsCommission (
			@PathVariable Integer isActiveIndex, @PathVariable Integer isCommissionIndex) {
		try {
			responseData = model.findAllByIsActiveAndIsCommission(isActiveIndex, isCommissionIndex);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	// Custom delete, moramo da proveravamo da li ima neke robe u magacinu i da brisemo samo ako nema nista
	@Override
	@DeleteMapping("")
	public ResponseEntity<Object> deleteEntity (@RequestBody Warehouse entity) {
		try {
			Boolean hasItemsInWarehouse = model.hasItemsInWarehouse(entity.getId());
			if (hasItemsInWarehouse) {
				throw new Exception ("Brisanje nije dozvoljeno: Magacin nije prazan.");	
			}
			repository.delete(entity);
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		}
		catch (Exception exception) {
			return generateResponse(exception);
		}
	}

}
