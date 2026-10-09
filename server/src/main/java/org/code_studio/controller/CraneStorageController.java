package org.code_studio.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${url.craneStorage}")
public class CraneStorageController {
	
	@Value("${craneStorageSharedFolder}")
	private String craneStorageSharedFolder;

	@Value("${craneStorageFileExtension}")
	private String craneStorageFileExtension;
	
	public CraneStorageController () {}
	
	@PutMapping("paletteStore")
	public void paletteStore (@RequestBody List<String> lstPalettePositions) {
		String fileName = String.format("%s.%s",System.currentTimeMillis(), craneStorageFileExtension);
		
		try (FileWriter writer = new FileWriter(craneStorageSharedFolder + fileName, true)) {
             BufferedWriter bufferedWriter = new BufferedWriter(writer);
             
             for (String palettePosition : lstPalettePositions) {
                 bufferedWriter.write(palettePosition);
                 bufferedWriter.newLine();	
			}
             bufferedWriter.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
