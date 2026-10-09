package org.code_studio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.code_studio.controller.WebSocketController;
import org.code_studio.messageprocessor.IncomingMessageProcessor;
import org.code_studio.model.Telegram;
import org.code_studio.telegram.TelegramSerializer;
import org.code_studio.model.CraneMessage;


@Configuration
@EnableIntegration
@IntegrationComponentScan
@MessageEndpoint
public class TcpServerConfig {

    private int TCP_SERVER_PORT;

    @Autowired
    private WebSocketController webSocketController;
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public TcpServerConfig() {
    	TCP_SERVER_PORT = Integer.parseInt(Common.applicationProperties.getProperty("tcpserver.port"));
    }
    
    
    @Bean
    public AbstractServerConnectionFactory tcpServer() {
        TcpNetServerConnectionFactory serverCf = new TcpNetServerConnectionFactory(TCP_SERVER_PORT);
        serverCf.setSerializer(new TelegramSerializer());
        serverCf.setDeserializer(new TelegramSerializer());
        serverCf.setSoTcpNoDelay(true);
        serverCf.setSoKeepAlive(true);
        serverCf.setSingleUse(false);
        Common.log(getClass(), "<tcpserver><info> TCP server started on port: " + TCP_SERVER_PORT);
        return serverCf;
    }

    
    // Inbound channel
    @Bean
    public MessageChannel requestChannel() {
        return new DirectChannel();
    }

    
    // Outbound channel
    @Bean
    public MessageChannel replyChannel() {
        return new DirectChannel();
    }

    
    // Inbound channel adapter
    @Bean
    public TcpReceivingChannelAdapter receivingChannelAdapter(AbstractServerConnectionFactory serverConnectionFactory,
            MessageChannel requestChannel) {
        TcpReceivingChannelAdapter tcpReceivingChannelAdapter = new TcpReceivingChannelAdapter();
        tcpReceivingChannelAdapter.setConnectionFactory(serverConnectionFactory);
        tcpReceivingChannelAdapter.setOutputChannel(requestChannel);
        return tcpReceivingChannelAdapter;
    }

    
    // Outbound channel adapter
    @Bean
    @ServiceActivator(inputChannel = "replyChannel")
    public TcpSendingMessageHandler tcpSendingMessageHandler(AbstractServerConnectionFactory serverConnectionFactory) {
        TcpSendingMessageHandler tcpSendingMessageHandler = new TcpSendingMessageHandler();
        tcpSendingMessageHandler.setConnectionFactory(serverConnectionFactory);
        return tcpSendingMessageHandler;
    }

    
    @ServiceActivator(inputChannel = "requestChannel", outputChannel = "replyChannel")
    public Message<String> processMessage(Message<String> message) {
    	
    	Message<String> reply = null; // ako je reply == null, onda ne saljemo nikakvu poruku nazad
    	Telegram telegram = null;
    	
    	/**
    	 * Ako imamo ispravnu poruku, procesiraj je i napravi reply
    	 */
        if (message.getPayload() != "") {
            Common.log(getClass(), "<tcpserver><recv> " + message.getPayload());
            webSocketController.sendWebsocketMessage(message.getPayload());

            telegram = new Telegram(message.getPayload());
            	
        	IncomingMessageProcessor incomingMessageProcessor = new IncomingMessageProcessor(new CraneMessage (telegram));
        	  	
            reply = MessageBuilder
            .withPayload(
            		incomingMessageProcessor
                    .processMessage()
                    .buildResponse()
                )
            .setHeader(IpHeaders.CONNECTION_ID, message.getHeaders().get(IpHeaders.CONNECTION_ID, String.class))
            .build();
            
            webSocketController.sendWebsocketMessage("<tcpserver><send>" + reply.getPayload());
    	/**
    	 * U suprotnom, poruka nije validna
    	 */
        } else {
            webSocketController.sendWebsocketMessage("tcpserver><err> Bytes received not equal to 48 bytes. Discarded.");
            reply = null;

        }
        
        return reply;
    }

    
    /**
     * Listens to ConnectionOpen and ConnectionClose events
     * These events are emitted JUST BEFORE the connection is established, not after!
     * @param replyChannel
     * @return
     */
    @Bean
    public ApplicationListener<TcpConnectionEvent> listener(MessageChannel replyChannel) {
        return tcpConnectionEvent -> {
        	String connectionSource = tcpConnectionEvent.getConnectionFactoryName().equals("tcpServer") ? "<tcpserver><info>" : "<tcpclient><info>"; 

        	if (tcpConnectionEvent instanceof TcpConnectionOpenEvent) {
            	Common.log(getClass(), connectionSource + " Connection established: " + tcpConnectionEvent.getConnectionId());
            	
            	/**
            	 * Publish custom event when crane client connects to our server.
            	 * Then, send DAYSTART message within {@link CraneConnectedListener class}
            	 */
            	if (tcpConnectionEvent.getConnectionFactoryName().equals("tcpServer")) {
            		applicationEventPublisher.publishEvent(new CraneClientConnectedEvent(this, connectionSource));
            	}
            }
        	else if (tcpConnectionEvent instanceof TcpConnectionCloseEvent) {
            	Common.log(getClass(), connectionSource + " Connection closed: " + tcpConnectionEvent.getConnectionId());
            }
        };
    }

}
