package org.code_studio.database;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "BILL_OF_EXCHANGE", schema = "PUBLIC", catalog = "RM")
public class BillOfExchange implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	//@JsonBackReference
	private ClientName client;
	private String billOfExchangeNumber;
	private BigDecimal billOfExchangeValue;
	private Date valueDate;
	private String description;

	public BillOfExchange() {}

	public BillOfExchange(ClientName client, String billOfExchangeNumber, BigDecimal billOfExchangeValue, Date valueDate, String description) {
		this.client = client;
		this.billOfExchangeNumber = billOfExchangeNumber;
		this.billOfExchangeValue = billOfExchangeValue;
		this.valueDate = valueDate;
		this.setDescription(description);
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
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false)
	public ClientName getClient() {
		return this.client;
	}

	public void setClient(ClientName client) {
		this.client = client;
	}

	@Column(name = "BILL_OF_EXCHANGE_NUMBER", length = 100)
	public String getBillOfExchangeNumber() {
		return this.billOfExchangeNumber;
	}

	public void setBillOfExchangeNumber(String billOfExchangeNumber) {
		this.billOfExchangeNumber = billOfExchangeNumber;
	}

	@Column(name = "BILL_OF_EXCHANGE_VALUE", precision = 18)
	public BigDecimal getBillOfExchangeValue() {
		return this.billOfExchangeValue;
	}

	public void setBillOfExchangeValue(BigDecimal billOfExchangeValue) {
		this.billOfExchangeValue = billOfExchangeValue;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
