package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CASH_DESK", schema = "PUBLIC", catalog = "RM")
public class CashDesk implements java.io.Serializable {

	private Integer id;
	private String name;
	private Integer clientId;
	private Boolean isRetail;
	private Boolean isCommissionSale;
	private String description;

	public CashDesk() {
	}

	public CashDesk(String name, Integer clientId, Boolean isRetail, Boolean isCommissionSale, String description) {
		this.name = name;
		this.clientId = clientId;
		this.isRetail = isRetail;
		this.isCommissionSale = isCommissionSale;
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

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CLIENT_ID")
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "IS_RETAIL")
	public Boolean getIsRetail() {
		return this.isRetail;
	}

	public void setIsRetail(Boolean isRetail) {
		this.isRetail = isRetail;
	}

	@Column(name = "IS_COMMISSION_SALE")
	public Boolean getIsCommissionSale() {
		return this.isCommissionSale;
	}

	public void setIsCommissionSale(Boolean isCommissionSale) {
		this.isCommissionSale = isCommissionSale;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
