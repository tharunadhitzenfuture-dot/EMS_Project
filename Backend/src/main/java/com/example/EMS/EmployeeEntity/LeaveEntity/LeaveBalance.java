package com.example.EMS.EmployeeEntity.LeaveEntity;



import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.enums.Department;
import com.example.EMS.enums.LeaveType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalance {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference("employee-leaveBalance")
    private Employee employee;

    @Column(nullable = false)
    private double totalDays;

    @Column(nullable = false)
    private double usedDays;
    
    @Column(nullable = true)
    private double remainingDays;

    @Column(nullable = false)
    private Integer year;
    
 
    private Integer month;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType type;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;
    

    public double getRemainingDays() {
        return totalDays - usedDays;
    }

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

	public double getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}

	public double getUsedDays() {
		return usedDays;
	}

	public void setUsedDays(Integer usedDays) {
		this.usedDays = usedDays;
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

	public void setTotalDays(double totalDays) {
		this.totalDays = totalDays;
	}

	public void setUsedDays(double usedDays) {
		this.usedDays = usedDays;
	}

	public void setRemainingDays(double remainingDays) {
		this.remainingDays = remainingDays;
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
