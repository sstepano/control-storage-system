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
@Table(name = "dbini", catalog = "rm")
public class Dbini implements java.io.Serializable {

	private Integer id;
	private String attributeName;
	private String parameterName;
	private String value;
	private String description;
	private boolean isActive;
	private boolean isDeleted;
	private Integer lastModifiedBy;
	private LocalDateTime lastModifiedTimestamp;

	public Dbini() {}

	public Dbini(String attributeName, String value, boolean isActive, boolean isDeleted) {
		this.attributeName = attributeName;
		this.value = value;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
	}

	public Dbini(String attributeName, String parameterName, String value, String description, boolean isActive,
			boolean isDeleted, Integer lastModifiedBy, LocalDateTime lastModifiedTimestamp) {
		this.attributeName = attributeName;
		this.parameterName = parameterName;
		this.value = value;
		this.description = description;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedTimestamp = lastModifiedTimestamp;
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

	@Column(name = "ATTRIBUTE_NAME", nullable = false, length = 30)
	public String getAttributeName() {
		return this.attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	@Column(name = "PARAMETER_NAME", length = 30)
	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	@Column(name = "VALUE", nullable = false, length = 1000)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "DESCRIPTION", length = 50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "IS_ACTIVE", nullable = false)
	public boolean isIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "IS_DELETED", nullable = false)
	public boolean isIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "LAST_MODIFIED_BY")
	public Integer getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(Integer lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Column(name = "LAST_MODIFIED_TIMESTAMP", length = 19)
	public LocalDateTime getLastModifiedTimestamp() {
		return this.lastModifiedTimestamp;
	}

	public void setLastModifiedTimestamp(LocalDateTime lastModifiedTimestamp) {
		this.lastModifiedTimestamp = lastModifiedTimestamp;
	}

}
