package org.code_studio.database;
// Generated Nov 21, 2021, 9:59:48 AM by Hibernate Tools 4.3.5.Final

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties({"hibernateLazyInitializer"}) /* TODO: Ovo moram da vidim zasto radi sa ovim annotationom, bez njega javlja gresku*/
@Entity
@Table(name = "BANK", schema = "PUBLIC", catalog = "RM")
public class Bank implements java.io.Serializable {

	private Integer id;
	private Country country;
	private String name;
	private Integer bankIdentificatorNumber;
	private String address;
	private String city;
	private String phone;
	private String fax;
	private String note;
	@JsonBackReference(value="Bank->clientBankAccounts") private List <ClientBankAccount> clientBankAccounts;

	public Bank() {}

	public Bank(Country country, String name, Integer bankIdentificatorNumber, String address, String city,
			String phone, String fax, String note, List <ClientBankAccount> clientBankAccounts) {
		this.setCountry(country);
		this.name = name;
		this.bankIdentificatorNumber = bankIdentificatorNumber;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.fax = fax;
		this.note = note;
		this.clientBankAccounts = clientBankAccounts;
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

	@Transient
	public String getCountryName() {
		return country.getName();
	}

	/*
	@Transient
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "BANK_IDENTIFICATOR_NUMBER")
	public Integer getBankIdentificatorNumber() {
		return this.bankIdentificatorNumber;
	}

	public void setBankIdentificatorNumber(Integer bankIdentificatorNumber) {
		this.bankIdentificatorNumber = bankIdentificatorNumber;
	}

	@Column(name = "ADDRESS", length = 300)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CITY", length = 100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "PHONE", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "FAX", length = 100)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "NOTE", length = 1000)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
	public List <ClientBankAccount> getClientBankAccounts() {
		return this.clientBankAccounts;
	}

	public void setClientBankAccounts (List <ClientBankAccount> clientBankAccounts) {
		this.clientBankAccounts = clientBankAccounts;
	}

}
