package org.code_studio.component;

import javafx.event.EventType;

@SuppressWarnings("serial")
public class DataNeededEvent extends CSCustomEvent {

	public static final EventType <DataNeededEvent> DATA_NEEDED
	= new EventType<>(CUSTOM_EVENT_TYPE, "DATA_NEEDED");

	public DataNeededEvent() {
		super(DATA_NEEDED);
	}

	@Override
	public void invokeHandler(CSTableEventHandler handler) {
		handler.onDataNeeded();
	}

}