package org.code_studio.messageflow;

import org.code_studio.model.CraneComponent;
import org.code_studio.model.SystemComponent;
import org.code_studio.model.USTRequestMessageType;
import org.code_studio.model.CraneMessage;
import org.code_studio.model.StatusCode;

public class TEST_USTDayStartResponse extends CraneMessage {
    
    public TEST_USTDayStartResponse (SystemComponent source, SystemComponent target) {
        setSender(CraneComponent.UST1);
        setReceiver(CraneComponent.LVS1);
        setMessageType(USTRequestMessageType.DAYSTART);
        setSource(source);
        setTarget(target);
        setStatusCode(StatusCode.AUTOMATIONENABLED);
    }
}
