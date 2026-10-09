package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientModel extends JpaRepository <Client, Integer> {	

	
	//Gets only ID and NAME
	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			where 1=1
				and g.isSupplierGroup = false
				and c.id = :id
	""")
	List <IClient> findAllById(Integer id);


	@Query("""
			select distinct c from Client c
			join PaletteDocument pd on c.id = pd.clientId 
			where 1=1
				and pd.typeId = 1
				and pd.statusId = 1
				and c.divisionId = :divisionId
			order by c.id
	""")
	List <Client> allByDivisionIdAndPreparedPalettesIn(Integer divisionId);

	@Query("""
			select distinct c from Client c
			join PaletteDocument pd on c.id = pd.clientId 
			where 1=1
				and pd.typeId = 2
				and pd.statusId = 1
				and c.divisionId = :divisionId
			order by c.id
	""")
	List <Client> allByDivisionIdAndPreparedPalettesOut(Integer divisionId);
	
}
