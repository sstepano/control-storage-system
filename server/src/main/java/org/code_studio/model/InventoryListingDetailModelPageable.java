package org.code_studio.model;

import java.util.List;

import org.code_studio.database.InventoryListingDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryListingDetailModelPageable extends PagingAndSortingRepository <InventoryListingDetail, Integer> {
		
	@Query("select ild from InventoryListingDetail ild")
	List <InventoryListingDetail> findAllPageable(Pageable pageable);
	List <InventoryListingDetail> findAllByInventoryListingIdOrderByIdDesc(int inventoryListingId, Pageable pageable);
	
	@Query("""
			select ild from InventoryListingDetail ild
			join InventoryListing i on ild.inventoryListingId = i.id
			join ItemBarcode ib on ib.itemId = ild.itemId
			join ItemWarehouse iw on iw.warehouseId = i.warehouseId and iw.itemId = ild.itemId
			where 1=1
			    and ild.inventoryListingId = :inventoryListingId
				and 
					(cast(ild.itemId as string) like '%' || upper(:searchKeyword ) || '%'
				        OR upper(ib.barcode) like '%' || upper(:searchKeyword ) || '%'
						OR upper(iw.rowId) like '%' || upper(:searchKeyword ) || '%'
					)
			order by ild.id
	""")
	List <InventoryListingDetail> search(int inventoryListingId, String searchKeyword, Pageable pageable);
	
	String qry1 = """
			select ild.* from Inventory_Listing_Detail ild
			join Inventory_Listing il on ild.inventory_listing_id = il.id
			where 1=1
			  and il.warehouse_id = :warehouseId
			  and ild.created_by_user_id = :createdByUserId
	""";
	@Query(value = qry1, nativeQuery = true)
	List <InventoryListingDetail> findAllByWarehouseIdAndCreatedByUserId(int warehouseId, int createdByUserId, Pageable pageable);
}
