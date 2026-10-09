package org.code_studio.model;

import java.util.Objects;

/** UST => LVS
 * Messages created by UST and sent separately to LVS server, as a response to LVS Client requests
 */
public enum USTRequestMessageType implements MessageType {
    
    DAYSTART    ("11", "Day start"),
    DAYEND      ("12", "Work end"),
    STATUS      ("13", "Status request"),
    LOADER      ("14", "Loading point - loaded (paleta stavljena na 1001/1003)"),
    RBGLOADED   ("15", "Runner (LE) transferred palette to RBG (forks)"),
    RBGUNLOADED ("16", "RBG unloaded palette from chains to runner (LE)"),
    COMPLETED   ("17", "Zavrsen transportni nalog"),
    REMOVAL     ("18", "Izlaz iz skladista (Paleta se skida sa 3005)"),
    SYNC        ("19", "Data synchronization request"),
    ;

    private final String id;
    private final String description;

    private USTRequestMessageType (String id, String description) {
        this.id = id;
        this.description = Objects.requireNonNull(description);
    }

    public String getId() {
        return this.id;
    };

    public String getDescription() {
        return this.description;
    }

}
