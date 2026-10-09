package org.code_studio.component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;

import javafx.application.Platform;
import javafx.beans.DefaultProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.control.CheckBox;

//ovde mora extends jer je AnchorPane subclass od Node
//a Node se negde fakin ocekuje kod postavljanja root-a ili tako nesto
@DefaultProperty(value="componentBarItems")
public class CSTable <T> extends AnchorPane implements Initializable {

	public enum SearchType {
		CLIENT, // defines local search in a table on a client level
		SERVER  // defines rest search on a server. Needs additional config for rest service params
	}

	@FXML private String topLabelText;
	@FXML private BorderPane borderPane;
	@FXML public  TableView <T> tableView;
	@FXML public  Label topLabel;
	@FXML private HBox hbSearch;
	@FXML public  CSTextField tfSearchBox;
	@FXML private Button btnSearch;
	@FXML public  Button btnAdd;
	@FXML public  Button btnView;
	@FXML public  Button btnEdit;
	@FXML public  Button btnDelete;
	@FXML public  Button btnExcelExport;
	@FXML public  Button btnPdfExport;
	@FXML public  Button btnRefresh;
	@FXML private Tooltip searchTooltip;
	@FXML public  HBox componentBar;

	private boolean searchVisible = false;
	private boolean topLabelVisible = false;
	//private final int searchBoxSize = 200;
	public boolean showDoubleClickDefaultAction = false;

	private String DTOName;
	private boolean addButtonVisible = true;
	private boolean viewButtonVisible = true;
	private boolean editButtonVisible = true;
	private boolean deleteButtonVisible = true;
	private boolean refreshButtonVisible = true;
	private boolean excelExportButtonVisible = true;
	private boolean pdfExportButtonVisible = true;
	private String addEditControllerName;
	private String searchTooltipText;
	private boolean rowCheckboxVisible = false;
	private boolean autoNumberColumnVisible = false;
	private List<T> lstCheckedItems;
	private boolean searchRequestFocus;
	private boolean tableRequestFocus;
	private CSTable<?> tblParent;
	private List<CSTable<?>> lstTblChildren = new ArrayList<>();
	private CSRestService<T> restService;
	private CSRestService<T> rsDelete;
	private Boolean dragAndDropEnabled;
	private ProgressIndicator progressIndicator;
	//private Boolean hasOnRowSelectionChangedListener = false;

	//dozvoljeno da se ne definise rest service za delete akciju
	//koristi se kod drag and drop, gde jednostavno mozemo da obrisemo item iz tabele
	//jer jos uvek nismo nista postovali u bazu
	private Boolean deleteRestServiceAllowedMissing = false;

	private ObservableList <Node> componentBarItems = FXCollections.observableArrayList();

	/*
	 * Tableview vertical scrollbar
	 * */
	private ScrollBar tableViewVerticalScrollbar = null;

	/*
	 * Indicator if top section (TOP) of this component's borderpane is visible
	 * */
	private boolean topSectionVisible = true;

	//@Value("${ui.table.autoselectfirstrow}")
	private Boolean autoSelectFirstRow = true;

	/*
	 * Used to load additional data in the grid, when scroll passes 3/4 of the scrollbar value
	*/
	private Double scrollNotifyValue = 0.99;

	/*
	 * Width in pixels. Used to calculate minimal column width, based on length of column name. Still not so perfect.
	 * This value is added to string length in pixels
	*/
	private final int columnWidthPixelAdd = 12;

	/*
	 * Used for storing result when component is rendered
	 * We need it to register event in which we use scroll-bar
	 * Scroll-bar gets it value ONLY when component is completely rendered
	 * */
    private boolean isRendered = false;
    
    /**
     * If TRUE, when user types anything while the table is in focus, search box captures letters and on ENTER performs the search
     * If FALSE, user must focus search box to type search phrase and search the table. 
     */
    private boolean enhancedSearch = false;
    
    /**
     * Used to track whether we should SEARCH on ENTER, or show DEFAULT TABLE ACTION, like EDIT
     * If FALSE -> then we show ACTION
     * if TRUE -> then we SEARCH 
     */
    private boolean enhancedSearchStatus = true;

    /*
     * Holds columns for the table in form of: DISPLAY_NAME | METHOD_NAME | COLUMN_SIZE
     * //public final ObservableList <CSTableColumn> tableColumns = FXCollections.observableArrayList();
     * */
    public List <CSTableColumn> tableColumns = new ArrayList<>();
    
    
    /*
     * List of CSS classes registered to hightlight rows or cells in the table view
     * Classes are defined in CSTable.css
     */
    private ObservableList<String> lstCssClass = FXCollections.observableArrayList();

    
    /*
     * If true, when user adds an item to table, it will show up at the beginning and table will scroll to the first item
     */
    private boolean reverseItemsOrder = false;
    
    
    /*
     * Used for mapping listeners to the table events
     * With this interface, we can use lambda expressions in implementations for table events
     * */
	@FunctionalInterface
    public interface CSTableRowDoubleClickListener {
    	public abstract void onRowDoubleClick(Object rowData);
    }

	@FunctionalInterface
    public interface CSTableRowSelectionChangedListener {
    	public abstract void onRowSelectionChanged(Object deselectedRow, Object selectedRow);
    }

	@FunctionalInterface
    public interface CSTableDataNeededListener {
    	public abstract void onDataNeeded();
    }

	@FunctionalInterface
    public interface CSTableSearchListener {
    	public abstract void onServerSearch();
    }

	@FunctionalInterface
    public interface CSTableFocusChangedListener {
    	public abstract void onFocusChanged(Boolean oldValue, Boolean newValue);
    }

	@FunctionalInterface
    public interface CSTableRowAddedListener {
    	public abstract void onRowAdded(Object addedItem);
    }
	
	@FunctionalInterface
    public interface CSTableRowDeletedListener {
    	public abstract void onRowDeleted(Object deletedItem);
    }
	
	@FunctionalInterface
    public interface CSTableRowCheckedListener {
    	public abstract void onRowChecked(Object checkedItem);
    }
	
	@FunctionalInterface
    public interface CSTableRowUncheckedListener {
    	public abstract void onRowUnchecked(Object uncheckedItem);
    }

	@SuppressWarnings("rawtypes")
    public final Callback<ResizeFeatures, Boolean> getColumnResizePolicy() {
        return tableView.getColumnResizePolicy() == null ? TableView.UNCONSTRAINED_RESIZE_POLICY : tableView.getColumnResizePolicy();
    }

	@SuppressWarnings("rawtypes")
	public void setColumnResizePolicy( Callback <ResizeFeatures, Boolean> callback) {
		this.tableView.setColumnResizePolicy(callback);
	}


	/*
	 * Default constructor
	 * */
	public CSTable() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CSTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        //register highlight CSS classes
        lstCssClass.addAll(
    	      "highlighted-red"
            , "highlighted-green"
            , "highlighted-orange"
        );

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

	}

	@Override
	public void initialize(URL fxmlUrl, ResourceBundle bundle) {
		// load properties from file, for ui.table
        enhancedSearch = Boolean.parseBoolean(Common.applicationProperties.getProperty("ui.table.enhancedsearch"));
		
		progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
		this.setSearchVisible(searchVisible);
		this.setEventHandler(ComponentRenderedEvent.COMPONENT_RENDERED, new CSTableEventHandler() {

			@Override
			public void onRender() {
				tableViewVerticalScrollbar.valueProperty().addListener((arg, oldVal, newVal)-> {
					// if scrolling downwards
					if (newVal.doubleValue() > oldVal.doubleValue() && newVal.doubleValue() > scrollNotifyValue) {
						//Scrollbar passed max scroll notify value. We must fire DATA_NEEDED event
						tableView.getParent().fireEvent(new DataNeededEvent());
					}
				});

				// osiguravamo se da, ako postoje podaci u tabeli, uvek imamo selektovan bar jedan red, u ovom slucaju prvi red
				// Mora da stoji ovde jer, kod inicijalnog popunjavanja, tabela mora da bude renderovana da bi se selektovao prvi red
				// i da bi se okinu onRowSelectionChanged. Weird :D
				if (autoSelectFirstRow && getRowCount() > 0) {
					//tableView.getSelectionModel().select(0);
					selectFirstRow();
					
				}

				//When table gets rendered event, request focus on it.
				// If we request focus BEFORE table gets rendered, it just does nothing.
				if (searchRequestFocus && searchVisible) {
					tfSearchBox.requestFocus();
				} else if (tableRequestFocus) {
					Platform.runLater(() -> tableView.requestFocus());
				}

				//Disableujemo ako nema rekorda u tabeli. Takodje, ovo moramo i kod data fetch-a da radimo.
				updateButtonsState();

				/**
				 * TODO: Unfinished implementation custom resize policy that resizes last column to the remaining visible width of the screen
				 *
				tableView.widthProperty().addListener( e -> {
					tableView.setColumnResizePolicy(CSTable.LASTCOLUMN_RESIZE_POLICY);
				});

				**/
				//tableView.setColumnResizePolicy(new ColumnResizePolicy());
			}

			@Override
			public void onDataNeeded() {}
			
			@Override
			public void onRowAdded(Object addedItem) {}
			
			@Override
			public void onRowSelectionChanged(Object deselectedItem, Object selectedItem) {}
			
			@Override
			public void onRowDeleted(Object deletedItem) {}
			
			@Override
			public void onRowChecked(Object checkedItem) {}
			
			@Override
			public void onRowUnchecked(Object uncheckedItem) {}
		});

		btnDelete.setOnAction(e->{
			ConfirmationDialog cd = new ConfirmationDialog(this);
			T deletedItem = null;
			T selectedItem = getSelectedItem();
			if (cd.result == ButtonType.OK) {
				if (getRestServiceDelete() != null) {
					deletedItem = getRestServiceDelete().Delete(selectedItem);
					// Ako je uspesno, vraca deleted item, u suprotnom null
					if (deletedItem != null) {
						tableView.getItems().remove(selectedItem);
						tableView.refresh();
						updateButtonsState();
					}
				}

				if (getRestServiceDelete() == null && getDeleteRestServiceAllowedMissing()) {
					T itemToDelete = getSelectedItem();
					if (tableView.getItems().remove(getSelectedItem())) {
						tableView.refresh();
						updateButtonsState();
						deletedItem = itemToDelete;
					}
				}

				if (getRestServiceDelete() == null && !getDeleteRestServiceAllowedMissing()) {
					throw new UnsupportedOperationException("CSTable::btnDelete.setOnAction->No delete rest service call defined! You must define it using CSTable.setRestServiceDelete()!");
				}

				if (deletedItem != null) {
					this.fireEvent(new RowDeletedEvent(selectedItem));
				}
			}
		});
		
		
		/**
		 * Refreshes tableview
		 */
		btnRefresh.setOnAction ( _ -> {
			this.tableView.refresh();
			//this.getRestService().fetch(null)
		});
		
		
		/**
		 * Export to Microsoft Excel
		 */
		btnExcelExport.setOnAction( e -> {
			if (restService != null) {
				System.out.println(getRestService().getUrl());
			} else {
				throw new UnsupportedOperationException("CSTable::btnExcelExport.setOnAction->No rest service call defined! You must define it using CSTable.setRestService()!");
			}
		});
		
		/**
		 * Export to PDF
		 */
		btnPdfExport.setOnAction( e -> {
			
		});


		/*
		 * Listener to add items to component bar. It triggers when CLIENT part of the implementation adds items to the observable list.
		 * TODO: OPTIMIZE! This part of code adds and removes components EVERY TIME when it gets invalidated.
		 * That means that it will be added and removed as many times as there are number of components to add
		 * */
		componentBarItems.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				componentBar.getChildren().removeAll(componentBarItems);
				addComponentBarItems(componentBarItems);
			}
		});

	} // INITIALIZE END


	public void setTop(Node node) {
		borderPane.setTop(node);
	}

	/**
	 * @return the topSectionVisible
	 */
	public boolean isTopSectionVisible() {
		return topSectionVisible;
	}

	/**
	 * @param topSectionVisible the topSectionVisible to set
	 */
	public void setTopSectionVisible(boolean topSectionVisible) {
		this.topSectionVisible = topSectionVisible;

		if(!topSectionVisible) {
			borderPane.setTop(null);
		}
	}

	/**
	 * @return the searchVisible
	 */
	public boolean isSearchVisible() {
		return searchVisible;
	}

	/**
	 * @param searchVisible the searchVisible to set
	 * The Node.managed property prevents a node in a Scene from affecting the layout of other scene nodes
	 * http://docs.oracle.com/javafx/2/api/javafx/scene/Node.html#managedProperty
	 */
	public void setSearchVisible(boolean searchVisible) {
		this.hbSearch.managedProperty().bind(this.hbSearch.visibleProperty());
		this.hbSearch.setVisible(searchVisible);
		this.searchVisible = searchVisible;
	}

	/**
	 * @return the addButtonVisible
	 */
	public boolean isAddButtonVisible() {
		return addButtonVisible;
	}

	/**
	 * @param addButtonVisible the addButtonVisible to set
	 */
	public void setAddButtonVisible(boolean addButtonVisible) {
		this.btnAdd.managedProperty().bind(this.btnAdd.visibleProperty());
		this.addButtonVisible = addButtonVisible;
		this.btnAdd.setVisible(addButtonVisible);
	}

	public boolean isViewButtonVisible() {
		return viewButtonVisible;
	}

	public void setViewButtonVisible(boolean viewButtonVisible) {
		this.btnView.managedProperty().bind(this.btnView.visibleProperty());
		this.btnView.setVisible(viewButtonVisible);
		this.viewButtonVisible = viewButtonVisible;
	}

	/**
	 * @return the editButtonVisible
	 */
	public boolean isEditButtonVisible() {
		return editButtonVisible;
	}

	/**
	 * @param editButtonVisible the editButtonVisible to set
	 */
	public void setEditButtonVisible(boolean editButtonVisible) {
		this.btnEdit.managedProperty().bind(this.btnEdit.visibleProperty());
		this.editButtonVisible = editButtonVisible;
		this.btnEdit.setVisible(editButtonVisible);
	}

	/**
	 * @return the deleteButtonVisible
	 */
	public boolean isDeleteButtonVisible() {
		return deleteButtonVisible;
	}

	/**
	 * @param deleteButtonVisible the deleteButtonVisible to set
	 */
	public void setDeleteButtonVisible(boolean deleteButtonVisible) {
		this.btnDelete.managedProperty().bind(this.btnDelete.visibleProperty());
		this.deleteButtonVisible = deleteButtonVisible;
		this.btnDelete.setVisible(deleteButtonVisible);
	}
	
	/**
	 * @return the refreshButtonVisible
	 */
	public boolean isRefreshButtonVisible() {
		return refreshButtonVisible;
	}

	/**
	 * @param refreshButtonVisible the refreshButtonVisible to set
	 */
	public void setRefreshButtonVisible(boolean refreshButtonVisible) {
		this.btnRefresh.managedProperty().bind(this.btnRefresh.visibleProperty());
		this.refreshButtonVisible = refreshButtonVisible;
		this.btnRefresh.setVisible(refreshButtonVisible);
	}

	
	/**
	 * @return the excelExportButtonVisible
	 */
	public boolean isexcelExportButtonVisible() {
		return excelExportButtonVisible;
	}

	/**
	 * @param excelExportButtonVisible the excelExportButtonVisible to set
	 */
	public void setExcelExportButtonVisible(boolean excelExportButtonVisible) {
		this.btnExcelExport.managedProperty().bind(this.btnExcelExport.visibleProperty());
		this.excelExportButtonVisible = excelExportButtonVisible;
		this.btnExcelExport.setVisible(excelExportButtonVisible);
	}
	
	/**
	 * @return the pdfExportButtonVisible
	 */
	public boolean isPdfExportButtonVisible() {
		return pdfExportButtonVisible;
	}

	/**
	 * @param pdfExportButtonVisible the pdfExportButtonVisible to set
	 */
	public void setPdfExportButtonVisible(boolean pdfExportButtonVisible) {
		this.btnPdfExport.managedProperty().bind(this.btnPdfExport.visibleProperty());
		this.pdfExportButtonVisible = pdfExportButtonVisible;
		this.btnPdfExport.setVisible(pdfExportButtonVisible);
	}

	/**
	 * @return the topLabelVisible
	 */
	public boolean isTopLabelVisible() {
		return topLabelVisible;
	}

	/**
	 * @param topLabelVisible the topLabelVisible to set
	 */
	public void setTopLabelVisible(boolean topLabelVisible) {
		this.topLabel.setVisible(topLabelVisible);
		this.topLabelVisible = topLabelVisible;
		//this.topLabel.setPrefWidth(topLabelVisible == false ? 0 : searchBoxSize);
	}

	/**
	 * @return the topLabelText
	 */
	public String getTopLabelText() {
		return topLabelText;
	}

	/**
	 * @param topLabelText the topLabelText to set
	 */
	public void setTopLabelText(String topLabelText) {
		this.topLabelText = topLabelText;
		topLabel.setText(topLabelText);
	}

	/**
	 * @return the DTOName
	 */
	public String getDTOName() {
		return DTOName;
	}

	/**
	 * @param dTOName the dTOName to set
	 */
	public void setDTOName(String DTOName) {
		this.DTOName = DTOName;	}

	public String getAddEditControllerName() {
		return addEditControllerName;
	}

	@Deprecated
	public void setAddEditControllerName(String addEditControllerName) {
		this.addEditControllerName = addEditControllerName;

		btnAdd.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(this.getAddEditControllerName(), null, 0), this, "%label.add.text");
			tableView.refresh();
			updateButtonsState();
		});

		btnEdit.setOnAction(e->{
			Common.displayForm(ControllerFactory.getController(this.getAddEditControllerName(), this.getSelectedItem(), 1), this, "%label.edit.text");
			tableView.refresh();
			updateButtonsState();
		});

	}


	/**
	 * @return the searchTooltip
	 */
	public String getSearchTooltipText() {
		return searchTooltipText;
	}

	/**
	 * @param searchTooltip the searchTooltip to set
	 */
	public void setSearchTooltipText(String searchTooltipText) {
		this.searchTooltipText = searchTooltipText;
		this.searchTooltip.setText(searchTooltipText);
	}

	public HBox getComponentBar() {
		return componentBar;
	}

	public void setComponentBar(HBox componentBar) {
		this.componentBar = componentBar;
	}

	public boolean isRowCheckboxVisible() {
		return rowCheckboxVisible;
	}

	public void setRowCheckboxVisible(boolean rowCheckboxVisible) {
		if (rowCheckboxVisible == true) {
			lstCheckedItems = new ArrayList<>();
			tableView.setEditable(true); // necessary to make checkbox editable
		}
		this.rowCheckboxVisible = rowCheckboxVisible;
	}
	
	public boolean getAutoNumberColumnVisible() {
		return autoNumberColumnVisible;
	}
	
	public void setAutoNumberColumnVisible (boolean autoNumberColumnVisible) {
		this.autoNumberColumnVisible = autoNumberColumnVisible;
	}

	/**
	 * @return the requestFocus
	 */
	public boolean getSearchRequestFocus() {
		return searchRequestFocus;
	}

	/**
	 * @param searchRequestFocus the requestFocus to set
	 */
	public void setSearchRequestFocus(boolean searchRequestFocus) {
		this.searchRequestFocus = searchRequestFocus;
	}


	public CSTable<?> getParentTable() {
		return tblParent;
	}

	/**
	 * @param tblParent
	 */
	public void setParentTable(CSTable<?> tblParent) {
		this.tblParent = tblParent;
		tblParent.setChildTable(this);
	}

	public List<CSTable<?>> getChildTables() {
		return lstTblChildren;
	}

	private void setChildTable(CSTable<?> tblChild) {
		this.lstTblChildren.add(tblChild);
		//tblChildDetail.setTblMasterParent(this); stsack overflow jer ide u mrtvu petlju
	}

	/*
	 * Set Add/Edit behavior when user clicks on Add/Edit buttons of the grid
	 * 
	 * */
	public void setAddEditDialog (String addEditControllerName) {
		this.setAddEditDialog(addEditControllerName, this, null);
	}
	
	/*
	 * @param String addEditControllerName - Controller which will handle the behaviour of the form
	 * @param Object controllerParam - If the controller needs additional params, we will pass it as an array
	 * where ALWAYS first index of array is table which is owner
	 */
	public void setAddEditDialog (String addEditControllerName, Object controllerParam) {
		this.setAddEditDialog(addEditControllerName, controllerParam, null);
	}

	/**
	 * Base implementation, containing all the method params
	 * All the others are just variation that takes 1, 2 or all 3 params
	 * @param data
	 */
	public void setAddEditDialog (String addEditControllerName, Object controllerParam, Integer p_mode) {
		this.addEditControllerName = addEditControllerName;
		
		btnAdd.setOnAction(e->{
			int currentCnt = getRowCount();

			// We can pass mode to form. If not, for add it is zero
			int mode = 0;
			if (p_mode != null) {
				mode = p_mode;
			}
			Common.displayForm(ControllerFactory.getController(this.getAddEditControllerName(), controllerParam, mode), this, "%label.add.text");
			tableView.refresh();
			if (getParentTable() != null) {
				getParentTable().tableView.refresh();
				getParentTable().updateButtonsState();
			}
	
			// Ako se rowcount razlikuje posle display forme, onda znaci da smo dodali novi red i moramo da selektujemo poslednji red
			// Selektuje prvi ili poslednji dodati red (u zavisnosti od reverseItemsOrder flag-a) i skrolujemo do njega
			if (currentCnt < getRowCount()) {
				if (reverseItemsOrder) {
					selectFirstRow();
				} else {
					selectLastRow();
				}
			}
			
			tableView.requestFocus();
		});
	
		btnEdit.setOnAction(e->{
			// We can pass mode to form. If not, for edit it is one
			int mode = 1;
			if (p_mode != null) {
				mode = p_mode;
			}

			Common.displayForm(ControllerFactory.getController(this.getAddEditControllerName(), controllerParam, mode), this, "%label.edit.text");
			tableView.refresh();
			if (getParentTable() != null) {
				getParentTable().tableView.refresh();
				getParentTable().updateButtonsState();
			}
		});
		
		btnView.setOnAction(e->{
			// We can pass mode to form. If not, for view it is two
			int mode = 2;
			if (p_mode != null) {
				mode = p_mode;
			}
			Common.displayForm(ControllerFactory.getController(this.getAddEditControllerName(), this, mode), this, "%label.view.text");
		});
		
	}
	
	
	//----------------------------------------------------------
	/*
	 * ADDS data to existing Items collection. If we use setItems,
	 * it replaces existing mapped list on the table-view
	 * , and we cannot get listChanged events
	 * */
	public void setItems (ObservableList <T> data) {
		//TODO: check if this is REALLY good way and place to add columns to the table
		if (tableView.getColumns().size() == 0) {
			setColumns(tableColumns);
		}

		//we must remove items from the existing list before adding new ones
		tableView.getItems().removeAll(tableView.getItems());
		tableView.getItems().addAll(data);
		tableView.scrollTo(0);

		//osiguravamo se da, ako postoje podaci u tabeli, uvek imamo selektovan
		//bar jedan red, u ovom slucaju prvi red
		//TODO: ne radi lepo, ovako ce samo da selektuje red, ali nece da okine onRowSelectionChanged
		// tako da npr ostali zavisni redovi iz drugih tabela nece da se popune
		if (this.autoSelectFirstRow && this.getRowCount() > 0) {
			tableView.getSelectionModel().select(0);
			this.fireEvent(new RowSelectionChangedEvent(null, tableView.getSelectionModel().getSelectedItem()));
		}

		//disable ako je prazna tabela, takodje ovo radimo i kod init-a
		updateButtonsState();
	}


	/********************************************
	 * Clears the observable list
	 ********************************************/
	public void clear () {
		Object selectedItem = tableView.getSelectionModel().getSelectedItem();
		btnEdit.setDisable(true);
		btnDelete.setDisable(true);
		tableView.getItems().clear();
		updateButtonsState();
		showProgressIndicator(false);
		fireEvent(new RowSelectionChangedEvent(selectedItem, null));
	}

	/********************************************
	 * Adds items to the existing list, without removing anything, it just adds to the list.
	 ********************************************/
	public void addItems (ObservableList <T> data) {
		if (tableView.getColumns().size() == 0) {
			setColumns(tableColumns);
		}

		tableView.getItems().addAll(data);
		updateButtonsState();
	}
	/********************************************
	 * Adds one T item to the existing list
	 * We should use just this method because it
	 * has updating buttons state
	 ********************************************/
	public void addItem (T item) {
		if (tableView.getColumns().size() == 0) {
			setColumns(tableColumns);
		}

		if (reverseItemsOrder) {
			tableView.getItems().add(0, item);
			selectFirstRow();
		} else {
			tableView.getItems().add(item);
			selectLastRow();
		}
		fireEvent(new RowAddedEvent(item));
		updateButtonsState();
	}
	
	/********************************************
	 * Removes one T item from the existing list
	 * We should use just this method because it
	 * has updating buttons state
	 ********************************************/
	public void removeItem (T item) {
		if (reverseItemsOrder) {
			tableView.getItems().remove(item);
			selectFirstRow();
		} else {
			tableView.getItems().remove(item);
			selectLastRow();
		}
		updateButtonsState();
	}
	//----------------------------------------------------------

	/**
	 * Finds item in the table based on the ID
	 * Every POJO MUST have an ID as primary key identifier
	 * @param id
	 * @return
	 */
	public T findById(Integer id) {
		final String className = "org.code_studio.database." + DTOName;
		try {
		Integer methodRes = null;
		Method method = Class.forName(className).getMethod("getId");
		for (T item : tableView.getItems()) {
			methodRes = (Integer) method.invoke(item);
			if (methodRes == id) {
				return item;
			}
		}
		    return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	//----------------------------------------------------------
	public T getSelectedItem() {
		return tableView.getSelectionModel().getSelectedItem();
	}
	//----------------------------------------------------------
	public int getRowCount() {
		return tableView.getItems().size();
	}

	/**
	 * @return the children
	 */
	public ObservableList <Node> getComponentBarItems() {
		return componentBarItems;
	}

	/**
	 * Add items to HBOX ComponentBar. It is triggered when observable list changes ???
	 */
	public void addComponentBarItems(ObservableList <Node> componentBarItems) {
		componentBar.getChildren().addAll(componentBarItems);
	}

	/*****************************************************************************
	 *
	 * @param columns
	 */
	public void setColumns (List <CSTableColumn> columns) {
		if (this.DTOName == null) throw new NullPointerException("CSTable::DTOName not set!");
		final String className = "org.code_studio.database." + DTOName;

		// Dodavanje 'select all' checkbox kolone
		//TODO: Zavrsiti implementaciju!
		if (this.rowCheckboxVisible) {
			TableColumn <T, T> checkBoxColumn = new TableColumn<>(); // Do not use <T, Boolean> here!
			checkBoxColumn.setReorderable(false);
			checkBoxColumn.setSortable(false);
			checkBoxColumn.setStyle("-fx-alignment: center;");
			checkBoxColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
			// Create custom cell with the check box control:
			checkBoxColumn.setCellFactory(cell -> new TableCell<>() {

	            @Override
	            protected void updateItem(T item, boolean empty) {
	                super.updateItem(item, empty);

	                if (item == null || empty) {
	                    setGraphic(null);
	                } else {
	                    CheckBox checkBox = new CheckBox();
	                    checkBox.setSelected(lstCheckedItems.contains(item));
	                    checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
	                    	if (newValue && !lstCheckedItems.contains(item)) { // ako je checked, dodajemo
	                    		lstCheckedItems.add(item);
	                    		this.fireEvent(new RowCheckedEvent(item));
	                    	} else if (!newValue && lstCheckedItems.contains(item)) {
	                    		lstCheckedItems.remove(item);
	                    		this.fireEvent(new RowUncheckedEvent(item));
	    						updateButtonsState();
	                    	}
	                    });
	                    setGraphic(checkBox);
	                }
	            }
	        });
			
			tableView.getColumns().add(checkBoxColumn);
		}
		
		// Row autonumber column START
		if (this.autoNumberColumnVisible) {
			TableColumn <T, T> autoNumberColumn = new TableColumn<>();
			autoNumberColumn.setGraphic(new Label("#"));
			autoNumberColumn.getStyleClass().add("column-center-right");
			autoNumberColumn.setMinWidth(35); //TODO - OTKUCATI
			autoNumberColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));			
			autoNumberColumn.setCellFactory(cell -> new TableCell<>() {
	
	            @Override
	            protected void updateItem(T item, boolean empty) {
	                super.updateItem(item, empty);
	
	                if (item == null || empty) {
	                    setText(null);
	                } else {
	                	setText(String.valueOf(getIndex() + 1)); // pocinje od 0, tako da dodajemo 1
	                }
	            }
	        });
			tableView.getColumns().add(autoNumberColumn);
		}
		// Row autonumber column END

		columns.forEach(column -> {
			/* Get method name for determining method returning type
			 * Used to add automatically checkboxes to the table cells
			 */
			String columnName = column.getDisplayName();
			String columnTooltip = column.getTooltip();
			String getterName = column.getMethodName();
			int columnWidth    = column.getWidth();
			//ne koristim
			//String backgroundColor = column.getBackgroundColor();
			//String textColor = column.getTextColor();
			String columnType = null;
			Method method     = null;

			try {
				method = Class.forName(className).getMethod("get" + getterName);
				columnType = method.getReturnType().getSimpleName();
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (columnType.equalsIgnoreCase("boolean")) {
				TableColumn <T, Boolean> col = new TableColumn<>();
				//TODO: ponavljanje koda dole ...
				col.setReorderable(false);
				col.setSortable(false);
				Label columnLabel = new Label(columnName);
				if (columnTooltip != null) {
					//columnLabel.setTooltip(new Tooltip(columnTooltip)); // PROFILING
				}
				col.setGraphic(columnLabel);
				
				if (columnWidth > 0) {
					col.setMinWidth(columnWidth);
					col.setMaxWidth(columnWidth);
				}

				final Method fMethod = method;
				col.setCellValueFactory ( cellDataFeatures -> {
					T item   = cellDataFeatures.getValue();
					boolean methodResult = false;

					try {
						Object res = fMethod.invoke(item);
						if (res != null) {
							methodResult = (boolean) res;
						} else { // eksplicitno za null vrednosti Boolean-a iz baze, zakucavamo FALSE na UI CSTable koloni.
							methodResult = false;
						}
			        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			        	System.out.println("Reflect exception - reading method:");
			            System.out.println(e.getCause());
			            System.out.println(fMethod.getName());
			            System.out.println(fMethod.getClass());
			            System.out.println(item.getClass());
			            System.out.println("===============================");
			        }

					SimpleBooleanProperty res = new SimpleBooleanProperty(methodResult);
					return res;
				});

				col.setCellFactory(enclosedColumn -> new CheckBoxTableCell<>() {
					@Override
					public void updateItem(Boolean item, boolean empty) {
						super.updateItem(item, empty);
						if (column.getConditionalFormattingParams() != null) {
							setCellConditionalFormatting(this, column);
						}
						/*
						if (!isEmpty() && item!=null) {
							if (column.getConditionalFormattingParams() != null) {
								setCellConditionalFormatting(this, column);
							}
	                    }
						else {
							setText(null);
							setStyle("-fx-background-color: null; -fx-text-fill: black"); // fixes random colored cells
						}
						*/
					}
				});


				// BUDZ za sirinu column headera
				Text theText = new Text(columnLabel.getText());
				theText.setFont(columnLabel.getFont());
				col.setMinWidth(theText.getBoundsInLocal().getWidth() + columnWidthPixelAdd);
				
				// HIDE the column
				if (!column.isVisible()) {
					col.setVisible(false);
				}
				tableView.getColumns().add(col);

			} else if (columnType.equalsIgnoreCase("date")) {
				SimpleDateFormat datetimeSimpleFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

				TableColumn <T, Date> col = new TableColumn<>();
				col.setReorderable(false);
				col.setSortable(false);
				Label columnLabel = new Label(columnName);

				if (columnTooltip != null) {
					// columnLabel.setTooltip(new Tooltip(columnTooltip)); // PROFILING
				}
				col.setGraphic(columnLabel);

				if (columnWidth > 0) {
					col.setMinWidth(columnWidth);
					col.setMaxWidth(columnWidth);
				}

				// setcellfactory je neophodan ako zelimo da menjamo bilo sta od prikaza unutar celije, kao sto ovde menjamo bg color i text color
				col.setCellFactory(enclosedColumn -> new TableCell<> () {

					@Override
					public void updateItem(Date item, boolean empty) {
						super.updateItem(item, empty);
						if (column.getConditionalFormattingParams() != null) {
							setCellConditionalFormatting(this, column);
						}
						if (!isEmpty() && item!=null) {
								setText(datetimeSimpleFormat.format(item));
	                      }
						else {
							setText(null);
						}
						//TODO OVde implementirati npr onCellValue, pa da obojim cell u zavisnosti od value itd ...
					}
				});

				col.setCellValueFactory(new PropertyValueFactory<T, Date>(getterName));

				// BUDZ za sirinu column headera
				Text theText = new Text(columnLabel.getText());
				theText.setFont(columnLabel.getFont());
				col.setMinWidth(theText.getBoundsInLocal().getWidth() + columnWidthPixelAdd);
				
				// HIDE the column
				if (!column.isVisible()) {
					col.setVisible(false);
				}
				tableView.getColumns().add(col);

			} else if (columnType.equalsIgnoreCase("localdatetime")) {
				//SimpleDateFormat datetimeSimpleFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				//DateTimeFormatter datetimeFormat = new DateTimeFormatter("dd.MM.yyyy HH:mm");

				TableColumn <T, LocalDateTime> col = new TableColumn<>();
				col.setReorderable(false);
				col.setSortable(false);
				Label columnLabel = new Label(columnName);

				if (columnTooltip != null) {
					// columnLabel.setTooltip(new Tooltip(columnTooltip)); // PROFILING
				}
				col.setGraphic(columnLabel);

				if (columnWidth > 0) {
					col.setMinWidth(columnWidth);
					col.setMaxWidth(columnWidth);
				}

				// setcellfactory je neophodan ako zelimo da menjamo bilo sta od prikaza unutar celije, kao sto ovde menjamo bg color i text color
				col.setCellFactory(enclosedColumn -> new TableCell<> () {

					@Override
					public void updateItem(LocalDateTime item, boolean empty) {
						super.updateItem(item, empty);
						if (column.getConditionalFormattingParams() != null) {
							setCellConditionalFormatting(this, column);
						}
						if (!isEmpty() && item!=null) {
							setText(item.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
	                    }
						else {
							setText(null);
						}
						//TODO OVde implementirati npr onCellValue, pa da obojim cell u zavisnosti od value itd ...
					}
				});

				col.setCellValueFactory(new PropertyValueFactory<T, LocalDateTime>(getterName));

				// BUDZ za sirinu column headera
				Text theText = new Text(columnLabel.getText());
				theText.setFont(columnLabel.getFont());
				col.setMinWidth(theText.getBoundsInLocal().getWidth() + columnWidthPixelAdd);
				
				// HIDE the column
				if (!column.isVisible()) {
					col.setVisible(false);
				}
				tableView.getColumns().add(col);

			} else if (columnType.equalsIgnoreCase("localdate")) {
				//TODO: pattern dodati u neki config
				DateTimeFormatter datetimeSimpleFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

				TableColumn <T, LocalDate> col = new TableColumn<>();
				col.setReorderable(false);
				col.setSortable(false);
				Label columnLabel = new Label(columnName);

				if (columnTooltip != null) {
					// columnLabel.setTooltip(new Tooltip(columnTooltip)); // PROFILING
				}
				col.setGraphic(columnLabel);

				if (columnWidth > 0) {
					col.setMinWidth(columnWidth);
					col.setMaxWidth(columnWidth);
				}

				// setcellfactory je neophodan ako zelimo da menjamo bilo sta od prikaza unutar celije, kao sto ovde menjamo bg color i text color
				col.setCellFactory(enclosedColumn -> new TableCell<> () {

					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (column.getConditionalFormattingParams() != null) {
							setCellConditionalFormatting(this, column);
						}
						if (!isEmpty() && item!=null) {
								setText(datetimeSimpleFormat.format(item));
	                      }
						else {
							setText(null);
						}
						//TODO OVde implementirati npr onCellValue, pa da obojim cell u zavisnosti od value itd ...
					}
				});

				col.setCellValueFactory(new PropertyValueFactory<T, LocalDate>(getterName));

				// BUDZ za sirinu column headera
				Text theText = new Text(columnLabel.getText());
				theText.setFont(columnLabel.getFont());
				col.setMinWidth(theText.getBoundsInLocal().getWidth() + columnWidthPixelAdd);
				
				// HIDE the column
				if (!column.isVisible()) {
					col.setVisible(false);
				}
				tableView.getColumns().add(col);
				
			} else if (columnType.equalsIgnoreCase("BigDecimal")) {
		    	DecimalFormatSymbols serbianSymbols = new DecimalFormatSymbols(Locale.getDefault());
		    	serbianSymbols.setDecimalSeparator('.');
		    	serbianSymbols.setGroupingSeparator(',');

		        DecimalFormat formatter = new DecimalFormat("###,##0.00", serbianSymbols);
		        formatter.setGroupingUsed(true);

				TableColumn <T, BigDecimal> col = new TableColumn<>();
				col.setReorderable(false);
				col.setSortable(false);
				Label columnLabel = new Label(columnName);

				if (columnTooltip != null) {
					// columnLabel.setTooltip(new Tooltip(columnTooltip)); // PROFILING
				}
				col.setGraphic(columnLabel);

				if (columnWidth > 0) {
					col.setMinWidth(columnWidth);
					col.setMaxWidth(columnWidth);
				}

				// setcellfactory je neophodan ako zelimo da menjamo bilo sta od prikaza unutar celije, kao sto ovde menjamo bg color i text color
				col.setCellFactory(enclosedColumn -> new TableCell<> () {

					@Override
					public void updateItem(BigDecimal item, boolean empty) {
						super.updateItem(item, empty);
						if (column.getConditionalFormattingParams() != null) {
							setCellConditionalFormatting(this, column);
						}
						if (!isEmpty() && item!=null) {
								setText(formatter.format(item));
	                      }
						else {
							setText(null);
						}
						//TODO OVde implementirati npr onCellValue, pa da obojim cell u zavisnosti od value itd ...
					}
				});

				col.setCellValueFactory(new PropertyValueFactory<T, BigDecimal>(getterName));

				// BUDZ za sirinu column headera
				Text theText = new Text(columnLabel.getText());
				theText.setFont(columnLabel.getFont());
				col.getStyleClass().add("column-center-right");
				col.setMinWidth(theText.getBoundsInLocal().getWidth() + columnWidthPixelAdd);
				
				// HIDE the column
				if (!column.isVisible()) {
					col.setVisible(false);
				}
				tableView.getColumns().add(col);
				
			} else {
				TableColumn <T, Object> col = new TableColumn<>();
				col.setReorderable(false);
				col.setSortable(false);
				Label columnLabel = new Label(columnName);
				
				// right alignment for integer column type
				if (columnType.equalsIgnoreCase("Integer")) {
					col.getStyleClass().add("column-center-right");
				}
				
				// adds style class for alignment, for example RIGHT, defined in FXML
				if (column.getAlignment() != null) {
					col.getStyleClass().add(column.getAlignment());
				}

				if (columnTooltip != null) {
					// columnLabel.setTooltip(new Tooltip(columnTooltip)); // PROFILING
				}
				col.setGraphic(columnLabel);

				if (columnWidth > 0) {
					col.setMinWidth(columnWidth);
					col.setMaxWidth(columnWidth);
				}

				// setcellfactory je neophodan ako zelimo da menjamo bilo sta od prikaza unutar celije, kao sto ovde menjamo bg color i text color
				col.setCellFactory(enclosedColumn -> new TableCell<> () {
					//TableCell<T, String> cell = new CheckBoxTableCell<>();

					@Override
					public void updateItem(Object item, boolean empty) {
						super.updateItem(item, empty);
						if (column.getConditionalFormattingParams() != null) {
							setCellConditionalFormatting(this, column);
						}
						
						if (!empty && item != null) {
	                        setText(String.valueOf(item));
	                        if (item != null && String.valueOf(item) != "") {
	                        	//setTooltip(new Tooltip(String.valueOf(item))); // PROFILING
	                        }
	                      }
						else {
							setText(null);
						}
					}
				});

				col.setCellValueFactory(new PropertyValueFactory<T, Object>(getterName));

				// BUDZ za sirinu column headera
				Text theText = new Text(columnLabel.getText());
				theText.setFont(columnLabel.getFont());
				col.setMinWidth(theText.getBoundsInLocal().getWidth() + columnWidthPixelAdd);
				
				// HIDE the column
				if (!column.isVisible()) {
					col.setVisible(false);
				}
				tableView.getColumns().add(col);
			}

		});
	}

	/*----------------------------------------------------------
	 * Used to set background and foreground color of the cell
	 * Also, check for conditional formatting and applies
	 * colors if needed
	 * Format in FXML is as follows: conditionalFormattingParams="lt,0"
	 * where lt is less than, and second value is value to which 
	 * column value is compared to, in this case zero.
	 * So, if column value is less than zero, it will have 
	 * conditional formatting
	 * If we want to compare this cell value to another column in table for selected row,
	 * we need to provide 3 params. 1: comparison lt|eq|gt; 2: value of column string|Integer|Double; 3: Index of column to compare Integer.
	 * Example: We want to compare if column 3 has value equal to "HAMA": conditionalFormattingParams="eq,HAMA,3". This will set conditional formatting to the
	 * column where we defined it in FXML, but takes value of another column to compare it to.
	----------------------------------------------------------*/
	private final void setCellConditionalFormatting(TableCell<T, ?> cell, CSTableColumn csTableColumn) {

		String[] formattingParams = csTableColumn.getConditionalFormattingParams();

		if (formattingParams != null && formattingParams.length > 1 && formattingParams.length <= 5) {
			String strCondition = formattingParams[0];
			String strStringToCompare = formattingParams[1].strip();
			
			// Ako imamo tri parametra, treci je indeks kolone sa kojom moramo da uporedimo vrednost
			Integer columnIndexToCompare = formattingParams.length > 2 
					? Integer.parseInt(formattingParams[2])
					: null;
			
			//Proveravamo da li su auto kolone checkbox i autonumber visible, i ako jesu onda moramo da povecamo index za 1 ili 2
			if (columnIndexToCompare != null) {
				columnIndexToCompare = rowCheckboxVisible ? columnIndexToCompare + 1 : columnIndexToCompare;
				columnIndexToCompare = autoNumberColumnVisible ? columnIndexToCompare + 1 : columnIndexToCompare;
			}

			// Ako imamo cetiri parametra, cetvrti je da li primenjujemo formatting na cell (0) ili row (1)
			Integer applyToRow = formattingParams.length >= 4
					? Integer.parseInt(formattingParams[3])
					: 0;
			
			// Ako imamo pet parametara, peti je boja tj color stil koji treba da primenimo, default je red
			String colorStyle = formattingParams.length == 5
					? "highlighted-" + formattingParams[4].toLowerCase()
					: "highlighted-red";
			
			String cellValue = columnIndexToCompare == null
					? cell.getItem() == null ? "null" : cell.getItem().toString()
					: tableView.getColumns().get(columnIndexToCompare).getCellData(cell.getIndex()) == null ? "null"
							: tableView.getColumns().get(columnIndexToCompare).getCellData(cell.getIndex())
					.toString();
			
			switch (strCondition) {
				//less than
				case "lt":
					if (!cellValue.equalsIgnoreCase("null") && Double.parseDouble(cellValue.replaceAll("[,]", "")) < Double.parseDouble(strStringToCompare) && cell.getTableRow().getIndex() <= tableView.getItems().size() - 1) {
						setCssClass(cell, colorStyle, applyToRow);
					} else {
					}
				break;

				//equal to
				case "eq":
					if (cellValue.equalsIgnoreCase(strStringToCompare) && cell.getTableRow().getIndex() <= tableView.getItems().size() - 1) {
						setCssClass(cell, colorStyle, applyToRow);
					} else {
						setCssClass(cell, null, applyToRow);
					}
				break;
				
				//NOT equal to
				case "neq":
					if (!cellValue.equalsIgnoreCase("null") && !cellValue.equalsIgnoreCase(strStringToCompare) && cell.getTableRow().getIndex() <= tableView.getItems().size() - 1) {
						setCssClass(cell, colorStyle, applyToRow);
					} else {
						setCssClass(cell, null, applyToRow);
					}
				break;

				//grater than
				case "gt":
					if (!cellValue.equalsIgnoreCase("null") && Double.parseDouble(cellValue.replaceAll("[,]", "")) > Double.parseDouble(strStringToCompare) && cell.getTableRow().getIndex() <= tableView.getItems().size() - 1) {
						setCssClass(cell, colorStyle, applyToRow);
					} else {
						setCssClass(cell, null, applyToRow);
					}
				break;

				default:
					setCssClass(cell, null, applyToRow);
				break;
			}

		}
	}


	/*
	 * Uses functional interface so the implementor can use lambda expression instead of overrides
	 * TODO: find way to remove already registered listener on observable list selectedItems
	 * because it gets fired twice for one event if we have additional listener i.e. on data needed
	 * */
	public final void onRowSelectionChanged (CSTableRowSelectionChangedListener listener) {
		ObservableList <T> selectedItems = tableView.getSelectionModel().getSelectedItems();

		selectedItems.addListener(new ListChangeListener <T> () {

			@Override
			public void onChanged(Change <? extends T> change) {
				T unSelected = null;
				T selected = null;

				// Ako je change prazna tabela, moramo da clearujemo child tabelu jer njen master nema nista u sebi
				if (!lstTblChildren.isEmpty()) {
					for (CSTable<?> tbl : lstTblChildren) {
						tbl.clear();
					}
				}

				if (change.getList().size() > 0) {
					while (change.next()) {
						unSelected = change.getRemoved().size() > 0 ? (T) change.getRemoved().get(0) : null;
						selected = change.getList().get(0);
						updateButtonsState();
						listener.onRowSelectionChanged(unSelected, selected);
					}
				} else { //change is actually empty table
					updateButtonsState();
					listener.onRowSelectionChanged(null, null);
				}
			}
		});
	}


	/*
	 * Fired when CSTable tableView gains or loses focus.
	 * We must use focus on tableview because CSTable does not gain focus directly
	 */
	public final void onFocusChanged (CSTableFocusChangedListener listener) {
		tableView.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				listener.onFocusChanged(oldValue, newValue);
			}
		});
	}

	/**
	 * Used internally to register drag and drop listeners
	 * @param acceptedDragObjectClass. Class type that table accepts as custom format to drag and drop.
	 * It is always <T>, but since we cannot get it in runtime, we must set it manually in compile time.
	 */
	@SuppressWarnings("unchecked")
	public final void setOnDragDetected(Class<T> acceptedDragObjectClass) {
		DataFormat csDataFormat;
		DataFormat checkDataFormat = DataFormat.lookupMimeType(acceptedDragObjectClass.getName());
		if (checkDataFormat == null) {
			checkDataFormat = new DataFormat(acceptedDragObjectClass.getName());
		}

		//budz za must be final
		csDataFormat = checkDataFormat;

		tableView.setOnDragDetected( e-> {
			Dragboard dragBoard = tableView.startDragAndDrop(TransferMode.MOVE);
			Object selectedItem = tableView.getSelectionModel().getSelectedItem();

			if(selectedItem != null) {
				((Node) e.getSource()).setCursor(Cursor.CLOSED_HAND);
				ClipboardContent clipboardContent = new ClipboardContent();
				clipboardContent.put(csDataFormat, tableView.getSelectionModel().getSelectedItem());
				dragBoard.setContent(clipboardContent);
			}
			e.consume();

			//if (dragBoard != null && selectedItem != null)
			//	System.out.println(dragBoard.getContent(csDataFormat));
		});

		// Cursor
		tableView.setOnDragExited( e -> {
            ((Node) e.getSource()).setCursor(Cursor.DEFAULT);
            //e.consume();
        });

		// Data dropped on this node from other node. Not fired if the same node drags and drops
		tableView.setOnDragDropped( e -> {
			boolean itemAlreadyInTable = false;
			Method method = null;
			Integer integerSourceMethodResult = null;
			Long longSourceMethodResult = null;
			((Node) e.getSource()).setCursor(Cursor.DEFAULT);

			if (e.getDragboard().getContent(csDataFormat) != null) {
				T item = (T) e.getDragboard().getContent(csDataFormat);

				try {
					method = Class.forName(acceptedDragObjectClass.getName()).getMethod("getId");
					switch(method.getReturnType().getSimpleName()) {
						case "Integer":
							integerSourceMethodResult = (Integer) method.invoke(item);
						break;
						case "Long":
							longSourceMethodResult = (Long) method.invoke(item);
						break;
						default:
						break;
					}

					//find all IDs already existing in target table
					for (T iterItem : tableView.getItems()) {
						Integer intRes = null;
						Long longRes = null;
						if (integerSourceMethodResult != null) {
							intRes = (Integer) method.invoke(iterItem);
							if (integerSourceMethodResult.intValue() == intRes.intValue()) {
							    itemAlreadyInTable = true;
							    break;
							}
						} else {
							longRes = (Long) method.invoke(iterItem);
							if (longSourceMethodResult.longValue() == longRes.longValue()) {
							    itemAlreadyInTable = true;
							    break;
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				if (!itemAlreadyInTable) {
					e.setDropCompleted(true);
					if (getRowCount() == 0) {
						ObservableList<T> itemList = FXCollections.observableArrayList();
						itemList.add(item);
						setItems(itemList);
					} else {
						addItem(item);
					}
				} else {
					e.setDropCompleted(false);
					Common.ShowNotification("Neuspešan prenos podataka!", "Izabrani red se već nalazi u destinacionoj tabeli!", true);
				}
			}
			e.consume();
		});

		//Ako smo uspesno dropovali item u target, obrisi ga iz source-a
		//da useri ne bi mogli vise puta isti red da prevlace
		tableView.setOnDragDone(e -> {
			System.out.println(e.getTransferMode());
			if (e.getGestureSource() != e.getGestureTarget()
					&& e.getGestureTarget() != null
					&& e.getTransferMode() == TransferMode.MOVE) {
				this.tableView.getItems().remove(this.getSelectedItem());
				this.updateButtonsState();
			}
		});

		//inicijalno mora ovaj da se procesira u targetu
		// jer se ovde definise da li i sta prihvata kao drop operation
		tableView.setOnDragOver( e -> {
            /* data is dragged over the target */
            /* accept it only if it is not dragged from the same node
             * and if it has a string data */
            if (e.getGestureSource() != e.getSource()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

	}
	
    public ScrollBar getVerticalScrollbar() {
        ScrollBar result = null;
        for (Node n : this.lookupAll(".scroll-bar:vertical")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    result = bar;
                }
            }
        }
        return result;
    }

    /*
     * Calls passed argument method when double click was detected on row
     *
     * */
    public final void onRowDoubleClick(CSTableRowDoubleClickListener listener) {
    	tableView.setRowFactory( tv -> {
    	    TableRow<T> row = new TableRow<>();
    	    
    	    row.setOnMouseClicked(event -> {
    	        if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
    	            T rowData = row.getItem();
    	            //TODO: Make it parameterizable in properties file or similar
    	            //By default on double click show edit form of the table
    	            listener.onRowDoubleClick(rowData);

    	            if (this.showDoubleClickDefaultAction)
    	            	if (editButtonVisible && btnEdit.getOnAction() != null) {
    	            		btnEdit.fire();
    	            	} else if (viewButtonVisible && btnView.getOnAction() != null) {
    	            		btnView.fire();
						} else if (addButtonVisible && btnAdd.getOnAction() != null) {
    	            		btnAdd.fire();
						}
    	        }

    	    });

    	    // Do the same ON ENTER RELEASED
			this.setOnKeyReleased(e->{
				if (e.getCode().equals(KeyCode.ENTER)) {
					/* Do sada je bilo da hoce da se default akcija pokrece na enter */
					if ((!enhancedSearch && tfSearchBox.getText().isEmpty()) 
						|| enhancedSearch && tfSearchBox.getText().isEmpty()) {
					listener.onRowDoubleClick(row.getItem());
    	            if (this.showDoubleClickDefaultAction)
    	            	if (editButtonVisible && btnEdit.getOnAction() != null) {
    	            		btnEdit.fire();
    	            	} else if (viewButtonVisible && btnView.getOnAction() != null) {
    	            		btnView.fire();
						} else if (addButtonVisible && btnAdd.getOnAction() != null) {
    	            		btnAdd.fire();
						}
					} // enhancedSearch END
					else {
						// Sada hoce search :)
						if (enhancedSearchStatus) {
							btnSearch.fire();
							this.enhancedSearchStatus = false;
						} else {
							listener.onRowDoubleClick(row.getItem());
		    	            if (this.showDoubleClickDefaultAction) {
		    	            	if (editButtonVisible && btnEdit.getOnAction() != null) {
		    	            		btnEdit.fire();
		    	            	} else if (viewButtonVisible && btnView.getOnAction() != null) {
		    	            		btnView.fire();
								} else if (addButtonVisible && btnAdd.getOnAction() != null) {
		    	            		btnAdd.fire();
								}
		    	            }
		    	            
						}
					}
					
				} else if (e.getCode().equals(KeyCode.BACK_SPACE)) { // ako user pritiska dugmice na tabeli, popuni search box, ako je vidljiv
					if (searchVisible && enhancedSearch && !tfSearchBox.isFocused()) {
						this.tfSearchBox.deletePreviousChar();
						this.enhancedSearchStatus = true;
					}
				} else if (e.getCode().equals(KeyCode.SPACE)) {
					if (enhancedSearch && !tfSearchBox.isFocused()) {
						this.tfSearchBox.appendText(e.getCode().getChar());
						this.enhancedSearchStatus = true;
					}
				} else if (e.getCode().isLetterKey() || e.getCode().isDigitKey()) {
					if (enhancedSearch && !tfSearchBox.isFocused()) {
						this.tfSearchBox.appendText(e.getText());
						this.enhancedSearchStatus = true;
					}
				}
			});

    	    return row ;
    	});

    }


    /*
     * Called when scroll passes 3/4 of the scroll-bar.
     * Then, table calls this method, where implementor needs to define rest service for data fetch
     * */
    public final void onDataNeeded (CSTableDataNeededListener listener) {
    	this.setEventHandler(DataNeededEvent.DATA_NEEDED, new CSTableEventHandler() {

			@Override
			public void onDataNeeded() {
				listener.onDataNeeded();
			}

			@Override
			public void onRender() {}

			@Override
			public void onRowSelectionChanged(Object deselectedItem, Object selectedItem) {}
			
			@Override
			public void onRowAdded(Object addedItem) {}
			
			@Override
			public void onRowDeleted(Object deletedItem) {}
			
			@Override
			public void onRowChecked(Object checkedItem) {}
			
			@Override
			public void onRowUnchecked(Object uncheckedItem) {}
		});
    }
    
    /*
     * Called when user clicks on btnDelete and deletes the row from the table
     * */
    public final void onRowDeleted (CSTableRowDeletedListener listener) {
    	this.setEventHandler(RowDeletedEvent.ROW_DELETED, new CSTableEventHandler() {

			@Override
			public void onRowDeleted(Object deletedItem) {
				listener.onRowDeleted(deletedItem);
			}
    		
			@Override
			public void onDataNeeded() {}

			@Override
			public void onRender() {}
			
			@Override
			public void onRowSelectionChanged(Object deselectedItem, Object selectedItem) {}
			
			@Override
			public void onRowAdded(Object addedItem) {}
			
			@Override
			public void onRowChecked(Object checkedItem) {}
			
			@Override
			public void onRowUnchecked(Object uncheckedItem) {}
		});
    }
    
    
    /*
     * Called when user clicks on checkbox in the table row and CHECKS the row
     * */
    public final void onRowChecked (CSTableRowCheckedListener listener) {
    	this.setEventHandler(RowCheckedEvent.ROW_CHECKED, new CSTableEventHandler() {

			@Override
			public void onRowChecked(Object checkedItem) {
				/**
				 * if we do not explicitly request focus, 
				 * user can click and select row, 
				 * but focus would remain on the previous control
				 */
				tableView.requestFocus();
				listener.onRowChecked(checkedItem);
			}
			
			@Override
			public void onRowAdded(Object addedItem) {}
			
			@Override
			public void onRowSelectionChanged(Object deselectedItem, Object selectedItem) {}
    		
			@Override
			public void onRowDeleted(Object deletedItem) {}
    		
			@Override
			public void onDataNeeded() {}

			@Override
			public void onRender() {}
			
			@Override
			public void onRowUnchecked(Object uncheckedItem) {}
		});
    }
    
    
    /*
     * Called when user clicks on checkbox in the table row and UNCHECKS the row
     * */
    public final void onRowUnchecked (CSTableRowUncheckedListener listener) {
    	this.setEventHandler(RowUncheckedEvent.ROW_UNCHECKED, new CSTableEventHandler() {

			@Override
			public void onRowUnchecked(Object uncheckedItem) {
				listener.onRowUnchecked(uncheckedItem);
			}
    		
			@Override
			public void onRowChecked(Object checkedItem) {}
    		
			@Override
			public void onRowAdded(Object addedItem) {}
			
			@Override
			public void onRowSelectionChanged(Object deselectedItem, Object selectedItem) {}
			
			@Override
			public void onRowDeleted(Object deletedItem) {}
    		
			@Override
			public void onDataNeeded() {}

			@Override
			public void onRender() {}
		});
    }
    
    
    /*
     * Called when the SINGLE row is added to the ITEMS list of the tableView
     * */
    public final void onRowAdded (CSTableRowAddedListener listener) {
    	this.setEventHandler(RowAddedEvent.ROW_ADDED, new CSTableEventHandler() {

			@Override
			public void onRowAdded(Object addedItem) {
				listener.onRowAdded(addedItem);
			}
			
			@Override
			public void onRowSelectionChanged(Object deselectedItem, Object selectedItem) {}
    		
			@Override
			public void onRowChecked(Object checkedItem) {}
			
			@Override
			public void onRowUnchecked(Object uncheckedItem) {}
			
			@Override
			public void onRowDeleted(Object deletedItem) {}
    		
			@Override
			public void onDataNeeded() {}

			@Override
			public void onRender() {}
		});
    }


    /*
     * Used to determine when the tableview was rendered, so we can obtain an instance of scroll-bar
     * */
    @Override
    protected void layoutChildren() {
    	super.layoutChildren();
        if (!this.isRendered) {
            //This is the first time the node is rendered. Event trigger logic should be here
        	this.tableViewVerticalScrollbar = this.getVerticalScrollbar();
        	//System.out.println("Rendered");
        	this.isRendered = true;
        	this.fireEvent(new ComponentRenderedEvent());
        }
    }

	public List<CSTableColumn> getTableColumns() {
		return tableColumns;
	}

	/*
	 * Manages search text within the table and updates its contents. Does search only locally.
	 * TODO: Ako je neka vrednost bar jedne kolone po kojoj se pretrazuje jednaka NULL, metod pukne.
	 */
	private boolean localSearch (T item, String searchText, String... methods) {
		boolean res = false;
		String methodResult;
		Class<?> methodParam = null;

		for (String method2 : methods) {

			try {
				Method method = item.getClass().getMethod("get" + method2, methodParam);
				methodResult = (String) method.invoke(item, methodParam);
				res = !res ? methodResult.toLowerCase().contains(searchText.toLowerCase()) : res;
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

	private Predicate<T> createPredicate(String searchText, String... methods){
	    return item -> {
	        if (searchText == null || searchText.isEmpty()) return true;
	        return localSearch(item, searchText, methods);
	    };
	}

	/*
	 * String... methods is the list of method names to call when performing the search
	 */
	public void addSearchListener(String... methods) {
		FilteredList<T> filteredData = new FilteredList<>(this.tableView.getItems());
		this.tableView.setItems(filteredData);

		tfSearchBox.textProperty().addListener((observable, oldValue, newValue) ->
	    filteredData.setPredicate(createPredicate(newValue, methods)));
	}

	public final void onServerSearch(CSTableSearchListener listener) {
		btnSearch.setOnAction(e->{
			tableView.requestFocus();
			e.consume();
			listener.onServerSearch();
		});

		tfSearchBox.setOnKeyReleased(e->{
			if (e.getCode().equals(KeyCode.ENTER)) {
				tableView.requestFocus();
				e.consume();
				listener.onServerSearch();
				this.enhancedSearchStatus = false;
			}
		});
	}
	

	/**
	 * TODO: NEZAVRSENA IMPLEMENTACIJA
	 * @author gkljajic
	 *
	 *
	static final class ColumnResizePolicy implements Callback<TableView.ResizeFeatures, Boolean>
	{
	    double mTVWidth;

	    @Override
	    public Boolean call(ResizeFeatures arg0)
	    {
	        TableView tableView = arg0.getTable();
	        Double tvWidth = tableView.widthProperty().getValue();
	        if (tvWidth == null || tvWidth <= 0.0 || tableView.getColumns().size() == 0)
	        {
	            return false;
	        }

	        if (mTVWidth != tvWidth && arg0.getColumn() == null)
	        {
	            double fixedColumnsWidths = 0;
	            for (TableColumn col : new ArrayList<TableColumn>(tableView.getColumns()))
	            {
	                if (col.isVisible()) {
	                    fixedColumnsWidths += col.getWidth();
	                }
	            }

	            /*
	            if (numColsToSize == 0)
	                return false;//TableView.UNCONSTRAINED_RESIZE_POLICY.call(arg0);



	            TableColumn lastCol = null;
	            for (TableColumn col : new ArrayList<TableColumn>(tv.getColumns()))
	            {
	                if (col.isResizable() && col.isVisible())
	                {
	                    double newWidth = (tvWidth - fixedColumnsWidths) / numColsToSize;
	                    col.setPrefWidth(newWidth);
	                    lastCol = col;
	                }
	            }
	            if (lastCol != null)
	            {
	                lastCol.setPrefWidth(lastCol.getPrefWidth()-2);
	            }
	        	 *

	        	TableColumn lastColumn = (TableColumn) tableView.getColumns().get(tableView.getColumns().size() - 1);
	        	lastColumn.setPrefWidth((tableView.getWidth() - fixedColumnsWidths - lastColumn.getWidth()));

	            return true;
	        }
	        else
	        {
	            return false;//TableView.UNCONSTRAINED_RESIZE_POLICY.call(arg0);
	        }
	    }
	}***/

	@SuppressWarnings("rawtypes")
	public static final Callback<ResizeFeatures, Boolean> LASTCOLUMN_RESIZE_POLICY = new Callback<>() {

		 	/**
	        @Override public Boolean call(ResizeFeatures prop) {
	            double result = 0.0;  //TableUtil.resize(prop.getColumn(), prop.getDelta());
	            System.out.println("DELTA: ");
	            System.out.println(prop.getDelta());
	            return Double.compare(result, 0.0) == 0;
	        }
	        **/

		 double mTVWidth;

		@SuppressWarnings("unchecked")
		@Override
		    public Boolean call(ResizeFeatures arg0)
		    {
		        TableView tableView = arg0.getTable();
		        Double tvWidth = tableView.widthProperty().getValue();
		        boolean hasVerticalScrollbar = false;
		        int minColumnWidth = 0;

		        // TODO: DUPLIRANJE KODA
		        for (Node n : tableView.lookupAll(".scroll-bar:vertical")) {
		            if (n instanceof ScrollBar) {
		                ScrollBar bar = (ScrollBar) n;
		                if (bar.getOrientation().equals(Orientation.VERTICAL) && bar.isVisible()) {
		                	//System.out.println("has");
		                	hasVerticalScrollbar = true;
		                }
		            }
		        }

		        int offsetValue = hasVerticalScrollbar ? 18 : 3;

		        if (tvWidth == null || tvWidth <= 0.0 || tableView.getColumns().size() == 0)
		        {
		            return TableView.UNCONSTRAINED_RESIZE_POLICY.call(arg0);
		        }

		        if (mTVWidth != tvWidth && arg0.getColumn() == null)
		        {
		            int fixedColumnsWidths = 0;
		            for (TableColumn col : new ArrayList<TableColumn>(tableView.getColumns()))
		            {
		                if (col.isVisible()) {
							// originalno
							//fixedColumnsWidths += col.getWidth();

							// gleda prvo minwidth, ali izgleda da i ovo ne radi dobro
							// jer za neke kolone za koje nisam definisao minwidth, on postavi
		                    fixedColumnsWidths += col.getMinWidth() == 0 ? col.getWidth() : col.getMinWidth();
		                }
		            }

		        	TableColumn lastColumn = (TableColumn) tableView.getColumns().get(tableView.getColumns().size() - 1);
		        	//TODO: ispravno izmeriti minwidth, maxwidth, kao sto se radi u tableutil
		        	//takodje, uzeti u obzir da li tabela ima scrollbar ... to ga zeza nesto u sirini. Treba oduzeti i sirinu scrollbara
		        	minColumnWidth = (int) tableView.getWidth() - fixedColumnsWidths + (int) lastColumn.getWidth() - offsetValue;
		        	lastColumn.setMinWidth(minColumnWidth);
		        	lastColumn.setPrefWidth(minColumnWidth);
		        	lastColumn.setMaxWidth(minColumnWidth);
		            return true;
		        }
		        else
		        {
		            return TableView.UNCONSTRAINED_RESIZE_POLICY.call(arg0);
		        }
		    }

	    };


    public Boolean getAutoSelectFirstRow() {
		return autoSelectFirstRow;
	}

	public void setAutoSelectFirstRow(Boolean autoSelectFirstRow) {
		this.autoSelectFirstRow = autoSelectFirstRow;
	}

	/***************************************************
     * SELECTS ROW IN TABLE BASED ON THE PROVIDED ITEM
     * Checks provided name, method for comparison
     ***************************************************/
	private void select (T item, String comparisonMethod, boolean scroll) {
		String methodResult;
		String selectedMethodResult = null;
		Method method = null;
		int itemIndex = -1;

		try {
			method = Class.forName(item.getClass().getName()).getMethod("get" + comparisonMethod);
			selectedMethodResult =  method.invoke(item).toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i=0; i < tableView.getItems().size(); i++) {
			try {
				T iteritem = tableView.getItems().get(i);
				methodResult = method.invoke(iteritem).toString();

				if (methodResult.equals(selectedMethodResult)) {
					itemIndex = i;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		tableView.getSelectionModel().clearSelection();
		if (itemIndex > -1) {
			tableView.getSelectionModel().select(itemIndex);
			if (scroll) {
				tableView.scrollTo(itemIndex); // TODO: ne radi dok se ne renderuje cela komponenta
			}
		}
	}
	
	
	/**
	 * Selects item and scrolls the tableView to it.
	 */
	public void select(T item, String comparisonMethod) {
		select(item, comparisonMethod, true);
	}
	
	/**
	 * Selects item but does not scrol the tableView to it.
	 */
	public void selectNoScroll(T item, String comparisonMethod) {
		select(item, comparisonMethod, false);
	}

	/***************************************************
     * Updates enabled/disabled state of Add/Edit/Delete
     * buttons in the table
     ***************************************************/
	private void updateButtonsState() {
		if (getRowCount() == 0) {
			btnView.setDisable(true);
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);
		} else {
			btnView.setDisable(false);
			btnEdit.setDisable(false);
			btnDelete.setDisable(false);
		}

		// ako imamo praznu parent tabelu ili popunjenu
		if (getParentTable() != null && getParentTable().getRowCount() == 0) {
			btnAdd.setDisable(true);
		} else if (getParentTable() != null && getParentTable().getRowCount() > 0) {
			btnAdd.setDisable(false);
		}
		
		// Ako nemamo definisan delete service, disableujemo button, bez obzira na prava korisnika
		if ((!deleteRestServiceAllowedMissing && getRestServiceDelete() == null) || getRowCount() == 0) {
			btnDelete.setDisable(true);
		} else {
			btnDelete.setDisable(false);
		}
	}

	
	/***************************************************
     * Gets and sets rest service for data fetch
     * Currently used to get the service URL and to call excel/pdf export
     ***************************************************/
	public CSRestService<T> getRestService() {
		return restService;
	}

	public void setRestService(CSRestService<T> restService) {
		this.restService = restService;
	}
	
	/***************************************************
     * Gets and sets rest service for deleting
     * currently selected item
     ***************************************************/
	public CSRestService<T> getRestServiceDelete() {
		return rsDelete;
	}

	public void setRestServiceDelete(CSRestService<T> rsDelete) {
		this.rsDelete = rsDelete;
		updateButtonsState();
	}

	public Boolean getDragAndDropEnabled() {
		return dragAndDropEnabled;
	}

	/**
	 *  ako cemo da disableujemo, moze samo Boolean, ali ako hocemo da enableujemo, treba nam class koji dragdrop podrzava
	 * @param dragAndDropEnabled
	 */
	public void setDragAndDropEnabled(Boolean dragAndDropEnabled) {
		if (!dragAndDropEnabled) {
			this.dragAndDropEnabled = dragAndDropEnabled;
			/* NE RADI OVO, NE ZNAM ZASTO. MORAM DA POSTAVLJAM NULL
			tableView.removeEventHandler(MouseEvent.DRAG_DETECTED, tableView.getOnDragDetected());
			tableView.removeEventHandler(DragEvent.ANY, tableView.getOnDragExited());
			tableView.removeEventHandler(DragEvent.DRAG_DROPPED, tableView.getOnDragDropped());
			tableView.removeEventHandler(DragEvent.ANY, tableView.getOnDragDone());
			tableView.removeEventHandler(DragEvent.ANY, tableView.getOnDragOver());
			*/

			tableView.setOnDragDetected(null);
			tableView.setOnDragExited(null);
			tableView.setOnDragDropped(null);
			tableView.setOnDragDone(null);
			tableView.setOnDragOver(null);

		} else {
			throw new IllegalArgumentException("Drag and Drop operation cannot be enabled without provided supported class!");
		}
	}

	/**
	 *
	 * @param dragAndDropEnabled, Boolean
	 * @param classType, Class<T>, mandatory if dragAndDropEnabled is true. Specifies object type that this table accepts for drag and drop operation
	 */
	public void setDragAndDropEnabled(Boolean dragAndDropEnabled, Class<T> classType) {
		this.dragAndDropEnabled = dragAndDropEnabled;
		setOnDragDetected(classType);
	}

	public Boolean getDeleteRestServiceAllowedMissing() {
		return deleteRestServiceAllowedMissing;
	}

	public void setDeleteRestServiceAllowedMissing(Boolean deleteRestServiceAllowedMissing) {
		this.deleteRestServiceAllowedMissing = deleteRestServiceAllowedMissing;
		updateButtonsState();
	}
	
	
	/**
	 * Shortcut for table items refresh
	 */
	public void refresh() {
		this.tableView.refresh();
	}
	
	/**
	 * Gets list of user checked items with checkboxes. If checkbox is not visible, list is null.
	 * @return List<T>
	 */
	public List<T> getCheckedItems() {
		return lstCheckedItems;
	}
	
	public void showProgressIndicator(boolean show) {
		if (show) {
			tableView.setPlaceholder(progressIndicator);			
		} else {
			tableView.setPlaceholder(new Label(""));
		}
	}

	
	/**
	 * @get enhancedSearch property
	 * @return boolean
	 */
	public boolean getEnhancedSearch() {
		return enhancedSearch;
	}

	/**
	 * @set enhancedSearch property
	 * @param enhancedSearch
	 */
	public void setEnhancedSearch(boolean enhancedSearch) {
		this.enhancedSearch = enhancedSearch;
	}
	
	/**
	 * @return the tableRequestFocus
	 */
	public boolean getTableRequestFocus() {
		return tableRequestFocus;
	}

	/**
	 * @param tableRequestFocus the tableRequestFocus to set
	 */
	public void setTableRequestFocus(boolean tableRequestFocus) {
		this.tableRequestFocus = tableRequestFocus;
		tableView.requestFocus();
	}

	/**
	 * Selects first row in the table
	 */
	public void selectFirstRow() {
		tableView.getSelectionModel().selectFirst();
		tableView.scrollTo(0);
	}
	
	/**
	 * Selects last row in the table
	 */
	public void selectLastRow() {
		
		if (tableView.getItems().size() == 1) {
			selectFirstRow();
		} else {
			tableView.getSelectionModel().selectLast();
			tableView.scrollTo(tableView.getItems().size()-1);
		}
	}
	
	
	/**
	 * Implementation of setting CSS class to row or cell
	 * Sets highlighted row CSS based on conditional formatting.
	 * CSS classes are defined in CSTable.css
	 */
	private void setCssClass(TableCell<T, ?> cell, String className, Integer applyToRow) {
		for (String clsName : lstCssClass) {
			if (applyToRow == 0) {
				if (cell.getStyleClass().contains(clsName)) {
					cell.getStyleClass().remove(clsName);
				}
			} else {
				if (cell.getTableRow().getStyleClass().contains(clsName)) {
					cell.getTableRow().getStyleClass().remove(clsName);
				}
			}
		}
		
		if (className != null) {
			if (applyToRow == 0) {
				cell.getStyleClass().add(className);
			} else {
				cell.getTableRow().getStyleClass().add(className);				
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean getReverseItemsOrder() {
		return reverseItemsOrder;
	}

	/**
	 * 
	 * @param reverseItemsOrder
	 */
	public void setReverseItemsOrder(boolean reverseItemsOrder) {
		this.reverseItemsOrder = reverseItemsOrder;
	}
	
	
	private void dragDetectedController() {
		
	}
}

