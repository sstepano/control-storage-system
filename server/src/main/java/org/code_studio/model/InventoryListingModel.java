package org.code_studio.model;

import java.util.List;

import org.code_studio.database.InventoryListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryListingModel extends JpaRepository <InventoryListing, Integer> {
	List <InventoryListing> findAllByWarehouseId(int warehouseId);
}
