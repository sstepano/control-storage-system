package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_CATALOG_ITEM_LINK", schema = "PUBLIC", catalog = "RM")
public class ItemCatalogItemLink implements java.io.Serializable {

	private Integer id;
	private Integer catalogId;
	private Integer itemId;

	public ItemCatalogItemLink() {
	}

	public ItemCatalogItemLink(Integer catalogId, Integer itemId) {
		this.catalogId = catalogId;
		this.itemId = itemId;
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

	@Column(name = "CATALOG_ID")
	public Integer getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	@Column(name = "ITEM_ID")
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

}
