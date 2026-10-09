package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_WAREHOUSE", schema = "PUBLIC", catalog = "RM")
public class ItemWarehouse implements java.io.Serializable {

	private Integer id;
	private Integer warehouseId;
	private Integer itemId;
	private Integer qty;
	private Integer reservedQty;
	private Integer soldQty;
	private Integer minForSaleQty;
	private Integer maxForSaleQty;
	private String  rowId;
	private String  shelfId;
	private String  verticalId;
	private Boolean isActive;

	private Item item;
	private Warehouse warehouse;

	public ItemWarehouse() {}

	public ItemWarehouse(Integer warehouseId, Integer itemId, Integer qty, Integer reservedQty, Integer soldQty,
			Integer minForSaleQty, Integer maxForSaleQty, String rowId, String shelfId, String verticalId,
			Boolean isActive) {
		this.warehouseId = warehouseId;
		this.itemId = itemId;
		this.qty = qty;
		this.reservedQty = reservedQty;
		this.soldQty = soldQty;
		this.minForSaleQty = minForSaleQty;
		this.maxForSaleQty = maxForSaleQty;
		this.rowId = rowId;
		this.shelfId = shelfId;
		this.verticalId = verticalId;
		this.isActive = isActive;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "WAREHOUSE_ID")
	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="WAREHOUSE_ID", insertable=false, updatable=false)
	public Warehouse getWarehouse() {
		return this.warehouse;
	}
	
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	//@Transient
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ITEM_ID", insertable=false, updatable=false)
	public Item getItem() {
		return this.item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	@Column(name = "ITEM_ID")
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	@Column(name = "QTY")
	public Integer getQty() {
		return this.qty == null ? 0 : this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Column(name = "RESERVED_QTY")
	public Integer getReservedQty() {
		return this.reservedQty;
	}

	public void setReservedQty(Integer reservedQty) {
		this.reservedQty = reservedQty;
	}

	@Column(name = "SOLD_QTY")
	public Integer getSoldQty() {
		return this.soldQty;
	}

	public void setSoldQty(Integer soldQty) {
		this.soldQty = soldQty;
	}

	@Column(name = "MIN_FOR_SALE_QTY")
	public Integer getMinForSaleQty() {
		return this.minForSaleQty;
	}

	public void setMinForSaleQty(Integer minForSaleQty) {
		this.minForSaleQty = minForSaleQty;
	}

	@Column(name = "MAX_FOR_SALE_QTY")
	public Integer getMaxForSaleQty() {
		return this.maxForSaleQty;
	}

	public void setMaxForSaleQty(Integer maxForSaleQty) {
		this.maxForSaleQty = maxForSaleQty;
	}

	@Column(name = "ROW_ID", length = 10)
	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		if (rowId!= null  && rowId.length() != 0) {
			this.rowId = rowId;
		}
		else {
			this.rowId = null;
		}
	}

	@Column(name = "SHELF_ID", length = 10)
	public String getShelfId() {
		return this.shelfId;
	}

	public void setShelfId(String shelfId) {
		if (shelfId != null && shelfId.length() != 0) {
			this.shelfId = shelfId;
		}
		else {
			this.shelfId = null;
		}
	}

	@Column(name = "VERTICAL_ID", length = 10)
	public String getVerticalId() {
		return this.verticalId;
	}

	public void setVerticalId(String verticalId) {
		if (verticalId != null && verticalId.length() != 0) {
			this.verticalId = verticalId;
		}
		else {
			this.verticalId = null;
		}
	}

	@Column(name = "IS_ACTIVE")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	//--------------------------------------------
	@Transient
	public String getWarehouseName() {
		return this.warehouse == null ? "" : this.warehouse.getName(); 
	}
	
	@Transient
	public Integer getAvailableQty() {
		return (this.qty == null ? 0 : this.qty) - (this.reservedQty == null ? 0 : this.reservedQty); 
	}
	
	@Transient
	public String getItemName() {
		return this.item == null 
			? ""
			: item.getName();
	}

}
