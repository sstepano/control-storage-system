package org.code_studio.model;

import java.util.List;

import org.code_studio.database.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemModelPageable extends PagingAndSortingRepository <Item, Integer> {
		
	@Query(value="""
				SELECT i.*
				FROM item i
				LEFT JOIN (
				  select
				    item_id, sum(QTY) qty 
				  from item_warehouse 
				  where 1=1
				  group by item_id
				) iw ON i.ID = iw.ITEM_ID
				WHERE 1=1
				  AND (
				    (:showInStockOnly = 1 AND IFNULL(iw.QTY, 0) > 0)
				    OR (:showInStockOnly = 0)
				  )
				order by i.id			
			""", nativeQuery = true)
	List <Item> findAllPageable(Boolean showInStockOnly, Pageable pageable);

	@Query(value="""
				SELECT i.*
				FROM item i
				LEFT JOIN (
				  select
				    item_id, sum(QTY) qty 
				  from item_warehouse 
				  where 1=1
				  group by item_id
				) iw ON i.ID = iw.ITEM_ID
				WHERE 1=1
				  AND i.CLIENT_ID = :clientId
				  AND (
				    (:showInStockOnly = 1 AND IFNULL(iw.QTY, 0) > 0)
				    OR (:showInStockOnly = 0)
				  )
				order by i.id			
			"""
	, nativeQuery = true)
	List <Item> findAllByClientId(Integer clientId, Boolean showInStockOnly, Pageable pageable);
	

	@Query(value="""
			select i.* 
			from ITEM_CATALOG c
			join ITEM_CATALOG_BRANCH_LINK l on c.ID = l.CATALOG_ID
			join ITEM_BRANCH b on l.BRANCH_ID = b.ID
			join ITEM_BRANCH_SUBGROUP_LINK sl on b.ID = sl.BRANCH_ID
			join ITEM_SUBGROUP sg on sl.SUBGROUP_ID = sg.ID
			join ITEM i on sg.ID = i.SUBGROUP_ID
			where c.ID = :catalogId
			order by i.ID			
		   """
	, nativeQuery = true)
	List <Item> findAllByCatalogId(Integer catalogId, Pageable pageable);

	@Query(value="""
			select i.* from ITEM i
			join ITEM_SUBGROUP s on i.SUBGROUP_ID = s.ID
			join ITEM_BRANCH_SUBGROUP_LINK l on s.ID = l.SUBGROUP_ID
			where l.BRANCH_ID = :branchId
		   """
	, nativeQuery = true)
	List <Item> findAllByBranchId(Integer branchId, Pageable pageable);

	//Imam groupId u Item DTO
	List <Item> findAllByGroupId(Integer groupId, Pageable pageable);

	//Imam subgroupId u Item DTO
	List <Item> findAllBySubgroupId(Integer subgroupId, Pageable pageable);
	
	//Imam typeId u Item DTO
	List <Item> findAllByTypeId(Integer typeId, Pageable pageable);
	
	
	/*
	 * Pretraga kod dodavanja item-a za Dobanovce gde mogu da proslede samo name i klijenta
	 * Tada moramo da proverimo da li takav item vec postoji i da ga vratimo, u suprotnom pravimo novi
	 */
	List <Item> findAllByClientIdAndNameContaining(Integer clientId, String name, Pageable pageable); // Ovo vraca %LIKE% iz db. Ubaciti samo ako B. eksplicitno trazi
	List <Item> findAllByNameAndClientId(String name, Integer clientId, Pageable pageable);
	List <Item> findAllByNameContaining(String name, Pageable pageable);
	
	String qry1 = """
			select DISTINCT i.*
			from Item i
			left join item_warehouse iw on i.id = iw.item_id
			where 1=1
			  and (
			       (
			         -- cast(i.id as char) like CONCAT(CONCAT('%', :searchPhrase), '%') -- mislim da treba da izbacim IDeve iz pretrage
				     i.code like CONCAT(CONCAT('%', :searchPhrase), '%')
			        OR i.name like CONCAT(CONCAT('%', :searchPhrase), '%')
			       )
			    AND (
			      (:showInStockOnly = 1 AND IFNULL(iw.QTY, 0) > 0)
			      OR (:showInStockOnly = 0)
			    )	
			  )
			  order by i.code
			""";
	@Query(value=qry1, nativeQuery=true)
	List <Item> findAllByIdOrCodeOrNameContaining(String searchPhrase, Boolean showInStockOnly, Pageable pageable);
	
}
