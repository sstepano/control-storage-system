package org.code_studio.database;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.CascadeType;
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
@Table(name = "ORDERS", schema = "PUBLIC", catalog = "RM")
public class Orders implements java.io.Serializable {

	private Integer id;
	private Integer clientOrderId;
	private Integer clientId;
	private Client client;
	private Boolean isImport;
	private Integer countryId;
	private String orderForClient;
	private String contactInfo;
	private String fax;
	private String cc;
	private String ccfax;
	private String emailFrom;
	private LocalDate orderDate; // na UI je datum ordera, tj kada je porucen.
	private LocalDate loadingDate; // na UI je datum utovara tj potvrdjen
	private LocalDate deliveryDate; // na UI ocekivani datum isporuke
	private LocalDate receiveDate; // datum prijema
	private Integer currencyId;
	private BigDecimal orderAmount;
	private String description;
	private Boolean isBackorder;
	private Integer createdByUserId;
	private Date openDate;
	private Integer validatedByUserId;
	private ApplicationUser validatedByUser;
	private Date validationDate;
	private Integer oldid;

	public Orders() {}
	
	public Orders(Client client) {
		this.client = client;
	}

	public Orders(Integer clientOrderId, Integer clientId, Boolean isImport, Integer countryId, String orderForClient,
			String contactInfo, String fax, String cc, String ccfax, String emailFrom, LocalDate orderDate, LocalDate loadingDate,
			LocalDate deliveryDate, LocalDate receiveDate, Integer currencyId, BigDecimal orderAmount, String description,
			Boolean isBackorder, Integer createdByUserId, Date openDate, Integer validatedByUserId, Date validationDate,
			Integer oldid) {
		this.clientOrderId = clientOrderId;
		this.clientId = clientId;
		this.isImport = isImport;
		this.countryId = countryId;
		this.orderForClient = orderForClient;
		this.contactInfo = contactInfo;
		this.fax = fax;
		this.cc = cc;
		this.ccfax = ccfax;
		this.emailFrom = emailFrom;
		this.orderDate = orderDate;
		this.loadingDate = loadingDate;
		this.deliveryDate = deliveryDate;
		this.receiveDate = receiveDate;
		this.currencyId = currencyId;
		this.orderAmount = orderAmount;
		this.description = description;
		this.isBackorder = isBackorder;
		this.createdByUserId = createdByUserId;
		this.openDate = openDate;
		this.validatedByUserId = validatedByUserId;
		this.validationDate = validationDate;
		this.oldid = oldid;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CLIENT_ORDER_ID")
	public Integer getClientOrderId() {
		return this.clientOrderId;
	}

	public void setClientOrderId(Integer clientOrderId) {
		this.clientOrderId = clientOrderId;
	}

	@Column(name = "CLIENT_ID", updatable = false, insertable = false)
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Column(name = "IS_IMPORT")
	public Boolean getIsImport() {
		return this.isImport;
	}

	public void setIsImport(Boolean isImport) {
		this.isImport = isImport;
	}

	@Column(name = "COUNTRY_ID")
	public Integer getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	@Column(name = "ORDER_FOR_CLIENT", length = 100)
	public String getOrderForClient() {
		return this.orderForClient;
	}

	public void setOrderForClient(String orderForClient) {
		this.orderForClient = orderForClient;
	}

	@Column(name = "CONTACT_INFO", length = 1000)
	public String getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	@Column(name = "FAX", length = 100)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "CC", length = 100)
	public String getCc() {
		return this.cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	@Column(name = "CCFAX", length = 100)
	public String getCcfax() {
		return this.ccfax;
	}

	public void setCcfax(String ccfax) {
		this.ccfax = ccfax;
	}

	@Column(name = "EMAIL_FROM", length = 100)
	public String getEmailFrom() {
		return this.emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	//@Temporal(TemporalType.DATE)
	@Column(name = "ORDER_DATE", length = 10)
	public LocalDate getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	//@Temporal(TemporalType.DATE)
	@Column(name = "LOADING_DATE", length = 10)
	public LocalDate getLoadingDate() {
		return this.loadingDate;
	}

	public void setLoadingDate(LocalDate loadingDate) {
		this.loadingDate = loadingDate;
	}

	//@Temporal(TemporalType.DATE)
	@Column(name = "DELIVERY_DATE", length = 10)
	public LocalDate getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	//@Temporal(TemporalType.DATE)
	@Column(name = "RECEIVE_DATE", length = 10)
	public LocalDate getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(LocalDate receiveDate) {
		this.receiveDate = receiveDate;
	}

	@Column(name = "CURRENCY_ID")
	public Integer getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "ORDER_AMOUNT", precision = 18)
	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "IS_BACKORDER")
	public Boolean getIsBackorder() {
		return this.isBackorder;
	}

	public void setIsBackorder(Boolean isBackorder) {
		this.isBackorder = isBackorder;
	}

	@Column(name = "CREATED_BY_USER_ID")
	public Integer getCreatedByUserId() {
		return this.createdByUserId;
	}

	public void setCreatedByUserId(Integer createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	@Column(name = "OPEN_DATE", length = 26)
	public Date getOpenDate() {
		return this.openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	@Column(name = "VALIDATED_BY_USER_ID", insertable = false, updatable = false)
	public Integer getValidatedByUserId() {
		return this.validatedByUserId;
	}

	public void setValidatedByUserId(Integer validatedByUserId) {
		this.validatedByUserId = validatedByUserId;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="VALIDATED_BY_USER_ID")
	public ApplicationUser getValidatedByUser() {
		return this.validatedByUser;
	}
	
	public void setValidatedByUser(ApplicationUser validatedByUser) {
		this.validatedByUser = validatedByUser;
	}

	@Column(name = "VALIDATION_DATE", length = 26)
	public Date getValidationDate() {
		return this.validationDate;
	}

	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}

	@Column(name = "_OLDID")
	public Integer getOldid() {
		return this.oldid;
	}

	public void setOldid(Integer oldid) {
		this.oldid = oldid;
	}
	
	//------------------------------------------------------
	@Transient
	public String getValidatedByUserName() {
		return this.validatedByUser == null ? "" : this.validatedByUser.getName();
	}
	
	@Transient
	public String getClientName() {
		return this.client == null ? "" : this.client.getName();
	}

}
