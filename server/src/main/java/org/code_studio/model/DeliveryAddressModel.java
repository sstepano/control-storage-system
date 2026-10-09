package org.code_studio.model;

import java.util.List;

import org.code_studio.database.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryAddressModel extends JpaRepository <DeliveryAddress, Integer> {
	
	List<DeliveryAddress> findAllByClientId(Integer clientId);
	
	
	String qry1 = """
		select y.ID, y.TYPE_ID, y.CLIENT_ID, y.ADDRESS, y.CITY, y.COUNTRY_ID
		from (
			select
			    ifnull(d.ID, x.TYPE_ID) ID, x.TYPE_ID, ifnull(d.CLIENT_ID, :clientId) CLIENT_ID, d.ADDRESS, d.CITY, d.COUNTRY_ID, dense_rank() over (partition by x.type_id order by d.id) dense_rank
			from (
			select distinct 
			'ROBA' TYPE_NAME, 1 TYPE_ID
			union select
			'POSTA' TYPE_NAME, 2 TYPE_ID
			union select
			'PRODAVNICA' TYPE_NAME, 3 TYPE_ID
			) x 
			left join delivery_address d on x.TYPE_ID = d.TYPE_ID and client_id = :clientId
		) y where y.dense_rank = 1
		ORDER BY TYPE_ID
		""";
	@Query(value = qry1, nativeQuery = true)
	List<DeliveryAddress> findAllCompositeByClientId(Integer clientId);
	
	
	String qry2 = """
			select d
			from DeliveryAddress d
			where 1=1
			    and d.clientId = :clientId
			    and d.typeId = 1
			""";
	@Query(value = qry2, nativeQuery = false)
	List<DeliveryAddress> findDeliveryAddressByClientId(Integer clientId);
}
