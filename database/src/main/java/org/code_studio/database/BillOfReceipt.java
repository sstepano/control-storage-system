package org.code_studio.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "BILL_OF_RECEIPT", schema = "PUBLIC", catalog = "RM")
public class BillOfReceipt implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private ClientName client;
	private Integer orderId;
	private String billOfReceiptType;
	private LocalDate billOfReceiptDate;
	private String orderDescription;
	private String description;
	//private Integer lastModifiedById;
	private ApplicationUser lastModifiedBy;
	private LocalDateTime lastModifiedDate;

	public BillOfReceipt() {
	}

	public BillOfReceipt(Integer orderId, String billOfReceiptType, LocalDate billOfReceiptDate, String orderDescription,
			String description, ApplicationUser lastModifiedBy, LocalDateTime lastModifiedDate) {
		this.orderId = orderId;
		this.billOfReceiptType = billOfReceiptType;
		this.billOfReceiptDate = billOfReceiptDate;
		this.orderDescription = orderDescription;
		this.description = description;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
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
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false )
	public ClientName getClient() {
		return this.client;
	}

	public void setClient(ClientName client) {
		this.client = client;
	}

	@Column(name = "ORDER_ID")
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "BILL_OF_RECEIPT_TYPE", length = 2)
	public String getBillOfReceiptType() {
		return this.billOfReceiptType;
	}

	public void setBillOfReceiptType(String billOfReceiptType) {
		this.billOfReceiptType = billOfReceiptType;
	}

	//@Temporal(TemporalType.DATE)
	@Column(name = "BILL_OF_RECEIPT_DATE", length = 10)
	public LocalDate getBillOfReceiptDate() {
		return this.billOfReceiptDate;
	}

	public void setBillOfReceiptDate(LocalDate billOfReceiptDate) {
		this.billOfReceiptDate = billOfReceiptDate;
	}

	@Column(name = "ORDER_DESCRIPTION", length = 100)
	public String getOrderDescription() {
		return this.orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JoinColumn(name = "LAST_MODIFIED_BY_ID")
	@ManyToOne (fetch = FetchType.LAZY)
	public ApplicationUser getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(ApplicationUser lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED_DATE", length = 26)
	public LocalDateTime getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	//----------------------------------------------------
	@Transient
	public String getClientName() {
		return this.client== null ? "" : this.client.getName();
	}

	@Transient
	public String getYear() {
		return billOfReceiptDate == null ? "" : Integer.toString(billOfReceiptDate.getYear());
	}

	@Transient
	public String getLastModifiedUserName() {
		return this.lastModifiedBy== null ? "" : this.lastModifiedBy.getName();
	}

}
