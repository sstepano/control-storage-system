package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ITEM_BRANCH_SUBGROUP_LINK", schema = "PUBLIC", catalog = "RM")
public class ItemBranchSubgroupLink implements java.io.Serializable {

	private Integer id;
	private Integer branchId;
	private Integer subgroupId;

	public ItemBranchSubgroupLink() {
	}

	public ItemBranchSubgroupLink(Integer branchId, Integer subgroupId) {
		this.branchId = branchId;
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

	@Column(name = "BRANCH_ID")
	public Integer getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	@Column(name = "SUBGROUP_ID")
	public Integer getSubgroupId() {
		return this.subgroupId;
	}

	public void setSubgroupId(Integer subgroupId) {
		this.subgroupId = subgroupId;
	}

}
