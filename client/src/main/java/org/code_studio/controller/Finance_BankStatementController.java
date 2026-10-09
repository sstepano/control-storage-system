package org.code_studio.controller;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.fxml.FXML;

import org.code_studio.component.CSTable;
import org.code_studio.component.CSTextField;
import org.code_studio.database.BankAccount;
import org.code_studio.database.BankStatement;
import org.code_studio.database.BankStatementDetail;
import org.code_studio.database.ItemBalance;
import org.code_studio.main.Common;
import org.code_studio.rest.CSRestService;
import org.code_studio.rest.JsonResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

public class Finance_BankStatementController extends BaseController {

	@FXML private CSTable <BankAccount> tblBankAccount;
	@FXML private CSTable <BankStatement> tblBankStatement;
	@FXML private CSTable <BankStatementDetail> tblBankStatementDetail;
	@FXML private CSTable <ItemBalance> tblBankStatementAnalytics;

	@FXML private CSTextField  tfTotalAmt;

	private int mode;
	private final String bankStatementAddEditController = "Finance_BankStatementAddEditController";
	private final String bankStatementDetailAddEditController = "Finance_BankStatementDetailAddEditController";
	
	final String urlBankAccount = "/bankAccount";
	CSRestService<BankAccount> rsBankAccount;
	
	final String urlBankStatement = "/bankStatement/allPageableByAccountId/";
	CSRestService<BankStatement> rsBankStatement;
	
	final String urlBankStatementDelete = "/bankStatement";
	CSRestService<BankStatement> rsBankStatementDelete;
	
	final String urlBankStatementDetail = "/bankStatementDetail/allPageableByBankStatementId/";
	CSRestService<BankStatementDetail> rsBankStatementDetail;
	
	final String urlBankStatementDetailDelete = "/bankStatementDetail";
	CSRestService<BankStatementDetail> rsBankStatementDetailDelete;
		
	AtomicInteger bankStatementPageId;
	AtomicInteger bankStatementDetailPageId;

	public Finance_BankStatementController(Object controllerParam, int mode, ApplicationContext ctx) {
		this.mode = mode;
		bankStatementPageId = new AtomicInteger(0);
		bankStatementDetailPageId = new AtomicInteger(0);
	}
	

	public void initialize() {
		try {
			rsBankAccount = new CSRestService<>(urlBankAccount);
			rsBankAccount.fetch(new ParameterizedTypeReference<JsonResponse<BankAccount>>() {});
			tblBankAccount.setItems(rsBankAccount.getDataAsObservableList());
			
			rsBankStatement = new CSRestService<>(urlBankStatement);
			rsBankStatementDetail = new CSRestService<>(urlBankStatementDetail);
			rsBankStatementDetailDelete = new CSRestService<>(urlBankStatementDetailDelete);
			rsBankStatementDelete = new CSRestService<>(urlBankStatementDelete);
			
			tblBankAccount.onRowSelectionChanged((_, newRow) -> {
				BankAccount bankAccount = (BankAccount) newRow;
				bankStatementPageId.set(0);
				rsBankStatement.setUrl(urlBankStatement + bankAccount.getId());
				rsBankStatement.fetch(bankStatementPageId.get(), new ParameterizedTypeReference<JsonResponse<BankStatement>>() {});
				tblBankStatement.setItems(rsBankStatement.getDataAsObservableList());				
			});

			
			//BANK STATEMENT
			tblBankStatement.setParentTable(tblBankAccount);
			tblBankStatement.setAddEditDialog(bankStatementAddEditController);
			tblBankStatement.setRestServiceDelete(rsBankStatementDelete);
			tblBankStatement.onRowDoubleClick(( _ ) -> {
				tblBankStatement.showDoubleClickDefaultAction = true;
			});
			
			tblBankStatement.onDataNeeded( () -> {
				rsBankStatement.fetch(bankStatementPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<BankStatement>>() {});
				tblBankStatement.addItems(rsBankStatement.getDataAsObservableList());
			});
			
			tblBankStatement.onRowSelectionChanged( (_, newRow) -> {
				BankStatement bankStatement = (BankStatement) newRow;
				if (bankStatement != null) {
					bankStatementDetailPageId.set(0);
					rsBankStatementDetail.setUrl(urlBankStatementDetail + bankStatement.getId());
					rsBankStatementDetail.fetch(bankStatementDetailPageId.get(), new ParameterizedTypeReference<JsonResponse<BankStatementDetail>>() {});
					tblBankStatementDetail.setItems(rsBankStatementDetail.getDataAsObservableList());
				}
			});
			
			
			//BANK STATEMENT DETAIL
			tblBankStatementDetail.setParentTable(tblBankStatement);
			tblBankStatementDetail.setAddEditDialog(bankStatementDetailAddEditController);
			tblBankStatementDetail.setRestServiceDelete(rsBankStatementDetailDelete);
			tblBankStatementDetail.onRowDoubleClick(( _ ) -> {
				tblBankStatementDetail.showDoubleClickDefaultAction = true;
			});
			
			tblBankStatementDetail.onDataNeeded(() -> {
				rsBankStatementDetail.fetch(bankStatementDetailPageId.incrementAndGet(), new ParameterizedTypeReference<JsonResponse<BankStatementDetail>>() {});
				tblBankStatementDetail.addItems(rsBankStatementDetail.getDataAsObservableList());
			});
			
			
			//TODO
			if(mode == 0) {
				
			}

			
			} catch (Exception ex) {
				Common.ShowNotification("GREŠKA", "Došlo je do greške kod prikazivanja forme", true);
				Common.logMessage(getClass(), ex, "ERROR");
			}
		} // initialize END
	
}
