package org.code_studio.telegram;

import org.code_studio.model.Telegram;

public class USTResponseMessageFactory {

    private Telegram telegramRequest;
    private Telegram telegramResponse;
    

    public USTResponseMessageFactory(Telegram telegramRequest) {
        this.telegramRequest = telegramRequest;
        this.telegramResponse = new Telegram(telegramRequest.getRawData());
    }

    public void processMessage() {
        switch(telegramRequest.getId()) {
            case "11":
                telegramResponse.setId("AA");
            break;
            case "12":
                telegramResponse.setId("AB");
            break;
            case "13":
                telegramResponse.setId("AC");
            break;
            case "14":
                telegramResponse.setId("AD");
            break;
            case "15":
                telegramResponse.setId("AE");
            break;
            case "16":
                telegramResponse.setId("AF");
            break;
            case "17":
                telegramResponse.setId("AG");
                switch(telegramRequest.getReportPoint()) {
                    case "1003":
                    break;
                    case "3005":
                    break;
                    case "5004":
                    break;
                    case "7004":
                    break;
                }
            break;
            case "18":
                telegramResponse.setId("AH");
            break;
            case "19":
                telegramResponse.setId("AI");
            break;
        }     
    }

    /**
     * 
     * @return
     */
    public String buildResponse() {
        telegramResponse.setSender("LVS1");
        telegramResponse.setReceiver("UST1");
        telegramResponse.setErrorCode("00");
        telegramResponse.setStatusCode("OK");
        return telegramResponse.getRawData();
    }

}
