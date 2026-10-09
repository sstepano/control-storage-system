package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "WAREHOUSE", schema = "PUBLIC", catalog = "RM")
public class Warehouse implements java.io.Serializable {

	private Integer id;
	private Integer warehouseNumber;
	private String name;
	private Boolean isRetail;
	// private Boolean isRetailDefault;
	private Integer retailSerialNo;
	private Boolean isWholesale;
	// private Boolean isWholesaleDefault;
	private Integer wholesaleSerialNo;
	private Boolean isDiscountRetail;
	// private Boolean isDiscountRetailDefault;
	private Integer discountRetailSerialNo;
	private Boolean isDiscountWholesale;
	// private Boolean isDiscountWholesaleDefault;
	private Integer discountWholesaleSerialNo;
	private Boolean isCommission;
	private Integer commissionSerialNo;
	private Boolean isOfferred;
	private Boolean isInternet;
	private Boolean isActive;

	public Warehouse() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "WAREHOUSE_NUMBER")
	public Integer getWarehouseNumber() {
		return warehouseNumber;
	}

	public void setWarehouseNumber(Integer warehouseNumber) {
		this.warehouseNumber = warehouseNumber;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "IS_RETAIL")
	public Boolean getIsRetail() {
		return this.isRetail;
	}

	public void setIsRetail(Boolean isRetail) {
		this.isRetail = isRetail;
	}

	/*
	@Column(name = "IS_RETAIL_DEFAULT")
	public Boolean getIsRetailDefault() {
		return this.isRetailDefault;
	}

	public void setIsRetailDefault(Boolean isRetailDefault) {
		this.isRetailDefault = isRetailDefault;
	}
	*/

	@Column(name = "RETAIL_SERIAL_NO")
	public Integer getRetailSerialNo() {
		return this.retailSerialNo;
	}

	public void setRetailSerialNo(Integer retailSerialNo) {
		this.retailSerialNo = retailSerialNo;
	}

	@Column(name = "IS_WHOLESALE")
	public Boolean getIsWholesale() {
		return this.isWholesale;
	}

	public void setIsWholesale(Boolean isWholesale) {
		this.isWholesale = isWholesale;
	}

	/*
	@Column(name = "IS_WHOLESALE_DEFAULT")
	public Boolean getIsWholesaleDefault() {
		return this.isWholesaleDefault;
	}

	public void setIsWholesaleDefault(Boolean isWholesaleDefault) {
		this.isWholesaleDefault = isWholesaleDefault;
	}
	*/

	@Column(name = "WHOLESALE_SERIAL_NO")
	public Integer getWholesaleSerialNo() {
		return this.wholesaleSerialNo;
	}

	public void setWholesaleSerialNo(Integer wholesaleSerialNo) {
		this.wholesaleSerialNo = wholesaleSerialNo;
	}

	@Column(name = "IS_DISCOUNT_RETAIL")
	public Boolean getIsDiscountRetail() {
		return this.isDiscountRetail;
	}

	public void setIsDiscountRetail(Boolean isDiscountRetail) {
		this.isDiscountRetail = isDiscountRetail;
	}

	/*
	@Column(name = "IS_DISCOUNT_RETAIL_DEFAULT")
	public Boolean getIsDiscountRetailDefault() {
		return this.isDiscountRetailDefault;
	}

	public void setIsDiscountRetailDefault(Boolean isDiscountRetailDefault) {
		this.isDiscountRetailDefault = isDiscountRetailDefault;
	}
	*/

	@Column(name = "DISCOUNT_RETAIL_SERIAL_NO")
	public Integer getDiscountRetailSerialNo() {
		return this.discountRetailSerialNo;
	}

	public void setDiscountRetailSerialNo(Integer discountRetailSerialNo) {
		this.discountRetailSerialNo = discountRetailSerialNo;
	}

	@Column(name = "IS_DISCOUNT_WHOLESALE")
	public Boolean getIsDiscountWholesale() {
		return this.isDiscountWholesale;
	}

	public void setIsDiscountWholesale(Boolean isDiscountWholesale) {
		this.isDiscountWholesale = isDiscountWholesale;
	}

	/*m
	@Column(name = "IS_DISCOUNT_WHOLESALE_DEFAULT")
	public Boolean getIsDiscountWholesaleDefault() {
		return this.isDiscountWholesaleDefault;
	}

	public void setIsDiscountWholesaleDefault(Boolean isDiscountWholesaleDefault) {
		this.isDiscountWholesaleDefault = isDiscountWholesaleDefault;
	}
	*/

	@Column(name = "DISCOUNT_WHOLESALE_SERIAL_NO")
	public Integer getDiscountWholesaleSerialNo() {
		return this.discountWholesaleSerialNo;
	}

	public void setDiscountWholesaleSerialNo(Integer discountWholesaleSerialNo) {
		this.discountWholesaleSerialNo = discountWholesaleSerialNo;
	}

	@Column(name = "IS_COMMISSION")
	public Boolean getIsCommission() {
		return this.isCommission;
	}

	public void setIsCommission(Boolean isCommission) {
		this.isCommission = isCommission;
	}

	@Column(name = "COMMISSION_SERIAL_NO")
	public Integer getCommissionSerialNo() {
		return this.commissionSerialNo;
	}

	public void setCommissionSerialNo(Integer commissionSerialNo) {
		this.commissionSerialNo = commissionSerialNo;
	}

	@Column(name = "IS_OFFERRED")
	public Boolean getIsOfferred() {
		return this.isOfferred;
	}

	public void setIsOfferred(Boolean isOfferred) {
		this.isOfferred = isOfferred;
	}

	@Column(name = "IS_INTERNET")
	public Boolean getIsInternet() {
		return this.isInternet;
	}

	public void setIsInternet(Boolean isInternet) {
		this.isInternet = isInternet;
	}
	
	@Column(name = "IS_ACTIVE")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	// -------------------------------------------
	@Transient
	public String getNameAndNumber() {
		return this.warehouseNumber + "-" + this.name;
	} 

}
