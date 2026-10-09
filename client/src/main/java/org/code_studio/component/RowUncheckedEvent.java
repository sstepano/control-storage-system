package org.code_studio.component;

import javafx.event.EventType;

@SuppressWarnings("serial")
public class RowUncheckedEvent extends CSCustomEvent {

	public static final EventType <RowUncheckedEvent> ROW_UNCHECKED
	= new EventType<>(CUSTOM_EVENT_TYPE, "ROW_UNCHECKED");
	
	private Object uncheckedItem;

	public RowUncheckedEvent(Object uncheckedItem) {
		super(ROW_UNCHECKED);
		this.uncheckedItem = uncheckedItem;
	}

	@Override
	public void invokeHandler(CSTableEventHandler handler) {
		handler.onRowUnchecked(uncheckedItem);
	}

}