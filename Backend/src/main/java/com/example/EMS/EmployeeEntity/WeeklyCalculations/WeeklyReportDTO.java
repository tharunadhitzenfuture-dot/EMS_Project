package com.example.EMS.EmployeeEntity.WeeklyCalculations;

import java.time.LocalDate;
import java.util.List;

import com.example.EMS.EmployeeEntity.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
public class WeeklyReportDTO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String empId;
	private String empName;
	private List<String> hours;
	private String totalHours;
	
	private LocalDate startDate;
	private LocalDate endDate;
	
	private String department_workHours;
	
	private String permission;
	private String compensation;
	private String overTime;
	private String shortFall;
	private String status;
	
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonBackReference("employee-weeklyReport")
	private Employee employee;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public List<String> getHours() {
		return hours;
	}
	public void setHours(List<String> hours) {
		this.hours = hours;
	}
	public String getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}
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
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getCompensation() {
		return compensation;
	}
	public void setCompensation(String compensation) {
		this.compensation = compensation;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getShortFall() {
		return shortFall;
	}
	public void setShortFall(String shortFall) {
		this.shortFall = shortFall;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getDepartment_workHours() {
		return department_workHours;
	}
	public void setDepartment_workHours(String department_workHours) {
		this.department_workHours = department_workHours;
	}
	
	
	

}
