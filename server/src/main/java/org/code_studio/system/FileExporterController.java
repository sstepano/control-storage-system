package org.code_studio.system;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.fileExporter}")
public class FileExporterController {
	
	@Value("${fileExporterSaveFolder}")
	private String fileExporterSaveFolder;
	
	@Autowired
	ApplicationContext ctx;
	
	public FileExporterController () {}
	
	@PostMapping("export/{fileTemplateId}/{id}")
	public ResponseEntity<Object> export (@PathVariable Integer fileTemplateId, @PathVariable Integer id) throws IOException, TransformerException {
		
		Resource resource = null;
		
		switch(fileTemplateId) {
			case 1: // INVOICE
				InvoiceFile invoiceFile = new InvoiceFile(ctx, id);
				invoiceFile.save();
			break;
			case 2: // GIGATRON INVOICE
				GigatronInvoiceFile gigatronInvoiceFile = new GigatronInvoiceFile(ctx, id);
				gigatronInvoiceFile.save();
			break;
			case 3: // INVOICE XLSX
				InvoiceExcelFile invoiceExcelFile = new InvoiceExcelFile(ctx, id);
				invoiceExcelFile.save();
				
				Path path = Paths.get("D:\\dev\\java-ws\\repro-market\\tmp\\export\\excel\\test1.xlsx");
				
				try {
					resource = new UrlResource(path.toUri());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				
				return ResponseEntity.ok()
						.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
						.body(resource);				
			//break;
			case 4: // TEHNOMEDIA INVOICE XML
				TehnomediaInvoiceFile tehnomediaInvoiceFile = new TehnomediaInvoiceFile(ctx, id);
				tehnomediaInvoiceFile.save();
			break;
			case 5: // XML e-Faktura
				ElectronicInvoiceFile electronicInvoiceFile = new ElectronicInvoiceFile(ctx, id);
				electronicInvoiceFile.save();
				// TODO: electronicInvoiceFile.sendToExternalWebService()
			break;
			default:
			break;
		}
		return null;
	}
	
	
	/**
	 * FILE DOWNLOAD
	 *
	public ResponseEntity<T> getLocalFile(String fileName) {
		Path path = Paths.get(fileExporterSaveFolder + fileName);
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	*/
}
