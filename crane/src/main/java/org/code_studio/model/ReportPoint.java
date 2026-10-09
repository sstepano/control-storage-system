package org.code_studio.model;

import java.util.Objects;


public enum ReportPoint implements SystemComponent {

    RP1001 ("1001", "Starting position on loading palettes"),
    RP1003 ("1003", "Position in front of the control room"),
    RP1004 ("1004", "Bad palette (skart) - exit"),
    RP1005 ("1005", "TP desc"),
    RP3005 ("3005", "Last (exit) position on the conveyor belt in warehouse exit flow"),
    RP5005 ("5005", "Next to last (exit) position on the conveyor belt. Crane reports this when there is already palette on 300, not yet removed."),
    RP5004 ("5004", "TP desc"),
    RP7004 ("7004", "TP desc"),
    RP7005 ("7005", "Pretpostavljam da je ovo kran 2, kada vile spuste paletu na lance krana"),
    RPRGB1 ("R001", "Crane I forks"),
    RPRGB2 ("R002", "Crane II forks"),
    RPUNKNOWN ("UNKN", "Unknown report point"),
    ;

    private final String id;
    private final String description;

    private ReportPoint (String id, String description) {
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
