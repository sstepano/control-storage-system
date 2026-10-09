package org.code_studio.model;

import java.util.Objects;


public enum StatusCode {

    OK                      ("00", "No error"),
    //UNAVAILABLE             ("01", "Section unavailable"), # Istu vrednost koristimo za automation enabled, a ovo jos nismo videli kao request/response
    REQUESTOK               ("OK", "Nalog obradjen bez gresaka"),
    BADPROFILE              ("KF", "Greska konture na transportnom sistemu (profil, tezina ili greska stopala)"),
    STORAGEOCCUPIED         ("FB", "Odeljak zauzet tokom skladistenja"),
    STORAGEEMPTY            ("FL", "Odeljak prazan nakon uklanjanja"),
    BADLOADUNIT             ("ZF", "Dodeljivanje jedinice za utovar na policu pogresno"),
    STORAGEUNUSED           ("NB", "Nalog za transport iz LVSa za nezauzeto parking mesto"),
    STORAGEUNKNOWN          ("PU", "Nalog za transport iz LVSa za nepoznato parking mesto"),
    TRANSFERDUPLICATE       ("DT", "Dvostruki transportni nalog iz UST-a. Kran (viljuske) je vec zauzet sa transportnim nalogom"),
    TRANSFERREQUESTUNKNOWN  ("NI", "LE nije moguce identifikovati (transportni nalog za NIO-Platz)"),
    AUTOMATIONENABLED       ("01", "Kod pocetka dana postavljamo status na 01, gde kran drugacije funkcionise. Npr kada je enabled, paleta se stavi na 1001, automatski ode na 1005, gde ga prijavi kao 1003"),
    AUTOMATIONDISABLED      ("0x3F", "Proveriti vrednost, postavlja se hex ako barcode ili LHM (ne znamo sta je LHM) nije enabled. Ako je disabled, kada se stavi paleta na 1001, ostane tu i kran ceka prvu komandu bez da bilo sta salje automatski."),
    UNKNOWN                 ("__", "Nepoznat status kod"),
    ;

    private final String id;
    private final String description;

    private StatusCode (String id, String description) {
        this.id = Objects.requireNonNull(id);
        this.description = Objects.requireNonNull(description);
    }

    public String getId() {
        return this.id;
    };

    public String getDescription() {
        return this.description;
    }

}
