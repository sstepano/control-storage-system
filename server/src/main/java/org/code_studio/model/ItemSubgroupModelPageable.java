package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ItemSubgroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemSubgroupModelPageable extends PagingAndSortingRepository <ItemSubgroup, Integer> {
		
	@Query("select c from ItemSubgroup c")
	List <ItemSubgroup> findAllPageable(Pageable pageable);

	@Query("select c from ItemSubgroup c where clientId = :clientId")
	List <ItemSubgroup> findAllByClientId(Integer clientId, Pageable pageable);

	@Query(value="""
			select sg.*
			from ITEM_CATALOG c
			join ITEM_CATALOG_BRANCH_LINK l on c.ID = l.CATALOG_ID
			join ITEM_BRANCH b on l.BRANCH_ID = b.ID
			join ITEM_BRANCH_SUBGROUP_LINK sl on b.ID = sl.BRANCH_ID
			join ITEM_SUBGROUP sg on sl.SUBGROUP_ID = sg.ID
			-- join ITEM i on sg.ID = i.SUBGROUP_ID
			where c.ID = :catalogId
		   """
	, nativeQuery = true)
	List <ItemSubgroup> findAllByCatalogId(Integer catalogId, Pageable pageable);

	
	@Query(value="""
			select * from item_subgroup s
            join item_branch_subgroup_link l on s.id = l.subgroup_id
            join item_branch c on l.branch_id = c.id 
            where c.id = :branchId
		   """
	, nativeQuery = true)
	List <ItemSubgroup> findAllByBranchId(Integer branchId, Pageable pageable);	

	//Ovde imam GroupId polje, ne treba mi custom query
	List <ItemSubgroup> findAllByGroupId(Integer groupId, Pageable pageable);	
	
}
