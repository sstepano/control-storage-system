package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "internet_catalog", catalog = "rm")
public class InternetCatalog implements java.io.Serializable {

	private Integer id;
	private String code;
	private String name;
	private Integer parentId;
	private Integer ordinalOrder;

	public InternetCatalog() {}

	public InternetCatalog(String code, String name, Integer parentId, Integer ordinalOrder) {
		this.code = code;
		this.name = name;
		this.parentId = parentId;
		this.ordinalOrder = ordinalOrder;
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

	@Column(name = "CODE", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PARENT_ID")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "ORDINAL_ORDER")
	public Integer getOrdinalOrder() {
		return this.ordinalOrder;
	}

	public void setOrdinalOrder(Integer ordinalOrder) {
		this.ordinalOrder = ordinalOrder;
	}

	// -----------------------------------------
	@Transient
	public String getCodeAndName() {
		return this.code + " - " + this.name;
	}

}
