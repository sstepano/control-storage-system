package org.code_studio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.code_studio.telegram.TelegramSerializer;

@Configuration
@EnableIntegration
@IntegrationComponentScan
@MessageEndpoint
public class TcpClientConfig {

    private String TCP_CLIENT_SERVERADDRESS;
    private int TCP_CLIENT_SERVERPORT;

    public TcpClientConfig() {
    	TCP_CLIENT_SERVERADDRESS = Common.applicationProperties.getProperty("tcpclient.serveraddress");
    	TCP_CLIENT_SERVERPORT = Integer.parseInt(Common.applicationProperties.getProperty("tcpclient.serverport"));
    }
    
    @Bean
    public TcpReceivingChannelAdapter tcpReceivingChannelAdapter() {
        TcpReceivingChannelAdapter adapter = new TcpReceivingChannelAdapter();
        adapter.setConnectionFactory(tcpClient());
        adapter.setClientMode(true);
        adapter.setOutputChannelName("fromTcp");
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "toTcp")
    public TcpSendingMessageHandler tcpOut(AbstractClientConnectionFactory connectionFactory) throws Exception {
        TcpSendingMessageHandler sender = new TcpSendingMessageHandler();
        sender.setConnectionFactory(connectionFactory);
        sender.setClientMode(true);
        return sender;
    }

    @Bean
    @DependsOn("tcpServer")
    public AbstractClientConnectionFactory tcpClient() {
        AbstractClientConnectionFactory clientCf = new TcpNetClientConnectionFactory(TCP_CLIENT_SERVERADDRESS, TCP_CLIENT_SERVERPORT);
        clientCf.setSerializer(new TelegramSerializer());
        clientCf.setDeserializer(new TelegramSerializer());
        Common.log(getClass(), "<tcpclient><info> TCP client has been created for server: " + TCP_CLIENT_SERVERADDRESS + ":" + TCP_CLIENT_SERVERPORT);
        return clientCf;
    }

    /**
     * Incoming messages to TCP Client comes to this channel
     * @param message
     */
    @ServiceActivator(inputChannel = "fromTcp")
    public void handleMessage(String message) {
    	Common.log(getClass(), "<tcpclient><recv> " + message);
    }

    @MessagingGateway(defaultRequestChannel = "toTcp")
    public interface TcpClientGateway {
        void send(String message);
    }
    
}
