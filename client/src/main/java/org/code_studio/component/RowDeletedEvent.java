package org.code_studio.component;

import javafx.event.EventType;

@SuppressWarnings("serial")
public class RowDeletedEvent extends CSCustomEvent {

	public static final EventType <RowDeletedEvent> ROW_DELETED
	= new EventType<>(CUSTOM_EVENT_TYPE, "ROW_DELETED");
	
	private Object deletedItem;

	public RowDeletedEvent(Object deletedItem) {
		super(ROW_DELETED);
		this.deletedItem = deletedItem;
	}

	@Override
	public void invokeHandler(CSTableEventHandler handler) {
		handler.onRowDeleted(deletedItem);
	}

}