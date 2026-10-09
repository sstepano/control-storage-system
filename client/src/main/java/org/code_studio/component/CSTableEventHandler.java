package org.code_studio.component;

import javafx.event.EventHandler;

public abstract class CSTableEventHandler implements EventHandler <CSCustomEvent> {
    public abstract void onRender();
    public abstract void onDataNeeded();
    public abstract void onRowSelectionChanged(Object deselectedItem, Object selectedItem);
    public abstract void onRowAdded(Object addedItem);
    public abstract void onRowDeleted(Object deletedItem);
    public abstract void onRowChecked(Object checkedItem);
    public abstract void onRowUnchecked(Object uncheckedItem);

    @Override
    public void handle(CSCustomEvent event) {
        event.invokeHandler(this);
    }
}
