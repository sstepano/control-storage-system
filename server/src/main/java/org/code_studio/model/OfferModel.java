package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferModel extends JpaRepository <Offer, Integer> {
	List <Offer> findAllByClientId(Integer clientId);
}
