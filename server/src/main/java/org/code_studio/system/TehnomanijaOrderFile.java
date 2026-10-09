package org.code_studio.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import org.code_studio.database.StoredProcedureResult;
import org.code_studio.model.StoredProcedureModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TehnomanijaOrderFile extends Excel97File {
	
	private static Integer CLIENT_ID = 5047; // TEHNOMANIJA ID, ovo mora iz filename-a da se parsira
	private static final String  CLIENT_STORE_NAME = "T-001"; // TEHNOMANIJA STORE NAME
	private static final Integer CLIENT_STORE_ID   = 1056;    // TEHNOMANIJA STORE ID
	private static final Integer ORDER_TEMPLATE    = 2;       // TEHNOMANIJA MASS ORDER FILE TEMPLATE
	
	private Map <String, List<Map<String, Integer>>> mapOrder; // final map of all orders from the loaded file
	private Map<String, Integer> orderCnt = null; // One keypair of order per store, within row, such as G-01: 1
	private List<Map<String, Integer>> lstOrders; // List of orderCnt orders
	
	@Autowired
	private StoredProcedureModel spModel;
	
	public TehnomanijaOrderFile() {
		super(ORDER_TEMPLATE);
	}
	
	
	/**
	 * Loads file, parses it and calls SP that inserts all parsed rows to the DB
	 * @param fileInputStream
	 * @return 
	 */
	public List<StoredProcedureResult> loadFileAndSaveToDatabase (MultipartFile file) {
		List<StoredProcedureResult> spRes = null;
		load(file);
		buildFileStructure(workbook);
		
		// insert into file_header
		spRes = spModel.call_SPFileHeaderAdd(2, file.getOriginalFilename());
		
		if (spRes != null && spRes.get(0).getResultCode() == 1) { // ako je uspesan upis u file_header
			Integer insertedFileHeaderId = Integer.valueOf(spRes.get(0).getReturnValue());
			String barcode = null;
			String storeName = null;
			Integer storeOrder = 0;
			for (Map.Entry<String, List<Map<String, Integer>>> entry : mapOrder.entrySet()) {
				barcode = entry.getKey();
				
				for (Map<String, Integer> mapOrders : entry.getValue()) {
					for (String key : mapOrders.keySet()) {
						storeName = key;
						storeOrder = mapOrders.get(key);
						/*
						System.out.println("Barcode: " + barcode);
						System.out.println("Store name: " + storeName);
						System.out.println("Store order: " + storeOrder);
						*/
						
						spRes = spModel.call_SPClientOrderAdd(insertedFileHeaderId, CLIENT_ID, barcode, storeName, storeOrder, CLIENT_STORE_ID);
					}
				}
			}
		} // if spRes
		return spRes; // TODO: Vraca samo poslednji result, napraviti da vraca sve
	}
	
	/**
	 * Parses the data from the file, generates maps and lists
	 */
	public void buildFileStructure(HSSFWorkbook workbook) {
		int rowIndexStart = 16; // from this row index starts the real data for load
		int cellIndexOrderCnt = 5; // this column holds order count for this store
		mapOrder = new LinkedHashMap<>();
		HSSFSheet sheet0 = workbook.getSheetAt(0);
		String barcode = null;

		if (sheet0 != null) {
			for (Row row : sheet0) {
				lstOrders = new ArrayList<>();

				for (Cell cell : row) {
					orderCnt = new LinkedHashMap<>();

					if (row.getRowNum() >= rowIndexStart && cell.getColumnIndex() == 2) { // get barcode of item as 3rd column, starting from 16th row
						barcode = cell.getStringCellValue();
					} else if (row.getRowNum() >= rowIndexStart && cell.getColumnIndex() == cellIndexOrderCnt // get ordered values 
							&& cell.getCellType() == CellType.NUMERIC && (int)cell.getNumericCellValue() != 0) {
						orderCnt.put(CLIENT_STORE_NAME, (int)cell.getNumericCellValue());
						lstOrders.add(orderCnt);
					}
				} // for cell
				if (barcode != null) {
					mapOrder.put(barcode, lstOrders);
				}
			} // for row
		}
	}
	
	/**
	 * Sets TEHNOMANIJA client id, parsed from the filename
	 * Every file is being used for one client (as Tehnomanija has)
	 * @param clientId
	 */
	public void setClientId(Integer clientId) {
		CLIENT_ID = clientId;
	}

}
