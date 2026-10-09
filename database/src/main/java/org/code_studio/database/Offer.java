package org.code_studio.database;

import java.math.BigDecimal;
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

@Entity
@SuppressWarnings("serial")
@Table(name = "offer", catalog = "rm")
public class Offer implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private String offerType;
	private String priceType;
	private Integer offerNumber;
	private Boolean isClosed;
	private LocalDate offerDate;
	private LocalDate expirationDate;
	private BigDecimal advancePaymentAmt;
	private Integer deliveryDays;
	private String offerHeaderText;
	private Boolean reserveItems;
	private Integer lastModifiedBy;
	private LocalDateTime lastModifiedDate;
	private String description;
	// ---------------------------------
	private List<OfferDetail> offerDetail;
	private ApplicationUserName userModifiedBy;

	public Offer() {}

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

	@Column(name = "OFFER_TYPE", length = 1)
	public String getOfferType() {
		return this.offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	@Column(name = "PRICE_TYPE", length = 1)
	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@Column(name = "OFFER_NUMBER")
	public Integer getOfferNumber() {
		return this.offerNumber;
	}

	public void setOfferNumber(Integer offerNumber) {
		this.offerNumber = offerNumber;
	}

	@Column(name = "IS_CLOSED")
	public Boolean getIsClosed() {
		return this.isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}

	@Column(name = "OFFER_DATE", length = 10)
	public LocalDate getOfferDate() {
		return this.offerDate;
	}

	public void setOfferDate(LocalDate offerDate) {
		this.offerDate = offerDate;
	}

	@Column(name = "EXPIRATION_DATE", length = 10)
	public LocalDate getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name = "ADVANCE_PAYMENT_AMT", precision = 18)
	public BigDecimal getAdvancePaymentAmt() {
		return this.advancePaymentAmt;
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

	@Column(name = "OFFER_HEADER_TEXT")
	public String getOfferHeaderText() {
		return offerHeaderText;
	}

	public void setOfferHeaderText(String offerHeaderText) {
		this.offerHeaderText = offerHeaderText;
	}

	@Column(name = "RESERVE_ITEMS")
	public Boolean getReserveItems() {
		return reserveItems;
	}

	public void setReserveItems(Boolean reserveItems) {
		this.reserveItems = reserveItems;
	}

	@Column(name = "LAST_MODIFIED_BY")
	public Integer getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(Integer lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Column(name = "LAST_MODIFIED_DATE", length = 19)
	public LocalDateTime getLastModifiedDate() {
		return this.lastModifiedDate;
	}

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
	@OneToMany
	@JoinColumn(name = "OFFER_ID", insertable = false, updatable = false)
	public List<OfferDetail> getOfferDetail() {
		return offerDetail;
	}

	public void setOfferDetail(List<OfferDetail> offerDetail) {
		this.offerDetail = offerDetail;
	}	
	
	@OneToOne
	@JoinColumn(name = "LAST_MODIFIED_BY", insertable = false, updatable = false)
	public ApplicationUserName getUserModifiedBy() {
		return userModifiedBy;
	}

	public void setUserModifiedBy(ApplicationUserName userModifiedBy) {
		this.userModifiedBy = userModifiedBy;
	}
	
	@Transient
	public String getModifiedByName() {
		return userModifiedBy != null ? userModifiedBy.getName() : "";
	}
}
