package com.example.EMS.EmployeeEntity;


import java.time.LocalDate;
import java.time.LocalTime;

import com.example.EMS.enums.LeaveType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Attendance {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String empName;
	private String designation;
	private String department;
	private LocalDate attendanceDate;
	private LocalTime checkIn;
	private LocalTime checkOut;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=true)
	private LeaveType status = LeaveType.PRESENT;
	private String totalWorkingHours;
	
	
	@JsonProperty("empId")
	public String getEmpId() {
	    return employee != null ? employee.getEmployeeId() : null;
	}
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	@JsonBackReference("employee-attendance")
	private Employee employee;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public LocalTime getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(LocalTime checkIn) {
		this.checkIn = checkIn;
	}
	public LocalTime getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(LocalTime checkOut) {
		this.checkOut = checkOut;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getTotalWorkingHours() {
		return totalWorkingHours;
	}
	public void setTotalWorkingHours(String totalWorkingHours) {
		this.totalWorkingHours = totalWorkingHours;
	}
	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public LeaveType getStatus() {
		return status;
	}
	public void setStatus(LeaveType status) {
		this.status = status;
	}

	
	
}