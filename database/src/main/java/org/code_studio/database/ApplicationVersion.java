package org.code_studio.database;

import jakarta.persistence.Entity;
import java.time.LocalDate;

import jakarta.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class ApplicationVersion implements java.io.Serializable {
	
	public ApplicationVersion() {}

	public ApplicationVersion(String version, Integer buildNumber, LocalDate buildDate) {
		super();
		this.version = version;
		this.buildNumber = buildNumber;
		this.buildDate = buildDate;
	}

	private Integer id = 1;
	private String version;
	private Integer buildNumber;
	private LocalDate buildDate;

	
	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(Integer buildNumber) {
		this.buildNumber = buildNumber;
	}

	public LocalDate getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(LocalDate buildDate) {
		this.buildDate = buildDate;
	}

	
}
