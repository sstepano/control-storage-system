package org.code_studio.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.opencsv.bean.CsvBindByPosition;

@SuppressWarnings("serial")
@Entity
@Table(name = "FILE_BANK_STATEMENT", catalog = "RM")
public class FileBankStatement implements java.io.Serializable {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinTable(name = "FILE_HEADER")
    @JoinColumns({
        @JoinColumn(name="FILE_HEADER_ID", referencedColumnName="ID")
    })
	private Integer fileHeaderId;
	
	@CsvBindByPosition(position = 0)  private String accountNumber;
	@CsvBindByPosition(position = 1)  private String processDate;
	@CsvBindByPosition(position = 2)  private Integer invoiceNumber;
	@CsvBindByPosition(position = 3)  private String currencyCode;
	@CsvBindByPosition(position = 4)  private String valueDate;
	@CsvBindByPosition(position = 5)  private String debitAmt;
	@CsvBindByPosition(position = 6)  private String creditAmt;
	@CsvBindByPosition(position = 7)  private String bookingMark;
	@CsvBindByPosition(position = 8)  private String description;
	@CsvBindByPosition(position = 9)  private String bookingDate;
	@CsvBindByPosition(position = 10) private String partnerAccountNumber;
	@CsvBindByPosition(position = 11) private String partnerName;
	@CsvBindByPosition(position = 12) private String paymentPurpose;
	@CsvBindByPosition(position = 13) private String paymentTypeId;
	@CsvBindByPosition(position = 14) private String creditReferenceNumber;
	@CsvBindByPosition(position = 15) private String debitReferenceNumber;
	@CsvBindByPosition(position = 16) private String creditModel;
	@CsvBindByPosition(position = 17) private String debitModel;
	@CsvBindByPosition(position = 18) private String orderId;
	@CsvBindByPosition(position = 19) private String createdTime;
	@CsvBindByPosition(position = 20) private String receiveTime;
	@CsvBindByPosition(position = 21) private String leftSignatory;
	@CsvBindByPosition(position = 22) private String rightSignatory;
	@CsvBindByPosition(position = 23) private String orderValueDate;
	@CsvBindByPosition(position = 24) private String prepareDate;
	@CsvBindByPosition(position = 25) private String orderType;
	@CsvBindByPosition(position = 26) private Character urgent;
	@CsvBindByPosition(position = 27) private String bankReference;

	public FileBankStatement() {
	}

	public FileBankStatement(Integer fileHeaderId, String accountNumber, String processDate, Integer invoiceNumber,
			String currencyCode, String valueDate, String debitAmt, String creditAmt, String bookingMark,
			String description, String bookingDate, String partnerAccountNumber, String partnerName,
			String paymentPurpose, String paymentTypeId, String creditReferenceNumber, String debitReferenceNumber,
			String creditModel, String debitModel, String orderId, String createdTime, String receiveTime,
			String leftSignatory, String rightSignatory, String orderValueDate, String prepareDate, String orderType,
			Character urgent, String bankReference) {
		this.fileHeaderId = fileHeaderId;
		this.accountNumber = accountNumber;
		this.processDate = processDate;
		this.invoiceNumber = invoiceNumber;
		this.currencyCode = currencyCode;
		this.valueDate = valueDate;
		this.debitAmt = debitAmt;
		this.creditAmt = creditAmt;
		this.bookingMark = bookingMark;
		this.description = description;
		this.bookingDate = bookingDate;
		this.partnerAccountNumber = partnerAccountNumber;
		this.partnerName = partnerName;
		this.paymentPurpose = paymentPurpose;
		this.paymentTypeId = paymentTypeId;
		this.creditReferenceNumber = creditReferenceNumber;
		this.debitReferenceNumber = debitReferenceNumber;
		this.creditModel = creditModel;
		this.debitModel = debitModel;
		this.orderId = orderId;
		this.createdTime = createdTime;
		this.receiveTime = receiveTime;
		this.leftSignatory = leftSignatory;
		this.rightSignatory = rightSignatory;
		this.orderValueDate = orderValueDate;
		this.prepareDate = prepareDate;
		this.orderType = orderType;
		this.urgent = urgent;
		this.bankReference = bankReference;
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

	@Column(name = "FILE_HEADER_ID")
	public Integer getFileHeaderId() {
		return this.fileHeaderId;
	}

	public void setFileHeaderId(Integer fileHeaderId) {
		this.fileHeaderId = fileHeaderId;
	}

	@Column(name = "ACCOUNT_NUMBER", length = 19)
	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Column(name = "PROCESS_DATE", length = 10)
	public String getProcessDate() {
		return this.processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	@Column(name = "INVOICE_NUMBER")
	public Integer getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@Column(name = "CURRENCY_CODE", length = 3)
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "VALUE_DATE", length = 10)
	public String getValueDate() {
		return this.valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	@Column(name = "DEBIT_AMT", length = 20)
	public String getDebitAmt() {
		return this.debitAmt;
	}

	public void setDebitAmt(String debitAmt) {
		this.debitAmt = debitAmt;
	}

	@Column(name = "CREDIT_AMT", length = 20)
	public String getCreditAmt() {
		return this.creditAmt;
	}

	public void setCreditAmt(String creditAmt) {
		this.creditAmt = creditAmt;
	}

	@Column(name = "BOOKING_MARK", length = 20)
	public String getBookingMark() {
		return this.bookingMark;
	}

	public void setBookingMark(String bookingMark) {
		this.bookingMark = bookingMark;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "BOOKING_DATE", length = 10)
	public String getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	@Column(name = "PARTNER_ACCOUNT_NUMBER", length = 19)
	public String getPartnerAccountNumber() {
		return this.partnerAccountNumber;
	}

	public void setPartnerAccountNumber(String partnerAccountNumber) {
		this.partnerAccountNumber = partnerAccountNumber;
	}

	@Column(name = "PARTNER_NAME", length = 30)
	public String getPartnerName() {
		return this.partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	@Column(name = "PAYMENT_PURPOSE", length = 100)
	public String getPaymentPurpose() {
		return this.paymentPurpose;
	}

	public void setPaymentPurpose(String paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}

	@Column(name = "PAYMENT_TYPE_ID", length = 3)
	public String getPaymentTypeId() {
		return this.paymentTypeId;
	}

	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	@Column(name = "CREDIT_REFERENCE_NUMBER", length = 20)
	public String getCreditReferenceNumber() {
		return this.creditReferenceNumber;
	}

	public void setCreditReferenceNumber(String creditReferenceNumber) {
		this.creditReferenceNumber = creditReferenceNumber;
	}

	@Column(name = "DEBIT_REFERENCE_NUMBER", length = 20)
	public String getDebitReferenceNumber() {
		return this.debitReferenceNumber;
	}

	public void setDebitReferenceNumber(String debitReferenceNumber) {
		this.debitReferenceNumber = debitReferenceNumber;
	}

	@Column(name = "CREDIT_MODEL", length = 20)
	public String getCreditModel() {
		return this.creditModel;
	}

	public void setCreditModel(String creditModel) {
		this.creditModel = creditModel;
	}

	@Column(name = "DEBIT_MODEL", length = 20)
	public String getDebitModel() {
		return this.debitModel;
	}

	public void setDebitModel(String debitModel) {
		this.debitModel = debitModel;
	}

	@Column(name = "ORDER_ID", length = 20)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "CREATED_TIME", length = 10)
	public String getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "RECEIVE_TIME", length = 10)
	public String getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Column(name = "LEFT_SIGNATORY", length = 20)
	public String getLeftSignatory() {
		return this.leftSignatory;
	}

	public void setLeftSignatory(String leftSignatory) {
		this.leftSignatory = leftSignatory;
	}

	@Column(name = "RIGHT_SIGNATORY", length = 20)
	public String getRightSignatory() {
		return this.rightSignatory;
	}

	public void setRightSignatory(String rightSignatory) {
		this.rightSignatory = rightSignatory;
	}

	@Column(name = "ORDER_VALUE_DATE", length = 10)
	public String getOrderValueDate() {
		return this.orderValueDate;
	}

	public void setOrderValueDate(String orderValueDate) {
		this.orderValueDate = orderValueDate;
	}

	@Column(name = "PREPARE_DATE", length = 10)
	public String getPrepareDate() {
		return this.prepareDate;
	}

	public void setPrepareDate(String prepareDate) {
		this.prepareDate = prepareDate;
	}

	@Column(name = "ORDER_TYPE", length = 10)
	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name = "URGENT", length = 1)
	public Character getUrgent() {
		return this.urgent;
	}

	public void setUrgent(Character urgent) {
		this.urgent = urgent;
	}

	@Column(name = "BANK_REFERENCE", length = 20)
	public String getBankReference() {
		return this.bankReference;
	}

	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}

}
