package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemCatalog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemCatalogViewModelPageable extends PagingAndSortingRepository <ItemCatalog, Integer> {
	List <IItemCatalogView> findAllByClientId(Integer clientId, Pageable pageable);
}
