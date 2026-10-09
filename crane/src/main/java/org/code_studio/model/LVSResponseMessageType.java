package org.code_studio.model;

import java.util.Objects;

/** LVS => UST
 * 
 */
public enum LVSResponseMessageType implements MessageType {

    START       ("AA", "Work start confirmation"),
    END         ("AB", "Work end confirmation"),
    STATUS      ("AC", "Status request confirmation"),
    LOADER      ("AD", "(Ukljuceno) Poruka jedinice za utovar, paleta postavljena na 1001"),
    RBGLOADED   ("AE", "Runner (LE) transferred palette to RBG (forks)"),
    RBGUNLOADED ("AF", "RBG unloaded palette from chains to runner (LE)"),
    COMPLETED   ("AG", "Zavrsen transportni nalog"),
    REMOVAL     ("AH", "Izlaz iz skladista (Paleta se skida sa 3005)"),
    SYNC        ("AI", "Data synchronization request")
    ;

    private final String id;
    private final String description;

    private LVSResponseMessageType (String id, String description) {
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
