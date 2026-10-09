package org.code_studio.database;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "BILL_OF_RECEIPT_DETAIL", schema = "PUBLIC", catalog = "RM")
public class BillOfReceiptDetail implements java.io.Serializable {

	private Integer id;
	private Integer billOfReceiptId;
	private Item item;
	private BigDecimal quantity;
	private Warehouse receiveWarehouse;
	private String receiveRowId;
	private String receiveShelfId;
	private String receiveVerticalId;
	private String rowId;
	private String shelfId;
	private String verticalId;

	public BillOfReceiptDetail() {
	}

	public BillOfReceiptDetail(Integer billOfReceiptId, Item item, BigDecimal quantity, Warehouse receiveWarehouse,
			String receiveRowId, String receiveShelfId, String receiveVerticalId, String rowId, String shelfId,
			String verticalId) {
		this.billOfReceiptId = billOfReceiptId;
		this.item = item;
		this.quantity = quantity;
		this.receiveWarehouse = receiveWarehouse;
		this.receiveRowId = receiveRowId;
		this.receiveShelfId = receiveShelfId;
		this.receiveVerticalId = receiveVerticalId;
		this.rowId = rowId;
		this.shelfId = shelfId;
		this.verticalId = verticalId;
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

	@Column(name = "BILL_OF_RECEIPT_ID")
	public Integer getBillOfReceiptId() {
		return this.billOfReceiptId;
	}

	public void setBillOfReceiptId(Integer billOfReceiptId) {
		this.billOfReceiptId = billOfReceiptId;
	}

	//@Column(name = "ITEM_ID")
	@OneToOne(fetch = FetchType.LAZY)
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "QUANTITY", precision = 18)
	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@JoinColumn(name = "RECEIVE_WAREHOUSE_ID")
	@OneToOne(fetch = FetchType.LAZY)
	public Warehouse getReceiveWarehouse() {
		return this.receiveWarehouse;
	}

	public void setReceiveWarehouse(Warehouse receiveWarehouse) {
		this.receiveWarehouse = receiveWarehouse;
	}

	@Column(name = "RECEIVE_ROW_ID", length = 10)
	public String getReceiveRowId() {
		return this.receiveRowId;
	}

	public void setReceiveRowId(String receiveRowId) {
		this.receiveRowId = receiveRowId;
	}

	@Column(name = "RECEIVE_SHELF_ID", length = 10)
	public String getReceiveShelfId() {
		return this.receiveShelfId;
	}

	public void setReceiveShelfId(String receiveShelfId) {
		this.receiveShelfId = receiveShelfId;
	}

	@Column(name = "RECEIVE_VERTICAL_ID", length = 10)
	public String getReceiveVerticalId() {
		return this.receiveVerticalId;
	}

	public void setReceiveVerticalId(String receiveVerticalId) {
		this.receiveVerticalId = receiveVerticalId;
	}

	@Column(name = "ROW_ID", length = 10)
	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	@Column(name = "SHELF_ID", length = 10)
	public String getShelfId() {
		return this.shelfId;
	}

	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}

	@Column(name = "VERTICAL_ID", length = 10)
	public String getVerticalId() {
		return this.verticalId;
	}

	public void setVerticalId(String verticalId) {
		this.verticalId = verticalId;
	}
	
	//------------------------------------------
	@Transient
	public String getItemName() {
		return this.item == null ? "" : this.item.getName();
	}
	
	@Transient
	public String getWarehouseName() {
		return this.receiveWarehouse == null ? "" : this.receiveWarehouse.getName();
	}

}
