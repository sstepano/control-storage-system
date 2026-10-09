package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_CATALOG_SUBGROUP_LINK", schema = "PUBLIC", catalog = "RM")
public class ItemCatalogSubgroupLink implements java.io.Serializable {

	private Integer id;
	private Integer catalogId;
	private Integer subgroupId;

	public ItemCatalogSubgroupLink() {
	}

	public ItemCatalogSubgroupLink(Integer catalogId, Integer subgroupId) {
		this.catalogId = catalogId;
		this.subgroupId = subgroupId;
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

	@Column(name = "SUBGROUP_ID")
	public Integer getSubgroupId() {
		return this.subgroupId;
	}

	public void setSubgroupId(Integer subgroupId) {
		this.subgroupId = subgroupId;
	}

}
