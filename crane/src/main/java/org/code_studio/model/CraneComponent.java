package org.code_studio.model;

import java.util.Objects;


public enum CraneComponent {

    LVS1 ("LVS1", "Windows Service client and server"),
    UST1 ("UST1", "Crane server"),
    ;

    private final String id;
    private final String description;

    private CraneComponent (String id, String description) {
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
