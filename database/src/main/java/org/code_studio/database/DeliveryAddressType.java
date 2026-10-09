package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties({"hibernateLazyInitializer"}) /* TODO: Ovo moram da vidim zasto radi sa ovim annotationom, bez njega javlja gresku*/
@Entity
@Table(name = "DELIVERY_ADDRESS_TYPE", schema = "PUBLIC", catalog = "RM")
public class DeliveryAddressType implements java.io.Serializable {

	private Integer id;
	private String name;
	private String description;
	@JsonBackReference(value="Back:deliveryAddressType->deliveryAddresses") private List<DeliveryAddress> deliveryAddresses;

	public DeliveryAddressType() {}

	public DeliveryAddressType(String name, String description, List<DeliveryAddress> deliveryAddresses) {
		this.name = name;
		this.description = description;
		this.deliveryAddresses = deliveryAddresses;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryAddressType")
	public List<DeliveryAddress> getDeliveryAddresses() {
		return this.deliveryAddresses;
	}

	public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
		this.deliveryAddresses = deliveryAddresses;
	}

}
