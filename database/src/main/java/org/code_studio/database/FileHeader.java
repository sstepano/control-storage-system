package org.code_studio.database;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "file_header", catalog = "rm")
public class FileHeader implements java.io.Serializable {

	private Integer id;
	private Integer fileTypeId;
	private String fileName;
	private String description;
	private Date createdDatetime;
	private String fileContent;

	public FileHeader() {
	}

	public FileHeader(Integer fileTypeId, String fileName, String description, Date createdDatetime,
			String fileContent) {
		this.fileTypeId = fileTypeId;
		this.fileName = fileName;
		this.description = description;
		this.createdDatetime = createdDatetime;
		this.fileContent = fileContent;
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

	@Column(name = "FILE_TYPE_ID")
	public Integer getFileTypeId() {
		return this.fileTypeId;
	}

	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	@Column(name = "FILE_NAME", length = 100)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "CREATED_DATETIME", length = 19)
	public Date getCreatedDatetime() {
		return this.createdDatetime;
	}

	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	@Column(name = "FILE_CONTENT", length = 65535)
	public String getFileContent() {
		return this.fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

}
