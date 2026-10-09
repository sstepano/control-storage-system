package org.code_studio.system;

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
public class GigatronOrderFile extends ExcelFile {
	
	private static final Integer CLIENT_ID = 7493; // GIGATRON ID
	private static final Integer ORDER_TEMPLATE = 1; // GIGATRON MASS ORDER FILE TEMPLATE
	
	private Map <String, List<Map<String, Integer>>> mapOrder; // final map of all orders from the loaded file
	private List<String> lstClientStoreName; // list of STRING client store names, from the first row in the file
	private Map<String, Integer> orderCnt = null; // One keypair of order per store, within row, such as G-01: 1
	private List<Map<String, Integer>> lstOrders; // List of orderCnt orders
	
	@Autowired
	private StoredProcedureModel spModel;
	
	public GigatronOrderFile() {
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
					spRes = spModel.call_SPClientOrderAdd(2, CLIENT_ID, barcode, storeName, storeOrder, 0);
				}
			}
			
		}
		return spRes; // TODO: Vraca samo poslednji result, napraviti da vraca sve
	}
	
	/**
	 * Parses the data from the file, generates maps and lists
	 */
	public void buildFileStructure(XSSFWorkbook workbook) {
		int cellIndexStoreNumbers = 6; // from this column index store numbers start
		mapOrder = new LinkedHashMap<>();
		lstClientStoreName = new ArrayList<>();
		XSSFSheet sheet0 = workbook.getSheetAt(0);
		String barcode = null;

		if (sheet0 != null) {
			for (Row row : sheet0) {
				
				lstOrders = new ArrayList<>();

				for (Cell cell : row) {
					orderCnt = new LinkedHashMap<>();
					
					// get all old store ID values from second row, and all columns with id 3+
					if (row.getRowNum() == 0 && cell .getColumnIndex() >= cellIndexStoreNumbers && cell.getCellType() == CellType.STRING) {
						lstClientStoreName.add(cell.getStringCellValue());
					} else {
						if (row.getRowNum() >= 1 && cell.getColumnIndex() == 0) { // get barcode of item as first column, starting from second row
							barcode = cell.getStringCellValue();
						} else if (row.getRowNum() >= 1 && cell.getColumnIndex() >= cellIndexStoreNumbers // get ordered values 
								&& cell.getCellType() == CellType.NUMERIC && (int)cell.getNumericCellValue() != 0) {
							orderCnt.put(lstClientStoreName.get(cell.getColumnIndex() - cellIndexStoreNumbers), (int)cell.getNumericCellValue());
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
