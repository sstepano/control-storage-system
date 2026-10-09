package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class SalePlannerReview implements java.io.Serializable {

	private Integer id;
	private LocalDateTime date;
	private Integer day;
	private String clientName;
	private String clientCity;
	private String clientGroup;
	private Integer dayNumber;
	private Boolean isSunday;
	private Boolean isCame;
	private Boolean isWentTo;
	private Boolean isCalled;
	private Boolean isCalledTo;
	private BigDecimal deliveredAmt;
	private BigDecimal chargedAmt;
	private BigDecimal promisedAmt;
	private String typeName;
	private String saleOfficerName;
	private String contactedByName;
	private String description;

	public SalePlannerReview() {}

	//@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@Column(name = "DATE", unique = true, nullable = false)
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientCity() {
		return clientCity;
	}

	public void setClientCity(String clientCity) {
		this.clientCity = clientCity;
	}

	public String getClientGroup() {
		return clientGroup;
	}

	public void setClientGroup(String clientGroup) {
		this.clientGroup = clientGroup;
	}

	public Integer getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}

	public Boolean getIsSunday() {
		return isSunday;
	}

	public void setIsSunday(Boolean isSunday) {
		this.isSunday = isSunday;
	}

	public Boolean getIsCame() {
		return isCame;
	}

	public void setIsCame(Boolean isCame) {
		this.isCame = isCame;
	}

	public Boolean getIsWentTo() {
		return isWentTo;
	}

	public void setIsWentTo(Boolean isWentTo) {
		this.isWentTo = isWentTo;
	}

	public Boolean getIsCalled() {
		return isCalled;
	}

	public void setIsCalled(Boolean isCalled) {
		this.isCalled = isCalled;
	}

	public Boolean getIsCalledTo() {
		return isCalledTo;
	}

	public void setIsCalledTo(Boolean isCalledTo) {
		this.isCalledTo = isCalledTo;
	}

	public BigDecimal getDeliveredAmt() {
		return deliveredAmt;
	}

	public void setDeliveredAmt(BigDecimal deliveredAmt) {
		this.deliveredAmt = deliveredAmt;
	}

	public BigDecimal getChargedAmt() {
		return chargedAmt;
	}

	public void setChargedAmt(BigDecimal chargedAmt) {
		this.chargedAmt = chargedAmt;
	}

	public BigDecimal getPromisedAmt() {
		return promisedAmt;
	}

	public void setPromisedAmt(BigDecimal promisedAmt) {
		this.promisedAmt = promisedAmt;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSaleOfficerName() {
		return saleOfficerName;
	}

	public void setSaleOfficerName(String saleOfficerName) {
		this.saleOfficerName = saleOfficerName;
	}

	public String getContactedByName() {
		return contactedByName;
	}

	public void setContactedByName(String contactedByName) {
		this.contactedByName = contactedByName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
