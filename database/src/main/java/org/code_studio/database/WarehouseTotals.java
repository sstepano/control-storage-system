package org.code_studio.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//ARTIFICIAL POJO
@SuppressWarnings("serial")
@Entity
public class WarehouseTotals implements java.io.Serializable {

	private Integer id;
	private Integer occupiedCnt;
	private Integer occupiedPct;
	private Integer freeCnt;
	private Integer freePct;
	

	public WarehouseTotals() {}

	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOccupiedCnt() {
		return occupiedCnt;
	}

	public void setOccupiedCnt(Integer occupiedCnt) {
		this.occupiedCnt = occupiedCnt;
	}

	public Integer getOccupiedPct() {
		return occupiedPct;
	}

	public void setOccupiedPct(Integer occupiedPct) {
		this.occupiedPct = occupiedPct;
	}

	public Integer getFreeCnt() {
		return freeCnt;
	}

	public void setFreeCnt(Integer freeCnt) {
		this.freeCnt = freeCnt;
	}

	public Integer getFreePct() {
		return freePct;
	}

	public void setFreePct(Integer freePct) {
		this.freePct = freePct;
	}


	
}
