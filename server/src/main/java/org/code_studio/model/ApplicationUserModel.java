package org.code_studio.model;

import java.util.List;

import org.code_studio.database.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationUserModel extends JpaRepository <ApplicationUser, Integer> {
	
	// This method is used for LOGIN
    List<ApplicationUser> findByUsername(String username);
    List<ApplicationUser> findByUsernameAndPassword(String username, String password);
	
	//TODO: Ovde radim konverziju int u str da bih poredio sa salOfficerRoleIDs string array-em, sto je inefficient
	String qrySaleOfficers = """
			select u from ApplicationUser u
			where 1=1
				and str(roleId) in (:saleOfficerRoleIDs)
	""";
	@Query(value=qrySaleOfficers, nativeQuery=false)
	List <ApplicationUser> findAllSaleOfficers(String[] saleOfficerRoleIDs);
	
	
	String qrySaleOfficersByClientId = """
			select u from ApplicationUser u
			join ClientSaleOfficerLink l on u.id = l.saleOfficerId
			where 1=1
				and l.clientId = :clientId
				and str(u.roleId) in (:saleOfficerRoleIDs)
	""";
	@Query(value=qrySaleOfficersByClientId, nativeQuery=false)
	List <ApplicationUser> findAllSaleOfficersByClientId(String[] saleOfficerRoleIDs, Integer clientId);
	
	
	String qryPrimarySaleOfficerByClientId = """
			select u from ApplicationUser u
			join ClientSaleOfficerLink l on u.id = l.saleOfficerId
			where 1=1
				and l.clientId = :clientId
				and str(u.roleId) in (:saleOfficerRoleIDs)
				and l.isPrimary = true
	""";
	@Query(value=qryPrimarySaleOfficerByClientId, nativeQuery=false)
	List <ApplicationUser> findPrimarySaleOfficerByClientId(String[] saleOfficerRoleIDs, Integer clientId);
	
	
	//TODO: Ovde radim konverziju int u str da bih poredio sa salOfficerRoleIDs string array-em, sto je inefficient
	String qryEnumerators = """
		SELECT 0 ID , 'SVI POPISIVAČI' USERNAME, 'SVI POPISIVAČI' NAME, NULL PASSWORD, NULL ROLE_ID, NULL LAST_LOGIN_DATE
		UNION ALL
		SELECT ID, USERNAME, NAME, PASSWORD, ROLE_ID, LAST_LOGIN_DATE
		FROM Application_User
		WHERE (
		  cast(ROLE_ID as varchar(100)) in (:enumeratorRoleIDs)
		);
	""";
	@Query(value=qryEnumerators, nativeQuery=true)
	List <ApplicationUser> findAllEnumerators(String[] enumeratorRoleIDs);
	
	String qryEnumeratorsWithoutALL = """
			SELECT ID, USERNAME, NAME, PASSWORD, ROLE_ID, LAST_LOGIN_DATE
			FROM Application_User
			WHERE (
			  cast(ROLE_ID as varchar(100)) in (:enumeratorRoleIDs)
			);
		""";
		@Query(value=qryEnumeratorsWithoutALL, nativeQuery=true)
		List <ApplicationUser> findAllEnumeratorsWithoutALL(String[] enumeratorRoleIDs);
}

