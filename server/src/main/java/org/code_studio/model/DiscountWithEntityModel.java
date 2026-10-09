package org.code_studio.model;

import java.util.List;

import org.code_studio.database.DiscountWithEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DiscountWithEntityModel extends JpaRepository <DiscountWithEntity, Integer> {
	
	String query1 = """
			SELECT
			     d.ID
			  , d.CLIENT_ID
			  , d.VALID_FROM
			  , d.VALID_TO
			  , d.DISCOUNT_RATE
			  , d.DISCOUNT_GROUP_ID
			  , d.DISCOUNT_ENTITY_ID
			  , dg.NAME DISCOUNT_GROUP_NAME
			  , COALESCE(b.NAME, c.NAME, ib.NAME, ig.NAME, isg.NAME, it.NAME) DISCOUNT_ENTITY_NAME
			  , d.DESCRIPTION
			  , d.VALID_TO_ORIGINAL
			FROM discount d
			  JOIN discount_group dg ON d.DISCOUNT_GROUP_ID = dg.ID
			  LEFT JOIN brand b ON d.DISCOUNT_GROUP_ID = 1 and d.DISCOUNT_ENTITY_ID = b.ID
			  LEFT JOIN item_catalog c ON d.DISCOUNT_GROUP_ID = 2 and d.DISCOUNT_ENTITY_ID = c.ID
			  LEFT JOIN item_branch ib ON d.DISCOUNT_GROUP_ID = 3 and d.DISCOUNT_ENTITY_ID = ib.ID
			  LEFT JOIN item_group ig ON d.DISCOUNT_GROUP_ID = 4 and d.DISCOUNT_ENTITY_ID = ig.ID
			  LEFT JOIN item_subgroup isg ON d.DISCOUNT_GROUP_ID = 5 and d.DISCOUNT_ENTITY_ID = isg.ID
			  LEFT JOIN item_type it ON d.DISCOUNT_GROUP_ID = 6 and d.DISCOUNT_ENTITY_ID = it.ID
			WHERE 1=1
	""";
	
	@Query(value = query1, nativeQuery = true)
	List<DiscountWithEntity> findAll();
	
	String query2 = """
			SELECT
			     d.ID
			  , d.CLIENT_ID
			  , d.VALID_FROM
			  , d.VALID_TO
			  , d.DISCOUNT_RATE
			  , d.DISCOUNT_GROUP_ID
			  , d.DISCOUNT_ENTITY_ID
			  , dg.NAME DISCOUNT_GROUP_NAME
			  , COALESCE(b.NAME, c.NAME, ib.NAME, ig.NAME, isg.NAME, it.NAME) DISCOUNT_ENTITY_NAME
			  , d.DESCRIPTION
			  , d.VALID_TO_ORIGINAL
			FROM discount d
			  JOIN discount_group dg ON d.DISCOUNT_GROUP_ID = dg.ID
			  LEFT JOIN brand b ON d.DISCOUNT_GROUP_ID = 1 and d.DISCOUNT_ENTITY_ID = b.ID
			  LEFT JOIN item_catalog c ON d.DISCOUNT_GROUP_ID = 2 and d.DISCOUNT_ENTITY_ID = c.ID
			  LEFT JOIN item_branch ib ON d.DISCOUNT_GROUP_ID = 3 and d.DISCOUNT_ENTITY_ID = ib.ID
			  LEFT JOIN item_group ig ON d.DISCOUNT_GROUP_ID = 4 and d.DISCOUNT_ENTITY_ID = ig.ID
			  LEFT JOIN item_subgroup isg ON d.DISCOUNT_GROUP_ID = 5 and d.DISCOUNT_ENTITY_ID = isg.ID
			  LEFT JOIN item_type it ON d.DISCOUNT_GROUP_ID = 6 and d.DISCOUNT_ENTITY_ID = it.ID
			WHERE 1=1
			  and d.client_id = :clientId
	""";
	
	@Query(value = query2, nativeQuery = true)
	List<DiscountWithEntity> findAllByClientId(Integer clientId);
	
}
