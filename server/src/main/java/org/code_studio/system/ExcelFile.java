package org.code_studio.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public abstract class ExcelFile {
	
	//TODO, promeniti da uzima iz config-a
	//private final String tmpGlobalLoadPath = "D:/dev/java-ws/repro-market/tmp/import";
	private final String tmpGlobalSavePath = "D:/dev/java-ws/repro-market/tmp/export";
	
	protected XSSFWorkbook workbook;
	protected XSSFSheet sheet;
	protected String folderName;
	protected String fileName;
	
	/**
	 * 
	 * @param fileTemplateId
	 */
	public ExcelFile(Integer fileTemplateId) {
		workbook = new XSSFWorkbook();
	};

	/**
	 * 
	 */
	protected abstract void buildFileStructure(XSSFWorkbook workbook);
	
	/**
	 * 
	 */
	public void save() {
		
		if (folderName == null || fileName == null) {
			throw new RuntimeException("Canot save file! Folder or file name is not set!");
		}
		
        // write dom document to a file
        try (FileOutputStream fileOutputStream = new FileOutputStream(tmpGlobalSavePath + "/" + folderName + "/" + fileName)) {
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	/**
	 * Loads XLSX file and saves it to the table.
	 * Then it calls processing logic to parse the data from the file.
	 */
	public void load(MultipartFile file) {

		// load dom document to a file
        try {
        	FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
            workbook = new XSSFWorkbook(fileInputStream);
            fileInputStream.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}


}
