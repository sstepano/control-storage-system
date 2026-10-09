package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "BANK_STATEMENT_DETAIL", schema = "PUBLIC", catalog = "RM")
public class BankStatementDetail implements java.io.Serializable {

	private Integer id;
	private Integer bankStatementId;
	private Integer detailOrdinalNumber;
	private Integer clientId;
	private ClientName client;
	private Integer clientBankAccountId;
	private ClientBankAccount clientBankAccount;
	private LocalDate valueDate;
	private BigDecimal amount;
	private Integer oldRecno;

	public BankStatementDetail() {
	}

	public BankStatementDetail(Integer bankStatementId, Integer detailOrdinalNumber, Integer clientId,
			Integer clientBankAccountId, LocalDate valueDate, BigDecimal amount, Integer oldRecno) {
		this.bankStatementId = bankStatementId;
		this.detailOrdinalNumber = detailOrdinalNumber;
		this.clientId = clientId;
		this.clientBankAccountId = clientBankAccountId;
		this.valueDate = valueDate;
		this.amount = amount;
		this.oldRecno = oldRecno;
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

	@Column(name = "BANK_STATEMENT_ID")
	public Integer getBankStatementId() {
		return this.bankStatementId;
	}

	public void setBankStatementId(Integer bankStatementId) {
		this.bankStatementId = bankStatementId;
	}

	@Column(name = "DETAIL_ORDINAL_NUMBER")
	public Integer getDetailOrdinalNumber() {
		return this.detailOrdinalNumber;
	}

	public void setDetailOrdinalNumber(Integer detailOrdinalNumber) {
		this.detailOrdinalNumber = detailOrdinalNumber;
	}

	@Column(name = "CLIENT_ID")
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "CLIENT_BANK_ACCOUNT_ID")
	public Integer getClientBankAccountId() {
		return this.clientBankAccountId;
	}

	public void setClientBankAccountId(Integer clientBankAccountId) {
		this.clientBankAccountId = clientBankAccountId;
	}

	@Column(name = "VALUE_DATE", length = 10)
	public LocalDate getValueDate() {
		return this.valueDate;
	}

	public void setValueDate(LocalDate valueDate) {
		this.valueDate = valueDate;
	}

	@Column(name = "AMOUNT", precision = 18)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "_OLD_RECNO")
	public Integer getOldRecno() {
		return this.oldRecno;
	}

	public void setOldRecno(Integer oldRecno) {
		this.oldRecno = oldRecno;
	}

	// 
	/**
	 * Custom DTO, Client samo sa nazivom
	 * insertable = false, updatable = false
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false)
	public ClientName getClient() {
		return client;
	}

	public void setClient(ClientName client) {
		this.client = client;
	}
	
	@ManyToOne()
	@JoinColumn(name = "CLIENT_BANK_ACCOUNT_ID", insertable = false, updatable = false)
	public ClientBankAccount getClientBankAccount() {
		return clientBankAccount;
	}

	public void setClientBankAccount(ClientBankAccount clientBankAccount) {
		this.clientBankAccount = clientBankAccount;
	}
	
	@Transient
	public String getClientName() {
		return client != null ? client.getName() : "";
	}
	
	@Transient
	public String getClientAccountNumber() {
		return clientBankAccount != null
			? clientBankAccount.getAccountNumber()
			: "";
	}

}
