package org.code_studio.component;

import javafx.event.EventType;

@SuppressWarnings("serial")
public class RowCheckedEvent extends CSCustomEvent {

	public static final EventType <RowCheckedEvent> ROW_CHECKED
	= new EventType<>(CUSTOM_EVENT_TYPE, "ROW_CHECKED");
	
	private Object checkedItem;

	public RowCheckedEvent(Object checkedItem) {
		super(ROW_CHECKED);
		this.checkedItem = checkedItem;
	}

	@Override
	public void invokeHandler(CSTableEventHandler handler) {
		handler.onRowChecked(checkedItem);
	}

}