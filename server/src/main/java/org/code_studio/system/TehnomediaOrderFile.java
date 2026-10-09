package org.code_studio.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.code_studio.database.StoredProcedureResult;
import org.code_studio.model.StoredProcedureModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TehnomediaOrderFile extends ExcelFile {
	
	private static final Integer CLIENT_ID = null; // Tehnomedia ID is passed as null, then SP pulls ID from db, based on first row, like in Gigatron, but here its client id, not store id.
	@SuppressWarnings("unused") private static final String  CLIENT_STORE_NAME = null; // STORE NAME is pulled by SP
	private static final Integer CLIENT_STORE_ID   = 1013; // FIXED TEHNOMEDIA STORE ID
	private static final Integer ORDER_TEMPLATE = 3; // TEHNOMEDIA MASS ORDER FILE TEMPLATE
	
	private static final int cellIndexClientNamesSTART = 3; // from this column index CLIENT NAMES start
	private static final int cellIndexClientNamesEND = 73; // this column index CLIENT NAMES ENDS
	
	private Map <String, List<Map<String, Integer>>> mapOrder; // final map of all orders from the loaded file
	private List<String> lstClientClientName; // list of STRING client store names, from the first row in the file
	private Map<String, Integer> orderCnt = null; // One keypair of order per store, within row, such as G-01: 1
	private List<Map<String, Integer>> lstOrders; // List of orderCnt orders
	
	@Autowired
	private StoredProcedureModel spModel;
	
	public TehnomediaOrderFile() {
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
		
		String barcode = null;
		String storeName = null;
		Integer storeOrder = 0;
		for (Map.Entry<String, List<Map<String, Integer>>> entry : mapOrder.entrySet()) {
			barcode = entry.getKey();
			
			for (Map<String, Integer> mapOrders : entry.getValue()) {
				for (String key : mapOrders.keySet()) {
					storeName = key;
					storeOrder = mapOrders.get(key);
					spRes = spModel.call_SPClientOrderAdd(3, CLIENT_ID, barcode, storeName, storeOrder, CLIENT_STORE_ID);
				}
			}
			
		}
		return spRes; // TODO: Vraca samo poslednji result, napraviti da vraca sve
	}
	
	/**
	 * Parses the data from the file, generates maps and lists
	 */
	public void buildFileStructure(XSSFWorkbook workbook) {
		mapOrder = new LinkedHashMap<>();
		lstClientClientName = new ArrayList<>();
		XSSFSheet sheet0 = workbook.getSheetAt(0);
		String barcode = null;

		if (sheet0 != null) {
			for (Row row : sheet0) {
				
				lstOrders = new ArrayList<>();

				for (Cell cell : row) {
					// exit foreach if we reached last cell with CLIENT ID
					if (row.getRowNum() >= cellIndexClientNamesEND) { 
						break;
					}
					
					orderCnt = new LinkedHashMap<>();
					
					// get all store ID values from first row, and all columns with id 3+
					if (row.getRowNum() == 0 && cell .getColumnIndex() >= cellIndexClientNamesSTART && cell.getCellType() == CellType.STRING) {
						lstClientClientName.add(cell.getStringCellValue());
					} else {
						if (row.getRowNum() >= 1 && cell.getColumnIndex() == 2) { // get barcode of item as 3rd column, starting from second row
							double doubleCellValue = cell.getNumericCellValue();
							barcode = new BigDecimal(doubleCellValue).stripTrailingZeros().toPlainString();
						} else if (row.getRowNum() >= 1 && cell.getColumnIndex() >= cellIndexClientNamesSTART // get ordered values 
								&& cell.getCellType() == CellType.NUMERIC && (int)cell.getNumericCellValue() != 0) {
							orderCnt.put(lstClientClientName.get(cell.getColumnIndex() - cellIndexClientNamesSTART), (int)cell.getNumericCellValue());
							lstOrders.add(orderCnt);
						}
					}
				} // for cell
				if (barcode != null) {
					mapOrder.put(barcode, lstOrders);
				}
			} // for row
		}
	}

}
