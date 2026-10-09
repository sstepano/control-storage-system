package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CLIENT", schema = "PUBLIC", catalog = "RM")
public class ClientName implements java.io.Serializable {

	private Integer id;
	private String name;

	public ClientName() {}

	public ClientName(String name) {
		this.name = name;
	}

	/**
	 * Used in Finance BankStatementDetailAddEdit, this way we will remove dependency of saving entire client
	 * @param id
	 * @param name
	 */
	public ClientName(Integer id, String name) {
		this.id = id;
		this.name = name;
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

	@Column(name="NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
