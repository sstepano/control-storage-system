package org.code_studio.messageflow;

import org.code_studio.model.CraneComponent;
import org.code_studio.model.LVSRequestMessageType;
import org.code_studio.model.CraneMessage;

public class SyncMessage extends CraneMessage {
    
    public SyncMessage () {
        setSender(CraneComponent.LVS1);
        setReceiver(CraneComponent.UST1);
        setMessageType(LVSRequestMessageType.SYNC);
    }
}
