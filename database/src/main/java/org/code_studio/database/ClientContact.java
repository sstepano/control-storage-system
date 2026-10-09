package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CLIENT_CONTACT", schema = "PUBLIC", catalog = "RM")
public class ClientContact implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private String name;
	private String title;
	private String identificationId;
	private String phone;
	private String mobile;
	private String fax;
	private String email;
	private Boolean includeInAttachment;
	private Boolean includeInEmailCc;
	private String description;
	
	public ClientContact() {}

	public ClientContact(/*Client client,*/ Integer clientId, String name, String title, String identificationId, String phone, String mobile,
			String fax, String email, Boolean includeInAttachment, Boolean includeInEmailCc,
			String description) {
		//this.client = client;
		this.clientId = clientId;
		this.name = name;
		this.title = title;
		this.identificationId = identificationId;
		this.phone = phone;
		this.mobile = mobile;
		this.fax = fax;
		this.email = email;
		this.includeInAttachment = includeInAttachment;
		this.includeInEmailCc = includeInEmailCc;
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

	
	@Column(name = "CLIENT_ID")//, updatable = false, insertable = false)
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "NAME", length = 1000)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "IDENTIFICATION_ID", length = 100)
	public String getIdentificationId() {
		return this.identificationId;
	}

	public void setIdentificationId(String identificationId) {
		this.identificationId = identificationId;
	}

	@Column(name = "PHONE", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "MOBILE", length = 100)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "FAX", length = 100)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "EMAIL", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "INCLUDE_IN_ATTACHMENT")
	public Boolean getIncludeInAttachment() {
		return this.includeInAttachment;
	}

	public void setIncludeInAttachment(Boolean includeInAttachment) {
		this.includeInAttachment = includeInAttachment;
	}

	@Column(name = "INCLUDE_IN_EMAIL_CC")
	public Boolean getIncludeInEmailCc() {
		return this.includeInEmailCc;
	}

	public void setIncludeInEmailCc(Boolean includeInEmailCc) {
		this.includeInEmailCc = includeInEmailCc;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
