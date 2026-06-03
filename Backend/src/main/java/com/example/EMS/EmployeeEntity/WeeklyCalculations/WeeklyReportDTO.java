package com.example.EMS.EmployeeEntity.WeeklyCalculations;

import java.util.List;

import lombok.Data;


@Data
public class WeeklyReportDTO {
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Long id;
//	
	private String empId;
	private String empName;
	private List<String> hours;
	private String totalHours;
	
	private String startDate;
	private String endDate;
	
	private String permission;
	private String compensation;
	private String overTime;
	private String shortFall;
	private String status;
	
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
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
//	public String getPermission() {
//		return permission;
//	}
//	public void setPermission(String permission) {
//		this.permission = permission;
//	}
//	public String getCompensation() {
//		return compensation;
//	}
//	public void setCompensation(String compensation) {
//		this.compensation = compensation;
//	}
//	public String getOverTime() {
//		return overTime;
//	}
//	public void setOverTime(String overTime) {
//		this.overTime = overTime;
//	}
//	public String getShortFall() {
//		return shortFall;
//	}
//	public void setShortFall(String shortFall) {
//		this.shortFall = shortFall;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
	

}
