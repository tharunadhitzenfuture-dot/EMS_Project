package com.example.EMS.EmployeeEntity.WeeklyCalculations;

import java.time.LocalDate;

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
	private Long totalWorkDays;
	private Long totalWorkHours;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getTotalWorkDays() {
		return totalWorkDays;
	}
	public void setTotalWorkDays(Long totalWorkDays) {
		this.totalWorkDays = totalWorkDays;
	}
	public Long getTotalWorkHours() {
		return totalWorkHours;
	}
	public void setTotalWorkHours(Long totalWorkHours) {
		this.totalWorkHours = totalWorkHours;
	}
	
	
	
	
	
	
	
}
