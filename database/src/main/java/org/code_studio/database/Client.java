package org.code_studio.database;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.SQLRestriction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings({"serial", "unused"})
@Entity
@Table(name = "CLIENT", schema = "PUBLIC", catalog = "RM")
public class Client implements java.io.Serializable {

	private Integer id;
	//private Integer groupId;
	private ClientGroup clientGroup;
	private String groupName;
	private Integer groupId;
	private Integer categoryId;
	private ClientCategory clientCategory;
	private String categoryName;
	private String name;
	private String fullName;
	private String additionalName;
	private String identificationId; //MB
	private String taxId; // PIB
	private Integer countryId;
	private Country country;
	private Integer poBox;
	private String address;
	private String centralPhoneNumber;
	private String faxNumber;
	private String website;
	private String activityCode;
	private String activity;
	private Boolean isSupplier;
	private Boolean isWholesale;
	private Boolean isRetail;
	private Boolean isCommissionSale;
	private Integer commissionWarehouseId;
	private Boolean supplierPriceOnly;
	private Boolean isVat;
	private Integer parentId;
	private Boolean isLoan;
	private Integer delayedPaymentDays;
	private String description;
	private Integer oldIdpp;
	private LocalDate planDate;
	
	private Boolean isDiscountSale;
	private String City;
	private String Municipality;
	private Double limitDin;
	private Double limitEur;
	private Double domRate;
	private Double workRate;
	private Double accountBalance;
	private Integer panelsGiven;
	
	//private List<ClientContact> ownersDirectors;
	
	//@JsonManagedReference
	private List <ClientContract> clientContracts;

	private List <ClientBankAccount> clientBankAccounts;
	
	//@JsonManagedReference
	private List <BillOfExchange> billsOfExchange;

	//@JsonManagedReference
	private List <DeliveryAddress> deliveryAddresses;
	
	private List<ClientSaleOfficerLink> primarySaleOfficer;

	@JsonBackReference
	private List<SalePlanner> salePlanner;
	
	@JsonBackReference(value="Backmanaged:Client->Orders")
	private List<Orders> orders;
	
	private Integer divisionId;
	private Division division;
	private Integer invoiceGenType;
	
	private List <ClientStoreImage> clientStoreImage;
	
	public Client() {}

	/*
	public Client(Long id, Integer divisionId) {
		this.id = id;
		this.divisionId = divisionId;
	}
	*/

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "GROUP_ID", insertable = false, updatable = false)
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	public ClientGroup getClientGroup() {
		return clientGroup;
	}

	public void setClientGroup(ClientGroup clientGroup) {
		this.clientGroup = clientGroup;
	}

	@Column(name = "CATEGORY_ID", insertable = false, updatable = false)
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	public ClientCategory getClientCategory() {
		return clientCategory;
	}

	public void setClientCategory(ClientCategory clientCategory) {
		this.clientCategory = clientCategory;
	}

	@Column(name = "NAME", length = 300)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FULL_NAME", length = 1000)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "ADDITIONAL_NAME", length = 1000)
	public String getAdditionalName() {
		return this.additionalName;
	}

	public void setAdditionalName(String additionalName) {
		this.additionalName = additionalName;
	}

	@Column(name = "IDENTIFICATION_ID", length = 100)
	public String getIdentificationId() {
		return this.identificationId;
	}

	public void setIdentificationId(String identificationId) {
		this.identificationId = identificationId;
	}

	@Column(name = "TAX_ID", length = 10)
	public String getTaxId() {
		return this.taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	@Column(name = "COUNTRY_ID", insertable = false, updatable = false)
	public Integer getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	

	@Column(name = "PO_BOX")
	public Integer getPoBox() {
		return this.poBox;
	}

	public void setPoBox(Integer poBox) {
		this.poBox = poBox;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CENTRAL_PHONE_NUMBER", length = 100)
	public String getCentralPhoneNumber() {
		return this.centralPhoneNumber;
	}

	public void setCentralPhoneNumber(String centralPhoneNumber) {
		this.centralPhoneNumber = centralPhoneNumber;
	}

	@Column(name = "FAX_NUMBER", length = 100)
	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	@Column(name = "WEBSITE", length = 100)
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "ACTIVITY_CODE", length = 100)
	public String getActivityCode() {
		return this.activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	@Column(name = "ACTIVITY", length = 100)
	public String getActivity() {
		return this.activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	@Column(name = "IS_SUPPLIER")
	public Boolean getIsSupplier() {
		return this.isSupplier;
	}

	public void setIsSupplier(Boolean isSupplier) {
		this.isSupplier = isSupplier;
	}

	@Column(name = "IS_WHOLESALE")
	public Boolean getIsWholesale() {
		return this.isWholesale;
	}

	public void setIsWholesale(Boolean isWholesale) {
		this.isWholesale = isWholesale;
	}

	@Column(name = "IS_RETAIL")
	public Boolean getIsRetail() {
		return this.isRetail;
	}

	public void setIsRetail(Boolean isRetail) {
		this.isRetail = isRetail;
	}

	@Column(name = "IS_COMMISSION_SALE")
	public Boolean getIsCommissionSale() {
		return this.isCommissionSale;
	}

	public void setIsCommissionSale(Boolean isCommissionSale) {
		this.isCommissionSale = isCommissionSale;
	}
	
	@Column(name = "COMMISSION_WAREHOUSE_ID")
	public Integer getCommissionWarehouseId() {
		return this.commissionWarehouseId;
	}

	public void setCommissionWarehouseId(Integer commissionWarehouseId) {
		this.commissionWarehouseId = commissionWarehouseId;
	}

	@Column(name = "SUPPLIER_PRICE_ONLY")
	public Boolean getSupplierPriceOnly() {
		return this.supplierPriceOnly;
	}

	public void setSupplierPriceOnly(Boolean supplierPriceOnly) {
		this.supplierPriceOnly = supplierPriceOnly;
	}

	@Column(name = "IS_VAT")
	public Boolean getIsVat() {
		return this.isVat;
	}

	public void setIsVat(Boolean isVat) {
		this.isVat = isVat;
	}

	@Column(name = "PARENT_ID")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "IS_LOAN")
	public Boolean getIsLoan() {
		return this.isLoan;
	}

	public void setIsLoan(Boolean isLoan) {
		this.isLoan = isLoan;
	}

	@Column(name = "DELAYED_PAYMENT_DAYS")
	public Integer getDelayedPaymentDays() {
		return delayedPaymentDays;
	}

	public void setDelayedPaymentDays(Integer delayedPaymentDays) {
		this.delayedPaymentDays = delayedPaymentDays;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "_OLD_IDPP")
	public Integer getOldIdpp() {
		return this.oldIdpp;
	}

	public void setOldIdpp(Integer oldIdpp) {
		this.oldIdpp = oldIdpp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
	public List <ClientContract> getClientContracts() {
		return this.clientContracts;
	}

	public void setClientContracts(List <ClientContract> clientContracts) {
		this.clientContracts = clientContracts;
	}

	/*
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
	public List <ClientContact> getClientContacts() {
		return this.clientContacts;
	}

	public void setClientContacts(List <ClientContact> clientContacts) {
		this.clientContacts = clientContacts;
	}
	*/

	// Ako stavimo cascade ALL, ako klijent ima racun, dobije null za client_id u bankaccounts i pukne
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.REMOVE)
	public List <ClientBankAccount> getClientBankAccounts() {
		return this.clientBankAccounts;
	}

	public void setClientBankAccounts(List <ClientBankAccount> clientBankAccounts) {
		this.clientBankAccounts = clientBankAccounts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
	public List <BillOfExchange> getBillOfExchanges() {
		return this.billsOfExchange;
	}

	public void setBillOfExchanges(List <BillOfExchange> billOfExchanges) {
		this.billsOfExchange = billOfExchanges;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
	public List <DeliveryAddress> getDeliveryAddresses() {
		return this.deliveryAddresses;
	}

	public void setDeliveryAddresses(List <DeliveryAddress> deliveryAddresses) {
		this.deliveryAddresses = deliveryAddresses;
	}
	
	/*
	@SQLRestriction("is_owner_director = true")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	public List <ClientContact> getOwnersDirectors() {
		return this.ownersDirectors;
	}

	public void setOwnersDirectors(List <ClientContact> ownersDirectors) {
		this.ownersDirectors = ownersDirectors;
	}
	*/

	@Column(name = "PLAN_DATE")
	public LocalDate getPlanDate() {
		return planDate;
	}

	public void setPlanDate(LocalDate planDate) {
		this.planDate = planDate;
	}
	
	@SQLRestriction("is_primary = true")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	public List<ClientSaleOfficerLink> getPrimarySaleOfficer() {
		return this.primarySaleOfficer;
	}

	public void setPrimarySaleOfficer(List<ClientSaleOfficerLink> primarySaleOfficer) {
		this.primarySaleOfficer = primarySaleOfficer;
	}
	
	//---------------------------------------------------
	@Transient
	public String getGroupName() {
		return clientGroup == null ? "" : clientGroup.getName();
	}
	
	@Transient
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
	@Transient
	public String getCategoryName() {
		return clientCategory == null ? "" : clientCategory.getName();
	}

	@Transient
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy= "client")
	//@JoinColumn(name = "CLIENT_ID")
	public List<SalePlanner> getSalePlanner() {
		return this.salePlanner;
	}

	public void setSalePlanner(List<Orders> orders) {
		this.orders = orders;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy= "client")
	public List<Orders> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public Boolean getIsDiscountSale() {
		return isDiscountSale;
	}

	public void setIsDiscountSale(Boolean isDiscountSale) {
		this.isDiscountSale = isDiscountSale;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getMunicipality() {
		return Municipality;
	}

	public void setMunicipality(String municipality) {
		Municipality = municipality;
	}

	public Double getlimitDin() {
		return limitDin;
	}

	public void setlimitDin(Double limitDin) {
		this.limitDin = limitDin;
	}

	public Double getlimitEur() {
		return limitEur;
	}

	public void setlimitEur(Double limitEur) {
		this.limitEur = limitEur;
	}

	public Double getDomRate() {
		return domRate;
	}

	public void setDomRate(Double domRate) {
		this.domRate = domRate;
	}

	public Double getWorkRate() {
		return workRate;
	}

	public void setWorkRate(Double workRate) {
		this.workRate = workRate;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Integer getPanelsGiven() {
		return panelsGiven;
	}

	public void setPanelsGiven(Integer panelsGiven) {
		this.panelsGiven = panelsGiven;
	}
	
	@Column(name = "DIVISION_ID", insertable = false, updatable = false)
	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
	
	public Integer getInvoiceGenType() {
		return invoiceGenType;
	}

	public void setInvoiceGenType(Integer invoiceGenType) {
		this.invoiceGenType = invoiceGenType;
	}
	
	@OneToMany
	@JoinColumn(name="CLIENT_ID")
	public List<ClientStoreImage> getClientStoreImage() {
		return this.clientStoreImage;
	}
	
	public void setClientStoreImage(List<ClientStoreImage> clientStoreImage) {
		this.clientStoreImage = clientStoreImage;
	}

}
