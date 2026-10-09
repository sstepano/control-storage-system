package org.code_studio.model;

import java.math.BigDecimal;
import java.util.List;

import org.code_studio.database.ClientPricelist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClientPricelistModel extends JpaRepository <ClientPricelist, Integer> {
	
	List<ClientPricelist> findAllByClientId(Integer clientId);
	List<ClientPricelist> findAllByClientIdAndItemId(Integer clientId, Integer itemId);

	String qry1 = """
			update ClientPricelist cp
			set 
				discountRate = :discountRate
			where 1=1
				and cp.clientId = :clientId
		""";
	@Transactional
	@Modifying
	@Query(value=qry1, nativeQuery=false)
	Object updateDiscountRateByClientId(Integer clientId, BigDecimal discountRate);
	
	
	String qry2 = """
					update ClientPricelist cp set
					    cp.measureUnitNetAmt = 
					      cp.measureUnitNetAmt
					    + ((cp.measureUnitNetAmt * 1 / :discountRatePct) * case when :increaseDecreaseFlag = true then 1 else -1 end)
					where 1=1
						and cp.clientId = :clientId
			""";
	@Transactional
	@Modifying
	@Query(value=qry2, nativeQuery=false)
	Object updateAllPricesByClientId(Integer clientId, BigDecimal discountRatePct, Boolean increaseDecreaseFlag);
	
	String qry3 = """
			delete ClientPricelist cp
			where 1=1
				and cp.clientId = :clientId
		""";
	@Transactional
	@Modifying
	@Query(value=qry3, nativeQuery=false)
	Object deleteAllByClientId(Integer clientId);
}
