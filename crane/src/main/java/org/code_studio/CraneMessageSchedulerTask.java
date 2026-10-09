package org.code_studio;

import org.code_studio.messageflow.LVSTransferRequest;
import org.code_studio.model.CraneMessage;

public class CraneMessageSchedulerTask implements Runnable {

	private CraneMessage incomingMessage;
	
	public CraneMessageSchedulerTask(CraneMessage incomingMessage) {
		this.incomingMessage = incomingMessage;
	}
	
	@Override
	public void run() {
		
		new LVSTransferRequest(
			  incomingMessage.getSource()
			, incomingMessage.getTarget()
			, incomingMessage.getReportPoint() 
			, incomingMessage.getUserData()
		).sendWithClient();
		
	}

}
