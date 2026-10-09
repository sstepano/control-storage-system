package org.code_studio.messageflow;

import org.code_studio.model.CraneComponent;
import org.code_studio.model.USTRequestMessageType;
import org.code_studio.model.CraneMessage;

public class TEST_USTDayEndResponse extends CraneMessage {
    
    public TEST_USTDayEndResponse () {
        setSender(CraneComponent.UST1);
        setReceiver(CraneComponent.LVS1);
        setMessageType(USTRequestMessageType.DAYEND);
    }
}
