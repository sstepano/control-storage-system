package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/***
 * Used for as return type for business logic service class, that is being called
 * for all SPs that have some business logic, without returning existing POJO
 * Return type is just int as code 0 OK - 1 ERROR, and return description as string
 */

@SuppressWarnings("serial")
@Entity
public class StoredProcedureResult implements java.io.Serializable {

	private Integer id; // fake id, always 0
	private Integer resultCode;
	private String resultDescription;
	private String returnValue;
	
	public StoredProcedureResult(){}

	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "RESULT_CODE")
	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	@Column(name = "RESULT_DESCRIPTION")
	public String getResultDescription() {
		return resultDescription;
	}

	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

	/**
	 * Holds any object value that we want to return from SP
	 * @return
	 */
	@Column(name = "RETURN_VALUE")
	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}


}
