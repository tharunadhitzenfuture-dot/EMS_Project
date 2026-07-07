package com.example.EMS.EmployeeDTO;

import lombok.Data;

@Data
public class CompanyDetailDTO {
	
	private String companyName;
	private String companyLogo;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	
	

}
