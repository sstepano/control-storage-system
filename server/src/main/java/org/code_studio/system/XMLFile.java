package org.code_studio.system;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public abstract class XMLFile {
	
	//TODO, promeniti da uzima iz config-a
	private final String tmpGlobalSavePath = "D:/dev/java-ws/repro-market/tmp/export";
	
	protected Document doc;
	protected String folderName;
	protected String fileName;
	protected DocumentBuilderFactory docFactory; 
	
	/**
	 * 
	 * @param fileTemplateId
	 */
	public XMLFile(Integer fileTemplateId) {
		docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

        doc = docBuilder.newDocument();
	};

	/**
	 * 
	 */
	protected abstract void buildFileStructure(Document doc);
	
	/**
	 * 
	 */
	public void save() throws IOException, TransformerException {
		
		if (folderName == null || fileName == null) {
			throw new RuntimeException("Canot save file! Folder or file name is not set!");
		}
		
        // write dom document to a file
        try (//FileOutputStream output = new FileOutputStream("D:/dev/java-ws/repro-market/tmp/outgoing/test-1.xml")) {
    		FileOutputStream fileOutputStream = new FileOutputStream(tmpGlobalSavePath + "/" + folderName + "/" + fileName)) {
        	
        	TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileOutputStream);

            // hide the standalone="no". Moved to buildFileStructure within the file implementation itself
            //doc.setXmlStandalone(false);
            
            // pretty print XML
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);        
        }
		
	}


}
