package org.code_studio.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseHandler {

	private static Map <String, Object> map = new HashMap <String, Object>();
	private static List <String> columns = new ArrayList <String>();
	
    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean error,String message, Object responseObj) {
    	
    	columns.clear();
    	columns.addAll(Arrays.asList("Col1", "Col2", "Col3", "Col4", "Col5"));
    	
        try {
            //map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("message", message);
            map.put("data", responseObj);
            map.put("columns", columns);

            return new ResponseEntity <Object> (map, status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess",false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity <Object> (map,status);
        }
    }
}