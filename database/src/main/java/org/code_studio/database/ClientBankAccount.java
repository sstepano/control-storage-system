package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "CLIENT_BANK_ACCOUNT", schema = "PUBLIC", catalog = "RM")
public class ClientBankAccount implements java.io.Serializable {

	private Integer id;
	private Bank bank;
	private Integer clientId;
	private ClientName client;
	private String accountNumber;
	private String iban;
	private String note;

	public ClientBankAccount() {}

	public ClientBankAccount(Bank bank, Integer clientId, String accountNumber, String iban, String note) {
		this.bank = bank;
		this.clientId = clientId;
		this.accountNumber = accountNumber;
		this.iban = iban;
		this.note = note;
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

	/*
	@Column(name = "BANK_ID", length = 100)
	public Integer getBankId() {
		return this.bankId;
	}
	
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Column(name = "CLIENT_ID")
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
		//this.client = new ClientName(client != null ? client.getName() : null);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false)
	public ClientName getClient() {
		return this.client;
	}

	public void setClient(ClientName client) {
		this.client = client;
	}

	@Column(name = "ACCOUNT_NUMBER", length = 100)
	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Column(name = "IBAN", length = 100)
	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	@Column(name = "NOTE", length = 1000)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Transient
	public String getClientName() {
		return this.client != null 
			? this.client.getName()
			: "";
	}
	
	@Transient
	public String getBankName() {
		return this.bank != null 
			? this.bank.getName()
			: "";
	}
}
