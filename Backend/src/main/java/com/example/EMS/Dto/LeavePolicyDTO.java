package com.example.EMS.EmployeeDTO;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class LeavePolicyDTO {
	
	private Long id;
	private Integer totalDays;
	private Integer year;
	private Integer month;
	

	private String leaveType;
	
	
	private String department;
	
	private Integer carryForward;
	private boolean encashment;
	private boolean status;
	private LocalDateTime lastUpdateDateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Integer getCarryForward() {
		return carryForward;
	}
	public void setCarryForward(Integer carryForward) {
		this.carryForward = carryForward;
	}
	public boolean isEncashment() {
		return encashment;
	}
	public void setEncashment(boolean encashment) {
		this.encashment = encashment;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public LocalDateTime getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}
	public void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}
	
	

}
