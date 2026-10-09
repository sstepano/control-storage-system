package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_SET", schema = "PUBLIC", catalog = "RM")
public class ItemSet implements java.io.Serializable {

	private Integer id;
	private Integer itemId;
	private String name;
	private String description;
	//@JsonManagedReference(value="Managed:ItemSet->ItemSetDetail") 
	private List<ItemSetDetail> itemSetDetails;

	public ItemSet() {
	}

	public ItemSet(Integer itemId, String name, String description, List<ItemSetDetail> itemSetDetails) {
		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.itemSetDetails = itemSetDetails;
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

	@Column(name = "ITEM_ID")
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "NAME", length = 100)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "itemSet")
	public List<ItemSetDetail> getItemSetDetails() {
		return this.itemSetDetails;
	}

	public void setItemSetDetails(List<ItemSetDetail> itemSetDetails) {
		this.itemSetDetails = itemSetDetails;
	}

}
