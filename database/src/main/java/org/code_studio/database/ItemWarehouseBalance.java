package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@JsonIgnoreProperties({"hibernateLazyInitializer"})
@SuppressWarnings("serial")
@Entity
public class ItemWarehouseBalance implements java.io.Serializable {

	private Integer id;
	private String itemCode;
	private String itemName;
	private String rowId;
	private String shelfId;
	private String verticalId;
	private Integer availableQty;
	private Integer reservedQty;
	private Integer qty;

	public ItemWarehouseBalance() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getShelfId() {
		return shelfId;
	}

	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}

	public String getVerticalId() {
		return verticalId;
	}

	public void setVerticalId(String verticalId) {
		this.verticalId = verticalId;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public Integer getReservedQty() {
		return reservedQty;
	}

	public void setReservedQty(Integer reservedQty) {
		this.reservedQty = reservedQty;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}


}
