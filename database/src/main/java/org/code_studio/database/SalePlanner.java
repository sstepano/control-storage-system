package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@SuppressWarnings("serial") 
@Entity
@Table(name = "SALE_PLANNER", schema = "PUBLIC", catalog = "RM")
public class SalePlanner implements java.io.Serializable {

	private Integer Id;
	private Integer clientId;
	private ClientName client;
	private Integer saleOfficerId;
	private ApplicationUserName saleOfficer;
	private Integer contactedById;
	private ApplicationUserName saleOfficerContacted;
	private LocalDateTime planDate;
	private LocalDateTime eventDate;
	private Boolean isCame;
	private Boolean isWentTo;
	private Boolean isCalled;
	private Boolean isCalledTo;
	private Integer clientContactId;
	private ClientContactName clientContact;
	private BigDecimal deliveredAmt;
	private BigDecimal chargedAmt;
	private BigDecimal promisedAmt;
	private String description;
	private SalePlannerType salePlannerType;

	public SalePlanner() {}
	
	/**
	 * This constructor is used when B. creates Workplan for sales officer
	 */
	public SalePlanner(Integer clientId, ApplicationUserName saleOfficer, LocalDateTime planDate) {
		this.clientId = clientId;
		this.saleOfficer = saleOfficer;
		this.planDate = planDate;
		this.setSalePlannerType(new SalePlannerType(Long.valueOf(1), "CNT", "Kontakt", null));
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.Id;
	}

	public void setId(Integer Id) {
		this.Id = Id;
	}

	@Column(name = "CLIENT_ID")
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	
	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENT_ID")
	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	*/

	//@Column(name = "TYPE_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPE_ID")
	public SalePlannerType getSalePlannerType() {
		return this.salePlannerType;
	}

	public void setSalePlannerType(SalePlannerType salePlannerType) {
		this.salePlannerType = salePlannerType;
	}

	@Column(name = "SALE_OFFICER_ID")
	public Integer getSaleOfficerId() {
		return this.saleOfficerId;
	}

	public void setSaleOfficerId(Integer saleOfficerId) {
		this.saleOfficerId = saleOfficerId;
	}
	
	@ManyToOne
	@JoinColumn(name = "SALE_OFFICER_ID", insertable = false, updatable = false)
	public ApplicationUserName getSaleOfficer() {
		return this.saleOfficer;
	}

	public void setSaleOfficer(ApplicationUserName saleOfficer) {
		this.saleOfficer = saleOfficer;
	}

	@Column(name = "CONTACTED_BY_ID")
	public Integer getContactedById() {
		return this.contactedById;
	}

	public void setContactedById(Integer contactedById) {
		this.contactedById = contactedById;
	}
	
	@ManyToOne
	@JoinColumn(name = "CONTACTED_BY_ID", insertable = false, updatable = false)
	public ApplicationUserName getSaleOfficerContacted() {
		return this.saleOfficerContacted;
	}

	public void setSaleOfficerContacted(ApplicationUserName saleOfficerContacted) {
		this.saleOfficerContacted = saleOfficerContacted;
	}
	
	@Column(name = "PLAN_DATE", length = 26)
	public LocalDateTime getPlanDate() {
		return this.planDate;
	}

	public void setPlanDate(LocalDateTime planDate) {
		this.planDate = planDate;
	}

	@Column(name = "EVENT_DATE", length = 26)
	public LocalDateTime getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	@Column(name = "IS_CAME")
	public Boolean getIsCame() {
		return isCame == null ? false : isCame;
	}

	public void setIsCame(Boolean isCame) {
		this.isCame = isCame;
	}

	@Column(name = "IS_WENT_TO")
	public Boolean getIsWentTo() {
		return isWentTo == null ? false : isWentTo;
	}

	public void setIsWentTo(Boolean isWentTo) {
		this.isWentTo = isWentTo;
	}

	@Column(name = "IS_CALLED")
	public Boolean getIsCalled() {
		return isCalled == null ? false : isCalled;
	}

	public void setIsCalled(Boolean isCalled) {
		this.isCalled = isCalled;
	}

	@Column(name = "IS_CALLED_TO")
	public Boolean getIsCalledTo() {
		return isCalledTo == null ? false : isCalledTo;
	}

	public void setIsCalledTo(Boolean isCalledTo) {
		this.isCalledTo = isCalledTo;
	}

	@Column(name = "CONTACT_ID")
	public Integer getClientContactId() {
		return this.clientContactId;
	}

	public void setClientContactId(Integer clientContactId) {
		this.clientContactId = clientContactId;
	}
	
	@ManyToOne
	@JoinColumn(name = "CONTACT_ID", updatable = false, insertable = false)
	public ClientContactName getClientContact() {
		return this.clientContact;
	}

	public void setClientContact(ClientContactName clientContact) {
		this.clientContact = clientContact;
	}

	@Column(name = "DELIVERED_AMT", precision = 18)
	public BigDecimal getDeliveredAmt() {
		return this.deliveredAmt;
	}

	public void setDeliveredAmt(BigDecimal deliveredAmt) {
		this.deliveredAmt = deliveredAmt;
	}

	@Column(name = "CHARGED_AMT", precision = 18)
	public BigDecimal getChargedAmt() {
		return this.chargedAmt;
	}

	public void setChargedAmt(BigDecimal chargedAmt) {
		this.chargedAmt = chargedAmt;
	}

	@Column(name = "PROMISED_AMT", precision = 18)
	public BigDecimal getPromisedAmt() {
		return promisedAmt;
	}

	public void setPromisedAmt(BigDecimal promisedAmt) {
		this.promisedAmt = promisedAmt;
	}

	@Column(name = "DESCRIPTION", length = 8000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	//===============================================
	@Transient
	public String getSalePlannerTypeName() {
		return this.salePlannerType.getName();
	}
	
	@Transient
	public String getSaleOfficerName() {
		return this.saleOfficer == null ? "" : this.saleOfficer.getName();
	}
	
	@Transient
	public String getSaleOfficerContactedName() {
		return this.saleOfficerContacted == null ? "" : this.saleOfficerContacted.getName();
	}
	
	@Transient
	public String getContactedClientName() {
		return this.clientContact == null ? "" : this.clientContact.getName();
	}
	
	@Transient
	public String getContactedClientPhone() {
		return this.clientContact == null ? "" : this.clientContact.getPhone();
	}
	
	@OneToOne
	@JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false)
	public ClientName getClient() {
		return client;
	}

	public void setClient(ClientName client) {
		this.client = client;
	}

	@Transient
	public String getClientName() {
		return this.client == null ? "" : this.client.getName();
	}

}
