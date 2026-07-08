package com.example.EMS.EmployeeEntity.LeaveEntity;

import java.util.List;

import com.example.EMS.EmployeeEntity.Attendance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public class LeaveType {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToMany
	private List<LeavePolicy> leavePolicy;
	
	@OneToMany
	private List<LeaveBalance> leaveBalance;
	
	@OneToMany
	private List<LeaveRequest> leaveRequest;
	
	@OneToMany
	private List<Attendance> attendance;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public List<LeavePolicy> getLeavePolicy() {
		return leavePolicy;
	}
	public void setLeavePolicy(List<LeavePolicy> leavePolicy) {
		this.leavePolicy = leavePolicy;
	}
	public List<LeaveRequest> getLeaveRequest() {
		return leaveRequest;
	}
	public void setLeaveRequest(List<LeaveRequest> leaveRequest) {
		this.leaveRequest = leaveRequest;
	}
	public List<Attendance> getAttendance() {
		return attendance;
	}
	public void setAttendance(List<Attendance> attendance) {
		this.attendance = attendance;
	}
	public List<LeaveBalance> getLeaveBalance() {
		return leaveBalance;
	}
	public void setLeaveBalance(List<LeaveBalance> leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	
	
	
	
	
	
}
