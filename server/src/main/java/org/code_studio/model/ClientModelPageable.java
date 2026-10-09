package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientModelPageable extends PagingAndSortingRepository <Client, Integer> {
	
	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			where 1=1
				and g.isSupplierGroup = false
			order by c.id
	""")
	List <Client> allPageable(Pageable pageable);
	
	
	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			where 1=1
				and g.isSupplierGroup = false
				and c.divisionId = :divisionId
			order by c.id
	""")
	List <Client> allByDivisionId(Integer divisionId, Pageable pageable);
	
	
	String qryHasQty = """
				select c
				from Client c
				left join PaletteDocument pd on pd.clientId = c.id
				left join Palette p on p.documentId = pd.id
				left join PaletteStatus ps on ps.id = p.statusId
				where 1=1
				    and (1 = case when p.statusId in (1, 2, 3, 4, 5, 6) and :hasQty = true then 1 else 0 end
				    	OR
				    	1 = case when (p.statusId = 7 or p.statusId is null) and :hasQty = false then 1 else 0 end
				    ) 
				    and c.divisionId = :divisionId
				group by c.id
				having ( 
					count(p.id) > case when :hasQty = true then 0 else null end
					OR
					count(p.id) = case when :hasQty = false then 0 else null end
				)
			""";
	@Query(value = qryHasQty, nativeQuery = false)
	List <Client> allByDivisionIdAndHasQty(Integer divisionId, Boolean hasQty, Pageable pageable);
	
	
	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			where 1=1
				and g.isSupplierGroup = false
				and c.groupId = :groupId
			order by c.id
	""")
	List <Client> findByGroupId(Integer groupId, Pageable pageable);
	

	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			inner join ClientCategory cc on c.categoryId = cc.id
			where 1=1
				and g.isSupplierGroup = false
				and c.categoryId = :categoryId
			order by c.id
	""")
	List <Client> findByCategoryId(Integer categoryId, Pageable pageable);

	
	@Query("""
			select c from Client c
			inner join ClientGroup g on c.groupId = g.id
			where 1=1
				and g.isSupplierGroup = false
				and 
					(cast(c.id as string) like '%' || upper(:searchKeyword ) || '%'
				        OR upper(c.name) like '%' || upper(:searchKeyword ) || '%'
						OR upper(c.identificationId) like '%' || upper(:searchKeyword ) || '%'
						OR upper(c.taxId) like '%' || upper(:searchKeyword ) || '%'
						OR upper(c.additionalName) like '%' || upper(:searchKeyword ) || '%'
						OR upper(c.city) like '%' || upper(:searchKeyword ) || '%'
						OR upper(c.address) like '%' || upper(:searchKeyword ) || '%'
					)
			order by c.id
	""")
	List <Client> search(String searchKeyword, Pageable pageable);
	

	@Query("""
			select c from Client c, ClientSaleOfficerLink l, ClientGroup g
			where 1=1
				and g.isSupplierGroup = false
			  and c.id = l.clientId
			  and l.saleOfficerId = :saleOfficerId
	""")
	List <Client> findBySaleOfficerId(Integer saleOfficerId, Pageable pageable);
	
	
	/*@Query("""
				from Client c 
				join fetch c.clientGroup g 
				join fetch c.country ct
				join fetch c.clientCategory cc 
				join fetch c.division d 
				where 1=1
				and g.isSupplierGroup = false
					and c.divisionId = :divisionId
	""")*/
	
	@Query("""
	select c
	from Client c
	join fetch c.clientGroup g 
	where 1=1
	    and g.isSupplierGroup = false
		and c.divisionId = :divisionId
	""")
	List <Client> findAllByDivisionId(Integer divisionId, Pageable pageable);
	
}
