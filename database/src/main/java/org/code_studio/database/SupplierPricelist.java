package org.code_studio.database;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "SUPPLIER_PRICELIST", schema = "PUBLIC", catalog = "RM")
public class SupplierPricelist implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private Integer itemId;
	private Item item;
	private String supplierItemCode;
	private BigDecimal purchaseAmt;
	private BigDecimal purchaseAmtSpec;
	private Integer minQty;
	private BigDecimal purchaseAmtPrevious;
	private BigDecimal purchaseAmtSpecPrevious;
	private Integer minQtyPrevious;
	private Integer oldRecNo;

	public SupplierPricelist() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CLIENT_ID")
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "ITEM_ID", insertable = false, updatable = false)
	public Integer getItemId() {
		return this.item != null ? this.getItem().getId().intValue() : itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@ManyToOne
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "SUPPLIER_ITEM_CODE", length = 100)
	public String getSupplierItemCode() {
		return this.supplierItemCode;
	}

	public void setSupplierItemCode(String supplierItemCode) {
		this.supplierItemCode = supplierItemCode;
	}

	@Column(name = "PURCHASE_AMT", precision = 18)
	public BigDecimal getPurchaseAmt() {
		return this.purchaseAmt;
	}

	public void setPurchaseAmt(BigDecimal purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}

	@Column(name = "PURCHASE_AMT_SPEC", precision = 18)
	public BigDecimal getPurchaseAmtSpec() {
		return this.purchaseAmtSpec;
	}

	public void setPurchaseAmtSpec(BigDecimal purchaseAmtSpec) {
		this.purchaseAmtSpec = purchaseAmtSpec;
	}

	@Column(name = "MIN_QTY")
	public Integer getMinQty() {
		return this.minQty;
	}

	public void setMinQty(Integer minQty) {
		this.minQty = minQty;
	}

	@Column(name = "PURCHASE_AMT_PREVIOUS", precision = 18)
	public BigDecimal getPurchaseAmtPrevious() {
		return this.purchaseAmtPrevious;
	}

	public void setPurchaseAmtPrevious(BigDecimal purchaseAmtPrevious) {
		this.purchaseAmtPrevious = purchaseAmtPrevious;
	}

	@Column(name = "PURCHASE_AMT_SPEC_PREVIOUS", precision = 18)
	public BigDecimal getPurchaseAmtSpecPrevious() {
		return this.purchaseAmtSpecPrevious;
	}

	public void setPurchaseAmtSpecPrevious(BigDecimal purchaseAmtSpecPrevious) {
		this.purchaseAmtSpecPrevious = purchaseAmtSpecPrevious;
	}

	@Column(name = "MIN_QTY_PREVIOUS")
	public Integer getMinQtyPrevious() {
		return this.minQtyPrevious;
	}

	public void setMinQtyPrevious(Integer minQtyPrevious) {
		this.minQtyPrevious = minQtyPrevious;
	}

	@Column(name = "_OLD_REC_NO")
	public Integer getOldRecNo() {
		return this.oldRecNo;
	}

	public void setOldRecNo(Integer oldRecNo) {
		this.oldRecNo = oldRecNo;
	}
	
	//-------------------------------------------
	@Transient
	public String getItemName() {
		return item != null ? item.getName() : "";
	}

}
