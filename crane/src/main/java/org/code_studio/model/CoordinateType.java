package org.code_studio.model;

import java.util.Objects;


public enum CoordinateType {

    RM    ("RM", "RM coordinates xxyyyzz"),
    CRANE ("CRANE", "Crane coordinates 00xxyyzz"),
    ;

    private final String id;
    private final String description;

    private CoordinateType (String id, String description) {
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
