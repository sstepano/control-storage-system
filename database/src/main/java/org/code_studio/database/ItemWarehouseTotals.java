package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@JsonIgnoreProperties({"hibernateLazyInitializer"})
@SuppressWarnings("serial")
@Entity
public class ItemWarehouseTotals implements java.io.Serializable {

	private Integer id;
	private Integer warehouseId;
	private Integer itemCnt;
	private Integer availableQty;
	private Integer reservedQty;
	private Integer qty;

	public ItemWarehouseTotals() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getItemCnt() {
		return itemCnt;
	}

	public void setItemCnt(Integer itemCnt) {
		this.itemCnt = itemCnt;
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
