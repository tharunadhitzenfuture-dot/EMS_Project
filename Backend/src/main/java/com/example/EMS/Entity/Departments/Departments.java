package com.example.EMS.Entity.Departments;

import java.util.ArrayList;
import java.util.List;

import com.example.EMS.Entity.ProfessionalDetails;
import com.example.EMS.Entity.LeaveEntity.LeaveBalance;
import com.example.EMS.Entity.LeaveEntity.LeavePolicy;
import com.example.EMS.Entity.LeaveEntity.LeaveRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    
    private String description;
    
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
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<LeaveRequest> getLeaveRequest() {
		return leaveRequest;
	}

	public void setLeaveRequest(List<LeaveRequest> leaveRequest) {
		this.leaveRequest = leaveRequest;
	}

	public List<ProfessionalDetails> getProfessionalDetails() {
		return professionalDetails;
	}

	public void setProfessionalDetails(List<ProfessionalDetails> professionalDetails) {
		this.professionalDetails = professionalDetails;
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
