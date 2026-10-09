package org.code_studio.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public abstract class Excel97File {
	
	//TODO, promeniti da uzima iz config-a
	//private final String tmpGlobalLoadPath = "D:/dev/java-ws/repro-market/tmp/import";
	private final String tmpGlobalSavePath = "D:/dev/java-ws/repro-market/tmp/export";
	
	protected HSSFWorkbook workbook;
	protected HSSFSheet sheet;
	protected String folderName;
	protected String fileName;
	
	/**
	 * 
	 * @param fileTemplateId
	 */
	public Excel97File(Integer fileTemplateId) {
		workbook = new HSSFWorkbook();
	};

	/**
	 * 
	 */
	protected abstract void buildFileStructure(HSSFWorkbook workbook);
	
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
		/*
		if (folderName == null || fileName == null) {
			throw new RuntimeException("Canot load file! Folder or file name is not set!");
		}
		*/
		
        // load dom document to a file
        try {
        	FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
            workbook = new HSSFWorkbook(fileInputStream);
            fileInputStream.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}


}
