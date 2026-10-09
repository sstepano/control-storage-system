package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@SuppressWarnings("serial")
@Table(name = "offer_detail", catalog = "rm")
public class OfferDetail implements java.io.Serializable {

	private Integer id;
	private Integer offerId;
	private String priceType;
	private Integer itemId;
	private Integer qty;
	private Integer specQty;
	private BigDecimal vatRate;
	private BigDecimal unitNetAmt;
	private BigDecimal unitNetAmtSpec;
	private BigDecimal discountAmt;
	private BigDecimal discountAmtSpec;
	private BigDecimal discountRate;
	private BigDecimal discountRateSpec;
	private BigDecimal unitVatAmt;
	private BigDecimal unitVatAmtSpec;
	private BigDecimal unitGrossAmt;
	private BigDecimal unitGrossAmtSpec;
	private BigDecimal netAmt;
	private BigDecimal netAmtSpec;
	private BigDecimal vatAmt;
	private BigDecimal vatAmtSpec;
	private BigDecimal grossAmt;
	private BigDecimal grossAmtSpec;
	private BigDecimal totalAmt;
	private BigDecimal totalAmtSpec;
	private BigDecimal grossAmtRm;
	private BigDecimal grossAmtRmSpec;
	private BigDecimal netAmtDinFak;
	private BigDecimal netAmtDinFakSpec;
	private BigDecimal netAmtDinRmDis;
	private BigDecimal netAmtDinRmDisSpec;
	private BigDecimal vatAmtDinRmDis;
	private BigDecimal grossAmtDinRmDis;
	private BigDecimal grossAmtDinRmDisSpec;
	private Integer lastModifiedBy;
	private LocalDateTime lastModifiedDate;
	
	private Item item;

	public OfferDetail() {}

	public OfferDetail(Integer id, Integer offerId, String priceType, Integer itemId, Integer qty, Integer specQty,
			BigDecimal vatRate, BigDecimal unitNetAmt, BigDecimal unitNetAmtSpec, BigDecimal discountAmt,
			BigDecimal discountAmtSpec, BigDecimal discountRate, BigDecimal discountRateSpec, BigDecimal unitVatAmt, BigDecimal unitVatAmtSpec, BigDecimal unitGrossAmt,
			BigDecimal unitGrossAmtSpec, BigDecimal netAmt, BigDecimal netAmtSpec, BigDecimal vatAmt,
			BigDecimal vatAmtSpec, BigDecimal grossAmt, BigDecimal grossAmtSpec, BigDecimal totalAmt,
			BigDecimal totalAmtSpec, BigDecimal grossAmtRm, BigDecimal grossAmtRmSpec, BigDecimal netAmtDinFak,
			BigDecimal netAmtDinFakSpec, BigDecimal netAmtDinRmDis, BigDecimal netAmtDinRmDisSpec,
			BigDecimal vatAmtDinRmDis, BigDecimal grossAmtDinRmDis, BigDecimal grossAmtDinRmDisSpec,
			Integer lastModifiedBy, LocalDateTime lastModifiedDate) {
		this.id = id;
		this.offerId = offerId;
		this.priceType = priceType;
		this.itemId = itemId;
		this.qty = qty;
		this.specQty = specQty;
		this.vatRate = vatRate;
		this.unitNetAmt = unitNetAmt;
		this.unitNetAmtSpec = unitNetAmtSpec;
		this.discountAmt = discountAmt;
		this.discountAmtSpec = discountAmtSpec;
		this.discountRate = discountRate;
		this.discountRateSpec = discountRateSpec;
		this.unitVatAmt = unitVatAmt;
		this.unitVatAmtSpec = unitVatAmtSpec;
		this.unitGrossAmt = unitGrossAmt;
		this.unitGrossAmtSpec = unitGrossAmtSpec;
		this.netAmt = netAmt;
		this.netAmtSpec = netAmtSpec;
		this.vatAmt = vatAmt;
		this.vatAmtSpec = vatAmtSpec;
		this.grossAmt = grossAmt;
		this.grossAmtSpec = grossAmtSpec;
		this.totalAmt = totalAmt;
		this.totalAmtSpec = totalAmtSpec;
		this.grossAmtRm = grossAmtRm;
		this.grossAmtRmSpec = grossAmtRmSpec;
		this.netAmtDinFak = netAmtDinFak;
		this.netAmtDinFakSpec = netAmtDinFakSpec;
		this.netAmtDinRmDis = netAmtDinRmDis;
		this.netAmtDinRmDisSpec = netAmtDinRmDisSpec;
		this.vatAmtDinRmDis = vatAmtDinRmDis;
		this.grossAmtDinRmDis = grossAmtDinRmDis;
		this.grossAmtDinRmDisSpec = grossAmtDinRmDisSpec;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "offer_id")
	public Integer getOfferId() {
		return this.offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	@Column(name = "price_type", length = 1)
	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@Column(name = "item_id")
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "qty")
	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Column(name = "spec_qty")
	public Integer getSpecQty() {
		return this.specQty;
	}

	public void setSpecQty(Integer specQty) {
		this.specQty = specQty;
	}

	@Column(name = "vat_rate", precision = 18)
	public BigDecimal getVatRate() {
		return this.vatRate;
	}

	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	@Column(name = "unit_net_amt", precision = 18)
	public BigDecimal getUnitNetAmt() {
		return this.unitNetAmt;
	}

	public void setUnitNetAmt(BigDecimal unitNetAmt) {
		this.unitNetAmt = unitNetAmt;
	}

	@Column(name = "unit_net_amt_spec", precision = 18)
	public BigDecimal getUnitNetAmtSpec() {
		return this.unitNetAmtSpec;
	}

	public void setUnitNetAmtSpec(BigDecimal unitNetAmtSpec) {
		this.unitNetAmtSpec = unitNetAmtSpec;
	}

	@Column(name = "discount_amt", precision = 18)
	public BigDecimal getDiscountAmt() {
		return this.discountAmt;
	}

	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
	}

	@Column(name = "discount_amt_spec", precision = 18)
	public BigDecimal getDiscountAmtSpec() {
		return this.discountAmtSpec;
	}

	public void setDiscountAmtSpec(BigDecimal discountAmtSpec) {
		this.discountAmtSpec = discountAmtSpec;
	}
	
	@Column(name = "discount_rate", precision = 18)
	public BigDecimal getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name = "discount_rate_spec", precision = 18)
	public BigDecimal getDiscountRateSpec() {
		return this.discountRateSpec;
	}

	public void setDiscountRateSpec(BigDecimal discountRateSpec) {
		this.discountRateSpec = discountRateSpec;
	}

	@Column(name = "unit_vat_amt", precision = 18)
	public BigDecimal getUnitVatAmt() {
		return this.unitVatAmt;
	}

	public void setUnitVatAmt(BigDecimal unitVatAmt) {
		this.unitVatAmt = unitVatAmt;
	}

	@Column(name = "unit_vat_amt_spec", precision = 18)
	public BigDecimal getUnitVatAmtSpec() {
		return this.unitVatAmtSpec;
	}

	public void setUnitVatAmtSpec(BigDecimal unitVatAmtSpec) {
		this.unitVatAmtSpec = unitVatAmtSpec;
	}

	@Column(name = "unit_gross_amt", precision = 18)
	public BigDecimal getUnitGrossAmt() {
		return this.unitGrossAmt;
	}

	public void setUnitGrossAmt(BigDecimal unitGrossAmt) {
		this.unitGrossAmt = unitGrossAmt;
	}

	@Column(name = "unit_gross_amt_spec", precision = 18)
	public BigDecimal getUnitGrossAmtSpec() {
		return this.unitGrossAmtSpec;
	}

	public void setUnitGrossAmtSpec(BigDecimal unitGrossAmtSpec) {
		this.unitGrossAmtSpec = unitGrossAmtSpec;
	}

	@Column(name = "net_amt", precision = 18)
	public BigDecimal getNetAmt() {
		return this.netAmt;
	}

	public void setNetAmt(BigDecimal netAmt) {
		this.netAmt = netAmt;
	}

	@Column(name = "net_amt_spec", precision = 18)
	public BigDecimal getNetAmtSpec() {
		return this.netAmtSpec;
	}

	public void setNetAmtSpec(BigDecimal netAmtSpec) {
		this.netAmtSpec = netAmtSpec;
	}

	@Column(name = "vat_amt", precision = 18)
	public BigDecimal getVatAmt() {
		return this.vatAmt;
	}

	public void setVatAmt(BigDecimal vatAmt) {
		this.vatAmt = vatAmt;
	}

	@Column(name = "vat_amt_spec", precision = 18)
	public BigDecimal getVatAmtSpec() {
		return this.vatAmtSpec;
	}

	public void setVatAmtSpec(BigDecimal vatAmtSpec) {
		this.vatAmtSpec = vatAmtSpec;
	}

	@Column(name = "gross_amt", precision = 18)
	public BigDecimal getGrossAmt() {
		return this.grossAmt;
	}

	public void setGrossAmt(BigDecimal grossAmt) {
		this.grossAmt = grossAmt;
	}

	@Column(name = "gross_amt_spec", precision = 18)
	public BigDecimal getGrossAmtSpec() {
		return this.grossAmtSpec;
	}

	public void setGrossAmtSpec(BigDecimal grossAmtSpec) {
		this.grossAmtSpec = grossAmtSpec;
	}

	@Column(name = "total_amt", precision = 18)
	public BigDecimal getTotalAmt() {
		return this.totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	@Column(name = "total_amt_spec", precision = 18)
	public BigDecimal getTotalAmtSpec() {
		return this.totalAmtSpec;
	}

	public void setTotalAmtSpec(BigDecimal totalAmtSpec) {
		this.totalAmtSpec = totalAmtSpec;
	}

	@Column(name = "gross_amt_rm", precision = 18)
	public BigDecimal getGrossAmtRm() {
		return this.grossAmtRm;
	}

	public void setGrossAmtRm(BigDecimal grossAmtRm) {
		this.grossAmtRm = grossAmtRm;
	}

	@Column(name = "gross_amt_rm_spec", precision = 18)
	public BigDecimal getGrossAmtRmSpec() {
		return this.grossAmtRmSpec;
	}

	public void setGrossAmtRmSpec(BigDecimal grossAmtRmSpec) {
		this.grossAmtRmSpec = grossAmtRmSpec;
	}

	@Column(name = "net_amt_din_fak", precision = 18)
	public BigDecimal getNetAmtDinFak() {
		return this.netAmtDinFak;
	}

	public void setNetAmtDinFak(BigDecimal netAmtDinFak) {
		this.netAmtDinFak = netAmtDinFak;
	}

	@Column(name = "net_amt_din_fak_spec", precision = 18)
	public BigDecimal getNetAmtDinFakSpec() {
		return this.netAmtDinFakSpec;
	}

	public void setNetAmtDinFakSpec(BigDecimal netAmtDinFakSpec) {
		this.netAmtDinFakSpec = netAmtDinFakSpec;
	}

	@Column(name = "net_amt_din_rm_dis", precision = 18)
	public BigDecimal getNetAmtDinRmDis() {
		return this.netAmtDinRmDis;
	}

	public void setNetAmtDinRmDis(BigDecimal netAmtDinRmDis) {
		this.netAmtDinRmDis = netAmtDinRmDis;
	}

	@Column(name = "net_amt_din_rm_dis_spec", precision = 18)
	public BigDecimal getNetAmtDinRmDisSpec() {
		return this.netAmtDinRmDisSpec;
	}

	public void setNetAmtDinRmDisSpec(BigDecimal netAmtDinRmDisSpec) {
		this.netAmtDinRmDisSpec = netAmtDinRmDisSpec;
	}

	@Column(name = "vat_amt_din_rm_dis", precision = 18)
	public BigDecimal getVatAmtDinRmDis() {
		return this.vatAmtDinRmDis;
	}

	public void setVatAmtDinRmDis(BigDecimal vatAmtDinRmDis) {
		this.vatAmtDinRmDis = vatAmtDinRmDis;
	}

	@Column(name = "gross_amt_din_rm_dis", precision = 18)
	public BigDecimal getGrossAmtDinRmDis() {
		return this.grossAmtDinRmDis;
	}

	public void setGrossAmtDinRmDis(BigDecimal grossAmtDinRmDis) {
		this.grossAmtDinRmDis = grossAmtDinRmDis;
	}

	@Column(name = "gross_amt_din_rm_dis_spec", precision = 18)
	public BigDecimal getGrossAmtDinRmDisSpec() {
		return this.grossAmtDinRmDisSpec;
	}

	public void setGrossAmtDinRmDisSpec(BigDecimal grossAmtDinRmDisSpec) {
		this.grossAmtDinRmDisSpec = grossAmtDinRmDisSpec;
	}

	@Column(name = "last_modified_by")
	public Integer getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(Integer lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Column(name = "last_modified_date")
	public LocalDateTime getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	
	@OneToOne
	@JoinColumn(name="ITEM_ID", insertable=false, updatable=false)
	public Item getItem() {
		return this.item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	// --------------------------------
	@Transient
	public String getItemCode() {
		return item != null ? item.getCode() : "";
	}

	@Transient
	public String getItemName() {
		return item != null ? item.getName() : "";
	}
	
	@Transient
	public String getItemNameEng() {
		return item != null ? item.getNameEng() : "";
	}
	
	@Transient
	public String getItemTechnicalCharasteristics() {
		return item != null ? item.getTechnicalCharacteristics() : "";
	}
	
	@Transient
	@Column(name = "UNIT_NET_AMT_PLUS_SPEC", precision = 18)
	public String getUnitNetAmtPlusSpec() {
		String res = "";
		res = this.unitNetAmt.toString().concat("\n").concat(this.unitNetAmtSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "DISCOUNT_AMT_PLUS_SPEC", precision = 18)
	public String getDiscountAmtPlusSpec() {
		String res = "";
		res = this.discountAmt.toString().concat("\n").concat(this.discountAmtSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "DISCOUNT_RATE_PLUS_SPEC", precision = 18)
	public String getDiscountRatePlusSpec() {
		String res = "";
		res = this.discountRate.toString().concat("\n").concat(this.discountRateSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "UNIT_VAT_AMT_PLUS_SPEC", precision = 18)
	public String getUnitVatAmtPlusSpec() {
		String res = "";
		res = this.unitVatAmt.toString().concat("\n").concat(this.unitVatAmtSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "UNIT_GROSS_AMT_PLUS_SPEC", precision = 18)
	public String getUnitGrossAmtPlusSpec() {
		String res = "";
		res = this.unitGrossAmt.toString().concat("\n").concat(this.unitGrossAmtSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "NET_AMT_PLUS_SPEC", precision = 18)
	public String getNetAmtPlusSpec() {
		String res = "";
		res = this.netAmt.toString().concat("\n").concat(this.netAmtSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "VAT_AMT_PLUS_SPEC", precision = 18)
	public String getVatAmtPlusSpec() {
		String res = "";
		res = this.vatAmt.toString().concat("\n").concat(this.vatAmtSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "GROSS_AMT_PLUS_SPEC", precision = 18)
	public String getGrossAmtPlusSpec() {
		String res = "";
		res = this.grossAmt.toString().concat("\n").concat(this.grossAmtSpec.toString());
		return res;
	}
	
	@Transient
	@Column(name = "TOTAL_AMT_PLUS_SPEC", precision = 18)
	public String getTotalAmtPlusSpec() {
		String res = "";
		res = this.totalAmt.toString().concat("\n").concat(this.totalAmtSpec.toString());
		return res;
	}


}
