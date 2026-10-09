package org.code_studio.database;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "invoice", catalog = "rm")
public class Invoice implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private String invoiceType;
	private String reservationType;
	private String priceType;
	private Integer invoiceNumber;
	private LocalDate invoiceDate;
	private LocalDate invoiceValueDate;
	private Integer proinvoiceNumber;
	private LocalDate proinvoiceDate;
	private LocalDate proinvoiceValueDate;
	private Integer dispatchNoteType;
	private Integer dispatchNoteId;
	private LocalDate dispatchNoteDate;
	private BigDecimal discountAmt;
	private BigDecimal discountRate;
	private Integer vatRate;
	private BigDecimal vatAmt;
	private BigDecimal netAmt;
	private BigDecimal amountGross;
	private BigDecimal totalAmtWithoutDiscount;
	private BigDecimal totalAmount;
	private BigDecimal advancePaymentAmt;
	private Integer deliveryDays;
	private LocalDate deliveryDate;
	private Integer warehouseId;
	private String deliveryAddressMail;
	private String deliveryAddressGoods;
	private String clientStoreName;
	private String clientStoreAddress;
	private Integer createdBy;
	private LocalDateTime createdDate;
	private Integer verifiedBy;
	private LocalDateTime verifiedDate;
	private LocalDateTime lastModifiedDate;
	private String description;
	// -----------------------
	private List<InvoiceDetail> invoiceDetail;
	private ApplicationUserName userCreatedBy;
	private ApplicationUserName userVerifiedBy;
	private ClientSimple client;

	public Invoice() {}

	public Invoice(Integer clientId, String invoiceType, String reservationType, String priceType,
			Integer invoiceNumber, LocalDate invoiceDate, LocalDate invoiceValueDate, Integer proinvoiceNumber,
			LocalDate proinvoiceDate, LocalDate proinvoiceValueDate, Integer dispatchNoteType, Integer dispatchNoteId,
			LocalDate dispatchNoteDate, BigDecimal discountAmt, BigDecimal discountRate, BigDecimal amountGross, BigDecimal totalAmount,
			Integer deliveryDays, LocalDate deliveryDate, Integer warehouseId, Integer createdBy, LocalDateTime lastModifiedDate,
			String description) {
		this.clientId = clientId;
		this.invoiceType = invoiceType;
		this.reservationType = reservationType;
		this.priceType = priceType;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.invoiceValueDate = invoiceValueDate;
		this.proinvoiceNumber = proinvoiceNumber;
		this.proinvoiceDate = proinvoiceDate;
		this.proinvoiceValueDate = proinvoiceValueDate;
		this.dispatchNoteType = dispatchNoteType;
		this.dispatchNoteId = dispatchNoteId;
		this.dispatchNoteDate = dispatchNoteDate;
		this.discountAmt = discountAmt;
		this.discountRate = discountRate;
		this.amountGross = amountGross;
		this.totalAmount = totalAmount;
		this.deliveryDays = deliveryDays;
		this.deliveryDate = deliveryDate;
		this.warehouseId = warehouseId;
		this.createdBy = createdBy;
		this.createdDate = LocalDateTime.now();
		this.lastModifiedDate = lastModifiedDate;
		this.description = description;
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

	@Column(name = "CLIENT_ID")
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@OneToOne
	@JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false)
	public ClientSimple getClient() {
		return client;
	}

	public void setClient(ClientSimple client) {
		this.client = client;
	}

	@Column(name = "INVOICE_TYPE", length = 2)
	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name = "RESERVATION_TYPE", length = 2)
	public String getReservationType() {
		return this.reservationType;
	}

	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}

	@Column(name = "PRICE_TYPE", length = 2)
	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@Column(name = "INVOICE_NUMBER")
	public Integer getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Column(name = "INVOICE_DATE", length = 10)
	public LocalDate getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Column(name = "INVOICE_VALUE_DATE", length = 10)
	public LocalDate getInvoiceValueDate() {
		return this.invoiceValueDate;
	}

	public void setInvoiceValueDate(LocalDate invoiceValueDate) {
		this.invoiceValueDate = invoiceValueDate;
	}

	@Column(name = "PROINVOICE_NUMBER")
	public Integer getProinvoiceNumber() {
		return this.proinvoiceNumber;
	}

	public void setProinvoiceNumber(Integer proinvoiceNumber) {
		this.proinvoiceNumber = proinvoiceNumber;
	}

	@Column(name = "PROINVOICE_DATE", length = 10)
	public LocalDate getProinvoiceDate() {
		return this.proinvoiceDate;
	}

	public void setProinvoiceDate(LocalDate proinvoiceDate) {
		this.proinvoiceDate = proinvoiceDate;
	}

	@Column(name = "PROINVOICE_VALUE_DATE", length = 10)
	public LocalDate getProinvoiceValueDate() {
		return this.proinvoiceValueDate;
	}

	public void setProinvoiceValueDate(LocalDate proinvoiceValueDate) {
		this.proinvoiceValueDate = proinvoiceValueDate;
	}

	@Column(name = "DISPATCH_NOTE_TYPE")
	public Integer getDispatchNoteType() {
		return this.dispatchNoteType;
	}

	public void setDispatchNoteType(Integer dispatchNoteType) {
		this.dispatchNoteType = dispatchNoteType;
	}

	@Column(name = "DISPATCH_NOTE_ID")
	public Integer getDispatchNoteId() {
		return this.dispatchNoteId;
	}

	public void setDispatchNoteId(Integer dispatchNoteId) {
		this.dispatchNoteId = dispatchNoteId;
	}

	@Column(name = "DISPATCH_NOTE_DATE", length = 10)
	public LocalDate getDispatchNoteDate() {
		return this.dispatchNoteDate;
	}

	public void setDispatchNoteDate(LocalDate dispatchNoteDate) {
		this.dispatchNoteDate = dispatchNoteDate;
	}

	@Column(name = "DISCOUNT_AMT", precision = 18)
	public BigDecimal getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
	}

	@Column(name = "DISCOUNT_RATE", precision = 18)
	public BigDecimal getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name = "VAT_RATE")
	public Integer getVatRate() {
		return vatRate;
	}

	public void setVatRate(Integer vatRate) {
		this.vatRate = vatRate;
	}

	@Column(name = "VAT_AMT", precision = 18)
	public BigDecimal getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(BigDecimal vatAmt) {
		this.vatAmt = vatAmt;
	}

	@Column(name = "NET_AMT", precision = 18)
	public BigDecimal getNetAmt() {
		return netAmt;
	}

	public void setNetAmt(BigDecimal netAmt) {
		this.netAmt = netAmt;
	}

	@Column(name = "AMOUNT_GROSS", precision = 18)
	public BigDecimal getAmountGross() {
		return this.amountGross;
	}

	public void setAmountGross(BigDecimal amountGross) {
		this.amountGross = amountGross;
	}

	@Column(name = "TOTAL_AMT_WITHOUT_DISCOUNT", precision = 18)
	public BigDecimal getTotalAmtWithoutDiscount() {
		return totalAmtWithoutDiscount;
	}

	public void setTotalAmtWithoutDiscount(BigDecimal totalAmtWithoutDiscount) {
		this.totalAmtWithoutDiscount = totalAmtWithoutDiscount;
	}

	@Deprecated
	@Column(name = "TOTAL_AMOUNT", precision = 18)
	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	@Deprecated
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "ADVANCE_PAYMENT_AMT", precision = 18)
	public BigDecimal getAdvancePaymentAmt() {
		return advancePaymentAmt;
	}

	public void setAdvancePaymentAmt(BigDecimal advancePaymentAmt) {
		this.advancePaymentAmt = advancePaymentAmt;
	}

	@Column(name = "DELIVERY_DAYS")
	public Integer getDeliveryDays() {
		return this.deliveryDays;
	}

	public void setDeliveryDays(Integer deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	@Column(name = "DELIVERY_DATE", length = 10)
	public LocalDate getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Column(name = "WAREHOUSE_ID")
	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Column(name = "DELIVERY_ADDRESS_MAIL")
	public String getDeliveryAddressMail() {
		return deliveryAddressMail;
	}

	public void setDeliveryAddressMail(String deliveryAddressMail) {
		this.deliveryAddressMail = deliveryAddressMail;
	}

	@Column(name = "DELIVERY_ADDRESS_GOODS")
	public String getDeliveryAddressGoods() {
		return deliveryAddressGoods;
	}

	public void setDeliveryAddressGoods(String deliveryAddressGoods) {
		this.deliveryAddressGoods = deliveryAddressGoods;
	}

	@Column(name = "CLIENT_STORE_NAME")
	public String getClientStoreName() {
		return clientStoreName;
	}

	public void setClientStoreName(String clientStoreName) {
		this.clientStoreName = clientStoreName;
	}

	@Column(name = "CLIENT_STORE_ADDRESS")
	public String getClientStoreAddress() {
		return clientStoreAddress;
	}

	public void setClientStoreAddress(String clientStoreAddress) {
		this.clientStoreAddress = clientStoreAddress;
	}

	@Column(name = "CREATED_BY")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE", length = 19)
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate() {
		this.createdDate = LocalDateTime.now();
	}
	
	// This one is needed because server cannot start without it. It says cannot find setter for CreatedDate :)
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "VERIFIED_BY")
	public Integer getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(Integer verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	@Column(name = "VERIFIED_DATE", length = 19)
	public LocalDateTime getVerifiedDate() {
		return verifiedDate;
	}

	public void setVerifiedDate() {
		this.verifiedDate = LocalDateTime.now();;
	}
	
	// This one is needed because server cannot start without it. It says cannot find setter for verifiedDate :)
	public void setVerifiedDate(LocalDateTime verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	@Column(name = "LAST_MODIFIED_DATE", length = 19)
	public LocalDateTime getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate() {
		this.lastModifiedDate = LocalDateTime.now();;
	}
	
	// This one is needed because server cannot start without it. It says cannot find setter for lastModifiedDate :)
	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//------------------------------------
	@OneToOne
	@JoinColumn(name = "CREATED_BY", insertable = false, updatable = false)
	public ApplicationUserName getUserCreatedBy() {
		return userCreatedBy;
	}

	public void setUserCreatedBy(ApplicationUserName userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
	}
	
	@Transient
	public String getCreatedByName() {
		return userCreatedBy != null ? userCreatedBy.getName() : "";
	}
	
	@OneToOne
	@JoinColumn(name = "VERIFIED_BY", insertable = false, updatable = false)
	public ApplicationUserName getUserVerifiedBy() {
		return userVerifiedBy;
	}

	public void setUserVerifiedBy(ApplicationUserName userVerifiedBy) {
		this.userVerifiedBy = userVerifiedBy;
	}
	
	@Transient
	public String getVerifiedByName() {
		return userVerifiedBy != null ? userVerifiedBy.getName() : "";
	}
	
	@Transient
	public Integer getYear() {
		return invoiceDate != null 
			? invoiceDate.getYear()
			: 1900;
	}

	@OneToMany
	@JoinColumn(name = "INVOICE_ID", insertable = false, updatable = false)
	public List<InvoiceDetail> getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(List<InvoiceDetail> invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}
	
	@Transient
	public BigDecimal getTotalAmt() {
		BigDecimal res[] = {new BigDecimal("0.00")};
		res[0].setScale(2, RoundingMode.HALF_UP);
		if (invoiceDetail != null) {
			invoiceDetail.forEach(inv -> {
				res[0] = res[0].add(inv.getAmountGross().multiply(new BigDecimal(inv.getItemQty())));
			});
		}
		return res[0];
	}

}
