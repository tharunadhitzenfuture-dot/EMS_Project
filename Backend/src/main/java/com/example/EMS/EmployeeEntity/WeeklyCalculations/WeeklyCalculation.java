package com.example.EMS.EmployeeEntity.WeeklyCalculations;



import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeeklyCalculation {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private LocalDate startDate;
	private LocalDate endDate;
	
	@Column(unique=true)
	private String deptName;
	private Long totalWorkDays;
	private Long workHours;
	private Long totalWorkHours;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	//	public LocalDate getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(LocalDate startDate) {
//		this.startDate = startDate;
//	}
//	public LocalDate getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(LocalDate endDate) {
//		this.endDate = endDate;
//	}
	public Long getTotalWorkDays() {
		return totalWorkDays;
	}
	public void setTotalWorkDays(Long totalWorkDays) {
		this.totalWorkDays = totalWorkDays;
	}
	
	public Long getWorkHours() {
		return workHours;
	}
	public void setWorkHours(Long workHours) {
		this.workHours = workHours;
	}
	public Long getTotalWorkHours() {
		return totalWorkHours;
	}
	public void setTotalWorkHours(Long totalWorkHours) {
		this.totalWorkHours = totalWorkHours;
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
