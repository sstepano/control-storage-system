package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@JsonIgnoreProperties({"hibernateLazyInitializer"})
@SuppressWarnings("serial")
@Entity
public class ItemBalance implements java.io.Serializable {

	private Integer id;
	private Integer itemId;
	private String typeCode;
	private LocalDateTime transferOrderDate;
	private Integer inQty;
	private Integer outQty;
	private Integer warehouseIdOrigin;
	private Integer warehouseIdDestination;

	public ItemBalance() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public LocalDateTime getTransferOrderDate() {
		return transferOrderDate;
	}

	public void setTransferOrderDate(LocalDateTime transferOrderDate) {
		this.transferOrderDate = transferOrderDate;
	}

	public Integer getInQty() {
		return inQty;
	}

	public void setInQty(Integer inQty) {
		this.inQty = inQty;
	}

	public Integer getOutQty() {
		return outQty;
	}

	public void setOutQty(Integer outQty) {
		this.outQty = outQty;
	}

	public Integer getWarehouseIdOrigin() {
		return warehouseIdOrigin;
	}

	public void setWarehouseIdOrigin(Integer warehouseIdOrigin) {
		this.warehouseIdOrigin = warehouseIdOrigin;
	}

	public Integer getWarehouseIdDestination() {
		return warehouseIdDestination;
	}

	public void setWarehouseIdDestination(Integer warehouseIdDestination) {
		this.warehouseIdDestination = warehouseIdDestination;
	}

}
