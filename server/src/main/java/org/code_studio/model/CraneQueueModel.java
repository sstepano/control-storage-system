package org.code_studio.model;

import java.util.List;

import org.code_studio.database.CraneQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CraneQueueModel extends JpaRepository <CraneQueue, Integer> {
	
	@Query(value = """
			select q
			from CraneQueue q
			where 1=1
			  and clientId = :clientId
			  and inOut = :inOut 
		""")
	public List<CraneQueue> allInputOutputByClientId(Integer clientId, Integer inOut);
}
