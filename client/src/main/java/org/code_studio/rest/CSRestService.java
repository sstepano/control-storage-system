package org.code_studio.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.code_studio.component.CSTable;
import org.code_studio.database.JwtRequest;
import org.code_studio.database.StoredProcedureResult;
import org.code_studio.main.Common;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import org.json.JSONObject;

public class CSRestService<T> {
	
	private String baseUrl = Common.applicationProperties.getProperty("api.serverurl");
	private SimpleStringProperty url = new SimpleStringProperty("");

	private RestTemplate restTemplate = null;
	private ResponseEntity<JsonResponse<T>> response = null;
	private final String successTitle = "USPEŠNO PROCESIRANO";
	private final String errorTitle = "GREŠKA U PROCESIRANJU PODATAKA";
	private final String http500ErrorMessage = "Došlo je do greške na serveru (500)\n";
	private static String authorizationToken = null;
	private HttpHeaders httpHeaders;
	
	/*
	 * Used to determine if there is no data else to fetch
	 * If flag is set, every further fetch method will not be called,
	 * to prevent unnecessary calls to server
	 */
	private boolean noData = false;
	
	/**
	 * set flag so rest clears table if we get 404 from rest template
	 */
	//private boolean clearTableon404 = false;
	
	
	
	/**
	 * CSTable component used to display and hide progress indicators when fetch method is invoked
	 */
	private CSTable<T> csTable;
	
	/**
	 * Fired when data got fetched from the server, using threads
	 */
	@FunctionalInterface
    public interface CSRestServiceDataFetchedListener {
    	public abstract void onDataFetched();
    }
	
	public CSRestService(String url) {
		this.url.setValue(url);
		restTemplate = new RestTemplate();
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	}
	
	public String getUrl() {
		return this.url.get();
	}
	
	public void setUrl (String url) {
		this.url.setValue(url);
		this.noData = false; // reset flag for new url
	}

	/*
	 * Must be called before we try to get the data
	 * We would need to call this method from background  Task/Thread
	 * See this: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
	 */
	public void fetch(ParameterizedTypeReference<JsonResponse<T>> ref) {		
		if (noData) {
			Common.logMessage(getClass(), "No data to fetch: " + baseUrl + url.getValue(), "DEBUG");
			return;
		}
		
		try {
			setHttpSecurity();
			response = restTemplate.exchange(baseUrl + url.getValue(), HttpMethod.GET, new HttpEntity<>(httpHeaders), ref);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().value() == 404) {
				response = null;
			} else if (e.getStatusCode().value() == 401) {
				//System.out.println("401 - Not Authorized");
				Common.ShowNotification(errorTitle, "401 - Not Authorized", true);
				Common.logMessage(getClass(), e, "ERROR");
				response = null;
			} else if (e.getStatusCode().value() == 403) {
				//System.out.println("403 - Forbidden");
				Common.ShowNotification(errorTitle, "403 - Forbidden", true);
				Common.logMessage(getClass(), e, "ERROR");
				response = null;
			} else {
				//System.out.println("HttpClientErrorException: " + e.getStatusCode().value());
				Common.ShowNotification(errorTitle, e.getStatusCode() + " - " + e.getStatusText(), true);
				Common.logMessage(getClass(), e, "ERROR");
				response = null;
			}
		} catch (Exception e) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), e, "ERROR");
		}
	}
	
	//overload da imamo i page
	public void fetch(int pageId, ParameterizedTypeReference<JsonResponse<T>> ref) {
		if (noData) {
			Common.logMessage(getClass(), "No data to fetch: " + baseUrl + url.getValue(), "DEBUG");
			return;
		}
		
		try {
			setHttpSecurity();
			response = restTemplate.exchange(baseUrl + url.getValue() + "/" + pageId, HttpMethod.GET, new HttpEntity<>(httpHeaders), ref);
			this.noData = false;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().value() == 404) {
				this.noData = true;
				Common.logMessage(getClass(), "404: " + baseUrl + url.getValue() + "/" + pageId, "INFO");
			} else {
				Common.logMessage(getClass(), Integer.toString(e.getStatusCode().value()) + ": " + baseUrl + url.getValue(), "ERROR");
			}
			response = null;
		} catch (Exception e) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), e, "ERROR");
		}
	}
	
	//overload da imamo i page i page size
	public void fetch(int pageId, int pageSize, ParameterizedTypeReference<JsonResponse<T>> ref) {
		if (noData) {
			Common.logMessage(getClass(), "No data to fetch: " + baseUrl + url.getValue(), "DEBUG");
			return;
		}
		
		try {
			setHttpSecurity();
			response = restTemplate.exchange(baseUrl + url.getValue() + "/" + pageId + "/" + pageSize, HttpMethod.GET, new HttpEntity<>(httpHeaders), ref);
			this.noData = false;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().value() == 404) {
				this.noData = true;
				Common.logMessage(getClass(), "404: " + baseUrl + url.getValue() + "/" + pageId, "DEBUG");
			} else {
				Common.logMessage(getClass(), Integer.toString(e.getStatusCode().value()) + ": " + baseUrl + url.getValue(), "ERROR");
			}
			response = null;
		} catch (Exception e) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), e, "ERROR");
		}
	}

	/**
	 * Fetches data in the thread, from the thread pool, then calls listener when the data got fetched from the server
	 * @param pageId
	 * @param ref
	 * @param listener
	 */
	public void fetch(ParameterizedTypeReference<JsonResponse<T>> ref, CSRestServiceDataFetchedListener listener) {
		fetchData(null, null, ref, false, listener);
	}
	
	/**
	 * Fetches data in the thread, from the thread pool, then calls listener when the data got fetched from the server
	 * @param pageId
	 * @param ref
	 * @param listener
	 */
	public void fetch(ParameterizedTypeReference<JsonResponse<T>> ref, boolean clearTableOn404, CSRestServiceDataFetchedListener listener) {
		fetchData(null, null, ref, clearTableOn404, listener);
	}

	/**
	 * Fetches data in the thread, from the thread pool, then calls listener when the data got fetched from the server
	 * @param pageId
	 * @param ref
	 * @param listener
	 */
	public void fetch(int pageId, ParameterizedTypeReference<JsonResponse<T>> ref, CSRestServiceDataFetchedListener listener) {
		fetchData(pageId, null, ref, false, listener);
	}
	
	/**
	 * 
	 * @param pageId
	 * @param pageSize
	 * @param ref
	 * @param listener
	 */
	public void fetch(int pageId, int pageSize, ParameterizedTypeReference<JsonResponse<T>> ref, CSRestServiceDataFetchedListener listener) {
		fetchData(pageId, pageSize, ref, false, listener);
	}
	
	/**
	 * Fetches data in the thread, from the thread pool, then calls listener when the data got fetched from the server
	 * @param pageId
	 * @param ref
	 * @param listener
	 * @param clearTableOn404 - clear table when we get 404 from rest
	 */
	public void fetch(int pageId, ParameterizedTypeReference<JsonResponse<T>> ref,  boolean clearTableOn404, CSRestServiceDataFetchedListener listener) {
		fetchData(pageId, null, ref, clearTableOn404, listener);
	}
	
	/**
	 * 
	 * @param pageId
	 * @param pageSize
	 * @param ref
	 * @param clearTableOn404
	 * @param listener
	 */
	public void fetch(int pageId, int pageSize, ParameterizedTypeReference<JsonResponse<T>> ref,  boolean clearTableOn404, CSRestServiceDataFetchedListener listener) {
		fetchData(pageId, pageSize, ref, clearTableOn404, listener);
	}
	
	/**
	 * Does the actual fetch in the thread, sanitize input to have pageid and pagesize all in one body function
	 */
	private void fetchData(Integer pageId, Integer pageSize, ParameterizedTypeReference<JsonResponse<T>> ref, boolean clearTableOn404, CSRestServiceDataFetchedListener listener) {
		// input handling
		String[] strPage = {"", ""}; // pageId, pageSize 
		
		if (pageId != null) {
			strPage[0] = "/" + pageId.toString();
		}
		
		if (pageSize != null) {
			strPage[1] = "/" + pageSize.toString();
		}
		
		try {
			Task<Void> task = new Task<Void>() {
				
			    @Override
			    public Void call() throws Exception {
			    	try {
			    		setHttpSecurity();
				    	response = restTemplate.exchange(baseUrl + url.getValue() + strPage[0] + strPage[1] , HttpMethod.GET, new HttpEntity<>(httpHeaders), ref);
						noData = false;
			    	}
					catch(HttpClientErrorException ex) {
						if (ex.getStatusCode().value() == 404) {
							noData = true;
							Common.logMessage(getClass(), "404: " + baseUrl + url.getValue() + strPage[0] + strPage[1], "DEBUG");
							throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
						}
					}
			        return null;
			    }
			};
			Common.getThreadPool().getExecutorService().submit(task);
			
			if (csTable != null) {
				csTable.showProgressIndicator(true);
			}			
				
			/* ne radi tj ne updateuje dovoljno brzo
			task.setOnRunning(e -> {
				if (csTable != null) {
				}
			});
			*/
			
			task.setOnScheduled( _ -> {
				if (csTable != null) {
					Platform.runLater( () -> {
						csTable.showProgressIndicator(true);
					});
				}
			});
			
			task.setOnSucceeded( _ -> {
				if (csTable != null) {
					Platform.runLater( () -> {
						csTable.showProgressIndicator(false);
					});
				}
				// call user defined function after the data was fetched
				listener.onDataFetched();
			});
			
			task.setOnFailed( _ -> {
				if (csTable != null) {
					Platform.runLater( () -> {
						csTable.showProgressIndicator(false);
						
						if (clearTableOn404) {
							csTable.clear();
						}
					});
				}
			});
		} catch (Exception e) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), e, "ERROR");
		}
		
	}
	

	/*
	 * returns data as List <T>
	 */
	public List<T> getData() {
		return response != null ? response.getBody().getData() : null;
	}

	/*
	 * returns data wrapped in ObservableList, so we can immediately use it in
	 * tableView.setItems()
	 */
	public ObservableList<T> getDataAsObservableList() {
		List<T> typeList = this.getData();
		ObservableList<T> observableList = 
				typeList != null 
				    ? FXCollections.observableArrayList(typeList)
					: FXCollections.emptyObservableList();
		return observableList;
	}
	
	/*
	 * SAVE ENTITY .. PUT METHOD -> ADD or UPDATE
	 */
	@SuppressWarnings("unchecked") // TODO: Kad se koristi vise od jednom, vraca null iako je uspesno insertovao/updateovao item u bazi
	public T addOrUpdate (T entity) {
		ResponseEntity<?> res = null;
		try {
			setHttpSecurity();
			HttpEntity<T> httpEntity = new HttpEntity<>(entity, httpHeaders);
			res = restTemplate.postForEntity(new URI(baseUrl + url.getValue()), httpEntity, entity.getClass());
		} catch (RestClientException | URISyntaxException ex) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
		return res != null ? (T) res.getBody() : null;
	}

	/*
	 * DELETE ENTITY
	 */
	@SuppressWarnings("unchecked")
	public T Delete (T entity) {
		ResponseEntity<T> res = null;
		try {
			setHttpSecurity();
			HttpEntity<T> httpEntity = new HttpEntity<>(entity, httpHeaders);
			res = (ResponseEntity<T>) restTemplate.exchange(new URI(baseUrl + url.getValue()), HttpMethod.DELETE, httpEntity, entity.getClass());
		} catch (RestClientResponseException ex) {
			JSONObject jsonResponse = new JSONObject(ex.getResponseBodyAsString());
			Common.ShowNotification(errorTitle, jsonResponse.getString("message"), true);
			Common.logMessage(getClass(), ex, "ERROR");
		} catch (Exception ex) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
		return res != null ? (T) res.getBody() : null;
	}
	
	/**
	 * Used for mass change (add/update/delete), where we do not expect any resultset
	 */
	public void execute(String ... args) {
		try {
			String urlValue = baseUrl + url.getValue();
			for (String urlArg : args) {
				urlValue += "/" + urlArg;
			}
			
			setHttpSecurity();
	        restTemplate.exchange(urlValue, HttpMethod.POST, null, new ParameterizedTypeReference<T>() {});
		} catch (RestClientException ex) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
	
	/**
	 * Used for calling SP thad does insert/update/delete, where we expect StoredProcedureResult POJO as resultset
	 */
	public void callStoredProcedure(ParameterizedTypeReference<JsonResponse<T>> ref, String ... lstSpParam) {
		try {
			String entityBody = "";
			//------------------------------------------------------------------
			// build entityBody JSON with all passed through params as strings
			//------------------------------------------------------------------
			entityBody = "{";
			int paramId = 1;
			for (String spParam : lstSpParam) {
				entityBody 
				+= "\""
				+  "param" + paramId // param1, param2, param3 ... nije bitno kako se zovu u jsonu, bitno je da se isti broj prosledi u SP, sa validnim value
				+  "\""
				+  ":"
				+  "\"" // values start here
				+  spParam
				+  "\""
				+  ",";
				paramId++;
			}
			entityBody = entityBody.replaceFirst(".$",""); // brise poslednji zarez, deserializer na serveru se buni ako ostane
			entityBody += "}";
			//------------------------------------------------------------------
			
			setHttpSecurity();
			HttpEntity<String> httpEntity = new HttpEntity<>(entityBody, httpHeaders);
			response = restTemplate.exchange(baseUrl + url.getValue(), HttpMethod.POST, httpEntity, ref);
			
			// Custom SP RESPONSE ERROR handling
			JsonResponse<T> responseBody = response.getBody();
			if (responseBody != null) {
				@SuppressWarnings("unchecked") List<StoredProcedureResult> lst = (List<StoredProcedureResult>) responseBody.getData();
				if (lst.getFirst().getResultCode() != 1) { //ERROR IN SP
					throw new RuntimeException("STORED_PROCEDURE_ERROR", new Throwable(lst.getFirst().getResultDescription()));
				} else {
					Common.ShowNotification(successTitle, lst.getFirst().getResultDescription(), false);		
				}
			}			
		} catch (RestClientException ex) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), ex, "ERROR");
		} catch (RuntimeException ex) {
			if (ex.getLocalizedMessage().equals("STORED_PROCEDURE_ERROR")) {
				Common.ShowNotification(errorTitle, ex.getCause().getLocalizedMessage(), true);
				Common.logMessage(getClass(), ex, "ERROR");		
			}
		}
	}	
	
	/**
	 * 
	 * @param file
	 */
	public void postUpload(File file, CSRestServiceDataFetchedListener listener) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			/*
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			*/
			setHttpSecurity();
			httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			
			MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<String, String>();
			 ContentDisposition contentDisposition = ContentDisposition
		                .builder("form-data")
		                .name("file")
		                .filename(file.getName())
		                .build();
			
	        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
	        HttpEntity<byte[]> fileEntity;
		
			fileEntity = new HttpEntity<>(fileInputStream.readAllBytes(), fileMap);
	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("file", fileEntity);
	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
			String urlValue = baseUrl + url.getValue();
			// --------------------------------------------------

				Task<Void> task = new Task<Void>() {
				    @Override
				    public Void call() throws Exception {
				    	restTemplate.postForEntity(urlValue, requestEntity, String.class);				        
				        return null;
				    }
				};
				Common.getThreadPool().getExecutorService().submit(task);
				
				if (csTable != null) {
					csTable.showProgressIndicator(true);
				}
				
				task.setOnScheduled( _ -> {
					if (csTable != null) {
						Platform.runLater( () -> {
							csTable.showProgressIndicator(true);
						});
					}
				});
				
				task.setOnSucceeded( _ -> {
					// call user defined function after the data was fetched
					listener.onDataFetched();
				});
				
				task.setOnFailed( _ -> {
					if (csTable != null) {
						Platform.runLater( () -> {
							csTable.showProgressIndicator(false);
						});
					}
				});
			// --------------------------------------------------
			
			fileInputStream.close();
		} catch (RestClientException | IOException ex) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
	
	/**
	 * 
	 */
	public void downloadFile() {
		try {
			setHttpSecurity();
			String urlValue = baseUrl + url.getValue();
		    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
		    HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		    ResponseEntity<byte[]> response = restTemplate.exchange(urlValue, HttpMethod.POST, entity, byte[].class);
		    Files.write(Paths.get("test__1.xlsx"), response.getBody());
		} catch (RestClientException | IOException ex) {
			Common.ShowNotification(errorTitle, http500ErrorMessage, true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	}
	
	/**
	 * Gets and Sets private CSTable, for display and hide progress indicator on table in FETCH method
	 */
	
	public CSTable<T> getParentTable() {
		return this.csTable;
	}
	
	public void setParentTable(CSTable<T> csTable) {
		this.csTable = csTable;
	}
	
	/***
	 * Called when user needs to login
	 * TODO: Json Deserializer cannot for some reason deserialize JWtResponse
	 * So I have to do it manually in this method by useng JsonObject
	 * Find why it cannot do it automatically with postForObject or postForEntity
	 */
	public Boolean authenticate(String username, String password) {
		try {
			String res = null;
			JwtRequest jwtRequest = new JwtRequest(username, password);
			HttpEntity<JwtRequest> httpEntity = new HttpEntity<JwtRequest>(jwtRequest, httpHeaders);
			
			//List<JwtResponse> res = (List<JwtResponse>) restTemplate.postForObject(baseUrl + "/authenticate", httpEntity, List.class);
			//ResponseEntity<?> res = restTemplate.exchange(baseUrl + url.getValue(), HttpMethod.POST, httpEntity, JwtResponse.class);
			//res = restTemplate.postForEntity(baseUrl + url.getValue(), httpEntity, new ParameterizedTypeReference<List<JwtResponse>>() {});
			//res = restTemplate.exchange(baseUrl + url.getValue(), HttpMethod.POST, httpEntity, String.class);
			res = restTemplate.postForObject(baseUrl + url.getValue(), httpEntity, String.class);
			//res = restTemplate.postForEntity(baseUrl + url.getValue(), httpEntity,new ParameterizedTypeReference<ResponseEntity<Object>>() {});
			JSONObject jsonResponse = new JSONObject(res);
			//System.out.println(jsonResponse);
			//System.out.println((JwtResponse)res);
			//JwtResponse jwtResponse = new JwtResponse(jsonResponse.getString("token"));
			//String authorizationToken = jsonResponse.getString("token");
			//System.out.println(authorizationToken);
			if (jsonResponse.getString("token") != null) {
				CSRestService.authorizationToken = jsonResponse.getString("token");
				return true;
			} else {
				//httpHeaders.setBearerAuth(null); // clear token
				return false;
			}			
		} catch (Exception e) {
			// Exception se catchuje posle u LoginControlleru gde se prikazue ispravna greska useru
			return false;	
		}
		
	}

	/***
	 * Enables sending JWT token with every request, if security.disable is false
	 */
	private void setHttpSecurity() {
		if (Common.applicationProperties.getProperty("security.disable").equals("false")
				&& !httpHeaders.containsKey("Authorization")) {
			httpHeaders.setBearerAuth(authorizationToken);
		}
	}

}