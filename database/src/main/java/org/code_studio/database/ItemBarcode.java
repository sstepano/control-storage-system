package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_BARCODE", schema = "PUBLIC", catalog = "RM", uniqueConstraints = @UniqueConstraint(columnNames = {
		"ITEM_ID", "BARCODE" }))
public class ItemBarcode implements java.io.Serializable {

	private Integer id;
	//private Item item;
	private Integer itemId;
	private String barcode;

	public ItemBarcode() {
	}

	public ItemBarcode(Integer itemId, String barcode) {
		this.itemId = itemId;
		this.barcode = barcode;
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

	@Column(name = "ITEM_ID", insertable = false, updatable = false)
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	/*
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	*/

	@Column(name = "BARCODE", length = 30)
	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	//-----------------------------------
	/*
	@Transient
	public Integer getClientId() {
		return item == null ? 0 : item.getClientId();
	}
	
	@Transient
	public String getItemName() {
		return item == null ? "" : item.getName();
	}
	*/

}
