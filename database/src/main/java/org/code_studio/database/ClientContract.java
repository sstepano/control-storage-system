package org.code_studio.database;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CLIENT_CONTRACT", schema = "PUBLIC", catalog = "RM")
public class ClientContract implements java.io.Serializable {

	private Integer id;
	//@JsonBackReference
	private Integer clientId;
	private ClientName client;
	private String contractNumber;
	private LocalDate validFrom;
	private LocalDate validTo;
	private String description;

	public ClientContract() {
	}

	public ClientContract(ClientName client, String contractNumber, LocalDate validFrom, LocalDate validTo, String description) {
		this.client = client;
		this.contractNumber = contractNumber;
		this.validFrom = validFrom;
		this.validTo = validTo;
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
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false)
	public ClientName getClient() {
		return this.client;
	}

	public void setClient(ClientName client) {
		this.client = client;
	}

	@Column(name = "CONTRACT_NUMBER", length = 100)
	public String getContractNumber() {
		return this.contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	@Column(name = "VALID_FROM", length = 10)
	public LocalDate getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	@Column(name = "VALID_TO", length = 10)
	public LocalDate getValidTo() {
		return this.validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
