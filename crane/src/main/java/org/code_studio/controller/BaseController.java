package org.code_studio.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController <T> {

	private Map <String, Object> responseMap = new LinkedHashMap <String, Object>();
	protected Object responseData = null;
	
	/*
	 * Pravimo generics input kao repo, jer cemo ovo koristiti u svakom
	 * kontroleru
	 */
	public BaseController () { }
	

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
	

}
