package org.code_studio.model;
import java.time.LocalDate;

public interface IClient {
	Long getId();
	//ClientGroup getClientGroup();
	//String getGroupName();
	Integer getGroupId();
	Integer getCategoryId();
	//ClientCategory getClientCategory();
	//String getCategoryName();
	String getName();
	String getFullName();
	String getAdditionalName();
	String getIdentificationId(); //MB
	String getTaxId(); // PIB
	Integer getCountryId();
	//Country country();
	Integer getPoBox();
	String getAddress();
	String getCentralPhoneNumber();
	String getFaxNumber();
	String getWebsite();
	String getActivityCode();
	String getActivity();
	Boolean getIsSupplier();
	Boolean getIsWholesale();
	Boolean getIsRetail();
	Boolean getIsCommissionSale();
	Boolean getSupplierPriceOnly();
	Boolean getIsVat();
	Integer getParentId();
	Boolean getIsLoan();
	String getDescription();
	Integer getOldIdpp();
	LocalDate getPlanDate();
	
	Boolean getIsDiscountSale();
	String getCity();
	String getMunicipality();
	Double getLimitDin();
	Double getLimitEur();
	Double getDomRate();
	Double getWorkRate();
	Double getAccountBalance();
	Integer getPanelsGiven();
}
