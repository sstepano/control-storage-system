package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import jakarta.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class ClientLastPaidAmount implements java.io.Serializable {

	private Integer id;
	private String name;
	private String city;
	private BigDecimal lastPaidAmt;
	private BigDecimal lastDeliveredAmt;
	private String clientGroupName;

	public ClientLastPaidAmount() {}

	/*@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	*/

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public BigDecimal getLastPaidAmt() {
		return lastPaidAmt;
	}

	public void setLastPaidAmt(BigDecimal lastPaidAmt) {
		this.lastPaidAmt = lastPaidAmt;
	}

	public BigDecimal getLastDeliveredAmt() {
		return lastDeliveredAmt;
	}

	public void setLastDeliveredAmt(BigDecimal lastDeliveredAmt) {
		this.lastDeliveredAmt = lastDeliveredAmt;
	}

	public String getClientGroupName() {
		return clientGroupName;
	}

	public void setClientGroupName(String clientGroupName) {
		this.clientGroupName = clientGroupName;
	}

}
