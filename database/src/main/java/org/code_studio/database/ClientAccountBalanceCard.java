/***
 * READ ONLY POJO
 * Used to get balance card for the client's account
 * We do not update it, save it or modify it anyhow 
 */

package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDate;
//import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class ClientAccountBalanceCard implements java.io.Serializable {

	/*
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID uuid;
	*/
	
	@Id
	@Column(name = "ID")
	private Integer id;
	
	private Integer clientId;
	private LocalDate valueDate;
	private LocalDate bookingDate;
	private Integer bankStatementId;
	private Integer invoiceId;
	private BigDecimal debitAmt;
	private BigDecimal creditAmt;
	private BigDecimal balanceAmt;

	public ClientAccountBalanceCard() {}

	/*
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	*/
	
	public Integer getId() {
		return id;
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
	
	@Column(name = "VALUE_DATE")
	public LocalDate getValueDate() {
		return this.valueDate;
	}

	public void setValueDate(LocalDate valueDate) {
		this.valueDate = valueDate;
	}

	@Column(name = "BOOKING_DATE")
	public LocalDate getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Integer getBankStatementId() {
		return bankStatementId;
	}

	public void setBankStatementId(Integer bankStatementId) {
		this.bankStatementId = bankStatementId;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "DEBIT_AMT", precision = 18)
	public BigDecimal getDebitAmt() {
		return this.debitAmt == null ? BigDecimal.ZERO : this.debitAmt;
	}

	public void setDebitAmt(BigDecimal debitAmt) {
		this.debitAmt = debitAmt;
	}

	public BigDecimal getCreditAmt() {
		return creditAmt == null ? BigDecimal.ZERO : this.creditAmt;
	}

	public void setCreditAmt(BigDecimal creditAmt) {
		this.creditAmt = creditAmt;
	}

	@Column(name = "BALANCE_AMT", precision = 18)
	public BigDecimal getBalanceAmt() {
		return this.balanceAmt == null ? BigDecimal.ZERO : this.balanceAmt;
	}

	public void setBalanceAmt(BigDecimal balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

}
