package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "PALETTE_ITEM", schema = "PUBLIC", catalog = "RM")
public class PaletteItem implements java.io.Serializable {

	private Integer id;
	private Integer paletteId;
	private Integer itemId;
	private Item item;
	private Integer quantity;
	private Integer weight;
	private LocalDate expiryDate;
	private String description;

	public PaletteItem() {
	}

	public PaletteItem(Integer paletteId, Item item, Integer quantity, Integer weight) {
		this.paletteId = paletteId;
		this.item = item;
		this.quantity = quantity;
		this.setWeight(weight);
	}

	public PaletteItem(Integer paletteId, Item item, Integer quantity, String description) {
		this.paletteId = paletteId;
		this.item = item;
		this.quantity = quantity;
		this.description = description;
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

	@Column(name = "PALETTE_ID", nullable = false)
	public Integer getPaletteId() {
		return this.paletteId;
	}

	public void setPaletteId(Integer paletteId) {
		this.paletteId = paletteId;
	}

	@Column(name = "ITEM_ID", nullable = false, insertable = false, updatable = false)
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "QUANTITY", nullable = false)
	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	//---------------------------------------------
	@Transient
	public String getItemName() {
		if (this.item != null) {
			return this.item.getName();
		}
		else return "";
	}
}
