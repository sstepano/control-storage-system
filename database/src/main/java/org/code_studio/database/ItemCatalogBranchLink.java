package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_CATALOG_BRANCH_LINK", schema = "PUBLIC", catalog = "RM")
public class ItemCatalogBranchLink implements java.io.Serializable {

	private Integer id;
	private Integer catalogId;
	private Integer branchId;

	public ItemCatalogBranchLink() {
	}

	public ItemCatalogBranchLink(Integer catalogId, Integer branchId) {
		this.catalogId = catalogId;
		this.branchId = branchId;
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

	@Column(name = "BRANCH_ID")
	public Integer getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

}
