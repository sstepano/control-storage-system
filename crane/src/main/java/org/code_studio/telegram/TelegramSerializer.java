package org.code_studio.telegram;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.code_studio.Common;
import org.code_studio.model.Telegram;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

/**
 * Serializer / Deserializer
 */
public class TelegramSerializer implements Serializer<String>, Deserializer<String> {


    @Override
    public void serialize(String message, OutputStream outputStream) throws IOException {
        outputStream.write(message.getBytes(Telegram.charset));
        //outputStream.flush();
    }

    @Override
    public String deserialize(InputStream inputStream) throws IOException {
        String res;
        byte[] buffer = new byte[48];
        int bytesRead = inputStream.read(buffer);
        
        if (bytesRead == -1) { // connection close generates several bytesRead with length -1. We do not want to log them.
    	    res = "";
        } else if (bytesRead != 48) {
        	Common.log(getClass(), "<tcpserver><err> Bytes received not equal to 48 bytes. Bytes Read: " + bytesRead + ". Discarded.");
            res = "";
        } else {
            res = new String (buffer, 0, bytesRead, Telegram.charset);
        }
        
        return res;
    }

}
