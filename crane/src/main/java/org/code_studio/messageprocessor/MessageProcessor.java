package org.code_studio.messageprocessor;

public interface MessageProcessor {

    MessageProcessor processMessage();
    MessageProcessor handleError();
    String buildResponse();
    void setNextProcessor(MessageProcessor messageProcessor);

}
