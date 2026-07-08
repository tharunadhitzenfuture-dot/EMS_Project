package com.example.EMS.EmployeeEntity.LeaveEntity;



import java.time.LocalDateTime;

import com.example.EMS.EmployeeEntity.Departments.Departments;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LeavePolicy {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private Integer totalDays;
	private Integer year;
	private Integer month;
	
//	@Enumerated(EnumType.STRING)
//    @Column(nullable=false)
//	private LeaveType type;
	
	@ManyToOne
	@JoinColumn(name="leaveType_id")
	private LeaveType leaveType;
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Departments department;
	
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
	
	
	public LeaveType getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}
	public Departments getDepartment() {
		return department;
	}
	public void setDepartment(Departments department) {
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
