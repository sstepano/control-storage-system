package org.code_studio.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.code_studio.Common;
import org.code_studio.TcpClientConfig.TcpClientGateway;
import org.code_studio.messageflow.LVSTransferRequest;
import org.code_studio.model.Coordinate;
import org.code_studio.model.CoordinateType;
import org.code_studio.model.ReportPoint;
import org.code_studio.model.StorageCompartment;
import org.code_studio.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TcpMessageController {

     @Autowired
     TcpClientGateway cgw;

     /**
      * Sends raw telegram message using TcpClient
      * @param message
      */
     @GetMapping("/sendRawMessage/{message}")
     public void sendRawMessage(@PathVariable String message) {
    	 Common.log(getClass(), "<tcpclient><send> " + message);
         cgw.send(message);
     }

     /**
      * Generates message for TCP CLIENT in format:
      * IXXYYYZZ - input.  This generates LVSTransferRequest 1003 -> 5004 or 7004
      * OXXYYYZZ - output. This generates LVSTransferRequest 00XXYYZZ -> 5004 or 7004
      * 
      * @param message
      */
    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message) {
        Pattern regexPatternSourceTarget = Pattern.compile("[IO]([0-9]{2})0([0-9]{2})([0-9]{2})(OK)"); // I(O)xxyyyzzOK
        String strRMCoords = message.substring(1, message.length() - 2);
        Integer coordinateX_row = Integer.parseInt(message.substring(1, 3));
        
        /**
         * TODO: MOVE THIS TO CraneMessage or MessageProcessor !!!
         * Determine to which crane we should send the message, based on the ROW coordinate
         * 5004 is the RIGHT crane
         * 7004 is the LEFT crane
         * Anything greater than 4 should go to the RIGHT
         */
        ReportPoint targetReportPoint = coordinateX_row <= 4 ? ReportPoint.RP5004 : ReportPoint.RP7004;
        
        switch (message) {
            case String s when regexPatternSourceTarget.matcher(s).matches() -> {
                Matcher matcher = regexPatternSourceTarget.matcher(s);
                matcher.matches();
                UserData userData = new UserData(strRMCoords);
                if (message.startsWith("I")) { // INPUT
                    new LVSTransferRequest(ReportPoint.RP1003, targetReportPoint, ReportPoint.RP1003, userData).sendWithClient();
                } else { // exit
                    Coordinate coords = new Coordinate(strRMCoords, CoordinateType.RM).getCraneCoordinates();
                    ReportPoint rpReturnPoint;
                    
                    /**
                     * If row is from 1 to 4, then the RIGHT crane should store it (R001).
                     * Otherwise, LEFT crane handles rows 5 to 8 (R002)
                     * WARNING: Crane coordinates ARE NOT the same as RM coordinates! Row in CRANE coords is Z, not X!!!
                     * Check description in {@link org.code_studio.model.Coordinate} class for details  
                     */
                    if (coordinateX_row <= 4) {
                    	rpReturnPoint = ReportPoint.RPRGB1;
                    } else {
                    	rpReturnPoint = ReportPoint.RPRGB2;
                    }
                    new LVSTransferRequest(new StorageCompartment(coords), ReportPoint.RP3005, rpReturnPoint, userData).sendWithClient();
                }
            }
            default -> {}
        }
    }

}
