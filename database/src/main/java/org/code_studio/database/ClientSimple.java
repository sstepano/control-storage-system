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
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.SQLRestriction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings({"serial", "unused"})
@Entity
@Table(name = "CLIENT", schema = "PUBLIC", catalog = "RM")
public class ClientSimple implements java.io.Serializable {

	private Integer id;
	private Integer groupId;
	private Integer categoryId;
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
	private Integer divisionId;
	
	public ClientSimple() {}

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

	@Column(name = "CATEGORY_ID", insertable = false, updatable = false)
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	@Column(name = "PLAN_DATE")
	public LocalDate getPlanDate() {
		return planDate;
	}

	public void setPlanDate(LocalDate planDate) {
		this.planDate = planDate;
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
	
	@Column(name = "DIVISION_ID")
	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

}
