package org.code_studio.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.code_studio.Common;
import org.code_studio.controller.TcpMessageController;
import org.springframework.context.ApplicationContext;

public class CraneMessage {

    private Telegram telegram;

    private CraneComponent  sender;
    private CraneComponent  receiver;
    private MessageType     messageType;
    private Integer         ordinalNumber;
    private ErrorCode       errorCode = ErrorCode.OK; // by default nema greske osim ako se eksplicitno ne postavi
    private SystemComponent source;
    private SystemComponent target;
    private ReportPoint     reportPoint;
    private LEType          leType;
    private StatusCode      statusCode = StatusCode.REQUESTOK;
    private UserData        userData;

    /**
     * Default constructor that is being used to create custom messages
     * We create extended classes such ash DayStart and manually populate
     * requested fields. Then message is being used in the transfer flow
     */
    public CraneMessage () {
    	this.ordinalNumber = Common.getNextMessageOrdinalNumber();
    	
        telegram = new Telegram();
        telegram.setOrdinalNumber(ordinalNumber.toString());
        telegram.setErrorCode(errorCode.getId());
        telegram.setStatusCode(statusCode.getId());
    }

    
    /**
     * Constructor that creates a message based on the Telegram data
     * Usually used when our server receives telegrams and converts them to CraneMessage objects
     * @param telegram
     */
    public CraneMessage(Telegram telegram) {
        this.telegram = telegram;

        Pattern regexPatternSourceTarget = Pattern.compile("(00[0-9]{2})([0-9]{2})([0-9]{2})"); // 00xxyyzz
        Pattern regexDateSourceTarget = Pattern.compile("[0-9]{2}[0-9]{2}20[0-9]{2}"); // ddMMyyyy
        Pattern regexTimeSourceTarget = Pattern.compile("[0-2][0-9][0-5][0-9][0-5][0-9]"); // hhmmss

        this.sender = telegram.getSender().equals("LVS1") ? CraneComponent.LVS1 : CraneComponent.UST1;
        this.receiver = telegram.getReceiver().equals("LVS1") ? CraneComponent.LVS1 : CraneComponent.UST1;
        this.messageType = switch (telegram.getId()) {
            // Received from UST as a request
            case "11" -> this.messageType = USTRequestMessageType.DAYSTART;
            case "12" -> this.messageType = USTRequestMessageType.DAYEND;
            case "13" -> this.messageType = USTRequestMessageType.STATUS;
            case "14" -> this.messageType = USTRequestMessageType.LOADER;
            case "15" -> this.messageType = USTRequestMessageType.RBGLOADED;
            case "16" -> this.messageType = USTRequestMessageType.RBGUNLOADED;
            case "17" -> this.messageType = USTRequestMessageType.COMPLETED;
            case "18" -> this.messageType = USTRequestMessageType.REMOVAL;
            case "19" -> this.messageType = USTRequestMessageType.SYNC;
            
            // Received from UST as a response
            case "41" -> this.messageType = USTResponseMessageType.DAYSTART;
            case "42" -> this.messageType = USTResponseMessageType.DAYEND;
            case "43" -> this.messageType = USTResponseMessageType.STATUS;
            case "44" -> this.messageType = USTResponseMessageType.TRANSFER;
            case "45" -> this.messageType = USTResponseMessageType.SYNC;

            // LVS request to UST
            case "DA" -> this.messageType = LVSRequestMessageType.DAYSTART;
            case "DB" -> this.messageType = LVSRequestMessageType.DAYEND;
            case "DC" -> this.messageType = LVSRequestMessageType.STATUS;
            case "DD" -> this.messageType = LVSRequestMessageType.TRANSFER;
            case "DE" -> this.messageType = LVSRequestMessageType.SYNC;

            //LVS response to UST requests
            case "AA" -> this.messageType = LVSResponseMessageType.START;
            case "AB" -> this.messageType = LVSResponseMessageType.END;
            case "AC" -> this.messageType = LVSResponseMessageType.STATUS;
            case "AD" -> this.messageType = LVSResponseMessageType.LOADER;
            case "AE" -> this.messageType = LVSResponseMessageType.RBGLOADED;
            case "AF" -> this.messageType = LVSResponseMessageType.RBGUNLOADED;
            case "AG" -> this.messageType = LVSResponseMessageType.COMPLETED;
            case "AH" -> this.messageType = LVSResponseMessageType.REMOVAL;
            case "AI" -> this.messageType = LVSResponseMessageType.SYNC;
            
            default -> throw new UnsupportedOperationException("Unimplemented message type received: " + telegram.getId());
        };
        this.ordinalNumber = Integer.parseInt(telegram.getOrdinalNumber());
        this.errorCode = switch (telegram.getErrorCode()) {
            case "00" -> ErrorCode.OK;
            case "01" -> ErrorCode.WRONGID;
            case "02" -> ErrorCode.WRONGORDINALID;
            case "03" -> ErrorCode.REPEAT;
            default   -> ErrorCode.UNKNOWN;
        };
        this.source = switch (telegram.getSource()) {
            case "1001" -> ReportPoint.RP1001;
            case "1003" -> ReportPoint.RP1003;
            case "1004" -> ReportPoint.RP1004;
            case "1005" -> ReportPoint.RP1005;
            case "3005" -> ReportPoint.RP3005;
            case "5004" -> ReportPoint.RP5004;
            case "5005" -> ReportPoint.RP5005;
            case "7004" -> ReportPoint.RP7004;
            case "7005" -> ReportPoint.RP7005;
            case "R001" -> ReportPoint.RPRGB1;
            case "R002" -> ReportPoint.RPRGB2;
            case String s when regexPatternSourceTarget.matcher(s).matches() -> {
                Matcher matcher = regexPatternSourceTarget.matcher(s);
                matcher.matches();
                yield new StorageCompartment(
                    new Coordinate (
                        Integer.parseInt(matcher.group(1).toString()),
                        Integer.parseInt(matcher.group(2).toString()),
                        Integer.parseInt(matcher.group(3).toString()),
                        CoordinateType.CRANE
                    ));
            }
            case String s when regexDateSourceTarget.matcher(s).matches() -> {
                Matcher matcher = regexDateSourceTarget.matcher(s);
                matcher.matches();
                yield new CurrentDate();
            }
            default -> ReportPoint.RPUNKNOWN;
        };
        this.target = switch (telegram.getTarget()) {
            case "1001" -> ReportPoint.RP1001;
            case "1003" -> ReportPoint.RP1003;
            case "1004" -> ReportPoint.RP1004;
            case "1005" -> ReportPoint.RP1005;
            case "3005" -> ReportPoint.RP3005;
            case "5004" -> ReportPoint.RP5004;
            case "5005" -> ReportPoint.RP5005;
            case "7004" -> ReportPoint.RP7004;
            case "7005" -> ReportPoint.RP7005;
            case "R001" -> ReportPoint.RPRGB1;
            case "R002" -> ReportPoint.RPRGB2;
            case String s when regexPatternSourceTarget.matcher(s).matches() -> {
                Matcher matcher = regexPatternSourceTarget.matcher(s);
                matcher.matches();
                yield new StorageCompartment(
                    new Coordinate (
                        Integer.parseInt(matcher.group(1).toString()),
                        Integer.parseInt(matcher.group(2).toString()),
                        Integer.parseInt(matcher.group(3).toString()),
                        CoordinateType.CRANE
                    ));
            }
            case String s when regexTimeSourceTarget.matcher(s).matches() -> {
                Matcher matcher = regexTimeSourceTarget.matcher(s);
                matcher.matches();
                yield new CurrentTime();
            }
            default -> ReportPoint.RPUNKNOWN;
        };
        this.reportPoint = switch (telegram.getReportPoint()) {
            case "1001" -> ReportPoint.RP1001;
            case "1003" -> ReportPoint.RP1003;
            case "1004" -> ReportPoint.RP1004;
            case "1005" -> ReportPoint.RP1005;
            case "3005" -> ReportPoint.RP3005;
            case "5004" -> ReportPoint.RP5004;
            case "5005" -> ReportPoint.RP5005;
            case "7004" -> ReportPoint.RP7004;
            case "7005" -> ReportPoint.RP7005;
            case "R001" -> ReportPoint.RPRGB1;
            case "R002" -> ReportPoint.RPRGB2;
            default     -> ReportPoint.RPUNKNOWN; // todo: videti kako da bacim exception, a da ipak podrzavam poruke koje nemaju report point
        };
        this.leType = switch (telegram.getLeType()) {
            case "18" -> LEType.LE18;
            case "20" -> LEType.LE20;
            default   -> LEType.LEUNKNOWN;
        };
        this.statusCode = switch (telegram.getStatusCode()) {
            case "00"   -> StatusCode.OK;
            case "01"   -> StatusCode.AUTOMATIONENABLED;
            case "0x3F" -> StatusCode.AUTOMATIONDISABLED;
            case "OK"   -> StatusCode.REQUESTOK;
            case "KF"   -> StatusCode.BADPROFILE;
            case "FB"   -> StatusCode.STORAGEOCCUPIED;
            case "FL"   -> StatusCode.STORAGEEMPTY;
            case "ZF"   -> StatusCode.BADLOADUNIT;
            case "NB"   -> StatusCode.STORAGEUNUSED;
            case "PU"   -> StatusCode.STORAGEUNKNOWN;
            case "DT"   -> StatusCode.TRANSFERDUPLICATE;
            case "NI"   -> StatusCode.TRANSFERREQUESTUNKNOWN;
            default     -> StatusCode.UNKNOWN;
        };
        this.userData = new UserData(telegram.getUserData());

    }

    /**
     * Sends crafted message using CLIENT sending channel (toTcp)
     */
    public void sendWithClient() {
        ApplicationContext ctx = Common.getApplicationContext();
        TcpMessageController ctrl = ctx.getBean(TcpMessageController.class);
        ctrl.sendRawMessage(telegram.getRawData());
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public CraneComponent getSender() {
        return sender;
    }

    public void setSender(CraneComponent sender) {
        this.sender = sender;
        telegram.setSender(sender.getId());
    }

    public CraneComponent getReceiver() {
        return receiver;
    }

    public void setReceiver(CraneComponent receiver) {
        this.receiver = receiver;
        telegram.setReceiver(receiver.getId());
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
        telegram.setId(messageType.getId());
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
        telegram.setErrorCode(errorCode.getId());
    }

    public SystemComponent getSource() {
        return source;
    }

    public void setSource(SystemComponent source) {
        this.source = source;
        telegram.setSource(source.getId());
    }

    public SystemComponent getTarget() {
        return target;
    }

    public void setTarget(SystemComponent target) {
        this.target = target;
        telegram.setTarget(target.getId());
    }

    public ReportPoint getReportPoint() {
        return reportPoint;
    }

    public void setReportPoint(ReportPoint reportPoint) {
        this.reportPoint = reportPoint;
        telegram.setReportPoint(reportPoint.getId());
    }

    public LEType getLeType() {
        return leType;
    }

    public void setLeType(LEType leType) {
        this.leType = leType;
        telegram.setLeType(leType.getId());
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
        telegram.setStatusCode(statusCode.getId());
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
        telegram.setUserData(userData.toString());
    }

    
}
