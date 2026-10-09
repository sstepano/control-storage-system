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
@Table(name = "CUSTOMS_GROUP", schema = "PUBLIC", catalog = "RM")
public class CustomsGroup implements java.io.Serializable {

	private Integer id;
	private String tariffNumber;
	private BigDecimal customsRate;
	private String description;

	public CustomsGroup() {
	}

	public CustomsGroup(String tariffNumber, BigDecimal customsRate, String description) {
		this.tariffNumber = tariffNumber;
		this.customsRate = customsRate;
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

	@Column(name = "TARIFF_NUMBER", length = 100)
	public String getTariffNumber() {
		return this.tariffNumber;
	}

	public void setTariffNumber(String tariffNumber) {
		this.tariffNumber = tariffNumber;
	}

	@Column(name = "CUSTOMS_RATE", precision = 18)
	public BigDecimal getCustomsRate() {
		return this.customsRate;
	}

	public void setCustomsRate(BigDecimal customsRate) {
		this.customsRate = customsRate;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
