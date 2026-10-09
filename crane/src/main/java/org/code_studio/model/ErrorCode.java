package org.code_studio.model;

import java.util.Objects;


public enum ErrorCode {

    OK             ("00", "No error"),
    WRONGID        ("01", "Wrong or unknown telegram identifier (ID)"),
    WRONGORDINALID ("02", "Wrong or incorrect telegram sequence identifier (ORDINALID)"),
    REPEAT         ("03", "Telegram repeated"),
	UNKNOWN        ("__", "ErrorCode unknown");

    private final String id;
    private final String description;

    private ErrorCode (String id, String description) {
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
