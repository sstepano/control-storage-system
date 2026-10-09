package org.code_studio.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.code_studio.component.CSDatePicker;
import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.BankAccount;
import org.code_studio.database.BankStatement;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Finance_BankStatementAddEditController extends BaseController {
	@FXML Button btnSave;
	@FXML Button btnCancel;
	
	@FXML CSTextField  tfBankStatementId;
	@FXML CSTextField  tfAccountNumber;
	@FXML CSTextField  tfYear;
	@FXML CSTextField  tfBankStatementNumber;
	@FXML CSDatePicker dtBankStatementDate;
	@FXML CSTextField  tfTotalAmt;
	
	@FXML Button btnItemList;
	
	private int mode;
	private BankStatement bankStatement;
	private CSTable<BankStatement> tblBankStatement;

	private final String urlBankStatement = "/bankStatement";
	private CSRestService<BankStatement> rsAddOrUpdate;
	
	private final String urlBankStatementMaxNumberByYearAndAccount = "/bankStatement/maxNumberByAccountIdAndYear/";
	private CSRestService<Integer> rsBankStatementMaxNumberByYearAndAccount;
	
	@SuppressWarnings("unchecked")
	public Finance_BankStatementAddEditController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		tblBankStatement = (CSTable<BankStatement>) controllerParam;
		bankStatement = tblBankStatement.getSelectedItem();
	}


	public void initialize() {
		try {
			tfAccountNumber.setText(((BankAccount) tblBankStatement.getParentTable().getSelectedItem()).getAccountNumber());
			tfYear.setText(String.valueOf(LocalDate.now().getYear()));
			
			tfTotalAmt.onTextChanged((_, _) -> {
				if (tfTotalAmt.getTextAsBigDecimal().signum() > 0) {
					btnSave.setDisable(false);
				} else {
					btnSave.setDisable(true);
				}
			});
			
			
			if (mode == 0) {
				bankStatement = new BankStatement();
				bankStatement.setAccountId(((BankAccount)tblBankStatement.getParentTable().getSelectedItem()).getId().intValue());
			} else {
				tfBankStatementId.setText(bankStatement.getId().toString());
				tfBankStatementNumber.setText(bankStatement.getBankStatementNumber().toString());
				//ako unesem novi statement i koristim bankStatement.getBankAccount().getAccountNumber(), imam null exception, zato sto postavljam sa setAccountId i getBankAccount je u ovom trenutku null
				tfAccountNumber.setText(((BankAccount)tblBankStatement.getParentTable().getSelectedItem()).getAccountNumber());
				tfYear.setText(bankStatement.getYear().toString());
				dtBankStatementDate.setValue(bankStatement.getBankStatementDate());
				tfTotalAmt.setText(bankStatement.getAmount().toString());
			}
			
			btnSave.setOnAction( e-> {
				bankStatement.setCreatedDate(LocalDateTime.now());
				bankStatement.setBankStatementDate(dtBankStatementDate.getValue());
				bankStatement.setAmount(tfTotalAmt.getTextAsBigDecimal());
				
				//max number per year
				rsBankStatementMaxNumberByYearAndAccount = new CSRestService<>(urlBankStatementMaxNumberByYearAndAccount + bankStatement.getAccountId() + "/" + LocalDate.now().getYear());
				rsBankStatementMaxNumberByYearAndAccount.fetch(new ParameterizedTypeReference<JsonResponse<Integer>>() {});
				if (rsBankStatementMaxNumberByYearAndAccount.getData() != null) {
					bankStatement.setBankStatementNumber(rsBankStatementMaxNumberByYearAndAccount.getData().get(0));
				}
				
				rsAddOrUpdate = new CSRestService<>(urlBankStatement);
				
				BankStatement insertedItem = rsAddOrUpdate.addOrUpdate(bankStatement);
				if (mode == 0) {
					tblBankStatement.addItem(insertedItem);
				}
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
	
			});
	
			btnCancel.setOnAction(e->{
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
			});
			
		} catch (Exception ex) {
			Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
			Common.logMessage(getClass(), ex, "ERROR");
		}
	} // initialize end
	
}
