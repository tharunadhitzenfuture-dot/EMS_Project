package com.example.EMS.EmployeeEntity.Departments;

import java.util.ArrayList;
import java.util.List;

import com.example.EMS.EmployeeEntity.ProfessionalDetails;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveBalance;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeavePolicy;
import com.example.EMS.EmployeeEntity.LeaveEntity.LeaveRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    
    @OneToMany(mappedBy="department")
    @JsonIgnore
    private List<LeavePolicy> leavePolicy = new ArrayList<>();
    
    @OneToMany(mappedBy="department")
    @JsonIgnore
    private List<LeaveBalance> leaveBalance = new ArrayList<>();
    
    @OneToMany(mappedBy="department")
    @JsonIgnore
    private List<LeaveRequest> leaveRequest = new ArrayList<>();
    
    @OneToMany(mappedBy="professional_department")
    @JsonIgnore
    private List<ProfessionalDetails> professionalDetails = new ArrayList<>();
   

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

	public List<LeaveBalance> getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(List<LeaveBalance> leaveBalance) {
		this.leaveBalance = leaveBalance;
	}
    
	
    
}
