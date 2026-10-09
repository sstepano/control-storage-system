package org.code_studio;

import org.code_studio.messageflow.LVSDayStartRequest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listens to connections on OUR server end.
 * When crane CLIENT connects to our SERVER component, we will only then send the DAYSTART message.
 * Until this class, we sent it on ApplicationReadyEvent, but at that point crane client was not connected to our server and we
 * were missing DAYSTART responses to our server. Only our client was receiving them, such as WRONGORINALID messages etc.
 * Now we should be able to detect all of them in our SERVER too.
 */
@Component
public class CraneClientConnectedListener {
	
    @EventListener(CraneClientConnectedEvent.class)
    public void onEvent(CraneClientConnectedEvent event) {
        Common.log(getClass(), "<tcpsvr><info> " + "CraneClientConnectedEvent detected");
        new LVSDayStartRequest().sendWithClient();
        Common.log(getClass(), "<tcpclient><send> " + "DAYSTART message sent to Crane server");

    }

}
