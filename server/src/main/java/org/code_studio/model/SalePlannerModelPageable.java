package org.code_studio.model;

import java.util.List;

import org.code_studio.database.SalePlanner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SalePlannerModelPageable extends PagingAndSortingRepository <SalePlanner, Integer> {
	String qry1 = """
			select sp 
			from SalePlanner sp 
			join fetch sp.client 
			join fetch sp.clientContact
			join fetch sp.saleOfficer
			join fetch sp.saleOfficerContacted
			join fetch sp.salePlannerType
			order by sp.planDate desc
			""";
	@Query(value = qry1)
	List <SalePlanner> findAllPageable(Pageable pageable);

	@Query("select sp from SalePlanner sp join fetch sp.client where sp.clientId = :clientId order by sp.eventDate DESC")
	List <SalePlanner> findAllByClientId(Integer clientId, Pageable pageable);
	
	List <SalePlanner> findAllByClientIdAndSaleOfficerIdOrderByEventDateDesc(Integer clientId, Integer saleOfficerId, Pageable pageable);

	@Query("select sp from SalePlanner sp join fetch sp.client where sp.saleOfficerId = :saleOfficerId order by sp.eventDate DESC")
	List <SalePlanner> findAllBySaleOfficerId(Integer saleOfficerId, Pageable pageable);

	List <SalePlanner> findAllBySaleOfficerIdOrderByPlanDateDesc(Integer saleOfficerId, Pageable pageable);
	
	@Query("select sp from SalePlanner sp join fetch sp.client where sp.saleOfficerId = :saleOfficerId order by sp.clientId, sp.eventDate desc")
	List <SalePlanner> findAllBySaleOfficerIdRefKomTermin(Integer saleOfficerId, Pageable pageable);

	@Query("select sp from SalePlanner sp join fetch sp.client where str(sp.planDate) = :planDate order by sp.planDate desc")
	List <SalePlanner> findAllByPlanDate(String planDate, Pageable pageable);
	
	String qry2 = """
			select sp 
			from SalePlanner sp 
			join fetch sp.client 
			where 1=1
			  and sp.saleOfficerId = :saleOfficerId 
			  and month(sp.eventDate) = :eventDateMonth
			  and year(sp.eventDate) = :eventDateYear
		    order by sp.eventDate desc
			""";
	@Query(value = qry2)
	List <SalePlanner> findAllBySaleOfficerIdAndEventDate(Integer saleOfficerId, Integer eventDateMonth, Integer eventDateYear, Pageable pageable);

	@Query("select sp from SalePlanner sp join fetch sp.client where sp.contactedById = :contactedById order by sp.eventDate desc")
	List <SalePlanner> findAllByContactedByIdOrderByPlanDateDesc(Integer contactedById, Pageable pageable);
	
	@Query("select sp from SalePlanner sp join fetch sp.client where sp.saleOfficerId = :saleOfficerId and str(sp.planDate) = :planDate order by sp.planDate desc")
	List <SalePlanner> findAllBySaleOfficerIdAndPlanDate(Integer saleOfficerId, String planDate, Pageable pageable);
	
	@Query("select sp from SalePlanner sp join fetch sp.client order by sp.eventDate desc")
	List <SalePlanner> findAllOrderByEventDateDesc(Pageable pageable);
}
