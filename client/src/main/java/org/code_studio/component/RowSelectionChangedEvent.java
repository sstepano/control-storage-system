package org.code_studio.component;

import javafx.event.EventType;

@SuppressWarnings("serial")
public class RowSelectionChangedEvent extends CSCustomEvent {

	public static final EventType <RowSelectionChangedEvent> ROW_SELECTION_CHANGED
	= new EventType<>(CUSTOM_EVENT_TYPE, "ROW_SELECTION_CHANGED");
	
	private Object deselectedItem;
	private Object selectedItem;

	public RowSelectionChangedEvent(Object deselectedItem, Object selectedItem) {
		super(ROW_SELECTION_CHANGED);
		this.deselectedItem = deselectedItem;
		this.selectedItem = selectedItem;
	}

	@Override
	public void invokeHandler(CSTableEventHandler handler) {
		handler.onRowSelectionChanged(deselectedItem, selectedItem);
	}

}