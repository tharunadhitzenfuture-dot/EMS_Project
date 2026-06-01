package com.example.EMS.EmployeeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CompanyPolicy {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String companyName;
	private String weeklyWorking;
	private String halfDayHours;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getWeeklyWorking() {
		return weeklyWorking;
	}
	public void setWeeklyWorking(String weeklyWorking) {
		this.weeklyWorking = weeklyWorking;
	}
	public String getHalfDayHours() {
		return halfDayHours;
	}
	public void setHalfDayHours(String halfDayHours) {
		this.halfDayHours = halfDayHours;
	}
	
	
	

}
