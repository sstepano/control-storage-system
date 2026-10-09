package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "APPLICATION_USER", schema = "PUBLIC", catalog = "RM")
public class ApplicationUserName implements java.io.Serializable {

	private Integer id;
	private String username;
	private String name;
	private Integer roleId;

	public ApplicationUserName() {}

	public ApplicationUserName(String username, String name, Integer roleId) {
		this.username = username;
		this.name = name;
		this.roleId = roleId;
	}
	
	//used in salePlanner
	public ApplicationUserName(Integer id, String username, String name, Integer roleId) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.roleId = roleId;
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

	@Column(name = "USERNAME", length = 100)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ROLE_ID")
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
}