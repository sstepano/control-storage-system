package org.code_studio.database;

import java.io.Serializable;

@SuppressWarnings({"serial", "unused"})
public class Composite_BankBOFContract implements Serializable {

	private Integer id;
	private ClientBankAccount clientBankAccount;
	private BillOfExchange billOfExchange;
	private ClientContract clientContract;
	//private String bankAccountName;
	
	private String objectType;
	private String objectValue;
	
	public Composite_BankBOFContract() {}

	public Composite_BankBOFContract(String strObjectType, String objectValue) {
		this.setObjectType(strObjectType);
		this.setObjectValue(objectValue);
	}

	public ClientBankAccount getClientBankAccount() {
		return clientBankAccount;
	}
	public void setClientBankAccount(ClientBankAccount clientBankAccount) {
		this.clientBankAccount = clientBankAccount;
	}
	public BillOfExchange getBillOfExchange() {
		return billOfExchange;
	}
	public void setBillOfExchange(BillOfExchange billOfExchange) {
		this.billOfExchange = billOfExchange;
	}
	public ClientContract getClientContract() {
		return clientContract;
	}
	public void setClientContract(ClientContract clientContract) {
		this.clientContract = clientContract;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}
	
	/*
	@Transient
	public String getBankAccountName() {
		return clientBankAccount.bank.getName() + ":" + clientBankAccount.getAccountNumber();
	}

	@Transient
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	*/
	
}
