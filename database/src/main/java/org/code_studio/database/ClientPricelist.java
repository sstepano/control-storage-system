package org.code_studio.database;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "CLIENT_PRICELIST", schema = "PUBLIC", catalog = "RM")
public class ClientPricelist implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private ClientSimple client;
	private Integer itemId;
	private Item item;
	private BigDecimal qty;
	private BigDecimal measureUnitNetAmt;
	private BigDecimal discountRate;
	private BigDecimal discountAmt;
	private BigDecimal measureUnitBaseVatAmt;
	private BigDecimal measureUnitVatAmt;
	private BigDecimal measureUnitGrossAmt;
	private BigDecimal discountRatePct;
	private Boolean isActive;
	private Boolean isDelivered;
	private Boolean isNew;
	private Integer offerId;
	private BigDecimal deliverQty;
	private Date createdDate;
	private Integer createdBy;
	private Integer oldid;
	private ItemClient itemClient;

	public ClientPricelist() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CLIENT_ID", insertable = false, updatable = false)
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	
	@ManyToOne
	public ClientSimple getClient() {
		return client;
	}

	public void setClient(ClientSimple client) {
		this.client = client;
	}

	@Column(name = "ITEM_ID", insertable = false, updatable = false)
	public Integer getItemId() {
		return this.itemId;
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

	@Column(name = "QTY", precision = 18)
	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	@Column(name = "MEASURE_UNIT_NET_AMT", precision = 18)
	public BigDecimal getMeasureUnitNetAmt() {
		return this.measureUnitNetAmt;
	}

	public void setMeasureUnitNetAmt(BigDecimal measureUnitNetAmt) {
		this.measureUnitNetAmt = measureUnitNetAmt;
	}

	@Column(name = "DISCOUNT_RATE", precision = 18)
	public BigDecimal getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name = "DISCOUNT_AMT", precision = 18)
	public BigDecimal getDiscountAmt() {
		return this.discountAmt;
	}

	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
	}

	@Column(name = "MEASURE_UNIT_BASE_VAT_AMT", precision = 18)
	public BigDecimal getMeasureUnitBaseVatAmt() {
		return this.measureUnitBaseVatAmt;
	}

	public void setMeasureUnitBaseVatAmt(BigDecimal measureUnitBaseVatAmt) {
		this.measureUnitBaseVatAmt = measureUnitBaseVatAmt;
	}

	@Column(name = "MEASURE_UNIT_VAT_AMT", precision = 18)
	public BigDecimal getMeasureUnitVatAmt() {
		return this.measureUnitVatAmt;
	}

	public void setMeasureUnitVatAmt(BigDecimal measureUnitVatAmt) {
		this.measureUnitVatAmt = measureUnitVatAmt;
	}

	@Column(name = "MEASURE_UNIT_GROSS_AMT", precision = 18)
	public BigDecimal getMeasureUnitGrossAmt() {
		return this.measureUnitGrossAmt;
	}

	public void setMeasureUnitGrossAmt(BigDecimal measureUnitGrossAmt) {
		this.measureUnitGrossAmt = measureUnitGrossAmt;
	}

	@Column(name = "DISCOUNT_RATE_PCT", precision = 18)
	public BigDecimal getDiscountRatePct() {
		return this.discountRatePct;
	}

	public void setDiscountRatePct(BigDecimal discountRatePct) {
		this.discountRatePct = discountRatePct;
	}

	@Column(name = "IS_ACTIVE")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "IS_DELIVERED")
	public Boolean getIsDelivered() {
		return this.isDelivered;
	}

	public void setIsDelivered(Boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	@Column(name = "IS_NEW")
	public Boolean getIsNew() {
		return this.isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	@Column(name = "OFFER_ID")
	public Integer getOfferId() {
		return this.offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	@Column(name = "DELIVER_QTY", precision = 18)
	public BigDecimal getDeliverQty() {
		return this.deliverQty;
	}

	public void setDeliverQty(BigDecimal deliverQty) {
		this.deliverQty = deliverQty;
	}

	@Column(name = "CREATED_DATE", length = 26)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "_OLDID")
	public Integer getOldid() {
		return this.oldid;
	}

	public void setOldid(Integer oldid) {
		this.oldid = oldid;
	}
	
	@OneToOne()
	@JoinColumns({
		@JoinColumn(updatable=false, insertable=false, name = "CLIENT_ID", referencedColumnName = "CLIENT_ID"),
		@JoinColumn(updatable=false, insertable=false, name = "ITEM_ID", referencedColumnName = "ITEM_ID")
	})
	public ItemClient getItemClient() {
		return itemClient;
	}

	public void setItemClient(ItemClient itemClient) {
		this.itemClient = itemClient;
	}

	// ------------------------------------------
	@Transient
	public String getItemName() {
		return item == null ? "" : item.getName();
	}
	
	@Transient
	public String getItemNameEng() {
		return item == null ? "" : item.getNameEng();
	}
	
	@Transient
	public String getClientName() {
		return client == null ? "" : client.getName();
	}
	
	@Transient
	public String getClientDOMCode() {
		return itemClient == null ? "" : itemClient.getItemIdClient();
	}
}
