package org.code_studio.database;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_SUPPLIER", schema = "PUBLIC", catalog = "RM")
public class ItemSupplier implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private Integer itemId;
	private String supplierItemId;
	private BigDecimal procurementPrice;
	private BigDecimal procurementPriceSpecial;
	private Integer minimumQuantity;

	public ItemSupplier() {
	}

	public ItemSupplier(Integer clientId, Integer itemId, String supplierItemId, BigDecimal procurementPrice,
			BigDecimal procurementPriceSpecial, Integer minimumQuantity) {
		this.clientId = clientId;
		this.itemId = itemId;
		this.supplierItemId = supplierItemId;
		this.procurementPrice = procurementPrice;
		this.procurementPriceSpecial = procurementPriceSpecial;
		this.minimumQuantity = minimumQuantity;
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

	@Column(name = "ITEM_ID")
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "SUPPLIER_ITEM_ID", length = 100)
	public String getSupplierItemId() {
		return this.supplierItemId;
	}

	public void setSupplierItemId(String supplierItemId) {
		this.supplierItemId = supplierItemId;
	}

	@Column(name = "PROCUREMENT_PRICE", precision = 18)
	public BigDecimal getProcurementPrice() {
		return this.procurementPrice;
	}

	public void setProcurementPrice(BigDecimal procurementPrice) {
		this.procurementPrice = procurementPrice;
	}

	@Column(name = "PROCUREMENT_PRICE_SPECIAL", precision = 18)
	public BigDecimal getProcurementPriceSpecial() {
		return this.procurementPriceSpecial;
	}

	public void setProcurementPriceSpecial(BigDecimal procurementPriceSpecial) {
		this.procurementPriceSpecial = procurementPriceSpecial;
	}

	@Column(name = "MINIMUM_QUANTITY")
	public Integer getMinimumQuantity() {
		return this.minimumQuantity;
	}

	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

}
