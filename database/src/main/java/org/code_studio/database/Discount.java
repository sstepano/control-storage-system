package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "discount", catalog = "rm")
public class Discount implements java.io.Serializable {

	private Integer id;
	private DiscountGroup discountGroup;
	private Integer clientId;
	private LocalDateTime validFrom;
	private LocalDateTime validTo;
	private BigDecimal discountRate;
	private Integer discountEntityId;
	private String description;
	private LocalDateTime validToOriginal;

	public Discount() {}

	public Discount(DiscountGroup discountGroup, Integer clientId, LocalDateTime validFrom, LocalDateTime validTo,
			BigDecimal discountRate, Integer discountEntityId, String description, LocalDateTime validToOriginal) {
		this.discountGroup = discountGroup;
		this.clientId = clientId;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.discountRate = discountRate;
		this.discountEntityId = discountEntityId;
		this.description = description;
		this.validToOriginal = validToOriginal;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISCOUNT_GROUP_ID")
	public DiscountGroup getDiscountGroup() {
		return this.discountGroup;
	}

	public void setDiscountGroup(DiscountGroup discountGroup) {
		this.discountGroup = discountGroup;
	}

	@Column(name = "CLIENT_ID")
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "VALID_FROM", length = 19)
	public LocalDateTime getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(LocalDateTime validFrom) {
		this.validFrom = validFrom;
	}

	@Column(name = "VALID_TO", length = 19)
	public LocalDateTime getValidTo() {
		return this.validTo;
	}

	public void setValidTo(LocalDateTime validTo) {
		this.validTo = validTo;
	}

	@Column(name = "DISCOUNT_RATE", precision = 18)
	public BigDecimal getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name = "DISCOUNT_ENTITY_ID")
	public Integer getDiscountEntityId() {
		return this.discountEntityId;
	}

	public void setDiscountEntityId(Integer discountEntityId) {
		this.discountEntityId = discountEntityId;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "VALID_TO_ORIGINAL", length = 19)
	public LocalDateTime getValidToOriginal() {
		return this.validToOriginal;
	}

	public void setValidToOriginal(LocalDateTime validToOriginal) {
		this.validToOriginal = validToOriginal;
	}
	
	// ----------------------------------------------
	@Transient
	public String getDiscountGroupName() {
		return this.getDiscountGroup().getName();
	}

}
