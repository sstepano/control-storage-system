package org.code_studio;

import java.util.concurrent.Callable;
import org.code_studio.model.Telegram;
import org.springframework.context.ApplicationContext;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class ComPortSchedulerTask implements Callable<Boolean> {

	private ApplicationContext ctx;
	private SerialPort comPort;
	private Integer craneBarcodeOpenRetryWaitSeconds;
	
	
	public ComPortSchedulerTask(SerialPort comPort) {
		this.comPort = comPort;
		this.ctx = Common.getApplicationContext();
		craneBarcodeOpenRetryWaitSeconds = Integer.valueOf(Common.applicationProperties.getProperty("crane.barcode.openretrywaitseconds"));
	}
	
	@Override
	public Boolean call() {
		boolean res = false;
		
		while (!res) {
			try {
				Common.log(getClass(), "<tcpserver><info> " + "Trying to open COM port for Barcode communication");
				res = openComPort();
				Thread.sleep(craneBarcodeOpenRetryWaitSeconds * 1000);
			} catch (InterruptedException e) {
				Common.log(getClass(), e, "ERROR");
			}
		}
		return res;
		
	}
	
	
	/**
	 * 
	 */
	private boolean openComPort () {
		boolean res = false;
		
		if (!comPort.isOpen()) {
			try {
				if (comPort.openPort()) {

					comPort.addDataListener(new SerialPortDataListener() {
						   @Override
						   public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }
						   @Override
						   public void serialEvent(SerialPortEvent event)
						   {
							   try {
								   String dataReceived;
							       byte[] buffer = event.getReceivedData();
							       int bytesRead = buffer.length;
							       dataReceived = new String (buffer, 0, bytesRead, Telegram.charset);
							       ctx.publishEvent(new BarcodeDataReceivedEvent(this, dataReceived));
							   } catch (Exception ex) {
								   Common.log(getClass(), "<tcpsvr><err> " + ex, "ERROR");
							   }
						   }
						});
					
					Common.log(getClass(), "<tcpserver><info> " + "Barcode communication enabled. COM port open: " + comPort.getSystemPortName(), "INFO");
					res = true;
				} else {
					Common.log(getClass(), "<tcpserver><err> " + "Barcode communication - Unable to open COM port: " + comPort.getSystemPortName() + ". Barcode functionality will be DISABLED until COM port gets connected", "ERROR");
				}
			} catch (Exception ex) {
				Common.log(getClass(), "<tcpserver><err> " + ex, "ERROR");
				res = false;
			}
		}
		return res;
	}

}
