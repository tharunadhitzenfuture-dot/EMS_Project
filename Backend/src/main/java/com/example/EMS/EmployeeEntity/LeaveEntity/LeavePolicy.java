package com.example.EMS.EmployeeEntity.LeaveEntity;

import com.example.EMS.enums.Department;
import com.example.EMS.enums.LeaveType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Department department;
	@Enumerated(EnumType.STRING)
    @Column(nullable=false)
	private LeaveType type;
	
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
	public LeaveType getType() {
		return type;
	}
	public void setType(LeaveType type) {
		this.type = type;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
	
	
}
