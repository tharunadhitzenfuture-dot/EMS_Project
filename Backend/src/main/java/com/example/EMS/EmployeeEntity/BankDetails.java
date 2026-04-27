package com.example.EMS.EmployeeEntity;

import jakarta.persistence.Embeddable;

@Embeddable
public class BankDetails {
	
	private String bankName;
	private String accountHolderName;
	private Long accountNumber;
	private Long confirmAccountNumber;
	private String branchName;
	private Long IFSC_Number;
	private String passbook_pdf;
	
	public String getPassbook_pdf() {
		return passbook_pdf;
	}
	public void setPassbook_pdf(String passbook_pdf) {
		this.passbook_pdf = passbook_pdf;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Long getConfirmAccountNumber() {
		return confirmAccountNumber;
	}
	public void setConfirmAccountNumber(Long confirmAccountNumber) {
		this.confirmAccountNumber = confirmAccountNumber;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getIFSC_Number() {
		return IFSC_Number;
	}
	public void setIFSC_Number(Long iFSC_Number) {
		IFSC_Number = iFSC_Number;
	}
	
	
	

}
