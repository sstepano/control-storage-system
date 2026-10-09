package org.code_studio.model;

import java.util.Objects;

/** LVS => UST
 * 
 */
public enum LVSRequestMessageType implements MessageType {

    DAYSTART ("DA", "Day start"),
    DAYEND   ("DB", "Day end"),
    STATUS   ("DC", "Status request"),
    TRANSFER ("DD", "Transfer request"),
    SYNC     ("DE", "Data synchronization request");

    private final String id;
    private final String description;

    private LVSRequestMessageType (String id, String description) {
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
