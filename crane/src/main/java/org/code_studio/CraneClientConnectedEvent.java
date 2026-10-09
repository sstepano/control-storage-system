package org.code_studio;

/**
 * Custom event which will be fired when crane client connects to our server component.
 * It is used to send DAYSTART message after the connection
 */
public class CraneClientConnectedEvent {
	
	private String connectionProperties;

	public CraneClientConnectedEvent(Object source, String connectionProperties) {
		this.setConnectionProperties(connectionProperties);
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	private void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

}
