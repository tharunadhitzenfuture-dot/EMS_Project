package com.example.EMS.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ApprovalSystem {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;	
	private String approverEmail1;
	private String approverEmail2;
	private String projectName;
	
	@OneToOne
	@JoinColumn(name = "employee_id")
	@JsonBackReference("employee-Report")
	private Employee employee;
	
	@JsonProperty
	 public String getEmployee_Id() {
		 return employee != null ? employee.getEmployeeId() : null;
	 }
	

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	
	
}
