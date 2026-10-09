package org.code_studio.system;

import java.util.List;
import java.util.Optional;

import org.code_studio.database.Invoice;
import org.code_studio.database.InvoiceDetail;
import org.code_studio.controller.InvoiceController;
import org.code_studio.controller.InvoiceDetailController;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ElectronicInvoiceFile extends XMLFile {

	private	Optional<Invoice> optInvoiceHeader;
	List<InvoiceDetail> lstInvoiceDetails;
	Invoice invoice;
	InvoiceDetail invoiceDetail;
	
	public ElectronicInvoiceFile(ApplicationContext ctx, Integer invoiceId) {
		super(invoiceId);
		//this.ctx = ctx;

		folderName = "electronic-invoice";
		fileName = "%d.xml".formatted(invoiceId);
		
		InvoiceController ctrl = ctx.getBean(InvoiceController.class);
		optInvoiceHeader = ctrl.model.findById(invoiceId);
		
		if (!optInvoiceHeader.isEmpty()) {
			invoice = optInvoiceHeader.get();
		
			InvoiceDetailController ctrlDetail = ctx.getBean(InvoiceDetailController.class);
			lstInvoiceDetails = ctrlDetail.model.findAllByInvoiceId(invoiceId);
	
			buildFileStructure(super.doc);
		} else {
			throw new RuntimeException("Invoice with ID: " + invoiceId + " does not exist!" );
		}
	}
	
	/**
	 * 
	 */
	public void buildFileStructure(Document doc) {
		docFactory.setNamespaceAware(true); // needed for namespaces
		doc.setXmlStandalone(true);
        Element elInvoice = doc.createElement("Invoice");
        elInvoice.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cec", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
        elInvoice.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
        elInvoice.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
        elInvoice.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        elInvoice.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
        elInvoice.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sbt", "http://mfin.gov.rs/srbdt/srbdtext");
        elInvoice.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:cec", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
        elInvoice.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2");        
        doc.appendChild(elInvoice);
        
        Element elCbcCustomizationId = doc.createElement("cbc:CustomizationID");
        elCbcCustomizationId.setTextContent("urn:cen.eu:en16931:2017#compliant#urn:mfin.gov.rs:srbdt:2022#conformant#urn:mfin.gov.rs:srbdtext:2022");
        elInvoice.appendChild(elCbcCustomizationId);

        Element elCbcId = doc.createElement("cbc:ID");
        elCbcId.setTextContent("1");
        elInvoice.appendChild(elCbcId);
        
        Element elCbcIssueDate = doc.createElement("cbc:IssueDate");
        elCbcIssueDate.setTextContent(invoice.getInvoiceDate().toString());
        elInvoice.appendChild(elCbcIssueDate);
        
        Element elCbcDueDate = doc.createElement("cbc:DueDate");
        elCbcDueDate.setTextContent(invoice.getDeliveryDate() == null ? "" : invoice.getDeliveryDate().toString());
        elInvoice.appendChild(elCbcDueDate);
     
        Element elCbcInvoiceTypeCode = doc.createElement("cbc:InvoiceTypeCode");
        elCbcInvoiceTypeCode.setTextContent("380"); // TODO: other codes for zaduzenje i avans
        elInvoice.appendChild(elCbcInvoiceTypeCode);
        
        Element elCbcNote = doc.createElement("cbc:Note");
        elCbcNote.setTextContent(invoice.getDescription()); 
        elInvoice.appendChild(elCbcNote);
        
        Element elCbcDocumentCurrencyCode = doc.createElement("cbc:DocumentCurrencyCode");
        elCbcDocumentCurrencyCode.setTextContent("RSD"); 
        elInvoice.appendChild(elCbcDocumentCurrencyCode);
        
        Element elCbcBuyerReference = doc.createElement("cbc:BuyerReference");
        elCbcBuyerReference.setTextContent(invoice.getClientStoreName()); 
        elInvoice.appendChild(elCbcBuyerReference);
        
        Element elCacInvoicePeriod = doc.createElement("cac:InvoicePeriod");
        Element elCbcDescriptionCode = doc.createElement("cbc:DescriptionCode");
        elCbcDescriptionCode.setTextContent("3");
        elCacInvoicePeriod.appendChild(elCbcDescriptionCode);
        elInvoice.appendChild(elCacInvoicePeriod);
        
        Element elCacDespatchDocumentReference = doc.createElement("cac:DespatchDocumentReference");
        Element elCbcDespatchId = doc.createElement("cbc:ID");
        elCbcDespatchId.setTextContent("1"); 
        elCacDespatchDocumentReference.appendChild(elCbcDespatchId);
        elInvoice.appendChild(elCacDespatchDocumentReference);
        
        Element elCacAccountingCustomerParty = doc.createElement("cac:AccountingCustomerParty");
        Element elCacParty = doc.createElement("cac:Party");
        Element elCbcEndpointID = doc.createElement("EndpointID");
        elCbcEndpointID.setAttribute("schemeID", "9948");
        Element elCacPartyIdentification = doc.createElement("cac:PartyIdentification");
        Element elCbcPartyIdentificationId = doc.createElement("cbc:ID");
        Element elCacPartyName = doc.createElement("cac:PartyPartyName");
        Element elCbcPartyNameName = doc.createElement("cbc:Name");
        Element elCacPostalAddress = doc.createElement("cac:PostalAddress");
        Element elCbcStreetName = doc.createElement("cbc:StreetName");
        Element elCbcCityName = doc.createElement("cbc:CityName");
        Element elCbcPostalZone = doc.createElement("cbc:PostalZone");
        Element elCacCountry = doc.createElement("cac:Country");
        Element elCbcIdentificationCode = doc.createElement("cbc:IdentificationCode");
        Element elCacPartyTaxScheme = doc.createElement("cac:PartyTaxScheme");
        Element elCbcCompanyID = doc.createElement("cbc:CompanyID");
        Element elCacTaxScheme = doc.createElement("cac:TaxScheme");
        Element elCbcTaxSchemeID = doc.createElement("cbc:ID");
        Element elCacPartyLegalEntity = doc.createElement("cac:PartyLegalEntity");
        Element elCbcRegistrationName = doc.createElement("cbc:RegistrationName");
        Element elCbcPartyLegalEntityCompanyID = doc.createElement("cbc:CompanyID");
        Element elCacContact = doc.createElement("cac:Contact");
        Element elCbcTelephone = doc.createElement("cbc:Telephone");
        Element elCbcElectronicMail = doc.createElement("cbc:ElectronicMail");
        
        elCacPartyIdentification.appendChild(elCbcPartyIdentificationId);
        elCacParty.appendChild(elCbcEndpointID);
        elCacParty.appendChild(elCacPartyIdentification);
        elCacPartyName.appendChild(elCbcPartyNameName);
        elCacParty.appendChild(elCacPartyName);
        elCacPostalAddress.appendChild(elCbcStreetName);
        elCacPostalAddress.appendChild(elCbcCityName);
        elCacPostalAddress.appendChild(elCbcPostalZone);
        elCacCountry.appendChild(elCbcIdentificationCode);
        elCacPostalAddress.appendChild(elCacCountry);
        elCacParty.appendChild(elCacPostalAddress);
        elCacPartyTaxScheme.appendChild(elCbcCompanyID);
        elCacTaxScheme.appendChild(elCbcTaxSchemeID);
        elCacPartyTaxScheme.appendChild(elCacTaxScheme);
        elCacParty.appendChild(elCacPartyTaxScheme);
        elCacPartyLegalEntity.appendChild(elCbcRegistrationName);
        elCacPartyLegalEntity.appendChild(elCbcPartyLegalEntityCompanyID);
        elCacParty.appendChild(elCacPartyLegalEntity);
        elCacContact.appendChild(elCbcTelephone);
        elCacContact.appendChild(elCbcElectronicMail);
        elCacParty.appendChild(elCacContact);
        elCacAccountingCustomerParty.appendChild(elCacParty);
        elInvoice.appendChild(elCacAccountingCustomerParty);
        
        /***
        Element elCacDelivery = doc.createElement("cac:Delivery");
        Element elCbcActualDeliveryDate = doc.createElement("cbc:ActualDeliveryDate");
        elCbcActualDeliveryDate.setTextContent(invoice.getDeliveryDate().toString());
        Element elCacDeliveryLocation = doc.createElement("cac:DeliveryLocation");
        Element elCacAddress = doc.createElement("cac:Address");
        Element elCbcStreetName = doc.createElement("cbc:StreetName");
        elCbcStreetName.setTextContent(invoice.getDeliveryAddressGoods());
        ***/
        
        
	}

}
