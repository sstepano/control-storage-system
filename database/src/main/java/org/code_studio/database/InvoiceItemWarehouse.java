package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Id;
@SuppressWarnings("serial")
@Entity
public class InvoiceItemWarehouse implements java.io.Serializable {

	private Integer id;
	private Integer itemId;
	private Integer fromWarehouseId;
	private Integer toWarehouseId;
	private Integer qty;

	public InvoiceItemWarehouse() {}

	public InvoiceItemWarehouse(Integer itemId, Integer fromWarehouseId, Integer toWarehouseId, Integer qty) {
		super();
		this.itemId = itemId;
		this.fromWarehouseId = fromWarehouseId;
		this.toWarehouseId = toWarehouseId;
		this.qty = qty;
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

	@Column(name = "ITEM_ID")
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "FROM_WAREHOUSE_ID")
	public Integer getFromWarehouseId() {
		return fromWarehouseId;
	}

	public void setFromWarehouseId(Integer fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}

	@Column(name = "TO_WAREHOUSE_ID")
	public Integer getToWarehouseId() {
		return toWarehouseId;
	}

	public void setToWarehouseId(Integer toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}

	@Column(name = "QTY")
	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

}
