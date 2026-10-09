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

public class GigatronInvoiceFile extends XMLFile {

	private	Optional<Invoice> optInvoiceHeader;
	List<InvoiceDetail> lstInvoiceDetails;
	Invoice invoice;
	InvoiceDetail invoiceDetail;
	
	public GigatronInvoiceFile(ApplicationContext ctx, Integer invoiceId) {
		super(invoiceId);
		//this.ctx = ctx;

		folderName = "gigatron";
		fileName = "102778428_%d.xml".formatted(invoiceId);
		
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

        Element elRoot = doc.createElement("root");
        doc.appendChild(elRoot);
        
        Element elFaktura = doc.createElement("faktura");
        elRoot.appendChild(elFaktura);
        
        Element elZaglavlje = doc.createElement("zaglavlje");
        elFaktura.appendChild(elZaglavlje);
        
        Element elKomitent = doc.createElement("komitent");
        elKomitent.setTextContent(invoice.getClient().getFullName());
        elZaglavlje.appendChild(elKomitent);

        Element elDokument = doc.createElement("dokument");
        elDokument.setTextContent(invoice.getInvoiceNumber() + "/" + String.valueOf(invoice.getYear()).substring(2, 4));
        elZaglavlje.appendChild(elDokument);
        
        Element elDatum = doc.createElement("datum");
        //elDatum.setTextContent(invoice.getInvoiceDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
        elDatum.setTextContent(invoice.getInvoiceDate().toString());
        elZaglavlje.appendChild(elDatum);
        
        Element elValuta = doc.createElement("valuta");
        elValuta.setTextContent("RSD");
        elZaglavlje.appendChild(elValuta);
        
        Element elCenaBezPdv = doc.createElement("cena_bez_pdv");
        elCenaBezPdv.setTextContent(invoice.getNetAmt().toString());
        elZaglavlje.appendChild(elCenaBezPdv);

        Element elCenaSaPdv = doc.createElement("cena_sa_pdv");
        elCenaSaPdv.setTextContent(invoice.getAmountGross().toString());
        elZaglavlje.appendChild(elCenaSaPdv);
        
        Element elPib = doc.createElement("pib");
        elPib.setTextContent(invoice.getClient().getTaxId().toString());
        elZaglavlje.appendChild(elPib);
        
        Element elPorudzbenica = doc.createElement("porudzbenica");
        elPorudzbenica.setTextContent(invoice.getInvoiceNumber().toString());
        elZaglavlje.appendChild(elPorudzbenica);
        
        Element elNapomena = doc.createElement("napomena");
        elNapomena.setTextContent(invoice.getDescription());
        elZaglavlje.appendChild(elNapomena);
        
        //detalji
        Element elStavke = doc.createElement("stavke");
        elFaktura.appendChild(elStavke);
        
        //foreach stavka
        Integer i = 1;
        for (InvoiceDetail invoiceDetail : lstInvoiceDetails) {
	        Element elStavka = doc.createElement("stavka");
	        //dodavanje stavke tek na kraju popune svih parametara jedne stavke
	        
	        Element elPozicija = doc.createElement("pozicija");
	        elPozicija.setTextContent(i.toString());
	        elStavka.appendChild(elPozicija);
	        
	        Element elSifra = doc.createElement("sifra");
	        elSifra.setTextContent(invoiceDetail.getItem().getCode());
	        elStavka.appendChild(elSifra);
	        
	        Element elBrend = doc.createElement("brend");
	        elBrend.setTextContent(""); // TODO
	        elStavka.appendChild(elBrend);
	        
	        Element elNaziv = doc.createElement("naziv");
	        elNaziv.setTextContent(invoiceDetail.getItem().getName());
	        elStavka.appendChild(elNaziv);
	        
	        Element elKolicina = doc.createElement("kolicina");
	        elKolicina.setTextContent(invoiceDetail.getItemQty().toString());
	        elStavka.appendChild(elKolicina);
	        
	        Element elPdv = doc.createElement("pdv");
	        elPdv.setTextContent(invoiceDetail.getVatRate().toString());
	        elStavka.appendChild(elPdv);
	        
	        Element elCena = doc.createElement("cena");
	        elCena.setTextContent(invoiceDetail.getAmountGross().toString());
	        elStavka.appendChild(elCena);        
	        
	        Element elStavkaCenaBezPdv = doc.createElement("cena_bez_pdv");
	        elStavkaCenaBezPdv.setTextContent(invoiceDetail.getAmountNet().toString());
	        elStavka.appendChild(elStavkaCenaBezPdv);
	        
	        Element elEan = doc.createElement("ean");
	        elEan.setTextContent(invoiceDetail.getItem().getItemBarcode() != null 
	        		? invoiceDetail.getItem().getItemBarcode().get(0).getBarcode()
	        		: "");
	        elStavka.appendChild(elEan);
	        
	        Element elJm = doc.createElement("jm");
	        elJm.setTextContent("KOM"); // TODO - ubaciti pravi unit of measure iz item.unitID
	        elStavka.appendChild(elJm);
	        
	        Element elGarancija = doc.createElement("garancija");
	        elStavka.appendChild(elGarancija);
	        
	        elStavke.appendChild(elStavka);
			i++;
        }
	}

}
