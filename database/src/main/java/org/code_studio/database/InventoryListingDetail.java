package org.code_studio.database;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "inventory_listing_detail", catalog = "rm")
public class InventoryListingDetail implements java.io.Serializable {

	private Integer id;
	private int inventoryListingId;
	private InventoryListing inventoryListing;
	private int itemId;
	private Item item;
	private String rowId;
	private String shelfId;
	private String verticalId;
	private Integer countedQty;
	private boolean isAccepted;
	private int createdByUserId;
	private LocalDateTime createdDate;

	public InventoryListingDetail() {}

	public InventoryListingDetail(InventoryListing inventoryListing, int itemId, int createdByUserId, LocalDateTime createdDate) {
		this.inventoryListing = inventoryListing;
		this.itemId = itemId;
		this.createdByUserId = createdByUserId;
		this.createdDate = createdDate;
	}

	public InventoryListingDetail(InventoryListing inventoryListing, int itemId, String rowId, String shelfId, String verticalId, Integer countedQty, boolean isAccepted, int createdByUserId, LocalDateTime createdDate) {
		this.inventoryListing = inventoryListing;
		this.itemId = itemId;
		this.rowId = rowId;
		this.shelfId = shelfId;
		this.verticalId = verticalId;
		this.countedQty = countedQty;
		this.setAccepted(isAccepted);
		this.createdByUserId = createdByUserId;
		this.createdDate = createdDate;
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

	@Column(name = "INVENTORY_LISTING_ID", nullable = false, insertable = false, updatable = false)
	public int getInventoryListingId() {
		return inventoryListingId;
	}

	public void setInventoryListingId(int inventoryListingId) {
		this.inventoryListingId = inventoryListingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVENTORY_LISTING_ID", nullable = false)
	public InventoryListing getInventoryListing() {
		return this.inventoryListing;
	}

	public void setInventoryListing(InventoryListing inventoryListing) {
		this.inventoryListing = inventoryListing;
	}

	@Column(name = "ITEM_ID", nullable = false)
	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
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

	@Column(name = "ROW_ID")
	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	@Column(name = "SHELF_ID")
	public String getShelfId() {
		return this.shelfId;
	}

	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}

	@Column(name = "VERTICAL_ID")
	public String getVerticalId() {
		return this.verticalId;
	}

	public void setVerticalId(String verticalId) {
		this.verticalId = verticalId;
	}

	@Column(name = "COUNTED_QTY")
	public Integer getCountedQty() {
		return this.countedQty;
	}

	public void setCountedQty(Integer countedQty) {
		this.countedQty = countedQty;
	}

	@Column(name = "IS_ACCEPTED")
	public boolean getAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	@Column(name = "CREATED_BY_USER_ID", nullable = false)
	public int getCreatedByUserId() {
		return this.createdByUserId;
	}

	public void setCreatedByUserId(int createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	public LocalDateTime getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	@Transient
	public Integer getWarehouseId () {
		return inventoryListing == null ? 0 : inventoryListing.getWarehouseId();
	}
	
	@Transient
	public String getWarehouseName () {
		return inventoryListing == null ? "" : inventoryListing.getWarehouseName();
	}
	
	@Transient
	public String getEnumeratorUsername () {
		return inventoryListing == null ? "" : inventoryListing.getEnumeratorUsername();
	}

	@Transient
	public String getItemName() {
		return item == null ? "" : item.getName();
	}
	
	@Transient
	public String getItemBarcode () {
		if (item == null || item.getItemBarcode().isEmpty()) {
			return "";
		} else return item.getItemBarcode().get(0).getBarcode();
	}
	
	
	@Transient
	public int getCountingNbr() {
		return inventoryListing == null ? 0 : inventoryListing.getCountingNbr();
	}

}
