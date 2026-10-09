package org.code_studio.messageflow;

import org.code_studio.model.CraneComponent;
import org.code_studio.model.ReportPoint;
import org.code_studio.model.SystemComponent;
import org.code_studio.model.USTRequestMessageType;
import org.code_studio.model.UserData;
import org.code_studio.model.CraneMessage;
import org.code_studio.model.LEType;

public class TEST_USTTransferResponse extends CraneMessage {
    
    public TEST_USTTransferResponse (SystemComponent source, SystemComponent target, ReportPoint reportPoint, UserData userData) {
        setSender(CraneComponent.UST1);
        setReceiver(CraneComponent.LVS1);
        setMessageType(USTRequestMessageType.COMPLETED);
        setSource(source);
        setTarget(target);
        setReportPoint(reportPoint);
        setLeType(LEType.LE18);
        setUserData(userData);
    }
}
