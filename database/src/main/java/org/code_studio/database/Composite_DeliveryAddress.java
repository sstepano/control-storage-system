package org.code_studio.database;

import java.io.Serializable;

@SuppressWarnings({"serial", "unused"})
public class Composite_DeliveryAddress implements Serializable {

	private Integer id;
	private DeliveryAddress deliveryAddressGoods;
	private DeliveryAddress deliveryAddressPostal;
	private DeliveryAddress deliveryAddressSaleStore;
	
	private String objectType;
	private String objectValue;
	
	public Composite_DeliveryAddress() {}

	public Composite_DeliveryAddress(String strObjectType, String objectValue) {
		this.setObjectType(strObjectType);
		this.setObjectValue(objectValue);
	}

	public DeliveryAddress getDeliveryAddressGoods() {
		return deliveryAddressGoods;
	}

	public void setDeliveryAddressGoods(DeliveryAddress deliveryAddressGoods) {
		this.deliveryAddressGoods = deliveryAddressGoods;
	}

	public DeliveryAddress getDeliveryAddressPostal() {
		return deliveryAddressPostal;
	}

	public void setDeliveryAddressPostal(DeliveryAddress deliveryAddressPostal) {
		this.deliveryAddressPostal = deliveryAddressPostal;
	}

	public DeliveryAddress getDeliveryAddressSaleStore() {
		return deliveryAddressSaleStore;
	}

	public void setDeliveryAddressSaleStore(DeliveryAddress deliveryAddressSaleStore) {
		this.deliveryAddressSaleStore = deliveryAddressSaleStore;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}
	
	
}
