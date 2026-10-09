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
@Table(name = "VAT_GROUP", schema = "PUBLIC", catalog = "RM")
public class VatGroup implements java.io.Serializable {

	private Integer id;
	private BigDecimal vatRate;
	private String description;

	public VatGroup() {
	}

	public VatGroup(BigDecimal vatRate, String description) {
		this.vatRate = vatRate;
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

	@Column(name = "VAT_RATE", precision = 18)
	public BigDecimal getVatRate() {
		return this.vatRate;
	}

	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
