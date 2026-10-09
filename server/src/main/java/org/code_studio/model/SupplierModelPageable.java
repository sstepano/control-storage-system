package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SupplierModelPageable extends PagingAndSortingRepository <Client, Integer> {


	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			where 1=1
				and (g.isSupplierGroup = true or c.isSupplier = true)
			order by c.id
	""")
	List <Client> allPageable(Pageable pageable);

	
	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			where 1=1
				and (g.isSupplierGroup = true or c.isSupplier = true)
				and c.divisionId = :divisionId
			order by c.id
	""")
	List <Client> findByDivisionId(Integer divisionId, Pageable pageable);
	
	
	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			where 1=1
				and (g.isSupplierGroup = true or c.isSupplier = true)
				and c.groupId = :groupId
			order by c.id
	""")
	List <Client> findByGroupId(Integer groupId, Pageable pageable);
	
	
	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			inner join ClientCategory cc on c.categoryId = cc.id
			where 1=1
				and (g.isSupplierGroup = true or c.isSupplier = true)
				and c.categoryId = :categoryId
			order by c.id
	""")
	List <Client> findByCategoryId(Integer categoryId, Pageable pageable);
	
	
	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			where 1=1
				and (g.isSupplierGroup = true or c.isSupplier = true)
				and cast(c.id as string) like '%' || upper(:searchKeyword ) || '%'
				OR upper(c.name) like '%' || upper(:searchKeyword ) || '%'
				OR upper(c.identificationId) like '%' || upper(:searchKeyword ) || '%'
				OR upper(c.taxId) like '%' || upper(:searchKeyword ) || '%'
	""")
	List <Client> search(String searchKeyword, Pageable pageable);
}
