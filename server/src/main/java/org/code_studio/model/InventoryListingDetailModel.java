package org.code_studio.model;

import java.util.List;

import org.code_studio.database.InventoryListingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryListingDetailModel extends JpaRepository <InventoryListingDetail, Integer> {
	List <InventoryListingDetail> findAllByInventoryListingId(int inventoryListingId);
	//List <InventoryListingDetail> findAllByItemIdAndRowIdAndShelfIdAndVerticalIdAndCountedQtyIsNotNull(int itemId, String rowId, String shelfId, String verticalId);
	List <InventoryListingDetail> findAllByItemIdAndRowIdAndShelfIdAndVerticalId(int itemId, String rowId, String shelfId, String verticalId);
}
