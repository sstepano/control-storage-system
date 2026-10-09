package org.code_studio.system;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.code_studio.database.FileBankStatement;
import org.code_studio.model.FileBankStatementModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class BankStatementLoader {
	
	@Autowired
	FileBankStatementModel model;
	
	private FileReader fileReader;
	
	public BankStatementLoader (FileBankStatementModel model) {
		this.model = model;
	}
	
	public void loadFile (File file) {
		//filename = "D:\\dev\\java-ws\\repro-market\\docs\\files_import_export\\bank_statement\\CREDITAGRICOLE 280223.txt";

		try {
			fileReader = new FileReader(file.getAbsoluteFile());
			List<FileBankStatement> lstFileBankStatement = new CsvToBeanBuilder<FileBankStatement>(fileReader)
				       .withType(FileBankStatement.class)
				       .withSeparator('#')
				       .withSkipLines(1)
				       .build().parse();
			
			if(lstFileBankStatement.size() > 0) {
				model.saveAll(lstFileBankStatement);
			}
			
			fileReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
