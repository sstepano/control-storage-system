package org.code_studio.messageflow;

import org.code_studio.model.CraneComponent;
import org.code_studio.model.LVSResponseMessageType;
import org.code_studio.model.CraneMessage;

public class LVSDayStartResponse extends CraneMessage {
    
    public LVSDayStartResponse () {
        setSender(CraneComponent.LVS1);
        setReceiver(CraneComponent.UST1);
        setMessageType(LVSResponseMessageType.START);
    }
}

