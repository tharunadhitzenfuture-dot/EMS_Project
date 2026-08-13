package com.example.EMS.Entity.LeaveEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.EMS.Entity.Employee;
import com.example.EMS.Entity.Departments.Departments;
import com.example.EMS.enums.LeaveStatus;
import com.example.EMS.enums.LeaveTime;
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
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonBackReference("employee-leaveRequest")
	private Employee employee;
	
	private LocalDate startDate;
	private LocalDate endDate;
	private double totalDays;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private LeaveTime leaveTime;
	
	@ManyToOne
	@JoinColumn(name="leaveType_id")
	private LeaveType leaveType;
	
	
	
//	@Enumerated(EnumType.STRING)
//	@Column(nullable=false)
//	private LeaveType LeaveTime = LeaveType.FULL_DAY; 
	

	@ManyToOne
	@JoinColumn(name="department_id")
	@JsonIgnore
	private Departments department;
	
	private String approverEmail1;
	private String approverEmail2;
	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private LeaveStatus status = LeaveStatus.PENDING;
	private String reason;
	private String  hrRemarks;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="reviwed_by")
	@JsonIgnore
	private Employee reviewedBy;
	
	@JsonProperty
	 public String getEmployee_Id() {
		 return employee != null ? employee.getEmployeeId() : null;
	 }
	
	private LocalDateTime reviewedAt;
	private boolean leavePaid;
	 @CreationTimestamp 
	 private LocalDateTime createdAt;

	 public Long getId() {
		 return id;
	 }

	 public void setId(Long id) {
		 this.id = id;
	 }
	 
	 

	 public Employee getEmployee() {
		 return employee;
	 }

	 public void setEmployee(Employee employee) {
		 this.employee = employee;
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

	 public Employee getReviewedBy() {
		 return reviewedBy;
	 }

	 public void setReviewedBy(Employee reviewedBy) {
		 this.reviewedBy = reviewedBy;
	 }

	 public LocalDateTime getCreatedAt() {
		 return createdAt;
	 }

	 public void setCreatedAt(LocalDateTime createdAt) {
		 this.createdAt = createdAt;
	 }

	 public LeaveStatus getStatus() {
		 return status;
	 }

	 public void setStatus(LeaveStatus status) {
		 this.status = status;
	 }

	 public LocalDateTime getReviewedAt() {
		 return reviewedAt;
	 }

	 public void setReviewedAt(LocalDateTime reviewedAt) {
		 this.reviewedAt = reviewedAt;
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

	 public Departments getDepartment() {
		 return department;
	 }

	 public void setDepartment(Departments department) {
		 this.department = department;
	 }

	 public LeaveType getLeaveType() {
		 return leaveType;
	 }

	 public void setLeaveType(LeaveType leaveType) {
		 this.leaveType = leaveType;
	 }

	 public boolean isLeavePaid() {
		 return leavePaid;
	 }

	 public void setLeavePaid(boolean leavePaid) {
		 this.leavePaid = leavePaid;
	 }

	 public LeaveTime getLeaveTime() {
		 return leaveTime;
	 }

	 public void setLeaveTime(LeaveTime leaveTime) {
		 this.leaveTime = leaveTime;
	 }

	 public double getTotalDays() {
		 return totalDays;
	 }

	 public void setTotalDays(double totalDays) {
		 this.totalDays = totalDays;
	 }
	 
	 
	 
     
	 
	 
	 
	 
	
}
