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
@Table(name = "ITEM_GROUP", schema = "PUBLIC", catalog = "RM")
public class ItemGroup implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private String code;
	private String name;
	private String nameInternet;
	private BigDecimal priceCorrectionRate;
	private Integer oldid;

	public ItemGroup() {
	}

	public ItemGroup(Integer clientId, String code, String name, String nameInternet, BigDecimal priceCorrectionRate,
			Integer oldid) {
		this.clientId = clientId;
		this.code = code;
		this.name = name;
		this.nameInternet = nameInternet;
		this.priceCorrectionRate = priceCorrectionRate;
		this.oldid = oldid;
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

	@Column(name = "PRICE_CORRECTION_RATE", precision = 18)
	public BigDecimal getPriceCorrectionRate() {
		return this.priceCorrectionRate;
	}

	public void setPriceCorrectionRate(BigDecimal priceCorrectionRate) {
		this.priceCorrectionRate = priceCorrectionRate;
	}

	@Column(name = "_OLDID")
	public Integer getOldid() {
		return this.oldid;
	}

	public void setOldid(Integer oldid) {
		this.oldid = oldid;
	}

}
