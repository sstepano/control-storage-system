package org.code_studio.controller;

import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.scene.Node;
import javafx.scene.control.Button;

import org.code_studio.database.BankStatement;
import org.code_studio.database.BankStatementDetail;
import org.code_studio.database.ClientBankAccount;
import org.code_studio.database.ClientName;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.component.ControllerFactory;

public class Finance_BankStatementDetailAddEditController extends BaseController {
	@FXML Button btnSave;
	@FXML Button btnCancel;
	
	@FXML private CSTextField  tfBankStatementId;
	@FXML private CSTextField  tfAccountId;
	@FXML private CSTextField  tfBankStatementNumber;
	@FXML private CSDatePicker dtBankStatementDate;
	@FXML private CSTextField  tfClientAccountNumber;
	@FXML private Button       btnClientChoose;
	@FXML private CSDatePicker dtValueDate;
	@FXML private CSTextField  tfPaymentAmt;
	@FXML private Button btnClientBankAccountChoose;
	
	private int mode;
	private CSTable<BankStatementDetail> tblBankStatementDetail;
	private BankStatementDetail bankStatementDetail;
	private BankStatement bankStatement;
	
	private final String urlAddUpdateDelete = "/bankStatementDetail";
	private CSRestService<BankStatementDetail> rsAddUpdateDelete;
	
	private final String urlBankStatementDetailMaxNumberByBankStatementId = "/bankStatementDetail/maxNumberByBankStatementId/";
	private CSRestService<Integer> rsBankStatementDetailMaxNumberByBankStatementId;
	
	private ClientBankAccount clientBankAccount;
	
	@SuppressWarnings("unchecked")
	public Finance_BankStatementDetailAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		tblBankStatementDetail = (CSTable<BankStatementDetail>) controllerParam;
		bankStatementDetail = tblBankStatementDetail.getSelectedItem();
		bankStatement = (BankStatement) tblBankStatementDetail.getParentTable().getSelectedItem();
	}


	public void initialize() {
		try {
	
			tfBankStatementId.setText(bankStatement.getId().toString());
			tfAccountId.setText(bankStatement.getAccountId().toString());
			tfBankStatementNumber.setText(bankStatement.getBankStatementNumber().toString());
			dtBankStatementDate.setValue(bankStatement.getBankStatementDate());
			
			if (mode == 1) {
				tfClientAccountNumber.setText(bankStatementDetail.getClientAccountNumber());
				dtValueDate.setValue(bankStatementDetail.getValueDate());
				tfPaymentAmt.setText(bankStatementDetail.getAmount().toString());
			}

			btnSave.setOnAction( e-> {
				rsAddUpdateDelete = new CSRestService<>(urlAddUpdateDelete);
				rsBankStatementDetailMaxNumberByBankStatementId = new CSRestService<>(urlBankStatementDetailMaxNumberByBankStatementId);
			
				if (mode == 0) {
					bankStatementDetail = new BankStatementDetail();
					rsBankStatementDetailMaxNumberByBankStatementId.setUrl(urlBankStatementDetailMaxNumberByBankStatementId + bankStatement.getId());
					rsBankStatementDetailMaxNumberByBankStatementId.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});
					if (rsBankStatementDetailMaxNumberByBankStatementId.getData() != null) {
					    bankStatementDetail.setDetailOrdinalNumber(rsBankStatementDetailMaxNumberByBankStatementId.getData().get(0));
					}
					bankStatementDetail.setClientId(clientBankAccount.getClientId());
					bankStatementDetail.setClient(new ClientName(clientBankAccount.getClientId(), clientBankAccount.getClientName())); // ne snima se u bazu, ovo mi treba za prikaz u gridu
					bankStatementDetail.setBankStatementId(bankStatement.getId());
					bankStatementDetail.setClientBankAccount(clientBankAccount); // ne snima se u bazu, ovo mi treba za prikaz u gridu
				}

				bankStatementDetail.setClientBankAccountId(
					clientBankAccount != null 
					? clientBankAccount.getId().intValue()
					: bankStatementDetail.getClientBankAccountId()
				);
				bankStatementDetail.setValueDate(dtValueDate.getValue());
				bankStatementDetail.setAmount(tfPaymentAmt.getTextAsBigDecimal());
				
				BankStatementDetail insertedItem = rsAddUpdateDelete.addOrUpdate(bankStatementDetail);
				if (mode == 0) {
					tblBankStatementDetail.addItem(insertedItem);
				}
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
	
			});
	
			btnCancel.setOnAction(e->{
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
			btnClientBankAccountChoose.setOnAction( _ -> {
				clientBankAccount = (ClientBankAccount) Common.displayForm(ControllerFactory.getController("Lookup_ClientBankAccountController", null, 2), btnClientBankAccountChoose, "Odabir računa");
				if (clientBankAccount != null) {
					tfClientAccountNumber.setText(clientBankAccount.getAccountNumber());
				}
			});
			
			btnSave.disableProperty().bind(Bindings.createBooleanBinding(
					()-> !validateInput(),
					tfClientAccountNumber.textProperty(),
					dtValueDate.valueProperty(),
					tfPaymentAmt.textProperty()
			));
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
	private Boolean validateInput() {
		if (tfClientAccountNumber.getText().isBlank()) return false;
		if (dtValueDate.getValue() == null) return false;
		if (tfPaymentAmt.getText().equals("0.00")) return false;
		return true;
	}
	
}
