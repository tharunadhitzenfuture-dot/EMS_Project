package com.example.EMS.EmployeeEntity.LeaveEntity;





import com.example.EMS.EmployeeEntity.Employee;
import com.example.EMS.EmployeeEntity.Departments.Departments;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    
    @ManyToOne
	@JoinColumn(name="leaveType_id")
	private LeaveType leaveType;
    
    
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Department department;
    
    @ManyToOne
    @JoinColumn(name="department_id")
    @JsonIgnore
    private Departments department;
    
    
    @JsonProperty
	 public String getEmployee_Id() {
		 return employee != null ? employee.getEmployeeId() : null;
	 }
   
   @JsonProperty
	 public String getLeaveTypeName() {
		 return leaveType != null ? leaveType.getName() : null;
	 }
   
   @JsonProperty
	 public String getDepartmentName() {
		 return department != null ? department.getName() : null;
	 }
   
    

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

	
    
	
    
    
}
