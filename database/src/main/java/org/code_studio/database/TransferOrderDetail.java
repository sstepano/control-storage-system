package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.Comparator;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "TRANSFER_ORDER_DETAIL", schema = "PUBLIC", catalog = "RM")
public class TransferOrderDetail implements java.io.Serializable {

	private Integer id;
	private Integer transferOrderId;
	//private Integer itemId;
	private Integer invoiceDetailId;
	private Item item;
	private Integer qty;
	private Integer warehouseIdOrigin;
	private String rowIdOrigin;
	private String shelfIdOrigin;
	private String verticalIdOrigin;
	private Integer warehouseIdDestination;
	private String rowIdDestination;
	private String shelfIdDestination;
	private String verticalIdDestination;
	private Integer issuedQty;
	
	private ItemWarehouse itemWarehouseOrigin;

	public TransferOrderDetail() {
	}

	public TransferOrderDetail(Integer transferOrderId, Item item, Integer qty) {
		this.transferOrderId = transferOrderId;
		this.item = item;
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

	@Column(name = "TRANSFER_ORDER_ID")
	public Integer getTransferOrderId() {
		return this.transferOrderId;
	}

	public void setTransferOrderId(Integer transferOrderId) {
		this.transferOrderId = transferOrderId;
	}

	@Column(name = "ITEM_ID", insertable = false, updatable = false)
	public Integer getItemId() {
		return this.item == null 
			? 0
			: this.item.getId().intValue();
	}

	public void setItemId(Integer itemId) {
		this.item.setId(itemId);
	}
	
	@Column(name = "INVOICE_DETAIL_ID")
	public Integer getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(Integer invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Item getItem() {
		return this.item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "QTY")
	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	@Column(name = "WAREHOUSE_ID_ORIGIN")
	public Integer getWarehouseIdOrigin() {
		return warehouseIdOrigin;
	}

	public void setWarehouseIdOrigin(Integer warehouseIdOrigin) {
		this.warehouseIdOrigin = warehouseIdOrigin;
	}

	@Column(name = "ROW_ID_ORIGIN")
	public String getRowIdOrigin() {
		return rowIdOrigin;
	}

	public void setRowIdOrigin(String rowIdOrigin) {
		this.rowIdOrigin = rowIdOrigin;
	}

	@Column(name = "SHELF_ID_ORIGIN")
	public String getShelfIdOrigin() {
		return shelfIdOrigin;
	}

	public void setShelfIdOrigin(String shelfIdOrigin) {
		this.shelfIdOrigin = shelfIdOrigin;
	}

	@Column(name = "VERTICAL_ID_ORIGIN")
	public String getVerticalIdOrigin() {
		return verticalIdOrigin;
	}

	public void setVerticalIdOrigin(String verticalIdOrigin) {
		this.verticalIdOrigin = verticalIdOrigin;
	}

	@Column(name = "WAREHOUSE_ID_DESTINATION")
	public Integer getWarehouseIdDestination() {
		return warehouseIdDestination;
	}

	public void setWarehouseIdDestination(Integer warehouseIdDestination) {
		this.warehouseIdDestination = warehouseIdDestination;
	}

	@Column(name = "ROW_ID_DESTINATION")
	public String getRowIdDestination() {
		return rowIdDestination;
	}

	public void setRowIdDestination(String rowIdDestination) {
		this.rowIdDestination = rowIdDestination;
	}

	@Column(name = "SHELF_ID_DESTINATION")
	public String getShelfIdDestination() {
		return shelfIdDestination;
	}

	public void setShelfIdDestination(String shelfIdDestination) {
		this.shelfIdDestination = shelfIdDestination;
	}

	@Column(name = "VERTICAL_ID_DESTINATION")
	public String getVerticalIdDestination() {
		return verticalIdDestination;
	}

	public void setVerticalIdDestination(String verticalIdDestination) {
		this.verticalIdDestination = verticalIdDestination;
	}

	@Column(name = "ISSUED_QTY")
	public Integer getIssuedQty() {
		return issuedQty == null ? 0 : issuedQty;
	}

	public void setIssuedQty(Integer issuedQty) {
		this.issuedQty = issuedQty;
	}
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(updatable=false, insertable=false, name = "WAREHOUSE_ID_ORIGIN", referencedColumnName = "WAREHOUSE_ID"),
		@JoinColumn(updatable=false, insertable=false, name = "ITEM_ID", referencedColumnName = "ITEM_ID")
	})
	public ItemWarehouse getItemWarehouseOrigin() {
		return itemWarehouseOrigin;
	}

	public void setItemWarehouseOrigin(ItemWarehouse itemWarehouseOrigin) {
		this.itemWarehouseOrigin = itemWarehouseOrigin;
	}

	//--------------------------------------
	/*
	@Transient
	public Integer getItemId() {
		return this.item == null 
				? 0
				: this.item.getId().intValue();
	}
	*/
	
	@Transient
	public String getItemCode() {
		return this.item == null 
			? "" 
			: this.item.getCode();
	}
	
	@Transient
	public String getItemName() {
		return this.item == null 
			? "" 
			: this.item.getName();
	}
	
	@Transient
	public String getBarcode() {
		String res = "";
		
		if (this.item == null || this.item.getItemBarcode() == null || this.item.getItemBarcode().size() == 0) {  
			res = "";
		} else {
			this.item.getItemBarcode().sort(Comparator.comparing(ItemBarcode::getBarcode));
			res = this.item.getItemBarcode().getFirst().getBarcode();
		}
		return res;
	}
	
	@Transient
	public Integer getQtyDifference () {
		return getIssuedQty() - getQty();
	}

}
