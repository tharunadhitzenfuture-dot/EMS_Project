package com.example.EMS.EmployeeDTO;

import java.time.LocalDate;

import lombok.Data;


@Data
public class WorkingHoursDTO {
	
	private String empId;
	private LocalDate startDate;
	private LocalDate endDate;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
	
	
	
	
	

}
