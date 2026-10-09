package org.code_studio.messageflow;

import org.code_studio.model.CraneComponent;
import org.code_studio.model.LVSRequestMessageType;
import org.code_studio.model.ReportPoint;
import org.code_studio.model.SystemComponent;
import org.code_studio.model.UserData;
import org.code_studio.model.CraneMessage;
import org.code_studio.model.LEType;

public class LVSTransferRequest extends CraneMessage {
    
    public LVSTransferRequest (SystemComponent source, SystemComponent target, ReportPoint reportPoint, UserData userData) {
        setSender(CraneComponent.LVS1);
        setReceiver(CraneComponent.UST1);
        setMessageType(LVSRequestMessageType.TRANSFER);
        setSource(source);
        setTarget(target);
        setReportPoint(reportPoint);
        setLeType(LEType.LE18);
        setUserData(userData);
    }
}
