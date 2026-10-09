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
public class ClientContactName implements java.io.Serializable {

	private Integer id;
	private Integer clientId;
	private String name;
	private String phone;
	
	public ClientContactName() {}

	public ClientContactName(Integer clientId, String name, String phone) {
		this.clientId = clientId;
		this.name = name;
		this.phone = phone;
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

	@Column(name = "CLIENT_ID")
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

	@Column(name = "PHONE", length = 1000)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
