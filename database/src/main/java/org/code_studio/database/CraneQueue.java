package org.code_studio.database;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "crane_queue", catalog = "rm")
public class CraneQueue implements java.io.Serializable {

	private Integer id;
	private int inOut;
	private Integer craneId;
	private String paletteCode;
	private Integer clientId;
	private String source;
	private String target;
	private String currentPosition;
	private String nextPosition;
	private String lastStatus;
	private LocalDateTime modifiedDatetime;

	public CraneQueue() {
	}

	public CraneQueue(int craneId) {
		this.craneId = craneId;
	}

	public CraneQueue(int inOut, int craneId, String paletteCode, Integer clientId, String source, String target,
			String currentPosition, String nextPosition, String lastStatus, LocalDateTime modifiedDatetime) {
		this.inOut = inOut;
		this.craneId = craneId;
		this.paletteCode = paletteCode;
		this.clientId = clientId;
		this.source = source;
		this.target = target;
		this.currentPosition = currentPosition;
		this.nextPosition = nextPosition;
		this.lastStatus = lastStatus;
		this.modifiedDatetime = modifiedDatetime;
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

	@Column(name = "IN_OUT", nullable = false)
	public int getInOut() {
		return inOut;
	}

	public void setInOut(int inOut) {
		this.inOut = inOut;
	}

	@Column(name = "CRANE_ID", nullable = false)
	public int getCraneId() {
		return this.craneId;
	}

	public void setCraneId(int craneId) {
		this.craneId = craneId;
	}

	@Column(name = "PALETTE_CODE", length = 7)
	public String getPaletteCode() {
		return this.paletteCode;
	}

	public void setPaletteCode(String paletteCode) {
		this.paletteCode = paletteCode;
	}

	@Column(name = "CLIENT_ID")
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "SOURCE", length = 7)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "TARGET", length = 7)
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "CURRENT_POSITION", length = 7)
	public String getCurrentPosition() {
		return this.currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	@Column(name = "NEXT_POSITION", length = 7)
	public String getNextPosition() {
		return this.nextPosition;
	}

	public void setNextPosition(String nextPosition) {
		this.nextPosition = nextPosition;
	}

	@Column(name = "LAST_STATUS", length = 2)
	public String getLastStatus() {
		return this.lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	@Column(name = "MODIFIED_DATETIME")
	public LocalDateTime getModifiedDatetime() {
		return this.modifiedDatetime;
	}

	public void setModifiedDatetime(LocalDateTime modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

}
