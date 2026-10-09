package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "ORDERS_DETAIL", schema = "PUBLIC", catalog = "RM")
public class OrdersDetail implements java.io.Serializable {

	private Integer id;
	private Integer orderId;
	private Integer itemId;
	private BigDecimal purchasePrice;
	private BigDecimal purchasePriceSpec;
	private BigDecimal price;
	private Integer minimumQty;
	private BigDecimal orderedQty;
	private BigDecimal deliveredQty;
	
	private Item item;
	
	private Orders order;

	public OrdersDetail() {}

	public OrdersDetail(Integer orderId, Integer itemId, BigDecimal purchasePrice, BigDecimal purchasePriceSpec,
			BigDecimal price, Integer minimumQty, BigDecimal orderedQty, BigDecimal deliveredQty) {
		this.orderId = orderId;
		this.itemId = itemId;
		this.purchasePrice = purchasePrice;
		this.purchasePriceSpec = purchasePriceSpec;
		this.price = price;
		this.minimumQty = minimumQty;
		this.orderedQty = orderedQty;
		this.deliveredQty = deliveredQty;
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

	@Column(name = "ORDER_ID", updatable = false, insertable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Orders getOrder() {
		return this.order;
	}
	
	public void setOrder(Orders order) {
		this.order = order;
	}

	@Column(name = "ITEM_ID", insertable = false, updatable = false)
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Item getItem() {
		return this.item;
	}
	
	public void setItem(Item item) {
		this.item = item;
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

	@Column(name = "PRICE", precision = 18)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "MINIMUM_QTY")
	public Integer getMinimumQty() {
		return this.minimumQty;
	}

	public void setMinimumQty(Integer minimumQty) {
		this.minimumQty = minimumQty;
	}

	@Column(name = "ORDERED_QTY", precision = 18)
	public BigDecimal getOrderedQty() {
		return this.orderedQty;
	}

	public void setOrderedQty(BigDecimal orderedQty) {
		this.orderedQty = orderedQty;
	}

	@Column(name = "DELIVERED_QTY", precision = 18)
	public BigDecimal getDeliveredQty() {
		return this.deliveredQty;
	}

	public void setDeliveredQty(BigDecimal deliveredQty) {
		this.deliveredQty = deliveredQty;
	}

	//-----------------------------------------
	@Transient
	public String getItemCode() {
		return this.item == null ? "" : this.item.getCode(); 
	}

	@Transient
	public String getItemName() {
		return this.item == null ? "" : this.item.getName(); 
	}

	@Transient
	public LocalDate getOrderDate() {
		if (this.order != null  && this.order.getOrderDate() != null) {
			return this.order.getOrderDate();
		} else return null; 
	}

	@Transient
	public LocalDate getOrderReceiveDate() {
		if (this.order != null  && this.order.getReceiveDate() != null) {
			return this.order.getReceiveDate();
		} else return null; 
	}

	
}
