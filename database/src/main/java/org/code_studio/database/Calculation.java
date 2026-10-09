package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@SuppressWarnings("serial")
@Entity
@Table(name = "CALCULATION", schema = "PUBLIC", catalog = "RM", uniqueConstraints = @UniqueConstraint(columnNames = {
		"CLIENT_ID", "CLIENT_CALCULATION_ID" }))
public class Calculation implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private Integer clientCalculationId;
	private String name;
	private BigDecimal customsExchangeRate;
	private BigDecimal buyingExchangeRate;
	private BigDecimal sellingExchangeRate;
	private BigDecimal discountExchangeRate;
	private BigDecimal incomeTaxRate;
	private BigDecimal vatRate;
	private BigDecimal customsRate;
	private BigDecimal forwarderRate;
	private BigDecimal purchasePriceRm;
	private BigDecimal purchasePriceDinRm;
	private BigDecimal purchasePriceRmSpec;
	private BigDecimal purchasePriceDinRmSpec;
	private BigDecimal transportCostRateRm;
	private BigDecimal transportCostKgRm;
	private BigDecimal transportCostRm;
	private BigDecimal transportCostDinRm;
	private BigDecimal customsBaseRm;
	private BigDecimal customsBaseDinRm;
	private BigDecimal customsRm;
	private BigDecimal customsDinRm;
	private BigDecimal forwarderRm;
	private BigDecimal forwarderDinRm;
	private BigDecimal importPriceRm;
	private BigDecimal importPriceDinRm;
	private BigDecimal bankingCostRateRm;
	private BigDecimal bankingCostRm;
	private BigDecimal bankingCostDinRm;
	private BigDecimal otherCostRateRm;
	private BigDecimal otherCostRm;
	private BigDecimal otherCostDinRm;
	private BigDecimal otherPurchaseCostRm;
	private BigDecimal totalPurchaseCostDinRm;
	private BigDecimal marginRateRm;
	private BigDecimal marginRateWholesaleRm;
	private BigDecimal marginRm;
	private BigDecimal marginDinRm;
	private BigDecimal incomeTaxRm;
	private BigDecimal incomeTaxDinRm;
	private BigDecimal netAmtRm;
	private BigDecimal netAmtRmSpec;
	private BigDecimal netAmtDinRm;
	private BigDecimal netAmtDinRmSpec;
	private BigDecimal vatRm;
	private BigDecimal vatDinRm;
	private BigDecimal grossAmtRm;
	private BigDecimal grossAmtRmSpec;
	private BigDecimal grossAmtDinRm;
	private BigDecimal grossAmtDinRmSpec;
	private BigDecimal discountRateFak;
	private BigDecimal purchasePriceFak;
	private BigDecimal purchasePriceFakSpec;
	private BigDecimal purchasePriceDinFak;
	private BigDecimal purchasePriceDinFakSpec;
	private BigDecimal transportCostRateFak;
	private BigDecimal transportCostFak;
	private BigDecimal transportCostDinFak;
	private BigDecimal customsBaseDinFak;
	private BigDecimal customsDinFak;
	private BigDecimal forwarderDinFak;
	private BigDecimal importPriceDinFak;
	private BigDecimal bankingCostDinFak;
	private BigDecimal otherCostDinFak;
	private BigDecimal totalPurchaseCostDinFak;
	private BigDecimal marginDinFak;
	private BigDecimal marginAdditionalRateDinFak;
	private BigDecimal marginAdditionalDinFak;
	private BigDecimal incomeTaxDinFak;
	private BigDecimal netAmtDinFak;
	private BigDecimal netAmtDinFakSpec;
	private BigDecimal vatDinFak;
	private BigDecimal grossPriceDinFak;
	private BigDecimal grossPriceDinFakSpec;
	private BigDecimal purchasePriceDinRmDis;
	private BigDecimal purchasePriceDinRmDisSpec;
	private BigDecimal transportCostDinRmDisSpec;
	private BigDecimal customsBaseDinRmDis;
	private BigDecimal customsDinRmDis;
	private BigDecimal forwarderDinRmDis;
	private BigDecimal importPriceDinRmDis;
	private BigDecimal bankingCostDinRmDis;
	private BigDecimal otherCostDinRmDis;
	private BigDecimal totalPurchaseCostDinDis;
	private BigDecimal marginRateRmDis;
	private BigDecimal marginDinRmDis;
	private BigDecimal incomeTaxDinRmDis;
	private BigDecimal netPriceDinRmDis;
	private BigDecimal netPriceDinRmDisSpec;
	private BigDecimal vatDinRmDis;
	private BigDecimal grossPriceDinRmDis;
	private BigDecimal grossPriceDinRmDisSpec;
	private Boolean isActive;
	private LocalDate createdDate;
	private Integer createdBy;

	public Calculation() {
	}

	public Calculation(Integer clientId, Integer clientCalculationId, String name, BigDecimal customsExchangeRate,
			BigDecimal buyingExchangeRate, BigDecimal sellingExchangeRate, BigDecimal discountExchangeRate,
			BigDecimal incomeTaxRate, BigDecimal vatRate, BigDecimal customsRate, BigDecimal forwarderRate,
			BigDecimal purchasePriceRm, BigDecimal purchasePriceDinRm,
			BigDecimal purchasePriceRmSpec, BigDecimal purchasePriceDinRmSpec, BigDecimal transportCostRateRm,
			BigDecimal transportCostKgRm, BigDecimal transportCostRm, BigDecimal transportCostDinRm,
			BigDecimal customsBaseRm, BigDecimal customsBaseDinRm, BigDecimal customsRm, BigDecimal customsDinRm,
			BigDecimal forwarderRm, BigDecimal forwarderDinRm, BigDecimal importPriceRm, BigDecimal importPriceDinRm,
			BigDecimal bankingCostRateRm, BigDecimal bankingCostRm, BigDecimal bankingCostDinRm,
			BigDecimal otherCostRateRm, BigDecimal otherCostRm, BigDecimal otherCostDinRm,
			BigDecimal otherPurchaseCostRm, BigDecimal totalPurchaseCostDinRm, BigDecimal marginRateRm,
			BigDecimal marginRateWholesaleRm, BigDecimal marginRm, BigDecimal marginDinRm, BigDecimal incomeTaxRm,
			BigDecimal incomeTaxDinRm, BigDecimal netAmtRm, BigDecimal netAmtRmSpec, BigDecimal netAmtDinRm,
			BigDecimal netAmtDinRmSpec, BigDecimal vatRm, BigDecimal vatDinRm, BigDecimal grossAmtRm,
			BigDecimal grossAmtRmSpec, BigDecimal grossAmtDinRm, BigDecimal grossAmtDinRmSpec,
			BigDecimal discountRateFak, BigDecimal purchasePriceFak, BigDecimal purchasePriceFakSpec,
			BigDecimal purchasePriceDinFak, BigDecimal purchasePriceDinFakSpec, BigDecimal transportCostRateFak,
			BigDecimal transportCostFak, BigDecimal transportCostDinFak, BigDecimal customsBaseDinFak,
			BigDecimal customsDinFak, BigDecimal forwarderDinFak, BigDecimal importPriceDinFak,
			BigDecimal bankingCostDinFak, BigDecimal otherCostDinFak, BigDecimal totalPurchaseCostDinFak,
			BigDecimal marginDinFak, BigDecimal marginAdditionalRateDinFak, BigDecimal marginAdditionalDinFak,
			BigDecimal incomeTaxDinFak, BigDecimal netAmtDinFak, BigDecimal netAmtDinFakSpec, BigDecimal vatDinFak,
			BigDecimal grossPriceDinFak, BigDecimal grossPriceDinFakSpec, BigDecimal purchasePriceDinRmDis,
			BigDecimal purchasePriceDinRmDisSpec, BigDecimal transportCostDinRmDisSpec, BigDecimal customsBaseDinRmDis,
			BigDecimal customsDinRmDis, BigDecimal forwarderDinRmDis, BigDecimal importPriceDinRmDis,
			BigDecimal bankingCostDinRmDis, BigDecimal otherCostDinRmDis, BigDecimal totalPurchaseCostDinDis,
			BigDecimal marginRateRmDis, BigDecimal marginDinRmDis, BigDecimal incomeTaxDinRmDis,
			BigDecimal netPriceDinRmDis, BigDecimal netPriceDinRmDisSpec, BigDecimal vatDinRmDis,
			BigDecimal grossPriceDinRmDis, BigDecimal grossPriceDinRmDisSpec, Boolean isActive, LocalDate createdDate,
			Integer createdBy) {
		this.clientId = clientId;
		this.clientCalculationId = clientCalculationId;
		this.name = name;
		this.customsExchangeRate = customsExchangeRate;
		this.buyingExchangeRate = buyingExchangeRate;
		this.sellingExchangeRate = sellingExchangeRate;
		this.discountExchangeRate = discountExchangeRate;
		this.incomeTaxRate = incomeTaxRate;
		this.vatRate = vatRate;
		this.customsRate = customsRate;
		this.forwarderRate = forwarderRate;
		this.purchasePriceRm = purchasePriceRm;
		this.purchasePriceDinRm = purchasePriceDinRm;
		this.purchasePriceRmSpec = purchasePriceRmSpec;
		this.purchasePriceDinRmSpec = purchasePriceDinRmSpec;
		this.transportCostRateRm = transportCostRateRm;
		this.transportCostKgRm = transportCostKgRm;
		this.transportCostRm = transportCostRm;
		this.transportCostDinRm = transportCostDinRm;
		this.customsBaseRm = customsBaseRm;
		this.customsBaseDinRm = customsBaseDinRm;
		this.customsRm = customsRm;
		this.customsDinRm = customsDinRm;
		this.forwarderRm = forwarderRm;
		this.forwarderDinRm = forwarderDinRm;
		this.importPriceRm = importPriceRm;
		this.importPriceDinRm = importPriceDinRm;
		this.bankingCostRateRm = bankingCostRateRm;
		this.bankingCostRm = bankingCostRm;
		this.bankingCostDinRm = bankingCostDinRm;
		this.otherCostRateRm = otherCostRateRm;
		this.otherCostRm = otherCostRm;
		this.otherCostDinRm = otherCostDinRm;
		this.otherPurchaseCostRm = otherPurchaseCostRm;
		this.totalPurchaseCostDinRm = totalPurchaseCostDinRm;
		this.marginRateRm = marginRateRm;
		this.marginRateWholesaleRm = marginRateWholesaleRm;
		this.marginRm = marginRm;
		this.marginDinRm = marginDinRm;
		this.incomeTaxRm = incomeTaxRm;
		this.incomeTaxDinRm = incomeTaxDinRm;
		this.netAmtRm = netAmtRm;
		this.netAmtRmSpec = netAmtRmSpec;
		this.netAmtDinRm = netAmtDinRm;
		this.netAmtDinRmSpec = netAmtDinRmSpec;
		this.vatRm = vatRm;
		this.vatDinRm = vatDinRm;
		this.grossAmtRm = grossAmtRm;
		this.grossAmtRmSpec = grossAmtRmSpec;
		this.grossAmtDinRm = grossAmtDinRm;
		this.grossAmtDinRmSpec = grossAmtDinRmSpec;
		this.discountRateFak = discountRateFak;
		this.purchasePriceFak = purchasePriceFak;
		this.purchasePriceFakSpec = purchasePriceFakSpec;
		this.purchasePriceDinFak = purchasePriceDinFak;
		this.purchasePriceDinFakSpec = purchasePriceDinFakSpec;
		this.transportCostRateFak = transportCostRateFak;
		this.transportCostFak = transportCostFak;
		this.transportCostDinFak = transportCostDinFak;
		this.customsBaseDinFak = customsBaseDinFak;
		this.customsDinFak = customsDinFak;
		this.forwarderDinFak = forwarderDinFak;
		this.importPriceDinFak = importPriceDinFak;
		this.bankingCostDinFak = bankingCostDinFak;
		this.otherCostDinFak = otherCostDinFak;
		this.totalPurchaseCostDinFak = totalPurchaseCostDinFak;
		this.marginDinFak = marginDinFak;
		this.marginAdditionalRateDinFak = marginAdditionalRateDinFak;
		this.marginAdditionalDinFak = marginAdditionalDinFak;
		this.incomeTaxDinFak = incomeTaxDinFak;
		this.netAmtDinFak = netAmtDinFak;
		this.netAmtDinFakSpec = netAmtDinFakSpec;
		this.vatDinFak = vatDinFak;
		this.grossPriceDinFak = grossPriceDinFak;
		this.grossPriceDinFakSpec = grossPriceDinFakSpec;
		this.purchasePriceDinRmDis = purchasePriceDinRmDis;
		this.purchasePriceDinRmDisSpec = purchasePriceDinRmDisSpec;
		this.transportCostDinRmDisSpec = transportCostDinRmDisSpec;
		this.customsBaseDinRmDis = customsBaseDinRmDis;
		this.customsDinRmDis = customsDinRmDis;
		this.forwarderDinRmDis = forwarderDinRmDis;
		this.importPriceDinRmDis = importPriceDinRmDis;
		this.bankingCostDinRmDis = bankingCostDinRmDis;
		this.otherCostDinRmDis = otherCostDinRmDis;
		this.totalPurchaseCostDinDis = totalPurchaseCostDinDis;
		this.marginRateRmDis = marginRateRmDis;
		this.marginDinRmDis = marginDinRmDis;
		this.incomeTaxDinRmDis = incomeTaxDinRmDis;
		this.netPriceDinRmDis = netPriceDinRmDis;
		this.netPriceDinRmDisSpec = netPriceDinRmDisSpec;
		this.vatDinRmDis = vatDinRmDis;
		this.grossPriceDinRmDis = grossPriceDinRmDis;
		this.grossPriceDinRmDisSpec = grossPriceDinRmDisSpec;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
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

	@Column(name = "CLIENT_CALCULATION_ID")
	public Integer getClientCalculationId() {
		return this.clientCalculationId;
	}

	public void setClientCalculationId(Integer clientCalculationId) {
		this.clientCalculationId = clientCalculationId;
	}

	@Column(name = "NAME", length = 1000)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CUSTOMS_EXCHANGE_RATE", precision = 18, scale = 4)
	public BigDecimal getCustomsExchangeRate() {
		return this.customsExchangeRate == null ? BigDecimal.ZERO : this.customsExchangeRate;
	}

	public void setCustomsExchangeRate(BigDecimal customsExchangeRate) {
		this.customsExchangeRate = customsExchangeRate;
	}

	@Column(name = "BUYING_EXCHANGE_RATE", precision = 18, scale = 4)
	public BigDecimal getBuyingExchangeRate() {
		return this.buyingExchangeRate == null ? BigDecimal.ZERO : this.buyingExchangeRate;
	}

	public void setBuyingExchangeRate(BigDecimal buyingExchangeRate) {
		this.buyingExchangeRate = buyingExchangeRate;
	}

	@Column(name = "SELLING_EXCHANGE_RATE", precision = 18, scale = 4)
	public BigDecimal getSellingExchangeRate() {
		return this.sellingExchangeRate == null ? BigDecimal.ZERO : this.sellingExchangeRate;
	}

	public void setSellingExchangeRate(BigDecimal sellingExchangeRate) {
		this.sellingExchangeRate = sellingExchangeRate;
	}

	@Column(name = "DISCOUNT_EXCHANGE_RATE", precision = 18, scale = 4)
	public BigDecimal getDiscountExchangeRate() {
		return this.discountExchangeRate == null ? BigDecimal.ZERO : this.discountExchangeRate;
	}

	public void setDiscountExchangeRate(BigDecimal discountExchangeRate) {
		this.discountExchangeRate = discountExchangeRate;
	}

	@Column(name = "INCOME_TAX_RATE", precision = 18, scale = 4)
	public BigDecimal getIncomeTaxRate() {
		return this.incomeTaxRate == null ? BigDecimal.ZERO : this.incomeTaxRate;
	}

	public void setIncomeTaxRate(BigDecimal incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}

	@Column(name = "VAT_RATE", precision = 18, scale = 4)
	public BigDecimal getVatRate() {
		return this.vatRate == null ? BigDecimal.ZERO : this.vatRate;
	}

	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	@Column(name = "CUSTOMS_RATE", precision = 18, scale = 4)
	public BigDecimal getCustomsRate() {
		return this.customsRate == null ? BigDecimal.ZERO : this.customsRate;
	}

	public void setCustomsRate(BigDecimal customsRate) {
		this.customsRate = customsRate;
	}

	@Column(name = "FORWARDER_RATE", precision = 18, scale = 4)
	public BigDecimal getForwarderRate() {
		return this.forwarderRate == null ? BigDecimal.ZERO : this.forwarderRate;
	}

	public void setForwarderRate(BigDecimal forwarderRate) {
		this.forwarderRate = forwarderRate;
	}

	@Column(name = "PURCHASE_PRICE_RM", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceRm() {
		return this.purchasePriceRm == null ? BigDecimal.ZERO : this.purchasePriceRm;
	}

	public void setPurchasePriceRm(BigDecimal purchasePriceRm) {
		this.purchasePriceRm = purchasePriceRm;
	}

	@Column(name = "PURCHASE_PRICE_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceDinRm() {
		return this.purchasePriceDinRm == null ? BigDecimal.ZERO : this.purchasePriceDinRm;
	}

	public void setPurchasePriceDinRm(BigDecimal purchasePriceDinRm) {
		this.purchasePriceDinRm = purchasePriceDinRm;
	}

	@Column(name = "PURCHASE_PRICE_RM_SPEC", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceRmSpec() {
		return this.purchasePriceRmSpec == null ? BigDecimal.ZERO : this.purchasePriceRmSpec;
	}

	public void setPurchasePriceRmSpec(BigDecimal purchasePriceRmSpec) {
		this.purchasePriceRmSpec = purchasePriceRmSpec;
	}

	@Column(name = "PURCHASE_PRICE_DIN_RM_SPEC", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceDinRmSpec() {
		return this.purchasePriceDinRmSpec == null ? BigDecimal.ZERO : this.purchasePriceDinRmSpec;
	}

	public void setPurchasePriceDinRmSpec(BigDecimal purchasePriceDinRmSpec) {
		this.purchasePriceDinRmSpec = purchasePriceDinRmSpec;
	}

	@Column(name = "TRANSPORT_COST_RATE_RM", precision = 18, scale = 4)
	public BigDecimal getTransportCostRateRm() {
		return this.transportCostRateRm == null ? BigDecimal.ZERO : this.transportCostRateRm;
	}

	public void setTransportCostRateRm(BigDecimal transportCostRateRm) {
		this.transportCostRateRm = transportCostRateRm;
	}

	@Column(name = "TRANSPORT_COST_KG_RM", precision = 18, scale = 4)
	public BigDecimal getTransportCostKgRm() {
		return this.transportCostKgRm == null ? BigDecimal.ZERO : this.transportCostKgRm;
	}

	public void setTransportCostKgRm(BigDecimal transportCostKgRm) {
		this.transportCostKgRm = transportCostKgRm;
	}

	@Column(name = "TRANSPORT_COST_RM", precision = 18, scale = 4)
	public BigDecimal getTransportCostRm() {
		return this.transportCostRm == null ? BigDecimal.ZERO : this.transportCostRm;
	}

	public void setTransportCostRm(BigDecimal transportCostRm) {
		this.transportCostRm = transportCostRm;
	}

	@Column(name = "TRANSPORT_COST_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getTransportCostDinRm() {
		return this.transportCostDinRm == null ? BigDecimal.ZERO : this.transportCostDinRm;
	}

	public void setTransportCostDinRm(BigDecimal transportCostDinRm) {
		this.transportCostDinRm = transportCostDinRm;
	}

	@Column(name = "CUSTOMS_BASE_RM", precision = 18, scale = 4)
	public BigDecimal getCustomsBaseRm() {
		return this.customsBaseRm == null ? BigDecimal.ZERO : this.customsBaseRm;
	}

	public void setCustomsBaseRm(BigDecimal customsBaseRm) {
		this.customsBaseRm = customsBaseRm;
	}

	@Column(name = "CUSTOMS_BASE_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getCustomsBaseDinRm() {
		return this.customsBaseDinRm == null ? BigDecimal.ZERO : this.customsBaseDinRm;
	}

	public void setCustomsBaseDinRm(BigDecimal customsBaseDinRm) {
		this.customsBaseDinRm = customsBaseDinRm;
	}

	@Column(name = "CUSTOMS_RM", precision = 18, scale = 4)
	public BigDecimal getCustomsRm() {
		return this.customsRm == null ? BigDecimal.ZERO : this.customsRm;
	}

	public void setCustomsRm(BigDecimal customsRm) {
		this.customsRm = customsRm;
	}

	@Column(name = "CUSTOMS_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getCustomsDinRm() {
		return this.customsDinRm == null ? BigDecimal.ZERO : this.customsDinRm;
	}

	public void setCustomsDinRm(BigDecimal customsDinRm) {
		this.customsDinRm = customsDinRm;
	}

	@Column(name = "FORWARDER_RM", precision = 18, scale = 4)
	public BigDecimal getForwarderRm() {
		return this.forwarderRm == null ? BigDecimal.ZERO : this.forwarderRm;
	}

	public void setForwarderRm(BigDecimal forwarderRm) {
		this.forwarderRm = forwarderRm;
	}

	@Column(name = "FORWARDER_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getForwarderDinRm() {
		return this.forwarderDinRm == null ? BigDecimal.ZERO : this.forwarderDinRm;
	}

	public void setForwarderDinRm(BigDecimal forwarderDinRm) {
		this.forwarderDinRm = forwarderDinRm;
	}

	@Column(name = "IMPORT_PRICE_RM", precision = 18, scale = 4)
	public BigDecimal getImportPriceRm() {
		return this.importPriceRm == null ? BigDecimal.ZERO : this.importPriceRm;
	}

	public void setImportPriceRm(BigDecimal importPriceRm) {
		this.importPriceRm = importPriceRm;
	}

	@Column(name = "IMPORT_PRICE_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getImportPriceDinRm() {
		return this.importPriceDinRm == null ? BigDecimal.ZERO : this.importPriceDinRm;
	}

	public void setImportPriceDinRm(BigDecimal importPriceDinRm) {
		this.importPriceDinRm = importPriceDinRm;
	}

	@Column(name = "BANKING_COST_RATE_RM", precision = 18, scale = 4)
	public BigDecimal getBankingCostRateRm() {
		return this.bankingCostRateRm == null ? BigDecimal.ZERO : this.bankingCostRateRm;
	}

	public void setBankingCostRateRm(BigDecimal bankingCostRateRm) {
		this.bankingCostRateRm = bankingCostRateRm;
	}

	@Column(name = "BANKING_COST_RM", precision = 18, scale = 4)
	public BigDecimal getBankingCostRm() {
		return this.bankingCostRm == null ? BigDecimal.ZERO : this.bankingCostRm;
	}

	public void setBankingCostRm(BigDecimal bankingCostRm) {
		this.bankingCostRm = bankingCostRm;
	}

	@Column(name = "BANKING_COST_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getBankingCostDinRm() {
		return this.bankingCostDinRm == null ? BigDecimal.ZERO : this.bankingCostDinRm;
	}

	public void setBankingCostDinRm(BigDecimal bankingCostDinRm) {
		this.bankingCostDinRm = bankingCostDinRm;
	}

	@Column(name = "OTHER_COST_RATE_RM", precision = 18, scale = 4)
	public BigDecimal getOtherCostRateRm() {
		return this.otherCostRateRm == null ? BigDecimal.ZERO : this.otherCostRateRm;
	}

	public void setOtherCostRateRm(BigDecimal otherCostRateRm) {
		this.otherCostRateRm = otherCostRateRm;
	}

	@Column(name = "OTHER_COST_RM", precision = 18, scale = 4)
	public BigDecimal getOtherCostRm() {
		return this.otherCostRm == null ? BigDecimal.ZERO : this.otherCostRm;
	}

	public void setOtherCostRm(BigDecimal otherCostRm) {
		this.otherCostRm = otherCostRm;
	}

	@Column(name = "OTHER_COST_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getOtherCostDinRm() {
		return this.otherCostDinRm == null ? BigDecimal.ZERO : this.otherCostDinRm;
	}

	public void setOtherCostDinRm(BigDecimal otherCostDinRm) {
		this.otherCostDinRm = otherCostDinRm;
	}

	@Column(name = "OTHER_PURCHASE_COST_RM", precision = 18, scale = 4)
	public BigDecimal getOtherPurchaseCostRm() {
		return this.otherPurchaseCostRm == null ? BigDecimal.ZERO : this.otherPurchaseCostRm;
	}

	public void setOtherPurchaseCostRm(BigDecimal otherPurchaseCostRm) {
		this.otherPurchaseCostRm = otherPurchaseCostRm;
	}

	@Column(name = "TOTAL_PURCHASE_COST_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getTotalPurchaseCostDinRm() {
		return this.totalPurchaseCostDinRm == null ? BigDecimal.ZERO : this.totalPurchaseCostDinRm;
	}

	public void setTotalPurchaseCostDinRm(BigDecimal totalPurchaseCostDinRm) {
		this.totalPurchaseCostDinRm = totalPurchaseCostDinRm;
	}

	@Column(name = "MARGIN_RATE_RM", precision = 18, scale = 4)
	public BigDecimal getMarginRateRm() {
		return this.marginRateRm == null ? BigDecimal.ZERO : this.marginRateRm;
	}

	public void setMarginRateRm(BigDecimal marginRateRm) {
		this.marginRateRm = marginRateRm;
	}

	@Column(name = "MARGIN_RATE_WHOLESALE_RM", precision = 18, scale = 4)
	public BigDecimal getMarginRateWholesaleRm() {
		return this.marginRateWholesaleRm == null ? BigDecimal.ZERO : this.marginRateWholesaleRm;
	}

	public void setMarginRateWholesaleRm(BigDecimal marginRateWholesaleRm) {
		this.marginRateWholesaleRm = marginRateWholesaleRm;
	}

	@Column(name = "MARGIN_RM", precision = 18, scale = 4)
	public BigDecimal getMarginRm() {
		return this.marginRm == null ? BigDecimal.ZERO : this.marginRm;
	}

	public void setMarginRm(BigDecimal marginRm) {
		this.marginRm = marginRm;
	}

	@Column(name = "MARGIN_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getMarginDinRm() {
		return this.marginDinRm == null ? BigDecimal.ZERO : this.marginDinRm;
	}

	public void setMarginDinRm(BigDecimal marginDinRm) {
		this.marginDinRm = marginDinRm;
	}

	@Column(name = "INCOME_TAX_RM", precision = 18, scale = 4)
	public BigDecimal getIncomeTaxRm() {
		return this.incomeTaxRm == null ? BigDecimal.ZERO : this.incomeTaxRm;
	}

	public void setIncomeTaxRm(BigDecimal incomeTaxRm) {
		this.incomeTaxRm = incomeTaxRm;
	}

	@Column(name = "INCOME_TAX_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getIncomeTaxDinRm() {
		return this.incomeTaxDinRm == null ? BigDecimal.ZERO : this.incomeTaxDinRm;
	}

	public void setIncomeTaxDinRm(BigDecimal incomeTaxDinRm) {
		this.incomeTaxDinRm = incomeTaxDinRm;
	}

	@Column(name = "NET_AMT_RM", precision = 18, scale = 4)
	public BigDecimal getNetAmtRm() {
		return this.netAmtRm == null ? BigDecimal.ZERO : this.netAmtRm;
	}

	public void setNetAmtRm(BigDecimal netAmtRm) {
		this.netAmtRm = netAmtRm;
	}

	@Column(name = "NET_AMT_RM_SPEC", precision = 18, scale = 4)
	public BigDecimal getNetAmtRmSpec() {
		return this.netAmtRmSpec == null ? BigDecimal.ZERO : this.netAmtRmSpec;
	}

	public void setNetAmtRmSpec(BigDecimal netAmtRmSpec) {
		this.netAmtRmSpec = netAmtRmSpec;
	}

	@Column(name = "NET_AMT__DIN_RM", precision = 18, scale = 4)
	public BigDecimal getNetAmtDinRm() {
		return this.netAmtDinRm == null ? BigDecimal.ZERO : this.netAmtDinRm;
	}

	public void setNetAmtDinRm(BigDecimal netAmtDinRm) {
		this.netAmtDinRm = netAmtDinRm;
	}

	@Column(name = "NET_AMT__DIN_RM_SPEC", precision = 18, scale = 4)
	public BigDecimal getNetAmtDinRmSpec() {
		return this.netAmtDinRmSpec == null ? BigDecimal.ZERO : this.netAmtDinRmSpec;
	}

	public void setNetAmtDinRmSpec(BigDecimal netAmtDinRmSpec) {
		this.netAmtDinRmSpec = netAmtDinRmSpec;
	}

	@Column(name = "VAT_RM", precision = 18, scale = 4)
	public BigDecimal getVatRm() {
		return this.vatRm == null ? BigDecimal.ZERO : this.vatRm;
	}

	public void setVatRm(BigDecimal vatRm) {
		this.vatRm = vatRm;
	}

	@Column(name = "VAT_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getVatDinRm() {
		return this.vatDinRm == null ? BigDecimal.ZERO : this.vatDinRm;
	}

	public void setVatDinRm(BigDecimal vatDinRm) {
		this.vatDinRm = vatDinRm;
	}

	@Column(name = "GROSS_AMT_RM", precision = 18, scale = 4)
	public BigDecimal getGrossAmtRm() {
		return this.grossAmtRm == null ? BigDecimal.ZERO : this.grossAmtRm;
	}

	public void setGrossAmtRm(BigDecimal grossAmtRm) {
		this.grossAmtRm = grossAmtRm;
	}

	@Column(name = "GROSS_AMT_RM_SPEC", precision = 18, scale = 4)
	public BigDecimal getGrossAmtRmSpec() {
		return this.grossAmtRmSpec == null ? BigDecimal.ZERO : this.grossAmtRmSpec;
	}

	public void setGrossAmtRmSpec(BigDecimal grossAmtRmSpec) {
		this.grossAmtRmSpec = grossAmtRmSpec;
	}

	@Column(name = "GROSS_AMT_DIN_RM", precision = 18, scale = 4)
	public BigDecimal getGrossAmtDinRm() {
		return this.grossAmtDinRm == null ? BigDecimal.ZERO : this.grossAmtDinRm;
	}

	public void setGrossAmtDinRm(BigDecimal grossAmtDinRm) {
		this.grossAmtDinRm = grossAmtDinRm;
	}

	@Column(name = "GROSS_AMT_DIN_RM_SPEC", precision = 18, scale = 4)
	public BigDecimal getGrossAmtDinRmSpec() {
		return this.grossAmtDinRmSpec == null ? BigDecimal.ZERO : this.grossAmtDinRmSpec;
	}

	public void setGrossAmtDinRmSpec(BigDecimal grossAmtDinRmSpec) {
		this.grossAmtDinRmSpec = grossAmtDinRmSpec;
	}

	@Column(name = "DISCOUNT_RATE_FAK", precision = 18, scale = 4)
	public BigDecimal getDiscountRateFak() {
		return this.discountRateFak == null ? BigDecimal.ZERO : this.discountRateFak;
	}

	public void setDiscountRateFak(BigDecimal discountRateFak) {
		this.discountRateFak = discountRateFak;
	}

	@Column(name = "PURCHASE_PRICE_FAK", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceFak() {
		return this.purchasePriceFak == null ? BigDecimal.ZERO : this.purchasePriceFak;
	}

	public void setPurchasePriceFak(BigDecimal purchasePriceFak) {
		this.purchasePriceFak = purchasePriceFak;
	}

	@Column(name = "PURCHASE_PRICE_FAK_SPEC", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceFakSpec() {
		return this.purchasePriceFakSpec == null ? BigDecimal.ZERO : this.purchasePriceFakSpec;
	}

	public void setPurchasePriceFakSpec(BigDecimal purchasePriceFakSpec) {
		this.purchasePriceFakSpec = purchasePriceFakSpec;
	}

	@Column(name = "PURCHASE_PRICE_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceDinFak() {
		return this.purchasePriceDinFak == null ? BigDecimal.ZERO : this.purchasePriceDinFak;
	}

	public void setPurchasePriceDinFak(BigDecimal purchasePriceDinFak) {
		this.purchasePriceDinFak = purchasePriceDinFak;
	}

	@Column(name = "PURCHASE_PRICE_DIN_FAK_SPEC", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceDinFakSpec() {
		return this.purchasePriceDinFakSpec == null ? BigDecimal.ZERO : this.purchasePriceDinFakSpec;
	}

	public void setPurchasePriceDinFakSpec(BigDecimal purchasePriceDinFakSpec) {
		this.purchasePriceDinFakSpec = purchasePriceDinFakSpec;
	}

	@Column(name = "TRANSPORT_COST_RATE_FAK", precision = 18, scale = 4)
	public BigDecimal getTransportCostRateFak() {
		return this.transportCostRateFak == null ? BigDecimal.ZERO : this.transportCostRateFak;
	}

	public void setTransportCostRateFak(BigDecimal transportCostRateFak) {
		this.transportCostRateFak = transportCostRateFak;
	}

	@Column(name = "TRANSPORT_COST_FAK", precision = 18, scale = 4)
	public BigDecimal getTransportCostFak() {
		return this.transportCostFak == null ? BigDecimal.ZERO : this.transportCostFak;
	}

	public void setTransportCostFak(BigDecimal transportCostFak) {
		this.transportCostFak = transportCostFak;
	}

	@Column(name = "TRANSPORT_COST_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getTransportCostDinFak() {
		return this.transportCostDinFak == null ? BigDecimal.ZERO : this.transportCostDinFak;
	}

	public void setTransportCostDinFak(BigDecimal transportCostDinFak) {
		this.transportCostDinFak = transportCostDinFak;
	}

	@Column(name = "CUSTOMS_BASE_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getCustomsBaseDinFak() {
		return this.customsBaseDinFak == null ? BigDecimal.ZERO : this.customsBaseDinFak;
	}

	public void setCustomsBaseDinFak(BigDecimal customsBaseDinFak) {
		this.customsBaseDinFak = customsBaseDinFak;
	}

	@Column(name = "CUSTOMS_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getCustomsDinFak() {
		return this.customsDinFak == null ? BigDecimal.ZERO : this.customsDinFak;
	}

	public void setCustomsDinFak(BigDecimal customsDinFak) {
		this.customsDinFak = customsDinFak;
	}

	@Column(name = "FORWARDER_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getForwarderDinFak() {
		return this.forwarderDinFak == null ? BigDecimal.ZERO : this.forwarderDinFak;
	}

	public void setForwarderDinFak(BigDecimal forwarderDinFak) {
		this.forwarderDinFak = forwarderDinFak;
	}

	@Column(name = "IMPORT_PRICE_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getImportPriceDinFak() {
		return this.importPriceDinFak == null ? BigDecimal.ZERO : this.importPriceDinFak;
	}

	public void setImportPriceDinFak(BigDecimal importPriceDinFak) {
		this.importPriceDinFak = importPriceDinFak;
	}

	@Column(name = "BANKING_COST_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getBankingCostDinFak() {
		return this.bankingCostDinFak == null ? BigDecimal.ZERO : this.bankingCostDinFak;
	}

	public void setBankingCostDinFak(BigDecimal bankingCostDinFak) {
		this.bankingCostDinFak = bankingCostDinFak;
	}

	@Column(name = "OTHER_COST_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getOtherCostDinFak() {
		return this.otherCostDinFak == null ? BigDecimal.ZERO : this.otherCostDinFak;
	}

	public void setOtherCostDinFak(BigDecimal otherCostDinFak) {
		this.otherCostDinFak = otherCostDinFak;
	}

	@Column(name = "TOTAL_PURCHASE_COST_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getTotalPurchaseCostDinFak() {
		return this.totalPurchaseCostDinFak == null ? BigDecimal.ZERO : this.totalPurchaseCostDinFak;
	}

	public void setTotalPurchaseCostDinFak(BigDecimal totalPurchaseCostDinFak) {
		this.totalPurchaseCostDinFak = totalPurchaseCostDinFak;
	}

	@Column(name = "MARGIN_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getMarginDinFak() {
		return this.marginDinFak == null ? BigDecimal.ZERO : this.marginDinFak;
	}

	public void setMarginDinFak(BigDecimal marginDinFak) {
		this.marginDinFak = marginDinFak;
	}

	@Column(name = "MARGIN_ADDITIONAL_RATE_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getMarginAdditionalRateDinFak() {
		return this.marginAdditionalRateDinFak == null ? BigDecimal.ZERO : this.marginAdditionalRateDinFak;
	}

	public void setMarginAdditionalRateDinFak(BigDecimal marginAdditionalRateDinFak) {
		this.marginAdditionalRateDinFak = marginAdditionalRateDinFak;
	}

	@Column(name = "MARGIN_ADDITIONAL_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getMarginAdditionalDinFak() {
		return this.marginAdditionalDinFak == null ? BigDecimal.ZERO : this.marginAdditionalDinFak;
	}

	public void setMarginAdditionalDinFak(BigDecimal marginAdditionalDinFak) {
		this.marginAdditionalDinFak = marginAdditionalDinFak;
	}

	@Column(name = "INCOME_TAX_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getIncomeTaxDinFak() {
		return this.incomeTaxDinFak == null ? BigDecimal.ZERO : this.incomeTaxDinFak;
	}

	public void setIncomeTaxDinFak(BigDecimal incomeTaxDinFak) {
		this.incomeTaxDinFak = incomeTaxDinFak;
	}

	@Column(name = "NET_AMT_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getNetAmtDinFak() {
		return this.netAmtDinFak == null ? BigDecimal.ZERO : this.netAmtDinFak;
	}

	public void setNetAmtDinFak(BigDecimal netAmtDinFak) {
		this.netAmtDinFak = netAmtDinFak;
	}

	@Column(name = "NET_AMT_DIN_FAK_SPEC", precision = 18, scale = 4)
	public BigDecimal getNetAmtDinFakSpec() {
		return this.netAmtDinFakSpec == null ? BigDecimal.ZERO : this.netAmtDinFakSpec;
	}

	public void setNetAmtDinFakSpec(BigDecimal netAmtDinFakSpec) {
		this.netAmtDinFakSpec = netAmtDinFakSpec;
	}

	@Column(name = "VAT_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getVatDinFak() {
		return this.vatDinFak == null ? BigDecimal.ZERO : this.vatDinFak;
	}

	public void setVatDinFak(BigDecimal vatDinFak) {
		this.vatDinFak = vatDinFak;
	}

	@Column(name = "GROSS_PRICE_DIN_FAK", precision = 18, scale = 4)
	public BigDecimal getGrossPriceDinFak() {
		return this.grossPriceDinFak == null ? BigDecimal.ZERO : this.grossPriceDinFak;
	}

	public void setGrossPriceDinFak(BigDecimal grossPriceDinFak) {
		this.grossPriceDinFak = grossPriceDinFak;
	}

	@Column(name = "GROSS_PRICE_DIN_FAK_SPEC", precision = 18, scale = 4)
	public BigDecimal getGrossPriceDinFakSpec() {
		return this.grossPriceDinFakSpec == null ? BigDecimal.ZERO : this.grossPriceDinFakSpec;
	}

	public void setGrossPriceDinFakSpec(BigDecimal grossPriceDinFakSpec) {
		this.grossPriceDinFakSpec = grossPriceDinFakSpec;
	}

	@Column(name = "PURCHASE_PRICE_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceDinRmDis() {
		return this.purchasePriceDinRmDis == null ? BigDecimal.ZERO : this.purchasePriceDinRmDis;
	}

	public void setPurchasePriceDinRmDis(BigDecimal purchasePriceDinRmDis) {
		this.purchasePriceDinRmDis = purchasePriceDinRmDis;
	}

	@Column(name = "PURCHASE_PRICE_DIN_RM_DIS_SPEC", precision = 18, scale = 4)
	public BigDecimal getPurchasePriceDinRmDisSpec() {
		return this.purchasePriceDinRmDisSpec == null ? BigDecimal.ZERO : this.purchasePriceDinRmDisSpec;
	}

	public void setPurchasePriceDinRmDisSpec(BigDecimal purchasePriceDinRmDisSpec) {
		this.purchasePriceDinRmDisSpec = purchasePriceDinRmDisSpec;
	}

	@Column(name = "TRANSPORT_COST_DIN_RM_DIS_SPEC", precision = 18, scale = 4)
	public BigDecimal getTransportCostDinRmDisSpec() {
		return this.transportCostDinRmDisSpec == null ? BigDecimal.ZERO : this.purchasePriceDinRmDisSpec;
	}

	public void setTransportCostDinRmDisSpec(BigDecimal transportCostDinRmDisSpec) {
		this.transportCostDinRmDisSpec = transportCostDinRmDisSpec;
	}

	@Column(name = "CUSTOMS_BASE_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getCustomsBaseDinRmDis() {
		return this.customsBaseDinRmDis == null ? BigDecimal.ZERO : this.customsBaseDinRmDis;
	}

	public void setCustomsBaseDinRmDis(BigDecimal customsBaseDinRmDis) {
		this.customsBaseDinRmDis = customsBaseDinRmDis;
	}

	@Column(name = "CUSTOMS_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getCustomsDinRmDis() {
		return this.customsDinRmDis == null ? BigDecimal.ZERO : this.customsDinRmDis;
	}

	public void setCustomsDinRmDis(BigDecimal customsDinRmDis) {
		this.customsDinRmDis = customsDinRmDis;
	}

	@Column(name = "FORWARDER_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getForwarderDinRmDis() {
		return this.forwarderDinRmDis == null ? BigDecimal.ZERO : this.forwarderDinRmDis;
	}

	public void setForwarderDinRmDis(BigDecimal forwarderDinRmDis) {
		this.forwarderDinRmDis = forwarderDinRmDis;
	}

	@Column(name = "IMPORT_PRICE_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getImportPriceDinRmDis() {
		return this.importPriceDinRmDis == null ? BigDecimal.ZERO : this.importPriceDinRmDis;
	}

	public void setImportPriceDinRmDis(BigDecimal importPriceDinRmDis) {
		this.importPriceDinRmDis = importPriceDinRmDis;
	}

	@Column(name = "BANKING_COST_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getBankingCostDinRmDis() {
		return this.bankingCostDinRmDis == null ? BigDecimal.ZERO : this.bankingCostDinRmDis;
	}

	public void setBankingCostDinRmDis(BigDecimal bankingCostDinRmDis) {
		this.bankingCostDinRmDis = bankingCostDinRmDis;
	}

	@Column(name = "OTHER_COST_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getOtherCostDinRmDis() {
		return this.otherCostDinRmDis == null ? BigDecimal.ZERO : this.otherCostDinRmDis;
	}

	public void setOtherCostDinRmDis(BigDecimal otherCostDinRmDis) {
		this.otherCostDinRmDis = otherCostDinRmDis;
	}

	@Column(name = "TOTAL_PURCHASE_COST_DIN_DIS", precision = 18, scale = 4)
	public BigDecimal getTotalPurchaseCostDinDis() {
		return this.totalPurchaseCostDinDis == null ? BigDecimal.ZERO : this.totalPurchaseCostDinDis;
	}

	public void setTotalPurchaseCostDinDis(BigDecimal totalPurchaseCostDinDis) {
		this.totalPurchaseCostDinDis = totalPurchaseCostDinDis;
	}

	@Column(name = "MARGIN_RATE_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getMarginRateRmDis() {
		return this.marginRateRmDis == null ? BigDecimal.ZERO : this.marginRateRmDis;
	}

	public void setMarginRateRmDis(BigDecimal marginRateRmDis) {
		this.marginRateRmDis = marginRateRmDis;
	}

	@Column(name = "MARGIN_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getMarginDinRmDis() {
		return this.marginDinRmDis == null ? BigDecimal.ZERO : this.marginDinRmDis;
	}

	public void setMarginDinRmDis(BigDecimal marginDinRmDis) {
		this.marginDinRmDis = marginDinRmDis;
	}

	@Column(name = "INCOME_TAX_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getIncomeTaxDinRmDis() {
		return this.incomeTaxDinRmDis == null ? BigDecimal.ZERO : this.incomeTaxDinRmDis;
	}

	public void setIncomeTaxDinRmDis(BigDecimal incomeTaxDinRmDis) {
		this.incomeTaxDinRmDis = incomeTaxDinRmDis;
	}

	@Column(name = "NET_PRICE_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getNetPriceDinRmDis() {
		return this.netPriceDinRmDis == null ? BigDecimal.ZERO : this.netPriceDinRmDis;
	}

	public void setNetPriceDinRmDis(BigDecimal netPriceDinRmDis) {
		this.netPriceDinRmDis = netPriceDinRmDis;
	}

	@Column(name = "NET_PRICE_DIN_RM_DIS_SPEC", precision = 18, scale = 4)
	public BigDecimal getNetPriceDinRmDisSpec() {
		return this.netPriceDinRmDisSpec == null ? BigDecimal.ZERO : this.netPriceDinRmDisSpec;
	}

	public void setNetPriceDinRmDisSpec(BigDecimal netPriceDinRmDisSpec) {
		this.netPriceDinRmDisSpec = netPriceDinRmDisSpec;
	}

	@Column(name = "VAT_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getVatDinRmDis() {
		return this.vatDinRmDis == null ? BigDecimal.ZERO : this.vatDinRmDis;
	}

	public void setVatDinRmDis(BigDecimal vatDinRmDis) {
		this.vatDinRmDis = vatDinRmDis;
	}

	@Column(name = "GROSS_PRICE_DIN_RM_DIS", precision = 18, scale = 4)
	public BigDecimal getGrossPriceDinRmDis() {
		return this.grossPriceDinRmDis == null ? BigDecimal.ZERO : this.grossPriceDinRmDis;
	}

	public void setGrossPriceDinRmDis(BigDecimal grossPriceDinRmDis) {
		this.grossPriceDinRmDis = grossPriceDinRmDis;
	}

	@Column(name = "GROSS_PRICE_DIN_RM_DIS_SPEC", precision = 18, scale = 4)
	public BigDecimal getGrossPriceDinRmDisSpec() {
		return this.grossPriceDinRmDisSpec == null ? BigDecimal.ZERO : this.grossPriceDinRmDisSpec;
	}

	public void setGrossPriceDinRmDisSpec(BigDecimal grossPriceDinRmDisSpec) {
		this.grossPriceDinRmDisSpec = grossPriceDinRmDisSpec;
	}

	@Column(name = "IS_ACTIVE")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", length = 26)
	public LocalDate getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

}
