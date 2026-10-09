package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "invoice_detail", catalog = "rm")
public class InvoiceDetail implements java.io.Serializable {

	private Integer id;
	private Integer invoiceId;
	private String priceType;
	private Integer itemId;
	private Integer itemQty;
	private BigDecimal unitOfMeasureNetValue;
	private Integer discountRate;
	private BigDecimal amountDiscount;
	private Integer vatRate;
	private BigDecimal amountVat;
	private BigDecimal amountGross;
	private BigDecimal amountNet;
	private Integer warehouseId;
	private Integer createdBy;
	private LocalDateTime lastModifiedDate;
	private Integer oldIdkifa;
	/*************************
	 * CUSTOM ADDED PROPERTIES
	 ************************/
	private Item item;


	public InvoiceDetail() {}

	public InvoiceDetail(Integer invoiceId, String priceType, Integer itemId, Integer itemQty,
			BigDecimal unitOfMeasureNetValue, Integer discountRate, BigDecimal amountDiscount, Integer vatRate,
			BigDecimal amountVat, BigDecimal amountGross, BigDecimal amountNet, Integer warehouseId, Integer createdBy,
			LocalDateTime lastModifiedDate) {
		this.invoiceId = invoiceId;
		this.priceType = priceType;
		this.itemId = itemId;
		this.itemQty = itemQty;
		this.unitOfMeasureNetValue = unitOfMeasureNetValue;
		this.discountRate = discountRate;
		this.amountDiscount = amountDiscount;
		this.vatRate = vatRate;
		this.amountVat = amountVat;
		this.amountGross = amountGross;
		this.amountNet = amountNet;
		this.warehouseId = warehouseId;
		this.createdBy = createdBy;
		this.lastModifiedDate = lastModifiedDate;
		//this.oldIdkifa = oldIdkifa;
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

	@Column(name = "INVOICE_ID")
	public Integer getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "PRICE_TYPE", length = 2)
	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@Column(name = "ITEM_ID")
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "ITEM_QTY")
	public Integer getItemQty() {
		return this.itemQty;
	}

	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}

	@Column(name = "UNIT_OF_MEASURE_NET_VALUE", precision = 18)
	public BigDecimal getUnitOfMeasureNetValue() {
		return this.unitOfMeasureNetValue;
	}

	public void setUnitOfMeasureNetValue(BigDecimal unitOfMeasureNetValue) {
		this.unitOfMeasureNetValue = unitOfMeasureNetValue;
	}

	@Column(name = "DISCOUNT_RATE")
	public Integer getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name = "AMOUNT_DISCOUNT", precision = 18)
	public BigDecimal getAmountDiscount() {
		return this.amountDiscount;
	}

	public void setAmountDiscount(BigDecimal amountDiscount) {
		this.amountDiscount = amountDiscount;
	}

	@Column(name = "VAT_RATE")
	public Integer getVatRate() {
		return this.vatRate;
	}

	public void setVatRate(Integer vatRate) {
		this.vatRate = vatRate;
	}

	@Column(name = "AMOUNT_VAT", precision = 18)
	public BigDecimal getAmountVat() {
		return this.amountVat;
	}

	public void setAmountVat(BigDecimal amountVat) {
		this.amountVat = amountVat;
	}

	@Column(name = "AMOUNT_GROSS", precision = 18)
	public BigDecimal getAmountGross() {
		return this.amountGross;
	}

	public void setAmountGross(BigDecimal amountGross) {
		this.amountGross = amountGross;
	}

	@Column(name = "AMOUNT_NET", precision = 18)
	public BigDecimal getAmountNet() {
		return this.amountNet;
	}

	public void setAmountNet(BigDecimal amountNet) {
		this.amountNet = amountNet;
	}

	@Column(name = "WAREHOUSE_ID")
	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Column(name = "CREATED_BY")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "LAST_MODIFIED_DATE", length = 19)
	public LocalDateTime getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Column(name = "_OLD_IDKIFA")
	public Integer getOldIdkifa() {
		return this.oldIdkifa;
	}

	public void setOldIdkifa(Integer oldIdkifa) {
		this.oldIdkifa = oldIdkifa;
	}

	// -------------------------------------------
	@OneToOne
	@JoinColumn(name = "ITEM_ID", insertable = false, updatable = false)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Transient
	public String getItemCode() {
		return item != null ? item.getCode() : "";
	}
	
	@Transient
	public String getItemName() {
		return item != null ? item.getName() : "";
	}

}
