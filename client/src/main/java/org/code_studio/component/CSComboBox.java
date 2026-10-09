package org.code_studio.component;

import java.lang.reflect.Method;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CSComboBox <T> extends ComboBox <T> {

	@FunctionalInterface
    public interface CSComboBoxSelectionChangedListener {
    	public abstract void onSelectionChanged(Object newItem);
    }

	public String emptySelectionText = null;
	private boolean initFactory = true;
	private String factoryMethodName = null;

	public CSComboBox() {}

	public String getEmptySelectionText() {
		return emptySelectionText;
	}

	public void setEmptySelectionText(String emptySelectionText) {
		this.emptySelectionText = emptySelectionText;
	}

	public boolean isInitFactory() {
		return initFactory;
	}

	public void setInitFactory(boolean isInitFactory) {
		this.initFactory = isInitFactory;

		if (this.initFactory) {
			this.setFactory();
		}
	}

    public String getFactoryMethodName() {
		return factoryMethodName;
	}

	public void setFactoryMethodName(String factoryMethodName) {
		this.factoryMethodName = factoryMethodName;
	}

	/***************************************************
     * INITIALIZES CELL FACTORY AND EMPTY SELECTION TEXT
     ***************************************************/
	private void setFactory() {

		Callback<ListView<T>, ListCell<T>> factory = _ -> new ListCell<>() {
		    @Override
		    protected void updateItem(T item, boolean empty) {
		    	String methodResult = null;
		        super.updateItem(item, empty);

		        if (item != null) {
		        	try {
						Method method = Class.forName(item.getClass().getName()).getMethod("get" + factoryMethodName);
					    methodResult = (String) method.invoke(item);
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
		        setText((empty || item == null) ? getEmptySelectionText() : methodResult);
		    }
		};
		this.setCellFactory(factory);
		this.setButtonCell(factory.call(null));

		//this adds empty row in combo box, which we will populate with emptySelectionText
		if (this.getEmptySelectionText() != null && !this.getEmptySelectionText().isEmpty()) {
			this.getItems().add(null);
		}
	}


	public void onSelectionChanged(CSComboBoxSelectionChangedListener listener) {
		this.valueProperty().addListener(new InvalidationListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void invalidated(Observable observable) {
				T newItem;
				newItem = ((ObjectProperty<T>) observable).getValue();
				listener.onSelectionChanged(newItem);
			}
        });
	}

	/***************************************************
     * SELECTS ITEM IN COMBOBOX BASED ON THE PROVIDED ITEM
     ***************************************************/
	public void select (T item) {
		String methodResult;
		String selectedMethodResult = null;
		Method method = null;
		int itemIndex = 0;

		try {
			method = Class.forName(item.getClass().getName()).getMethod("get" + factoryMethodName);
			selectedMethodResult = (String) method.invoke(item);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i=0; i < this.getItems().size(); i++) {
			try {
				T iteritem = this.getItems().get(i);
				methodResult = (String) method.invoke(iteritem);

				if (methodResult.equals(selectedMethodResult)) {
					itemIndex = i;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.getSelectionModel().select(itemIndex);
	}
	
	/***************************************************
     * SELECTS ITEM IN COMBOBOX BASED ON THE PROVIDED ITEM ID
     ***************************************************/
	public void selectByItemId (Long itemId) {
		Class<?> clazz = getItems().get(0).getClass();
		Long methodResult;
		Method method = null;
		int itemIndex = 0;
		
		try {
			method = Class.forName(clazz.getName()).getMethod("getId");
		} catch (Exception e) {
			
		}

		if (itemId == null) {
			this.getSelectionModel().select(0);
		} else {
			for (int i=0; i < this.getItems().size(); i++) {
				try {
					T iteritem = this.getItems().get(i);
					methodResult = (Long) method.invoke(iteritem);
	
					if (methodResult == itemId) {
						itemIndex = i;
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.getSelectionModel().select(itemIndex);
		} // else end
	}
	
	/***************************************************
     * SELECTS ITEM IN COMBOBOX BASED ON THE PROVIDED ITEM ID
     ***************************************************/
	public void selectByItemId (Integer itemId) {
		Class<?> clazz = getItems().get(0).getClass();
		Integer methodResult;
		Method method = null;
		int itemIndex = 0;
		
		try {
			method = Class.forName(clazz.getName()).getMethod("getId");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (itemId == null) {
			this.getSelectionModel().select(0);
		} else {
			for (int i=0; i < this.getItems().size(); i++) {
				try {
					T iteritem = this.getItems().get(i);
					methodResult = (Integer) method.invoke(iteritem);
	
					if (methodResult == itemId.longValue()) {
						itemIndex = i;
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.getSelectionModel().select(itemIndex);
		} //else end
	}
	
	/***************************************************
     * Calls Combobox.setItems AND selects first item
     * in the CB. That way we ensure that CB is never empty
     * We cannot override ComboBox.setItems because it is
     * final. So, we named it setItemsAndSelectFirstItem
     ***************************************************/
	public void setItemsAndSelectFirstItem (ObservableList <T> lstItems) {
		setItems(lstItems);
		if (getItems().size() > 0)
			getSelectionModel().clearAndSelect(0);
	}

}
