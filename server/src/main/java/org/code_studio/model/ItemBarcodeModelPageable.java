package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemBarcode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemBarcodeModelPageable extends PagingAndSortingRepository <ItemBarcode, Integer> {
		
	@Query("select ib from ItemBarcode ib")
	List <ItemBarcode> findAllPageable(Pageable pageable);

	@Query("select ib from ItemBarcode ib where itemId = :itemId")
	List <ItemBarcode> findAllByItemId(Integer itemId, Pageable pageable);
	
	//List <ItemBarcode> findAllByBarcodeContaining(String barcode, Pageable pageable); // Ovo vraca %LIKE% iz db. Ubaciti samo ako B. eksplicitno trazi
	List <ItemBarcode> findAllByBarcode(String barcode, Pageable pageable);
	
	
	@Query(value="from ItemBarcode ib inner join Item i on ib.itemId = i.id where i.clientId = :clientId", nativeQuery=false)
	List <ItemBarcode> findAllByClientId(Integer clientId, Pageable pageable);

}
