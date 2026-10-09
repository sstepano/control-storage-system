package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties({"hibernateLazyInitializer", "inspection"}) /* TODO: Ovo moram da vidim zasto radi sa ovim annotationom, bez njega javlja gresku*/
@Entity
@Table(name = "CLIENT_SALE_OFFICER_LINK", schema = "PUBLIC", catalog = "RM")
public class ClientSaleOfficerLink implements java.io.Serializable {

	private Integer id;
	private int clientId;
	private boolean isPrimary;
	private Integer saleOfficerId;
	//private String saleOfficerName = "VESTACKO IME";
	
	@JsonBackReference(value="Back:ClientSaleOfficerLink<--Client")
	private Client client;
	
	//@JsonBackReference 
	private ApplicationUser applicationUser;

	public ClientSaleOfficerLink() {
	}

	public ClientSaleOfficerLink(int clientId, boolean isPrimary, Integer saleOfficerId) {
		this.clientId = clientId;
		this.isPrimary = isPrimary;
		this.saleOfficerId = saleOfficerId;
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

	@Column(name = "CLIENT_ID", nullable = false, insertable = false, updatable = false)
	public int getClientId() {
		return this.clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Column(name = "IS_PRIMARY", nullable = false)
	public boolean isIsPrimary() {
		return this.isPrimary;
	}

	public void setIsPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	@Column(name = "SALE_OFFICER_ID", nullable = false, insertable = false, updatable = false)
	public Integer getSaleOfficerId() {
		return this.saleOfficerId;
	}

	public void setSaleOfficerId(Integer saleOfficerId) {
		this.saleOfficerId = saleOfficerId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@PrimaryKeyJoinColumn
	//@JoinColumn(name = "CLIENT_ID")
	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	

	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@PrimaryKeyJoinColumn
	@JoinColumn(name = "SALE_OFFICER_ID")
	public ApplicationUser getApplicationUser() {
		return this.applicationUser;
	}

	public void setApplicationUser(ApplicationUser applicationUser) {
		this.applicationUser = applicationUser;
	}

	/*
	@Transient
	public String getSalesOfficerName() {
		//return this.saleOfficerName;
		return this.applicationUser.getName();
	}
	
	public void setSalesOfficerName(String saleOfficerName) {
		this.saleOfficerName = saleOfficerName;
	}
	*/

}
