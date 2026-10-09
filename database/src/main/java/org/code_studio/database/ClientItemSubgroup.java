package org.code_studio.database;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CLIENT_ITEM_SUBGROUP", schema = "PUBLIC", catalog = "RM")
public class ClientItemSubgroup implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private Integer groupId;
	private Integer catalogId;
	private String code;
	private String name;
	private String nameInternet;
	private String manager;
	private BigDecimal priceCorrectionRate;
	private String description;

	public ClientItemSubgroup() {
	}

	public ClientItemSubgroup(Integer clientId, Integer groupId, Integer catalogId, String code, String name,
			String nameInternet, String manager, BigDecimal priceCorrectionRate, String description) {
		this.clientId = clientId;
		this.groupId = groupId;
		this.catalogId = catalogId;
		this.code = code;
		this.name = name;
		this.nameInternet = nameInternet;
		this.manager = manager;
		this.priceCorrectionRate = priceCorrectionRate;
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

	@Column(name = "GROUP_ID")
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "CATALOG_ID")
	public Integer getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	@Column(name = "CODE", length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NAME_INTERNET", length = 200)
	public String getNameInternet() {
		return this.nameInternet;
	}

	public void setNameInternet(String nameInternet) {
		this.nameInternet = nameInternet;
	}

	@Column(name = "MANAGER", length = 100)
	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Column(name = "PRICE_CORRECTION_RATE", precision = 18)
	public BigDecimal getPriceCorrectionRate() {
		return this.priceCorrectionRate;
	}

	public void setPriceCorrectionRate(BigDecimal priceCorrectionRate) {
		this.priceCorrectionRate = priceCorrectionRate;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
