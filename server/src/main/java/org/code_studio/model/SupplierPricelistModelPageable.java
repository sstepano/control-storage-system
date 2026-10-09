package org.code_studio.model;

import java.util.List;

import org.code_studio.database.SupplierPricelist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SupplierPricelistModelPageable extends PagingAndSortingRepository <SupplierPricelist, Integer> {

	List<SupplierPricelist> findAllByClientId(Integer clientId, Pageable pageable);
	List<SupplierPricelist> findAllByItemId(Integer itemId, Pageable pageable);
}
