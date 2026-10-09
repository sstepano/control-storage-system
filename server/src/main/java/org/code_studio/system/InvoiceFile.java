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

public class InvoiceFile extends XMLFile {

	private	Optional<Invoice> optInvoiceHeader;
	List<InvoiceDetail> lstInvoiceDetails;
	Invoice invoice;
	InvoiceDetail invoiceDetail;
	
	public InvoiceFile(ApplicationContext ctx, Integer invoiceId) {
		super(invoiceId);
		//this.ctx = ctx;

		folderName = "financial_accounting";
		fileName = "faktura_%d.xml".formatted(invoiceId);
		
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

        Element elDokumenti = doc.createElement("dokumenti");
        doc.appendChild(elDokumenti);
        
        Element elZaglavlje = doc.createElement("zaglavlje");
        elDokumenti.appendChild(elZaglavlje);

        Element elIdDokumenta = doc.createElement("IDDokumenta");
        elIdDokumenta.setTextContent(invoice.getId().toString());
        elZaglavlje.appendChild(elIdDokumenta);
        
        Element elBrojDokumenta = doc.createElement("BrojDokumenta");
        elBrojDokumenta.setTextContent(invoice.getInvoiceNumber().toString());
        elZaglavlje.appendChild(elBrojDokumenta);
        
        Element elBarKod = doc.createElement("BarKod");
        elZaglavlje.appendChild(elBarKod);
        
        Element elVrstaDokumenta = doc.createElement("VrstaDokumenta");
        elZaglavlje.appendChild(elVrstaDokumenta);
                
        Element elDatumDokumenta = doc.createElement("DatumDokumenta");
        elZaglavlje.appendChild(elDatumDokumenta);
 
        Element elDatumPrometa = doc.createElement("DatumPrometa");
        elZaglavlje.appendChild(elDatumPrometa);

        Element elDatumValute = doc.createElement("DatumValute");
        elZaglavlje.appendChild(elDatumValute);

        Element elIzlazniMagacinSifra = doc.createElement("IzlazniMagacinSifra");
        elZaglavlje.appendChild(elIzlazniMagacinSifra);
        
        Element elIzlazniMagacinNaziv = doc.createElement("IzlazniMagacinNaziv");
        elZaglavlje.appendChild(elIzlazniMagacinNaziv);
        
        Element elUlazniMagacinSifra = doc.createElement("UlazniMagacinSifra");
        elZaglavlje.appendChild(elUlazniMagacinSifra);
        
        Element elUlazniMagacinNaziv = doc.createElement("UlazniMagacinNaziv");
        elZaglavlje.appendChild(elUlazniMagacinNaziv);
        
        Element elPartnerSifra = doc.createElement("PartnerSifra");
        elZaglavlje.appendChild(elPartnerSifra);
        
        Element elPartnerNaziv = doc.createElement("PartnerNaziv");
        elZaglavlje.appendChild(elPartnerNaziv);
        
        Element elPartnerPIB = doc.createElement("PartnerPIB");
        elZaglavlje.appendChild(elPartnerPIB);
        
        Element elPartnerMaticniBroj = doc.createElement("PartnerMaticniBroj");
        elZaglavlje.appendChild(elPartnerMaticniBroj);
        
        Element elPartnerSediste = doc.createElement("PartnerSediste");
        elZaglavlje.appendChild(elPartnerSediste);
        
        Element elPartnerPostanskiBroj = doc.createElement("PartnerPostanskiBroj");
        elZaglavlje.appendChild(elPartnerPostanskiBroj);
        
        Element elPartnerAdresa = doc.createElement("PartnerAdresa");
        elZaglavlje.appendChild(elPartnerAdresa);
        
        Element elPravnoLice = doc.createElement("PravnoLice");
        elZaglavlje.appendChild(elPravnoLice);
        
        Element elPDVobveznik = doc.createElement("PDVobveznik");
        elZaglavlje.appendChild(elPDVobveznik);
        
        Element elZbirniSifra = doc.createElement("ZbirniSifra");
        elZaglavlje.appendChild(elZbirniSifra);
        
        Element elZbirniNaziv = doc.createElement("ZbirniNaziv");
        elZaglavlje.appendChild(elZbirniNaziv);
        
        Element elZbirniPIB = doc.createElement("ZbirniPIB");
        elZaglavlje.appendChild(elZbirniPIB);
        
        Element elZbirniMaticniBroj = doc.createElement("ZbirniMaticniBroj");
        elZaglavlje.appendChild(elZbirniMaticniBroj);
        
        Element elZbirniSediste = doc.createElement("ZbirniSediste");
        elZaglavlje.appendChild(elZbirniSediste);
        
        Element elZbirniPostanskiBroj = doc.createElement("ZbirniPostanskiBroj");
        elZaglavlje.appendChild(elZbirniPostanskiBroj);
        
        Element elZbirniAdresa = doc.createElement("ZbirniAdresa");
        elZaglavlje.appendChild(elZbirniAdresa);
        
        Element elOperater = doc.createElement("Operater");
        elZaglavlje.appendChild(elOperater);
        
        Element elVrstaFakture = doc.createElement("VrstaFakture");
        elZaglavlje.appendChild(elVrstaFakture);
        
        Element elKurs = doc.createElement("Kurs");
        elZaglavlje.appendChild(elKurs);
        
        //detalji
        Element elStavke = doc.createElement("Stavke");
        elZaglavlje.appendChild(elStavke);
        
        /*
        Element elStavke = doc.createElement("stavke");
        elFaktura.appendChild(elStavke);
        Element elStavka = doc.createElement("stavka");
        Element elPozicija = doc.createElement("pozicija");
        elPozicija.appendChild(elStavka);
        Element elSifra = doc.createElement("sifra");
        elStavka.appendChild(elSifra);
        Element elBrend = doc.createElement("brend");
        elStavka.appendChild(elBrend);
        Element elNaziv = doc.createElement("naziv");
        elStavka.appendChild(elNaziv);
        Element elKolicina = doc.createElement("kolicina");
        elStavka.appendChild(elKolicina);
        Element elPdv = doc.createElement("pdv");
        elStavka.appendChild(elPdv);
        Element elStavkaCenaBezPdv = doc.createElement("cena_bez_pdv");
        elStavka.appendChild(elStavkaCenaBezPdv);
        Element elEan = doc.createElement("ean");
        elStavka.appendChild(elEan);
        Element elJm = doc.createElement("jm");
        elStavka.appendChild(elJm);
        Element elGarancija = doc.createElement("garancija");
        elStavka.appendChild(elGarancija);
        elStavke.appendChild(elStavka);
        */
	}

}
