package org.code_studio.system;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;

import org.code_studio.database.Invoice;
import org.code_studio.database.InvoiceDetail;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.code_studio.controller.InvoiceController;
import org.code_studio.controller.InvoiceDetailController;
import org.springframework.context.ApplicationContext;

public class InvoiceExcelFile extends ExcelFile {

	private	Optional<Invoice> optInvoiceHeader;
	List<InvoiceDetail> lstInvoiceDetails;
	Invoice invoice;
	InvoiceDetail invoiceDetail;
	
	public InvoiceExcelFile(ApplicationContext ctx, Integer invoiceId) {
		super(invoiceId);
		//this.ctx = ctx;

		folderName = "excel";
		fileName = "test1.xlsx".formatted(invoiceId);
		
		InvoiceController ctrl = ctx.getBean(InvoiceController.class);
		optInvoiceHeader = ctrl.model.findById(invoiceId);
		invoice = optInvoiceHeader.get();
		
		InvoiceDetailController ctrlDetail = ctx.getBean(InvoiceDetailController.class);
		lstInvoiceDetails = ctrlDetail.model.findAllByInvoiceId(invoiceId);

		buildFileStructure(super.workbook);
	}

	@Override
	protected void buildFileStructure(XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		
        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
        data.put("2", new Object[] {1, "Name1", "Lastname1"});
        data.put("3", new Object[] {2, "Name2", "Lastname2"});
          
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            XSSFRow row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
	}
	


}
