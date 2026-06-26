package com.example.EMS.EmployeeEntity.LeaveEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.enums.LeaveStatus;
import com.example.EMS.enums.LeaveType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Permission {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String reason;
	private String hours;
	
	private LocalDate startDate;
	private LocalDate endDate;
	
	private LocalDate permissionDate;
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonBackReference("employee-permissionRequest")
	private Employee employee;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private LeaveType leaveType = LeaveType.PERMISSION;
	
	private String approverEmail1;
	private String approverEmail2;
	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private LeaveStatus status = LeaveStatus.PENDING;
	private String  hrRemarks;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="reviwed_by")
	@JsonIgnore
	private Employee reviewedBy;
	
	private LocalDateTime reviewedAt;
	
	 @CreationTimestamp 
	 private LocalDateTime createdAt;
	 
	 @JsonProperty
	 public String getEmpId() {
		    return employee != null ? employee.getEmployeeId() : null;
		}
	 
	

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

	 public Employee getEmployee() {
		 return employee;
	 }

	 public void setEmployee(Employee employee) {
		 this.employee = employee;
	 }

	 public LeaveType getLeaveType() {
		 return leaveType;
	 }

	 public void setLeaveType(LeaveType leaveType) {
		 this.leaveType = leaveType;
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

	 public Employee getReviewedBy() {
		 return reviewedBy;
	 }

	 public void setReviewedBy(Employee reviewedBy) {
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



	 public void setPermissionDate(LocalDate permissionDate) {
		 this.permissionDate = permissionDate;
	 }
	 

	
}
