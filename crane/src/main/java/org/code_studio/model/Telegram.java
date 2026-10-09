package org.code_studio.model;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

/**
 * Used to transfer data over TCP from and to crane system.
 */
public final class Telegram {

    /**
     * Character that is used to fill NULL values and to fill up to total attribute length.
     * I.E. if some field is not being used, we fill all of its characters with this
     * ie. source is length 3, and if it is null, we fill it with ???
     **/ 
    public final static String padChar = "?";

    /**
     * Charset used to encode/decode the string in the TCP channel
     */
    public final static Charset charset = StandardCharsets.US_ASCII;

    private String sender; // 4 bytes, LVS1 or UST1
    private final static int senderLength = 4;

    private String receiver; // 4 bytes, LVS1 or UST1
    private final static int receiverLength = 4;

    protected String id; // message type, 2 bytes I.E. DA, DD, DB, DC
    protected final static int idLength = 2;

    protected String ordinalNumber; // 2 bytes, 01-99
    private final static int ordinalNumberLength = 2;

    private String errorCode; // 2 bytes, 00 - No error; 01 - Incorrect telegram identificator; 02 - wrong number; 03 - repeated telegram after timeout
    private final static int errorCodeLength = 2;

    private String source; // 8 bytes, variable, depending on id field. I.E. if id is DA, then here we need to add DDMMYYYY
    private final static int sourceLength = 8;

    private String target; // 8 bytes, variable, depending on id field. I.E. if id is DA, then here we need to add HHMMSS and 2 more ?? chars (hours 00-23)
    private final static int targetLength = 8;

    private String reportPoint; // 4 bytes, 
    private final static int reportPointLength = 4;

    private String leType; // 2 bytes, 18 or 20
    private final static int leTypeLength = 2;

    private String statusCode; // 2 bytes, OK
    private final static int statusCodeLength = 2;

    private String userData; // 10 bytes, LVS managed
    private final static int userDataLength = 7;

    /**
     * Default constructor
     */
    public Telegram(){}

    /**
     * Generates new Telegram from the ALL STRING input data
     * @param sender
     * @param receiver
     * @param id
     * @param ordinalNumber
     * @param errorCode
     * @param source
     * @param target
     * @param reportPoint
     * @param type
     * @param statusCode
     * @param userData
     */
    public Telegram (
        String sender,
        String receiver,
        String id,
        String ordinalNumber,
        String errorCode,
        String source,
        String target,
        String reportPoint,
        String type,
        String statusCode,
        String userData
    ) {
        this.sender        = sender;
        this.receiver      = receiver;
        this.id            = id;
        this.ordinalNumber = ordinalNumber;
        this.errorCode     = errorCode;
        this.source        = source;
        this.target        = target;
        this.reportPoint   = reportPoint;
        this.leType          = type;
        this.statusCode    = statusCode;
        this.userData      = userData;
    }


    /**
     * Generates new telegram from received raw data string, that includes padding char.
     * Constructor parses the string and assigns values WITHOUT padding char to corresponding field in the class
     * I.E. raw source is 1111????, but the source field will contain only 1111 after class instance creation
     * @param rawData
     */
    public Telegram(String rawData){
        int startPos = 0;
        this.sender        = rawData.substring(startPos, startPos+=senderLength).replace(padChar, "");
        this.receiver      = rawData.substring(startPos, startPos+=receiverLength).replace(padChar, "");
        this.id            = rawData.substring(startPos, startPos+=idLength).replace(padChar, "");
        this.ordinalNumber = rawData.substring(startPos, startPos+=ordinalNumberLength).replace(padChar, "");
        this.errorCode     = rawData.substring(startPos, startPos+=errorCodeLength).replace(padChar, "");
        this.source        = rawData.substring(startPos, startPos+=sourceLength).replace(padChar, "");
        this.target        = rawData.substring(startPos, startPos+=targetLength).replace(padChar, "");
        this.reportPoint   = rawData.substring(startPos, startPos+=reportPointLength).replace(padChar, "");
        this.leType        = rawData.substring(startPos, startPos+=leTypeLength).replace(padChar, "");
        this.statusCode    = rawData.substring(startPos, startPos+=statusCodeLength).replace(padChar, "");
        this.userData      = rawData.substring(startPos, startPos+=userDataLength).replace(padChar, "");
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(String number) {
        this.ordinalNumber = number;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getReportPoint() {
        return reportPoint;
    }

    public void setReportPoint(String reportPoint) {
        this.reportPoint = reportPoint;
    }

    public String getLeType() {
        return leType;
    }

    public void setLeType(String type) {
        this.leType = type;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getRawData() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder
            .append(StringUtils.rightPad(sender == null ? "" : sender, senderLength, padChar))
            .append(StringUtils.rightPad(receiver == null ? "" : receiver, receiverLength, padChar))
            .append(StringUtils.rightPad(id == null ? "" : id, idLength, padChar))
            .append(StringUtils.leftPad(ordinalNumber == null ? StringUtils.rightPad("", ordinalNumberLength, padChar) : ordinalNumber, ordinalNumberLength, "0"))
            .append(StringUtils.rightPad(errorCode == null ? "" : errorCode, errorCodeLength, padChar))
            .append(StringUtils.rightPad(source == null ? "" : source, sourceLength, padChar))
            .append(StringUtils.rightPad(target == null ? "" : target, targetLength, padChar))
            .append(StringUtils.rightPad(reportPoint == null ? "" : reportPoint, reportPointLength, padChar))
            .append(StringUtils.rightPad(leType == null ? "" : leType, leTypeLength, padChar))
            .append(StringUtils.rightPad(statusCode == null ? "" : statusCode, statusCodeLength, padChar))
            .append(StringUtils.rightPad(userData == null ? "" : userData, userDataLength, padChar))
            .append("001");

        return strBuilder.toString();
    }


}