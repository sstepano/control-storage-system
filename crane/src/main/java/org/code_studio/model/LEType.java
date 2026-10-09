package org.code_studio.model;

import java.util.Objects;


public enum LEType {

    LE18      ("18", "TP desc"),
    LE20      ("20", "TP desc"),
    LEUNKNOWN ("__", "LE Unknown"),
    ;

    private final String id;
    private final String description;

    private LEType (String id, String description) {
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
