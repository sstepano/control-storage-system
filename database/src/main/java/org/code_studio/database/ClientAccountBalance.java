package org.code_studio.database;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CLIENT_ACCOUNT_BALANCE", schema = "PUBLIC", catalog = "RM")
public class ClientAccountBalance implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private String changeType;
	private Date bookingDate;
	private Date valueDate;
	private BigDecimal amount;
	private Byte debitCredit;
	private Integer bankStatementId;
	private Integer invoiceId;
	private Integer kifId;
	private Integer oldRecNo;

	public ClientAccountBalance() {
	}

	public ClientAccountBalance(Integer clientId, String changeType, Date bookingDate, Date valueDate,
			BigDecimal amount, Byte debitCredit, Integer bankStatementId, Integer invoiceId, Integer kifId,
			Integer oldRecNo) {
		this.clientId = clientId;
		this.changeType = changeType;
		this.bookingDate = bookingDate;
		this.valueDate = valueDate;
		this.amount = amount;
		this.debitCredit = debitCredit;
		this.bankStatementId = bankStatementId;
		this.invoiceId = invoiceId;
		this.kifId = kifId;
		this.oldRecNo = oldRecNo;
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

	@Column(name = "CHANGE_TYPE", length = 2)
	public String getChangeType() {
		return this.changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	@Column(name = "BOOKING_DATE", length = 10)
	public Date getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	@Column(name = "VALUE_DATE", length = 10)
	public Date getValueDate() {
		return this.valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	@Column(name = "AMOUNT", precision = 18)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "DEBIT_CREDIT")
	public Byte getDebitCredit() {
		return this.debitCredit;
	}

	public void setDebitCredit(Byte debitCredit) {
		this.debitCredit = debitCredit;
	}

	@Column(name = "BANK_STATEMENT_ID")
	public Integer getBankStatementId() {
		return this.bankStatementId;
	}

	public void setBankStatementId(Integer bankStatementId) {
		this.bankStatementId = bankStatementId;
	}

	@Column(name = "INVOICE_ID")
	public Integer getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "KIF_ID")
	public Integer getKifId() {
		return this.kifId;
	}

	public void setKifId(Integer kifId) {
		this.kifId = kifId;
	}

	@Column(name = "_OLD_REC_NO")
	public Integer getOldRecNo() {
		return this.oldRecNo;
	}

	public void setOldRecNo(Integer oldRecNo) {
		this.oldRecNo = oldRecNo;
	}

}
