package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "DELIVERY_ADDRESS", schema = "PUBLIC", catalog = "RM")
public class DeliveryAddress implements java.io.Serializable {

	private Integer id;
	private DeliveryAddressType deliveryAddressType;
	private Integer typeId;
	private Integer clientId;
	private ClientName client;
	
	private String address;
	private String city;
	private Country country;

	public DeliveryAddress() {}
	
	public DeliveryAddress(ClientName client) {
		this.client = client;
	}

	public DeliveryAddress(DeliveryAddressType deliveryAddressType, ClientName client, String address, String city) {
		this.deliveryAddressType = deliveryAddressType;
		this.client = client;
		this.address = address;
		this.city = city;
	}
	/*
	public DeliveryAddress(Integer deliveryAddressTypeId, Client client, String address, String city) {
		this.deliveryAddressTypeId = deliveryAddressTypeId;
		this.client = client;
		this.address = address;
		this.city = city;
	}
	*/

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
	@JoinColumn(name = "TYPE_ID", insertable = false, updatable = false)
	public DeliveryAddressType getDeliveryAddressType() {
		return this.deliveryAddressType;
	}

	public void setDeliveryAddressType(DeliveryAddressType deliveryAddressType) {
		this.deliveryAddressType = deliveryAddressType;
	}
	

	@Column(name = "TYPE_ID")
	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false)
	public ClientName getClient() {
		return this.client;
	}

	public void setClient(ClientName client) {
		this.client = client;
	}

	@Column(name = "ADDRESS", length = 1000)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "CITY", length = 100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	@Transient
	//Ja dodao ovaj property jer mi treba name u gridu a ne objekat
	public String getDeliveryAddressTypeName() {
		return this.deliveryAddressType.getName(); 
	}
	
	public void setDeliveryAddressTypeName(String deliveryAddressTypeName) {
		this.deliveryAddressType.setName(deliveryAddressTypeName);
	}
	
	@Transient
	public String getCountryName() {
		return this.country != null ? this.country.getName() : ""; 
	}
	
	public void setCountryName(String countryName) {
		if (this.country != null) {
			this.country.setName(countryName);
		}
	}
	
	@Transient
	public String getFullAddress() {
		return getAddress() 
			+ ", "
			+ getCity()
			// + ", "
			// + getCountryName()
			;
	}
}
