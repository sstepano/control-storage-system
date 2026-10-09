package org.code_studio.database;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_CATALOG", schema = "PUBLIC", catalog = "RM")
public class ItemCatalog implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private String code;
	private String name;
	private String description;
	private Integer oldid;	
	private List <ItemBranch> itemBranches;

	public ItemCatalog() {}

	public ItemCatalog(Integer clientId, String code, String name, String description, Integer oldid/*, List <ItemBranch> itemBranches*/) {
		this.clientId = clientId;
		this.code = code;
		this.name = name;
		this.description = description;
		this.oldid = oldid;
		//this.itemBranches = itemBranches;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

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
        name = "ITEM_CATALOG_BRANCH_LINK", 
        joinColumns = { @JoinColumn(name = "catalog_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "branch_id") }
    )
	public List<ItemBranch> getItemBranches() {
		return itemBranches;
	}

	public void setItemBranches(List<ItemBranch> itemBranches) {
		this.itemBranches = itemBranches;
	}

}
