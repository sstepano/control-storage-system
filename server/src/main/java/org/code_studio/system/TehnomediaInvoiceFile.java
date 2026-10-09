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

public class TehnomediaInvoiceFile extends XMLFile {

	private	Optional<Invoice> optInvoiceHeader;
	List<InvoiceDetail> lstInvoiceDetails;
	Invoice invoice;
	InvoiceDetail invoiceDetail;
	
	public TehnomediaInvoiceFile(ApplicationContext ctx, Integer invoiceId) {
		super(invoiceId);
		//this.ctx = ctx;

		folderName = "tehnomedia";
		fileName = "100280269_%d.xml".formatted(invoiceId);
		
		InvoiceController ctrl = ctx.getBean(InvoiceController.class);
		optInvoiceHeader = ctrl.model.findById(invoiceId);
		invoice = optInvoiceHeader.get();
		
		InvoiceDetailController ctrlDetail = ctx.getBean(InvoiceDetailController.class);
		lstInvoiceDetails = ctrlDetail.model.findAllByInvoiceId(invoiceId);

		buildFileStructure(super.doc);
	}
	
	/**
	 * 
	 */
	public void buildFileStructure(Document doc) {
        Element elFaktura = doc.createElement("faktura");
        doc.appendChild(elFaktura);
        
        Element elZaglavlje = doc.createElement("zaglavlje");
        elFaktura.appendChild(elZaglavlje);

        Element elDokument = doc.createElement("dokument");
        elDokument.setTextContent(invoice.getInvoiceNumber().toString());
        elZaglavlje.appendChild(elDokument);
        
        Element elDatum = doc.createElement("datum");
        elDatum.setTextContent(invoice.getInvoiceDate().toString());
        elZaglavlje.appendChild(elDatum);
        
        Element elValuta = doc.createElement("valuta");
        elValuta.setTextContent("90");
        elZaglavlje.appendChild(elValuta);
        
        Element elPib = doc.createElement("pib");
        elPib.setTextContent(invoice.getClient().getTaxId().toString());
        elZaglavlje.appendChild(elPib);
        
        /*
        Element elCenaBezPdv = doc.createElement("cena_bez_pdv");
        elCenaBezPdv.setTextContent(invoice.getNetAmt().toString());
        elZaglavlje.appendChild(elCenaBezPdv);

        Element elCenaSaPdv = doc.createElement("cena_sa_pdv");
        elCenaSaPdv.setTextContent(invoice.getAmountGross().toString());
        elZaglavlje.appendChild(elCenaSaPdv);
        
        Element elPorudzbenica = doc.createElement("porudzbenica");
        elPorudzbenica.setTextContent(invoice.getInvoiceNumber().toString());
        elZaglavlje.appendChild(elPorudzbenica);
        
        Element elNapomena = doc.createElement("napomena");
        elNapomena.setTextContent(invoice.getDescription());
        elZaglavlje.appendChild(elNapomena);
        */
        
        //detalji
        Element elStavke = doc.createElement("stavke");
        elFaktura.appendChild(elStavke);
        
        //foreach stavka
        for (InvoiceDetail invoiceDetail : lstInvoiceDetails) {
	        Element elStavka = doc.createElement("stavka");
	        //dodavanje stavke tek na kraju popune svih parametara jedne stavke
	        
	        Element elBarcode = doc.createElement("barcode");
	        elBarcode.setTextContent(invoiceDetail.getItem().getItemBarcode() != null 
	        		? invoiceDetail.getItem().getItemBarcode().get(0).getBarcode()
	        		: "");
	        elStavka.appendChild(elBarcode);
	        
	        Element elSifra = doc.createElement("sifra");
	        elSifra.setTextContent(invoiceDetail.getItem().getCode());
	        elStavka.appendChild(elSifra);
	        
	        Element elNaziv = doc.createElement("naziv");
	        elNaziv.setTextContent(invoiceDetail.getItem().getName());
	        elStavka.appendChild(elNaziv);

	        Element elBrend = doc.createElement("brend");
	        elBrend.setTextContent(""); // TODO
	        elStavka.appendChild(elBrend);
	        
	        Element elKolicina = doc.createElement("kolicina");
	        elKolicina.setTextContent(invoiceDetail.getItemQty().toString());
	        elStavka.appendChild(elKolicina);
	        
	        Element elStavkaJedinicnaCena = doc.createElement("cena");
	        elStavkaJedinicnaCena.setTextContent(invoiceDetail.getUnitOfMeasureNetValue().toString());
	        elStavka.appendChild(elStavkaJedinicnaCena);

	        Element elPdv = doc.createElement("stopapdv");
	        elPdv.setTextContent(invoiceDetail.getVatRate().toString());
	        elStavka.appendChild(elPdv);

	        Element elRabat = doc.createElement("rabat");
	        elRabat.setTextContent(invoiceDetail.getAmountDiscount().toString());
	        elStavka.appendChild(elRabat);
	        
	        Element elCena = doc.createElement("ukupno");
	        elCena.setTextContent(invoiceDetail.getAmountNet().toString());
	        elStavka.appendChild(elCena);        
	        	        
	        elStavke.appendChild(elStavka);
        }
	}

}
