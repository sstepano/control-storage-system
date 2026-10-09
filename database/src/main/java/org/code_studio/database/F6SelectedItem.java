package org.code_studio.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class F6SelectedItem {

	private Integer id;
	private String name; // if item then it is CODE, othwewise it is NAME
	private Integer qty;	
	/**
	 *  0: ITEM
	 *  1: Client -> Catalog -> Branch -> SUBGROUP
	 *  2: Client -> Group -> SUBGROUP :: #2 is NOT IMPLEMENTED !!!
	 */
	private Integer typeId;
	private Object objectInstance;
	
	
	public F6SelectedItem() {}
	
	public F6SelectedItem(Integer id, String name, Integer qty, Integer typeId, Object objectInstance) {
		this.id = id;
		this.name = name;
		this.qty = qty;
		this.typeId = typeId;
		this.setObjectInstance(objectInstance);
	}
	

	@Id
	//@GeneratedValue(strategy = IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Object getObjectInstance() {
		return objectInstance;
	}

	public void setObjectInstance(Object objectInstance) {
		this.objectInstance = objectInstance;
	}


}
