package org.code_studio.messageflow;

import org.code_studio.model.CraneComponent;
import org.code_studio.model.LVSRequestMessageType;
import org.code_studio.model.StatusCode;
import org.code_studio.model.CraneMessage;
import org.code_studio.model.CurrentDate;
import org.code_studio.model.CurrentTime;

public class LVSDayStartRequest extends CraneMessage {
    
    public LVSDayStartRequest () {
        setSender(CraneComponent.LVS1);
        setReceiver(CraneComponent.UST1);
        setMessageType(LVSRequestMessageType.DAYSTART);
        setSource(new CurrentDate());
        setTarget(new CurrentTime());
        setStatusCode(StatusCode.AUTOMATIONENABLED);
    }
}

