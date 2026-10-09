package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class InvoiceDetailSum implements java.io.Serializable {

	private Integer id;
	private Integer cnt;
	private BigDecimal sumNetAmt;


	public InvoiceDetailSum() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public BigDecimal getSumNetAmt() {
		return sumNetAmt;
	}

	public void setSumNetAmt(BigDecimal sumNetAmt) {
		this.sumNetAmt = sumNetAmt;
	}

}
