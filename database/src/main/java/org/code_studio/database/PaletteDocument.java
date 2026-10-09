package org.code_studio.database;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "PALETTE_DOCUMENT", schema = "PUBLIC", catalog = "RM")
public class PaletteDocument implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private Integer typeId;
	private PaletteDocumentType paletteDocumentType;
	private Integer craneId;
	private Integer statusId;
	private LocalDate createDate;
	private LocalDateTime processDate;
	private PaletteDocumentStatus paletteDocumentStatus; 

	
	private Client client;
	
	public PaletteDocument() {
	}

	public PaletteDocument(Integer clientId, Integer typeId, Integer statusId, LocalDate createDate) {
		this.clientId = clientId;
		this.typeId = typeId;
		this.statusId = statusId;
		this.createDate = createDate;
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

	@Column(name = "CLIENT_ID", insertable = false, updatable = false)
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Column(name = "TYPE_ID")
	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@OneToOne
	@JoinColumn(name = "TYPE_ID", insertable = false, updatable = false)
	public PaletteDocumentType getPaletteDocumentType() {
		return paletteDocumentType;
	}

	public void setPaletteDocumentType(PaletteDocumentType paletteDocumentType) {
		this.paletteDocumentType = paletteDocumentType;
	}

	public Integer getCraneId() {
		return craneId;
	}

	public void setCraneId(Integer craneId) {
		this.craneId = craneId;
	}

	@Column(name = "STATUS_ID")
	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Column(name = "CREATE_DATE", length = 10)
	public LocalDate getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getProcessDate() {
		return processDate;
	}

	public void setProcessDate(LocalDateTime processDate) {
		this.processDate = processDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATUS_ID", insertable = false, updatable = false)
	public PaletteDocumentStatus getPaletteDocumentStatus() {
		return paletteDocumentStatus;
	}

	public void setPaletteDocumentStatus(PaletteDocumentStatus paletteDocumentStatus) {
		this.paletteDocumentStatus = paletteDocumentStatus;
	}
	
	@Transient
	public String getPaletteDocumentStatusName() {
		return this.getPaletteDocumentStatus() != null ? this.getPaletteDocumentStatus().getName() : ""; 
	}
	
	@Transient
	public String getPaletteDocumentTypeName() {
		return this.getPaletteDocumentType() != null ? this.getPaletteDocumentType().getName() : ""; 
	}

}
