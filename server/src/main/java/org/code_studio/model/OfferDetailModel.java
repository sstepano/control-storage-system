package org.code_studio.model;

import java.util.List;

import org.code_studio.database.OfferDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferDetailModel extends JpaRepository <OfferDetail, Integer> {
	List <OfferDetail> findAllByOfferId(Integer offerId);

}
