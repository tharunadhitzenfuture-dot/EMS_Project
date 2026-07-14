package com.example.EMS.EmployeeDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;


import com.example.EMS.EmployeeEntity.Departments.Departments;
import com.example.EMS.enums.LeaveStatus;
import com.example.EMS.enums.LeaveTime;


public class LeaveRequestDTO {
	
	private Long id;
	private String empId;
	private LocalDate startDate;
	private LocalDate endDate;
	private double totalDays;
	private LeaveTime leaveTime;
	private String leaveType;
	private Departments department;
	private String approverEmail1;
	private String approverEmail2;
	private LeaveStatus status;
	private String reason;
	private String  hrRemarks;
	private String reviewedBy;	
	private LocalDateTime reviewedAt;
	private boolean leavePaid;
	private LocalDateTime createdAt;
	
	public Long getId() {
		return id;
	}
	
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
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
	
	public double getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(double totalDays) {
		this.totalDays = totalDays;
	}

	public LeaveTime getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(LeaveTime leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public Departments getDepartment() {
		return department;
	}
	public void setDepartment(Departments department) {
		this.department = department;
	}
	public String getApproverEmail1() {
		return approverEmail1;
	}
	public void setApproverEmail1(String approverEmail1) {
		this.approverEmail1 = approverEmail1;
	}
	public String getApproverEmail2() {
		return approverEmail2;
	}
	public void setApproverEmail2(String approverEmail2) {
		this.approverEmail2 = approverEmail2;
	}
	public LeaveStatus getStatus() {
		return status;
	}
	public void setStatus(LeaveStatus status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getHrRemarks() {
		return hrRemarks;
	}
	public void setHrRemarks(String hrRemarks) {
		this.hrRemarks = hrRemarks;
	}
	public String getReviewedBy() {
		return reviewedBy;
	}
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}
	public LocalDateTime getReviewedAt() {
		return reviewedAt;
	}
	public void setReviewedAt(LocalDateTime reviewedAt) {
		this.reviewedAt = reviewedAt;
	}
	public boolean isLeavePaid() {
		return leavePaid;
	}
	public void setLeavePaid(boolean leavePaid) {
		this.leavePaid = leavePaid;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
