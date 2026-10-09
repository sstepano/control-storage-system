package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM", schema = "PUBLIC", catalog = "RM")
public class Item implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private String code;
	private Integer groupId;
	private Integer subgroupId;
	private Integer typeId;
	private Integer statusId;
	private String name;
	private String nameEng;
	private Integer brandId;
	private String composition;
	private String commercial;
	private String purpose;
	private String technicalCharacteristics;
	private BigDecimal grossPriceRmD;
	private BigDecimal grossPriceRmDSpec;
	private BigDecimal netPriceRmD;
	private BigDecimal netPriceRmDSpec;
	private BigDecimal grossPriceD;
	private BigDecimal grossPriceDSpec;
	private BigDecimal netPriceDFak;
	private BigDecimal netPriceDFakSpec;
	private BigDecimal grossPriceDFak;
	private BigDecimal grossPriceDFakSpec;
	private Integer tariffGroupId;
	private Integer minQty;
	private Integer countryOfOriginId;
	private Integer currencyId;
	private Integer packageTypeId;
	private Integer packageQty;
	private Integer measurementUnitId;
	private BigDecimal weight;
	private BigDecimal volume;
	private Integer masterboxUnitQty;
	private BigDecimal masterboxUnitVolume;
	private Integer cardboardUnitQty;
	private BigDecimal paletteUnitQty;
	private String tariffNumberEu;
	private String tariffNumberUs;
	private BigDecimal container20inchQty;
	private Integer qtyForFree;
	private Integer freeQty;
	private BigDecimal purchasePrice;
	private BigDecimal purchasePriceSpec;
	private BigDecimal priceCorrectionRate;
	private LocalDate expirationDate;
	private Integer internetCatalogId;
	private String imagePath;
	private Integer oldid;
	private List<ItemBarcode> itemBarcode;

	public Item() {}

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

	@Column(name = "CODE", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "GROUP_ID")
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "SUBGROUP_ID")
	public Integer getSubgroupId() {
		return this.subgroupId;
	}

	public void setSubgroupId(Integer subgroupId) {
		this.subgroupId = subgroupId;
	}

	@Column(name = "TYPE_ID")
	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "STATUS_ID")
	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Column(name = "NAME", length = 1000)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NAME_ENG", length = 1000)
	public String getNameEng() {
		return this.nameEng == null ? "" : this.nameEng;
	}

	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}

	@Column(name = "BRAND_ID")
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	@Column(name = "COMPOSITION", length = 1000)
	public String getComposition() {
		return this.composition;
	}

	public void setComposition(String composition) {
		this.composition = composition;
	}

	@Column(name = "COMMERCIAL", length = 1000)
	public String getCommercial() {
		return this.commercial;
	}

	public void setCommercial(String commercial) {
		this.commercial = commercial;
	}

	@Column(name = "PURPOSE", length = 1000)
	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Column(name = "TECHNICAL_CHARACTERISTICS", length = 1000)
	public String getTechnicalCharacteristics() {
		return this.technicalCharacteristics;
	}

	public void setTechnicalCharacteristics(String technicalCharacteristics) {
		this.technicalCharacteristics = technicalCharacteristics;
	}

	@Column(name = "GROSS_PRICE_RM_D", precision = 18)
	public BigDecimal getGrossPriceRmD() {
		return this.grossPriceRmD;
	}

	public void setGrossPriceRmD(BigDecimal grossPriceRmD) {
		this.grossPriceRmD = grossPriceRmD;
	}

	@Column(name = "GROSS_PRICE_RM_D_SPEC", precision = 18)
	public BigDecimal getGrossPriceRmDSpec() {
		return this.grossPriceRmDSpec;
	}

	public void setGrossPriceRmDSpec(BigDecimal grossPriceRmDSpec) {
		this.grossPriceRmDSpec = grossPriceRmDSpec;
	}

	@Column(name = "NET_PRICE_RM_D", precision = 18)
	public BigDecimal getNetPriceRmD() {
		return this.netPriceRmD;
	}

	public void setNetPriceRmD(BigDecimal netPriceRmD) {
		this.netPriceRmD = netPriceRmD;
	}

	@Column(name = "NET_PRICE_RM_D_SPEC", precision = 18)
	public BigDecimal getNetPriceRmDSpec() {
		return this.netPriceRmDSpec;
	}

	public void setNetPriceRmDSpec(BigDecimal netPriceRmDSpec) {
		this.netPriceRmDSpec = netPriceRmDSpec;
	}

	@Column(name = "GROSS_PRICE_D", precision = 18)
	public BigDecimal getGrossPriceD() {
		return this.grossPriceD;
	}

	public void setGrossPriceD(BigDecimal grossPriceD) {
		this.grossPriceD = grossPriceD;
	}

	@Column(name = "GROSS_PRICE_D_SPEC", precision = 18)
	public BigDecimal getGrossPriceDSpec() {
		return this.grossPriceDSpec;
	}

	public void setGrossPriceDSpec(BigDecimal grossPriceDSpec) {
		this.grossPriceDSpec = grossPriceDSpec;
	}

	@Column(name = "NET_PRICE_D_FAK", precision = 18)
	public BigDecimal getNetPriceDFak() {
		return this.netPriceDFak;
	}

	public void setNetPriceDFak(BigDecimal netPriceDFak) {
		this.netPriceDFak = netPriceDFak;
	}

	@Column(name = "NET_PRICE_D_FAK_SPEC", precision = 18)
	public BigDecimal getNetPriceDFakSpec() {
		return this.netPriceDFakSpec;
	}

	public void setNetPriceDFakSpec(BigDecimal netPriceDFakSpec) {
		this.netPriceDFakSpec = netPriceDFakSpec;
	}

	@Column(name = "GROSS_PRICE_D_FAK", precision = 18)
	public BigDecimal getGrossPriceDFak() {
		return this.grossPriceDFak;
	}

	public void setGrossPriceDFak(BigDecimal grossPriceDFak) {
		this.grossPriceDFak = grossPriceDFak;
	}

	@Column(name = "GROSS_PRICE_D_FAK_SPEC", precision = 18)
	public BigDecimal getGrossPriceDFakSpec() {
		return this.grossPriceDFakSpec;
	}

	public void setGrossPriceDFakSpec(BigDecimal grossPriceDFakSpec) {
		this.grossPriceDFakSpec = grossPriceDFakSpec;
	}

	@Column(name = "TARIFF_GROUP_ID")
	public Integer getTariffGroupId() {
		return this.tariffGroupId;
	}

	public void setTariffGroupId(Integer tariffGroupId) {
		this.tariffGroupId = tariffGroupId;
	}

	@Column(name = "MIN_QTY")
	public Integer getMinQty() {
		return this.minQty;
	}

	public void setMinQty(Integer minQty) {
		this.minQty = minQty;
	}

	@Column(name = "COUNTRY_OF_ORIGIN_ID")
	public Integer getCountryOfOriginId() {
		return this.countryOfOriginId;
	}

	public void setCountryOfOriginId(Integer countryOfOriginId) {
		this.countryOfOriginId = countryOfOriginId;
	}

	@Column(name = "CURRENCY_ID")
	public Integer getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "PACKAGE_TYPE_ID")
	public Integer getPackageTypeId() {
		return this.packageTypeId;
	}

	public void setPackageTypeId(Integer packageTypeId) {
		this.packageTypeId = packageTypeId;
	}
	
	@Column(name = "PACKAGE_QTY")
	public Integer getPackageQty() {
		return this.packageQty == null ? 0 : this.packageQty;
	}

	public void setPackageQty(Integer packageQty) {
		this.packageQty = packageQty;
	}

	@Column(name = "MEASUREMENT_UNIT_ID")
	public Integer getMeasurementUnitId() {
		return this.measurementUnitId;
	}

	public void setMeasurementUnitId(Integer measurementUnitId) {
		this.measurementUnitId = measurementUnitId;
	}

	@Column(name = "WEIGHT", precision = 18)
	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@Column(name = "VOLUME", precision = 18)
	public BigDecimal getVolume() {
		return this.volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	@Column(name = "MASTERBOXUNIT_QTY")
	public Integer getMasterboxUnitQty() {
		return this.masterboxUnitQty == null ? 0 : this.masterboxUnitQty;
	}

	public void setMasterboxUnitQty(Integer masterboxUnitQty) {
		this.masterboxUnitQty = masterboxUnitQty;
	}

	@Column(name = "MASTERBOXUNIT_VOLUME", precision = 18)
	public BigDecimal getMasterboxUnitVolume() {
		return this.masterboxUnitVolume;
	}

	public void setMasterboxUnitVolume(BigDecimal masterboxUnitVolume) {
		this.masterboxUnitVolume = masterboxUnitVolume;
	}

	@Column(name = "CARDBOARDUNIT_QTY")
	public Integer getCardboardUnitQty() {
		return this.cardboardUnitQty == null ? 0 : this.cardboardUnitQty;
	}

	public void setCardboardUnitQty(Integer cardboardUnitQty) {
		this.cardboardUnitQty = cardboardUnitQty;
	}

	@Column(name = "PALETTEUNIT_QTY", precision = 18)
	public BigDecimal getPaletteUnitQty() {
		return this.paletteUnitQty == null ? BigDecimal.ZERO : this.paletteUnitQty;
	}

	public void setPaletteUnitQty(BigDecimal paletteUnitQty) {
		this.paletteUnitQty = paletteUnitQty;
	}

	@Column(name = "TARIFF_NUMBER_EU", length = 100)
	public String getTariffNumberEu() {
		return this.tariffNumberEu;
	}

	public void setTariffNumberEu(String tariffNumberEu) {
		this.tariffNumberEu = tariffNumberEu;
	}

	@Column(name = "TARIFF_NUMBER_US", length = 100)
	public String getTariffNumberUs() {
		return this.tariffNumberUs;
	}

	public void setTariffNumberUs(String tariffNumberUs) {
		this.tariffNumberUs = tariffNumberUs;
	}

	@Column(name = "CONTAINER20INCH_QTY", precision = 18)
	public BigDecimal getContainer20inchQty() {
		return this.container20inchQty == null ? BigDecimal.ZERO : this.container20inchQty;
	}

	public void setContainer20inchQty(BigDecimal container20inchQty) {
		this.container20inchQty = container20inchQty;
	}

	@Column(name = "QTY_FOR_FREE")
	public Integer getQtyForFree() {
		return this.qtyForFree;
	}

	public void setQtyForFree(Integer qtyForFree) {
		this.qtyForFree = qtyForFree;
	}

	@Column(name = "FREE_QTY")
	public Integer getFreeQty() {
		return this.freeQty;
	}

	public void setFreeQty(Integer freeQty) {
		this.freeQty = freeQty;
	}

	@Column(name = "PURCHASE_PRICE", precision = 18)
	public BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Column(name = "PURCHASE_PRICE_SPEC", precision = 18)
	public BigDecimal getPurchasePriceSpec() {
		return this.purchasePriceSpec;
	}

	public void setPurchasePriceSpec(BigDecimal purchasePriceSpec) {
		this.purchasePriceSpec = purchasePriceSpec;
	}

	@Column(name = "PRICE_CORRECTION_RATE", precision = 18)
	public BigDecimal getPriceCorrectionRate() {
		return this.priceCorrectionRate;
	}

	public void setPriceCorrectionRate(BigDecimal priceCorrectionRate) {
		this.priceCorrectionRate = priceCorrectionRate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Integer getInternetCatalogId() {
		return internetCatalogId;
	}

	public void setInternetCatalogId(Integer internetCatalogId) {
		this.internetCatalogId = internetCatalogId;
	}

	@Column(name = "_OLDID")
	public Integer getOldid() {
		return this.oldid;
	}

	public void setOldid(Integer oldid) {
		this.oldid = oldid;
	}

	public String getImagePath() {
		return this.imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	// ----------------------------
	@OneToMany
	@JoinColumn(name = "ITEM_ID")
	public List<ItemBarcode> getItemBarcode() {
		return itemBarcode;
	}

	public void setItemBarcode(List<ItemBarcode> itemBarcode) {
		this.itemBarcode = itemBarcode;
	}
	// ----------------------------
	@Transient
	@Column(name = "FULL_NAME", length = 1000)
	public String getFullName() {
		String res = "";
		res = this.name.concat("\n").concat(this.getNameEng());
		return res;
	}
	
	@Transient
	@Column(name = "GROSS_PRICE_RM_D_PLUS_SPEC", precision = 18)
	public String getGrossPriceRmDPlusSpec() {
		String res = "";
		res = this.grossPriceRmD.toString().concat("\n").concat(this.grossPriceRmDSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "NET_PRICE_RM_D_PLUS_SPEC", precision = 18)
	public String getNetPriceRmDPlusSpec() {
		String res = "";
		res = this.netPriceRmD.toString().concat("\n").concat(this.netPriceRmDSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "GROSS_PRICE_D_PLUS_SPEC", precision = 18)
	public String getGrossPriceDPlusSpec() {
		String res = "";
		res = this.grossPriceD.toString().concat("\n").concat(this.grossPriceDSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "NET_PRICE_D_FAK_PLUS_SPEC", precision = 18)
	public String getNetPriceDFakPlusSpec() {
		String res = "";
		res = this.netPriceDFak.toString().concat("\n").concat(this.netPriceDFakSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "GROSS_PRICE_D_FAK_PLUS_SPEC", precision = 18)
	public String getGrossPriceDFakPlusSpec() {
		String res = "";
		res = this.grossPriceDFak.toString().concat("\n").concat(this.grossPriceDFakSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "PACKAGING", length = 1000)
	public String getPackaging() {
		String res = "";
		res =
			  "PAKOVANJE:"  + this.getPackageQty().toString()
			+ " | MASTERBOX:"  + this.getMasterboxUnitQty().toString()
			+ " | CARDBOX:" + this.getCardboardUnitQty().toString()
			+ "\n"
			+ "PALETA:"  + this.getPaletteUnitQty().toString()
			+ " | KONTEJNER:" + this.getContainer20inchQty().toString()
			;
		return res;
	}
	
	
	@Transient
	@Column(name = "PACKAGING", length = 1000)
	public String getPackagingInSeparateLines() {
		String res = "";
		res =
			  "PAKOVANJE:"  + this.getPackageQty().toString()
			+ "\n"
			+ "MASTERBOX:"  + this.getMasterboxUnitQty().toString()
			+ "\n"
			+ "CARDBOX:" + this.getCardboardUnitQty().toString()
			+ "\n"
			+ "PALETA:"  + this.getPaletteUnitQty().toString()
			+ "\n"
			+ "KONTEJNER:" + this.getContainer20inchQty().toString()
			;
		return res;
	}

	
	/**
	 * Creates new ItemSImple object, assigns the values from this item.
	 * Used in Client::F6
	 * @return
	 */
	@Transient
	public ItemSimple getItemSimple() {
		ItemSimple is = new ItemSimple();
		is.setId(id);
		is.setCode(code);
		is.setName(name);
		is.setNameEng(nameEng);
		is.setStatusId(statusId);
		is.setTypeId(typeId);
		return is;
	}

}
