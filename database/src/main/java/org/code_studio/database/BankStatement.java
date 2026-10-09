package org.code_studio.database;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@SuppressWarnings("serial")
@Entity
@Table(name = "BANK_STATEMENT", schema = "PUBLIC", catalog = "RM")
public class BankStatement implements java.io.Serializable {
	
	private Integer id;
	private Integer accountId;
	private BankAccount bankAccount;
	private Integer bankStatementNumber;
	private LocalDate bankStatementDate;
	private BigDecimal amount;
	private LocalDateTime createdDate;
	private LocalDateTime bookingDate;
	private Integer createdBy;
	private Integer bookedBy;
	private Integer oldIdizvod;
	private List<BankStatementDetail> bankStatementDetail;

	public BankStatement() {
	}

	public BankStatement(Integer accountId, Integer bankStatementNumber, LocalDate bankStatementDate, BigDecimal amount,
			LocalDateTime createdDate, LocalDateTime bookingDate, Integer createdBy, Integer bookedBy, Integer oldIdizvod) {
		this.accountId = accountId;
		this.bankStatementNumber = bankStatementNumber;
		this.bankStatementDate = bankStatementDate;
		this.amount = amount;
		this.createdDate = createdDate;
		this.bookingDate = bookingDate;
		this.createdBy = createdBy;
		this.bookedBy = bookedBy;
		this.oldIdizvod = oldIdizvod;
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

	@Column(name = "ACCOUNT_ID")
	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Column(name = "BANK_STATEMENT_NUMBER")
	public Integer getBankStatementNumber() {
		return this.bankStatementNumber;
	}

	public void setBankStatementNumber(Integer bankStatementNumber) {
		this.bankStatementNumber = bankStatementNumber;
		
	}

	@Column(name = "BANK_STATEMENT_DATE", length = 10)
	public LocalDate getBankStatementDate() {
		return this.bankStatementDate;
	}

	public void setBankStatementDate(LocalDate bankStatementDate) {
		this.bankStatementDate = bankStatementDate;
	}

	@Column(name = "AMOUNT", precision = 18)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "CREATED_DATE", length = 26)
	public LocalDateTime getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "BOOKING_DATE", length = 26)
	public LocalDateTime getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	@Column(name = "CREATED_BY")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "BOOKED_BY")
	public Integer getBookedBy() {
		return this.bookedBy;
	}

	public void setBookedBy(Integer bookedBy) {
		this.bookedBy = bookedBy;
	}

	@Column(name = "_OLD_IDIZVOD")
	public Integer getOldIdizvod() {
		return this.oldIdizvod;
	}

	public void setOldIdizvod(Integer oldIdizvod) {
		this.oldIdizvod = oldIdizvod;
	}
	
	@ManyToOne
	@JoinColumn(name="ACCOUNT_ID", insertable = false, updatable = false)
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Transient
	public Integer getYear() {
		return getBankStatementDate().getYear();		
	}

	// Greska u SB 3.3.4 @NotFound(action = NotFoundAction.IGNORE) // Ovim necemo dobiti EntityNotFound gresku ako prvo obrisemo detail, pa pokusamo statement
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name="BANK_STATEMENT_ID", insertable = false, updatable = false)
	public List<BankStatementDetail> getBankStatementDetail() {
		return bankStatementDetail;
	}

	public void setBankStatementDetail(List<BankStatementDetail> bankStatementDetail) {
		this.bankStatementDetail = bankStatementDetail;
	}

}
