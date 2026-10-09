package org.code_studio.service;

import org.code_studio.controller.BaseController;
import org.code_studio.database.StoredProcedureResult;
import org.code_studio.model.StoredProcedureModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("${url.businessLogicService}")
public class BusinessLogicService extends BaseController<StoredProcedureResult> {
	
	/***
	 * KLASE koje definisu IN parametre Stored Procedura
	 * Moraju da se predefinisu kako bi Jackson mogao da ih deserializuje iz JSON-a
	 * IMPORTANT INFO: Jackson moze da instancira samo STATIC INNER class za deserializaciju
	 * JOS JEDAN IMPORTANT INFO: Ako npr koristim u SPParam static klais parametre sa dugim nazivom,
	 * Spring Boot jednostavno ne procita parametre, tj oni budu NULL. I to je zato sto u klijentu koristim
	 * "param" + paramId za generisane JSON-a u CSRestService::callStoredProcedure.
	 * Dakle, u BusinessLogicService.java moram da ih nazivam - param1, param2 itd. Ne smem da koristim npr p_in_int_some_long_param_name.
	 */
	public static class SPParam_BusinessLogicTest {
		public String param1;
		public String param2;
		public String param3;
		public String param4;
		public String param5;
		public SPParam_BusinessLogicTest() {}
	}
	
	// SP_CLIENT_ORDER_PARSE
	public static class SPParam_ClientOrderParse {
		public Integer p_in_int_file_header_id;
		public SPParam_ClientOrderParse() {}
	}
	
	// SP_INVENTORY_LISTING_ADD
	public static class SPParam_InventoryListingAdd {
		public Integer param1; // p_in_int_inventory_listing_detail_id
		public Integer param2; // p_in_int_created_by_user_id
		public SPParam_InventoryListingAdd() {}
	}
	
	// SP_INVENTORY_LISTING_ALL_DISCREPANCIES_ADD
	public static class SPParam_InventoryListingAllDiscrepanciesAdd {
		public Integer param1; // p_in_int_created_by_user_id
		public SPParam_InventoryListingAllDiscrepanciesAdd() {}
	}
	
	// SP_PALETTE_STATUS_UPDATE
	public static class SPParam_PaletteStatusUpdate {
		public String param1; // p_in_str_palette_code
		public Integer param2; // p_in_int_status_id
		public SPParam_PaletteStatusUpdate() {}
	}
	
	// SP PARAMS END
	//-----------------------------------------------------------------------------

	@Autowired
	StoredProcedureModel model;
	
	public BusinessLogicService(StoredProcedureModel model) {
		super(model);
		this.model = model;
	}
	
	
	/** SP BUSINESS LOGIC TEST
	 * @param sp1Param
	 * @return
	 */
	@PostMapping("businessLogicService_2")
	public ResponseEntity <Object> businessLogicService_2 (@RequestBody SPParam_BusinessLogicTest sp1Param) {
		try {
			//p_in_int_param1
			System.out.println("param1: " + sp1Param.param1);
			System.out.println("param2: " + sp1Param.param2);
			System.out.println("param3: " + sp1Param.param3);
			System.out.println("param4: " + sp1Param.param4);
			System.out.println("param5: " + sp1Param.param5);
			
			responseData = model.callStoredProcedure1 (
				sp1Param.param1,
				sp1Param.param2,
				sp1Param.param3,
				sp1Param.param4,
				sp1Param.param5
			);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/** SP CLIENT ORDER PARSE
	 * @param p_in_int_param1Ste
	 * @return
	 */
	@PostMapping("spClientOrderParse")
	public ResponseEntity <Object> spClientOrderParse (@RequestBody SPParam_ClientOrderParse spParamClientOrderParse) {
		try {
			System.out.println("param1: " + spParamClientOrderParse.p_in_int_file_header_id);
			
			responseData = model.call_SPClientOrderParse (
				spParamClientOrderParse.p_in_int_file_header_id
			);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/** SP INVENTORY_LISTING_ADD
	 * @param p_in_int_inventory_listing_detail_id
	 * @param p_in_int_created_by_user_id
	 * @return
	 */
	@PostMapping("spInventoryListingAdd")
	public ResponseEntity <Object> spInventoryListingAdd (@RequestBody SPParam_InventoryListingAdd spParam) {
		try {
			responseData = model.call_SPInventoryListingAdd (
					spParam.param1,
					spParam.param2
			);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/** SP INVENTORY_LISTING_ALL_DISCREPANCIES_ADD
	 * @param p_in_int_created_by_user_id
	 * @return
	 */
	@PostMapping("spInventoryListingAllDiscrepanciesAdd")
	public ResponseEntity <Object> spInventoryListingAllDiscrepanciesAdd (@RequestBody SPParam_InventoryListingAllDiscrepanciesAdd spParam) {
		try {
			responseData = model.call_SPInventoryListingAllDiscrepanciesAdd (
					spParam.param1
			);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}
	
	/** SP_PALETTE_STATUS_UPDATE
	 * @param p_in_str_palette_code
	 * * @param p_in_int_status_id
	 * @return
	 */
	@PostMapping("spPaletteStatusUpdate")
	public ResponseEntity <Object> spPaletteStatusUpdate (@RequestBody SPParam_PaletteStatusUpdate spParam) {
		try {
			responseData = model.call_SPPaletteStatusUpdate (
				spParam.param1,
				spParam.param2
			);
			return generateResponse(responseData);
		} catch (Exception exception) {
			return generateResponse(exception);
		}
	}

	
}
