package org.code_studio.database;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class ClientPricelistTotal implements java.io.Serializable {

	private Integer id;
	private Integer    clientId;
	private Integer    activeCnt;
	private BigDecimal activeAmt;
	private Integer    inactiveCnt;
	private BigDecimal inactiveAmt;
	private Integer    totalCnt;
	private BigDecimal totalAmt;

	public ClientPricelistTotal() {}

	/**
	 *  da bi dobili POJO od resultseta koji nije tabela: 
	 * mora ID, iskljuciti generatedValue, mora nativequery
	 */
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CLIENT_ID")//, insertable = false, updatable = false)
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	
	/*
	@ManyToOne
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	*/

	public Integer getActiveCnt() {
		return activeCnt;
	}

	public void setActiveCnt(Integer activeCnt) {
		this.activeCnt = activeCnt;
	}

	public BigDecimal getActiveAmt() {
		return activeAmt;
	}

	public void setActiveAmt(BigDecimal activeAmt) {
		this.activeAmt = activeAmt;
	}

	public Integer getInactiveCnt() {
		return inactiveCnt;
	}

	public void setInactiveCnt(Integer inactiveCnt) {
		this.inactiveCnt = inactiveCnt;
	}

	public BigDecimal getInactiveAmt() {
		return inactiveAmt;
	}

	public void setInactiveAmt(BigDecimal inactiveAmt) {
		this.inactiveAmt = inactiveAmt;
	}

	public Integer getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(Integer totalCnt) {
		this.totalCnt = totalCnt;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

}
