package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_GROUP_LINK", schema = "PUBLIC", catalog = "RM")
public class ItemGroupLink implements java.io.Serializable {

	private Integer id;
	private int itemId;
	private Integer groupId;

	public ItemGroupLink() {
	}

	public ItemGroupLink(int itemId) {
		this.itemId = itemId;
	}

	public ItemGroupLink(int itemId, Integer groupId) {
		this.itemId = itemId;
		this.groupId = groupId;
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

	@Column(name = "GROUP_ID")
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

}
