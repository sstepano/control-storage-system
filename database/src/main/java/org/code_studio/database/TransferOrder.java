package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "transfer_order", catalog = "rm")
public class TransferOrder implements java.io.Serializable {

	private Integer id;
	private Integer warehouseIdOrigin;
	private Integer warehouseIdDestination;
	private Integer invoiceId;
	private Integer cashierInvoiceId;
	private Integer billOfReceiptId;
	private String typeCode;
	private Integer transferOrderNumber;
	private LocalDateTime transferOrderDate;
	private LocalDateTime dueDate;
	/***
	private LocalDateTime completedDate;
	private Integer completedByUserId;
	***/
	private LocalDateTime deliveredDate;
	private Integer deliveredByUserId;
	private LocalDateTime separationStartDate;
	private LocalDateTime separationCompletedDate;
	private Integer separationByUserId;
	private LocalDateTime scanningStartDate;
	private LocalDateTime scanningCompletedDate;
	private Integer scanningByUserId;
	private LocalDateTime packingStartDate;
	private LocalDateTime packingCompletedDate;
	private Integer packingByUserId;
	private Integer statusId;
	private String description;
	private Invoice invoice;
	private List<TransferOrderDetail> transferOrderDetail;

	public TransferOrder() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "WAREHOUSE_ID_ORIGIN")
	public Integer getWarehouseIdOrigin() {
		return this.warehouseIdOrigin;
	}

	public void setWarehouseIdOrigin(Integer warehouseIdOrigin) {
		this.warehouseIdOrigin = warehouseIdOrigin;
	}

	@Column(name = "WAREHOUSE_ID_DESTINATION")
	public Integer getWarehouseIdDestination() {
		return this.warehouseIdDestination;
	}

	public void setWarehouseIdDestination(Integer warehouseIdDestination) {
		this.warehouseIdDestination = warehouseIdDestination;
	}

	@Column(name = "INVOICE_ID")
	public Integer getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "CASHIER_INVOICE_ID")
	public Integer getCashierInvoiceId() {
		return this.cashierInvoiceId;
	}

	public void setCashierInvoiceId(Integer cashierInvoiceId) {
		this.cashierInvoiceId = cashierInvoiceId;
	}

	@Column(name = "BILL_OF_RECEIPT_ID")
	public Integer getBillOfReceiptId() {
		return this.billOfReceiptId;
	}

	public void setBillOfReceiptId(Integer billOfReceiptId) {
		this.billOfReceiptId = billOfReceiptId;
	}

	@Column(name = "TYPE_CODE", length = 10)
	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "TRANSFER_ORDER_NUMBER")
	public Integer getTransferOrderNumber() {
		return this.transferOrderNumber;
	}

	public void setTransferOrderNumber(Integer transferOrderNumber) {
		this.transferOrderNumber = transferOrderNumber;
	}

	@Column(name = "TRANSFER_ORDER_DATE")
	public LocalDateTime getTransferOrderDate() {
		return this.transferOrderDate;
	}

	public void setTransferOrderDate(LocalDateTime transferOrderDate) {
		this.transferOrderDate = transferOrderDate;
	}

	@Column(name = "DUE_DATE")
	public LocalDateTime getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	/***
	@Column(name = "COMPLETED_DATE")
	public LocalDateTime getCompletedDate() {
		return this.completedDate;
	}

	public void setCompletedDate(LocalDateTime completedDate) {
		this.completedDate = completedDate;
	}

	@Column(name = "COMPLETED_BY_USERID")
	public Integer getCompletedByUserId() {
		return this.completedByUserId;
	}

	public void setCompletedByUserId(Integer completedByUserId) {
		this.completedByUserId = completedByUserId;
	}
	***/

	@Column(name = "DELIVERED_DATE")
	public LocalDateTime getDeliveredDate() {
		return this.deliveredDate;
	}

	public void setDeliveredDate(LocalDateTime deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	@Column(name = "DELIVERED_BY_USERID")
	public Integer getDeliveredByUserId() {
		return this.deliveredByUserId;
	}

	public void setDeliveredByUserId(Integer deliveredByUserId) {
		this.deliveredByUserId = deliveredByUserId;
	}

	@Column(name = "SEPARATION_START_DATE")
	public LocalDateTime getSeparationStartDate() {
		return separationStartDate;
	}

	public void setSeparationStartDate(LocalDateTime separationStartDate) {
		this.separationStartDate = separationStartDate;
	}

	@Column(name = "SEPARATION_COMPLETED_DATE")
	public LocalDateTime getSeparationCompletedDate() {
		return separationCompletedDate;
	}

	public void setSeparationCompletedDate(LocalDateTime separationCompletedDate) {
		this.separationCompletedDate = separationCompletedDate;
	}

	@Column(name = "SEPARATION_BY_USERID")
	public Integer getSeparationByUserId() {
		return separationByUserId;
	}

	public void setSeparationByUserId(Integer separationByUserId) {
		this.separationByUserId = separationByUserId;
	}

	@Column(name = "SCANNING_START_DATE")
	public LocalDateTime getScanningStartDate() {
		return scanningStartDate;
	}

	public void setScanningStartDate(LocalDateTime scanningStartDate) {
		this.scanningStartDate = scanningStartDate;
	}

	@Column(name = "SCANNING_COMPLETED_DATE")
	public LocalDateTime getScanningCompletedDate() {
		return scanningCompletedDate;
	}

	public void setScanningCompletedDate(LocalDateTime scanningCompletedDate) {
		this.scanningCompletedDate = scanningCompletedDate;
	}

	@Column(name = "SCANNING_BY_USERID")
	public Integer getScanningByUserId() {
		return scanningByUserId;
	}

	public void setScanningByUserId(Integer scanningByUserId) {
		this.scanningByUserId = scanningByUserId;
	}

	public LocalDateTime getPackingStartDate() {
		return packingStartDate;
	}

	@Column(name = "PACKING_START_DATE")
	public void setPackingStartDate(LocalDateTime packingStartDate) {
		this.packingStartDate = packingStartDate;
	}

	@Column(name = "PACKING_COMPLETED_DATE")
	public LocalDateTime getPackingCompletedDate() {
		return packingCompletedDate;
	}

	public void setPackingCompletedDate(LocalDateTime packingCompletedDate) {
		this.packingCompletedDate = packingCompletedDate;
	}

	@Column(name = "PACKING_BY_USERID")
	public Integer getPackingByUserId() {
		return packingByUserId;
	}

	public void setPackingByUserId(Integer packingByUserId) {
		this.packingByUserId = packingByUserId;
	}

	@Column(name = "STATUS_ID")
	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne
	@JoinColumn (name = "INVOICE_ID", insertable = false, updatable = false)
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@OneToMany
	@JoinColumn(name = "TRANSFER_ORDER_ID", insertable = false, updatable = false)
	public List<TransferOrderDetail> getTransferOrderDetail() {
		return transferOrderDetail;
	}

	public void setTransferOrderDetail(List<TransferOrderDetail> transferOrderDetail) {
		this.transferOrderDetail = transferOrderDetail;
	}

	//-------------------------------------------------
	@Transient
	public Integer getYear() {
		return transferOrderDate == null ? 0 : transferOrderDate.getYear();
	}
	
	@Transient
	public String getDispatchNote() {
		return invoice != null ? invoice.getInvoiceType() + " " + invoice.getDispatchNoteId() : "";
	}

	@Transient
	public String getClientName() {
		return invoice != null ? invoice.getClient().getName() : "";
	}

	@Transient
	public Integer getTotalQty() {
		Integer res = null;
		if (transferOrderDetail != null) { 
			res = transferOrderDetail.stream().map(el -> el.getQty()).reduce(0, (x, y) -> x + y);
		}
		return res;
	}
	
	@Transient
	public Integer getTotalIssuedQty() {
		Integer res = null;
		if (transferOrderDetail != null) { 
			res = transferOrderDetail.stream().map(el -> el.getIssuedQty()).reduce(0, (x, y) -> x + y);
		}
		return res;
	}

	@Transient
	public ApplicationUserName getInvoiceCreatedBy () {
		return invoice != null ? invoice.getUserCreatedBy() : null;
	}
	
	@Transient
	public LocalDateTime getInvoiceCreatedDate () {
		return invoice != null ? invoice.getCreatedDate() : null;
	}
	
	@Transient
	public ApplicationUserName getInvoiceVerifiedBy () {
		return invoice != null ? invoice.getUserVerifiedBy() : null;
	}
	
	@Transient
	public String getInvoiceVerifiedByUserName () {
		return invoice != null && invoice.getUserVerifiedBy() != null ? invoice.getUserVerifiedBy().getName() : null;
	}
	
}
