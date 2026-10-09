package org.code_studio;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import com.fazecast.jSerialComm.SerialPort;

@Service
@DependsOn({"Common", "taskScheduler"})
@ConditionalOnProperty(value="crane.barcode.enabled", havingValue = "true", matchIfMissing = false)
public class BarcodeConfig {

	private SerialPort[] lstComPorts;
	private SerialPort comPort;
	
	private ApplicationContext ctx;
	
	/**
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    **/
	
	private ThreadPoolTaskScheduler taskScheduler;
    
    //@Value("${crane.barcode.systemportname}") ne moze value zato sto bean prvo mora da se kreira pa tek onda moze da se popuni value. A mi imamo konstruktor gde ovo pozivamo.
    // Tako da konstruktor puca.
    private String comPortSystemName;
    
	
	public BarcodeConfig() {

		this.ctx = Common.getApplicationContext();
		comPortSystemName = Common.applicationProperties.getProperty("crane.barcode.systemportname");
		this.taskScheduler = (ThreadPoolTaskScheduler) this.ctx.getBean("taskScheduler");
		taskScheduler.setDaemon(true); // Shuts down threads when the app closes. It does not wait.
		comPort = SerialPort.getCommPort(comPortSystemName);
		comPort.setComPortParameters(9600, 8, 1, 0);
		taskScheduler.submit(new ComPortSchedulerTask(comPort));		
	}

	
	/**
	 * Closes open port, without exposing port itself outside config class
	 */
	public void closeComPort() {
		if (comPort.isOpen()) {
			if (comPort.closePort()) {
				Common.log(getClass(), "<tcpserver><info> " + "Barcode communication - COM port closed: " + comPort.getSystemPortName(), "INFO");
			} else {
				Common.log(getClass(), "<tcpserver><err> " + "Barcode communication - Unable to close COM port: " + comPort.getSystemPortName(), "ERROR");
			}
		}
		
	}


}
