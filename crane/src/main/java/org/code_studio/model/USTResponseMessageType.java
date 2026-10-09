package org.code_studio.model;

import java.util.Objects;

/** LVS => UST
 * Messages created by LVS and sent to UST server, as a response to UST server response confirmations
 * This is considered as confirmation of the confirmation
 * For example, LVS client sends DAY START, client receives confirmation message,
 * then, UST server sends separate message type 11 to LVS server as a confirmation.
 * LVS must response to that msg type 11 as confirmation of type 41. Weird :)
 */
public enum USTResponseMessageType implements MessageType {

    DAYSTART ("41", "Day start"),
    DAYEND   ("42", "Day end"),
    STATUS   ("43", "Status request"),
    TRANSFER ("44", "Transfer request"),
    SYNC     ("45", "Data synchronization request");

    private final String id;
    private final String description;

    private USTResponseMessageType (String id, String description) {
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
