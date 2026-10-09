package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CalculationModel extends JpaRepository <Calculation, Integer> {
	
	List<Calculation> findAllByClientId(Integer clientId);
	
	
	/**********************************
	 * Gets last created client calculation id sequence number
	 ***********************************/
	List<Calculation> findTop1ClientCalculationIdByClientIdOrderByClientCalculationIdDesc(Integer clientId);
	
	/**********************************
	 * Gets last created client calculation id sequence number
	 ***********************************/
	String qry1 = """
				update Calculation c set
				    c.isActive = case when id = :calculationId then true else false end
				where 1=1
				    and c.clientId = (select clientId from Calculation c where Id = :calculationId)
			""";
	@Transactional
	@Modifying
	@Query(value=qry1, nativeQuery=false)
	Object setIsActiveByCalculationId(Integer calculationId);
}
