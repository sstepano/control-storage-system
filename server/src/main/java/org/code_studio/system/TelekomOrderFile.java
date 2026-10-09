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
/**
 * One client, multiple stores
 * Stores are in column 1, items are in row 1
 */
@Component
public class TelekomOrderFile extends ExcelFile {
	
	private static final Integer CLIENT_ID = 3415; // TELEKOM ID
	private static final Integer ORDER_TEMPLATE = 4; // TELEKOM MASS ORDER FILE TEMPLATE
	
	private static final int cellIndexItemBarcodesSTART = 3; // from this column index Item Barcodes start
	
	private Map <String, List<Map<String, Integer>>> mapOrder; // final map of all orders from the loaded file <Barcode, <StoreName, OrderNumber>>
	private List<String> lstItemBarcodes; // list of NUMERIC item barcodes, from the 3rd row in the file
	private Map<String, Integer> orderCnt = null; // One keypair of order per store, within row, such as G-01: 1
	private List<Map<String, Integer>> lstOrders; // List of orderCnt orders
	
	@Autowired
	private StoredProcedureModel spModel;
	
	public TelekomOrderFile() {
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
			storeName = entry.getKey();
			
			for (Map<String, Integer> mapOrders : entry.getValue()) {
				for (String key : mapOrders.keySet()) {
					barcode = key;
					storeOrder = mapOrders.get(key);
					spRes = spModel.call_SPClientOrderAdd(4, CLIENT_ID, barcode, storeName, storeOrder, 0);
				}
			}
			
		}
		System.out.println(mapOrder);
		return spRes; // TODO: Vraca samo poslednji result, napraviti da vraca sve
	}
	
	/**
	 * Parses the data from the file, generates maps and lists
	 */
	public void buildFileStructure(XSSFWorkbook workbook) {
		XSSFSheet sheet0 = workbook.getSheetAt(0);
		mapOrder = new LinkedHashMap<>();
		lstItemBarcodes = new ArrayList<>();
		String storeName = null;
		String barcode = null;
		int cellValueIndex = 0; // used to fetch barcodes

		if (sheet0 != null) {
			for (Row row : sheet0) {
				lstOrders = new ArrayList<>();
				cellValueIndex = 0;
				for (Cell cell : row) {
					orderCnt = new LinkedHashMap<>();
					// parse
					if (row.getRowNum() == 2 && cell .getColumnIndex() >= cellIndexItemBarcodesSTART && cell.getCellType() == CellType.NUMERIC) {
						lstItemBarcodes.add(new BigDecimal(cell.getNumericCellValue()).stripTrailingZeros().toPlainString());
					} else {
						if (row.getRowNum() >= 4 && cell.getColumnIndex() == 1) { // get store name from rows
							storeName = cell.getStringCellValue();
						} else if (row.getRowNum() >= 4 && cell.getColumnIndex() >= 6 // get ordered values 
								&& cell.getCellType() == CellType.NUMERIC /*&& (int)cell.getNumericCellValue() != 0*/) {
							barcode = lstItemBarcodes.get(cellValueIndex);
							orderCnt.put(barcode, (int)cell.getNumericCellValue());
							lstOrders.add(orderCnt);
							cellValueIndex++;
						}
					}
				} // for cell
				///if (!barcode.equalsIgnoreCase(null)) {
					mapOrder.put(storeName, lstOrders);
				///}
			} // for row
		}
		//System.out.println(mapOrder);
	}

}
