package org.code_studio.model;

import java.util.List;

import org.code_studio.database.StoredProcedureResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoredProcedureModel extends JpaRepository <StoredProcedureResult, Integer> {
	
	// Ako koristim SP, ne moram Transactional i Modifying, posto to kontrolisem iz SP
	//@Transactional
	//@Modifying
    @Query(value = """
    		call SP_BUSINESS_LOGIC_TEST (
    		  :p_in_int_param1,
    		  :p_in_int_param2,
    		  :p_in_int_param3,
    		  :p_in_int_param4,
    		  :p_in_int_param5
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> callStoredProcedure1 (
    	String p_in_int_param1,
    	String p_in_int_param2,
    	String p_in_int_param3,
    	String p_in_int_param4,
    	String p_in_int_param5
	);
    
    //-------------------------------------------
    // SP_CLIENT_ORDER_PARSE
    //-------------------------------------------
    @Query(value = """
    		call SP_CLIENT_ORDER_PARSE (
    		  :p_in_int_file_header_id
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SPClientOrderParse (
    	Integer p_in_int_file_header_id
	);

    //-------------------------------------------
    // SP_FILE_HEADER_ADD
    //-------------------------------------------
    @Query(value = """
    		call SP_FILE_HEADER_ADD (
    		  :p_in_int_file_type_id,
    		  :p_in_str_file_name
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SPFileHeaderAdd (
    		  Integer p_in_int_file_type_id,
    		  String p_in_str_file_name
	);
    //-------------------------------------------  
    
    //-------------------------------------------
    // SP_CLIENT_ORDER_ADD
    //-------------------------------------------
    @Query(value = """
    		call SP_CLIENT_ORDER_ADD (
    		  :p_in_int_file_header_id,
    		  :p_in_int_client_id,
    		  :p_in_str_item_barcode,
    		  :p_in_str_store_name,
    		  :p_in_int_store_order,
    		  :p_in_int_store_id
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SPClientOrderAdd (
    		  Integer p_in_int_file_header_id,
    		  Integer p_in_int_client_id,
    		  String  p_in_str_item_barcode,
    		  String  p_in_str_store_name,
    		  Integer p_in_int_store_order,
    		  Integer p_in_int_store_id
	);
    //-------------------------------------------  
    
    //-------------------------------------------
    // SP_INVENTORY_LISTING_ADD
    //-------------------------------------------
    @Query(value = """
    		call SP_INVENTORY_LISTING_ADD (
    		  :p_in_int_inventory_listing_detail_id,
    		  :p_in_int_created_by_user_id
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SPInventoryListingAdd (
    		  Integer p_in_int_inventory_listing_detail_id,
    		  Integer p_in_int_created_by_user_id
	);
    //------------------------------------------- 

    //-------------------------------------------
    // SP_INVENTORY_LISTING_ALL_DISCREPANCIES_ADD
    //-------------------------------------------
    @Query(value = """
    		call SP_INVENTORY_LISTING_ALL_DISCREPANCIES_ADD (
    		  :p_in_int_created_by_user_id
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SPInventoryListingAllDiscrepanciesAdd (
    		  Integer p_in_int_created_by_user_id
	);
    //-------------------------------------------
    
    
    //-------------------------------------------
    // SP_PALETTE_STATUS_UPDATE
    //-------------------------------------------
    @Query(value = """
    		call SP_PALETTE_STATUS_UPDATE (
    		  :p_in_str_palette_code,
    		  :p_in_int_status_id
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SPPaletteStatusUpdate (
    		  String p_in_str_palette_code,
    		  Integer p_in_int_status_id
	);
    //-------------------------------------------
    
    
    //-------------------------------------------
    // SP_STORE_PALETTE
    //-------------------------------------------
    @Query(value = """
    		call SP_STORE_PALETTE (
    		  :p_in_str_palette_code
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SPStorePalette (
    		  String p_in_str_palette_code
	);
    
    
    //-------------------------------------------
    // SP_CRANE_QUEUE_UPDATE
    //-------------------------------------------
    @Query(value = """
    		call SP_CRANE_QUEUE_UPDATE (
			  :p_in_str_palette_code,
			  :p_in_str_source,
			  :p_in_str_target,
			  :p_in_str_current_position,
			  :p_in_str_next_position,
			  :p_in_str_last_status 
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SP_CRANE_QUEUE_UPDATE (
    	String p_in_str_palette_code,
		String p_in_str_source,
		String p_in_str_target,
		String p_in_str_current_position,
		String p_in_str_next_position,
		String p_in_str_last_status
	);
    
    
    //-------------------------------------------
    // SP_BARCODE_EVENT
    //-------------------------------------------
    @Query(value = """
    		call SP_BARCODE_EVENT (
			  :p_in_int_client_id, 
			  :p_in_str_palette_code
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SP_BARCODE_EVENT (
		Integer p_in_int_client_id,
    	String p_in_str_palette_code
	);
    
    
    //-------------------------------------------
    // SP_NEXT_FREE_STORAGE_GET
    //-------------------------------------------
    @Query(value = """
    		call SP_NEXT_FREE_STORAGE_GET (
			  :p_in_int_crane_id, 
			  :p_in_int_algorithm_id
    		)
    		""", nativeQuery = true)
    List<StoredProcedureResult> call_SP_NEXT_FREE_STORAGE_GET (
		Integer p_in_int_crane_id,
		Integer p_in_int_algorithm_id
	);
    
    
}
