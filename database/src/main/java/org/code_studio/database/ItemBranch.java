package org.code_studio.database;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_BRANCH", schema = "PUBLIC", catalog = "RM")
public class ItemBranch implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	//private Integer catalogId;
	private String code;
	private String name;
	private String description;
	private Integer oldid;
	private List<ItemSubgroup> itemSubgroups;

	@ManyToMany(mappedBy = "ItemCatalog", fetch = FetchType.LAZY)
	private List <ItemCatalog> itemCatalogs;
	
	public ItemBranch() {}

	public ItemBranch(Integer clientId, String code, String name, String description,
			Integer oldid) {
		this.clientId = clientId;
		//this.catalogId = catalogId;
		this.code = code;
		this.name = name;
		this.description = description;
		this.oldid = oldid;
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

	/*
	@Column(name = "CATALOG_ID")
	public Integer getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	*/

	@Column(name = "CODE", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "_OLDID")
	public Integer getOldid() {
		return this.oldid;
	}

	public void setOldid(Integer oldid) {
		this.oldid = oldid;
	}
	
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "ITEM_BRANCH_SUBGROUP_LINK", 
        joinColumns = { @JoinColumn(name = "branch_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "subgroup_id") }
    )
	public List<ItemSubgroup> getItemSubgroups() {
		return itemSubgroups;
	}

	public void setItemSubgroups(List<ItemSubgroup> itemSubgroups) {
		this.itemSubgroups = itemSubgroups;
	}

}
