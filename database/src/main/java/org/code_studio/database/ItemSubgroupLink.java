package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_SUBGROUP_LINK", schema = "PUBLIC", catalog = "RM")
public class ItemSubgroupLink implements java.io.Serializable {

	private Integer id;
	private int itemId;
	private Integer subgroupId;

	public ItemSubgroupLink() {
	}

	public ItemSubgroupLink(int itemId) {
		this.itemId = itemId;
	}

	public ItemSubgroupLink(int itemId, Integer subgroupId) {
		this.itemId = itemId;
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

	@Column(name = "ITEM_ID", nullable = false)
	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	@Column(name = "SUBGROUP_ID")
	public Integer getSubgroupId() {
		return this.subgroupId;
	}

	public void setSubgroupId(Integer subgroupId) {
		this.subgroupId = subgroupId;
	}

}
