package org.code_studio.component;

import javafx.event.EventType;

@SuppressWarnings("serial")
public class ComponentRenderedEvent extends CSCustomEvent {

	public static final EventType <ComponentRenderedEvent> COMPONENT_RENDERED
	= new EventType<>(CUSTOM_EVENT_TYPE, "COMPONENT_RENDERED");

	public ComponentRenderedEvent() {
		super(COMPONENT_RENDERED);
	}

	@Override
	public void invokeHandler(CSTableEventHandler handler) {
		handler.onRender();
	}

}