package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_ACCESSORY", schema = "PUBLIC", catalog = "RM")
public class ItemAccessory implements java.io.Serializable {

	private Integer id;
	private int parentItemId;
	//@JsonManagedReference(value="ItemAccessory->Item")
	private List<Item> items;
	private String description;

	public ItemAccessory() {}

	public ItemAccessory(int parentItemId, List<Item> item) {
		this.parentItemId = parentItemId;
		this.setItems(item);
	}

	public ItemAccessory(int parentItemId, List<Item> item, String description) {
		this.parentItemId = parentItemId;
		this.setItems(item);
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

	@Column(name = "PARENT_ITEM_ID", nullable = false)
	public int getParentItemId() {
		return this.parentItemId;
	}

	public void setParentItemId(int parentItemId) {
		this.parentItemId = parentItemId;
	}
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID")
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}	
	
	/*
	@Column(name = "ITEM_ID", nullable = false)
	public int getItemId() {
		return this.item;
	}

	public void setItemId(int itemId) {
		this.item = itemId;
	}
	*/

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Transient //TODO: Ovo sam nabudzio, pokazuje samo jedan naziv jednog itema. Popraviti asap.
	public String getItemName() {
		return items.get(0).getName();
	}

}
