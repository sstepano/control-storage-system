package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class InventoryListingDetailSum implements java.io.Serializable {

	private Integer id;
	private Integer inventoryListingId;
	private Integer countingNbr;
	private Integer itemId;
	private Item item;
	private String rowId;
	private String shelfId;
	private String verticalId;
	private Integer qty;
	private Integer countedQty;
	private Integer differenceQty;

	public InventoryListingDetailSum() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInventoryListingId() {
		return inventoryListingId;
	}

	public void setInventoryListingId(Integer inventoryListingId) {
		this.inventoryListingId = inventoryListingId;
	}

	public Integer getCountingNbr() {
		return countingNbr;
	}

	public void setCountingNbr(Integer countingNbr) {
		this.countingNbr = countingNbr;
	}

	@Column(name = "ITEM_ID")
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@OneToOne
	@JoinColumn(name = "ITEM_ID", insertable = false, updatable = false)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getShelfId() {
		return this.shelfId;
	}

	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}

	public String getVerticalId() {
		return this.verticalId;
	}

	public void setVerticalId(String verticalId) {
		this.verticalId = verticalId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getCountedQty() {
		return this.countedQty;
	}

	public void setCountedQty(Integer countedQty) {
		this.countedQty = countedQty;
	}

	public Integer getDifferenceQty() {
		return differenceQty;
	}

	public void setDifferenceQty(Integer differenceQty) {
		this.differenceQty = differenceQty;
	}
	
	@Transient
	public String getItemName() {
		return item == null ? "" : item.getName();
	}
	
	@Transient
	public BigDecimal getItemPrice() {
		return item == null ? BigDecimal.ZERO : item.getNetPriceRmD();
	}

}
