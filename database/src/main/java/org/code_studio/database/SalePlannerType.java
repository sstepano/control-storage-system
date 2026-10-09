package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties({"hibernateLazyInitializer"}) 
@Entity
@Table(name = "SALE_PLANNER_TYPE", schema = "PUBLIC", catalog = "RM")
public class SalePlannerType implements java.io.Serializable {

	private Integer id;
	private String code;
	private String name;
	private String description;
	
	@JsonBackReference(value="Back:salePlannerType->salePlanner")
	private List <SalePlanner> salePlanner;

	public SalePlannerType() {
	}

	public SalePlannerType(String code) {
		this.code = code;
	}

	public SalePlannerType(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * This constructor is used for manual creation of SalePlannerType.
	 * Not used for database update, just for populating SalePlanner info 
	 */
	public SalePlannerType(Long id, String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
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

	@Column(name = "CODE", nullable = false, length = 3)
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

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "salePlannerType")
	public List <SalePlanner> getSalePlanner() {
		return this.salePlanner;
	}

	public void setSalePlanner( List <SalePlanner> salePlanner) {
		this.salePlanner = salePlanner;
	}

}
