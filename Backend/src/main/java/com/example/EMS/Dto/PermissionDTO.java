package com.example.EMS.EmployeeDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;


import com.example.EMS.enums.LeaveStatus;
import com.example.EMS.enums.LeaveTypes;



public class PermissionDTO {
	
	private Long id;
	private String reason;
	private String hours;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate permissionDate;
	private String employeeId;
	private LeaveTypes leaveType;
	private String approverEmail1;
	private String approverEmail2;
	private String approver1Detail;
	private LeaveStatus status;
	private String  hrRemarks;
	private String reviewedBy;
	private LocalDateTime reviewedAt;
	private LocalDateTime createdAt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
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
	public LocalDate getPermissionDate() {
		return permissionDate;
	}
	public void setPermissionDate(LocalDate permissionDate) {
		this.permissionDate = permissionDate;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public LeaveTypes getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveTypes leaveType) {
		this.leaveType = leaveType;
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
	public String getApprover1Detail() {
		return approver1Detail;
	}
	public void setApprover1Detail(String approver1Detail) {
		this.approver1Detail = approver1Detail;
	}
	public LeaveStatus getStatus() {
		return status;
	}
	public void setStatus(LeaveStatus status) {
		this.status = status;
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
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	

	

}
