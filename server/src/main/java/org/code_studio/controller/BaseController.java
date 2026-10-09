package org.code_studio.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseController <T> {

	private Map <String, Object> responseMap = new LinkedHashMap <String, Object>();
	protected Object responseData = null;
	private final JpaRepository <T, Integer> repository;
	
	/*
	 * Pravimo generics input kao repo, jer cemo ovo koristiti u svakom
	 * kontroleru
	 */
	public BaseController (JpaRepository <T, Integer> repository) {
		this.repository = repository;
	}
	
	// Used in custom delete methds, such as in WarehouseListController, where we need to check something before deletion
	public JpaRepository<T, Integer> getRepository() {
		return repository;
	}

	protected ResponseEntity <Object> generateResponse (Object responseData) {
		boolean isResponseEmpty = false;
		
		/*
		 * We're checking here if responseData is Optional type and if it is empty
		 * or if it is ArrayList of some other object type. In second case we're
		 * converting it to array list and using isEmpty to check if it is.
		 * If it is empty, we'we showing 404 instead of 200 with empty data attribute
		 * */
		// here we can check if it is instance of Collection too! :)
		if (responseData instanceof ArrayList) {
			ArrayList<?> arrListResponse = (ArrayList<?>) responseData;
			if (arrListResponse.isEmpty()) {
				isResponseEmpty = true;
				responseData = null;
			}
		} else if (responseData == null || responseData.equals(Optional.empty())) {
			isResponseEmpty = true;
			responseData = null;
		}
		
		responseMap.put("status", isResponseEmpty ? HttpStatus.NOT_FOUND.value() :  HttpStatus.OK.value());
		responseMap.put("message", isResponseEmpty ? "No data" :  null);
		responseMap.put("data", responseData);
		
		/*ako je izvedena klasa TableController napunila ovu listu, ubacicemo je u odgovor jer treba klijentu za pravljenje kolona u gridu
		if (!tableColumns.isEmpty()) {
			responseMap.put("columns", tableColumns);
		}
		*/
		
		//TODO: Here we should better handle return statuses
		return new ResponseEntity <Object> (responseMap, isResponseEmpty ? HttpStatus.NOT_FOUND :  HttpStatus.OK);
	}
	
	protected ResponseEntity <Object> generateResponse (Exception exceptionData) {
		
		responseMap.put("data", null);
		responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		responseMap.put("message", exceptionData.getLocalizedMessage());
		
		return new ResponseEntity <Object> (responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* =========================================================================
	 * Pravimo genericke metode gde ce CRUD samo ovde biti definisan da ne 
	 * moram u svakom kontroleru to da radim.
	 */
	
	@GetMapping("")
	public ResponseEntity <Object> findAll () {
		try {
			responseData = repository.findAll();
			System.out.println("BROJ3: ");
			//throw new Exception("Threw exception!");
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	
	/**
	 * 
	 * @param id id of the entity
	 * @returns List<T> or null if response is empty
	 */
	@GetMapping("/{id:[0-9]+}")
	public ResponseEntity <Object> findById (@PathVariable Integer id) {
		List<Optional<T>> lstOptional = new ArrayList<Optional<T>>();
		Optional<T> optionalResponse = repository.findById(id);
		System.out.println("BROJ4: ");
		try {
			if (!optionalResponse.isEmpty()) {
				lstOptional.add(optionalResponse);
				responseData = lstOptional;
			} else {
				responseData = optionalResponse;
			}
			return generateResponse(responseData);
			
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Object> updateEntity (@RequestBody T entity) {
		try {
			entity = repository.saveAndFlush(entity);
			//res = new ResponseEntity <Object> (entity, HttpStatus.OK);
			
			//T resObj = repository.saveAndFlush(entity);
			//ResponseEntity<Object> res = generateResponse(entity);
			//ResponseEntity<Object> res = generateResponse(resObj);
			
			/*try {
				System.out.println(((Client)entity).getId().toString());
			} catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}*/
			//Ovde ne koristim generateResponse jer mi vraca null za insertovane IDeve, a nemam vremena sad da gledam zasto
		    return new ResponseEntity<Object>(entity, HttpStatus.OK);
		}
		catch (DataAccessException exception) {
			return generateResponse(exception);
		}
	}

	@PostMapping("/{id}")
	public ResponseEntity<Object> saveEntity (@RequestBody T entity, @PathVariable Long id) {
		try {
			//repository.save(entity);
			//return new ResponseEntity <Object> (entity, HttpStatus.OK);
			return generateResponse(repository.save(entity));
		}
		catch (DataAccessException exception) {
			return generateResponse(exception);
		}
	}
	
	@DeleteMapping("")
	public ResponseEntity<Object> deleteEntity (@RequestBody T entity) {
		try {
			repository.delete(entity);
			return new ResponseEntity<Object>(entity, HttpStatus.OK);
		}
		catch (DataAccessException exception) {
			return generateResponse(exception);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteEntity (@PathVariable Integer id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		}
		catch (DataAccessException exception) {
			return generateResponse(exception);
		}
	}

}
