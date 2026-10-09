package org.code_studio.database;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "inventory_listing", catalog = "rm")
public class InventoryListing implements java.io.Serializable {

	private Integer id;
	private int warehouseId;
	private Warehouse warehouse;
	private int countingNbr;
	private int enumeratorUserId;
	private ApplicationUserName enumeratorUser;
	private int statusId;
	private InventoryListingStatus status;
	private int createdByUserId;
	private ApplicationUserName createdByUser; 
	private LocalDateTime createdDate;

	public InventoryListing() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "WAREHOUSE_ID", nullable = false)
	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	@OneToOne//(insertable = false, updatable = false)
	@JoinColumn(name = "WAREHOUSE_ID", insertable = false, updatable = false)
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "COUNTING_NBR", nullable = false)
	public int getCountingNbr() {
		return this.countingNbr;
	}

	public void setCountingNbr(int countingNbr) {
		this.countingNbr = countingNbr;
	}

	@Column(name = "ENUMERATOR_USER_ID", nullable = false)
	public int getEnumeratorUserId() {
		return this.enumeratorUserId;
	}

	public void setEnumeratorUserId(int enumeratorUserId) {
		this.enumeratorUserId = enumeratorUserId;
	}
	
	@OneToOne
	@JoinColumn(name = "ENUMERATOR_USER_ID", insertable = false, updatable = false)
	public ApplicationUserName getEnumeratorUser() {
		return enumeratorUser;
	}

	public void setEnumeratorUser(ApplicationUserName enumeratorUser) {
		this.enumeratorUser = enumeratorUser;
	}

	@Column(name = "STATUS_ID", nullable = false)
	public int getStatusId() {
		return this.statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	@ManyToOne
	@JoinColumn(name = "STATUS_ID", insertable = false, updatable = false)
	public InventoryListingStatus getStatus() {
		return status;
	}

	public void setStatus(InventoryListingStatus status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY_USER_ID", nullable = false)
	public int getCreatedByUserId() {
		return this.createdByUserId;
	}

	public void setCreatedByUserId(int createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	@OneToOne
	@JoinColumn (name = "CREATED_BY_USER_ID", insertable = false, updatable = false)
	public ApplicationUserName getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(ApplicationUserName createdByUser) {
		this.createdByUser = createdByUser;
	}

	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	public LocalDateTime getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/***
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "inventoryListing")
	public List<InventoryListingDetail> getInventoryListingDetail() {
		return this.inventoryListingDetail;
	}

	public void setInventoryListingDetail(List<InventoryListingDetail> inventoryListingDetail) {
		this.inventoryListingDetail = inventoryListingDetail;
	}
	***/
	
	// -----------------------------------------------
	@Transient
	public String getWarehouseName() {
		return warehouse == null ? "" : warehouse.getName();
	}

	@Transient
	public String getEnumeratorUsername() {
	return enumeratorUser == null ? "" : enumeratorUser.getName();
	}
	
	@Transient
	public String getCreatedByUsername() {
		return createdByUser == null ? "" : createdByUser.getName();
	}
	
	@Transient
	public String getStatusName() {
		return status == null ? "" : status.getName();
	}
	
}
