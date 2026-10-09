package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Offer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OfferModelPageable extends PagingAndSortingRepository <Offer, Integer> {
	//------------------------------------------------------------------------	
	@Query("select o from Offer o")
	List <Offer> findAllPageable(Pageable pageable);
	//------------------------------------------------------------------------
	@Query("select o from Offer o where clientId = :clientId order by id desc")
	List <Offer> findAllPageableByClientId(Integer clientId, Pageable pageable);
}
