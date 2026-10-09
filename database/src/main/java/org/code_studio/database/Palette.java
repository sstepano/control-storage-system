package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "PALETTE", schema = "PUBLIC", catalog = "RM")
public class Palette implements java.io.Serializable {

	private Integer id;
	private Integer documentId;
	private String paletteCode;
	private String clientPaletteCode;
	private String rowId;
	private String shelfId;
	private String verticalId;
	private String name;
	private String barcode;
	/**private Integer weight; **/
	private Integer length;
	private Integer width;
	private Integer height;
	private Integer craneId;
	private Integer statusId;
	private Integer previousDocumentId;
	private PaletteStatus paletteStatus;
	
	private List<PaletteItem> paletteItem;
	
	private PaletteDocument paletteDocument;

	public Palette() {
	}

	public Palette(Integer documentId, String paletteCode, String clientPaletteCode, String rowId, String shelfId,
			String verticalId, String name, String barcode, /**Integer weight,**/ Integer length,
			Integer width, Integer height, Integer statusId) {
		this.documentId = documentId;
		this.paletteCode = paletteCode;
		this.clientPaletteCode = clientPaletteCode;
		this.rowId = rowId;
		this.shelfId = shelfId;
		this.verticalId = verticalId;
		this.name = name;
		this.barcode = barcode;
		/**this.weight = weight;**/
		this.length = length;
		this.width = width;
		this.height = height;
		this.statusId = statusId;
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

	@Column(name = "DOCUMENT_ID")
	public Integer getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	
	@ManyToOne
	@JoinColumn(name = "DOCUMENT_ID", insertable = false, updatable = false)
	public PaletteDocument getPaletteDocument() {
		return this.paletteDocument;
	}

	public void setPaletteDocument(PaletteDocument paletteDocument) {
		this.paletteDocument = paletteDocument;
	}

	@Column(name = "PALETTE_CODE", length = 10)
	public String getPaletteCode() {
		return this.paletteCode;
	}

	public void setPaletteCode(String paletteCode) {
		this.paletteCode = paletteCode;
	}

	@Column(name = "CLIENT_PALETTE_CODE", length = 30)
	public String getClientPaletteCode() {
		return this.clientPaletteCode;
	}

	public void setClientPaletteCode(String clientPaletteCode) {
		this.clientPaletteCode = clientPaletteCode;
	}

	@Column(name = "ROW_ID", length = 5)
	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	@Column(name = "SHELF_ID", length = 5)
	public String getShelfId() {
		return this.shelfId;
	}

	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}

	@Column(name = "VERTICAL_ID", length = 5)
	public String getVerticalId() {
		return this.verticalId;
	}

	public void setVerticalId(String verticalId) {
		this.verticalId = verticalId;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "BARCODE", length = 100)
	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	@Column(name = "WEIGHT")
	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	**/

	@Column(name = "LENGTH")
	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Column(name = "WIDTH")
	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "HEIGHT")
	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "STATUS_ID", updatable = false, insertable = false)
	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Column(name = "PREVIOUS_DOCUMENT_ID")
	public Integer getPreviousDocumentId() {
		return previousDocumentId;
	}

	public void setPreviousDocumentId(Integer previousDocumentId) {
		this.previousDocumentId = previousDocumentId;
	}

	public Integer getCraneId() {
		return craneId;
	}

	public void setCraneId(Integer craneId) {
		this.craneId = craneId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATUS_ID")
	public PaletteStatus getPaletteStatus() {
		return paletteStatus;
	}

	public void setPaletteStatus(PaletteStatus paletteStatus) {
		this.paletteStatus = paletteStatus;
	}
	
	@OneToMany
	@JoinColumn(name = "PALETTE_ID", insertable = false, updatable = false)
	public List<PaletteItem> getPaletteItem() {
		return paletteItem;
	}

	public void setPaletteItem(List<PaletteItem> paletteItem) {
		this.paletteItem = paletteItem;
	}

	//--------------------------------------------------------------
	@Transient
	public String getPaletteStatusName() {
		return this.getPaletteStatus() == null ? "" : this.getPaletteStatus().getName();
	}
	
	@Transient
	public Integer getRowOrdinalValue() {
		return Integer.valueOf(this.getRowId());
	}
	
	@Transient
	public Integer getShelfOrdinalValue() {
		return Integer.valueOf(this.getShelfId());
	}
	
	@Transient
	public Integer getVerticalOrdinalValue() {
		return Integer.valueOf(this.getVerticalId());
	}
	
	@Transient
	public LocalDateTime getProcessDate() {
		return this.paletteDocument.getProcessDate();
	}

}
