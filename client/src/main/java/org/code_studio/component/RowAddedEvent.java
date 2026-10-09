package org.code_studio.component;

import javafx.event.EventType;

@SuppressWarnings("serial")
public class RowAddedEvent extends CSCustomEvent {

	public static final EventType <RowAddedEvent> ROW_ADDED
	= new EventType<>(CUSTOM_EVENT_TYPE, "ROW_ADDED");
	
	private Object addedItem;

	public RowAddedEvent(Object addedItem) {
		super(ROW_ADDED);
		this.addedItem = addedItem;
	}

	@Override
	public void invokeHandler(CSTableEventHandler handler) {
		handler.onRowAdded(addedItem);
	}

}