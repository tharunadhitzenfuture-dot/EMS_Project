package com.example.EMS.EmployeeDTO;

import lombok.Data;


public class LeaveBalanceDTO {
	
	   private Long id;
	   private String employeeId;
	   private double totalDays;
	   private double usedDays;
	   private double remainingDays;
	   private Integer year;
	   private Long leaveType;
	   private Long department;
	   public Long getId() {
		   return id;
	   }
	   public void setId(Long id) {
		   this.id = id;
	   }
	   
	   public String getEmployeeId() {
		return employeeId;
	}
	   public void setEmployeeId(String employeeId) {
		   this.employeeId = employeeId;
	   }
	   public double getTotalDays() {
		   return totalDays;
	   }
	   public void setTotalDays(double totalDays) {
		   this.totalDays = totalDays;
	   }
	   public double getUsedDays() {
		   return usedDays;
	   }
	   public void setUsedDays(double usedDays) {
		   this.usedDays = usedDays;
	   }
	   public double getRemainingDays() {
		   return remainingDays;
	   }
	   public void setRemainingDays(double remainingDays) {
		   this.remainingDays = remainingDays;
	   }
	   public Integer getYear() {
		   return year;
	   }
	   public void setYear(Integer year) {
		   this.year = year;
	   }
	   public Long getLeaveType() {
		   return leaveType;
	   }
	   public void setLeaveType(Long leaveType) {
		   this.leaveType = leaveType;
	   }
	   public Long getDepartment() {
		   return department;
	   }
	   public void setDepartment(Long department) {
		   this.department = department;
	   }
	   
	   
	   
	    
	    

	   

}
