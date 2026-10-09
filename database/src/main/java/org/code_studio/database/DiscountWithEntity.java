package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class DiscountWithEntity implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private LocalDateTime validFrom;
	private LocalDateTime validTo;
	private BigDecimal discountRate;
	private Integer discountEntityId;
	private Integer discountGroupId;
	private String discountGroupName;
	private String discountEntityName;
	private String description;
	private LocalDateTime validToOriginal;

	public DiscountWithEntity() {}

	public DiscountWithEntity(Integer clientId, LocalDateTime validFrom, LocalDateTime validTo,
			BigDecimal discountRate, Integer discountEntityId, String discountGroupName, String discountEntityName
			, String description, LocalDateTime validToOriginal) {
		this.clientId = clientId;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.discountRate = discountRate;
		this.discountEntityId = discountEntityId;
		this.discountGroupName = discountGroupName;
		this.discountEntityName = discountEntityName;
		this.description = description;
		this.validToOriginal = validToOriginal;
	}

	//@GeneratedValue(strategy = IDENTITY)
	@Id
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

	@Column(name = "VALID_FROM")
	public LocalDateTime getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(LocalDateTime validFrom) {
		this.validFrom = validFrom;
	}

	@Column(name = "VALID_TO")
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

	public Integer getDiscountGroupId() {
		return discountGroupId;
	}

	public void setDiscountGroupId(Integer discountGroupId) {
		this.discountGroupId = discountGroupId;
	}

	@Column(name = "DISCOUNT_GROUP_NAME", length = 100)
	public String getDiscountGroupName() {
		return discountGroupName;
	}

	public void setDiscountGroupName(String discountGroupName) {
		this.discountGroupName = discountGroupName;
	}

	@Column(name = "DISCOUNT_ENTITY_NAME", length = 100)
	public String getDiscountEntityName() {
		return this.discountEntityName;
	}
	
	public void setDiscountEntityName(String discountEntityName) {
		this.discountEntityName = discountEntityName;
	}
	
	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "VALID_TO_ORIGINAL")
	public LocalDateTime getValidToOriginal() {
		return this.validToOriginal;
	}

	public void setValidToOriginal(LocalDateTime validToOriginal) {
		this.validToOriginal = validToOriginal;
	}

}
