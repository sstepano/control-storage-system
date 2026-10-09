package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@SuppressWarnings("serial")
@Entity
@Table(name = "item_client", catalog = "rm", uniqueConstraints = @UniqueConstraint(columnNames = { "ITEM_ID", "CLIENT_ID" }))
public class ItemClient implements java.io.Serializable {

	private Integer id;
	private Integer itemId;
	private Integer clientId;
	private String  itemIdClient;

	public ItemClient() {}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ITEM_ID")
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "CLIENT_ID")
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "ITEM_ID_CLIENT", length = 30)
	public String getItemIdClient() {
		return this.itemIdClient;
	}

	public void setItemIdClient(String itemIdClient) {
		this.itemIdClient = itemIdClient;
	}

}
