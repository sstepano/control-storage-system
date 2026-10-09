package org.code_studio.database;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CURRENCY", schema = "PUBLIC", catalog = "RM")
public class Currency implements java.io.Serializable {

	private Integer id;
	private String isoCurrencyCode;
	private String description;
	private Character symbol;
	private boolean isActive;
	private boolean isDeleted;
	private Short lastModifiedBy;
	private Date lastModifiedTimestamp;

	public Currency() {
	}

	public Currency(String isoCurrencyCode, boolean isActive, boolean isDeleted) {
		this.isoCurrencyCode = isoCurrencyCode;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
	}

	public Currency(String isoCurrencyCode, String description, Character symbol, boolean isActive, boolean isDeleted,
			Short lastModifiedBy, Date lastModifiedTimestamp) {
		this.isoCurrencyCode = isoCurrencyCode;
		this.description = description;
		this.symbol = symbol;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedTimestamp = lastModifiedTimestamp;
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

	@Column(name = "ISO_CURRENCY_CODE", nullable = false, length = 3)
	public String getIsoCurrencyCode() {
		return this.isoCurrencyCode;
	}

	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}

	@Column(name = "DESCRIPTION", length = 50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "SYMBOL", length = 1)
	public Character getSymbol() {
		return this.symbol;
	}

	public void setSymbol(Character symbol) {
		this.symbol = symbol;
	}

	@Column(name = "IS_ACTIVE", nullable = false)
	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "IS_DELETED", nullable = false)
	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "LAST_MODIFIED_BY")
	public Short getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(Short lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Column(name = "LAST_MODIFIED_TIMESTAMP", length = 26)
	public Date getLastModifiedTimestamp() {
		return this.lastModifiedTimestamp;
	}

	public void setLastModifiedTimestamp(Date lastModifiedTimestamp) {
		this.lastModifiedTimestamp = lastModifiedTimestamp;
	}

}
