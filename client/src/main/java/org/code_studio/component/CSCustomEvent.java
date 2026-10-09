package org.code_studio.component;

import javafx.event.Event;
import javafx.event.EventType;

@SuppressWarnings("serial")
public abstract class CSCustomEvent extends Event {

    public static final EventType<CSCustomEvent> CUSTOM_EVENT_TYPE = new EventType<>(ANY);

	public CSCustomEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}
	public abstract void invokeHandler(CSTableEventHandler handler);

}
